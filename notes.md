# Swagger format

Top level keys of interest:
* schemes - http or https
* host - management.azure.com
* info - General info about client
    - description
    - title - Use to instantiate Client?
    - version - Maybe use as well
    - Header stuff
* securityDefinitions - Auth info
* parameters - Parameter definitions (query, path, body) that can be referenced in endpoints
* defintions - Same as parameters, but for definitions (what are they?)
* paths - each api path as a kv-pair

We need schemes and host to construct the url, but paths is the main course. Let's take a look at an example path, from `apimanagement-apimissues`:

* "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.ApiManagement/service/{serviceName}/issues"

Lots of path parameters! How do we know how they're specified? Let's explore further.

The value of the path is a map of verb-map pairs. This path in particular only has a get verb. Some of its keys are:

* description
* operationId - Issue_ListByService
    - We'll use this to invoke the endpoints
* parameters - list of parameters (path, query or body)
* responses - Response codes (200, 404, default)
    - Irrelevant for performing requests. Useful for generating docs
    - Ignored for now

some of parameter's keys are:
* in - path, query, body
* name - serviceName, etc
    - If it's a pathParameter this needs to be subbed into the url
    <!-- - query params have a $ in front of them (why?) They don't? -->
* various fields for verification
    - required
    - minLength
    - maxLength
    - (regex) pattern
    - type

aws-api invocation:
```clojure
(aws/invoke s3 {:op :CreateBucket :request {:Bucket "my-unique-bucket-name-in-us-west-1"
                                            :CreateBucketConfiguration
                                            {:LocationConstraint "us-west-1"}}})
```

An example call based on the above example of `apimanagement-apimissues` might look like this:

```clojure
(azure/invoke ApiManagementClient {:op  :Issue_ListByService
                                   :request {:resourceGroupName "resource"
                                             :serviceName "some-name"
                                             :api-version "idk, get from client?"
                                             :subscriptionId 324
                                             :top 10}})
```
Here we just pass all parameters in through request (path, query, body).
Case-wise, we're just using Whatever's been used by the swagger-file.

How is this used to construct and send off a request map?

aws-api creates a repo/library for each service. That might be difficult for us, since the swaggerfiles make the APIs heavily segmented (+20 swaggerfiles for apimanagement alone).

Let's say we just store all the (potentially processed) swaggerfiles in a folder.
When we create a client, we can just load the file as a map. Since we invoke with the client, the map will be available to us when we construct the request.

That doesn't seem too bad. Do we want to do some processing (like gcp swagger->descriptor)?
Swagger->Descriptor also resolves the refs. Honestly, we could pretty much reuse the gcp code. It's not particularly difficult to rewrite, but it's going to look awfully similar.

We might want to merge similar swagger.jsons together at a later stage (like apimanagement) so we oughta make sure that we at least have a good grasp of our code.
But let's write something like swagger->descriptor

Example of a referenced parameter:
 
```clojure
:parameters [
  {
    :$ref "#/parameters/ApiVersionParameter"
}
```



# Body

Most of the basic framework is now functional (in a PoC way). Body parameters are still not handled. They seem difficult.

- Bodies almost always contain references to definitions
- Definitions usually contain nested references (often multiple levels)
- Body can contain nested objects

Not immediately obvious how to handle this wrt how the user creates his request map.
Currently we want the user to not have to specify parameter type (path, query, etc).
This works fine for path and query params since they have a flat structure, but things are less obvious for bodies with nested objects.

Anatomy of a body parameter:

- in: Always set to body
- name and description: whatever
- schema: map containing the interesting stuff
    - type: usually object (json). Can be a primitive (e.g. string) if the body is just a primitive. If *not* object, the properties fields does not exist
    - required: vec of required fields if object, bool if primitive
    - properties: A map of the object parameters themselves


For properties, the key is the name of the field. This name can be referenced in the "required" field.
The value is a type definition. It could either be a primitive, like `"type": "string"`, or an object definition, in which case the structure is the same as for schema.

Let's take a look at an example. We have a json body with the following structure:
```json
{
  "id": "10",
  "user": {"firstName": "John",
         "lastName": "Doe"
         }
}


```
The parameter entry for body could look like this:

