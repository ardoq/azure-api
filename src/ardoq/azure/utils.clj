(ns ardoq.azure.utils)

(def kw-pattern #"[$A-Za-z]+[-*+_'?<>=A-Za-z0-9]*")

(defn json-key
  [param]
  (if (re-matches kw-pattern param)
    (keyword param)
    param))
