# AWS-api

Cognitect has created a library called aws-api. This is a replacement for aws-sdk-java, an aws-built SDK which lets developers interact with the APIs of the various aws services.

The library uses *Service Descriptions*. They're usually in json-format. Ex. An s3 service description provides s3 metadata, but also api endpoint metadata. Cognitect's aws-api uses these service description files to generate clojure code which allows programmatic access to the APIs through clojure.

Three parts:

* aws.api - Manually written clojure code which provides access to the api. Consists of two functions: client (creates an AWS connection) and invoke (Runs a command via a map).
* endpoints - Generated file which details all the endpoints
* service - Generated file, one for each service. User depends only on those they need.


# Azure-api

The idea is to build the same thing, but for Azure cloud instead. Where would I even start?

[This talk was a good starting point.](https://www.youtube.com/watch?v=ppDtDP0Rntw)

![Image from talk.](awsapi.png)

Azure apparently have their own [service descriptions](https://github.com/Azure/azure-rest-api-specs). Figuring out their structure might be a good starting point.

Some guy has made the same thing but for [Google cloud platform](https://github.com/ComputeSoftware/gcp-api). Seems quite quite short (in terms of LOC). Figuring out how he does it is a good idea.

Of course, I also need some general domain knowledge about Azure and AWS.

Credentials: This part is undoubtedly different for Azure. It may (probably) use Oauth2 as well, but it's something I have to read up on.
Auth uses Azure Active Directory (AD).

# gcp api

In gcp-api, APIs are represented as "descriptor" files, an edn format. To generate/update these, all apis are retrieved from a website which hosts all the gcp endpoints and their swagger.json files. They're then converted using a swagger->descriptor function (along with some other logic).

I could potentially just write a swagger.json scraper for the azure rest apis and pass it into the gcp-api converter. It seems like a decent starting point, at least.

Hopefully the rest of the functionality is quite similar to aws-api.

It seems gcp-api fetches it definitions from [googleapis](https://github.com/googleapis/googleapis), which exposes all of google's apis, not just the cloud part. Is that intended behavior?

[Assum posed a question on clojurians](https://clojurians-log.clojureverse.org/aws/2020-02-04), asking if the code that transform aws-specs into clojure.specs and the aws-api stuff is proprietary. It is. But the gcp dude came in to the thread and shilled his repo, saying that the basic idea is very simple. They only convert from swagger->descriptor to minimize the size of the final artifact. He was thinking of drawing the swagger part out into a separate library.

# Loose thoughts

There was something about how they also generate clojure.specs (from the service descriptions?)

Both GCP and Azure's service descriptions seem to be written as swagger docs!

The name of a "service" equivalent in azure seems to be [resource provider](https://docs.microsoft.com/en-us/azure/azure-resource-manager/management/resource-providers-and-types). Not really though? I'm unsure what an RP is exactly. Apparently it's classified as a service...
Example RPs:
* compute (a core azure service)
* logic
* eventhub
* frontdoor
* sql

So RP definitely seems to be just a fancy word for service



# Azure stuff

Azure Resource Manager enables you to deploy and manage the infrastructure for your Azure solutions. You organize related resources in resource groups, and deploy your resources with JSON templates.

[A Resource manager is a resource provider...](https://docs.microsoft.com/en-us/azure/role-based-access-control/resource-provider-operations)
[All Azure requests (SDK or API) go thru the Resource manager.](https://docs.microsoft.com/en-us/azure/azure-resource-manager/management/overview) This site contains terminology definitions!!


[Here's the API docs for Azure. Not ideal, would be better if it was in json format like for google apis.](https://docs.microsoft.com/en-us/rest/api/?view=Azure)
[Some reddit user whose been developing on the Azure plain API says there are some "inconsistencies" in the different RP apis.](https://www.reddit.com/r/AZURE/comments/a9mzp8/two_years_after_developing_purely_on_azure_rest/)

The azure rest api repo makes a lot more sense now...


# Recap

I think I know enough basic stuff now to be able to start approaching the problem. Here are some tasks I need to do:

* Scrape and organize azure swagger-files
* (Potentially) convert into descriptor files (Using gcp-api converter?)
* Figure out how these files are actually used to make api-calls
    - Kind of a big one
* Azure-specific auth


## Scraping

https://api.apis.guru/v2/list.json is like wikipedia for web-apis. It seems to list all the azure.com APIs, with a link to their swagger.json file. Useful for scraping. gcp-api uses this


It might be a good idea to just fork gcp-api and try to fit it to work with azure instead.

