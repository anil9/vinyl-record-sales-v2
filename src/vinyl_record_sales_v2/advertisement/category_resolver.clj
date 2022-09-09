(ns vinyl-record-sales-v2.advertisement.category-resolver 
  (:require
    [clojure.string :as s]))

(def product-category
  {:DECADE_50S_60S 210901
   :DECADE_70S 210903
   :DECADE_80S 210905
   :DECADE_90S 210907
   :DECADE_2000 302335
   :ROCK 340414
   :POP 340634
   :JAZZ 340511
   :FOLK 210839
   :COUNTRY 340502
   :CHILDREN 210818
   :CLASSICAL 210810
   :OTHER 210830})

(comment
  (->> ["rock"]
       (map #(if (s/starts-with? % "Children's") "CHILDREN" (s/upper-case %)))
       (map keyword)
       (map #(% product-category))
       (filter some?)
       (first)))
    
(defn category-id [record-info]
  (let [genre (:genre record-info)
        year (:year record-info)
        genre-id (->> genre
                     (map #(if (s/starts-with? % "Children's") "CHILDREN" (s/upper-case %)))
                     (map keyword)
                     (map #(% product-category))
                     (filter some?)
                     (first))
        year-id (when (number? year) 
                  (cond
                    (< 1949 year 1970) (:DECADE_50S_60S product-category)
                    (< 1969 year 1980) (:DECADE_70S product-category)
                    (< 1979 year 1990) (:DECADE_80S product-category)
                    (< 1989 year 2000) (:DECADE_90S product-category)
                    (< 1999 year 3000) (:DECADE_2000 product-category)
                    :else nil))]
    (cond
      (some? genre-id) genre-id
      (some? year-id) year-id
      :else (:OTHER product-category))))
