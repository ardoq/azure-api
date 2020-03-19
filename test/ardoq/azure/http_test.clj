(ns ardoq.azure.http-test
  (:require
    [clojure.test :refer :all]
    [ardoq.azure.http :as http]
    [ardoq.azure.http-test-data :as test-data]
    ))

(deftest resolve-path-parameters-test
  (testing "path-parameters successfully resolves into path"
    (let [path "/test/{id}/path/{version-no}/{somestring}"
           req-map {:id 10 :version-no 1.3 :somestring "hello"}
           resolved-path "/test/10/path/1.3/hello"]
      (is (= resolved-path
           (http/resolve-path-parameters req-map path))))))

(deftest structure-parameters-test
  (testing "path and query structuring works properly"
    (let [req-map {:kind "alright" :callous "yup" :mean "why not"}
           structured-map {:query {:kind "alright"
                                   :callous "yup"}
                           :path {:mean "why not"}}]
      (is (= structured-map
             (http/structure-parameters test-data/path-and-query req-map))))))

(deftest resolve-refs-test
  (testing "Parameter refs are resolved into op-params"
    ;; Convert to set first because vec is ordered and map is not
    (is (= (set test-data/resolved-path-and-refs)
           (set (http/resolve-refs test-data/path-and-refs test-data/parameters nil)))))
  (testing "Recursive definition refs are resolved correctly"
    (is (= (test-data/resolved-definition-refs)
           (http/resolve-refs test-data/definition-refs nil test-data/definitions)))))

(deftest build-request-map-test
  (testing "Correct request map is built"
    (let [op-map {:op :PeeringLocations_List
                  :request {:subscriptionId "10" :api-version "2019-08-01-preview"
                            :kind "Direct" :directPeeringType "Edge"}}]
    (is (= "ok" (http/build-request-map (:client test-data/client) op-map))))))
