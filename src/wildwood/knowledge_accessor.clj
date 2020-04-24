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
         this `id` value, as a map whose `:id` key has this `id` value.

         *NOTE THAT:* I now think knowledge should only be managed at the
         Wildwood level as sets of propositions, so the idea of bringing
         back some sort of object representation here is probably wrong.")
  (match [self proposition]
         "Return all the propositions I know which match this proposition. The
         intended use case here is that you will either supply a fully
         specified proposition to verify that that proposition is true, or else
         supply a partially specified proposition to query.

         e.g. passing the proposition

             {:verb :kill :object :caesar}

         would be a way of asking 'who killed caesar', and might return

             [{:verb :kill :subject :brutus :object :caesar}
              {:verb :kill :subject :cassius :object :caesar}
              {:verb :kill :subject :longus :object :caesar}]")
  (store [self proposition]
         "Add this `proposition` to the knowledge I hold."))
