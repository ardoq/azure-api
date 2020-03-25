(ns ardoq.azure.http
  (:require
    [clj-http.client :as client]
    [ardoq.azure.utils :as utils]
    [clojure.string :as str]
    [clojure.walk :as walk]
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



;; TODO: Exhaustive list of "special" keys (tags, allOf etc)
(def reserved #{:tags :required :allOf})

;; XXX: Does required only exist for top-level objects?
(defn verify-body-object
  "properties contains kv-pairs
  the values can contain objects which in turn contain their own :properties, so this is recursive"
  [schema req-value]
  (let [{:keys [properties required]} schema]
    (reduce-kv
      (fn [acc k v]
        (cond
          (k reserved) (println "This is reserved, so we don't want to process it as a json field")
          (and (utils/col-contains? required (name k)) (not (k req-value)))
            (println "Required object field" k "not given")
          (k req-value) (if (= "object" (:type v))
                          (assoc acc k (verify-body-object v (k req-value)))
                          (assoc acc k (k req-value)))
          :else acc))
      {} properties)))


(defn structure-parameters
  "Takes op :parameters vec and request-map parameters, returns map like:
  {:path {:id 10 :num 40}
  :query {:uh true}
  :body {:sure 20}}"
  [op-params request]
  ;; FIXME: Disallow user-given parameters which are not in param list
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
                      req-value (if (and (= :body in) (= "object" (get-in param [:schema :type])))
                                  {in {name (verify-body-object (:schema param) req-value)}}
                                  {in {name req-value}}))
                      ))
              op-params)))

;; FIXME: Naming
(defn resolve-refs
  [traversable parameters definitions]
  (walk/postwalk
    (fn [map-elem]
      ;; Surely there's a better way to do this
      (if-let [value (or (get map-elem :reference/parameters) (get map-elem :reference/definitions))]
        (let [type (-> map-elem first key name keyword) ;; Ref-maps only have one key
              value (keyword value)]
          (case type
            :parameters (resolve-refs (get parameters value) parameters definitions)
            :definitions (resolve-refs (get definitions value) parameters definitions)
            (throw (AssertionError. (str "Invalid reference type: " type)))
            ))
        map-elem))
    traversable))

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
