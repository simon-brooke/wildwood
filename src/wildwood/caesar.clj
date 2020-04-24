(ns wildwood.caesar
  "A dummy set of advocates and knowledge accessors with knowledge about the
  death of Julius Caesar.

  # The Case Against Marcus Brutus

  Did Brutus conspire to kill Caesar in the forum in the Ides of March?

  Falco, the detective, must find out.

  ## The witnesses

  Anthony knows that Brutus is honourable, and that Caesar is buried.

  Brutus will admit he was in the forum on the ides of March and is a witness
  that Cassius was also present.

  Cassius and Longus each bear witness that the other killed Caesar in the
  Forum on the Ides of March.

  Drusilla believes that Brutus killed Caesar in the Forum on the Ides of
  March, but was not a witness. She also bears witness that Caesar was buried
  on the 18th March.

  Gaius believes that Brutus killed Caesar in the Forum in April, but was not
  a witness.

  ## The rules

  There is a rule which says that if you kill someone and you have accomplices
  then you're not honourable, and a default that has-accomplices is false.

  Note that has-accomplices boils down to
  > For a given verb, object, location and time, there is more than one subject.

  That's quite sophisticated to represent.

  There is a rule which says you can't be killed after you're dead (temporal
  reasoning).

  There is a rule which says if you've been killed or been buried then you're
  dead.

  The case against Brutus is based on Drusilla's claim and on Gaius's.

  Drusilla's can be doubted because
  1. She wasn't a witness and
  2. Is a woman.

  Gaius's can be doubted because
  1. he wasn't a witness, and because
  2. it's inconsistent with the evidence that Caesar was buried on the 18th.

  ## The conclusion

  Thus, I think, Falco must conclude that Brutus didn't kill Caesar, because if
  he had he must have had accomplices (Cassius and Longus, who clearly were
  accomplices and implicate one another), but honourable men don't kill with
  accomplices and Brutus is an honourable man.
  "
  (:require [wildwood.knowledge-accessor :refer [Accessor]]
            [wildwood.advocate :refer [Advocate]]))


(def ides-of-march
  "16th March, 44BC"
  (+ -440000 300 16))

(def eighteenth-march
  "18th March, 44BC"
  (+ -440000 300 18))

(def march
  "The month of March, 44BC, as a range."
  (sort [(+ -440000 300 1) (+ -440000 300 30)]))

(def april
  "The month of April, 44BC, as a range."
  (sort [(+ -440000 400 1) (+ -440000 400 30)]))

(def anthony-kb
  "Mark Antony knows that Brutus is honourable, and that Caesar is buried."
  {:brutus [{:verb :is :subject :brutus :object :honourable}]
   :caesar [{:verb :bury :subject :calpurnia :object :caesar :date eighteenth-march :nth-hand 1}]})

(def brutus-kb
  "Brutus will admit that he and Cassius were in the forum in the Ides of March"
  {:brutus [{:verb :present :subject :brutus :object :forum :location :forum :date ides-of-march :nth-hand 1}]
   :cassius [{:verb :present :subject :cassius :object :forum :location :forum :date ides-of-march :nth-hand 1}]
   :forum [{:verb :present :subject :brutus :object :forum :location :forum :date ides-of-march :nth-hand 1}
           {:verb :present :subject :cassius :object :forum :location :forum :date ides-of-march :nth-hand 1}]})

(def cassius-kb
  "Cassius and Longus each bear witness that the other killed Caesar in the
  Forum on the Ides of March."
  {:caesar [{:verb :kill :subject :longus :object :caesar :location :forum :date ides-of-march :nth-hand 1}]
   :longus [{:verb :kill :subject :longus :object :caesar :location :forum :date ides-of-march :nth-hand 1}]
   :forum [{:verb :kill :subject :longus :object :caesar :location :forum :date ides-of-march :nth-hand 1}]})

(def drusila-kb
  "Drusila has heard that Brutus killed Caesar in the forum. She keys it on all three, for efficiency
  of retrieval."
  {:caesar [{:verb :kill :subject :brutus :object :caesar :location :forum :date ides-of-march :nth-hand 2}
            {:verb :bury :subject :calpurnia :object :caesar :date eighteenth-march :nth-hand 1}]
   :brutus [{:verb :kill :subject :brutus :object :caesar :location :forum :date ides-of-march :nth-hand 2}]
   :forum [{:verb :kill :subject :brutus :object :caesar :location :forum :date ides-of-march :nth-hand 2}]})

(def faldo-db
  "Falco believes that Caesar has been killed, but doesn't know by whom or when."
  {:caesar [{:verb :kill :object :caesar :location :forum}]
   :brutus [{:verb :kill :object :caesar :location :forum}]
   :forum [{:verb :kill :object :caesar :location :forum}]})

(def gaius-db
  "Gaius has heard that Brutus killed Caesar, but believes it happened in April."
  {:caesar [{:verb :kill :subject :brutus :object :caesar :location :forum :date april :nth-hand 2}]
   :brutus [{:verb :kill :subject :brutus :object :caesar :location :forum :date april :nth-hand 2}]
   :forum [{:verb :kill :subject :brutus :object :caesar :location :forum :date april :nth-hand 2}]})

(def longus-kb
  "Cassius and Longus each bear witness that the other killed Caesar in the
  Forum on the Ides of March."
  {:caesar [{:verb :kill :subject :cassius :object :caesar :location :forum :date ides-of-march :nth-hand 1}]
   :cassius [{:verb :kill :subject :cassius :object :caesar :location :forum :date ides-of-march :nth-hand 1}]
   :forum [{:verb :kill :subject :cassius :object :caesar :location :forum :date ides-of-march :nth-hand 1}]})