```json
{
"in": "body",
"name": "userInfo",
"description": "Info about the user",
"schema": {"type": "object",
        "required": ["user", "id"],
        "properties": {"id": {"type": "string"},
                      "user": {"type": "object",
                               "required": ["firstName", "lastName"],
                               "properties": {"firstName": {"type": "string"},
                                              "lastName": {"type": "string"}}
                      }}
        }
}
```

We might begin by assuming that the user passes in a map identical to the json object, without specifying that we're sending in body parameters. 

Complications: Swagger supports several additional fields with varying behavior. All specified here: https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md
E.g. In PeeringManagementClient, we define a schema by a reference to a definition. That definition contains an "allOf", which in turn contains a reference to another definition.
We may assume that there are several cases like this, each of which has to be solved.

From what I gather this is not something that gcp-api addresses at all, possibly due to the gcp swaggerfiles being simpler.

Another thing to consider is that the structure could contain references, like this:

```json
{
"in": "body",
"name": "userInfo",
"description": "Info about the user",
"schema": {"reference/definitions": "userInfo"}
}

"definitions" : {"userInfo":
                {"type": "object",
                 "required": ["user", "id"],
                 "properties": {"id": {"type": "string"},
                                "user": {"reference/definitions": "user"}}
                },
              "user": {"type": "object",
                       "required": ["firstName", "lastName"],
                       "properties": {"firstName": {"type": "string"},
                                       "lastName": {"type": "string"}}
                      }
}
```

*Note: this is the descriptor.edn format.*

The current reference solution is to resolve references upon request construction, not while creating the descriptor files.

This is done by mapping a resolving function over the parameter vector in one pass.
This generally works for path and query parameters, because the references are at top-level, and nested references are rare.

For body parameters, this is completely infeasible. References are usually (not necessarily) placed at the schema field, and references are often nested.

The initially attempted solution was use postwalk to resolve all references at descriptor-generation time. This could cause files to balloon 10-100x, so it's not feasible.
The optimal solution would be to only resolve the parameters that are used. Currently, we only resolve parameters for the operation in use. Extending this solution to resolve recursively is probably the best solution.

When structure-parameters encounters a :body param, it dispatches to structure-body with the parameter and req-value.

At this point, body might look like this:

```clojure
{:description "The properties needed to create or update a peering service.",
:in "body",
:name "peeringService",
:required true,
:schema {
  :description "Peering Service",
  :properties {
    :location {:description "The location of the resource.",
               :type "string"},
    :userInfo {:description "Info about user.",
                :type "object"
                :properties {
                  :id {:description "user id",
                       :type "string"
                       :required true}}
               }
    :required ["location" "userInfo"],
    :type "object"},
}}
```

In reality it's a bit more complex, but let's start with this.

Anyways, this is what we pass in to structure-body, along with a req map which could contain something like `:peeringService {:location "Right here"}`.

Properties is the most interesting thing. Since properties can be nested it is passed into its own method, `structure-properties`.

Properties has a type field, which is always set to "object" (only objects can have properties).

Properties contains a kv-pair for each object member. It also contains certain optional/required kv-pairs like :required and :type.

There's no clear way to distinguish between which keys are object members and which are swagger metadata. Presumably swagger distinguishes them based on a list of reserved keywords.

We'll have to keep a set of reserved keywords that we either ignore or handle on a case basis.

In structure-properties we might do a reduce-kv over the non-reserved kv-pairs.

Each key is 

How exactly are we going to incorporate the request values into this?

The reason we're doing all this stuff is to figure out what parameters are what type (plus for documentation at a later stage).

The easiest thing to do would be to only check the top-level object/primitive, then naively return anything inside.

It would be a lot better if we checked the body members as well. In order to do this, we have to traverse both the request map and the body param at the same time.

# Some time later...
After letting this gestate for a bit I realized I was overcomplicating it. For now I'm just gonna do verification of the body (all body fields specified in swagger are recursively validated against the request map parameters).
I still need to disallow incorrect user parameters. I also need to add individual handling of all the swagger meta-keys (allOf, additionalProperties etc). That's probably going to be a big task.

Before all that however, I have to figure out what I want to do next. The request maps are (mostly) being constructed correctly now, so documentation and authentication are the two big things left.
