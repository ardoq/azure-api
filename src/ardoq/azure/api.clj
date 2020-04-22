(ns ardoq.azure.api
  (:require
    [ardoq.azure.http :as http]
    [ardoq.azure.auth :as auth]
    [ardoq.azure.docs :as doc]
    [clojure.pprint :refer [pprint]]
    [ardoq.azure.apis :refer [PeeringManagementClient SqlManagementClient ResourceManagementClient]]))

(defn ops
  [client]
  (pprint (doc/ops client)))

(defn doc
  [client op]
  (pprint (doc/get-op-info (:client client) op)))

(defn invoke
  [client op-map]
  (let [request (http/build-request-map client op-map)]
    (http/send-request request)))

(defn client
  [client sub-id auth-token]
  {:client client :sub-id sub-id :token auth-token})

(defn auth
  [tenant-id client-id client-secret]
  (auth/make-token tenant-id client-id client-secret))


