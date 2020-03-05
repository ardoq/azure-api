(ns ardoq.azure.client
  (:require
    [ardoq.azure.http :as http]
    [ardoq.azure.apis :refer [PeeringManagementClient]]))

(defn invoke
  [client op-map]
  (let [request (http/build-request-map client op-map)]
    (http/send-request request)))

(defn client
 [client]
 {:client client})

(defn -main []
  (client PeeringManagementClient))