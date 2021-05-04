(ns ardoq.azure.descriptor-test
  (:require
    [clojure.test :refer [deftest testing is]]
    [ardoq.azure.descriptor :as desc]
    [ardoq.azure.descriptor-test-data :as test-data]))

(comment "Outdated"
         (deftest resolve-ref
           (testing "Refs are successfully resolved"
             (is (= test-data/swagger-resolved
                    (desc/resolve-all-refs test-data/swagger-unresolved))))))

(deftest paths->ops-test
  (testing "Path structure successfully converted"
    (is (= test-data/paths-converted
           (desc/paths->ops test-data/paths-unconverted)))))
