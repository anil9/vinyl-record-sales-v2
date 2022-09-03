(ns vinyl-record-sales-v2.advertisement.text-resolver
  (:require [clojure.string :as s]))

(def standard-text "Skick se bilder. Ej provspelad. Kolla gärna in mina andra annonser och passa på att buda in fler LP-skivor, jag samfraktar gärna!<br>Fraktpriser (ca):<br>1 skiva 70 kr<br>2 skivor 73 kr<br>3 skivor 76 kr<br>4 skivor 87 kr<br>5 skivor 99 kr<br>6 skivor 102 kr")

(defn resolve-tracklist [record-info]
  (let [tracks (->> (:tracklist record-info)
                    (map :title)
                    (filter some?)
                    (s/join "<br>"))]
    (if (not-empty tracks) (str "Tracklist:<br>" tracks) nil)))

(comment
 (resolve-tracklist {:tracklist [{:position "A1", :type_ "track", :title "110 Meter Häck", :duration ""}
                                 {:position "A2", :type_ "track", :title "Hur Man Än Sig Vänder", :duration ""}
                                 {:position "A3", :type_ "track", :title "Ett Jäkla System", :duration ""}
                                 {:position "A4", :type_ "track", :title "Kontaktannons", :duration ""}
                                 {:position "A5", :type_ "track", :title "I Hemmets Lugna...", :duration ""}
                                 {:position "B1", :type_ "track", :title "Sweet Jenny", :duration ""}
                                 {:position "B2", :type_ "track", :title "Frisk Trots Allt", :duration ""}
                                 {:position "B3", :type_ "track", :title "Kärlek, Solsken Och Fjong", :duration ""}
                                 {:position "B4", :type_ "track", :title "En Provare", :duration ""}]}))
(defn resolve-year [record-info]  
  (let [year (:year record-info)]
    (if year (str "År: " year))))

(defn resolve-genre [record-info]  
  (let [genre (:genres record-info)]
    (if (not-empty genre) (str "Genre: " (s/join ", " genre)))))

(defn resolve-style [record-info]  
  (let [style (:styles record-info)]
    (if (not-empty style) (str "Style: " (s/join ", " style)))))

(defn create-text [record-info]
  (s/join "<br>" [standard-text (resolve-year record-info) (resolve-genre record-info) (resolve-style record-info) (resolve-tracklist record-info)]))

(declare example-record-info)

(comment 
  (create-text example-record-info))

