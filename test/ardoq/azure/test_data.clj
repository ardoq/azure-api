(ns ardoq.azure.test-data)

(def swagger-unresolved
{
  :parameters {
    :ApiVersionParameter {
      :description "The API version to use for this operation.",
      :in "query",
      :name "api-version",
      :required true,
      :type "string"
    },
  },
  :paths {
    "/providers/Microsoft.Authorization/elevateAccess" {
      :post {
        :description "Elevates access for a Global Administrator.",
        :operationId "ElevateAccess_Post",
        :parameters [
          {
            :$ref "#/parameters/ApiVersionParameter"
          }
        ],
        :responses {
          :200 {
            :description "OK - Returns an HttpResponseMessage with HttpStatusCode 200."
          }
        },
      }
    },
  }
})

(def swagger-resolved
{
  :parameters {
    :ApiVersionParameter {
      :description "The API version to use for this operation.",
      :in "query",
      :name "api-version",
      :required true,
      :type "string"
    },
  },
  :paths {
    "/providers/Microsoft.Authorization/elevateAccess" {
      :post {
        :description "Elevates access for a Global Administrator.",
        :operationId "ElevateAccess_Post",
        :parameters [
          {
            :description "The API version to use for this operation.",
            :in "query",
            :name "api-version",
            :required true,
            :type "string"
          },
        ],
        :responses {
          :200 {
            :description "OK - Returns an HttpResponseMessage with HttpStatusCode 200."
          }
        },
      }
    },
  }
})




(def paths-unconverted
  {"some/path/like/this" {:get
                          {:description "A description" ,
                           :operationId "GetSomething",
                           :parameters [
                                        {:description "Whatever",
                                         :in "query",
                                         :name "api-version",
                                         :required true,
                                         :type "string"
                                         }
                                        ]
                           }
                          :post
                          {:description "Another description" ,
                           :operationId "PostSomething",
                           :parameters [
                                        {:description "Who cares",
                                         :in "body",
                                         :name "post-message",
                                         :required true,
                                         :type "string"
                                         }
                                        ]
                           }
                          }
   "another/identical/path" {:get
                             {:description "A description" ,
                              :operationId "GetSomethingElse",
                              :parameters [
                                           {:description "Whatever",
                                            :in "query",
                                            :name "api-version",
                                            :required true,
                                            :type "string"
                                            }
                                           ]
                              }
                             :post
                             {:description "Another description" ,
                              :operationId "PostSomethingElse",
                              :parameters [
                                           {:description "Who cares",
                                            :in "body",
                                            :name "post-message",
                                            :required true,
                                            :type "string"
                                            }
                                           ]
                              }
                             }
   })


(def paths-converted
  {:GetSomething {:description "A description"
                   :path "some/path/like/this"
                   :parameters [
                                {:description "Whatever"
                                 :in "query"
                                 :name "api-version"
                                 :required true
                                 :type "string"
                                 }
                                ]
                   }
    :PostSomething {:description "Another description" 
                    :path "some/path/like/this"
                    :parameters [
                                 {:description "Who cares"
                                  :in "body"
                                  :name "post-message"
                                  :required true
                                  :type "string"
                                  }
                                 ]
                    }

    :GetSomethingElse { :description "A description"
                       :path "another/identical/path"
                       :parameters [
                                    {:description "Whatever"
                                     :in "query"
                                     :name "api-version"
                                     :required true
                                     :type "string"
                                     }
                                    ]
                       }
    :PostSomethingElse {:description "Another description"
                        :path "another/identical/path"
                        :parameters [
                                     {:description "Who cares"
                                      :in "body"
                                      :name "post-message"
                                      :required true
                                      :type "string"
                                      }
                                     ]
                        }
    })



