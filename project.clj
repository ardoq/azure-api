(defproject com.ardoq/azure-api "0.1.0"
  :description "Allows for simple usage of the Azure REST APIs in Clojure."
  :url "https://github.com/ardoq/azure-api"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[clj-http "3.12.1"]
                 [clj-time "0.15.2"]
                 [com.cognitect/anomalies "0.1.12"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "1.0.0"]]
  :repl-options {:init-ns ardoq.azure.api}
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[clj-kondo "2020.10.10"]]}}
  :aliases {"lint" ["run" "-m" "clj-kondo.main" "--lint" "src" "--lint" "dev" "--lint" "test"]
            "l" "lint"
            "t" ["do" ["clean"] ["test"]]}
  :main ^:skip-aot update-api-descriptors)
