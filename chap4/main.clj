(ns braveclojure)

(defn parse
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\r{0,1}\n")))

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(def filename "suspects.csv")

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

;; 1. Turn the result of your glitter filter into a list of names.

(defn glitter-filter-name
  [minimum-glitter records]
  (map :name (glitter-filter minimum-glitter records)))


;; 2. Write a function, append, which will append a new suspect to your list of suspects.

(defn append
  [suspects new-suspect]
  (into suspects [new-suspect]))

;; 3. Write a function, validate, which will check that
;; :name and :glitter-index are present when you append.
;; The validate function should accept two arguments:
;; a map of keywords to validating functions, similar to
;; conversions, and the record to be validated.

(def validations
  {:name #(not (nil? (:name %)))
   :glitter-index #(not (nil? (:glitter-index %)))})

(defn validate
  [validations record]
  (and ((get validations :name) record) ((get validations :glitter-index) record)))

;; 4. Write a function that will take your list of maps and
;; convert it back to a CSV string. You’ll need to use the
;; clojure.string/join function.

(defn convert-to-csv
  [list-of-maps]
  (clojure.string/join "\n"
                        (map #(clojure.string/join "," [(:name %) (:glitter-index %)]) list-of-maps)))
