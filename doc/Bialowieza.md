# Bialowieza

{ this chapter is in active development; quite a lot of the technical detail in this chapter at present will probably end up in [Implementing](Implementing.html), while additional high level and conceptual design, as it develops, will be here. }

## Why Bialowieza?

Bialowieza is the second iteration of the Wildwood engine, and this following convention its name should start with 'B'. [Białowieża](https://en.wikipedia.org/wiki/Bia%C5%82owie%C5%BCa) is Europe's last great wild wood, and it is currently under threat.

## Motivation

The current motivation for restarting work on Wildwood is to provide non-player characters in a game world with sufficient intelligence that they can enter into meaningful unscripted conversations about objects and events in that world.

## Knowledge representation

It's my intention that knowledge will be represented in Bialowieza not as Features, as were used in Arboretum, but as propositions, and in this section I intend to state what I mean by a proposition.

### On propositional and predicate calculi

But firstly I should be clear that Bialowieza does *not* represent a propositional calculus. On the contrary, Arboretum, which dealt with features, is a propositional calculus. How so?

A feature, in Arboretum, was a one-position predicate; an example would be, 'is a widow'. Propositions were (in effect) constructed by applying that predicate to the case. Thus, suppose we were considering the case of someone called Calpurnia, the feature 'is a widow' expands to the proposition 'Calpurnia is a widow'; and this proposition is, to Arboretum, atomic.

Thus Arboretum can reason about whether Calpurnia is a widow. It can have a rule (and indeed, did have a rule) which says

    if
      is a woman, and
      has been married, and
      husband is dead
    then
      is a widow
    unless
      was divorced, or
      has remarried.

The features are given as predicates, because all must apply to the same subject; expanding them to propositions gives us, in this case

    if
      Calpurnia is a woman, and
      Calpurnia has been married, and
      Calpurnia's husband is dead
    then
      Calpurnia is a widow
    unless
      Calpurnia was divorced, or
      Calpurnia has remarried.

However, another proposition about a claimant that might have been been interesting to an adjudicating officer might be 'is a widowed mother'. One might define that in common sense terms as

    if
      Calpurnia is a widow, and
      there exists some person whose mother is Calpurnia, and
      that person is still a child
    then
      Calpurnia is a widowed mother



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
* `:confidence` - how sure am I? A value, perhaps, in the range -1 to 1, where values less than one in effect represent a belief that the proposition is false;
* `:data` - a collection of (zero or more) argument structures;
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
* that `:confidence` is a `qualifier` of the claim in the sense of the **Q** term;
* that `:authority` is a form of `backing` in the sense of the **B** term.

So what, then, is an 'argument structure', as described above? It seems to me
that it may be exactly a proposition, with the special feature that the value
of the `:data` key is not minimised.

Recall that in the chapter on Arboretum I observed that [the working of the DTree decision algorithm caused precisely those nodes to be collected whose fragments which provided the most relevant explanation](Arboretum.html#relevance-filtering) to support the decision, in a natural sequence from the general to the particular. I believe that precisely the same fortuitous alchemy will provide the argument structure to provide Toulmin's **D** - out `:data` term. The DTree itself then becomes the **W** - the `:warrant`; and the author of the DTree becomes the `:authority`.

### The located proposition

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

then provided the atomicity of our notions of time and space is sufficiently fine, we're getting pretty close. Adding a notion of location to propositions leads to the notion of an event, a small bundle or ball of time and space which gives them context; and we can reason with this.

The size of an event is, of course, a slightly slippery notion. The inference that Caesar died (at least partly) from the blow struck by Brutus is only possible if the envelope of the event is fairly small - no more than a few metres, no more than a few minutes. But if we replace Dagger<sub>1</sub> with Rifle<sub>1</sub> then the spatial extent of the event can be considerable expanded; and if it's LandMine<sub>1</sub>, then the temporal aspect similarly so.

### Are located two-position propositions sufficient?

The reason I like the idea of investigating whether located two position propositions are sufficient is that a very regular knowlege representation is easy to compute over. The reason I think it might not be is that some things don't - at first glance - seem to fit naturally into this schema.

For example, suppose Calpurnia told Drusilla that Brutus killed Caesar in the Forum on the Ides of March. For simplicity, let's call

* Brutus killed Caesar in the Forum on the Ides of March.

'Proposition<sub>1</sub>', or 'P<sub>1</sub>' for short. What is the warrant for believing P<sub>1</sub>? Well, it's that Calpurnia told Drusilla (and presumably, that Drusilla has now told us).

So we have a notional event E<sub>1</sub> such that

* P<sub>1</sub> := 'Brutus killed Caesar in the Forum on the Ides of March.'
* P<sub>2</sub> := 'Calpurnia uttered P<sub>1</sub> at E<sub>1</sub>.'
* P<sub>3</sub> := 'Drusilla heard P<sub>1</sub> at E<sub>1</sub>.'

And the warrant for Drusilla's belief that P<sub>1</sub> is the conjunction of P<sub>2</sub> and P<sub>3</sub>.

So we can represent this as

```clojure
    {:verb :kill :subject :brutus :object :caesar :location :forum :date ides-of-march
      :data [{:verb :utter :subject :calpurnia
              :object {:verb :kill :subject :brutus :object :caesar :location :forum :date ides-of-march :authority :calpurnia}
              :authority :drusilla}
             {:verb :hear :subject :calpurnia
              :object {:verb :kill :subject :brutus :object :caesar :location :forum :date ides-of-march :authority :calpurnia} :authority :drusilla}]
      :authority :drusilla}
```

Taking it further to look at the sorts of things required by [The Great Game](https://simon-brooke.github.io/the-great-game/codox/), there are two classes of things which need frequently to be handled. one is the passing of news, in [gossip](https://blog.journeyman.cc/2008/04/the-spread-of-knowledge-in-large-game.html); and the other is recording trading information, in [markets](https://simon-brooke.github.io/the-great-game/codox/the-great-game.merchants.markets.html).

The **gossip** case is covered by the *Calpurnia told Drusilla* example given above, leaving the markets case. When trading in markets we need three things: who sold the consignment, who bought the consignment, and the unit price of the commodity. Note that putting it like that makes a `consignment` a first class entity, with properties of at least `commodity` and `quantity`.

So, again, we have a notional event E<sub>2</sub> at which a commodity C<sub>1</sub> was traded, where E<sub>2</sub> has (for example) a location of `Forfar` and a timestamp of 202005181600; while C<sub>1</sub> has a commodity of `Fish` and a quantity of `50`. Let's say the price was 4 bronze coins per kilo.

This transaction could be represented by the propositions:

* P<sub>4</sub> := 'Donald sold 50 Kg of Fish in Forfar at 202005181600'
* P<sub>5</sub> := 'Fiona bought 50 Kg of Fish in Forfar at 202005181600'
* P<sub>6</sub> := 'Fish sold for 4 bronze/Kg in Forfar at 202005181600'

So it seems to me that it's possible to represent the things I know I need to represent, using only located two-position propositions.

As an exercise for the reader, how would we represent 'Dirck, Joris and I carried the good news from Ghent to Aix' using only located two position propositions? It feels, as I said, clumsy.

There is, of course, also a lurking combinatorial explosion here. If for each proposition which is learned, two further propositions must be learned as warrant for the first proposition, the world blows up. In an ideal platonic universe we may indeed have turtles all the way down, but in a finite machine we need to say, arbitrarily but ruthlessly, that some classes of proposition will be stored unwarranted.

### The event is (like) a situation

I would draw the reader's attention, here, to the similarity between the notion of an event, discussed above, and the [notion of a situation](Analysis.html#towards-a-situation-semantic-account-of-explanation), as discussed by Barwise & Perry. In particular, I'd like to draw attention to similarity between the account I've given of how Drusilla's belief that Brutus killed Caesar is warranted by

* P<sub>2</sub> := 'Calpurnia uttered P<sub>1</sub> at E<sub>1</sub>.'
* P<sub>3</sub> := 'Drusilla heard P<sub>1</sub> at E<sub>1</sub>.'

and the account of explanation as a situation E defined:

    E := at I1: understands, a, c; no
        understands, b, c; yes
        enquirlng, a; yes
        addressing, a, b; yes
        saying, a, q; yes
        subject, q, c; yes

      at I2: responding, b, q; yes
        addressing, b, a; yes
        saying, b, u; yes
        subject, u, c; yes

      at I3: understands, a, c; yes
        understands, b, c; yes

      I1 < I2 < I3

where: a, b are some actors; c is some concept; q, u are some
utterances.

Explicitly:

* Drusilla => **a**
* Calpurnia => **b**
* P<sub>1</sub> => **c**
* E<sub>1</sub> => **I2**

I'd argue that these are clearly very similar.

My schema does not specify that "at I1: ... understands, a, c; no", but there's a reason for that.

### Learning, consistency, trust and confidence

Let us suppose that Drusilla already knows the proposition that

* Brutus killed Caesar in Rome in March.

Calpurnia now tells her that

* Brutus killed Caesar in the Forum on the Ides of March.

The two accounts are compatible; this compatibility might be represented, if you choose, by two further propositions:

* P<sub>4</sub> The Forum is within Rome.
* P<sub>5</sub> The Ides of March is within March.

Philosophers, after Plato, very often argue as though they inhabited ideal universes in which every agent always tells the truth, but the real world is not like that. For any person, there are few other people that that person trusts implicitly. The very notion that we need a warrant for a belief is an explicit recognition of the fact that knowledge is imperfect.

So (unless she witnessed it herself, in which case you'd expect her to have more precise information), Drusilla does not *know*, in a strong sense, that Brutus killed Caesar in Rome in March. She has some degree of confidence in that proposition, which is likely to be less than perfect.

When she learns from Calpurnia that Brutus killed Caesar in the Forum on the Ides of March, because the two claims are compatible, her confidence in each is likely to increase.

By contrast, if Falco then says 'No, I heard from Gaius that it happened in April', then that casts doubt on both the first two claims - but also on this new claim. Because the claims are not compatible, they can't all be right.

For the time being, I'm going to leave the issue of how confidence is derived and adjusted as an implementation detail; I don't - yet, at any rate - have an account of how this should work that I can defend. There are two main avenues to explore,

1. [The Kolmogorov axioms](https://en.wikipedia.org/wiki/Probability_axioms);
2. [Cox's theorem](https://en.wikipedia.org/wiki/Cox%27s_theorem)

However, I haven't yet worked out what the implications of either of these are for my schema.

However, there's one further significant point to make about propositions before we move on.

### On the subtext of propositions

Propositions are not atomic. They do not come single spies, but freighted
with battalions of inferable subtexts. Suppose Calpurnia says

* Brutus killed Caesar in Rome during the ides of March

I learn more than just that 'Brutus killed Caesar in Rome during the
ides of March'. I also learn that

* Brutus is a killer
* Caesar has been killed
* Rome is a place where killings happen
* The Ides of March are a time to be extra cautious

Suppose Cassius now says

* Longus killed Caesar in Rome during the ides of March

this may cast some doubt on Calpurnia's primary claim, and on the belief that
Brutus is a killer. It doesn't rule it out - the accounts are compatible - but it
certainly doesn't confirm it. However it does reinforce the beliefs that

* Caesar has been killed
* Rome is a place where killings happen
* The ides of March are a time to be extra cautious.

If Falco then says

* No, I heard from Gaius that it happened in April

the beliefs that

* Caesar has been killed
* Rome is a place where killings happen

are still further strengthened.

In proposing a formalism to express propositions, we need to consider how
it allows this freight to be unpacked.

### Conjunctions, disjunctions, and confidence

Suppose we have an impeccable witness - one in whom we have 100% confidence - who tells us

* Brutus, Cassius **and** Longus killed Caesar in the Forum on the Ides of March

From this we learn - all with 100% confidence - that

* Brutus is a killer
* Cassius is a killer
* Longus is a killer
* Caesar has been killed
* The Forum is a place where killings happen
* The Ides of March are a time to be extra cautious

And there are no problems there. But suppose, instead, our witness had said

* Brutus, Cassius **or** Longus killed Caesar in the Forum on the Ides of March

What do we learn? Natural English does not distinguish between inclusive and exclusive disjunction. So we still know, with 100% certainty

* Caesar has been killed
* The Forum is a place where killings happen
* The Ides of March are a time to be extra cautious

Do we know with any confidence at all that `Brutus is a killer`? We know with certainty that one of the three is a killer; there is a possibility (inclusive `or`) that all are killers. So it seems to me, naively, that we can at least divide the confidence we have in the initial, composite proposition by the number of disjuncts.

So we now have

* Brutus is a killer (33%)
* Cassius is a killer (33%)
* Longus is a killer (33%)
* Caesar has been killed (100%)
* The Forum is a place where killings happen (100%)
* The Ides of March are a time to be extra cautious (100%)

But wait.

What happens if we learn from another impeccable source that `Longus is not a killer`. What does that say about whether Brutus is? Obviously the confidence in the idea that `Brutus is a killer` must increase, so the three propositions

* Brutus is a killer
* Cassius is a killer
* Longus is a killer

are not distict. Change in confidence in one implies change in confidence in the others. But how?

Suppose instead our second impeccable source had instead confirmed that `Longus killed Caesar in the Forum`. By the logic that said that the negation of this would cause our confidence in the possibility that `Brutus is a killer` should increase, surely now it must decrease?

But wait further.

Suppose our second impeccable source says `Cassius and Longus killed Caesar in the Forum`. Now we know that the **or** - the disjunction - was not exclusive. We know that two of the accused are guilty. What, if anything, does this say about Brutus? Is our confidence that he, too, is a killer, increased or decreased by this? I cannot see any justification for either.

#### TODO: read

* http://web.mit.edu/24.954/www/files/simons.disjunction.pdf
* https://link.springer.com/chapter/10.1007/BFb0031745


### Universally located and unlocated propositions

In the last passage, I observed that if we had multiple competing reported locations at which Caesar was said to have been killed, then there was nevertheless agreement on the fact that Caesar had been killed; so while we might be in doubt about whether the death had happened in March or June, there's much less doubt that the death happened.

So we have a hierarchy of confidence in the propositions:

1. We're reasonably certain that Caesar was killed;
2. We may think it probable that he was killed in March, since we have two independent sources for this;
3. We're probably more skeptical that he was killed in April, since the report is second hand and since it's inconsistent with the others.

Of these, the first proposition is not located in time. There must have been an event, but we don't know when it was. Strictly, of course, we could in this case assign a broad event - 'the spring' - but what I want to talk about here is cases of things which we believe have happened, but do not know when. The reason I want to talk about them is to draw a clear distinction between unlocated propositions, and universally located propositions.

A universally located proposition is something like

* Water is wet.

Or, to use a more classical example,

* Socrates is a man.

For the purposes of the pedagogy of logic, Socrates is always and everywhere a man. His manliness is universally located; thus the proposition 'Socrates is a man' is always deemed to be true. Consider the proposition 'Socrates was hungry'. This is also, almost certainly true. But he wasn't hungry for all time, he was hungry at most for short periods during his lifetime. There's an important disctinction between

* Socrates is a man

and

* Socrates was hungry

and we need to make that explicit, so that we know whether we're asserting an unlocated proposition or a universal one. I'm going to elect that it is the universal that should always be explicitly stated, so we have

* Water is wet in the universe in all time;
* Socrates is a man in the universe in all time;
* Socrates was hungry.

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

## Major components of Bialowieza

### Knowledge Accessors

The `wildwood.knowledge-accessor/Accessor` protocol defines a bidirectional transducer which can fetch data from whatever storage representation the calling application uses into the representation defined by `wildwood.schema`.

### Advocates

The `wildwood.advocate/Advocate` protocol describes an agent which can take part in decision processes.

### The engine itself

The engine is implemented by the namespace `wildwood.bialowieza`.

### Inference processes

Advocates are entitled to use whatever inference processes they like, but they have access to `wildwood.dengine`, which is an implementation of the DTree engine described in the chapter on [Arboretum](Arboretum.html) adapted to propositions as defined in `wildwood.schema`.

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

