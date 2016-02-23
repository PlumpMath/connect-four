(ns connect-four.views
  (:require-macros  [reagent.ratom  :refer [reaction]])
  (:require
   [clairvoyant.core :refer-macros [trace-forms]]
   [clairvoyant.core :as c]
   [re-frame-tracer.core :refer [tracer]]
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]))

;; home


(enable-console-print!)
(defn game-title []
    (fn []
      [re-com/title
       :label (str "Awesome Connect Four game")
       :level :level1]))

(def slot-color
  {:empty "white"
   :red    "red"
   :blue   "blue"})

(defn slot [& {:keys [row col size]
               :or {size 10
                    row 0
                    col 0}}]
  (let [board (re-frame/subscribe [:board])
        slot-value (reaction (get-in @board [row col]))]
    (fn slot-render []
      (let [size-str (str size)
            point-str (str (/ size 2))]
        [re-com/box
         :child [:div {:id "first"
                       :on-click #(re-frame/dispatch [:maybe-play row col])}
                 [:svg {:width size-str :height size-str}
                  [:circle {:cx point-str
                            :cy point-str
                            :stroke "black"
                            :stroke-width "2"
                            :fill (get-in slot-color [@slot-value] :brown)
                            :r point-str
                            }]]]]))))

(defn slot-row [& {:keys [row]
                   :or {row 0}}]
  (re-com/h-box
   :gap "5px"
   :children
   (for [col (range 7)]
     [slot :col col :row row :size 40])))


(defn board []
  (re-com/v-box
   :gap "5px"
   :children
   (for [row (range 6)]
     [slot-row :row row])))

(defn game-data []
  (let [player (  re-frame/subscribe [:player])
        turn   ( re-frame/subscribe [:turn])
        winner ( re-frame/subscribe [:winner])
        error  (re-frame/subscribe [:error])] 
    (fn player-stats [] 
      [re-com/v-box
       :children
       [[re-com/title
         :label "Stats"
         :level :level1]
        [re-com/label :label (str "Currnet Player " @player)]
        [re-com/label :label (str "Currnet Turn " @turn)]
        [re-com/label :label (str "Winner " @winner)]
        [re-com/label :label (str @error)]]]))
  )

(defn game-panel []
  [re-com/v-box
   :gap "1em"
   :children [[game-title]
              [re-com/h-box
               :gap "5em"
               :children [ 
                          [board]
                          [game-data]]]]])

(defn rooms-link []
  [re-com/hyperlink-href
   :label "go to rooms"
   :href "#/rooms"])

(defn game-link []
  [re-com/hyperlink-href
   :label "go to game"
   :href "#/game"])



(defn home-panel []
  [re-com/v-box
   :gap "1em"
   :children [[game-title]
              [rooms-link]
              [game-link]
              ]])


;; about

(defn about-title []
  [re-com/title
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title] [link-to-home-page]]])


;; main

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :game-panel [] [game-panel])
(defmethod panels :rooms-panel [] [about-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [re-com/v-box
       :height "100%"
       :children [(panels @active-panel)]])))


