(ns connect-four.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require
     [clairvoyant.core :refer-macros [trace-forms]]
     [re-frame-tracer.core :refer [tracer]]
     [re-frame.core :as re-frame]))

(trace-forms {:tracer (tracer :color "green")}
(re-frame/register-sub
 :name
 (fn [db]
   (reaction (:name @db))))

(re-frame/register-sub
 :board
 (fn [db _]
   (reaction (:board @db))))

(re-frame/register-sub
 :winner
 (fn [db _]
   (reaction (:winner @db))))

(re-frame/register-sub
 :player
 (fn [db _]
   (reaction (:player @db))))

(re-frame/register-sub
 :turn
 (fn [db _]
   (reaction (:turn @db))))


(re-frame/register-sub
 :error
 (fn [db _]
   (reaction (get @db :error ""))))


(re-frame/register-sub
 :active-panel
 (fn [db _]
   (reaction (:active-panel @db))))

)
