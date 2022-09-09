(ns vinyl-record-sales-v2.advertisement.text-resolver
  (:require [clojure.string :as s]))

(def standard-text "Skick se bilder. Ej provspelad. Kolla gärna in mina andra annonser och passa på att buda in fler LP-skivor, jag samfraktar gärna!<br>Fraktpriser (ca):<br>1 skiva 70 kr<br>2 skivor 73 kr<br>3 skivor 76 kr<br>4 skivor 87 kr<br>5 skivor 99 kr<br>6 skivor 102 kr")

(defn resolve-tracklist [record-info]
  (let [tracks (->> (:tracklist record-info)
                    (map :title)
                    (filter some?)
                    (s/join "<br>"))]
    (if (not-empty tracks) (str "Tracklist:<br>" tracks) nil)))

(defn join-with 
  [prefix coll]
  (cond
    (coll? coll) (when (not-empty coll) (str prefix (s/join ", " coll)))
    (some? coll) (str prefix coll)))

(defn resolve-year [record-info]  
  (join-with "År: " (:year record-info)))

(defn resolve-genre [record-info]  
  (join-with "Genre: " (:genres record-info)))

(defn resolve-style [record-info]  
  (join-with "Style: " (:styles record-info)))

(defn create-text [record-info]
  (->> [(str standard-text "<br>") (resolve-year record-info) (resolve-genre record-info) (resolve-style record-info) (resolve-tracklist record-info)]
       (filter some?)
       (s/join "<br>")))

