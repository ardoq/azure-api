(ns ardoq.azure.docs
  (:require
    [ardoq.azure.http :as http]))

(defn ops
  [client]
  (keys (get-in client [:client :ops])))

(defn get-op-info
  [client op]
  (http/resolve-refs (get-in client [:ops op]) (:parameters client) (:definitions client)))
