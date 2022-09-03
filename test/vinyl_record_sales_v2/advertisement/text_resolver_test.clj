(ns vinyl-record-sales-v2.advertisement.text-resolver-test
  (:require [clojure.test :refer :all]
            [vinyl-record-sales-v2.advertisement.text-resolver :refer :all]))


(deftest resolve-tracklist-test
       (is (= "Tracklist:<br>trackOne<br>trackTwo" (resolve-tracklist {:tracklist [{:title "trackOne"}
                                                                                   {:title "trackTwo"}]})))
       (is (nil? (resolve-tracklist {:tracklist [{}
                                                 {}]}))))


(deftest resolve-year-test
        (is (= "År: 1987" (resolve-year {:year 1987}))) 
        (is (= "År: 1987" (resolve-year {:year "1987"}))) 
        (is (nil? (resolve-year {})))) 
