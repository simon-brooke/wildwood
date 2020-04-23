(ns wildwood.schema
  "The knowledge representation. This probably ends up looking a bit like a
  Toulmin schema, where claims are represented as propositions. There also
  need to be rules or predicates, things which can test whether a given
  proposition has a given value. There may be other stuff in here.

  Internal representation of most of this will be as Clojure maps."
  )

(def required-keys
  "Every proposition is expected to have values for these keys."
  #{:verb :subject :object})

(def consensual-keys
  "Every proposition which has these keys, in a given decision process,
  must have the same semantics and types for their values. The exact
  representations used for the values of these keys does not
  matter, it is consensual between all participating advocates in a
  decision process."
  #{:time       ;; a representation of time - which should have a canonical ordering;
    :location   ;; a representation of place - which may have concepts of proximity;
    :truth      ;; if present and value `false`, negates the proposition;
    :data       ;; an argument structure...!
    })

(def argument-keys
  "Every argument is a proposition, which additionally has these keys."
  #{:confidence ;; how sure am I? A value, perhaps, in the range -1 to 1, although conventionally if less than 1 we probably set the `:truth` value to false;
    :authority  ;; id of agent from whom, or rule from which, I know this.
    })

;; (argument-keys :authority)
;; (argument-keys :data)

(defn proposition?
  "True if `o` qualifies as a proposition. A proposition is probably a map
  with some privileged keys, and may look something like a minimised
  `the-great-game.gossip.news-items` item.

  If `minimised` is passed and is `true`, then the proposition must
  be minimised - that is to say, the values of keys in a proposition map may
  not themselves be keys. Where the value of a key represents an object in the
  world, that value must be simply the `id` of the object, not a richer
  representation."
  ([o]
   (and
     (map? o)
     (every? #(o %) required-keys)))
  ([o minimised]
   (and
     (proposition? o))
   (not
     (when
       (true? minimised)
       ;; not good enough. An argument is a proposition even if its argument-keys
       ;; are not minimised (indeed, they should not be). TODO: fix.
       (some map? (vals o))))))

;; (proposition? {:verb :killed :subject :brutus :object :caesar}) ;; true
;; (proposition? {:verb :killed :subject :brutus :object :caesar} true) ;; true
;; (proposition? {:verb :killed :subject :brutus :object :caesar :truth false}) ;; true
;; (proposition? {:verb :killed :subject :brutus}) ;; false: no :object key
;; (proposition? {:verb :killed
;;                :subject {:id :brutus :name "Marcus Brutus"}
;;                :object {:id :caesar
;;                         :name "Gaius Julius Caesar"
;;                         :wife :drusila}}) ;; true, although not minimised
;; (proposition? {:verb :killed
;;                :subject {:id :brutus :name "Marcus Brutus"}
;;                :object {:id :caesar
;;                         :name "Gaius Julius Caesar"
;;                         :wife :drusila}} true) ;; false, because minimisation was required

(defn truth
  "If `p` is a proposition, return whether the value asserted by that
  proposition is `true`. If the `:truth` key is missing, `true` is
  assumed."
  ;; TODO: for orthogonality, this might be renamed `decide`.
  [p]
  (if
    (proposition? p)
    (if
      (false? (:truth p))
      false
      true)
    nil))

;; (truth {:verb :killed :subject :brutus :object :caesar})
;; (truth {:verb :killed :subject :brutus :object :caesar :truth true})
;; (truth {:verb :killed :subject :brutus :object :caesar :truth false})

(defn rule?
  "True if `o` qualifies as a rule. A rule is a structure which comprises
  * an id and
  * a function of two arguments, a proposition and a knowledge accessor,
  and which should (if this can simply be checked) return an argument
  structure."
  [o]
  ;; TODO: write this. In practice it may be simpler if we defprotocol or
  ;; defrecord a rule structure.
  false)

(defn argument?
  "True if `o` qualifies as an argument structure.

  An argument structure is a (potentially rich proposition which, in addition, should have values
  for `:confidence` and `:authority`. A value for `:data` may, and probably will,
  also be present but is not required."
  [o]
  (and
    (proposition? o)
    (every? #(o %) argument-keys)))

;; (argument? {:verb :killed :subject :brutus :object :caesar}) ;; false, lacks :confidence, :authority
;; (argument? {:verb :killed :subject :brutus :object :caesar :confidence 0.7 :authority :falco}) ;; true
;; (argument? {:verb :killed
;;             :subject {:id :brutus :name "Marcus Brutus"}
;;             :object {:id :caesar :name "Gaius Julius Caesar" :wife :drusila}
;;             :confidence 1
;;             :authority :brutus}) ;; true

(defn minimise
  "Expecting that `o` is a (potentially rich) proposition, return a map identical
  to `o` save that for each value `v` of key `k` in `o`, if `v` is a map and `k`
  is not a member of `argument-keys`, then the returned map shall substitute the
  value of `(:id v)`.

  see also `wildwood.knowledge-access/maximise`."
  [o]
  (if
    (map? o)
    (reduce
      merge
      {}
      (map
        (fn [k]
          {k
           (let [v (k o)]
             (if
               (and (not (argument-keys k)) (map? v))
               (:id v)
               v))})
        (keys o)))
    o))

;; (proposition?
;;   (minimise {:verb :killed
;;              :subject {:id :brutus :name "Marcus Brutus"}
;;              :object {:id :caesar :name "Gaius Julius Caesar" :wife :drusila}}))
