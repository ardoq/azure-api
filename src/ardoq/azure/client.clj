(ns ardoq.azure.client
  (:require
    [clojure.java.io :as io]
    [clojure.edn :as edn])
  (:import [java.io PushbackReader]))

(defn- versions [client-kw]
  (-> (io/resource (name client-kw))
      io/file
      (.list)
      (sort)))

(defn load-client
  ([client-kw]
   (let [api-version (last (versions client-kw))]
     (load-client client-kw api-version)))
  ([client-kw api-version]
   (let [url (io/resource (str (name client-kw) "/" api-version "/descriptor.edn"))]
     (-> url io/reader PushbackReader. edn/read))))
