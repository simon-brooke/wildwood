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

    {:verb :kill :subject :brutus :object :caesar}

is a proposition which asserts that Brutus killed Caesar.

There may be many other privileged keys, such as

* `:location` - where did it happen? value might be something from which proximity may be derived;
* `:time` - when did it happen? This needs to be a value with a canonical order, such as a number;
* `:truth` - is it true? if present and value `false`, negates the proposition;
* `:confidence` - how sure am I? A value, perhaps, in the range -1 to 1, although conventionally if less than 1 we probably set the `:truth` value to false;
* `:data` - an argument structure...!
* `:authority` - id of agent from whom, or rule from which, I know this;

and so on. The exact set of privileged keys is probably actually a matter for
particular advocates rather than for the engine itself, although if the advocates
in the game don't broadly share the same set of privileged keys then it won't
work very well.

*However...*

The attentive reader will note that some of the proposed privileged keys map
closely onto the [Toulmin schema](Analysis.html#the-toulmin-schema). Thus we can say:

* that the proposition itself is a `claim` in the sense of the **C** term;
* that `:data` above is precisely `data` in the sense of the **D** term in Toulmin's schema, but may (is likely to) also provide a `warrant` in the sense of the **W** term;
* that `:truth` and `:confidence` are both `qualifiers` of the claim in the sense of the **Q** term;
* that `:authority` is a form of `backing` in the sense of the **B** term.

So what, then, is an 'argument structure', as described above? It seems to me
that it may be exactly a proposition, with the special feature that the value
of the `:data` key is not minimised.

Recall that in the chapter on Arboretum I observed that [the working of the DTree decision algorithm caused precisely those nodes to be collected whose fragments which provided the most relevant explanation](Arboretum.html#relevance-filtering) to support the decision, in a natural sequence from the general to the particular. I believe that precisely the same fortuitous alchemy will provide the argument structure to provide Toulmin's **D** - out `:data` term. The DTree itself then becomes the **W** - the `:warrant`; and the author of the DTree becomes the `:authority`.

{ **TODO**: investigate how this notion of a proposition - and a Toulmin structure - relates to situation semantics; especially, consider how locating a proposition in time and space captures the notion of a situation. }

#### Are located two-position propositions sufficient?

Aristotle's propositions are essentially two position: they describe a relationship between two entities, a subject and an object. But they're not located.

Thus:

* Socrates is a man

Tells us that Socrates is always, and everywhere, a man. If Socrates led a double life - if, perhaps, when in Lesbos, he lived as a woman - this simple proposition cannot capture the fact. Let's draw another example, to make clear.

* Brutus killed Caesar

tells us that, and only that, Brutus killed Caesar. It doesn't allow us to say how Brutus killed Caesar. If we then say

* Brutus used Dagger<sub>1</sub>

It still doesn't help, because we don't know how these two propositions relate to one another. Brutus could indeed have used this particular dagger at some time, it might well be the dagger that kille Caesar, but there's no smoking gun.

But if we say

1. Brutus killed Caesar in the Forum on the Ides of March
2. Brutus used Dagger<sub>1</sub> in the Forum on the Ides of March
3. Dagger<sub>1</sub> caused Wound<sub>1</sub> in the Forum on the Ides of March
4. Caesar died of Wound<sub>1</sub> in the Forum on the Ides of March

then provided the atomicity of our notions of time and space is sufficiently fine, we're getting pretty close.

Adding a notion of location to propositions leads to the notion of an event, a small bundle or ball of time and space which gives them context; and we can reason with this.

The reason I like the idea of investigating whether located two position propositions are sufficient is that a very regular knowlege representation is easy to compute over. The reason I think it might not be is this:

Suppose Calpurnia told Drusilla that Brutus killed Caesar in the Forum on the Ides of March. For simplicity, let's call

* Brutus killed Caesar in the Forum on the Ides of March.

'Proposition<sub>1</sub>', or 'P<sub>1</sub>' for short. What is the warrant for believing P<sub>1</sub>? Well, it's that Calpurnia told Drusilla (and presumably, that Drusilla has now told us).

So we have a notional event E<sub>1</sub> such that

* P<sub>1</sub> := 'Brutus killed Caesar in the Forum on the Ides of March.'
* P<sub>2</sub> := 'Calpurnia uttered P<sub>1</sub> at E<sub>1</sub>.'
* P<sub>3</sub> := 'Drusilla heard P<sub>1</sub> at E<sub>1</sub>.'

And the warrant for the belief that P<sub>1</sub> is the conjunction of P<sub>2</sub> and P<sub>3</sub>.

Writing it down like that, it kind of works, but I'm not yet wholly persuaded. It feels clumsy.

As an exercise for the reader, how would we represent 'Dirck, Joris and I carried the good news from Ghent to Aix' using only located two position propositions? It feels, as I said, clumsy.

There is, of course, also a lurking combinatorial explosion here. If for each proposition which is learned, two further propositions must be learned as warrant for the first proposition, the world blows up. In an ideal platonic world we may indeed have turtles all the way down, but in a finite machine we need to say, arbitrarily but ruthlessly, that some classes of proposition will be stored unwarranted.

#### Learning, consistency and confidence

{ **TODO**: if we receive a new proposition which confirms a proposition we already know, our confidence in both increases. If we learn a new one which contradicts one we already know, our confidence in both decreases. Expand!
}

#### Proposition minimisation

{ **TODO**: probably lose this. I increasingly think that, whatever the internal representation of the proposition within the advocate or knowledge base, the proposition as passed around must *always* be minimised. This is, in any case, very much an implementation detail. }

How are the values of `:subject`, `:object` and so on to be passed? If we pass
rich knowledge structures around, then we lose the insight that different
advocates may know different things about given objects. Thus, while internally
within each advocate's knowledge base objects may be stored with rich data, when
they're passed around in propositions they should be minimised - that is to say,
the value should just be a unique identifier, such that, for every object in the
domain, if an advocate knows anything at all about that object, it knows its
unique identifier and knows the object by that unique identifier.

Thus the unique identifier has something of the nature of a 'true name', in the
magical sense. A given true name, a given unique identifier, refers to precisely
one thing in the world, and provided that two advocates both know the same true
name, they can debats propositions which refer to the object with that true name.

Generally, a true name shall be a Clojure keyword. That keyword, passed to any
advocate in the game, shall identify either `nil` (the advocate knows nothing
of the object), or a map representing everything the advocate knows about the
object, and within that map, the value of the key `:id` shall be that true name.

But in saying 'the advocate knows', actually, the advocate knows nothing. The
advocate has access to a knowledge base, and it is in the knowledge base that
the knowledge is stored. It may be an individual knowledge base, in which case
we can implement that idea that different advocates may have the different
knowledge about the same object, or it may be a shared consensual knowledge
base.

A proposition is represented as a map. So to minimise a proposition, for every
value in that map, if the value is itself a map it shall be replaced by the
value of the key `:id` in that map.

This means that every implementation of the `wildwood.knowledge-accessor/Accessor`
protocol must transduce whatever token its backing store uses as the primary key
for an object to `:id` when it performs a `fetch` operation.

## Thoughts on the shape of a knowledge base

The object of building Bialowieza as a library is that we should not constrain
how applications which use the library store their knowledge. Rather, knowledge
accessors must transduce between the representation used by the particular
storage implementation and that defined in `wildwood.schema`. However, what
we've described above suggests that a hierarchical database would be a very
natural fit for knowlege base data - more natural, in this case, than a
relational database.

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

