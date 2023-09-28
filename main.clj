(use 'clojure.data)

;; Debug function. Takes in a list of things to print, prints them, and returns the last in the list
(defn dbg [& string] (println string) (last string))

;; Define the mutations as keywords
(def default-mutations {:boil (fn [food] (assoc food :hot true))
                        :wash (fn [food] (assoc food :wet true))
                        :dry  (fn [food] (dissoc food :wet))})

;; Define the initial state of a potato
(def potato {:type :potato :starchy true :history []})
(def potato-mutations {:boil (fn [food] (assoc food :mushy true))})

;; A list of all specialized mutations
(def mutations {:potato potato-mutations})

;; Diffs previuous and current maps and appends the additions and removals to the `:history` key of the map
(defn update-history [food new-food mutation] (let [diff          (diff (dissoc food :history) (dissoc new-food :history))
                                                    history-entry {:mutation mutation :added (second diff) :removed (first diff)}
                                                    new-history   (conj (:history food) history-entry)]
                                                (assoc new-food :history new-history)))

;; Define a function to apply a mutation to the food
(defn apply-mutation [mutation food]
  (let [food-type          (get food :type)
        ;; Special mutation based on the type
        special-mutation   (get (get mutations food-type) mutation)
        ;; The default mutation
        default-mutation   (get default-mutations mutation)

        ;; Mutations to apply
        mutations-to-apply (if (nil? special-mutation) [default-mutation] [default-mutation special-mutation])

        ;; Apply the default mutation then the special mutation if present
        ;; The special mutation gets the updated food from the default mutation
        new-food           (update-history food (reduce (fn [food mutation-fn] (mutation-fn food)) food mutations-to-apply) mutation)]

    ;; Return the new food
    new-food))

;; Test the implementation
(defn -main []
  ;; Apply mutations to the potato
  (let [new-potato (->> potato
                        (apply-mutation :wash)
                        (apply-mutation :boil)
                        (apply-mutation :dry))]

    ;; Print each step
    (dorun (map #(println "step" %)
                (get new-potato :history)))

    ;; Print the resulting potato excluding the history
    (println (dissoc new-potato :history))))

;; Run the main function
(-main)
