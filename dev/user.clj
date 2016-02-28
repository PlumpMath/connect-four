(ns user
  (:require [figwheel-sidecar.repl-api :as repl]))

(defn start []
  (repl/start-figwheel!) ; idempotent
  (repl/cljs-repl))

(defn stop []
  (repl/stop-figwheel!))
