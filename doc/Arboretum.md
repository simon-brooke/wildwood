# Arboretum

This chapter describes briefly an inference mechanism, implemented in
the Arboretum prototype; this is included here to show the results
achieved in the author's early work on explanation, on which it is hoped
to build in the current work. A fuller description of this mechanism,
and of the Arboretum prototype, will be found in \[Mott & Brooke 87\],
from which this chapter is largely drawn.

Arboretum was written in InterLisp-D\[4\] using LOOPS \[5\] object
oriented facilities, to allow people to manipulate DTree structures
through graphical representations: to build arbitrarily large knowledge
bases, to use these to provide answers to questions about objects in
domains admitting incomplete information - and to provide natural
language explanations of these answers. The inference process by which
answers are produced is shown as an animated graph. The user can ask the
system how the value of any particular feature was arrived at, and what
that value was. . It was developed for the Alvey DHSS Large Demonstrator
Project, and sought to meet early perceptions of the needs of DHSS
Adjudication Officers. Adjudication Officers decide claimants'
eligibility over a wide range of welfare benefits. There is a very large
volume of work to be done, so they work under considerable pressure.

The Adjudication process within the DHSS has its own levels of authority
culminating in the

Office of the Chief Adjudication Officer. This monitors the quality of
the decision making of Adjudication Officers, issues written guidelines
to assist them in their decision making, and distributes information
about changes in the benefit rules.

These modifications occur frequently, both to correct local anomalies
and of course to reflect more substantial political changes in the
provision of welfare. A static support system could not be useful
because it would become outdated almost immediately. What is required is
a system that can be modified and updated easily by the officials in the
O.C.A.O.

The general context is then of a large organisation in which relatively
numerous junior officials apply extensive and frequently changed
legislation in limited time under the supervision of relatively few
senior officials. The task of an IKBS support system is to maintain and
if possible improve the decision making quality of the former and render
more effective the supervision exercised by the latter.

We designed a system to meet these perceived needs around the DTree
algorithm devised by Peter Mott. This algorithm interprets graphical
data-structures (called 'DTrees') as rules of a non-monotonic (default)
logic.

Our intention was that the system should access a legislative rule base
composed of such rules, should propose a decision for the case in
question, and should then draft a letter explaining that decision to the
claimant. If the case was straightforward the Adjudication Officer would
accept the suggestion offered by the machine. Otherwise (s)he could
study a graphically presented trace of the machine's inference process
to assist in formulating an alternative decision.

One of the most significant advantages we would claim for the system as
implemented is that it is extremely simple for relatively naive users to
build knowledge bases. Knowledge engineering with this system requires
no understanding of computer language, no very abstruse knowledge about
the machine, and no complex calculation of weightings and probabilities.
The logic is designed to facilitate a methodology which we call
Elicitation by Exception.

The knowledge engineer's task, using this methodology to build a rule,
is simply to ask: is a given predicate normally true? If it were true,
would it normally be a T sufficient condition? And having got this far,
is there anything at all which would overturn the decision? If nothing
could, the process terminates; but if there is some more abstruse factor
which could still cause a change of mind, then that is added as a new
condition and once more one asks if there is anything further that could
cause a change of mind. Thus we proceed down a conceptual tree where at
each level the decision just made is reversed.

Intuitively the deeper the level, the more unlikely the situations that
occupy that level. It is our suggestion that the structure of exceptions
that can explicitly be recovered by the knowledge engineer in this way
is what is implicitly and imperfectly represented by the certainty
factors in classical expert systems.

These trees can then be directly interpreted as rules by the DTree
algorithm, the root idea of which is that a decision has always been
made, there is always an answer available, but one which the system is
currently trying to refute. The eventual decision is simply the last one
made, the one that the system has failed to refute. At any point it
tries to "change its mind", and when it can no longer do so that is the
decision it delivers. After all, if there is nothing as yet unexamined
that could make you change your mind why deliberate further, while if
there is how may you legitimately stop? The idea of an alternating
'yes/no' with decision characterised simply by its position at the end
is a very old one indeed due to Thomas Hobbes (16\]. The emphasis on
trying to refute rather than trying to confirm is of course Popperian
(passim, but see for example his \[17\]).

