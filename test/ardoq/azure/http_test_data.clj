(ns ardoq.azure.http-test-data
  (:require
    [ardoq.azure.api :as api]
    ))

(def path-and-query
  [{:description "Something",
    :in "query",
    :name "kind",
    :required true,
    :type "string"},

   {:description "Hmm",
    :in "query",
    :name "callous",
    :required true,
    :type "string"}

   {:description "Something else",
    :in "path",
    :name "mean",
    :required false,
    :type "string"}
   ])

(def path-and-refs
  [
   {:description "Something else",
    :in "path",
    :name "mean",
    :required false,
    :type "string"}
   {:reference/parameters "IdParameter"}
   {:reference/parameters "ApiVersionParameter"}
   ])

(def resolved-path-and-refs
  [
   {:description "Something else",
    :in "path",
    :name "mean",
    :required false,
    :type "string"}
   {:description "The client API version.",
    :in "query",
    :name "api-version",
    :required true,
    :type "string"},
   {:description "The Azure subscription ID.",
    :in "path",
    :name "subscriptionId",
    :required true,
    :type "string"}])

(def parameters
  {:ApiVersionParameter {:description "The client API version.",
                         :in "query",
                         :name "api-version",
                         :required true,
                         :type "string"},
   :IdParameter {:description "The Azure subscription ID.",
                 :in "path",
                 :name "subscriptionId",
                 :required true,
                 :type "string"}})

(def definition-refs
  [
   {:description "Something else",
    :in "body",
    :name "rudeboy",
    :required true,
    :type "object"
    :schema {:reference/definitions "RudeBoy"}}
   ]
  )

(def resolved-definition-refs
  [
   {:description "Something else",
    :in "body",
    :name "rudeBoy",
    :required true,
    :type "object"
    :schema {:properties {:badMan {:type "string"}
                          :address {:type "string"}}
             :required ["badMan" "address"] ,
             :type "object"}}
   ]
  )

(def definitions
  {:RudeBoy {:properties {:badMan {:references/definitions "BadMan"}
                          :address {:type "string"}}
             :required ["badMan" "address"] ,
             :type "object"},
   :BadMan {:type "string"}
   }
  )

