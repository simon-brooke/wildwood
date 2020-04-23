(ns wildwood.knowledge-accessor
  "The key point of building Bialowieza as a library rather than a complete
  application is that it should be possible to hook it up to multiple sources
  of knowledge. Thus we must design a protocol through which knowledge can be
  accessed, and a schema in which it will be returned. Note that the
  accessor must be able to add knowledge to the knowledge base, as well as
  retrieve it."
  (:require [wildwood.schema :refer [proposition?]]))

(defprotocol Accessor
  (fetch [self id]
         "Fetch all the knowledge I have about the object identified by
         this `id` value, as a map whose `:id` key has this `id` value.")
  (store [self id proposition]
         "Add this `proposition` to the knowledge I hold about the object
         identified by this `id` value."))
