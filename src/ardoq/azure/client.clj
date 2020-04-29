(ns ardoq.azure.client
  (:require
    [clojure.java.io :as io]
    [clojure.edn :as edn]
    [clojure.string :as str]
    [clj-time.core :as t]))

(defn load-client
  [client-kw api-version]
  (let [url (io/resource (str (name client-kw) "/" api-version "/descriptor.edn"))]
    (-> url io/reader java.io.PushbackReader. edn/read)))

(defn string->date-time
  [string-date]
  (let [split-time (str/split string-date #"-")]
    (if (#{"preview" "privatepreview"} (last split-time))
      (apply t/date-time (map #(Integer/parseInt %) (butlast split-time)))
      (apply t/date-time (map #(Integer/parseInt %) split-time))
      )))

(defn get-latest-api
  [client-kw]
  (let [folder (-> (name client-kw) io/resource io/file)
        versions (->> folder .listFiles seq (map #(.getName %)))]
    (loop [idx 0
           latest 0]
      (if (= idx (count versions))
        (load-client client-kw (nth versions latest))
        (if (t/before?
              (string->date-time (nth versions latest))
              (string->date-time (nth versions idx)))
          (recur (inc idx) idx)
          (recur (inc idx) latest)
          )))))
