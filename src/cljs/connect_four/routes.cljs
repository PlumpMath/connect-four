(ns connect-four.routes
    (:require-macros [secretary.core :refer [defroute]])
    (:import goog.History)
    (:require [secretary.core :as secretary]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [accountant.core :as accountant]
              [re-frame.core :as re-frame]))


(def routes
  ["/"  {"" :home-panel
      "rooms" :rooms-panel
      ["game/" :game-id] :game-panel
      "about" :about-panel}])

(defmulti handle :handler)

(defmethod handle :home-panel []
  (re-frame/dispatch [:set-active-panel :home-panel]))

(defmethod handle :rooms-panel []
  (re-frame/dispatch [:set-active-panel :rooms-panel]))

(defmethod handle :game-panel [{:keys [route-params] :as all}]
  (.log js/console all)
  (re-frame/dispatch [:set-active-panel :game-panel])
  (re-frame/dispatch [:join-game (:game-id route-params)]))

(defmethod handle :about-panel []
  (re-frame/dispatch [:set-active-panel :about-panel]))


