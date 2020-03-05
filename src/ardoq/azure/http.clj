(ns ardoq.azure.http
  (:require
    [clj-http.client :as client]
    [ardoq.azure.utils :as utils]
    [clojure.string :as str]
    ))


(def curly-brackets-regex #"\{(.*?)\}")


(defn resolve-path-parameters
  "Takes path-parameters map (e.g. {:id 20 :name \"steve\"})
  and path (e.g. \"/hello/world/{id}/{name}\").
  Returns path with resolved path parameters"
  [parameters path]
  (let [path-parts (str/split path #"/")
        resolved-path-parts (map (fn [path]
                                   (if-let [path-param
                                            (->> path
                                                 (re-find curly-brackets-regex)
                                                 last keyword)]
                                     (path-param parameters)
                                     path))
                                 path-parts)]
    (str/join "/" resolved-path-parts)))


(defn handle-request-param
  [req-value])

(defn structure-parameters
  "Takes op param vec and op-map reqs, returns map like:
  {:path {:id 10 :num 40}
  :query {:uh true}
  :body {:sure 20}}"
  [op-parameters request]
  (apply utils/deep-merge
         (map (fn [param]
                ;; TODO: Expand. We ignore body params for now, and no type-checking
                (let [{:keys [in required name]} param
                      [in name] (map keyword [in name])
                      req-value (name request)]
                  (cond
                    (and required (not req-value))
                    ;; FIXME: Don't throw
                    (throw (IllegalArgumentException. (str "Required parameter not given: " name)))
                    req-value {in {name req-value}}
                    )))
              op-parameters)))

(defn resolve-parameter-refs
  [op-params parameters definitions]
  (map (fn [op-param]
         (if (= "reference" (-> op-param first key namespace))
           (let [type (-> op-param first key name keyword) ;; XXX: Kind of verbose
                 value (-> op-param first val keyword)]
             (case type
               :parameters (get parameters value)
               :definitions (get definitions value)
               (throw (AssertionError. (str "Invalid reference type: " type)))
               ))
           op-param))
       op-params))

(defn with-request-parameters
  [base-req parameters]
  (reduce-kv (fn [acc k v]
               (case k
                 :query (assoc acc :query-params v)
                 acc
                 ))
             base-req parameters))

(defn build-request-map
  [client op-map]
  (let [op (get-in client [:ops (:op op-map)]) ;; FIXME: If-let
        {:keys [path parameters verb]} op
        parameters (resolve-parameter-refs parameters (:parameters client) (:definitions client))
        parameters (structure-parameters parameters (:request op-map))
        path (resolve-path-parameters (:path parameters) path)
        url (format "%s://%s%s"
                    (:scheme client)
                    (:host client)
                    path)
        base-req {:method verb
                  :url    url}]
    (with-request-parameters base-req parameters)))



(defn send-request
  [request])
