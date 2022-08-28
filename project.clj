(defproject vinyl-record-sales-v2 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [clj-http "3.12.3"]
                 [cheshire "5.11.0"]
                 [org.clojure/tools.reader "1.3.6"]
                 [org.clj-commons/digest "1.4.100"]
                 [org.clj-commons/byte-streams "0.3.1"]]
  :main ^:skip-aot vinyl-record-sales-v2.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
