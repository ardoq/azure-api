(ns update-api-descriptors
  (:require
    [clojure.string :as str]
    [clojure.java.io :as io]
    [clojure.data.json :as json]
    [clj-http.client :as client]
    [ardoq.azure.descriptor :as desc]
    [ardoq.azure.utils :as utils]
    ))

(def dir "azure_api_swagger")
(def force-download-all false)


;; Retrieving and Downloading

(defn get-json
  [url]
  (json/read-str (-> url client/get :body) :key-fn utils/json-key))

(defn fetch-azure-apis
  []
  (filter #(->> % first name (re-matches #"azure(.*)")) (get-json "https://api.apis.guru/v2/list.json")))

(defn dl-file!
  [name version url]
  (let [path (str/join "/" [dir name version "descriptor.edn"])]
    (if (or (not (.exists (io/file path))) force-download-all)
      (do
        (println "Downloading version" version)
        (io/make-parents path)
        ;; Conversion happens here
        (spit path (-> url get-json desc/swagger->descriptor)))
      (println version "Already exists. Skipping..."))))

(defn write-swaggerfiles!
  [api]
  (let [name (-> api first name (str/split #":") last)
        versions (-> api val :versions)
        version-names (map #(-> % val :info :version) versions)
        swagger-urls (map #(-> % val :swaggerUrl) versions)]

    (println "Retrieving swaggerfiles for" name)
    (mapv #(dl-file! name %1 %2) version-names swagger-urls)))

(defn -main
  [& args]
  (mapv write-swaggerfiles! (fetch-azure-apis)))
