(ns vinyl-record-sales-v2.advertisement.creation
  (:require [clojure.string :as s]
            [clj-http.client :as client]
            [vinyl-record-sales-v2.advertisement.text-resolver :as text]
            [vinyl-record-sales-v2.advertisement.category-resolver :as category]))


(def sello-token (s/trim-newline (slurp "/home/andreas/sello/api.token")))
(def base-url "https://api.sello.io/v5/")
(def folder-id 1102266)
(def tradera-id 53501)

(def client-map
  {:as :auto
   :headers {:authorization sello-token}
   :content-type :json}) 

(comment
  (client/get (str base-url "products") (assoc-in client-map [:headers :authorization] sello-token))
  (client/get (str base-url "products/64536005") (assoc-in client-map [:headers :authorization] sello-token)))

(defn upload-ad! [ad]
  (client/post (str base-url "products")
               (assoc client-map :form-params ad)))
(declare example-ad)
(comment
  (upload-ad! example-ad)
  (assoc client-map :form-params example-ad))

(def template-ad
  {:purchase_price 0.5,
   :tax 25,
   :folder_id folder-id,
   :prices {(keyword (str tradera-id)) {:auction {:start 25}, :currency "SEK"}}
   :categories {:default {:id 340634}}
   :condition "used",
   :shipping {(keyword (str tradera-id)) {:pickup true :schenker "70.00"}},
   :quantity 1})


(defn create-ad [record-info]
  ;(text/create-text record-info))
  (-> template-ad
      (assoc-in [:texts :default :sv :description] (text/create-text record-info)) 
      (assoc-in [:texts :default :sv :name] (:title record-info)) 
      (assoc-in [:categories :default :id] (category/category-id record-info))))
      
  

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

; :body {:properties [],
;        :submitter_id nil,
;        :quantity_threshold nil,
;        :errors {:validation []},
;        :private_name "",
;        :supplier_product_number nil,
;        :integrations {:53501 {:active false, :extra "", :item_id nil, :quantity nil}},
;        :images [{:id 334157857,
;                  :url_large "https://images.sello.io/products/acc/46613/64536005/1aa2e26994491629f77180959416c4a6.png",
;                  :url_small "https://images.sello.io/products/acc/46613/64536005/10f8373e73a2abdd2f3c4ab3827d73ec.png",
;                  :url_original "https://res.cloudinary.com/dairr8mgm/image/upload/v1635886537/xnoqilsdf9gmgsq54upp.jpg",
;                  :url_transparent nil,
;                  :url_ml "https://images.sello.io/products/acc/46613/64536005/79e346b07b8c2ef34254b54744f19e23.png",
;                  :type nil
;                  }],
;        :texts {:53501 {:sv {:template_id "3393796", :use "sv"}},
;                :default {:sv {:bulletpoints [],
;                               :description "Skick se bilder. Ej provspelad. Kolla gärna in mina andra annonser och passa på att buda in fler LP-skivor, jag samfraktar gärna!<br>Fraktpriser (ca):<br>1 skiva 70 kr<br>2 skivor 73 kr<br>3 skivor 76 kr<br>4 skivor 87 kr<br>5 skivor 99 kr<br>6 skivor 102 kr<br><br>År: 1983<br>Genre: Pop, Folk, World, & Country<br>Style: Vocal, Folk, Schlager<br>Tracklist:<br>Sinatra In Concert 4:50<br>Sommar Som Vilar I Gräset 2:39<br>Om Du Nånsin Kommer Fram Till Samarkand 4:22<br>I Write The Songs 2:42<br>Den Första Gång Jag Såg Dig 3:17<br>Send In The Clowns 4:28<br>Ge Mig En Dag 1:59<br>Den Glade Bagarn 3:00<br>Annies Song 2:37<br>Minnet = Memory 3:50<br>I Left My Heart In San Francisco 3:38<br>Romantik 3:56<br>Jubileumsblues 3:10<br>Kullerullvisan 3:16<br>Ha Det Bra ",
;                               :meta_description "",
;                               :meta_keywords "",
;                               :name "Ludvika Musikkår - Sonja Stjernquist - Ha Det Bra",
;                               :title ""}}},
;        :manufacturer_no nil,
;        :image_settings {:53501 {:image_order [334157857 334157858 334157859 334157860 334157861], :skip []}},
;        :manufacturer_name "",
;        :purchase_price 0.5,
;        :source "sello",
;        :sold 0,
;        :tax 25,
;        :volume 0,
;        :group_id 45202804,
;        :updated_at "2022-08-27T21:51:06.000Z",
;        :folder_id 1083770,
;        :prices {:53501 {:auction {:buynow nil, :reserve nil, :start 25}, :currency "SEK", :store nil, :has_campaign false},
;                 :default {:adjust false, :calculate false, :minimum_price nil, :recommended_price nil, :target_price nil, :has_campaign false}},
;        :unsold 6,
;        :categories {:53501 [{:crumb "Musik > Vinyl > Pop > Övrigt",
;                              :id "340634",
;                              :name "Övrigt",
;                              :path ["0" "21" "2108" "340559" "340634"],
;                              :is_leaf true}],
;                     :default {:crumb "Musik &gt; Vinyl &gt; Pop &gt; Ã–vrigt", :id 340634, :name "Ã–vrigt", :path [21 2108 340559 340634]}},
;        :last_sold nil,
;        :weight 0,
;        :supplier_id nil,
;        :id 64536005,
;        :condition "used",
;        :notes "",
;        :brand_id nil,
;        :delivery_times {:default {:max nil, :min nil}},
;        :tax_class nil,
;        :manufacturer nil,
;        :brand_name "",
;        :source_category "",
;        :shipping {:53501 {:bussgods nil, :dhl nil, :other nil, :pickup true, :posten nil, :postnord_box nil, :postnord_letter nil, :schenker "70.00"}},
;        :stock_location "10",
;        :quantity 1,
;        :version "6395152241a03f92abe40994560f9a985592edbcf715136bde4d3935ef11f9aa",
;        :quantity_ordered 0,
;        :created_at "2021-11-02T21:14:00.000Z",
;        :private_reference "",
;        :volume_unit "m3"}