(def PeeringManagementClient 
  {:scheme "https",
   :host "management.azure.com",
   :info {:description "APIs to manage Peering resources through the Azure Resource Manager.",
          :title "PeeringManagementClient",
          :version "2019-08-01-preview"},
   :ops {:PeeringLocations_List {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/peeringLocations",
                                 :description "Lists all of the available peering locations for the specified kind of peering.",
                                 :parameters [{:description "The kind of the peering.",
                                               :enum ["Direct"
                                                      "Exchange"],
                                               :in "query",
                                               :name "kind",
                                               :required true,
                                               :type "string"}
                                              {:description "The type of direct peering.",
                                               :enum ["Edge"
                                                      "Transit"
                                                      "Cdn"
                                                      "Internal"],
                                               :in "query",
                                               :name "directPeeringType",
                                               :required false,
                                               :type "string"}
                                              {:reference/parameters "SubscriptionIdParameter"}
                                              {:reference/parameters "ApiVersionParameter"}]},
         :PeerAsns_ListBySubscription {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/peerAsns",
                                       :description "Lists all of the peer ASNs under the given subscription.",
                                       :parameters [{:reference/parameters "SubscriptionIdParameter"}
                                                    {:reference/parameters "ApiVersionParameter"}]},
         :PeerAsns_Get {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/peerAsns/{peerAsnName}",
                        :description "Gets the peer ASN with the specified name under the given subscription.",
                        :parameters [{:description "The peer ASN name.",
                                      :in "path",
                                      :name "peerAsnName",
                                      :required true,
                                      :type "string"}
                                     {:reference/parameters "SubscriptionIdParameter"}
                                     {:reference/parameters "ApiVersionParameter"}]},
         :PeeringServices_ListByResourceGroup {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peeringServices",
                                               :description "Lists all of the peering services under the given subscription and resource group.",
                                               :parameters [{:description "The name of the resource group.",
                                                             :in "path",
                                                             :name "resourceGroupName",
                                                             :required true,
                                                             :type "string"}
                                                            {:reference/parameters "SubscriptionIdParameter"}
                                                            {:reference/parameters "ApiVersionParameter"}]},
         :PeeringServiceLocations_List {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/peeringServiceLocations",
                                        :description "Lists all of the available peering service locations for the specified kind of peering.",
                                        :parameters [{:reference/parameters "SubscriptionIdParameter"}
                                                     {:reference/parameters "ApiVersionParameter"}]},
         :PeeringServices_CreateOrUpdate {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peeringServices/{peeringServiceName}",
                                          :description "Creates a new peering service or updates an existing peering with the specified name under the given subscription and resource group.",
                                          :parameters [{:description "The name of the resource group.",
                                                        :in "path",
                                                        :name "resourceGroupName",
                                                        :required true,
                                                        :type "string"}
                                                       {:description "The name of the peering service.",
                                                        :in "path",
                                                        :name "peeringServiceName",
                                                        :required true,
                                                        :type "string"}
                                                       {:description "The properties needed to create or update a peering service.",
                                                        :in "body",
                                                        :name "peeringService",
                                                        :required true,
                                                        :schema {:reference/definitions "PeeringService"}}
                                                       {:reference/parameters "SubscriptionIdParameter"}
                                                       {:reference/parameters "ApiVersionParameter"}]},
         :Peerings_Delete {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peerings/{peeringName}",
                           :description "Deletes an existing peering with the specified name under the given subscription and resource group.",
                           :parameters [{:description "The name of the resource group.",
                                         :in "path",
                                         :name "resourceGroupName",
                                         :required true,
                                         :type "string"}
                                        {:description "The name of the peering.",
                                         :in "path",
                                         :name "peeringName",
                                         :required true,
                                         :type "string"}
                                        {:reference/parameters "SubscriptionIdParameter"}
                                        {:reference/parameters "ApiVersionParameter"}]},
         :PeeringServices_Delete {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peeringServices/{peeringServiceName}",
                                  :description "Deletes an existing peering service with the specified name under the given subscription and resource group.",
                                  :parameters [{:description "The name of the resource group.",
                                                :in "path",
                                                :name "resourceGroupName",
                                                :required true,
                                                :type "string"}
                                               {:description "The name of the peering service.",
                                                :in "path",
                                                :name "peeringServiceName",
                                                :required true,
                                                :type "string"}
                                               {:reference/parameters "SubscriptionIdParameter"}
                                               {:reference/parameters "ApiVersionParameter"}]},
         :PeeringServices_ListBySubscription {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/peeringServices",
                                              :description "Lists all of the peerings under the given subscription.",
                                              :parameters [{:reference/parameters "SubscriptionIdParameter"}
                                                           {:reference/parameters "ApiVersionParameter"}]},
         :PeerAsns_Delete {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/peerAsns/{peerAsnName}",
                           :description "Deletes an existing peer ASN with the specified name under the given subscription.",
                           :parameters [{:description "The peer ASN name.",
                                         :in "path",
                                         :name "peerAsnName",
                                         :required true,
                                         :type "string"}
                                        {:reference/parameters "SubscriptionIdParameter"}
                                        {:reference/parameters "ApiVersionParameter"}]},
:CheckServiceProviderAvailability {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/CheckServiceProviderAvailability",
                                   :description "Checks if the peering service provider is present within 1000 miles of customer's location",
                                   :parameters [{:description "The CheckServiceProviderAvailabilityInput\r\n            indicating customer location and service provider.",
                                                 :in "body",
                                                 :name "checkServiceProviderAvailabilityInput",
                                                 :required true,
                                                 :schema {:reference/definitions "CheckServiceProviderAvailabilityInput"}}
                                                {:reference/parameters "SubscriptionIdParameter"}
                                                {:reference/parameters "ApiVersionParameter"}]},
:Peerings_Update {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peerings/{peeringName}",
                  :description "Updates tags for a peering with the specified name under the given subscription and resource group.",
                  :parameters [{:description "The name of the resource group.",
                                :in "path",
                                :name "resourceGroupName",
                                :required true,
                                :type "string"}
                               {:description "The name of the peering.",
                                :in "path",
                                :name "peeringName",
                                :required true,
                                :type "string"}
                               {:description "The resource tags.",
                                :in "body",
                                :name "tags",
                                :required true,
                                :schema {:reference/definitions "ResourceTags"}}
                               {:reference/parameters "SubscriptionIdParameter"}
                               {:reference/parameters "ApiVersionParameter"}]},
:PeeringServicePrefixes_Get {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peeringServices/{peeringServiceName}/prefixes/{prefixName}",
                             :description "Gets the peering service prefix.",
                             :parameters [{:description "The resource group name.",
                                           :in "path",
                                           :name "resourceGroupName",
                                           :required true,
                                           :type "string"}
                                          {:description "The peering service name.",
                                           :in "path",
                                           :name "peeringServiceName",
                                           :required true,
                                           :type "string"}
                                          {:description "The prefix name.",
                                           :in "path",
                                           :name "prefixName",
                                           :required true,
                                           :type "string"}
                                          {:reference/parameters "SubscriptionIdParameter"}
                                          {:reference/parameters "ApiVersionParameter"}]},
:Peerings_ListBySubscription {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/peerings",
                              :description "Lists all of the peerings under the given subscription.",
                              :parameters [{:reference/parameters "SubscriptionIdParameter"}
                                           {:reference/parameters "ApiVersionParameter"}]},
:Prefixes_ListByPeeringService {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peeringServices/{peeringServiceName}/prefixes",
                                :description "Lists the peerings prefix in the resource group.",
                                :parameters [{:description "The resource group name.",
                                              :in "path",
                                              :name "resourceGroupName",
                                              :required true,
                                              :type "string"}
                                             {:description "The peering service name.",
                                              :in "path",
                                              :name "peeringServiceName",
                                              :required true,
                                              :type "string"}
                                             {:reference/parameters "SubscriptionIdParameter"}
                                             {:reference/parameters "ApiVersionParameter"}]},
:PeeringServicePrefixes_CreateOrUpdate {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peeringServices/{peeringServiceName}/prefixes/{prefixName}",
                                        :description "Creates or updates the peering prefix.",
                                        :parameters [{:description "The resource group name.",
                                                      :in "path",
                                                      :name "resourceGroupName",
                                                      :required true,
                                                      :type "string"}
                                                     {:description "The peering service name.",
                                                      :in "path",
                                                      :name "peeringServiceName",
                                                      :required true,
                                                      :type "string"}
                                                     {:description "The prefix name",
                                                      :in "path",
                                                      :name "prefixName",
                                                      :required true,
                                                      :type "string"}
                                                     {:description "The IP prefix for an peering",
                                                      :in "body",
                                                      :name "peeringServicePrefix",
                                                      :required true,
                                                      :schema {:reference/definitions "PeeringServicePrefix"}}
                                                     {:reference/parameters "SubscriptionIdParameter"}
                                                     {:reference/parameters "ApiVersionParameter"}]},
:LegacyPeerings_List {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/legacyPeerings",
                      :description "Lists all of the legacy peerings under the given subscription matching the specified kind and location.",
                      :parameters [{:description "The location of the peering.",
                                    :in "query",
                                    :name "peeringLocation",
                                    :required true,
                                    :type "string"}
                                   {:description "The kind of the peering.",
                                    :enum ["Direct" "Exchange"],
                                    :in "query",
                                    :name "kind",
                                    :required true,
                                    :type "string"}
                                   {:reference/parameters "SubscriptionIdParameter"}
                                   {:reference/parameters "ApiVersionParameter"}]},
:Peerings_Get {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peerings/{peeringName}",
               :description "Gets an existing peering with the specified name under the given subscription and resource group.",
               :parameters [{:description "The name of the resource group.",
                             :in "path",
                             :name "resourceGroupName",
                             :required true,
                             :type "string"}
                            {:description "The name of the peering.",
                             :in "path",
                             :name "peeringName",
                             :required true,
                             :type "string"}
                            {:reference/parameters "SubscriptionIdParameter"}
                            {:reference/parameters "ApiVersionParameter"}]},
:PeeringServices_Update {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peeringServices/{peeringServiceName}",
                         :description "Updates tags for a peering service with the specified name under the given subscription and resource group.",
                         :parameters [{:description "The name of the resource group.",
                                       :in "path",
                                       :name "resourceGroupName",
                                       :required true,
                                       :type "string"}
                                      {:description "The name of the peering service.",
                                       :in "path",
                                       :name "peeringServiceName",
                                       :required true,
                                       :type "string"}
                                      {:description "The resource tags.",
                                       :in "body",
                                       :name "tags",
                                       :required true,
                                       :schema {:reference/definitions "ResourceTags"}}
                                      {:reference/parameters "SubscriptionIdParameter"}
                                      {:reference/parameters "ApiVersionParameter"}]},
:PeeringServicePrefixes_Delete {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peeringServices/{peeringServiceName}/prefixes/{prefixName}",
                                :description "removes the peering prefix.",
                                :parameters [{:description "The resource group name.",
                                              :in "path",
                                              :name "resourceGroupName",
                                              :required true,
                                              :type "string"}
                                             {:description "The peering service name.",
                                              :in "path",
                                              :name "peeringServiceName",
                                              :required true,
                                              :type "string"}
                                             {:description "The prefix name",
                                              :in "path",
                                              :name "prefixName",
                                              :required true,
                                              :type "string"}
                                             {:reference/parameters "SubscriptionIdParameter"}
                                             {:reference/parameters "ApiVersionParameter"}]},
:PeerAsns_CreateOrUpdate {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/peerAsns/{peerAsnName}",
                          :description "Creates a new peer ASN or updates an existing peer ASN with the specified name under the given subscription.",
                          :parameters [{:description "The peer ASN name.",
                                        :in "path",
                                        :name "peerAsnName",
                                        :required true,
                                        :type "string"}
                                       {:description "The peer ASN.",
                                        :in "body",
                                        :name "peerAsn",
                                        :required true,
                                        :schema {:reference/definitions "PeerAsn"}}
                                       {:reference/parameters "SubscriptionIdParameter"}
                                       {:reference/parameters "ApiVersionParameter"}]},
:Peerings_ListByResourceGroup {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peerings",
                               :description "Lists all of the peerings under the given subscription and resource group.",
                               :parameters [{:description "The name of the resource group.",
                                             :in "path",
                                             :name "resourceGroupName",
                                             :required true,
                                             :type "string"}
                                            {:reference/parameters "SubscriptionIdParameter"}
                                            {:reference/parameters "ApiVersionParameter"}]},
:PeeringServices_Get {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peeringServices/{peeringServiceName}",
                      :description "Gets an existing peering service with the specified name under the given subscription and resource group.",
                      :parameters [{:description "The name of the resource group.",
                                    :in "path",
                                    :name "resourceGroupName",
                                    :required true,
                                    :type "string"}
                                   {:description "The name of the peering.",
                                    :in "path",
                                    :name "peeringServiceName",
                                    :required true,
                                    :type "string"}
                                   {:reference/parameters "SubscriptionIdParameter"}
                                   {:reference/parameters "ApiVersionParameter"}]},
:PeeringServiceProviders_List {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/peeringServiceProviders",
                               :description "Lists all of the available peering service locations for the specified kind of peering.",
                               :parameters [{:reference/parameters "SubscriptionIdParameter"}
                                            {:reference/parameters "ApiVersionParameter"}]},
:Operations_List {:path "/providers/Microsoft.Peering/operations",
                  :description "Lists all of the available API operations for peering resources.",
                  :parameters [{:reference/parameters "ApiVersionParameter"}]},
:Peerings_CreateOrUpdate {:path "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Peering/peerings/{peeringName}",
                          :description "Creates a new peering or updates an existing peering with the specified name under the given subscription and resource group.",
                          :parameters [{:description "The name of the resource group.",
                                        :in "path",
                                        :name "resourceGroupName",
                                        :required true,
                                        :type "string"}
                                       {:description "The name of the peering.",
                                        :in "path",
                                        :name "peeringName",
                                        :required true,
                                        :type "string"}
                                       {:description "The properties needed to create or update a peering.",
                                        :in "body",
                                        :name "peering",
                                        :required true,
                                        :schema {:reference/definitions "Peering"}}
                                       {:reference/parameters "SubscriptionIdParameter"}
                                       {:reference/parameters "ApiVersionParameter"}]}},
:parameters {:ApiVersionParameter {:description "The client API version.",
                                   :in "query",
                                   :name "api-version",
                                   :required true,
                                   :type "string"},
             :SubscriptionIdParameter {:description "The Azure subscription ID.",
                                       :in "path",
                                       :name "subscriptionId",
                                       :required true,
                                       :type "string"}},
:definitions {:PeeringServiceProviderProperties {:description "The properties that define connectivity to the Peering Service Provider.",
                                                 :properties {:serviceProviderName {:description "The name of the service provider.",
                                                                                    :type "string"}},
                                                 :type "object"},
              :PeeringPropertiesExchange {:description "The properties that define an exchange peering.",
                                          :properties {:connections {:description "The set of connections that constitute an exchange peering.",
                                                                     :items {:reference/definitions "ExchangeConnection"},
                                                                     :type "array"},
                                                       :peerAsn {:reference/definitions "SubResource"}},
                                          :type "object"},
              :PeeringListResult {:description "The paginated list of peerings.",
                                  :properties {:nextLink {:description "The link to fetch the next page of peerings.",
                                                          :type "string"},
                                               :value {:description "The list of peerings.",
                                                       :items {:reference/definitions "Peering"},
                                                       :type "array"}},
                                  :type "object"},
              :PeeringSku {:description "The SKU that defines the tier and kind of the peering.",
                           :properties {:family {:description "The family of the peering SKU.",
                                                 :enum ["Direct"
                                                        "Exchange"],
                                                 :type "string",
                                                 :x-ms-enum {:modelAsString true,
                                                             :name "family"}},
                                        :name {:description "The name of the peering SKU.",
                                               :enum ["Basic_Exchange_Free"
                                                      "Basic_Direct_Free"
                                                      "Premium_Direct_Free"
                                                      "Premium_Exchange_Metered"
                                                      "Premium_Direct_Metered"
                                                      "Premium_Direct_Unlimited"],
                                               :type "string",
                                               :x-ms-enum {:modelAsString true,
                                                           :name "name"}},
                                        :size {:description "The size of the peering SKU.",
                                               :enum ["Free"
                                                      "Metered"
                                                      "Unlimited"],
                                               :type "string",
                                               :x-ms-enum {:modelAsString true,
                                                           :name "size"}},
                                        :tier {:description "The tier of the peering SKU.",
                                               :enum ["Basic"
                                                      "Premium"],
                                               :type "string",
                                               :x-ms-enum {:modelAsString true,
                                                           :name "tier"}}},
                           :type "object"},
              :OperationListResult {:description "The paginated list of peering API operations.",
                                    :properties {:nextLink {:description "The link to fetch the next page of peering API operations.",
                                                            :type "string"},
                                                 :value {:description "The list of peering API operations.",
                                                         :items {:reference/definitions "Operation"},
                                                         :type "array"}},
                                    :type "object"},
              :ContactInfo {:description "The contact information of the peer.",
                            :properties {:emails {:description "The list of email addresses.",
                                                  :items {:type "string"},
                                                  :type "array"},
                                         :phone {:description "The list of contact numbers.",
                                                 :items {:type "string"},
                                                 :type "array"}},
                            :type "object"},
              :Operation {:description "The peering API operation.",
                          :properties {:display {:reference/definitions "OperationDisplayInfo"},
                                       :isDataAction {:description "The flag that indicates whether the operation applies to data plane.",
                                                      :readOnly true,
                                                      :type "boolean"},
                                       :name {:description "The name of the operation.",
                                              :readOnly true,
                                              :type "string"}},
                          :type "object"},
              :PeeringProperties {:description "The properties that define connectivity to the Microsoft Cloud Edge.",
                                  :properties {:direct {:reference/definitions "PeeringPropertiesDirect"},
                                               :exchange {:reference/definitions "PeeringPropertiesExchange"},
                                               :peeringLocation {:description "The location of the peering.",
                                                                 :type "string"},
                                               :provisioningState {:description "The provisioning state of the resource.",
                                                                   :enum ["Succeeded"
                                                                          "Updating"
                                                                          "Deleting"
                                                                          "Failed"],
                                                                   :readOnly true,
                                                                   :type "string",
                                                                   :x-ms-enum {:modelAsString true,
                                                                               :name "provisioningState"}}},
                                  :type "object"},
              :PeeringService {:allOf [{:reference/definitions "Resource"}],
                               :description "Peering Service",
                               :properties {:location {:description "The location of the resource.",
                                                       :type "string",
                                                       :x-ms-mutability ["read"
                                                                         "create"]},
                                            :properties {:reference/definitions "PeeringServiceProperties"},
                                            :tags {:additionalProperties {:type "string"},
                                                   :description "The resource tags.",
                                                   :type "object"}},
                               :required ["location"],
                               :type "object"},
              :Peering {:allOf [{:reference/definitions "Resource"}],
                        :description "Peering is a logical representation of a set of connections to the Microsoft Cloud Edge at a location.",
                        :properties {:kind {:description "The kind of the peering.",
                                            :enum ["Direct"
                                                   "Exchange"],
                                            :type "string",
                                            :x-ms-enum {:modelAsString true,
                                                        :name "kind"}},
                                     :location {:description "The location of the resource.",
                                                :type "string",
                                                :x-ms-mutability ["read"
                                                                  "create"]},
                                     :properties {:reference/definitions "PeeringProperties"},
                                     :sku {:reference/definitions "PeeringSku"},
                                     :tags {:additionalProperties {:type "string"},
                                            :description "The resource tags.",
                                            :type "object"}},
                        :required ["sku" "kind" "location"],
                        :type "object"},
:PeeringServiceLocation {:allOf [{:reference/definitions "Resource"}],
                         :description "PeeringService location",
                         :properties {:properties {:reference/definitions "PeeringServiceLocationProperties"}},
                         :type "object"},
:PeeringLocationPropertiesExchange {:description "The properties that define an exchange peering location.",
                                    :properties {:peeringFacilities {:description "The list of exchange peering facilities at the peering location.",
                                                                     :items {:reference/definitions "ExchangePeeringFacility"},
                                                                     :type "array"}},
                                    :type "object"},
:PeeringLocation {:allOf [{:reference/definitions "Resource"}],
                  :description "Peering location is where connectivity could be established to the Microsoft Cloud Edge.",
                  :properties {:kind {:description "The kind of peering that the peering location supports.",
                                      :enum ["Direct"
                                             "Exchange"],
                                      :type "string",
                                      :x-ms-enum {:modelAsString true,
                                                  :name "kind"}},
                               :properties {:reference/definitions "PeeringLocationProperties"}},
                  :type "object"},
:PeeringServiceLocationProperties {:description "The properties that define connectivity to the Peering Service Location.",
                                   :properties {:azureRegion {:description "Azure region for the location",
                                                              :type "string"},
                                                :country {:description "Country of the customer",
                                                          :type "string"},
                                                :state {:description "State of the customer",
                                                        :type "string"}},
                                   :type "object"},
:PeeringLocationProperties {:description "The properties that define a peering location.",
                            :properties {:azureRegion {:description "The Azure region associated with the peering location.",
                                                       :type "string"},
                                         :country {:description "The country in which the peering location exists.",
                                                   :type "string"},
                                         :direct {:reference/definitions "PeeringLocationPropertiesDirect"},
                                         :exchange {:reference/definitions "PeeringLocationPropertiesExchange"},
                                         :peeringLocation {:description "The name of the peering location.",
                                                           :type "string"}},
                            :type "object"},
:PeeringServiceProperties {:description "The properties that define connectivity to the Peering Service.",
                           :properties {:peeringServiceLocation {:description "The PeeringServiceLocation of the Customer.",
                                                                 :type "string"},
                                        :peeringServiceProvider {:description "The MAPS Provider Name.",
                                                                 :type "string"},
                                        :provisioningState {:description "The provisioning state of the resource.",
                                                            :enum ["Succeeded"
                                                                   "Updating"
                                                                   "Deleting"
                                                                   "Failed"],
                                                            :readOnly true,
                                                            :type "string",
                                                            :x-ms-enum {:modelAsString true,
                                                                        :name "provisioningState"}}},
                           :type "object"},
:SubResource {:description "The sub resource.",
              :properties {:id {:description "The identifier of the referenced resource.",
                                :type "string"}},
              :type "object"},
:PeeringServicePrefix {:allOf [{:reference/definitions "Resource"}],
                       :description "The peering service prefix class.",
                       :properties {:properties {:reference/definitions "PeeringServicePrefixProperties"}},
                       :type "object"},
:PeeringServicePrefixListResult {:description "The paginated list of [T].",
                                 :properties {:nextLink {:description "The link to fetch the next page of [T].",
                                                         :type "string"},
                                              :value {:description "The list of [T].",
                                                      :items {:reference/definitions "PeeringServicePrefix"},
                                                      :type "array"}},
                                 :type "object"},
:ExchangePeeringFacility {:description "The properties that define an exchange peering facility.",
                          :properties {:bandwidthInMbps {:description "The bandwidth of the connection between Microsoft and the exchange peering facility.",
                                                         :format "int32",
                                                         :type "integer"},
                                       :exchangeName {:description "The name of the exchange peering facility.",
                                                      :type "string"},
                                       :facilityIPv4Prefix {:description "The IPv4 prefixes associated with the exchange peering facility.",
                                                            :type "string"},
                                       :facilityIPv6Prefix {:description "The IPv6 prefixes associated with the exchange peering facility.",
                                                            :type "string"},
                                       :microsoftIPv4Address {:description "The IPv4 address of Microsoft at the exchange peering facility.",
                                                              :type "string"},
                                       :microsoftIPv6Address {:description "The IPv6 address of Microsoft at the exchange peering facility.",
                                                              :type "string"},
                                       :peeringDBFacilityId {:description "The PeeringDB.com ID of the facility.",
                                                             :format "int32",
                                                             :type "integer"},
                                       :peeringDBFacilityLink {:description "The PeeringDB.com URL of the facility.",
                                                               :type "string"}},
                          :type "object"},
:CheckServiceProviderAvailabilityInput {:description "Class for CheckServiceProviderAvailabilityInput",
                                        :properties {:peeringServiceLocation {:description "Gets or sets the PeeringServiceLocation",
                                                                              :type "string"},
                                                     :peeringServiceProvider {:description "Gets or sets the PeeringServiceProvider",
                                                                              :type "string"}},
                                        :type "object"},
:PeerAsnProperties {:description "The properties that define a peer's ASN.",
                    :properties {:peerAsn {:description "The Autonomous System Number (ASN) of the peer.",
                                           :format "int32",
                                           :type "integer"},
                                 :peerContactInfo {:reference/definitions "ContactInfo"},
                                 :peerName {:description "The name of the peer.",
                                            :type "string"},
                                 :validationState {:description "The validation state of the ASN associated with the peer.",
                                                   :enum ["None"
                                                          "Pending"
                                                          "Approved"
                                                          "Failed"],
                                                   :type "string",
                                                   :x-ms-enum {:modelAsString true,
                                                               :name "validationState"}}},
                    :type "object"},
:PeeringLocationPropertiesDirect {:description "The properties that define a direct peering location.",
                                  :properties {:bandwidthOffers {:description "The list of bandwidth offers available at the peering location.",
                                                                 :items {:reference/definitions "PeeringBandwidthOffer"},
                                                                 :type "array"},
                                               :peeringFacilities {:description "The list of direct peering facilities at the peering location.",
                                                                   :items {:reference/definitions "DirectPeeringFacility"},
                                                                   :type "array"}},
                                  :type "object"},
:PeeringServiceLocationListResult {:description "The paginated list of peering service locations.",
                                   :properties {:nextLink {:description "The link to fetch the next page of peering service locations.",
                                                           :type "string"},
                                                :value {:description "The list of peering service locations.",
                                                        :items {:reference/definitions "PeeringServiceLocation"},
                                                        :type "array"}},
                                   :type "object"},
:PeeringServicePrefixProperties {:description "The peering service prefix properties class.",
                                 :properties {:learnedType {:description "The prefix learned type",
                                                            :enum ["None"
                                                                   "ViaPartner"
                                                                   "ViaSession"],
                                                            :type "string",
                                                            :x-ms-enum {:modelAsString true,
                                                                        :name "learnedType"}},
                                              :prefix {:description "Valid route prefix",
                                                       :type "string"},
                                              :prefixValidationState {:description "The prefix validation state",
                                                                      :enum ["None"
                                                                             "Invalid"
                                                                             "Verified"
                                                                             "Failed"
                                                                             "Pending"
                                                                             "Unknown"],
                                                                      :type "string",
                                                                      :x-ms-enum {:modelAsString true,
                                                                                  :name "prefixValidationState"}},
                                              :provisioningState {:description "The provisioning state of the resource.",
                                                                  :enum ["Succeeded"
                                                                         "Updating"
                                                                         "Deleting"
                                                                         "Failed"],
                                                                  :readOnly true,
                                                                  :type "string",
                                                                  :x-ms-enum {:modelAsString true,
                                                                              :name "provisioningState"}}},
                                 :type "object"},
:PeeringServiceProviderListResult {:description "The paginated list of peering service providers.",
                                   :properties {:nextLink {:description "The link to fetch the next page of peering service providers.",
                                                           :type "string"},
                                                :value {:description "The list of peering service providers.",
                                                        :items {:reference/definitions "PeeringServiceProvider"},
                                                        :type "array"}},
                                   :type "object"},
:OperationDisplayInfo {:description "The information related to the operation.",
                       :properties {:description {:description "The description of the operation.",
                                                  :readOnly true,
                                                  :type "string"},
                                    :operation {:description "The name of the operation.",
                                                :readOnly true,
                                                :type "string"},
                                    :provider {:description "The name of the resource provider.",
                                               :readOnly true,
                                               :type "string"},
                                    :resource {:description "The type of the resource.",
                                               :readOnly true,
                                               :type "string"}},
                       :type "object"},
:DirectPeeringFacility {:description "The properties that define a direct peering facility.",
                        :properties {:address {:description "The address of the direct peering facility.",
                                               :type "string"},
                                     :directPeeringType {:description "The type of the direct peering.",
                                                         :enum ["Edge"
                                                                "Transit"
                                                                "Cdn"
                                                                "Internal"],
                                                         :type "string",
                                                         :x-ms-enum {:modelAsString true,
                                                                     :name "directPeeringType"}},
                                     :peeringDBFacilityId {:description "The PeeringDB.com ID of the facility.",
                                                           :format "int32",
                                                           :type "integer"},
                                     :peeringDBFacilityLink {:description "The PeeringDB.com URL of the facility.",
                                                             :type "string"}},
                        :type "object"},
:ExchangeConnection {:description "The properties that define an exchange connection.",
                     :properties {:bgpSession {:reference/definitions "BgpSession"},
                                  :connectionIdentifier {:description "The unique identifier (GUID) for the connection.",
                                                         :type "string"},
                                  :connectionState {:description "The state of the connection.",
                                                    :enum ["None"
                                                           "PendingApproval"
                                                           "Approved"
                                                           "ProvisioningStarted"
                                                           "ProvisioningFailed"
                                                           "ProvisioningCompleted"
                                                           "Validating"
                                                           "Active"],
                                                    :readOnly true,
                                                    :type "string",
                                                    :x-ms-enum {:modelAsString true,
                                                                :name "connectionState"}},
                                  :peeringDBFacilityId {:description "The PeeringDB.com ID of the facility at which the connection has to be set up.",
                                                        :format "int32",
                                                        :type "integer"}},
                     :type "object"},
:Resource {:description "The ARM resource class.",
           :properties {:id {:description "The ID of the resource.",
                             :readOnly true,
                             :type "string"},
                        :name {:description "The name of the resource.",
                               :readOnly true,
                               :type "string"},
                        :type {:description "The type of the resource.",
                               :readOnly true,
                               :type "string"}},
           :type "object",
           :x-ms-azure-resource true},
:BgpSession {:description "The properties that define a BGP session.",
             :properties {:sessionStateV6 {:description "The state of the IPv6 session.",
                                           :enum ["None"
                                                  "Idle"
                                                  "Connect"
                                                  "Active"
                                                  "OpenSent"
                                                  "OpenConfirm"
                                                  "OpenReceived"
                                                  "Established"
                                                  "PendingAdd"
                                                  "PendingUpdate"
                                                  "PendingRemove"],
                                           :readOnly true,
                                           :type "string",
                                           :x-ms-enum {:modelAsString true,
                                                       :name "sessionStateV6"}},
                          :sessionStateV4 {:description "The state of the IPv4 session.",
                                           :enum ["None"
                                                  "Idle"
                                                  "Connect"
                                                  "Active"
                                                  "OpenSent"
                                                  "OpenConfirm"
                                                  "OpenReceived"
                                                  "Established"
                                                  "PendingAdd"
                                                  "PendingUpdate"
                                                  "PendingRemove"],
                                           :readOnly true,
                                           :type "string",
                                           :x-ms-enum {:modelAsString true,
                                                       :name "sessionStateV4"}},
                          :sessionPrefixV4 {:description "The IPv4 prefix that contains both ends' IPv4 addresses.",
                                            :type "string"},
                          :sessionPrefixV6 {:description "The IPv6 prefix that contains both ends' IPv6 addresses.",
                                            :type "string"},
                          :peerSessionIPv4Address {:description "The IPv4 session address on peer's end.",
                                                   :type "string"},
                          :maxPrefixesAdvertisedV4 {:description "The maximum number of prefixes advertised over the IPv4 session.",
                                                    :format "int32",
                                                    :type "integer"},
                          :microsoftSessionIPv6Address {:description "The IPv6 session address on Microsoft's end.",
                                                        :readOnly true,
                                                        :type "string"},
                          :peerSessionIPv6Address {:description "The IPv6 session address on peer's end.",
                                                   :type "string"},
                          :maxPrefixesAdvertisedV6 {:description "The maximum number of prefixes advertised over the IPv6 session.",
                                                    :format "int32",
                                                    :type "integer"},
                          :md5AuthenticationKey {:description "The MD5 authentication key of the session.",
                                                 :type "string"},
                          :microsoftSessionIPv4Address {:description "The IPv4 session address on Microsoft's end.",
                                                        :readOnly true,
                                                        :type "string"}},
             :type "object"},
:ErrorResponse {:description "The error response that indicates why an operation has failed.",
                :properties {:code {:description "The error code.",
                                    :readOnly true,
                                    :type "string"},
                             :message {:description "The error message.",
                                       :readOnly true,
                                       :type "string"}},
                :type "object"},
:PeeringLocationListResult {:description "The paginated list of peering locations.",
                            :properties {:nextLink {:description "The link to fetch the next page of peering locations.",
                                                    :type "string"},
                                         :value {:description "The list of peering locations.",
                                                 :items {:reference/definitions "PeeringLocation"},
                                                 :type "array"}},
                            :type "object"},
:PeeringServiceListResult {:description "The paginated list of peering services.",
                           :properties {:nextLink {:description "The link to fetch the next page of peering services.",
                                                   :type "string"},
                                        :value {:description "The list of peering services.",
                                                :items {:reference/definitions "PeeringService"},
                                                :type "array"}},
                           :type "object"},
:PeerAsn {:allOf [{:reference/definitions "Resource"}],
          :description "The essential information related to the peer's ASN.",
          :properties {:properties {:reference/definitions "PeerAsnProperties"}},
          :type "object"},
:PeerAsnListResult {:description "The paginated list of peer ASNs.",
                    :properties {:nextLink {:description "The link to fetch the next page of peer ASNs.",
                                            :type "string"},
                                 :value {:description "The list of peer ASNs.",
                                         :items {:reference/definitions "PeerAsn"},
                                         :type "array"}},
                    :type "object"},
:PeeringBandwidthOffer {:description "The properties that define a peering bandwidth offer.",
                        :properties {:offerName {:description "The name of the bandwidth offer.",
                                                 :type "string"},
                                     :valueInMbps {:description "The value of the bandwidth offer in Mbps.",
                                                   :format "int32",
                                                   :type "integer"}},
                        :type "object"},
:ResourceTags {:description "The resource tags.",
               :properties {:tags {:additionalProperties {:type "string"},
                                   :description "Gets or sets the tags, a dictionary of descriptors arm object",
                                   :type "object"}},
               :type "object"},
:DirectConnection {:description "The properties that define a direct connection.",
                   :properties {:bandwidthInMbps {:description "The bandwidth of the connection.",
                                                  :format "int32",
                                                  :type "integer"},
                                :bgpSession {:reference/definitions "BgpSession"},
                                :connectionIdentifier {:description "The unique identifier (GUID) for the connection.",
                                                       :type "string"},
                                :connectionState {:description "The state of the connection.",
                                                  :enum ["None"
                                                         "PendingApproval"
                                                         "Approved"
                                                         "ProvisioningStarted"
                                                         "ProvisioningFailed"
                                                         "ProvisioningCompleted"
                                                         "Validating"
                                                         "Active"],
                                                  :readOnly true,
                                                  :type "string",
                                                  :x-ms-enum {:modelAsString true,
                                                              :name "connectionState"}},
                                :peeringDBFacilityId {:description "The PeeringDB.com ID of the facility at which the connection has to be set up.",
                                                      :format "int32",
                                                      :type "integer"},
                                :provisionedBandwidthInMbps {:description "The bandwidth that is actually provisioned.",
                                                             :format "int32",
                                                             :type "integer"},
                                :sessionAddressProvider {:description "The field indicating if Microsoft provides session ip addresses.",
                                                         :enum ["Microsoft"
                                                                "Peer"],
                                                         :type "string",
                                                         :x-ms-enum {:modelAsString true,
                                                                     :name "sessionAddressProvider"}},
                                :useForPeeringService {:description "The flag that indicates whether or not the connection is used for peering service.",
                                                       :type "boolean"}},
                   :type "object"},
:PeeringPropertiesDirect {:description "The properties that define a direct peering.",
                          :properties {:connections {:description "The set of connections that constitute a direct peering.",
                                                     :items {:reference/definitions "DirectConnection"},
                                                     :type "array"},
                                       :directPeeringType {:description "The type of direct peering.",
                                                           :enum ["Edge"
                                                                  "Transit"
                                                                  "Cdn"
                                                                  "Internal"],
                                                           :type "string",
                                                           :x-ms-enum {:modelAsString true,
                                                                       :name "directPeeringType"}},
                                       :peerAsn {:reference/definitions "SubResource"},
                                       :useForPeeringService {:description "The flag that indicates whether or not the peering is used for peering service.",
                                                              :type "boolean"}},
                          :type "object"},
:PeeringServiceProvider {:allOf [{:reference/definitions "Resource"}],
                         :description "PeeringService provider",
                         :properties {:properties {:reference/definitions "PeeringServiceProviderProperties"}},
                         :type "object"}}}
)



(def client (api/client PeeringManagementClient))
