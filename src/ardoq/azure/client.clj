(ns ardoq.azure.client
  (:require
    [clojure.java.io :as io]
    [clojure.edn :as edn]))

(defn load-client
  [client-kw api-version]
  (let [url (io/resource (str (name client-kw) "/" api-version "/descriptor.edn"))]
    (-> url io/reader java.io.PushbackReader. edn/read)))