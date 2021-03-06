(ns wildwood.dengine.engine
  "An implementation of the DTree engine adapted to `wildwood.schema` propositions."
  (:require [wildwood.knowledge-accessor :refer [Accessor]]
            [wildwood.schema :refer [proposition?]]))

(defn decide
  "Decide the truth value of this `proposition`, using the dtree rooted at
  this `node` and knowledge provided by this `accessor`."
  ;; how is explanation returned in this schema? We need a richer return value
  ;; than just a truth value.
  [proposition node accessor]
  ;; TODO: implement
  false)
