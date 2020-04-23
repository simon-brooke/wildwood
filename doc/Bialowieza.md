# Bialowieza

{not yet written. To cover development of the Clojure Wildwood library, and the thinking and design which develops as I do it}

## Why Bialowieza?

Bialowieza is the second iteration of the Wildwood engine, and this following convention its name should start with 'B'. [Białowieża](https://en.wikipedia.org/wiki/Bia%C5%82owie%C5%BCa) is Europe's last great wild wood, and it is currently under threat.

## Motivation

## Major components of Bialowieza

### Knowledge Accessors

The `wildwood.knowledge-accessor/Accessor` protocol defines a bidirectional transducer which can fetch data from whatever storage representation the calling application uses into the representation defined by `wildwood.schema`.

### Advocates

The `wildwood.advocate/Advocate` protocol describes an agent which can take part in decision processes.

### The engine itself

The engine is implemented by the namespace `wildwood.bialowieza`.

### Inference processes

Advocates are entitled to use whatever inference processes they like, but they have access to `wildwood.dengine`, which is an implementation of the DTree engine described in the chapter on [Arboretum](Arboretum.html) adapted to propositions as defined in `wildwood.schema`.

## How shall we represent a proposition?

{ **TODO** read, and follow references from, https://plato.stanford.edu/entries/propositions/ }

As a slightly tendentious first stab, a proposition is a sentence which is either true or false. This is tendentious because two different sentences which have the same underlying semantic import are usually considered to be instances of the same proposition, and seen from the other end, there may be many ways you can validly express a single proposition in a single natural language. However, for the present purpose, the proposition that a proposition is a sentence is good enough.

A sentence generally has the form

    <verb subject object>

with possibly a lot of other qualifying material.

So we shall say that a proposition will be represented as a Clojure map with at least the keys:

* :verb
* :subject
* :object

There may be many other privileged keys, such as

* :location - where did it happen
* :time - when did it happen

and so on. The exact set of privileged keys is probably actually a matter for particular advocates rather than for the engine itself, although if the advocates in the game don't broadly share the same set of privileged keys then it won't workvery well.

## Proposition minimisation

How are the values of `:subject`, `:object` and so on to be passed? If we pass rich knowledge structures around, then we lose the insight that different advocates may know different things about given objects. Thus, while internally within each advocate's knowledge base objects may be stored with rich data, when they're passed around in propositions they should be minimised - that is to say, the value should just be a unique identifier, such that, for every object in the domain, if an advocate knows anything at all about that object, it knows its unique identifier and knows the object by that unique identifier.

Thus the unique identifier has something of the nature of a 'true name', in the magical sense. A given true name, a given unique identifier, refers to precisely one thing in the world, and provided that two advocates both know the same true name, they can debats propositions which refer to the objects with those true names.

Generally, a true name shall be a Clojure keyword. That keyword, passed to any advocate in the game, shall identify either `nil` (the advocate knows nothing of the object), or a map representing everything the advocate knows about the object.

But in saying 'the advocate knows', actually, the advocate knows nothing. The advocate has access to a knowledge base, and it is in the knowledge base that the knowledge is stored. It may be an individual knowledge base, in which case we can implement that idea that different advocates may have the different knowledge about the same object, or it may be a shared consensual knowledge base.

A proposition is represented as a map. So to minimise a proposition, for every value in that map, if the value is itself a map it shall be replaced by the value of the key `:id` in that map.

This means that every implementation of the `wildwood.knowledge-accessor/Accessor` protocol must transduce whatever token its backing store uses as the primary key for an object to `:id` when it performs a `fetch` operation.

## Thoughts on the shape of a knowledge base

The object of building Bialowieza as a library is that we should not constrain how applications which use the library store their knowledge. Rather, knowledge accessors must transduce between the representation used by the particular storage implementation and that defined in `wildwood.schema`. However, what we've described above suggests that a hierarchical database would be a very natural fit for knowlege base data - more natural, in this case, than a relational database.


