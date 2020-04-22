# Arden

## Why Arden?

It was something of tradition in the InterLisp-D community to give successive versions of a project codenames with successive alphabetical initials. So the first version would have a name starting 'A', the second 'B', and so on. The first prototype for Wildwood was called 'Arden', because it starts with an 'A', and because it is a fantastical dream-like forest depicted in Shakespeare's play 'As You Like It', which if I recall correctly was performed as a promenade performance by the Duke's Theatre in Lancaster in that year. While Arboretum - that carefully tended garden of trees - had been, as I've said, largely Peter's in concept, Wildwood would be mine.

## Background

After work had finished on [Arboretum](Arboretum.html), I started at once to think how the ideas we had implemented could be improved. However, academic work is a job, and I was a significant part of the programming resource of the Alvey DHSS Large Demonstrator Project. I was switched to a different task within the project, the 'Policy Prototype', a constraints-based system intended to assist DHSS policy officers in the formulation of new policy.

From my perspective, the great frustration of my time with the Alvey project was that, at the very start, we'd had a meeting at which people had been asked whether they could program or whether they could not. I, of course, could. But, actually, at least on the academic teams within the project, only a few, and only junior people admitted that they could (and, strictly, this was mostly honest - those who said they couldn't, couldn't). But this left us with a very small pool of developers, and a much larger pool of analysts.

Peter Mott and I had worked largely experimentally, developing our own design insights on the creation of Arboretum. I should say that while the implementation was mine, the ideas and the insights were mostly Peter's. But we worked together as a great team, and felt that we were building something which was genuinely powerful and exciting. Arboretum was completed in less than a year, between September 1986 and June 1987, and it worked. It felt like a success. We had achieved something.

While we were doing this, an enormous and ambitious specification was being written for the Policy Prototype by people who had little idea how it might be implemented. Three people, myself and two others, were assigned to build it. It was rapidly apparent that the specification was in many areas incomplete, in some areas inconsistent, and the bits that were implementable were vastly too expensive of compute power to run efficiently on our hardware. Over the course of a year, there were repeated demonstrations within the project of progress so far, and there was always disappointment from the senior members of the team - again, especially on the academic side.

