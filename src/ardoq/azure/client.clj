(ns ardoq.azure.client
  (:require
    [clojure.java.io :as io]
    [clojure.edn :as edn])
  (:import [java.io PushbackReader]))

(defn load-client
  [client-kw api-version]
  (let [url (io/resource (str (name client-kw) "/" api-version "/descriptor.edn"))]
    (-> url io/reader PushbackReader. edn/read)))
