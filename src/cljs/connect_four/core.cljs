(ns connect-four.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [connect-four.handlers]
              [connect-four.subs]
              [connect-four.routes :as routes]
              [connect-four.views :as views]
              [connect-four.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init [] 
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
