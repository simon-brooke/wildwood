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
  #{:time ;; a representation of time - which should have a canonical ordering
    :location ;; a representation of place - which may have concepts of proximity
    })

(defn proposition?
  "True if `o` qualifies as a proposition. A proposition is probably a map
  with some privileged keys, and may look something like a minimised
  `the-great-game.gossip.news-items` item. In particular, a proposition must
  be minimised - that is to say, the values of keys in a proposition map may
  not themselves be keys. Where the value of a key represents an object in the
  world, that value must be simply the `id` of the object, not a richer
  representation."
  ;; TODO: write this.
  [o]
  (and
    (map? o)
    (not (some map? (vals o)))
    (every? #(o %) required-keys)))

(defn rule?
  "True if `o` qualifies as a rule. A rule is should be function of one
  argument, and should (if this can simply be checked) return a truth value,
  where truth value is one of `true`, `false` or `nil`."
  [o]
  ;; TODO: write this
  false)

(defn argument?
  "True if `o` qualifies as an argument structure.

  I don't yet fully know what an argument looks like,
  but it is probably conceptually very like a recursive Toulmin structure,
  or else a list each of whose elements comprises a map with keys :
  * :vote - `true`, `false` or `nil`;
  * :move - a keyword representing one of the valid moves in the game;
  * :agent - something identifying the agent which made the move;
  * :argument - a Toulmin structure."
  [o]
  ;; TODO: write this.
  false)
