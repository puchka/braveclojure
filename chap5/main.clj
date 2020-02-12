(ns braveclojure)

(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))


;; 1. Create a new function, attr, that you can call like (attr :intelligence)

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})
(defn attr
  [attribute]
  (fn [arg]((comp attribute :attributes) arg)))

;; 2. Implement the comp function.

(defn my-comp
  [& funcs]
  (loop [fun (first funcs)
         fns (rest funcs)]
    (if (empty? fns)
      (fn [& args]
        (apply fun args))
      (recur (fn [& args] (fun (apply (first fns) args))) (rest fns)))))

;; 3. Implement the assoc-in function

(defn my-assoc-in
  [m [k & ks] v]
  (loop [mp (assoc {} (last ks) v)
         keys (drop-last ks)]
    (if (empty? keys)
      (assoc m k mp)
      (recur (assoc {} (last keys) mp) (drop-last keys)))))

;; 5. Implement update-in.

(defn my-update-in
  [m ks f & args]
   (assoc-in m ks (apply f (get-in m ks) args)))
