(ns ardoq.azure.utils)

;; From gcp-api
(def kw-pattern #"[$A-Za-z]+[-*+_'?<>=A-Za-z0-9]*")

(defn json-key
  [param]
  (if (re-matches kw-pattern param)
    (keyword param)
    param))

;; From https://gist.github.com/danielpcox/c70a8aa2c36766200a95
 (defn deep-merge [& maps]
  (apply merge-with (fn [& args]
                      (if (every? map? args)
                        (apply deep-merge args)
                        (last args)))
         maps))
  

(defn col-contains? [col elem] (some #(= elem %) col))
