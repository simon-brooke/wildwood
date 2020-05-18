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

  ## Features

  The `features` in DTree terms we're interested in to make these inferences are

  * `did-kill` - true of an entity which is in the subject position of a `kill` proposition;
  * `was-killed` - true of an entity which is in the object position of a `kill` proposition;
  * `buried` - true of an entity which is in the object position of a `bury` proposition;
  * `dead` - true of an entity of which either `was-killed` or `buried` is true;
  * `honourable` - true of an entity, provided that `did-lie` and `did-murder` are false;
  * `did-murder` - true of an entity x that `did-kill[x,y]` for some object y is true of,
  provided that there exists some other entity p of whom `did-kill[p,x]` is also true,
  or that `was-unarmed[y]` is true;
  * `did-lie` - true of an entity which has offered a proposition which for other reasons we do not believe. Tricky. False by default and I think we probably leave it at that for now.
  * `was-unarmed` - true of an entity at a time `t` if they were unarmed at the time.

  Note that ALL of this is too complex for the simple DTree logic of the Arboretum /
  KnacqTools generation. They could not unpack propositions as I'm proposing here."
  (:require [wildwood.advocate :refer [Advocate]]
            [wildwood.knowledge-accessor :refer [Accessor]]
            [wildwood.logic :as l]))


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

(defrecord KnowledgeBase [propositions]
  Accessor
  (fetch [self id]
         (reduce
           concat
           (filter
             (fn [p]
               (some?
                 (fn [k] (= (p k) id))
                 (keys p))))
           (.propositions self)))
  (match [self proposition]
         (l/match proposition (.propositions self)))
  (store [self proposition]
         (throw (UnsupportedOperationException. "Not yet implemented"))))


(def anthony-kb
  "Mark Antony knows that Brutus is honourable, and that Caesar is buried."
  (KnowledgeBase.
    [{:verb :is :subject :brutus :object :honourable :authority :anthony}
     {:verb :bury :subject :calpurnia :object :caesar :date eighteenth-march :authority :anthony}]))

(def brutus-kb
  "Brutus will admit that he and Cassius were in the forum in the Ides of March"
  (KnowledgeBase.
    [{:verb :present :subject :brutus :object :forum :location :forum :date ides-of-march :authority :brutus}
     {:verb :present :subject :cassius :object :forum :location :forum :date ides-of-march :authority :brutus}]))

(def cassius-kb
  "Cassius and Longus each bear witness that the other killed Caesar in the
  Forum on the Ides of March."
  (KnowledgeBase.
    [{:verb :kill :subject :longus :object :caesar :location :forum :date ides-of-march :authority :cassius}]))

(def drusilla-kb
  "Drusilla has heard that Brutus killed Caesar in the forum. "
  (KnowledgeBase.
    [{:verb :kill :subject :brutus :object :caesar :location :forum :date ides-of-march
      ;; Second hand knowledge. The `:data` (maybe should be `:warrant`?)
      ;; shows, as two-position propositions, that Drusilla learned this
      ;; from Calpurnia, and the authority is Drusilla since that's who the
      ;; knowledge comes from. I think.
      :data [{:verb :utter :subject :calpurnia
              :object {:verb :kill :subject :brutus :object :caesar :location :forum :date ides-of-march :authority :calpurnia}
              :authority :drusilla}
             {:verb :hear :subject :calpurnia
              :object {:verb :kill :subject :brutus :object :caesar :location :forum :date ides-of-march :authority :calpurnia} :authority :drusilla}]
      :authority :drusilla}
     {:verb :utter :subject :calpurnia :object {:verb :bury :subject :calpurnia :object :caesar :date eighteenth-march :authority :calpurnia}
      :authority :drusilla}
     {:verb :hear :subject :calpurnia :object {:verb :bury :subject :calpurnia :object :caesar :date eighteenth-march :authority :calpurnia}
      :authority :drusilla}
     {:verb :bury :subject :calpurnia :object :caesar :date eighteenth-march :authority :drusilla
      :data [{:verb :utter :subject :calpurnia :object {:verb :bury :subject :calpurnia :object :caesar :date eighteenth-march :authority :calpurnia}
              :authority :drusilla}
             {:verb :hear :subject :calpurnia :object {:verb :bury :subject :calpurnia :object :caesar :date eighteenth-march :authority :calpurnia}
              :authority :drusilla}]}]))

(def falco-kb
  "Falco believes that Caesar has been killed, but doesn't know by whom or when."
  (KnowledgeBase.
    [{:verb :kill :object :caesar :location :forum :authority :falco}]))

(def gaius-kb
  "Gaius has heard that Brutus killed Caesar, but believes it happened in April."
  (KnowledgeBase.
    ;; TODO: replace that nth-hand with an actual warrant. Who did he hear it from?
    [{:verb :kill :subject :brutus :object :caesar :location :forum :date april :nth-hand 2 :authority :gaius}]))

(def longus-kb
  "Cassius and Longus each bear witness that the other killed Caesar in the
  Forum on the Ides of March."
  (KnowledgeBase.
    [{:verb :kill :subject :cassius :object :caesar :location :forum :date ides-of-march :authority :longus}]))

(defrecord CompoundKnowledgeBase [kbs]
  Accessor
  (fetch [self id]
         (throw (UnsupportedOperationException. "Not yet implemented")))
  (match [self proposition]
         (l/match proposition (reduce concat (map #(.propositions %) (.kbs self)))))
  (store [self proposition]
         (throw (UnsupportedOperationException. "Not yet implemented"))))


;; (knowledge brutus-kb cassius-kb)
(def all-knowledge
  (CompoundKnowledgeBase.
    [anthony-kb
     brutus-kb
     cassius-kb
     drusilla-kb
     falco-kb
     gaius-kb
     longus-kb]))



