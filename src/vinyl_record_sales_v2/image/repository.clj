(ns vinyl-record-sales-v2.image.repository 
  (:require
    [clj-http.client :as client]
    [clojure.string :as s]))


(def repo-url "http://localhost:3428")

(def client-map
  {:as :auto
   :content-type :json})

(defn store-url! [url]
  (client/post (s/join "/" [repo-url "url"])
              (assoc client-map :form-params {:url url})))

(defn peek-url! []
  (:body (client/get (s/join "/" [repo-url "url"]))))

(defn delete-url! [id]
  (client/delete (s/join "/" [repo-url "url"])
              (assoc client-map :query-params {:id id})))
(comment
  (store-url! repo-url)
  (peek-url!)
  (delete-url! 14))
  
