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



(def reserved #{:tags :required})

(defn structure-properties
  ":properties contains kv-pairs
  the values can contain objects which in turn contain their own :properties
  However, :properties is a valid keyname, so we're careful to only resolve
  :properties for objects, not for :properties itself"
  [properties]
  
  )

;; XXX: We assume references are resolved at this point
(defn structure-body
  [param req-value]
  (let [{:keys [properties required]} (:schema param)
        properties (structure-properties properties)])
  
  )

(defn structure-parameters
  "Takes op param vec and op-map reqs, returns map like:
  {:path {:id 10 :num 40}
  :query {:uh true}
  :body {:sure 20}}"
  [op-params request]
  (apply utils/deep-merge
         (map (fn [param]
                ;; TODO: Type-checking
                (let [{:keys [in required name]} param
                        [in name] (map keyword [in name])
                        req-value (name request)]
                    (cond
                      (and required (not req-value))
                      ;; FIXME: Don't throw, return error-map
                      (throw (IllegalArgumentException. (str "Required parameter not given: " name)))
                      req-value
                      (if (= :body in)
                        (structure-body param req-value)
                        {in {name req-value}})
                      )))
              op-params)))



(defn resolve-refs
  "Returns op-param vec with parameter and definition refs recursively resolved into it"
  [op-params parameters definitions]
  (map (fn [op-param]
         (if (= "reference" (-> op-param first key namespace))
           (let [type (-> op-param first key name keyword) ;; XXX: Kind of verbose
                 value (-> op-param first val keyword)]
             (case type
               :parameters (resolve-refs (get parameters value) parameters definitions)
               :definitions (resolve-refs (get definitions value) parameters definitions)
               (throw (AssertionError. (str "Invalid reference type: " type)))
               ))
           op-param))
       op-params))

(defn with-request-parameters
  ;; TODO: Header and form params?
  [base-req parameters]
  (reduce-kv (fn [acc k v]
               (case k
                 :query (assoc acc :query-params v)
                 :body acc
                 acc
                 ))
             base-req parameters))

(defn build-request-map
  [client op-map]
  (let [op (get-in client [:ops (:op op-map)]) ;; FIXME: If-let
        {:keys [path parameters verb]} op
        parameters (resolve-refs parameters (:parameters client) (:definitions client))
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
