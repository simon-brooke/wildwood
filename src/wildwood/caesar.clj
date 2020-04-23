(ns wildwood.caesar
  "A dummy set of advocates and knowledge accessors with knowledge about the
  death of Julius Caesar."
  (:require [wildwood.knowledge-accessor :refer [Accessor]]
            [wildwood.advocate :refer [Advocate]]))

(def ides-of-march
  "16th March, 44BC"
  (+ -440000 300 16))

(def march
  "The month of March, 44BC, as a range."
  (sort [(+ -440000 300 1) (+ -440000 300 30)]))

(def april
  "The month of April, 44BC, as a range."
  (sort [(+ -440000 400 1) (+ -440000 400 30)]))

(def drusila-kb
  "Drusila knows that Longus killed Caesar in the forum. She keys it on all three, for efficiency
  of retrieval."
  {:caesar [{:verb :killed :subject :longus :object :caesar :location :forum :date ides-of-march}]
   :longus [{:verb :killed :subject :longus :object :caesar :location :forum :date ides-of-march}]
   :forum [{:verb :killed :subject :longus :object :caesar :location :forum :date ides-of-march}]})

(defn faldo-db
  "Falco knows that Caesar has been killed, but doesn't know who by or when."
  {:caesar [{:verb :killed :object :caesar :location :forum}]
   :brutus [{:verb :killed :object :caesar :location :forum}]
   :forum [{:verb :killed :object :caesar :location :forum}]})

(def gaius-db
  "Gaius knows that Brutus killed him, but thinks it happened in April."
  {:caesar [{:verb :killed :subject :brutus :object :caesar :location :forum :date april}]
   :brutus [{:verb :killed :subject :brutus :object :caesar :location :forum :date april}]
   :forum [{:verb :killed :subject :brutus :object :caesar :location :forum :date april}]})

(def marc-anthony-kb
  "Mark Antony knows that Brutus is honourable."
  {:brutus [{:verb :is :subject :brutus :object :honourable}]})


