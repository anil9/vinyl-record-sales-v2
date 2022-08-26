(ns vinyl-record-sales-v2.lookup.core-test
  (:require [clojure.test :refer :all]
            [vinyl-record-sales-v2.lookup.core :refer [releases-matching-title-words contain-all-words?]]))

(deftest contains-all-words-test
  (is (true? (contain-all-words? "word" ["word"])))
  (is (true? (contain-all-words? "This is a sentence" ["this"])))
  (is (true? (contain-all-words? "This is a sentence" ["this" "sentence"])))
  (is (true? (contain-all-words? "This is a sentence" ["this" "sentence" "is" "a"])))
  (is (false? (contain-all-words? "This is a sentence" ["random"])))
  (is (false? (contain-all-words? "This is a sentence" ["this" "is" "a" "random" "sentence"]))))

(def simple-result [{:id 1 :title "This title is awesome!" :type "release"}
                    {:id 2 :title "This is another title?" :type "release"}
                    {:id 3 :title "Awesome but not a release" :type "master"}])

(deftest get-release-id-test
  (testing "Cannot find a distinct title"
    (is (= (drop-last simple-result) (releases-matching-title-words simple-result [])))
    (is (= (drop-last simple-result) (releases-matching-title-words simple-result ["title"])))
    (is (= (drop-last simple-result) (releases-matching-title-words simple-result ["ThIs"]))))
  (testing "Finding unique titles"
    (is (= [(first simple-result)] (releases-matching-title-words simple-result ["awesome"])))
    (is (= [(second simple-result)](releases-matching-title-words simple-result ["anOtHeR"]))))
  (testing "Wont find non-release types"
    (is (empty? (releases-matching-title-words simple-result ["release"])))
    (is (empty? (releases-matching-title-words simple-result ["awesome but not a release"])))))
