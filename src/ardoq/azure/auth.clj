(ns ardoq.azure.auth
  (:require
    [clj-http.client :as client]
    [clojure.data.json :as json])
  (:import (clojure.lang IFn)))

(def RENEW-TIME 15)

(defrecord Auth [fetch-token token-atom auth-info]
  IFn
  (invoke [_]
    (fetch-token token-atom auth-info)))

;; TODO: Error handling
(defn get-auth-token
  [tenant-id client-id client-secret]
  (let [res (client/post (str "https://login.microsoftonline.com/" tenant-id "/oauth2/token")
                         {:form-params {:grant_type "client_credentials"
                                        :client_id client-id
                                        :client_secret client-secret
                                        :resource "https://management.core.windows.net/"}})
    body (-> res :body (json/read-str :key-fn keyword))
    token (:access_token body)
    timestamp (Long/valueOf (:expires_on body))]
    {:token token
     :timestamp timestamp}))

(defn fetch-token
  "Checks token timestamp, updates and returns new token if remaining time < RENEW-TIME minutes"
  [token-atom auth-info]
  (let [cur-time (quot (System/currentTimeMillis) 1000)
        {:keys [tenant-id client-id client-secret]} auth-info]
    (when (< (- (:timestamp @token-atom) cur-time)
           (* 60 RENEW-TIME))
      (swap! token-atom merge (get-auth-token tenant-id client-id client-secret)))
    (:token @token-atom)))

(defn make-token 
  [tenant-id client-id client-secret]
  (let [token-atom (atom (get-auth-token tenant-id client-id client-secret))
        auth-info {:tenant-id tenant-id
                   :client-id client-id
                   :client-secret client-secret}]
    (->Auth fetch-token token-atom auth-info)))
