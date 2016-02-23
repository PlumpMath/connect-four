(ns connect-four.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require
     [connect-four.db :as d]
     [clairvoyant.core :refer-macros [trace-forms]]
     [re-frame-tracer.core :refer [tracer]]
     [schema.core :as s]
     [re-frame.core :as re-frame]))


(defn winner-query [db]
  (reaction (get-in @db [:game :winner])))

(defn board-query [db ]
  (reaction (get-in @db [:game :board])))

(defn player-query [db ]
  (reaction (get-in @db [:game :player])))

(defn turn-query [db ]
  (reaction (get-in @db [:game :turn])))

(defn error-query [db ]
  (reaction (get-in @db [:game :error])))

(re-frame/register-sub
 :board
 board-query)

(re-frame/register-sub
 :winner
 winner-query)

(re-frame/register-sub
 :player
 player-query)

(re-frame/register-sub
 :turn
 turn-query)

(re-frame/register-sub
 :error
 error-query)


(re-frame/register-sub
 :active-panel
 (fn [db _]
   (reaction (:active-panel @db))))
