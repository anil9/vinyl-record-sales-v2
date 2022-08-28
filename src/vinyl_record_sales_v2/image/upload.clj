(ns vinyl-record-sales-v2.image.upload
 (:require [clj-http.client :as client]
           [clojure.java.io :as io]
           [clojure.string :as s]
           [clj-commons.digest :as digest]))

(def cloud-name "dairr8mgm")
(def api-key "571648665453141")
(def cloudinary-url (str "https://api.cloudinary.com/v1_1/" cloud-name "/image/upload"))

(def cloudinary-token (s/trim-newline (slurp "/home/andreas/cloudinary/api.token")))
(declare gen-signature)

(defn vectorize-entries [m]
  (mapv (fn [[k v]] {:name (name k) :content (if (number? v) (str v) v)}) m))

(defn upload-image! [image-path]
  (let [params {:timestamp (quot (System/currentTimeMillis) 1000)
                :api_key api-key
                :file (io/file image-path)}
        signature (gen-signature params cloudinary-token)
        request {:multipart (-> params
                                (assoc :signature signature)
                                (vectorize-entries))
                 :as :auto}]
    (client/post cloudinary-url request)))

(comment
  (upload-image! "/home/andreas/Downloads/1.jpg")

; How to generate a signature https://cloudinary.com/documentation/upload_images#generating_authentication_signatures

;   By default, Cloudinary supports both SHA-1 and SHA-256 digests for validation, and you can use either. 
;    Create a string with the parameters used in the POST request to Cloudinary:
;        All parameters added to the method call should be included except: file, cloud_name, resource_type and your api_key.
;        Add the timestamp parameter.
;        Sort all the parameters in alphabetical order.
;        Separate the parameter names from their values with an = and join the parameter/value pairs together with an &.
;    Append your API secret to the end of the string.
;    Create a hexadecimal message digest (hash value) of the string using an SHA cryptographic function.))
 (defn gen-signature [req secret]
  (->> (dissoc req :file :api_key :cloud_name :resource-type)
       (sort-by first)
       (map (fn [[k v]] (str (name k) "=" v)))
       (s/join "&") 
       (#(str % secret))
       (digest/sha1)))) 
