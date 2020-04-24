(ns wildwood.mongo-ka
  "A knowledge accessor fetching from and storing to Mongo DB.

  Hierarchical databases seem a very natural fit for how we're storing
  knowledge. Mongo DB seems a particularly natural fit since its
  internal representation is JSON, which can be transformed to EDN
  extremely naturally."
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [wildwood.knowledge-accessor :refer [Accessor]])
  (:import [com.mongodb MongoOptions ServerAddress]
           [com.mongodb DB WriteConcern]
           [org.bson.types ObjectId]))

;; MongoDB data items are identified by ObjectId objects. In the retrieved
;; record from MongoDB, key value is the value of a keyword `:_id` I don't
;; think there's any *in principle* reason why we should not use these objects
;; as key values - they're presumably designed to be globally unique.
;;
;; In which case, on the way down we have to set `:_id` to the value of `:id`
;; and vice versa on the way back up.

(defrecord MongoKA
  ;; It's not clear to me whether we need to pass both the connection and the
  ;; database in - it's possible that the connected database handle is
  ;; sufficient. The value of `:collection` is the name of the collection
  ;; within the database to which this accessor writes.
  [connection db ^String collection]
  Accessor
  (fetch
    [_ id]
    (let [oid (cond
                (instance? ObjectId id) id
                (string? id) (ObjectId. id)
                (keyword? id) (ObjectId. (name id)))
          record (mc/find-by-id db collection oid)]
      (when record
        (assoc
          (dissoc record :_id)
          :id id))))
  (match [_ proposition]
         ;; I know I've seen how to do this in the Mongo documentation...
         )
  (store [_ proposition]
         ;; don't really know how to do this and am too tired just now.
         ))

