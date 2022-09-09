(ns vinyl-record-sales-v2.advertisement.text-resolver-test
  (:require [clojure.test :refer :all]
            [vinyl-record-sales-v2.advertisement.text-resolver :refer :all]))

(def example-record-info
  {:genres ["Non-Music"],
   :title "110 Meter Häck",
   :year 1974,
   :styles ["Comedy"],
   :tracklist [{:position "A1", :type_ "track", :title "110 Meter Häck", :duration ""}
               {:position "A2", :type_ "track", :title "Hur Man Än Sig Vänder", :duration ""}]})

(deftest resolve-tracklist-test
  (is (= "Tracklist:<br>trackOne<br>trackTwo" (resolve-tracklist {:tracklist [{:title "trackOne"}
                                                                              {:title "trackTwo"}]})))
  (is (nil? (resolve-tracklist {:tracklist [{}
                                            {}]}))))

(deftest join-with-test
  (is (= "År: 1987" (join-with "År: " (:year {:year 1987}))))
  (is (= "År: 1987" (join-with "År: " "1987")))
  (is (= "Test: something, other" (join-with "Test: " '("something" "other"))))
  (is (nil? (join-with "Test: " nil)))
  (is (nil? (join-with "Test: " '()))))

(deftest create-text-test
  (testing "Complete example"
    (is (= "Skick se bilder. Ej provspelad. Kolla gärna in mina andra annonser och passa på att buda in fler LP-skivor, jag samfraktar gärna!<br>Fraktpriser (ca):<br>1 skiva 70 kr<br>2 skivor 73 kr<br>3 skivor 76 kr<br>4 skivor 87 kr<br>5 skivor 99 kr<br>6 skivor 102 kr<br><br>År: 1974<br>Genre: Non-Music<br>Style: Comedy<br>Tracklist:<br>110 Meter Häck<br>Hur Man Än Sig Vänder"
           (create-text example-record-info))))
  (testing "Minimal example"
    (is (= "Skick se bilder. Ej provspelad. Kolla gärna in mina andra annonser och passa på att buda in fler LP-skivor, jag samfraktar gärna!<br>Fraktpriser (ca):<br>1 skiva 70 kr<br>2 skivor 73 kr<br>3 skivor 76 kr<br>4 skivor 87 kr<br>5 skivor 99 kr<br>6 skivor 102 kr<br>"
           (create-text {})))))

(comment
  (create-text example-record-info))

