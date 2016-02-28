(ns connect-four.handlers
  (:require
   [clairvoyant.core :refer-macros [trace-forms]]
   [re-frame-tracer.core :refer [tracer]]
   [re-frame.core :as re-frame]
   [schema.core :as s]
   [matchbox.core :as m]
   [matchbox.reagent :as mr]
   [connect-four.game :as game]
   [connect-four.db :as db]))

(defn check-and-throw
  "throw an exception is something does not match the
  schema"
  [a-schema db]
  (if-let [problems (s/check a-schema db)]
    (.error js/console problems)))

(defn push-game-state
  [ref game]
  (m/reset-in! ref [:rooms (:id game) :game] game))

(def push-middleware
  (re-frame/after (partial push-game-state db/rooms)))

(def check-middleware
  (re-frame/after (partial check-and-throw db/AppState)))

(def game-middleware
  [check-middleware
   (re-frame/path :game)
   push-middleware
   re-frame/trim-v])

(re-frame/register-handler
 :initialize-db
 check-middleware
 (fn  [_ _]
   (re-frame/dispatch [:connect-firebase])
   (re-frame/dispatch [:watch-rooms])
   db/default-db))

(re-frame/register-handler
 :connect-firebase
 check-middleware
 (fn [db [_ id]]
   (m/auth-info db/rooms)
   db
   ))

(re-frame/register-handler
 :join-game
 [check-middleware]
 (fn [db [_ id]]
   (-> db/rooms
       (m/merge-in! [ :rooms (keyword  id) :players ]  [  (get-in db [:user-id])]))
   (let [ disconn 
         (-> db/rooms
             (m/get-in [:rooms (keyword  id) :game])
             (m/listen-to :value
                          (fn [[_ new]]
                            (.log js/console  new  )
                            (re-frame/dispatch [:update-game new]))))])
   (-> db
       (dissoc :game) 
       (assoc-in [:game-id] id))))

(re-frame/register-handler
 :watch-rooms
 check-middleware
 (fn [db [_ ]]
   (let [disconn
         (-> db/rooms
             (m/get-in [:rooms])
             (m/listen-to :value
                          (fn [[_ new]]
                            (re-frame/dispatch [:update-rooms new]))))])
   db))


(re-frame/register-handler
 :create-room
 check-middleware
 (fn [db [_ name]]
   (let [new-game {:name name
                   :id (str (count (:rooms db)))
                   :game (db/empty-game (str (count (:rooms db)) ))}]
     (-> db/rooms
         (m/reset-in! [:rooms (:id new-game)] new-game))
     db)))

(re-frame/register-handler
 :delete-room
 [check-middleware ]
 (fn [db [_ id]]
   (-> db/rooms
       (m/dissoc-in! [:rooms id] ))
   (-> db
       (update-in [:rooms] dissoc id))))


(re-frame/register-handler
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/register-handler
 :maybe-play
 game-middleware
 (fn [game [row col]]
   (let [play-status (game/valid-play? (:board game)
                                  [row col])]
     (if (and  (= play-status "ok")
               (nil? (:winner game)))
       (do
         (re-frame/dispatch [:play row col])
         game)
       (assoc-in game [:error] play-status)))))

(re-frame/register-handler
 :ai-play
 (fn [db _]
   ))

(re-frame/register-handler
 :update-game
 [check-middleware]
 (fn [db [_ new]]
   (update-in db [:game]  #(merge % new))))

(re-frame/register-handler
 :update-user-id
 check-middleware
 (fn [db [_ new]]
   (assoc-in db [:user-id] new )))


(re-frame/register-handler
 :update-rooms
 [ check-middleware]
 (fn [db [_ new]]
   (let [munged  (reduce
                  (fn [acc r]
                    (assoc acc (str  (:id r)) r))
                  {}
                  new)] 
     (.log js/console (assoc  db :rooms munged))
     (assoc-in db [:rooms] munged))))


(re-frame/register-handler
 :play
 [  game-middleware]
 (fn [game [row  col]]
   (re-frame/dispatch [:switch-slot row col])
   (re-frame/dispatch [:end-turn])
    game))

(re-frame/register-handler
 :end-turn
 game-middleware
 (fn [game _]
   (if (not (empty?  (game/wins (:board game))))
     (assoc-in game [:winner] (:player game))
     (-> game
         (assoc-in [:player]
                   (game/next-player
                    (:player game)))
         (assoc-in [:error] "")
         (update-in [:turn] inc)))))


(re-frame/register-handler
 :switch-slot
 game-middleware
 (fn [game [row col]]
   (assoc-in game [:board row col] (:player game))))




