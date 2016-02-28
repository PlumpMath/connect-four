(ns connect-four.game)

(def next-player
  {:blue :red
   :red :blue})

(def ways-to-win
  {:r-diag [1 1]
   :l-diag [1 -1]
   :hori   [0 1]
   :vert   [1 0]})

(defn get-rows-below [board row col]
  (into #{}
        (for [cur-row (range (+ row 1) 6)]
          (get-in board [cur-row col]))))


(defn bottom-row? [board [row col]]
  (let [things (get-rows-below board row col)] 
    (not (contains?
          (get-rows-below board row col) :empty ))))

(defn valid-play? [board play]
  (let [target-slot (get-in board play)]
    (if (and  (= target-slot :empty)
              (bottom-row? board play))
      "ok"
      "you're a dick")))

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