Let us summarise how to read a DTree rule structure. The basic units are
nodes and the edges between them. An edge should always be read
downwards, and, when connecting different colours, as meaning 'unless'.
Thus the most basic structure is "hypothesis is false unless condition
is true":

Basic DTree

Hypothesis -

Condition +

fig 3: simplest possible rule Conjunctions are represented by columns of
nodes, only the last of which has the colour to be returned if all are
true and disjunctions by branches, each of which terminates in the
colour to be returned if any are true. These can be combined in any
fashion desired, although we consider it good practise to keep
individual rule structures small. This is shown in the figure below:

Example DTree

Root Node -

1st Conjunct -

2nd Conjunct +

1st Disjunct -

2nd Disjunct -

fig 4: example rule, showing syntax The rule would read: "(rootnode) is
false unless (first conjunct) is true and (second conjunct) is true, in
which case it is true unless either (first disjunct) or (second
disjunct) is true".

A DTree system contains at any time a number of features, objects and
nodes. In a LISP implementation these are litatoms equipped with
property lists; the LOOPS implementation is rather different but not in
any way that affects the underlying ideas.

A feature has the properties:

    (methods DTreeRootnode default activeFlg)

where methods is the list of
methods that the feature applies to see if it holds of the given object,
DTreeRootnode holds a pointer to a DTree node if the feature is equipped
with one (else NIL), default holds the default value of the feature, and
activeFlg is simply a semaphore to prevent DTrees calling themselves.

A word more about the methods property. This may in principle hold a
list of any functions that are to be applied (in the order found) with
standard arguments by the feature when it is called. A feature may thus
be seen as a local "expert" whose task is to decide whether it holds of
a given object by applying the list of methods it has been supplied with
(this picture was suggested by Lenat \[18\]). However, for the sake of
simplicity, we shall henceforth assume, that the methods property is
just the list (DoTree Default) and describe the algorithms accordingly.

A DTree for a feature is a list of nodes. A node has the properties:

    (feature, colour, children, parent)

Feature points to a feature of the
system, colour is either Yes or No, children point to successor nodes of
the node (if any) and parent to the unique predescessor node (NIL for
the origin of the DTree).

An object is just a litatom. The system updates the property list of the
object.

The top-level function call is (Decide Feat feature object). This first
looks on the property list of object. If it finds (feature. Yes) or
(feature . No) it returns Yes or No as the case may be. Otherwise it
calls the features's Dtree if there is one (using DoTree) or if not,
returns a default value (using Default). Default may be a complicated
function (indeed it may initiate a substantial train of activity) but
for present purposes it may be thought of as simply recovering 'Yes or
'No from the default property of the feature.

The function of DoTree is just clerical, the work is done by DSearch.
This function induces a subtree of the DTree, the leaf nodes of which
are exactly those nodes from which no exit was possible. They represent
the points at which the system can no longer "change its mind". We call
them stickNodes, and DSearch passes them to the function Decide.

DSearch uses left to right depth first search. which corresponds well
with the conceptual structure of the DTree, exploring each possibility
exhaustively as it arises; but other traversals are not ruled out.

This only leaves the function Decide. This simply scans the nodes
submitted to it. If all of them have colour No then Decide returns No.
But if even one has colour Yes then Yes is returned instead. This
represents the fact that our domain rules provide sufficient conditions
for access to a benefit. If you qualify by any of the criteria then the
overall decision is Yes.

## The Explanation System

The explanation facility is the feature of the system which is of most
interest to us here. Arboretum had two innovations in this field. The
first was the Natural Language justification of decisions, which is the
central matter of interest to us; but the graphical display, and in
particular the animated search trace, also contributed to the user's
ease of comprehension of the systems decisions.

This system provided the equivalent of the more traditional "ask how"
and "ask why" queries provided by other inference mechanisms. The user
could see why a question was being asked, simply by looking at the
display and seeing that the answer was needed in the evaluation of the
current rule, whose result was needed by the preceding rule, and so on
back to the question originally asked. To ask how a particular value was
found, after completion of a run, we could point to a node representing
the feature in question and select "How?" from the left button menu. The
machine would respond by printing out a message saying whether the value
was supplied by the user, evaluated from a DTree, or taken from a
default. If a DTree had been called, it was displayed.

