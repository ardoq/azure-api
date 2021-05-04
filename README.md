
[![Clojars Project](https://img.shields.io/clojars/v/com.ardoq/azure-api.svg)](https://clojars.org/com.ardoq/azure-api)

# Usage

Azure-api allows for simple usage of the Azure REST APIs in Clojure.
The functionality mimicks that of [aws-api](https://github.com/cognitect-labs/aws-api). 

## Creating a client

In order to create a client, you need three things:
* Client keyword name and api-version
* Azure Subscription id
* Authentication Token (See [Authenticating](#authenticating))

Clients are located in the `resources` folder. The keyword for a client is simply the name of the folder as a keyword.

If a specific api version is to be used, the api-version can also be specified as a string. Otherwise, the latest api version is used.

In other words, use one of these:
``` clojure
;; Use latest api-version
(def res-client (client :resources sub-id token))

;; Use specified api-version
(def res-client (client :resources "2016-07-01" sub-id token))
``` 

## Invoking a client

Operations are executed on a client with `(invoke client operation-map)`

The operation map has two keys:
* `:op,` the operation to be invoked
* `:request`, a request map containing the operation parameters


Operations are given as name-value kv-pairs; object parameters are nested maps. Where a parameter is located (path, query, body) is irrelevant to the operation map.

NB: `subscriptionId` and `api-version` are retrieved automatically from the client, so there is no need to provide them.

## Documentation

Available operations for a client can be found with `(ops client)`.

Information about an operation, including parameters, can be found with `(docs client :op-keyword)`.

Execute `lein run` to download fresh api specs from [Azure](https://api.apis.guru/v2/list.json).

# Authenticating

Azure-api authenticates via an Azure app. A guide for how to set up and grant permissions to an app can be found [here.](https://blog.tekspace.io/accessing-azure-rest-api/)

Three values are required:
* Directory (tenant) id
* Application (client) id
* Client secret

Create a self-refreshing auth-token with `(auth tenant-id client-id client-secret)`.

# Example

```clojure
(def token (auth "6b5150ae-853c-11ea-bc55-0242ac130003"
                 "a0cefbf2-dcd3-4632-aee6-251fda2034cd"
                 "32b804e7-a6a1-4dd7-882f-afe3f36d9c19"))

(def res-client (client :resources "51ff24d7-63d5-4819-8658-9510de9ac061" token))

(invoke res-client {:op :Resources_Get
                    :request {:resourceGroupName "Test group name"
                              :resourceProviderNamespace "Test namespcae"
                              :parentResourcePath "Test path"
                              :resourceType "Test type"
                              :resourceName "Test resource"}})
```

