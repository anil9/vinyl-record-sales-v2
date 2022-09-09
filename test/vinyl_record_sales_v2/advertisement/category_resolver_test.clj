(ns vinyl-record-sales-v2.advertisement.category-resolver-test
  (:require [clojure.test :refer :all]
            [vinyl-record-sales-v2.advertisement.category-resolver :refer :all]))

(deftest name-test
  (testing "Categories"
    (is (= (:ROCK product-category) (category-id {:genre ["rock"]})))
    (is (= (:POP product-category) (category-id {:genre ["pop" "rock"]})))
    (is (= (:FOLK product-category) (category-id {:genre ["folk"]}))))
  (testing "Years"
    (is (= (:DECADE_50S_60S product-category) (category-id {:year 1950})))
    (is (= (:DECADE_50S_60S product-category) (category-id {:year 1959})))
    (is (= (:DECADE_50S_60S product-category) (category-id {:year 1969})))
    (is (= (:DECADE_70S product-category) (category-id {:year 1970})))
    (is (= (:DECADE_70S product-category) (category-id {:year 1979})))
    (is (= (:DECADE_80S product-category) (category-id {:year 1980})))
    (is (= (:DECADE_80S product-category) (category-id {:year 1989})))
    (is (= (:DECADE_90S product-category) (category-id {:year 1990})))
    (is (= (:DECADE_90S product-category) (category-id {:year 1999})))
    (is (= (:DECADE_2000 product-category) (category-id {:year 2000}))))
  (testing "No match found"
    (is (= (:OTHER product-category) (category-id {})))))
