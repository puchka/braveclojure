(ns braveclojure)

;;2. Write a function that takes a number and adds 100 to it.

(defn add100 [n] (+ n 100))

;;3. Write a function, dec-maker, that works exactly like the function inc-maker except with subtraction:

(defn dec-maker [dec-by]
  #(- % dec-by))

(def dec10 (dec-maker 10))

;;4. Write a function, mapset, that works like map except the return value is a set: 

(defn mapset
  [fn & args]
  (set (apply map fn args)))
