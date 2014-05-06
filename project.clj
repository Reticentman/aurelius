(defproject marcus "0.1.0-SNAPSHOT"
  :description "A collection of quotes from Marcus Aurelius"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.5.0"]
                 [secretary "1.1.0"]
                 [prismatic/schema "0.2.1"]]

  :plugins [[lein-cljsbuild "1.0.2"]]

  :source-paths ["src"]

  :cljsbuild { 
    :builds [{:id "dev"
              :source-paths ["src"]
              :compiler {
                :output-to "marcus.js"
                :output-dir "out"
                :optimizations :none
                         :source-map true}}
             {:id "prod"
              :source-paths ["src"]
              :compiler {
                         :output-to "marcus.js"
                         :optimizations :advanced
                         :pretty-print false
                         :preamble ["react/react.min.js"]
                         :externs ["react/externs/react.js"]}}]})