However, the natural language explanation system was vitally important
to the usefulness of the system. Without it, the user must effectively
take the decisions of the machine on faith, and in a domain with
imperfect information that is dangerous; furthermore, the machine is
reduced to giving yes/no answers. We could ask: 'is this person entitled
to benefit', but not 'which benefit is this person entitled to'. Of
course the system with explanation could still only give 'yes/no'
decisions, because that is the nature of the logic; but now it could
say: 'yes, this person is entitled to benefit; and the benefit they are
entitled to is mobility allowance'.

The explanation system depended on and exploited the fact that DTrees
are structured through exceptions from the very general to the more
abstruse and particular; and that, in consequence, any path through a
rule structure follows a line of coherent argument, again from the
general to the particular. Thus a sticking node on the DTree for a
feature both records a decision and, by its position in the DTree,
implies an explanation of why that decision was made.

Consequently, we attached to each node in the system a text fragment to
explain the consequence for the feature whose rule the node was in, if
that node were a sticking node. This explanation fragment was a piece of
canned text, written by the knowledge engineer.

When writing fragments, the knowledge engineer did not need to look
beyond the DTree that was being edited. The task was simply to consider
a node and attach text saying why the feature of the DTree obtained (or
did not) when that node was conceived as the only sticking node. The
structure of the system itself then ensured that in the final text, the
fragment would follow sensibly from the preceding one.

We discussed and experimented with several algorithms for the recovery
of explanations. The one we used worked as follows: when a search
resulted in a 'no' decision (ineligible for a benefit) then we
concatenated the explanation fragments from the deepest sticking node in
each succesive tree on the search path. The reason was that this
represents the "nearest" that the claimant got to succeeding in the
claim. This follows from the structure of the DTree, the deeper nodes
represent more abstruse conditions: to reach them at all more general
requirements for eligibility must have been met.

Furthermore, the information given in this explanation should be
sufficient to assist in the preparation of an appeal, if the claimant
felt there were further relevant facts which hadn't been considered -
and this was, indeed, precisely our intention. It is, we think, part of
the notion of relevance that it is the "nearest miss" that should be
described in such cases.

In the case of a 'yes' decision we chose the opposite approach and
selected the shallowest sticking node available. This was partly because
the claimant who succeeds is less concerned about why, but mostly
because it is not relevant to describe how a long and tortuous inference
path finally delivered 'yes' when a much shorter less involved one did
so too. Again this seems in accord with ordinary ideas of relevance.

To provide a small worked example of an explanation generated by the
system, which is yet large enough to give some flavour, let us assume
our knowledge base contains the following rules:

DTree for Entitled to Widow's Allowance

Entitled to Widov's Allowance -

Satisfies conditions for Widoy's Allowance +

&gt;26 Yeeks Killed husband - Dead - bereaved -

-

in prison - Levering with

in prison - Living Yith

Partner -

fig 1: Rule for "Entitled to Widow's Allowance"

DTree for Living with Partner

Living with Partner -

Remarried +

Cohabiting +

fig 2: rule for "Living with Partner"

which, together, partially encode
the following legislation fragment, from the Social Security Act 1975
\[6\], chapter 14, section 24, as amended by the Social Security
(Miscellaneous Provisions) Act 1977, chapter 5, .section 22(2). This
reads:

    24.-(1) A woman who has been widowed shall be entitled to widow's
    allowance at the weekly rate specified in relation thereto in Schedule
    4, Part I, paragraph 5, if -

      \(a) she was under pensionable age at the time when her late husband
      died, or he was then not entitled to a Category A retirement pension
      (section 28); and

      \(b) her late husband satisfied the contribution requirement for a
      widow's allowance specified in Schedule 3, Part I, paragraph 4.

      \(2) The period for which widow's allowance shall be payable shall be the
      26 weeks next following the husband's death:

    Provided that the allowance shall not be payable for any period after
    the widow's death or remarriage or for any period during which she and a
    man to whom she is not married are living together as man and wife.

