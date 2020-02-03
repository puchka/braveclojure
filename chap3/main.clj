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

;;5. Create a function that’s similar to symmetrize-body-parts except that it has to work with weird space aliens with radial symmetry. Instead of two eyes, arms, legs, and so on, they have five.

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])


(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts 
         final-body-parts []]
    (if (empty? remaining-asym-parts) 
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining 
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(defn alienize
  [parts]
  (if (some? (second parts))
    (loop [idx (range 1 6) multiplized-parts []]
      (if (empty? idx)
        multiplized-parts
        (recur (rest idx)
          (into multiplized-parts [
            { :name (str (:name (first parts)) "-" (first (take 1 idx)))
              :size (:size (first parts))
            }
            { :name (str (:name (second parts)) "-" (first (take 1 idx)))
              :size (:size (second parts))
            }
          ]))
      )
    )
    [(first parts)]
  )
)

(defn symmetrize-body-parts-alien
  [asym-body-parts]
  (loop [remaining-asym-body-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-body-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-body-parts]
        (recur remaining
               (into final-body-parts
                     (alienize
                      (set [part (matching-part part)]))))))))

;; 6. Create a function that generalizes symmetrize-body-parts and
;; the function you created in Exercise 5.

(defn multiplize
  [parts n]
  (if (some? (second parts))
    (loop [idx (range 1 (+ n 1)) multiplized-parts []]
      (if (empty? idx)
        multiplized-parts
        (recur (rest idx)
          (into multiplized-parts [
            { :name (str (:name (first parts)) (if (> n 1) (str "-" (first (take 1 idx))) ""))
              :size (:size (first parts))
            }
            { :name (str (:name (second parts)) (if (> n 1) (str "-" (first (take 1 idx))) ""))
              :size (:size (second parts))
            }
          ]))
      )
    )
    [(first parts)]
  )
)

(defn symmetrize-body-parts-gen
  [asym-body-parts n]
  (loop [remaining-asym-body-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-body-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-body-parts]
        (recur remaining
               (into final-body-parts
                     (multiplize
                      (set [part (matching-part part)]) n)))))))
