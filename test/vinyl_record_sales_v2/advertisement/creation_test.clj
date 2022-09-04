(ns vinyl-record-sales-v2.advertisement.creation-test
  (:require [clojure.test :refer :all]
            [vinyl-record-sales-v2.advertisement.creation :refer :all]))

(def example-record-info
  {:genres ["Non-Music"],
   :title "110 Meter Häck",
   :year 1974,
   :styles ["Comedy"],
   :tracklist [{:position "A1", :type_ "track", :title "110 Meter Häck", :duration ""}
               {:position "A2", :type_ "track", :title "Hur Man Än Sig Vänder", :duration ""}]})
(def expected-ad {:texts {:default {:sv {:description "Skick se bilder. Ej provspelad. Kolla gärna in mina andra annonser och passa på att buda in fler LP-skivor, jag samfraktar gärna!<br>Fraktpriser (ca):<br>1 skiva 70 kr<br>2 skivor 73 kr<br>3 skivor 76 kr<br>4 skivor 87 kr<br>5 skivor 99 kr<br>6 skivor 102 kr<br><br>År: 1974<br>Genre: Non-Music<br>Style: Comedy<br>Tracklist:<br>110 Meter Häck<br>Hur Man Än Sig Vänder",
                                         :name "110 Meter Häck"}}}
                  :purchase_price 0.5,
                  :tax 25,
                  :folder_id folder-id,
                  :prices {(keyword (str tradera-id)) {:auction {:start 25}, :currency "SEK"}}
                  :categories {:default {:id 340634}}
                  :condition "used",
                  :shipping {(keyword (str tradera-id)) {:pickup true :schenker "70.00"}},
                  :quantity 1})

(deftest create-ad-test
        (is (= expected-ad (create-ad example-record-info)))) 
