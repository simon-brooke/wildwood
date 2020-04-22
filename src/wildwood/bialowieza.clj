(ns wildwood.bialowieza
  "The second iteration of the core inference engine for Wildwood"
  (:require [wildwood.advocate :refer [Advocate]]
            [wildwood.schema :refer [proposition?]]))

  ;; to start a game we must have n advocates, where n > 1. Each must have
  ;; its own knowledge accessor, so at the level of the game engine we don't
  ;; need to know how to access knowledge.

  (defn decide
    "Decide the truth value of this `proposition` by convening a game between
    these advocate `agents`. Iterate the game until all agents PASS; then finally
    offer each agent's `assert` method the `proposition` together with the
    decided truth value (`true` or `false`), before returning that value.

    The `proposition` is a proposition as defined in the `wildwood.schema`;
    that is to say, the predicate `wildwood.schema/predicate?` returns true
    of it. If the proposition isn't a predicate, throw an exception.

    Each of `agents` should be an object implementing the
    `wildwood.advocate/Advocate` protocol. If an agent isn't an Advocate,
    throw an exception.

    Do not throw an exception under any other circumstances.

    If an agent throws an exception, catch it and treat it as a PASS."
    [proposition & agents]
    ;; TODO: actually write it.

    false)
