(ns ardoq.azure.api
  (:require
    [ardoq.azure.http :as http]
    [ardoq.azure.apis :refer [PeeringManagementClient]]))

(defn invoke
  [client op-map]
  (let [request (http/build-request-map client op-map)]
    (http/send-request request)))

(defn client
 [client bearer-token]
 {:client client :auth bearer-token})

(defn -main [])
