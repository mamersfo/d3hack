(defproject d3hack "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]
                 [om "0.7.1"]
                 [cljs-http "0.1.14"]]
  :bower-dependencies [[d3 "3.4.11"]
                       [topojson "1.6.18"]
                       [react "0.9.0"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-bower "0.5.1"]]
  :cljsbuild {:builds [{:source-paths ["src"]
                        :compiler {:output-to "resources/public/main.js"
                                   :output-dir "resources/public/out"
                                   :optimizations :none
                                   :source-map true}}]})