(def example-record-info
  {:formats [{:name "Vinyl", :qty "1", :descriptions ["LP"]}],
   :blocked_from_sale false,
   :thumb "https://i.discogs.com/Y9s5wZOxcsMVzJ9pzBqBAeeVeRqA2A8fRLL33TGfnXY/rs:fit/g:sm/q:40/h:150/w:150/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTExMjA4/MzUyLTE1MTE4OTEz/OTgtNzY1NC5qcGVn.jpeg",
   :labels [{:name "Polydor",
             :catno "2462 150",
             :entity_type "1",
             :entity_type_name "Label",
             :id 1610,
             :resource_url "https://api.discogs.com/labels/1610",
             :thumbnail_url "https://i.discogs.com/WynEB1Bybc-IlhhO3JS10_MZabdALkkwmkmrZ2SkTNY/rs:fit/g:sm/q:40/h:600/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9MLTE2MTAt/MTU4ODY3Mzk0Ny0x/NjI2LmpwZWc.jpeg"}],
   :extraartists [],
   :format_quantity 1,
   :identifiers [],
   :estimated_weight 230,
   :community {:have 7,
               :want 1,
               :rating {:count 1, :average 4.0},
               :submitter {:username "StenkakorTrbg", :resource_url "https://api.discogs.com/users/StenkakorTrbg"},
               :contributors [{:username "StenkakorTrbg", :resource_url "https://api.discogs.com/users/StenkakorTrbg"}
                              {:username "nadsat", :resource_url "https://api.discogs.com/users/nadsat"}
                              {:username "Uffe2", :resource_url "https://api.discogs.com/users/Uffe2"}],
               :data_quality "Needs Vote",
               :status "Accepted"},
   :series [],
   :images [{:type "secondary",
             :uri "https://i.discogs.com/zVKb49tjCdgKikx7ctF9iF5nJ8E92gqkffWMZayNDzc/rs:fit/g:sm/q:90/h:598/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTExMjA4/MzUyLTE1MTE4OTEz/OTgtNzY1NC5qcGVn.jpeg",
             :resource_url "https://i.discogs.com/zVKb49tjCdgKikx7ctF9iF5nJ8E92gqkffWMZayNDzc/rs:fit/g:sm/q:90/h:598/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTExMjA4/MzUyLTE1MTE4OTEz/OTgtNzY1NC5qcGVn.jpeg",
             :uri150 "https://i.discogs.com/Y9s5wZOxcsMVzJ9pzBqBAeeVeRqA2A8fRLL33TGfnXY/rs:fit/g:sm/q:40/h:150/w:150/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTExMjA4/MzUyLTE1MTE4OTEz/OTgtNzY1NC5qcGVn.jpeg",
             :width 600,
             :height 598}
            {:type "secondary",
             :uri "https://i.discogs.com/YNzxd5-y_4_dK0OBRwN4wmopb4A3VgbTUq56khY3to0/rs:fit/g:sm/q:90/h:593/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTExMjA4/MzUyLTE1MTE4OTEz/OTgtNTUwNi5qcGVn.jpeg",
             :resource_url "https://i.discogs.com/YNzxd5-y_4_dK0OBRwN4wmopb4A3VgbTUq56khY3to0/rs:fit/g:sm/q:90/h:593/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTExMjA4/MzUyLTE1MTE4OTEz/OTgtNTUwNi5qcGVn.jpeg",
             :uri150 "https://i.discogs.com/NMyDQJ_xWOu0QMsP2mASBZWhFMyiNlSCqHhw4dg-5lQ/rs:fit/g:sm/q:40/h:150/w:150/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTExMjA4/MzUyLTE1MTE4OTEz/OTgtNTUwNi5qcGVn.jpeg",
             :width 600,
             :height 593}],
   :date_added "2017-11-28T08:48:50-08:00",
   :genres ["Non-Music"],
   :resource_url "https://api.discogs.com/releases/11208352",
   :title "110 Meter Häck",
   :year 1974,
   :released "1974",
   :status "Accepted",
   :id 11208352,
   :released_formatted "1974",
   :styles ["Comedy"],
   :companies [],
   :lowest_price 3.13,
   :tracklist [{:position "A1", :type_ "track", :title "110 Meter Häck", :duration ""}
               {:position "A2", :type_ "track", :title "Hur Man Än Sig Vänder", :duration ""}
               {:position "A3", :type_ "track", :title "Ett Jäkla System", :duration ""}
               {:position "A4", :type_ "track", :title "Kontaktannons", :duration ""}
               {:position "A5", :type_ "track", :title "I Hemmets Lugna...", :duration ""}
               {:position "B1", :type_ "track", :title "Sweet Jenny", :duration ""}
               {:position "B2", :type_ "track", :title "Frisk Trots Allt", :duration ""}
               {:position "B3", :type_ "track", :title "Kärlek, Solsken Och Fjong", :duration ""}
               {:position "B4", :type_ "track", :title "En Provare", :duration ""}],
   :data_quality "Needs Vote",
   :uri "https://www.discogs.com/release/11208352-Nils-Ahlroth-110-Meter-H%C3%A4ck",
   :artists [{:name "Nils Ahlroth",
              :anv "",
              :join "",
              :role "",
              :tracks "",
              :id 2137976,
              :resource_url "https://api.discogs.com/artists/2137976",
              :thumbnail_url "https://i.discogs.com/wQY9LP4Do2oAF_Tfdy4PxayZcjw9Vv1koEMZIzrui_4/rs:fit/g:sm/q:40/h:540/w:400/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTIxMzc5/NzYtMTU3NzM4Mjc2/OS02MTAxLmpwZWc.jpeg"}],
   :date_changed "2021-01-10T15:00:39-08:00",
   :artists_sort "Nils Ahlroth",
   :country "Sweden",
   :num_for_sale 3})
