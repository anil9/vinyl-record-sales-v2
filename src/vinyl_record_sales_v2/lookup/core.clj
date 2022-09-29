(ns vinyl-record-sales-v2.lookup.core
  (:require [clj-http.client :as client]
            [clojure.string :as s]))

(def base "https://api.discogs.com/")
(def database-search "database/search")
(defn release-search [r] (str "releases/" r))
(def user-agent "MyDiscogsClient/1.0 +http://mydiscogsclient.org")

(def discogs-token (str "Discogs token=" (s/trim-newline (slurp "/home/andreas/discogs/api.token"))))

(def client-map
  {:headers {:user-agent user-agent}
   :as :auto})

(defn- create-request
  ([auth-token]
   (assoc-in client-map [:headers :authorization] auth-token))
  ([auth-token query-params]
   (-> (create-request auth-token)
       (assoc :query-params query-params))))

(defn- discogs-url [url]
  (str base url))

(defn- query-discogs! [url request]
  (client/get (discogs-url url)
              request))

(defn contain-all-words? [text words]
  (every? (into #{} (re-seq #"[a-zåäö]+" (s/lower-case text))) words))

(defn releases-matching-title-words
  "Use catalogue-num (and extra title words) to find the correct release-id"
  [results extra-title-words]
  (let [title-words (map s/lower-case extra-title-words)]
      (->> results
                   (filter #(= "release" (:type %)))
                   (filter #(contain-all-words? (:title %) title-words)))))
    

(comment
  (def catalogue-num "2379 044")
  (def disc-results (get-in (query-discogs! database-search (create-request discogs-token {:catno catalogue-num})) [:body :results])
    (prn disc-results))
  (releases-matching-title-words disc-results ["bröder" "skål"])
  (releases-matching-title-words disc-results ["skål"])
  (releases-matching-title-words disc-results ["wahlgren"])
  (re-seq #"[a-zåäö]+" "Test string with å ä and ö"))

(defn get-release-meta! [catalogue-num extra-title-words]
  (let [catno-response (get-in (query-discogs! database-search (create-request discogs-token {:catno catalogue-num})) [:body :results])
        releases-matching-title-words (releases-matching-title-words catno-response extra-title-words)]
    (if (= 1 (count (distinct (map #(:title %) releases-matching-title-words))))
      (select-keys (first releases-matching-title-words) [:id :title])
      (println (str "Cant decide on a unique release-id. Try adding more title words. Available titles were " (seq (map :title releases-matching-title-words)))))))


(comment
  (def catalogue-num "2379 044")
  (get-release-meta! catalogue-num ["skål"])
  (get-release-meta! catalogue-num ["wahlgren"]))

(defn get-release-info! [release-id]
  (:body (query-discogs! (release-search release-id) (create-request discogs-token))))

(comment
  (def release-response (query-discogs! (release-search 14237528) (create-request discogs-token)))
  (query-discogs! (release-search 14237528) (create-request discogs-token))
  (get-in release-response [:body :title])
  (:body release-response)
  (get-release-info! 11208352))

(comment
  (def catalogue-num "2379 044")
  (slurp (format "http://api.discogs.com/database/search?catno=%s" catalogue-num))
  (client/get (str base database-search)
              {:query-params {"catno" catalogue-num}
               :headers {"User-Agent" user-agent
                         "Authorization" discogs-token}
               ;:debug true
               :as :json})
  (:body (query-discogs! database-search (create-request discogs-token {:catno catalogue-num})))
  (get-in (query-discogs! database-search (create-request discogs-token {:catno catalogue-num})) [:body :results])
  (get-in (query-discogs! database-search (create-request discogs-token {:catno "2462 150"})) [:body :results]))

; real world result below
(comment
  {:pagination {:page 1, :pages 1, :per_page 50, :items 4, :urls {}},
   :results [{:formats [{:name "Vinyl", :qty "1", :descriptions ["LP" "Album"]}],
              :genre ["Pop" "Folk, World, & Country"],
              :thumb "https://i.discogs.com/SK3-r3-4Sy9yVtTzte9bYt1fR2L7oGafUX3t2r7ih9Y/rs:fit/g:sm/q:40/h:150/w:150/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTE0MjM3/NTI4LTE1NzA0NjY5/NTMtOTczNC5qcGVn.jpeg",
              :catno "2379 044",
              :format ["Vinyl" "LP" "Album"],
              :format_quantity 1,
              :barcode ["NCB" "2379 044 A 10 P72 SV" "2379 044 B 10 P72 SV"],
              :community {:want 2, :have 25},
              :master_id 1617621,
              :master_url "https://api.discogs.com/masters/1617621",
              :cover_image "https://i.discogs.com/xZtQ_CkXGEv5qf0Abo5XatVDhhRsSHq9Egd51zblPAQ/rs:fit/g:sm/q:90/h:605/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTE0MjM3/NTI4LTE1NzA0NjY5/NTMtOTczNC5qcGVn.jpeg",
              :type "release",
              :resource_url "https://api.discogs.com/releases/14237528",
              :title "Nils Ferlin, Thorstein Bergman, Hans Wahlgren - En Skål, I Bröder...",
              :style ["Folk"],
              :year "1972",
              :label ["Polydor" "Polydor AB" "EMI Studios, Stockholm" "SIB-Tryck, Tumba"],
              :id 14237528,
              :uri "/Nils-Ferlin-Thorstein-Bergman-Hans-Wahlgren-En-Sk%C3%A5l-I-Br%C3%B6der/release/14237528",
              :country "Sweden",
              :user_data {:in_wantlist false, :in_collection false}}
             {:formats [{:name "Vinyl", :qty "1", :descriptions ["LP" "Album"]}],
              :genre ["Pop" "Folk, World, & Country"],
              :thumb "https://i.discogs.com/U5qrwoBXse6CYk7TKFPBzC-6kkNrC2tKIPB3mZilfDE/rs:fit/g:sm/q:40/h:150/w:150/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTEwMzkx/NTktMTQ4MTQwNzA1/My04NzY0LmpwZWc.jpeg",
              :catno "2379 044",
              :format ["Vinyl" "LP" "Album"],
              :format_quantity 1,
              :barcode ["NCB" "2379 044 A 10 P72 SV" "2379 044 B 10 P72 SV"],
              :community {:want 3, :have 106},
              :master_id 1617621,
              :master_url "https://api.discogs.com/masters/1617621",
              :cover_image "https://i.discogs.com/rVAPcaAZ-znDuBV64WTiYfdgphZwPyU4G7uXLlmPQHw/rs:fit/g:sm/q:90/h:600/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTEwMzkx/NTktMTQ4MTQwNzA1/My04NzY0LmpwZWc.jpeg",
              :type "release",
              :resource_url "https://api.discogs.com/releases/1039159",
              :title "Thorstein Bergman - En Skål, I Bröder...",
              :style ["Folk"],
              :year "1972",
              :label ["Polydor" "Polydor AB" "EMI Studios, Stockholm" "SIB-Tryck, Tumba"],
              :id 1039159,
              :uri "/Thorstein-Bergman-En-Sk%C3%A5l-I-Br%C3%B6der/release/1039159",
              :country "Sweden",
              :user_data {:in_wantlist false, :in_collection false}}
             {:genre ["Pop" "Folk, World, & Country"],
              :thumb "https://i.discogs.com/SK3-r3-4Sy9yVtTzte9bYt1fR2L7oGafUX3t2r7ih9Y/rs:fit/g:sm/q:40/h:150/w:150/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTE0MjM3/NTI4LTE1NzA0NjY5/NTMtOTczNC5qcGVn.jpeg",
              :catno "2379 044",
              :format ["Vinyl" "LP" "Album"],
              :barcode ["NCB" "2379 044 A 10 P72 SV" "2379 044 B 10 P72 SV"],
              :community {:want 5, :have 131},
              :master_id 1617621,
              :master_url "https://api.discogs.com/masters/1617621",
              :cover_image "https://i.discogs.com/xZtQ_CkXGEv5qf0Abo5XatVDhhRsSHq9Egd51zblPAQ/rs:fit/g:sm/q:90/h:605/w:600/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9SLTE0MjM3/NTI4LTE1NzA0NjY5/NTMtOTczNC5qcGVn.jpeg",
              :type "master",
              :resource_url "https://api.discogs.com/masters/1617621",
              :title "Nils Ferlin, Thorstein Bergman, Hans Wahlgren - En Skål, I Bröder...",
              :style ["Folk"],
              :year "1972",
              :label ["Polydor" "Polydor AB" "EMI Studios, Stockholm" "SIB-Tryck, Tumba"],
              :id 1617621,
              :uri "/Nils-Ferlin-Thorstein-Bergman-Hans-Wahlgren-En-S"}
             {:resource_url "https://api.discogs.com/releases/12532440"
              :title "Various - Bravo Dance Serie 6",
              :style ["Europop" "Hip-House" "Trance" "Euro House" "Eurodance" "Hip Hop" "Downtempo" "Happy Hardcore" "Italodance" "Progressive Trance"],
              :year "1998",
              :label ["Zomba Music" "Bravo Dance Serie" "Zomba Records" "Zomba Records" "Zomba Music"],
              :id 12532440,
              :uri "/Various-Bravo-Dance-Serie-6/release/12532440",
              :country "Europe",
              :user_data {:in_wantlist false, :in_collection false}}]})
