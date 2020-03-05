(ns ardoq.azure.http
  (:require
    [clj-http.client :as client]))



(defn handle-request-param
  [req-value]
  
  )

(defn resolve-parameters
  [parameters request]
  (map (fn [param]
         ;; TODO: Expand. We ignore body params for now
         (let [{:keys [in required name]} param
               req-value ((keyword name) request)]
         (cond
           (and required (not req-value))
           (throw (ArgumentException. (str "Required parameter not given: " name)))
           req-value (handle-request-param req-value)

           
           )))))


(defn build-request-map

  [client op-map]
  (let [op (get-in client [:ops (:op op-map)])
        {:keys [path parameters]} op
        parameters (resolve-parameters parameters (:request client))
        url (format "%s://%s%s"
                    (:scheme client)
                    (:host client)
                    path)
        base-req {:method (::descriptors/http-method op-descriptor)
                  :url    url}]
    (with-request-parameters base-req (:parameters op-descriptor) request-map)))

  [client op-map])


(defn send-request
  [request])
