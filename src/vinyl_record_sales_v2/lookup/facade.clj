(ns vinyl-record-sales-v2.lookup.facade
  (:require [vinyl-record-sales-v2.lookup.core :as l]))

(defn lookup! [catalogue-num extra-title-words]
  (let [release-meta (l/get-release-meta! catalogue-num extra-title-words)
        release-id (:id release-meta)
        title (:title release-meta)]
    (if (nil? (:id release-meta))
      (println "Failed to find a unique release id. You can try adding more title words")
      (assoc (l/get-release-info! release-id) :title title))))

(comment
  (lookup! "2462 150" ["häck"])
  (lookup! "2462 150" []))
