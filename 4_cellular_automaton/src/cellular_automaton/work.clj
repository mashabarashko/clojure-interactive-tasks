;;; Your task is to implement cellular automaton.
;;; The most famous example of cellular automaton is Conway's Game of Life.
;;; Unlike previous tasks now you have to implement visualization and bots. So you need to implement everything :)
;;; I suggest to use quil library for animation (it was used in all previous tasks): https://github.com/quil/quil
;;; But of course you can use whatever you want.
;;; Keep in mind that is should be simple to run your simulator with different automata (Game of Life is only 1 example).


;;; Implement and run Brian's Brain automaton in your simulator: http://en.wikipedia.org/wiki/Brian%27s_Brain


;;; Implement Wireworld automaton: http://en.wikipedia.org/wiki/Wireworld


;;; Add Wireworld implementation to Rosetta Code (it's not present here yet): http://rosettacode.org/wiki/Wireworld


;;; Implement Von Neumann cellular automaton: http://en.wikipedia.org/wiki/Von_Neumann_cellular_automata


;;; Implement Langton's ant: http://en.wikipedia.org/wiki/Langton%27s_ant


;;; Add ability to change cells' states by mouse click, to restart and pause simulation.

(ns cellular-automaton.work
  (:use quil.core))

(def cell-size 20)

(def cells (atom #{[1 0] [2 1] [2 2] [1 2] [0 2]}))

(defn to-real-coords [cell]
  (map #(* cell-size %) cell))

(defn draw-cell [draw-fn cell]
  (let [[real-x real-y] (to-real-coords cell)]
    (draw-fn real-x real-y cell-size cell-size)))

(defn in? [s elm]  
  (some #(= elm %) s)) 

(defn alive? [cell] (let
       [v (vector [(- (first cell) 1) (- (last cell) 1)] [(- (first cell) 1) (last cell)] 
       [(- (first cell) 1) (+ (last cell) 1)] [(first cell) (- (last cell) 1)]
       [(first cell) (+ (last cell) 1)] [(+ (first cell) 1) (- (last cell) 1)] 
       [(+ (first cell) 1) (last cell)] [(+ (first cell) 1) (+ (last cell) 1)])]
 (if (in? @cells cell)
           (if (or (= (count (filter (partial in? @cells) v)) 2) (= (count (filter (partial in? @cells) v)) 3)) true false)
           (if (= (count (filter (partial in? @cells) v)) 3) true false))))

(defn update-cells [] (reset! cells (set (filter alive? (for [x (range 25)
      y (range 25)]
      [x y])))))

(defn draw-cells [cells]
  (fill 0 0 255)
  (doseq [cell cells]
    (draw-cell rect cell)))

(defn draw []
  (background 255)
  (draw-cells @cells)
  (update-cells))

(defn setup []
  (fill 255)
  (frame-rate 2))

(defsketch cellular-automaton
  :title "Game of Life"
  :setup setup
  :draw draw
  :size [500 500])