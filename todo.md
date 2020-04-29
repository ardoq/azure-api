### Todo

* allOf: All the mentioned schema specs should be validated against the given schema body
* oneOf: Only one (XOR) of the mentioned schema specs should be validated
* anyOf: One (OR) of the mentioned schema specs

allOf is used quite frequently in azure swaggerfiles, so that should be prio 1. tbh I'm not sure if oneOf and anyOf are even supported in Swagger 2.0.

* Parameter data type validation: Currently only checking name and object structure, i.e. incorrectly passing a string instead of an integer will not yield an error
  - Could be extended to include the entire swagger spec for data types, including stuff like min-max ranges, enums, discriminators, (not sure what's 2.0 and what's 3.0)

* [additionalProperties](https://stackoverflow.com/questions/41239913/why-additionalproperties-is-the-way-to-represent-dictionary-map-in-swagger-ope): Used for specifying object kv-pairs that are not known in advance. If f.ex. we want to allow an arbitrary amount of string kv-pairs with arbitrary key names, we would specify that in additionalProperties

* Response docs: Some of the swaggerfiles include information about the structure of the response. This is useful for exploration (using `ops` and `doc`)

* Microsoft-specific keys: Azure swaggerfiles use a couple of keys that are outside the swagger specification (stuff like x-ms-visibility, x-ms-examples, x-ms-pageable). I'm not sure how significant they are

* Https/http: Currently always using https, I don't believe any of the azure swagger-specs allow for http

* Relative paths: Some parameter/definition references move up a directory. The site I use to retrieve the swaggerfiles does not contain the entire directory structure, so I can't do this. I'll probably have to use the [azure github repo](https://github.com/Azure/azure-rest-api-specs) for this.
