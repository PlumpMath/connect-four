(ns connect-four.db
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [schema.core :as s]
            [matchbox.reagent :as mr]
            [matchbox.core :as m]))


(def firebase-uri "https://connect-four-cljs.firebaseio.com")

(def rooms (m/connect firebase-uri :rooms))


(s/defschema Slot
  (s/enum :empty :red :blue))

(s/defschema Board
  [[Slot]])

(s/defschema GameState
  {:turn s/Int
   :error s/Str
   :id   s/Str
   :player (s/enum :red :blue)
   (s/optional-key :winner) (s/enum :red :blue)
   :board Board})

(s/defschema Room
  {:name s/Str
   :id   s/Str
   :game GameState
   (s/optional-key  :players) [s/Str]})

(s/defschema RoomsState
  {s/Str Room})

(s/defschema AppState
  {(s/optional-key  :game) GameState
   :active-panel (s/enum :rooms-panel :game-panel
                         :home-panel :about-panel)
   (s/optional-key  :game-id) s/Str
   (s/optional-key  :rooms) RoomsState
   :user-id s/Str})

(s/defn a-row [] :- [Slot]
  (into [] (take 7 (repeat :empty))))

(s/defn a-board [] :- Board
  (into [] (take 6 (repeat (a-row )))))

(s/defn empty-game [id :- s/Str] :- GameState
  {:player :blue
   :turn 0
   :id id
   :error ""
   :board (a-board)})

(s/def default-db :- AppState
  {:active-panel :home-panel
   :user-id "Test"})

(s/def default-rooms :- RoomsState
  {:rooms {"0"
           {:name "a Room"
            :id  "0"
            :game (empty-game "0")
            :players ["you" "me"]}}})
