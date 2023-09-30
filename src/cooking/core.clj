(ns cooking.core
  (:require [clojure.pprint :as pprint])
  (:require [clojure.data.json :as json]))

(defn new-step [name props]
  {name props})
(defn new-recipe [name ingredients steps]
  {:name name
   :ingredients (if (not (vector? ingredients))
    [ingredients] ingredients)
   :steps steps})
(defn new-prep [ingredients steps]
  {:ingredients ingredients :steps steps})

;; Steps
(defn bake [tempF minutes]
  (new-step "bake" {:temp tempF :minutes minutes}))
(defn season []
  (new-step "season" {}))
(defn grill [tempF minutes]
  (new-step "grill" {:temp tempF :minutes minutes}))
(defn chop [chunk-grams]
  (new-step "chop" {:chunk-grams chunk-grams}))
(defn weigh [grams]
  (new-step "weigh" {:grams grams}))

;; Recipes
(def baked-potato
  (new-recipe "baked-potato" ["potato" (new-prep "butter" [(weigh 5)])] [(season) (bake 400 60)]))
(def seasoned-baked-potato
  (new-recipe "seasoned-baked-potato" [baked-potato (new-prep "salt" [(weigh 1)])] [(season)]))

(def grilled-steak
  (new-recipe "grilled-steak" ["steak"] [(grill 500 10)]))
(def seasoned-grilled-steak
  (new-recipe "seasoned-grilled-steak" [grilled-steak (new-prep "salt" [(weigh 1)])] [(season)]))

(def steak-and-potatoes
  (new-recipe "steak-and-potatoes" [seasoned-grilled-steak (new-prep baked-potato [(chop 10)])] []))

;; Test the implementation
(defn -main []
  (pprint/pprint steak-and-potatoes))

;; Run the main function
(-main)