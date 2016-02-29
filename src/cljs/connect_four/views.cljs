(ns connect-four.views
  (:require-macros  [reagent.ratom  :refer [reaction]])
  (:require
   [reagent.core :as reagent]
   [re-frame-tracer.core :refer [tracer]]
   [re-frame.core :as re-frame]
   [accountant.core :as accountant]
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

(defn slot [& {:keys [row col size interactive]
               :or {size 10
                    row 0
                    interactive true
                    col 0}}]
  (let [board (re-frame/subscribe [:board])
        slot-value (reaction (get-in @board [row col]))]
    (fn slot-render []
      (let [size-str (str size)
            point-str (str (/ size 2))]
        [re-com/box
         :child [:div {:id "first"
                       :on-click #(when interactive
                                     (re-frame/dispatch [:maybe-play row col]))}
                 [:svg {:width size-str :height size-str}
                  [:circle {:cx point-str
                            :cy point-str
                            :stroke "black"
                            :stroke-width "2"
                            :fill (get-in slot-color [@slot-value] :brown)
                            :r point-str
                            }]]]]))))

(defn slot-row [& {:keys [row size interactive]
                   :or {row 0
                        size 40
                        interactive true}}]
  (re-com/h-box
   :gap "5px"
   :children
   (for [col (range 7)]
     [slot
      :col col
      :row row
      :size size
      :interactive interactive])))


(defn board [& {:keys [size interactive]
                :or {size 40
                     interactive true}}]
  (re-com/v-box
   :gap "5px"
   :children
   (for [row (range 6)]
     [slot-row
      :row row
      :size size
      :interactive interactive])))

(defn game-data []
  (let [player (re-frame/subscribe [:player])
        turn   (re-frame/subscribe [:turn])
        winner (re-frame/subscribe [:winner])
        game-id (re-frame/subscribe [:game-id])
        error  (re-frame/subscribe [:error])]
    (fn player-stats []
      [re-com/v-box
       :children
       [[re-com/title
         :label "Stats"
         :level :level1]
        [re-com/label :label (str "Currnet Player " @player)]
        [re-com/label :label (str "Currnet Turn " @turn)]
        (when (not (nil? @winner))
          [re-com/label :label (str "Winner " @winner)]
          [re-com/button :label "delete this game"
           :on-click #(do
                        (accountant/navigate! "/")
                        (re-frame/dispatch [:delete-room @game-id]))])
        [re-com/label :label (str @error)]]]))
  )

(defn game-panel []
  [re-com/v-box
   :gap "1em"
   :children [[game-title]
              [re-com/h-box
               :gap "5em"
               :children [[board]
                          [game-data]]]]])

(defn rooms-link []
  [re-com/hyperlink-href
   :label "go to rooms"
   :href "/rooms"])

(defn home-panel []
  [re-com/v-box
   :gap "1em"
   :children [[game-title]
              [rooms-link]
              ]])

(defn room-name [name]
  [re-com/title :level :level2
   :label name])

(defn player-names [[p1 p2]]
  [re-com/v-box
   :children
   [[re-com/title
     :label (str "Player 1: "  p1)
     :level :level3]
    [re-com/title
     :label (str "Player 2: " p2)
     :level :level3]]])

(defn mini-board [brd]
  [board
   :size 5
   :interactive false])

(defn room-panel [& {:keys [room-id]}]
  (let [room (re-frame/subscribe [:room room-id])
        mouse-over? (reagent/atom true) ]
    (fn panel []  
      [re-com/border
       :height "100px"
       :child
       [re-com/h-box
        :gap "1em"
        :size "8"

        :attr  {:on-mouse-down #(
                                   (accountant/navigate! ( str  "/rooms/" room-id)) )}
        :style (merge {}
                      (when
                          @mouse-over?
                        {:background-color
                         "#d8d8d8"})
                      (when
                          (not @mouse-over?)
                        {:background-color
                         "#e8e8e8"}))
        :attr {:on-mouse-over #(reset! mouse-over? true)
               :on-mouse-out #(reset! mouse-over? false)}
        :justify :around
        :children
        [[re-com/v-box
          :children [[room-name (:name @room)]
                     [re-com/button :label "join"
                      :on-click  #(accountant/navigate! ( str  "/game/" room-id))]]]
         [re-com/box
          :child [player-names (:players @room)]]]]])))

(defn rooms-list []
  (let [rooms (re-frame/subscribe [:rooms])]
    (fn r-list []
      [re-com/v-box
       :size "auto"
       :gap "1em"
       :children (for [[id _] @rooms]
                     [room-panel :room-id id ]) ])))

(defn actions-panel []
  (let [show-panel? (reagent/atom false)
        new-room-name (reagent/atom "")
        user-info   (re-frame/subscribe [:user-info])]
    (fn actions []
      [re-com/v-box
       :children
       [
        [re-com/h-box
         :children [[re-com/label
                     :label  (str  "Name: " ( get-in @user-info [:github :displayName]))]
                    ]]
        [re-com/button :label "create game"
         :on-click #(reset! show-panel? true)]
        (when @show-panel? 
          [re-com/modal-panel
           :backdrop-on-click #(reset! show-panel? false)
           :child
           [re-com/v-box
            :children
            [[re-com/title
              :label "Game Name" :level :level3]
             [re-com/input-text
              :model new-room-name
              :on-change #(reset! new-room-name %)]
             [re-com/h-box
              :children
              [[re-com/button
                :label "create"
                :on-click #(do   (re-frame/dispatch [:create-room @new-room-name])
                                 (reset! show-panel? false))]
               [re-com/button
                :label "cancel"
                :on-click #(reset! show-panel? false)]]]]]])]])))

(defn rooms-panel []
  [re-com/v-box
   :gap "1em"
   :size "auto"
   :children [[game-title]
              [re-com/h-box
               :gap "1em"
               :children [[rooms-list]
                          [actions-panel]]]]])



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
(defmethod panels :home-panel [] [rooms-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :game-panel [] [game-panel])
(defmethod panels :rooms-panel [] [rooms-panel])
(defmethod panels :default [] [:div])

(defn wait-for-auth []
  [re-com/title
    :level :level1
    :label "LOGGING IN"])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])
        authed? (re-frame/subscribe [:authed?])]
    (fn main []
      (if @authed?
        [re-com/v-box
         :height "100%"
         :children [(panels @active-panel)]]
        [wait-for-auth]))))


