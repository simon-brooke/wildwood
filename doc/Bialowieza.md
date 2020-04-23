# Bialowieza

{ this chapter is in active development; quite a lot of the technical detail in this chapter at present will probably end up in [Implementing](Implementing.html), while additional high level and conceptual design, as it develops, will be here. }

## Why Bialowieza?

Bialowieza is the second iteration of the Wildwood engine, and this following convention its name should start with 'B'. [Białowieża](https://en.wikipedia.org/wiki/Bia%C5%82owie%C5%BCa) is Europe's last great wild wood, and it is currently under threat.

## Motivation

The current motivation for restarting work on Wildwood is to provide non-player characters in a game world with sufficient intelligence that they can enter into meaningful unscripted conversations about objects and events in that world.

## Major components of Bialowieza

### Knowledge Accessors

The `wildwood.knowledge-accessor/Accessor` protocol defines a bidirectional transducer which can fetch data from whatever storage representation the calling application uses into the representation defined by `wildwood.schema`.

### Advocates

The `wildwood.advocate/Advocate` protocol describes an agent which can take part in decision processes.

### The engine itself

The engine is implemented by the namespace `wildwood.bialowieza`.

### Inference processes

Advocates are entitled to use whatever inference processes they like, but they have access to `wildwood.dengine`, which is an implementation of the DTree engine described in the chapter on [Arboretum](Arboretum.html) adapted to propositions as defined in `wildwood.schema`.

### Propositions

{ **TODO** read, and follow references from, https://plato.stanford.edu/entries/propositions/ }

As a slightly tendentious first stab, a proposition is a sentence which is either true or false. This is tendentious because two different sentences which have the same underlying semantic import are usually considered to be instances of the same proposition, and seen from the other end, there may be many ways you can validly express a single proposition in a single natural language. However, for the present purpose, the proposition that a proposition is a sentence is good enough.

A sentence generally has the form

    <verb subject object>

with possibly a lot of other qualifying material.

So we shall say that a proposition will be represented as a Clojure map with at least the keys:

* `:verb` - what is asserted
* `:subject` - of whom (or what)
* `:object` - to whom (or what)

Thus

    {:verb :killed :subject :brutus :object :caesar}

is a proposition which asserts that Brutus killed Caesar.

There may be many other privileged keys, such as

* `:location` - where did it happen? value might be something from which proximity may be derived;
* `:time` - when did it happen? This needs to be a value with a canonical order, such as a number;
* `:truth` - is it true? if present and value `false`, negates the proposition;
* `:confidence` - how sure am I? A value, perhaps, in the range -1 to 1, although conventionally if less than 1 we probably set the `:truth` value to false;
* `:data` - an argument structure...!
* `:authority` - id of agent from whom, or rule from which, I know this;

and so on. The exact set of privileged keys is probably actually a matter for particular advocates rather than for the engine itself, although if the advocates in the game don't broadly share the same set of privileged keys then it won't work very well.

*However...*

The attentive reader will note that some of the proposed privileged keys map closely onto the [Toulmin schema](Analysis.html#the-toulmin-schema). Thus we can say:

* that the proposition itself is a `claim` in the sense of the **C** term;
* that `:data` above is precisely `data` in the sense of the **D** term in Toulmin's schema, but may (is likely to) also provide a `warrant` in the sense of the **W** term;
* that `:truth` and `:confidence` are both `qualifiers` of the claim in the sense of the **Q** term;
* that `:authority` is a form of `backing` in the sense of the **B** term.

So what, then, is an 'argument structure', as described above? It seems to me that it may be exactly a proposition, with the special feature that the value of the `:data` key is not minimised.

#### Proposition minimisation

How are the values of `:subject`, `:object` and so on to be passed? If we pass rich knowledge structures around, then we lose the insight that different advocates may know different things about given objects. Thus, while internally within each advocate's knowledge base objects may be stored with rich data, when they're passed around in propositions they should be minimised - that is to say, the value should just be a unique identifier, such that, for every object in the domain, if an advocate knows anything at all about that object, it knows its unique identifier and knows the object by that unique identifier.

Thus the unique identifier has something of the nature of a 'true name', in the magical sense. A given true name, a given unique identifier, refers to precisely one thing in the world, and provided that two advocates both know the same true name, they can debats propositions which refer to the object with that true name.

Generally, a true name shall be a Clojure keyword. That keyword, passed to any advocate in the game, shall identify either `nil` (the advocate knows nothing of the object), or a map representing everything the advocate knows about the object, and within that map, the value of the key `:id` shall be that true name.

But in saying 'the advocate knows', actually, the advocate knows nothing. The advocate has access to a knowledge base, and it is in the knowledge base that the knowledge is stored. It may be an individual knowledge base, in which case we can implement that idea that different advocates may have the different knowledge about the same object, or it may be a shared consensual knowledge base.

A proposition is represented as a map. So to minimise a proposition, for every value in that map, if the value is itself a map it shall be replaced by the value of the key `:id` in that map.

This means that every implementation of the `wildwood.knowledge-accessor/Accessor` protocol must transduce whatever token its backing store uses as the primary key for an object to `:id` when it performs a `fetch` operation.

## Thoughts on the shape of a knowledge base

The object of building Bialowieza as a library is that we should not constrain how applications which use the library store their knowledge. Rather, knowledge accessors must transduce between the representation used by the particular storage implementation and that defined in `wildwood.schema`. However, what we've described above suggests that a hierarchical database would be a very natural fit for knowlege base data - more natural, in this case, than a relational database.

## Prejudice, and defaults

In Arboretum and later in KnacqTools, default values of features were determined by the 'knowledge engineer', normally by asking the domain expert, and were fixed for the knowledge base at all times. But these two programs each reasoned about one case at a time, and did not store knowledge about multiple cases.

These systems could thus be said to be *prejudiced*, to the extent that knowledge of the world acquired over time did not change their default judgements. Wildwood holds knowledge on potentially very many objects, and that knowledge may change dynamically over time, both as the world changes and as new things which already existed in the world become known.

Suppose we wish to decide the truth value of the proposition

    {:verb :is :subject :brutus :object :honourable}

Suppose we have no rule for this, so we want to use a default. Suppose we scan all the propositions in the knowledge base which match the pattern

    {:verb :is :object :honourable}

and divide them into two sets, those which assert `true` and those which assert `false`.

If the arity of the true set is greater than the arity of the false set, then the default is `true` (and vice versa). But we can derive more than this: we can derive a confidence score, based on the ratio of true instances to false instances.

The problem is that this is computationally expensive. So we need to cache default values. Different advocates may use different caching strategies, with different 'time to live'. The longer the time to live of a cached default, the more prejudiced - the less agile in learning - the advocate will be; but the faster it will be able to produce decisions.

