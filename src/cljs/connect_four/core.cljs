(ns connect-four.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [connect-four.handlers]
              [connect-four.subs]
              [devtools.core :as devtools]
              [connect-four.routes :as routes]
              [connect-four.views :as views]
              [accountant.core :as accountant]
              [secretary.core :as secretary]
              [bidi.bidi :as bidi]
              [connect-four.config :as config]))

(when config/debug?
  (println "dev mode")
  (devtools/enable-feature! :sanity-hints :dirac)
  (devtools/install!))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])

  (accountant/configure-navigation!
   {:nav-handler (fn [path]
                   (let [route (bidi/match-route routes/routes path)] 
                     (.log js/console route)
                     (.log js/console path)
                     (routes/handle route)))
    :path-exists? (fn [path]
                    (boolean(bidi/match-route routes/routes path)))})

  (accountant/dispatch-current!)
  (mount-root))
