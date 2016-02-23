(defproject connect-four "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [reagent "0.5.1"]
                 [re-frame "0.6.0"]
                 [org.clojars.stumitchell/clairvoyant "0.2.0"]
                 [day8/re-frame-tracer "0.1.0-SNAPSHOT"]
                 [re-com "0.7.0"]
                 [binaryage/devtools "0.5.2"]
                 [com.cemerick/piggieback "0.2.1"]
                 [figwheel-sidecar "0.5.0-1"]
                 [matchbox "0.0.8-SNAPSHOT"]
                 [prismatic/schema "1.0.5"]
                 [secretary "1.2.3"]
                 [garden "1.3.0"]]

  :min-lein-version "2.5.3"
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :source-paths ["src/clj" "src/cljs" "dev"]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-2"]
            [lein-garden "0.2.6"]
            [lein-doo "0.1.6"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"
                                    "resources/public/css/compiled"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :garden {:builds [{:id "screen"
                     :source-paths ["src/clj"]
                     :stylesheet connect-four.css/screen
                     :compiler {:output-to "resources/public/css/compiled/screen.css"
                                :pretty-print? true}}]}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:on-jsload "connect-four.core/mount-root"}
                        :compiler {:main connect-four.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "test"
                        :source-paths ["src/cljs" "test/cljs"]
                        :compiler {:output-to "resources/public/js/compiled/test.js"
                                   :main connect-four.runner
                                   :optimizations :none}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main connect-four.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :closure-defines {goog.DEBUG false}
                                   :pretty-print false}}]})
