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

