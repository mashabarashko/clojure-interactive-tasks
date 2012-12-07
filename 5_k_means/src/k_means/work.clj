(ns k-means.work
  (:use [k-means.core :only [run-empty run-2-circles run-3-circles run-random-circles]]))


;;; Your task is to implement clustering algorithm.
;;; You're a given a set of points on plane. And your goal is to divide them to k clusters.
;;; Implement k-means algorithm to solve this task: http://en.wikipedia.org/wiki/K-means_clustering
;;; Your function must take collection of points. Each point is a vector of x and y.
;;; It must return collection of clusters. Each cluster - collection of points.
;;; E.g. you have 4 points: [0 0] [1 1] [9 9] [10 10] and you need to partition them to 2 clusters.
;;; Input will be [[0 0] [9 9] [1 1] [10 10]] and output should be something like [[[0 0] [1 1]] [[9 9] [10 10]]].
;;; Note that you don't get k - number of clusters. You need to specify it somewhere in function.
;;; To test you solution use following tests:
(defn distance [p1 p2] (Math/sqrt (+ (* (- (first p1) (first p2)) (- (first p1) (first p2)))
                                 (* (- (second p1) (second p2)) (- (second p1) (second p2))))))


(defn distances [v item] (let [vec (map #(distance % item) v)]
                           (.indexOf vec (apply min vec))
                          ))

(defn k-means [k v] (
                    (fn fnc [klusters]
                        (let [vn (into [] (vals (group-by #(distances klusters %) v)))]
                             (let [kn (map #(apply vector [(/ (first (into [] (apply map + %))) 
                                               (count %))
                                             (/ (second (into [] (apply map + %)))
                                               (count %))]) vn)]
                               (if(not= (into #{} kn) (into #{} klusters)) (fnc kn) vn)))) (apply vector (take k v))))

 (run-empty (partial k-means 3))

; (run-2-circles (partial k-means 2))

; (run-3-circles (partial k-means 3))

;;; Manipulation: mouse click - add new point
;;;               space - reset simulation (remove all points or regenerate them, depenends on test)
;;; Note that may need use different solutions (with k = 2 for run-2-circles and  k = 3 for run-3-circles).



;;; Now try to improve your solution so it can determine k based on given points. So if there are visually 3 clusters it should partition points to 3 clusters, if 4 than to 4 clusters.
;;; Test your solution on this test:

; (run-random-circles SOLUTION)



;;; Implement some other clustering algorithm.