Let us further assume we have entered at least the following explanation
fragments, each providing an explanation simply in the context of the
rule in which it appears:

1\. on the node for "Living with Partner" in the rule for "Entitled to
Widow's Allowance", the text:

> Although you satisfy the basic conditions for eligibility for Widow's
Allowance, you are not eligible, as we understand that you now have
another partner."

2\. on the node for "Remarried" in the rule for "Living with Partner",
the text:

> "We understand that you have remarried."

Of course we would also enter text fragments for all the other nodes in
our rules. Now assume we are dealing with the case of a widow whose
previous husband had paid his National Insurance stamps as required, and
who had not reached pensionable age at the time of his death. Assume
further that, within the first six months of her bereavement, our
claimant has remarried. The text that would be generated would be:

> Dear \[name of claimant\]

> Although you satisfy the basic conditions for eligibility for Widow's
Allowance, you are not eligible, as we understand that you now have
another partner. We understand that you have remarried.

> Yours Sincerely

> \[your name\]

There are a number of points to notice. Firstly, we could easily have
written far more friendly and less formal fragments; for example, We
could have written "we wish you all the best in your new marriage". The
formality here is simply to help in understanding the mechanical nature
of the concatenation process.

Much more significantly, note that although the inference engine must
have known or discovered, for example, that her previous husband had
indeed been regular and conscientious about his national insurance
contributions for it to have reached its conclusions, this information
has been 'included out of the explanation. It is irrelevant. It is clear
that, if our claimant wished to make an appeal, her grounds for doing so
would have to be that the information provided had been incorrect, and
that she had not, in fact remarried.

## Explanation: a discussion

{\*\* Write something (preferably rather a lot) here \*\*}

This ability to abstract relevant information from an inference engine's
backtrace represents a partial solution to the problem described in the
preceding chapters.

## References:

3\. Xerox Corporation: InterLisp Reference Manual ( Xerox Corporation,
1985)

4\. Daniel Bobrow & Mark Stefik: The LOOPS Manual (Xerox Corporation,
1983)

5\. Mark Richer & William Clancey: "Guidon Watch: A Graphic Interface for
Viewing a knowledge-Based System" IEEE Computer Gaphics and Applications
vol 5 no 11,1985

6\. Social Security Act 1975: Her Majesty's Stationery Office, London,
1975 Social Security (Miscellanous Provisions) Act 1977: Her Majesty's
Stationery Office, London, 1977

7\. D. Neligan: Social Security Case Law: Digest of Commisioners'
Decisions (Her Majesty's Stationary Office, London, 1979)

8\. Mark Richer: "An Evaluation of Expert System Development Tools"
Expert Systems vol 3 no 3 1986

9\. William Swartout: "XPLAIN: a System for Creating and Explaining
Expert Consulting Programs" Artificial Intelligence 21, 1983

10\. Peter Jackson: "Explaining Expert Systems Behaviour" paper given at
Workshop on Explanation, Alvey IKBS Expert System Theme 20th-21st March
1986: available from the Alvey Directorate.

11\. William Clancey: "The Epistemology of a Rule-Based Expert System - a
Framework for Explanation" Artificial Intelligence 20, 1983

12\. E. H. Shortliffe: MYCIN: Computer Based Medical Consultations
(Elsevier 1976)

13\. P. Hammond: "APES: A user manual" Research Report DOC 82/9, Imperial
College, London

14\. W. van Melle, E. Shortliffe & G. Buchanan: "EMYCIN: A Knowledge
Engineer's tool for constructing Rule-Based Expert Systems" (in Rule
Based Expert Systems ed Buchanan, G. & Shortliffe, E., Addison-Wesley,
1984)

15\. Diane Warner Hasling, William Clancey & Glenn Rennels: "Strategic
Explanations for a Diagnostic Consulting System" International Journal
of Man Machine Studies 20, 1984

16\. Thomas Hobbes: Leviathan (ed C. B. MacPherson, Penguin 1968)

17\. Karl Popper: Conjectures and Refutations (Routledge Kegan Paul,
1963)
