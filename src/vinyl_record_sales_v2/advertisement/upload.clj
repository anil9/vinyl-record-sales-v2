(ns vinyl-record-sales-v2.advertisement.upload
  (:require [clj-http.client :as client]
            [clojure.string :as s]))

(def sello-token (s/trim-newline (slurp "/home/andreas/sello/api.token")))
(def base-url "https://api.sello.io/v5/")
(def client-map
  {:as :auto
   :headers {:authorization sello-token}
   :content-type :json})

(defn upload-ad! [ad]
  (client/post (str base-url "products")
               (assoc client-map :form-params ad)))


(comment
  (client/get (str base-url "products") (assoc-in client-map [:headers :authorization] sello-token))
  (client/get (str base-url "products/64536005") (assoc-in client-map [:headers :authorization] sello-token)))


(comment
  (def folder-id 1102266)
  (def tradera-id 53501)
  (def example-ad {:texts {:default {:sv {:description "Skick se bilder. Ej provspelad. Kolla gärna in mina andra annonser och passa på att buda in fler LP-skivor, jag samfraktar gärna!<br>Fraktpriser (ca):<br>1 skiva 70 kr<br>2 skivor 73 kr<br>3 skivor 76 kr<br>4 skivor 87 kr<br>5 skivor 99 kr<br>6 skivor 102 kr<br><br>År: 1983<br>Genre: Pop, Folk, World, & Country<br>Style: Vocal, Folk, Schlager<br>Tracklist:<br>Sinatra In Concert 4:50<br>Sommar Som Vilar I Gräset 2:39<br>Om Du Nånsin Kommer Fram Till Samarkand 4:22<br>I Write The Songs 2:42<br>Den Första Gång Jag Såg Dig 3:17<br>Send In The Clowns 4:28<br>Ge Mig En Dag 1:59<br>Den Glade Bagarn 3:00<br>Annies Song 2:37<br>Minnet = Memory 3:50<br>I Left My Heart In San Francisco 3:38<br>Romantik 3:56<br>Jubileumsblues 3:10<br>Kullerullvisan 3:16<br>Ha Det Bra ",
                                          :name "Ludvika Musikkår - Sonja Stjernquist - Ha Det Bra"}}}
                   :purchase_price 0.5,
                   :tax 25,
                   :folder_id folder-id,
                   :prices {(keyword (str tradera-id)) {:auction {:start 25}, :currency "SEK"}}
                   :categories {:default {:id 340634}}
                   :condition "used",
                   :shipping {(keyword (str tradera-id)) {:pickup true :schenker "70.00"}},
                   :quantity 1})
  (upload-ad! example-ad)
  (assoc client-map :form-params example-ad))
