(ns ardoq.azure.descriptor
  (:require
    [clojure.string :as str]
    [clojure.walk :as walk]))

;; Converting swagger to descriptor

;; FIXME: Improperly named. We resolve references at invoke-time
;; If we attempt to resolve them here we end up with a stack overflow
(defn resolve-ref
  [ref-path]
  (let [[_ type location] (str/split ref-path #"\/" 3)]
    (if (#{"definitions" "parameters"} type)
      {(keyword "reference" type) location}
      ;; FIXME: Reference can use dir traversal (e.g. ../somefile.json/definitions)
      ;; Seems to mainly be network APIs like network-loadBalancer
      (println "Invalid reference type for swagger ref:" type))))

(defn resolve-all-refs
  [swagger]
  (walk/prewalk
    (fn [form]
      (if-let [ref-path (get form :$ref)]
        (resolve-ref ref-path)
        form))
    swagger))

;; We're assuming (for now) that :operationId's are unique
(defn path->ops
  [acc path verbs]
  ;; We want :operationId to be on top, not the path, so we have to reduce over verbs as well.
  ;; We're willfully ignoring responses for now
  (merge
    acc
    (reduce-kv (fn [inner-acc verb data]
                 (let [{:keys [operationId description parameters]} data]
                   (assoc inner-acc (keyword operationId) ;; Assuming all names are valid keywords
                             {:path path
                              :description description
                              :parameters parameters ;; XXX: Should params have name keyword?
                              :verb verb
                              })))
               {} verbs)))

(defn paths->ops
  [paths]
  (reduce-kv path->ops {} paths))


(defn parse-info
  [info]
  (select-keys info [:description :title :version]))

(defn swagger->descriptor
  [swagger]
  (let [swagger (resolve-all-refs swagger)
        {:keys [schemes host info paths parameters definitions]} swagger]
    {:scheme (first schemes) ; FIXME: Copy gcp-api approach
     :host host
     :info (parse-info info)
     :ops (paths->ops paths)
     :parameters parameters
     :definitions definitions
     }))
