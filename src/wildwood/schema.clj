(ns wildwood.schema
  "The knowledge representation. This probably ends up looking a bit like a
  Toulmin schema, where claims are represented as propositions. There also
  need to be rules or predicates, things which can test whether a given
  proposition has a given value. There may be other stuff in here.

  Internal representation of most of this will be as Clojure maps."
  )

(defn proposition?
  "True if `o` qualifies as a proposition. A proposition is probably a map
  with some privileged keys, and may look something like a minimised
  `the-great-game.gossip.news-items` item."
  ;; TODO: write this.
  [o]
  false)

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
