(ns vinyl-record-sales-v2.lookup.facade
  (:require [vinyl-record-sales-v2.lookup.core :as l]))

(defn lookup! [catalogue-num extra-title-words]
  (let [release-id (l/get-release-id! catalogue-num extra-title-words)]
    (if (nil? release-id)
      (println "Failed to find a unique release id. You can try adding more title words")
      (l/get-release-info! release-id))))

(comment
  (lookup! "2462 150" ["häck"]))
