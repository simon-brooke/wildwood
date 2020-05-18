(ns wildwood.logic
  "Highly experimental work towards basic logic operators on Bialowieza-style
  proposition structures"
  )

(defn matches?
  "True if this `candidate` matches this `pattern`. AT THIS STAGE, a
  match is found if for every key in `pattern`, the value of that key in the
  candidate is the same as the value in `pattern`. Note that in future the
  values of the `:time`, `:location`, `:confidence` and `:data` keys may be
  handled specially."
  [pattern candidate]
  (every?
    #(= (pattern %) (candidate %))
    (keys pattern)))

(defn match
  "Return those of these `candidates` matched by this `pattern`. Both `pattern`
  and each candidate in `candidates` are expected to be maps. AT THIS STAGE, a
  match is found if for every key in `pattern`, the value of that key in the
  candidate is the same as the value in `pattern`. Note that in future the
  values of the `:time`, `:location`, `:confidence` and `:data` keys may be
  handled specially."
  [pattern candidates]
  (filter
    #(matches? pattern %)
    candidates))

(defn fetch
  "Return those propositions from among these `propositions` where some key
  matches this `id`."
  [id propositions]
  (remove
    (fn [p]
      (empty?
        (filter
          (fn [k] (= id (p k)))
          (keys p))))
    propositions))
