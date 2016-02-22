(ns connect-four.handlers
  (:require
   [clairvoyant.core :refer-macros [trace-forms]]
   [re-frame-tracer.core :refer [tracer]]
   [re-frame.core :as re-frame]
   [connect-four.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(def next-player
  {:blue :red
   :red :blue
   })

(def ways-to-win
  {:r-diag [1 1]
   :l-diag [1 -1]
   :hori   [0 1]
   :vert   [1 0]})

(defn candidate-moves [board]
  (for [row (range 6)
        col (range 7)
        :when (valid-play? board [row col])]
    [row col]))

(defn do-way [step board start]
  (loop [cur start
         cnt 1]
    (let [next (mapv + cur step)
          cur-slot (get-in board cur)
          next-slot (get-in board next)]
      (if (not= :empty cur-slot)
        (if (= cur-slot next-slot)
          (if (=  cnt 3)
            true
            (recur next (inc cnt))))
        false))))

(defn wins [board]
  (let [stuff
        (for [row (range 6)
              col (range 7)
              [way step] ways-to-win
              :when (= (do-way step board [row col]) true)]
          {:way way :start [row col] :color (get-in board [row col])})]
    stuff))

(defn get-rows-below [board row col]
  (into #{}
        (for [cur-row (range (+ row 1) 6)]
          (get-in board [cur-row col]))))

(defn bottom-row? [board [row col]]
  (let [things (get-rows-below board row col)] 
    (not (contains?  (get-rows-below board row col) :empty ))))

(defn valid-play? [board play]
  (let [target-slot (get-in board play)]
    (if (and  (= target-slot :empty) (bottom-row? board play))
      :ok
      :youre-a-dick
      )))

(re-frame/register-handler
 :maybe-play
 (fn [db [_ row col]]
   (let [play-status (valid-play? (:board db) [row col])]
     (if (and  (= play-status :ok) (empty? (:winner db)))
       (do
         (re-frame/dispatch [:play row col])
         db)
       (assoc-in db [:error] play-status)))))

(re-frame/register-handler
 :ai-play
 (fn [db _]
   
   ))

(re-frame/register-handler
 :play
 (fn [db [_ row col]]
   (re-frame/dispatch [:switch-slot row col])
   (re-frame/dispatch [:end-turn])
    db))

(re-frame/register-handler
 :end-turn
 (fn [db [_]]
   (if (not (empty?  (wins (:board db))))
     (assoc-in db [:winner] (:player db))
     (-> db
         (assoc-in [:player] (next-player (:player db)))
         (assoc-in [:error] "")
         (update-in [:turn] inc)))))


(re-frame/register-handler
 :switch-slot
 (fn [db [_ row col]]
   (assoc-in db [:board row col] (:player db))))




