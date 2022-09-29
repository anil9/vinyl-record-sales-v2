(ns vinyl-record-sales-v2.core
  (:require [vinyl-record-sales-v2.lookup.facade :as lookup]
            [vinyl-record-sales-v2.advertisement.creation :as ad-creation]
            [vinyl-record-sales-v2.advertisement.upload :as ad-upload])
  (:gen-class))


(defn -main
  ([] (println "Zero args to main function"))
  ([catalogue-num & extra-title-words]
   (let [record-info (lookup/lookup! catalogue-num extra-title-words)]
      (ad-upload/upload-ad! (ad-creation/create-ad record-info)))))
(def catalogue-num "2379 044")

(comment
  (slurp (format "http://api.discogs.com/database/search?catno=%s" catalogue-num))
  (-main)
  (-main "2462 150" "häck"))

  