It's worth saying that we were well supported by the commercial team in ICL, of whom I'd especially note Charlie Portman, Chris Burton and Nick Perry. Chris was a very old hand: he'd worked, as an apprentice, on the original build of the [Manchester Mark 1 ('Baby')](https://en.wikipedia.org/wiki/Manchester_Mark_1), and, later, after he had retired from ICL, would go on to lead the team which rebuilt it. Charlie was a very senior software person and also an excellent man manager. He'd led the operating systems design team within ICL, and had been responsible for (among many other things) the [VME operating system environment](https://en.wikipedia.org/wiki/ICL_VME). Nick was the geek's geek: someone we would now recognise as, like me, very autistic. I learned a lot from these people, and greatly appreciated their support.

As deadlines approached for the Policy Prototype, the three of us who were actually building it entered what would now be known as a four month 'crunch' - trying to build a system with too little manpower on inadequate hardware under immense pressure. I worked regularly sixteen hours a day, including weekends. However, by the time the Alvey conference was due, major parts of the prototype didn't work, and the parts which did work were so slow as to be unusable.

This wasn't a pleasant experience.

It was under these conditions that I started on the design of Wildwood, and most of what there is of this chapter were written. I still had access to InterLisp-D, which is a wonderful programming environment; I built a prototype I called Arden, which implemented some of the ideas; Arden was to be the first iteration of the Wildwood project. And I started writing this thesis, in support of it.

But the pressure of the Policy Prototype had been too much; I burned out, left the project, and worked for a year as a research fellow in the Department of Computing at Lancaster, building a noddy natural language query interface for relational databases in Prolog, which worked but didn't excite me. I lost access to the InterLisp-D machines, and consequently work on Arden came to an end. I don't believe I now have even floppy disks of the source code, and I certainly don't have a machine which could read them.

In 1989, after the year building the query interface, Peter Mott, myself, and some other ex-students of Peter's set up a company, Common Knowledge, to commercialise the work we'd done on Arboretum, because we thought it was both interesting and important, but the commercial partners in the Alvey DHSS Large Demonstrator Project were not interested in taking it further. ICL was entering a spiral of decline, and had no energy for experimental projects; I think they too felt burned by the experience of the Policy Prototype.

At Common Knowledge, I was appointed Managing Director and also techincal lead. It was in this period that we built KnacqTools, described in a separate chapter. The Wildwood project went on hold and has remained so until 2020.

## The ideas in Arden: starting to play

**TODO**: write something.

## Explanation is a social behaviour

**TODO**: write something.

## Games are social behaviours which can be modeled

**TODO**: write something.

## Explanation can be viewed as a game-playing activity

One of the models we frequently use in real life situations where a
decision has to be made on the basis of uncertain or conflicting
evidence is the dialectic model. In this, one authority proposes a
decision, giving an argument which supports it; another authority brings
forward objections to the argument, and may propose an alternative
decision. This process may continue recursively until there is no
further argument which can be advanced.

This is the model which underlies both academic and parliamentary
debate, and the proceedings of the law courts. The model supports a
'decision support' conception of the Expert System's role, rather than a
'decision-making' one: the user is placed in a position analogous to
that of a jury, considering the arguments presented by the advocates,
and making a decision on the basis of them.

We can view this dialectic approach to decision support as a game, with
a fixed set of legitimate moves.

## Hintikka's game theoretic logic

This approach seems very promising. It may tie in well with
game-theoretic logics such as those of Ehrenfeucht \[Ehrenfeucht 61\]
and Hintikka \[Hintikka & Kulas, 83 and passim\] Of course, these logics
are strictly monotonic. Problem areas where monotonic logics are
adequate are of limited interest, and unlikely to give rise to
interesting problems of explanation; but it should be possible to
develop a provable semantics for a non-monotonic extension. The decision
procedure for a game theoretic logic should not raise any particular
difficulties; the 'games' are zero sum, under complete information.

However, the adoption of a game-theoretic explanation mechanism does not
commit us to adopting a game theoretic logic; so (although interesting)
this line will only be pursued if it proves easy.

## The object of an explanation game

If explanation is to be seen as a game, what is to be its objective? If
we were to take a positivist position, we might say that the purpose of
explanation was to convey understanding; then the game would be a
co-operative one, with the object of both players being to achieve this
end.

But seeing that we have rejected this position, the object of an
explanation must be to persuade.

Furthermore, if we accept the argument advanced in chapter iii above
that explanation is hegemonistic, the explanee may be seen to have an
object in resisting explanation. So now we can have s clear idea of what
the object of an explanation game will be: The explainer will win, if
the explainee is brought to the point of no longer being able to produce
a rational reason for resisting the explanation. Otherwise, if when all
the supporting arguments the explainer can bring to bear have been
advanced the explainee is still unconvinced, then the explainee has won.

'Rational', here, is of course a fix. In an explanation situation
involving two human beings, the explainee might continue to resist even
if there were no 'rational’ reason for doing so. This situation would be
non-deterministic, though, and too difficult for me to deal with.

## Legitimate moves in an explanation game

A possible set of legitimate moves which might be used as the basis for
an explanation generator in a non-monotonic expert system might be:

### PRESENT

- present a case, supported by an argument, which may include one or
more assumptions.

Generally, in a non-monotonic system, we are dealing with incomplete
information \[and indeed, this is generally the case in 'real life').
Consequently, any conclusion is likely to be based upon one or more
unsupported assumptions.

\[starting move\]

### DOUBT

- challenge one or more of the assumptions.

\[basic response to PRESENT, REBUT, or COUNTER-EXAMPLE\]

### DOUBT-IRRELEVANT

- claim that if the assumption were false, it would make no difference
to the decision.

\[blocking response to DOUBT\]

### ASSUMPTION-PROBABLE

- claim that the assumption is probable on the basis of prior
experience.

\[weaker response to DOUBT\]

### REBUT

- this is essentially a PRESENT, but of an argument based on the
opposite assumption to that challenged in the preceding DOUBT.

\[response to a PASS following a DOUBT\]

### COUNTER-EXAMPLE

- present a known case similar to the current one, where the predicate
of the assumption which has been challenged holds the opposite value.

\[response to ASSUMPTION-PROBABLE\]

### PASS

- a null move in response to any other move. Forces other player to
move. In some sense this might be seen as equivalent to saying 'so
what': the opponent has made a move, but there is nothing further which
the player wants to advance at this stage. This move is inherently
dangerous, because the game ends when the two players PASS in succesive
moves.

\[weakest response to anything\]

Obviously, in human dialectic, other legitimate moves attack the
validity of

inference steps; but for a machine implementation, we can assume all
inference steps are in themselves valid.

Implementing a game theoretic approach to explanation
======================================================

Playing the explanation game
-----------------------------

The game playing algorithm required to implement an explanation system
need not be sophisticated: indeed, it must not be too sophisticated, at
least as a game player. Consider: a good game playing algorithm looks
ahead to calculate what the probable responses to the moves it makes
will be. If it sees that one possible move leads to a dead end, it will
not make that move.

But in generating an explanation, we need to explore any path whose
conclusion is not immediately obvious. So either the game-playing
algorithm should not look ahead more than, perhaps, one ply, or else, if
it does so, then when it sees a 'closed path', it should print out a
message something of the form:

Critic: Oh yes, I see that even if the assumption that Carol hasTwoLegs
is false it would not affect the assumption that Carol canSwim.

Initially, however, I propose to implement a game-playing algorithm
which simply makes the strongest move available to the player, based on
a scoring scheme which does not look ahead. The game will proceed
something like tennis; the player who last made a PRESENT or REBUT move
will have the initiative, the 'serve' as it were, and need only defend
to win. The other player must try to 'break' this 'serve', by forcing a
DOUBT...PASS sequence, which allows a REBUT play. Dialectic explanation
- the two player game.

With this schema, we can mechanize a dialectic about a case. The game is
played by the machine against itself; 'agents' in the machine play moves
in turn until both sequentially pass, or until the user interrupts. Each
move produces a natural language statement on the display. Ideally, the
user could interrupt at any time, either to request further
justification for a move, or to halt the process.

As a (simple) example of what might be produced, here is some sample
output from a very crude prototype system :

User: K- \#\$ Carol Explain \#\$CanSwim)

\[send to the object called Carol a message asking for an explanation of
the value Carol holds for the predicate named

CanSwim \]

Consultant: What is the value of CanSwim for Carol?

\[ 'Consultant' and 'Critic' are the names given to the two 'agents' -
the knowledge base doesn't contain the relevant information, so it must
be inferred. \]

User: Don't know...

Consultant: I found that CanSwim was true of Carol by Default using the
following assumptions:

(I assumed \#ScanSwim was true of Carol)

My reasons are:

CanSwim is true of most Mammals; Carol is a Mammal; Therefore it is
probable that CanSwim

is true of Carol.

Critic: The assumption that CanSwim is true is unsafe: Henry is also of
type (s) (Person) but

CanSwim is false of Henry.

This is output from Wildwood version Arden, my first prototype
object-based inference mechanism. It DOES NOT implement the game-playing
model given.

This example shows 'Consultant' PRESENTing a case based on a single
assumption, and 'Critic' responding with a COUNTER-EXAMPLE - a move
which would not be legal under the scheme given. However, this should
give some indication of how the idea might be implemented.

{**TODO**: figure}

Interaction Trace

User: \[QU\] Is it true that Carol canSwim?

Consultant: \[PR\] I assume that carol canSwim.

User: \[DO\] I doubt that...

Consultant: \[AP\] It is probable that Carol canSwim because:

can Swim is true of most mammals, and Carol is a mammal

Options... Counter

QUery

Andrew Present

Belinda Doubt

Charles Doubl relevant Diana Assumption Prob Frederick Rebut

Ginny Counter-Exam Henry PAss

Tris James Katharine Leonard

User:

fig 5.1: Showing how the Critic could guide the user in interacting with
the Consultant. The Consultant has made an 'Assumption Probable' move
(marked \[AP\]). The Critic is guiding the user firstly by greying the
illegal moves, and secondly by indicating with a tick the preferred
move. The user has selected this (shown by the boxing on the options
menu) and the Critic has opened a menu of all the objects which might be
advanced as valid counter-examples, again indicating the preferred choice.

The simple model described above has not achieved all that we want,
however. The machine, in the form of the agent Consultant' has attempted
to explain to itself, wearing its 'Critic' hat. The user's role has
simply been that of passive observer. The first enhancement would be to
give the user control of the 'critic'; but because the user cannot have
the same view of the knowledge base as the machine, the Critic will have
to guide the user, firstly by showing the user the legal moves
available, and second by finding supplementary information to support
these moves.

Explanation as conversation - a three player game
--------------------------------------------------

A better design still might be to allow the machine to carry on its
dialogue as before, but to allow the user to interrupt and challenge
either of the agents. It seems probable that the most interesting moves
the user could make would be DOUBT, and also COUNTER-EXAMPLE, where the
user could present an example case not previously known to the machine.
Now the user can sit back and watch, if the conversation brings out the
points which seem relevant; but equally the user can intervene to steer
the conversation.

These ideas had not, needless to say, yet been implemented in 1988 when
I moved onto working on KnacqTools.

I hope that they will be implemented as I develop the ideas in this
document.
