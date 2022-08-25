(ns vinyl-record-sales-v2.lookup.core-test
  (:require [clojure.test :refer :all]
            [vinyl-record-sales-v2.lookup.core :refer [get-release-id]]))

(def simple-result [{:id 1 :title "This title is awesome!" :type "release"}
                    {:id 2 :title "This is another title?" :type "release"}
                    {:id 3 :title "Awesome but not a release" :type "master"}]) 


(deftest get-release-id-test
  (testing "Cannot find a distinct title"
    (is (nil? (get-release-id simple-result [])))
    (is (nil? (get-release-id simple-result ["title"])))
    (is (nil? (get-release-id simple-result ["ThIs"]))))
  (testing "Finding unique titles"
    (is (= 1 (get-release-id simple-result ["awesome"])))
    (is (= 2 (get-release-id simple-result ["anOtHeR"]))))
  (testing "Wont find non-release types"
    (is (nil? (get-release-id simple-result ["release"])))
    (is (nil? (get-release-id simple-result ["awesome but not a release"])))))
