(ns wildwood.advocate
  "An agent capable of playing the explanation game.

  An advocate must have its own knowledge accessor. Different advocates
  within a game may be accessing different knowledge bases, or different
  subsets of the same knowledge base with different - potentially competing
  - knowledge. It also needs to know the schema in which knowledge will be
  presented.

  Since the mechanism by which the application will communicate with the
  library must include a way for users to interact with the game, and
  since the role of the user in the came is just as a participant,
  advocate must be defined as a protocol, in order that it may be extended
  by code within the application which is passed in to the game when the
  game is started. Indeed, multiple agents - the user(s) and potentially
  non-player characters - may be passed in.

  In this conception, nothing within a default advocate has to be able to
  produce or consume natural language. It is sufficient for the API exposed
  by wildwood.advocate to receive and return wildwood.schema objects.

  Obviously to show a user interface anything similar to Arden's, or for
  what I intend for The Great Game, the advocates passed must 'at their
  other end' - that is, on the application side rather than the library
  side - be able to consume and emit natural language, but that functionality
  does not need to be part of the wildwood library, and certainly does
  not need to be part of the default advocate as specified here."
  (:require [wildwood.knowledge-accessor :refer []]
            [wildwood.schema :refer [proposition? argument?]]))


(defprotocol Advocate
  (id [self] "Return a value which uniquely identifies this agent.")
  (decide [self proposition argument]
          "Return `true`, `false` or `nil` as the value of this `proposition`,
          given this `argument`. The value of `proposition` should be a
          proposition as defined by `wildwood.schema/proposition?`;
          the value of `argument` should satisfy `wildwood.schema/argument?`.")
  (move [self proposition argument]
        "Return a new argument structure representing the result of applying my
        `decide` method to this `proposition` and `argument`, specifying in
        some way not yet determined what sort of move I have made."))
