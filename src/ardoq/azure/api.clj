(ns ardoq.azure.api
  (:require
    [ardoq.azure.http :as http]
    [ardoq.azure.auth :as auth]
    [ardoq.azure.docs :as doc]
    [ardoq.azure.client :as client]
    [clojure.pprint :refer [pprint]]
    [clojure.java.io :as io]
    [clojure.edn :as edn])
  (:import [java.io PushbackReader]))

(def ^:const api-list-filename "api-list.edn")

(defn apis
  []
  (let [url (io/resource api-list-filename)]
    (-> url io/reader PushbackReader. edn/read)))

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
   [client-kw api-version sub-id auth-token]
    {:client (client/load-client client-kw api-version) :sub-id sub-id :auth auth-token})

(defn auth
  [tenant-id client-id client-secret]
  (auth/make-token tenant-id client-id client-secret))
