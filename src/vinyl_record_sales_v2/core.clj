(ns vinyl-record-sales-v2.core
  (:gen-class))

(defn -main
  [& args]
  (println "Hello, World!"))

(def catalogue-num "2379 044")

(comment
  (slurp (format "http://api.discogs.com/database/search?catno=%s" catalogue-num)))
  
