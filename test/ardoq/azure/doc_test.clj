(ns ardoq.azure.doc-test
  (:require
    [clojure.test :refer [deftest testing is]]
    [ardoq.azure.http-test-data :as test-data]
    [ardoq.azure.docs :as docs]))

(deftest get-ops-test
  (testing "Retrieving the ops works fine"
    (is (= '(:PeeringLocations_List
              :PeerAsns_ListBySubscription
              :PeerAsns_Get
              :PeeringServices_ListByResourceGroup
              :PeeringServiceLocations_List
              :PeeringServices_CreateOrUpdate
              :Peerings_Delete
              :PeeringServices_Delete
              :PeeringServices_ListBySubscription
              :PeerAsns_Delete
              :CheckServiceProviderAvailability
              :Peerings_Update
              :PeeringServicePrefixes_Get
              :Peerings_ListBySubscription
              :Prefixes_ListByPeeringService
              :PeeringServicePrefixes_CreateOrUpdate
              :LegacyPeerings_List
              :Peerings_Get
              :PeeringServices_Update
              :PeeringServicePrefixes_Delete
              :PeerAsns_CreateOrUpdate
              :Peerings_ListByResourceGroup
              :PeeringServices_Get
              :PeeringServiceProviders_List
              :Operations_List
              :Peerings_CreateOrUpdate)
           (docs/ops (test-data/client))))))

(deftest get-op-info-test
  (testing "Retrieving info about an op works fine"
    (is (= {:path "/subscriptions/{subscriptionId}/providers/Microsoft.Peering/peeringLocations",
            :verb "get",
            :description "Lists all of the available peering locations for the specified kind of peering.",
            :parameters [{:description "The kind of the peering.",
                          :enum ["Direct" "Exchange"],
                          :in "query",
                          :name "kind",
                          :required true,
                          :type "string"}
                         {:description
                          "The type of direct peering.",
                          :enum ["Edge" "Transit" "Cdn" "Internal"],
                          :in "query",
                          :name "directPeeringType",
                          :required false,
                          :type "string"}
                         {:description "The Azure subscription ID.",
                          :in "path",
                          :name "subscriptionId",
                          :required true,
                          :type "string"}
                         {:description "The client API version.",
                          :in "query",
                          :name "api-version",
                          :required true,
                          :type "string"}]}
           (docs/get-op-info (:client (test-data/client)) :PeeringLocations_List)))))
