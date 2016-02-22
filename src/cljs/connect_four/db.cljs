(ns connect-four.db)

(comment :empty :red :blue)



(defn a-row []
  (into [] (take 7 (repeat :empty))))

(defn a-board []
  (into [] (take 6 (repeat (a-row )))))

(def default-db
  {:name "re-frame"
   :player :blue
   :turn 0
   :error ""
   :board (a-board)})
