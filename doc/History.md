# History

## History: Introduction

The object of this chapter is to  describe and discuss the development of Expert System  explanations from the beginning' to the most recent systems. The  argument which I will try to advance is that development has been  continuously driven by the perceived inadequacy of the  explanations given; and that, while many ad hoc, and some  principled, approaches have been tried, no really adequate  explanation system has emerged. Further, I will claim that, as  some of the later and more principled explanation systems  accurately model the accounts of explanation advanced in current  philosophy, the philosophical understanding of explanation is  itself inadequate.

{I ought to add to this chapter to give some overview of what's happened since 1990, and look at explanations of neural network decisions, because that will help in later parts/chapters of Part One}

## Family Tree  of Systems discussed

![Family tree](../img/family-tree.svg)

Chronology relates to publication,  and not to implementation. Links are shown where system designers  acknowledge influence, or where family resemblance between  systems is extremely obvious. In a small field like this, it is  reasonably (but not absolutely) safe to assume that major  practitioners are up to date with the current  literature.

Contrary to the current view,  expressed by such authors as Weiner:

"... (Expert) systems include some  mechanism for giving explanations, since their credibility  depends on the user's ability to follow their reasoning, thereby  verifying that an answer is correct." [Weiner, 80]

This view might be paraphrased as  saying that an explanation generator is an intrinsic and  essential part of an expert system. By contrast, the first thing  that I intend to argue is that:

## The earliest  systems contained no explanation facilities

Two of the famous early expert  systems, Internist [Pople 77] and Macsyma [Martin &amp; Fateman 71]  did not have anything approaching an explanation system and made  no claims to have one. Consequently, these will not be discussed  at any length here. One other, Dendral, had a command 'EXPLAIN';  and the last, MYCIN, is famous for its explanations. To maintain  my claim that neither of these systems had, in their original  conception, what we would recognise as an explanation system, we  will examine them in detail.

## Dendral

### General  description of the system

Dendral is one of the earliest  programmes which are conventionally included in the history of  Expert Systems. As Jackson says:

> "DENDRAL can be seen as a kind of  stepping stone between the older, general-purpose problem solving  programs and more recent approaches involving the explicit  representation of domain knowledge." [Jackson 86 p 19]

The system is designed to deduce  the molecular structure of an organic compound from mass-spectrum  analysis. It differs from the modern, post MYCIN, conception of  an 'expert system' - or indeed even the weaker conception of a  'knowledge based system'- in a number of ways.

Firstly, it operates in  'batch-mode' - that is, when the system is started, it prompts  the user for input, and then 'goes away' and analyses this  without further interaction. When this is completed, it outputs a  report.

Secondly, the program explicitly  implements an algorithm, which is described [Buchanan et al 69,  section 7].

Most significantly for the purpose  of the current argument, although an attempt is made to produce  information from which a justification of the conclusion could be  reconstructed (by printing out the states of some internal  variables at the end of the run), and although the function which  causes the state of the variables to be printed is called  'EXPLAIN', there is no 'explanation facility' as currently  understood. This lack is partially made good by a 'speak' option,  which causes information about the current hypothesis to be  printed out at each stage in the inference process.

### Example  output:

    (EXPLAIN (QUOTE C8H160) s:09046  (QUOTE TEST1) (QUOTE JULY8)) *GOODLIST= (*ETHYL-KETONE  3*)

    *BADLIST= (*c-2-ALCOHOL*  *PRIMARY-ALCOHOL* *ETHYL-ETHER2* *METHYL-ETHER2* *ETHER2*  *ALDEHYDE* *ALCOHOL* *ISO-PROPYL KETONE3* *N-PROPYL-KETONE3*  *METHYL-KETONE 3*)

    (JULY-4-1968 VERSION)  c2*ETHYL-KETONE 3*H8 MOLECULES NO DOUBLE BOND EQUIVS

    CH2..CH2.c3H7 c=.0 C2H5,  CH2..CH..CH3 C2H5c=.0 C215 CH2..CH2.CH..CH3 CH3 c=.0  C2H5.

    DONE
> {from op. cit. table 10, p  250}

### DENDRAL as  an Expert System

So why should DENDRAL be  considered an 'Expert System'? The programme consists of two  major components, a 'structure generator' and a 'evaluation  function'. Both of these incorporate inference mechanisms,  supported by explicit representations of knowledge.

### The  'Generate' stage

The input data gives approximate  information about the relative quantities of different ion-masses  in the compound, and consequently roughly suggests the  proportions of elements present. The 'structure generator  generates compounds compatible with the analysis data, by  exploiting knowledge about possible and impossible atomic bonds.  This knowledge appears to be held essentially as patterns,  against which generated patterns are matched. Two primary  collections of patterns are maintained, a 'badlist' and a  'goodlist'. The badlist comprises, initially, those primitive  compounds which cannot exist in nature; those compounds which are  ruled out by features of the input data are added.

### The 'Test'  stage

The evaluation function takes  structures passed by the generator, and uses a predictor to  calculate what the spectrum to be expected from this structure  would be. It then compares this against the spectrum originally  entered, and scores it for  similarity.

The predictor uses some form of a  rule engine. My caution in that statement derives from the  extremely technical nature of the passage in [Buchanan et al 69,  section 4], and the fact that no actual examples of rules given.  These rules determine the way in which a compound is likely to  break down under conditions inside the spectrometer, and what new  compounds in what proportion will be the products of these  breakdowns; generally the form of the rule appears to be a  pair:

    (<compound-specification> ·  <product-specification>)

where  <compound-specification> is a description of a compound or  class of compounds, and <product-specification> may be a  list of compound specifications with information about their  proportions, or may, where it is uncertain what the precise  products would be, or no further decomposition is likely, be  spectrum fragments. The spectrum fragments which form the nodes  of the decomposition graph are then summed to generate a  'predicted spectrum'.

So that it appears that two main  inference mechanisms are employed in DENDRAL: a simple pattern  matcher helps to generate hypotheses, and a more sophisticated  forward chaining mechanism supports the test stage, eliminating  the impossible ones. Summary

It is clear from the above that  DENDRAL is an 'Intelligent knowledge Based System'; but the  absence of any high-level explanation or justification system, or  any method of exploring the inference interactively with the  machine, make it very different from what we now think of as an  'expert system'.

Despite this, DENDRAL has a very  direct relevance to the present project: as [Buchanan &amp;  Feigenbaum 78] report:

> "Another concern has been to  exploit the AI methodology to understand better some fundamental  questions in the philosophy of science, for example the processes  by which explanatory hypotheses are discovered or judged  adequate." (Buchanan &amp; Feigenbaum 78, p 5)

Thus DENDRAL set out to contribute  to exactly the same debate that I am addressing.

Interestingly, although later  developments of the DENDRAL family included interactive editing  of hypotheses, and although Buchanan was involved in the MYCIN  project in the interim, no explanation facility had been added to  the system by 1978, the date of the later of these two papers.  This may be seen as providing some very tenuous support for one  or other of two hypotheses of mine:

1. It was Davis, with TEIRESIAS,  who developed what we now think of as MYCIN's explanation  facility;

2. It is extremely difficult to  add explanation post facto, if the knowledge representation and  inference mechanism have not been designed to support  it.

## Mycin

Mycin [Davis et al, 77] is perhaps  the program most often seen as the starting point for expert  system research, and is certainly the first system which is  remembered for its explanation facilities.

### Explanation  Facilities

What isn't so frequently  remembered is that MYCIN itself, the consulting programme, didn't  have any explanation facilities. These were provided by a  separate front end module, TEIRESIAS, which was intended as a  knowledge engineers tool. The point here is that the MYCIN  project did not (as | understand it) expect end users to use - or  need - explanations. Rather, the explanation facility was  conceived as a high level debugging trace to help the knowledge  engineer, presumed to be an "experienced programmer", with  knowledge of the problem domain, to discover what is going on  inside the system. Consequently:

> "The fundamental goal of an explanation  facility is to enable a program to display a comprehensible  account of the motivation  for all its actions." [Davis &amp; Lenat, 82] my  emphasis.

The explanation tells why the  machine carried out an action, not why it believes a proposition.  This is justified on the grounds that:

> "We assume ... that a recap of  program actions can be an effective explanation as long as the  correct level of detail is chosen."

> "With a program that does  symbolic reasoning, recapping offers an easily understood  explanation." [ibid]

This understanding of the  explanation as simply a development of high level trace  facilities is confirmed by the fact that the fragments chosen for  concatenation to form the explanation are constructed by applying  fixed templates to the rules. It is a (perhaps the) classic  sweetened backtrace.

Rules were assumed to be  comprehensible in themselves because they had been formulated by  human experts attempting to formalise their own knowledge of the  domain. As such the rules were expected to:

> "...embody accepted patterns of  human reasoning, implying that they should be relatively easy to  understand, especially for those familiar with the domain. ...  They also attack the problem at what has been judged (by the  expert) to be an appropriate level of detail."

The explanation is also helped by  the very high level language in which the rules are expressed,  with its stylised code and small number of primitive operations,  which together made it easy to apply templates to transform  executable code into legible English.

#### The WHY  question

The WHY question had two different  semantics, depending on the mode that MYCIN was in when the  question is asked. MYCIN is generally thought of (as it is) as an  inference mechanism, so that the literature generally refers to  MYCIN working in this mode. But before starting on an inference  process on a new case, MYCIN asks a series of questions to allow  it to set up the primary attributes of the objects it is  instantiating. This is more akin to form filling than to rule  driven behaviour.

In the more familiar Inference  mode, TEIRESIAS response to a WHY question was to justify its  action in asking the current question, by printing the rule  currently being executed, in a special template. WHY is thus an  'immediate mode' command; it is not clear from the material  whether it was possible to ask WHY any question other than the  current one had been asked.

However, WHY queries can be  repeated, to climb back up the inference trace one rule at a  time.

In the form filling mode referred  to above, WHY queries are simply responded to by printing out  some canned text associated with the primary attribute being  requested.

#### The HOW  question

The HOW query, by contrast,  operates on a history list, and requires, as argument, a  statement number. The response given is (again templated) a print  out of the rule whose 'test part is given in the numbered  statement. Thus there are two quite different semantics to HOW.  HOW of a rule which has been evaluated will give what is in some  sense a justification (by modus ponens) for belief in the  statement - in this sense it might be rendered "how do you know  that...". HOW of a rule which has yet to be evaluated gives  procedural information about how to find the truth value of the  statement, and might be rendered "how would you find out  whether...". These different semantics are to some extent  signalled by the use of different templates.

Some numbered statements, eg (5.0)  below, do not appear to be 'test parts' of any rule. It is not  made clear what the effect of asking 'WHY [5.0]' would  be.

##### Example,  user input prefixed with '**' prompt:

    Where is the suspected portal  of entry of organism-1 into this sterile site?

    ** WHY 

    [I.e., why is it important to  determine the suspected portal of entry of organism-l into this  sterile site?]

    [3.0] ...in order to find out  about an organism or class of organisms for which therapy should  cover.

    It has already been established  that

    [3.1] The site of the culture is  blood [3.2] The stain of the organism is gram positive

    Therefore if:

    [3.3] The portal of entry of the  organism is G.I.

    [3.4] [Abdomen is the locus of  infection, or

    Pelvis is the locus of  infection]

    Then:

    There is strongly suggestive  evidence (.9) that Enterobacteriacea is the class of organisms  for which therapy should cover.

    [Rule 095]

    ** HOW [3.4]

    [I.e., how were you trying to  determine that the Abdomen is the locus of infection, or that the  pelvis is the locus of infection]

    [4.0] At that point Rule 021  was being used.

    ** HOW [4.0] 

    [I.e., how was Rule 021  used?]

    [5.0] It has already been  established that

    [5.1] the culture is  recent

    Therefore if:

    [5.2] There is therapeutically  significant disease associated with the occurrence of this  organism

    Then

    It is definite (1.0) that the site  of the culture is the locus of infection in the  patient.
> {Taken from Barr &amp; Feigenbaum, vol  ii pp 96-97; similar, but more extensive, examples may be found  in Davis &amp; Lenat, pp 265-285. More surprising examples appear in  Davis et al. pp 35-37}

### Relevance  filtering

Another feature of MYCIN for  rather, of TIERESIAS) which is often forgotten is the advanced  relevance filtering, which has rarely been equaled by  imitators.

Briefly the problem which gives  rise to the need for filtering is this. An 'explanation' (at  least one given in the form of a syntactically sugared inference  trace) which gives all the steps used to reach a goal will, in  real applications, tend to be too long and complex for the user  to understand. The critical information will be lost in a mass of  trivial detail. This problem does not, of course, arise with toy  systems, where the knowledge base is not large enough for  extended chains of inference to develop. As Davis  writes:

> "In an explanation we must not  carry reasoning too far back, or the length of our argument will  cause obscurity; neither must we put in all the steps which lead  to our conclusion, or we shall waste words by saying what is  manifest."

> "Depending on the individual  user, it might be best to show all steps in a reasoning chain, to  omit those that are definitional or trivial, or, for the most  sophisticated user, to display only the highlights." [Davis &amp;  Lenat]

Later, we find Weiner  writing:

> "If an explanation is to be understood, it  must not appear complex." [Weiner, 80]

TIERESIAS' relevance filter was  based on the ['certainty factor']3 of inference steps. A function  of this was used as a measure of the significance of an inference  step, with inferences having a CF of 1 (true in every case) being  considered to have no contribution to make to explanation, and  lower certainty factors having higher indices on a logarithmic  scale. This was seen as a "...clearly imperfect..." solution, but  provided:

> ".... a 'dial' with which the  user can adjust the level of detail in the explanations. Absolute  settings are less important than the ability to make relative  adjustments."

This index of explanation  abstraction could be used as an optional argument to the WHY  query, as in the example:

    ** WHY 4

    We are trying to find out  whether the organism has been observed in significant numbers, in  order to determine an organism or class of organisms for which  therapy should cover.

> {taken from Davis and Lenat pp  269 - 270. See also ibid pp 265 - 266 for the 'low level' version  of this reply. This is a very impressive feature which has been  quite neglected in the general literature.}

This feature is further extended  with an EXPLAIN command, which can be used to go over the same  ground as an immediately previous WHY command, but at a different  level of detail. Thus if the user tried, for example 'WHY 10' and  got back an answer that was too sketchy, it would be possible to  try 'EXPLAIN 3' to bring the level down. Whether the reverse  would be possible - to try EXPLAIN 10 after WHY 3 - is not made  clear, but it appears not, as "...the EXPLAIN command will cover  the same starting and ending points in the  reasoning...".

### Limitations  of the explanation system

Parts of the MYCIN expertise  (those parts concerned with the selection of drugs are mentioned)  were not encoded as rules but were coded in LISP directly. This  expertise could not be explained by TIERESIAS.

Other limitations of the system  recognised by Davis &amp; Lenat include the limited semantics of the  question commands (i.e. the user was tightly constrained in what  could be asked), the fact that beyond the level at which  knowledge was primitive in the system, further explanations could  not be given, and the lack of any user model, which might help  remove unneeded detail from explanations. Furthermore, they  observe that "... the system should be able to describe its  actions at different conceptual levels...".

### Conclusion

TIERESIAS is one of the earliest  examples of Expert Systems explanation. It is significant that  explanation was not seen as being a critical or integral part of  MYCIN, but was provided in a separate programme initially  intended only as an aid to knowledge engineers and not as part of  the consulting system. In this context it is not surprising that  it should have developed out of high level backtrace facilities  familiar from LISP programming environments.

Despite the fact that it was a  very early attack on the problem, and is in essence simply a  syntactically sugared backtrace, TIERESIAS is highly  sophisticated in a number of ways; notably in the provision of an  effective (if crude) measure of detail, which allowed for  remarkably successful abstraction of high-level explanations from  the inference trace.

MYCIN/TEIRESIAS was undoubtedly a  revolutionary system in many ways, and it has spawned many  derivative systems. But it was less revolutionary than it  appeared to be. The 'explanation facilities', which are made so  much of in the literature, are not able to give declarative  reasons for belief in propositions. They were not designed to,  being conceptually merely very high level trace facilities for  the knowledge engineer.

The fact that users eagerly  accepted the facilities MYCIN/TEIRESIAS provided indicated that  there was a demand for explanation systems.

# A wide  variety of 'technical fixes' have been experimented  with

A very wide range of approaches to  the problem of providing a high level account of a system beliefs  or actions have been tried. One of the interesting avenues has  been that followed William Swartout, in attempting to provide  'explanations' of what conventional procedural programmes are  doing.

## Digitalis  Therapy Advisor

This is yet another medical expert  system - this time dealing with the administration of digitalis  to heart attack sufferers.

The knowledge base maintains a  model of the patient's individual response to digitalis - a  highly toxic drug to which people have widely varying response -  as well as general information about its properties and  administration.

Digitalis Therapy Advisor was  written in, and clearly developed with, a prototype  'self-documenting' language called OWL 1. This is a clearly LISP  like language, which incorporates a parser which can translate  programme statements into a high-level procedural account. This  parser can be applied not only to pieces of code, to show what  the programme would do to execute it, but also to items on the  history list, to show what it has done.

### Explanation by  translation of programming language

Explanation was a key goal in the  design of the advisor programme, and the implementation of it  largely exploited this feature of the underlying language.  Broadly, english-language templates are associated with each  primitive procedure of the language, into which the arguments  passed, and on completion the results returned, can be spliced.  However the programmer, when writing a new procedure, does not to  need to supply templates, as the system is able to splice  together the templates belonging to the primitives. As the OWL  interpreter runs, it builds up an 'event structure', or trace, of  the procedures it has called, and what their arguments  were:

> "The system is  'self-documenting' in the sense that it can produce English  explanations of the procedures it uses and the actions it takes  directly from the code it executes. Most of its explanations are  produced in this manner, although a few types of explanation are  canned phrases... The explanations are designed to be understood  by a physician with no programming experience."

The limitations of this approach  are acknowledged, and the 'work around" used is  described:

> "When writing a computer  program, it is sometimes necessary to use methods that are  totally foreign to users of the system. This may be because the  methods employed by humans are unknown, (or) too inefficient...  Whenever this situation occurs, it will not be possible to  provide explanations by merely translating the code of the  program into English...

> To deal with this problem ...  we have attached English comments to the OWL code... When the ...  method is explained, the comments are displayed along with the  translated OWL code."

### Sample  Explanation

    DURING THE SESSION ON 9/21/76  AT 11:10, I CHECKED SENSITIVITY DUE TO THYROID-FUNCTION BY  EXECUTING THE FOLLOWING STEPS:
    1: I ASKED THE USER THE STATUS OF  MYXEDEMA. THE USER RESPONDED THAT MYXEDEMA WAS PRESENT.
    2: SINCE  THE STATUS OF MYXEDEMA WAS PRESENT I DID THE FOLLOWING
      2.1 I ADDED MYXEDEMA TO THE  PRESENT AND CORRECTABLE CONDITIONS. THE PRESENT AND CORRECTABLE  CONDITIONS THEN BECAME MYXEDEMA.
      2.2 I REMOVED MYXEDEMA FROM THE  DEGRADABLE CONDITIONS. THE DEGRADABLE CONDITIONS THEN BECAME  HYPOKALEMIA, HYPOXEMIA, CARDIOMYOPATHIESMI, AND POTENTIAL  POTASSIUM LOSS DUE TO DIURETICS

> {And so on ad nauseam. Taken  from Swartout 77, page 822}

### Explanation:  Discussion

Essentially this is a 'syntactic  sugaring system, which provides for splicing text fragments into  the output where necessary. Clearly, as the methods which are  executed are procedural, the explanation given is a procedural  explanation, an explanation of why things were done, and not of  why things were believed. It appears for example that we cannot  ask why the various actions were carried out - ie what the system  was attempting to achieve, as in a MYCIN 'WHY' question; nor why  specific things are believed: for example, why 'hypoxemia' is one  of the degradable conditions.

Instead of dividing the system  into an inference engine and a knowledge base, the knowledge is  hard-wired into OWL1 'methods' (procedures). This approach  appears more applicable to domains where an algorithm is  available, than to the more classic 'Expert System'  domains.

## XPLAIN

### General  Description

Swartout's next system, XPLAIN,  grew out of the work on Digitalis Therapy Advisor, and parallels  the development of in that an objective in the design was the  justification of programme actions.

The explanation system follows DTA  in which explanations were based on expansions of the actual  executable code - a sophisticated variant of syntactic sugaring.  Swartout argues against the use of canned text:

> "There are several problems  with the canned text approach. The fact that the program code and  the text strings that explain that code can be changed  independently makes it difficult to maintain consistency between  what the program does and what it claims to do. Another problem  is that all questions must be anticipated in advance... Finally,  the system has no conceptual model of what it is saying... Thus,  it is difficult to use this approach to provide more advanced  sorts of explanations..." [Swartout 83, p 291]

But now he explores the  limitations of the approach used in DTA, mentioning, in addition  to those problems noted in his earlier paper [Swartout 77], that  redundant and irrelevant information is included in a mechanical  expansion of code:

> "The fact that every operation  must be explicitly spelled out sometimes forces the programmer to  program operations which the physician would perform without  thinking.... (eg) steps which are involved more with record  keeping than with medical. reasoning... Since they appear in the  code, they are described in the explanation routines, although  they are more likely to confuse a physician user than enlighten  him. An additional problem is that it is difficult to get an  overview of what is really going on..." (ibid, p 293)

Once again, we see that the  inclusion of irrelevant material can mask the important points  from the human reader.

Swartout's solution to his  rejection both of canned text and of syntactic sugar is to have  the computer generate the expert system - called 'the performance  program' - and simultaneously, from the same knowledge base,  generates a 'refinement structure' which records the  transformations from the input information to the performance  program. This is exploited in the later construction of  explanations. XPLAIN itself is thus an automatic programmer,  whose purpose is to write such a system.

The 'refinement structure' is a  tree of goals, each being a subgoal of the one above it, at a  less abstract level. Each subgoal is succesively refined until a  primitive of the underlying programming language is produced. The  performance program is thus found at the leaves of the refinement  structure.

In addition to the refinement  structure, knowledge is held in a 'domain model and a collection  of 'domain principles'.

### Generating  explanations

The explanation system which  exploits this structure is constructed in two modules, a phrase  generator, and an answer generator. The phrase generator task is  to compile sections of the refinement structure into English  fragments; the answer generator first chooses which sections of  the refinement structure to compile, and then concatenates the  generated phrases to produce the answer. In addition to  generating phrases from the refinement structure, the explanation  generator has access to the domain model and  principles.

It applies relevance filtering by  maintaining

> “...knowledge of the state of  the program execution, knowledge of what has already been said,  and knowledge of what the user is likely to be interested in.  Other issues the answer generators confront include deciding  whether to omit information the user can be presumed to know from  the explanation and determining whether analogies can be  suggested to previous explanations." [ibid, p 306]

This is a much more sophisticated  conception of what it is relevant to tell the user than we have  yet seen.

### Sample  Explanation, user input in bold

    Please enter the value of serum  potassium:

    **why?**

    The system is anticipating  digitalis toxicity. Decreased serum potassium causes increased  automaticity, which may cause a change of ventricular  fibrillation. Increased digitalis also causes increased  automaticity. Thus, if the system observes decreased serum  potassium, it reduces the dose of digitalis due to decreased  serum potassium.

    Please enter the value of serum  potassium:

    3.7

    Please enter the value of serum  calcium:

    **why?** 

    The system is anticipating  digitalis toxicity. Increased serum calcium also causes increased  automaticity. Thus, (as with decreased serum potassium) if the  system observes increased serum calcium, it reduces the dose of  digitalis due to increased serum calcium
> {after  Swartout 83, p 294} 

Points to note are that, unlike  the DTA example, this explanation tells us why the system is  doing what it is, rather than how it is doing it. It now  resembles, conceptually, the MYCIN type WHY explanation, except  that the text is noticeably more fluid. It is also worth noticing  how the generator of the second explanation fragment has taken  into account the information given in the earlier one. The  procedural explanations, as produced by DTA, are still available.  However, the system still cannot produce an account of why it  holds a particular belief.

## APES

Another interest which was  developing in the Artificial Intelligence community at the same  time as Expert Systems was 'Logic Programming': implementing  restricted subsets of first order predicate calculus as  programming languages. The most significant of these languages  was PROLOG. It was inevitable that these two strands would come  together, and one of the first signs of this was APES - A PROLOG  Expert System - developed Peter Hammond in the early  80s.

Hammond and Sergot discuss the  motivation for writing an expert system in PROLOG: they show the  structural similarity between the production rules used in the  MYCIN family of systems and horn clauses, note that horn clauses  offer greater expressive power, and claim that this will assist  in the construction of :

> "... knowledge bases which are  more flexible, more useful, and more elegant than would be  possible with less powerful languages." [Hammond &amp; Sergot 83 p  95]

The inference engine is  constructed as a meta-interpreter in PROLOG, similar in concept  to Walker's Syllog [Walker et al, 87]. The explanation mechanism  is a syntactic sugaring of the rule trace, clearly modelled  closely on MYCIN or some derivative.

Explanation fragments are  generated by applying english-language templates written by the  knowledge engineer to the rules, which are themselves written in  a strict horn-clause form.

### Example  explanation, user input in bold


    Is it true that Peter  suffers-from peptic-ulcer ?

    **why?** 

    aspirin aggravates peptic-ulcer
    if Peter suffers-from peptic-ulcer
      then aspirin is-unsuitable-for  Peter

    Peter complains-of  pain
    aspirin suppresses pain
    if not  aspirin is unsuitable-for Peter
      then Peter should-take  aspirin

    Is it true that Peter  suffers-from peptic-ulcer ?

    **yes**

    Is it true that Peter  suffers-from impaired-liver function ?


    **no**

    ==> Peter should-take  lomotil.

    **how**

    To show Peter should-take  lomotil I used the rule

      <person> should-take  <drug> if
        <person> complains-of  <symptom> and
        <drug> suppresses  <symptom> and
        not <drug> is-unsuitable-for  <person>

    You said Peter complains-of  diarrhoea

    I know lomotil suppresses  diarrhoea

    I can show not lomotil  is-unsuitable-for Peter.

### Discussion

Although PROLOG is a declarative  language, and it would seem natural to provide it with a  declarative explanation facility, the implementers of APES seem  to have been more concerned to demonstrate that existing Expert  System functionality could be implemented in PROLOG than to  consider what functionality was actually desirable. Thus they  provide a system which is similar to but actually cruder than  MYCIN - there is, for example, no relevance filtering.

So this must be seen as a toy  system, whose only real interest is that it demonstrates that it  may be possible to build an explanation system in Prolog. It does  not demonstrate that a good explanation system can be built, and  it would not effectively handle a knowledge base of any  size.

## Syllog

Syllog, like APES, is an attempt  to make a fusion between Expert Systems and logic programming. In  some senses it is a better thought out and better engineered  attempt, as I hope to show, than APES; and this is reflected by  the fact that Syllog has been employed in a number of  experimental, but significant, applications, by IBM (Syllog was  developed by Adrian Walker of IBM's Thomas J Watson Research  Centre).

Syllog is a rule based system, and  like Apes, the rules are technically horn clauses - but they are  expressed in a high-level rule language, which makes them easier  to understand, and are termed 'syllogisms' by Walker - even  though they clearly aren't.

What makes Syllog interesting from  the present view point is the explanation system, which, although  lacking in interesting capabilities like relevance filtering, is  that the explanation given is declarative. The technique of  explanation generation is also extremely different from preceding  systems, in that the rule is (conceptually, at any rate) compiled  into the explanation, in something like the way that a  conventional language compiler works. The system compiles  reasonable English with remarkably little knowledge of the  language; and indeed is very simply adapted to work in other  natural languages.

### Sample  explanation (1):

This sample ‘explains’ planning a  flight from John F Kennedy airport, New York, to San Francisco.  It is, essentially, a pretty-printed execution trace, without  syntactic sugar.

    FLY ( JFK, SFO, 9.30,  15.30)
      OK ( JFK, SFO, AMERIC,  9.30,10.0, 15.25, 15.30)
      FLIGHT ( AMERIC, 183, JFK,  CHI, 10.0, 11.24)
        BEFORE( 9.30,  10.0)
          LT( 9, 10)
        CONNECTION ( CHI, AMERIC,  UNITED, 11.24, 11.44)
          ADD( 11.24, 0.20,  11.44)
            SUM( 11, 0, 11)
            SUM( 24, 20, 44)
            LT ( 44, 60)
        OK( CHI, SFO, UNITED, 11.44,  13.5,15.30)
          FLIGHT ( UNITED, 121, CHI,  SFO, 13.5, 15.25)
            BEFORE ( 11.44,  13.5)
              LT ( 11, 13)
            BEFORE ( 15.25,  15.30)
              EQ ( 15, 15)
              LE ( 25, 30)

 {from [Walker 82] page  9}

### Sample  explanation (2):

    We shall set up testers for  18719 of part chip2 in quarter 3

    Yes, that's true

    Because...

    we shall set up testers for  2273 of card1 in quarter 3 card1 has 7 of immediate part chip2  2273 * 7 = 15911
    the expected yield of cardi is  85% based on past experience
    15911 divided by 85  (normalized and rounded up) is 18719 
    we shall set up testers for  18719 of part chip2 in quarter 3

    we plan to ship 1000 of box1 in  quarter 3
    box1 has 2 of the immediate  part card1
    the expected yield of card1 is  88%, based on past experience
    1000 * 2 = 2000
    2000 divided by 88 (normalised  and rounded up) is 2273 
    we shall set up testers for  2273 of card1 in quarter 3

{after [Walker et al 87], p  244}

These two explanations look  superficially very different, but a careful reading will show  that the form of the later (published 1987) explanation is simply  a - very competent - syntactic sugaring of exactly the same  semantic form as that of the earlier explanation.

Note that (in the later version)  the user has to give the system exactly the proposition to be  explained. This is supported by a menu system which allows the  user to browse through - and pick from - templates for all the  statements the system knows about. Once the user has picked a  template, further menus help with filling in the  blanks.

The slightly weird arithmetic is,  as they say, sic: otherwise we see a clearly expressed  declarative statement of why just this number of testers are  needed. We also see that without relevance filtering, this  arrangement is only suitable for relatively shallow search  spaces.

To be fair, there is something  that serves in place of a relevance filter: the top few nodes of  the proof tree constructed by the inference engine are compiled  into explanation fragments, which are placed on the screen; this  proceeds until the screen is filled. Because (as we argued in  [Mott &amp; Brooke 87] - although we were discussing a single path  selected from the tree) an inference mechanism chaining backwards  will generate a proof from the general to the particular, it can  be assumed that a general statement of the explanation will be  given first, with what follows being more and more tightly  focussed detail. So that what is immediately presented on the  screen is likely to be the most important - and perhaps the most  relevant - part of the proof.

So, once again, this system should  be classified as a bit ad hoc - an explanation system constructed  without a lot of thought for what explanation is. However, the  explanation constructed now conforms to the deductive nomological  account of explanation, rather than (to use Nagel's terminology)  the genetic form. So we have arrived at last at the classic  explanation form of the Philosophy of Science.

## Arboretum

### General  description

Arboretum is more completely  described in a later chapter, so I will not go into any great  detail here. The system was built to demonstrate a decision  procedure for a novel non-monotonic logic developed by Peter  Mott. The other major innovation of the system was the graphical  presentation of rules and of inference traces: this feature has  been seen by others as a form of explanation, but is not my  central interest here. The generation of textual explanation was  not part of the original conception but was added in an ad-hoc  manner during implementation.

The explanation system, as we  wrote, depended on:

> "... the fact that DTrees (the  knowledge representation used) are structured through exceptions  from the very general to the more abstruse and particular; and  that, in consequence, any path through a rule structure follows a  coherent argument, again from the general to the particular. "  [Mott &amp; Brooke 87, p 110]

This allowed us to attach an  explanation fragment to each node, knowing that each implied a  unique conclusion for the structure in which it appeared. We used  fragments of canned text, because we found this allowed us to  produce more fluid explanations, but as we noted:

> "... there is no reason why the  system should not be modified to generate explanation fragments  itself, for example by using a text macro similar to '<feature  of root-node> was found to be <colour of stick-node>  because <feature of stick-node> was true'." [Ibid, p  111]

### Relevence  filtering

The most interesting feature of  this explanation system was that fortuitously, the evaluation  process enabled us to extract precisely that clause in each rule  which was relevant to the eventual outcome. We also developed a  neat heuristic to the effect that, when generating a 'no'  explanation, we should:

> "... concatenate the  explanation fragments from the deepest sticking node in each  successive tree on the search path. The reason is that this  represents the 'nearest' that the claimant got to succeeding in  the claim... In the case of a 'yes' decision we chose the  opposite approach and select the shallowest sticking node  available... it is not relevent to describe how a long and  tortuous inference path finally delivered 'yes' when a much  shorter, less involved one, did so too." [Ibid]

### Sample  explanation

The application here is to the  adjudication of claims to health insurance benefits. The system  would be used by the adjudication officer, and the explanation  would be sent to the claimant.

    Dear [Name of  Claimant]

    Although physically capable of work you may
    nonetheless be deemed incapable of work today. You are
    deemed incapable of work for precautionary reasons. You
    are deemed incapable of work on precautionary grounds
    as you are under medical care, and your doctor has
    recommended that you abstain from working.

    Yours Sincerely

    [your name]

### Discussion

It will be seen that this is a  short, clear, declarative statement in seemingly natural English,  which covers all (and only) the relevant points of a complex  case. To be fair, the system does not always do this well, but  most of its explanations are of this quality.

# Attempts at  more principled approaches

After a long series of systems,  such as those just described, in which the approach taken to  explanation generation was essentially one of ad hoc mechanisms  and technical fixes, systems began to emerge in the late 1970's  which took a more principled approach to the problem. One of the  first of these was BLAH.

## BLAH

### General  description

This system sought to address  issues of explanation structuring and complexity. Like XPLAIN, it  sought to reduce detail by maintaining a model of what the user  could be expected to know. However, its design was based on  studies of human explanation behaviour, described in [Goguen et  al., 83] and in [Wiener 1979).

This system is also interesting in  that for the first time we see declarative  explanations:

> "The third type of question  (supported by BLAH) is a request to BLAH to explain why some  assertion, already in the knowledge base, is believed." [Weiner  80, p 20]

The inference mechanism used was  written in AMORD [de Kleer et al., '78] with a truth maintenance  sub-system described in [Doyle, 78]. Essentially this appears to  be a production system.

The knowledge base contains  assertions, each of which is supported by a list of other  assertions which tend to justify belief in it, and optionally, a  list of assertions which tend to question such belief.  Justifications are based on a set of rules: PREMISE,  STATEMENT/REASON, REASON/STATEMENT, IF/THEN, THEN/IF, AND, OR,  GENERAL/SPECIFIC, EXAMPLES, and ALTERNATIVES; these are claimed  to derive from justifications used by subjects in the studies of  natural explanation. Each rule has associated with it a series of  alternative templates into which the predicates and instantiated  variables can be patched.

Two parallel views of this  knowledge base are maintained: a system view and a user's  view.

> "... When a user poses a  question to BLAH, BLAH uses the knowledge in the system's view to  reason about it; and when BLAH generates an explanation, it uses  knowledge in the user's view to determine (by reasoning) what  information the user already knows, so that it can be deleted  from the explanation."

The system's view is built by the  knowledge engineer; information given by the user is added to the  user's view, and information generated by the inference process  is added to both.

The knowledge base is also  segmented into 'partitions' based on category; and further  divided into separate 'hypothetical worlds'; these last being  used, presumably, by the truth maintenance system.

The inference process generates a  tree having at its terminals instantiated statements about the  case, and at its non-terminals justification types, drawn from  those listed above. This structure is passed to the explanation  generator, which generates text by applying templates which are  associated with the justification types. These templates, as well  as english-ifying the system's statements, have the power to  reorder the nodes of the tree below them, for example by  converting an IF/THEN justification type to a THEN/IF. The  reorderings are intended to improve the explanation  structure.

However before applying the  templates it prunes the tree by removing all those statements  which the user is presumed to know (those which can be derived  from the user's view of the knowledge base), and which have no  dependents, using a bottom up, right to left search; and then  further prunes the tree by removing sub-trees which are  considered to contain detail.

The primary measure of detail used  is a function of the depth of the explanation tree, but trees are  also pruned for complexity if any node has more than two  dependents.

Where complexity pruning has been  used, explanations generated from the excised sub-trees are  successively appended to the original explanation. A meaningless  interjection ("uh") apparently culled from the study of human  explanation is used as a marker that this has been  done!

The length of the explanation is  thus clearly a function of the size (number of nodes) of the  explanation tree, but with the rider that splitting the tree in  order to improve the explanation structure will actually LENGTHEN  the explanation. Wiener claims this as a benefit:

> "As we see in (9), by copying a  node from one tree to another we cause the text associated with  that node to be repeated in the explanation. As [Halliday and  Hassan 76] point out, repetition is one factor which influences  the view that sentences, although separate, are tied together to  form a unified text."

BLAH provided three top level  facilities to the user. These were of the form:

    (SHOW  <assertion>)
    ->  <assertion>
    (CHOICE  <assertion1><assertion2>{<category  partition>})
    -> (I CHOSE  <assertionX>) (NOT (I CHOSE <assertionY>))
    (EXPLAIN  <explanation>)
    ->  <explanation>

Although these are all LISP like  in form (indeed the assertions themselves are in the form of  lists), it is not clear whether the user had the option of  entering:

    (EXPLAIN (SHOW  <assertion>))

### Example  explanation:

    Well, Peter makes less than 750  dollars, and Peter is under 19, and
    Harry supports Peter so Peter  is a dependent of Harry's. Uh Peter
    makes less than 750 dollars  because Peter does not work, and Peter
    is a dependent of Harry's  because Harry provides more than one half
    of Peter's  support.

I should explain that the  application is to the US Federal Income Tax system. This  explanation does indeed capture something of the flavour of a  natural spoken explanation. Furthermore, it is clearly  declarative rather than procedural. However, personally, I find  its style rather too informal for textual presentation. I  particularly dislike the meaningless 'Uh' which is use to tag the  supporting point.

### Discussion

With this system we can begin to  construct a model of what the designers have meant by  explanation, and relate it to the philosophical work to be  described in the following chapter. The form of the explanation  is essentially the deductive-nomological explanation, as  described by Hempel, but there are subtleties. The deductive  nomological form essentially requires that the explanation must  be given in terms of things which can be verified by reference to  the world; we will discuss the meaning of this later. But BLAH'S  explanations are given simply in terms of things which BLAH knows  that the user knows, making the assumption that the user can  supply the rest of the argument.

## ATTENDING

### General  Description

ATTENDING takes a radically novel  approach to the problem of assisting decision making in complex  domains: it works by inviting the user to describe a case, and to  then describe the proposed course of action. The machine reviews  the proposals, and produces a critique. The critique is generated  by fragment concatenation, and appears to be of high quality,  with very natural seeming english. The application described  [Miller, 84] is medical, considering plans for the  anaesthetisation of patients requiring surgery.

### Explanation  System

The explanation system is provided  with limited ability to prevent repetitiousness by allowing the  fragment concatenator to follow alternative routes at points in  the knowledge base, thus allowing for differently worded  explanations of the same inference.

### Interaction  Style

The input methods are crude in the  extreme, however, with the user being presented with fairly brief  menus of options to describe the case being handled. Thus courses  of action not foreseen by the knowledge engineer cannot be  described, and, consequently, cannot be criticised

### Inference  mechanism

The explanation generator is based  on the knowledge representation chosen, which is a variant of the  Augmented Transition Network and is called an 'Augmented Decision  Network'. The nodes of this network are 'states' which the  patient may be in. These states are joined by arcs, labelled with  actions which may move the patient from the initial to the  consequent state of the arc. Each arc also holds a list of risks  and benefits consequent on the action. Where a choice of arc  exists between two nodes, the arc whose total risks scores least  will be prefered; where more than one arc has no risks associated  with it, the arc whose total benefits score most will be  preffered. Fragments (it is interesting to note that the author  uses the word) are stored on arcs of futher transition nets,  which are themselves expansions of the arcs in the decision net.,  and the explanation generator chooses a path through this net  collecting and concatenating fragments along the way.

### Redundancy  filtering

The concatenator maintains lists  of topics mentioned at sentence, paragraph, and text level, and  uses these to prevent redundancy. Where a topic is mentioned a  second or subsequent time, a template is substituted for the  reference. Thus it is clear that the fragments are more complex  than just strings; they must also have some information about  their content, in machine handleable form.

### Explanation:  Discussion

This principled approach to  explanation generation is seen as more sophisticated than the 'if  x and y and z then print "this is an explanation" school of  explanation generation:

> "Many systems which produce  prose output use a fairly ad-hoc approach. Sentences and sentence  fragments are stored in the machine as 'Canned Text'. The control  of the generation of this 'canned text' is embedded in the  procedural logic, often in an ad-hoc way.

> This approach can work well if  the system's discussion is straightforward and predictable. If  complex analysis is attempted, however, and the system designer  wants flexibility for the discussion to vary depending on the  particulars of the content, then this approach can become quite  unwieldy.

> There are a number of  drawbacks. 1] the programming of the discussion itself becomes  difficult. 2] Any major revision of the prose output may involve  substantial reprogramming. 3] The logic that generates the prose  expression may become hopelessly interwoven with the logic that  determines and organises the content of the material to be  discussed." p 56

The strategy used is described as  less ambitious than schemes which involve constructing  explanations from semantic information generated by an inference  mechanism. This is seen to be a research problem in  itself.

> "Attending has set itself an intermediate  goal: developing a flexible formalism to facilitate the  generation of polished  prose. Although the PROSENET approach is  clearly closer in spirit to canned text generation than to  sophisticated language generation it does allow the system  designer great flexibility to maipulate, massage, and refine the  system's prose output, independent of the rest of the system's  analysis." [Miller 84 p77] (Miller's emphasis)

> "From the standpoint of computer science,  critiquing can be perceived as a mode of explanation which lets a system  structure its advice around the particular concerns of the user  in a direct and natural way." [Miller 84 p 74] (Miller's  emphasis).

> "...critiquing allows the  physician to be the final decision maker. The computer is never  forced to commit itself to one approach or another." [Ibid]


[Waah! I forgot to copy a sample  explanation!]

[Here insert all the analysis and  discussion for this chapter...
## Models of  Explanation


## Developing  relevance

"Just as Thompson's lookup  program displayed exasperating shallowness, so total lookahead  has its own 'mentality' which from the point of view of the human  questioner could be described as impenetrably deep. While the  response of lookup is instantaneous, lookahead ruminates through  combinatorially vast ramifications while constructing its forward  tree of possibilities. Long rumination before each reply is not  of course in itself a guarantee of mental depth. But when asked  how it selected its move, lookahead is able to make an  exceptionally profound response by disgorging the complete  analysis tree. This comprises not only a complete strategy but at  the same time... a complete justification of the strategy. Could  anyone wish for a more profound response?

"On the contrary, mortal minds are  overwhelmed by so much  reactive detail. Reporting on the Three Mile  Island nuclear plant accident the Malone committee stated that  .... the operator was bombarded with displays, warning lights,  print-outs and so on to the point where detection of any error  condition and the assessment of the right action to correct the  condition was impossible'. So lookahead, with a quite opposite  mentality from lookup, has its own reasons for inability to  interact helpfully with a human." [ Michie, 83; Michie's  emphasis]

[look out and refer to recent work  by Shiela Hughes and Allison Kidd]

## Endnotes

1 This should not be understood  too literally, I think. The conceptual distinction between  algorithmic and heuristic programmes had not developed at the  time DENDRAL was first developed. The algorithm simply provides a  method of generating all the possible combinations of compounds  in a fixed sequence, and thus supports only part of the generate  stage.

2 This assertion will probably be  seen as contentious. I take as evidence the following: the  assertion [Davis and Lenat 1982 p 276] that .... the current  performance program (is) MYCIN', together with the diagram [  ibid., p 243 figure 2-3] which clearly shows that the explanation  module is outside the performance program. To support my argument  that the explanation mechanism described in [Davis et al. 1977] -  the MYCIN paper - is in fact the TEIRESIAS explanation module,  compare e.g. the discussion of information metrics [Davis and  Lenat p 269] with [Davis et al p 36]; and the sample explanations  given in the two sources.

3 MYCIN/TEIRESIAS used "certainty factors"  (not to be confused with formal indices of probability) to  express its confidence in steps of reasoning. These were entered  by the Knowledge Engineer for the individual rules, and  manipulated arithmetically by the inference mechanism. They  ranged in value from -1 (certainly false) through to (no  confidence at all in the reasoning step) to 1  (certainty). 

## References

Barr, A &amp; Feigenbaum, E A: The  Handbook of 'Artificial Intelligence, Pitman, 82,  especially articles VII B,  TEIRESIAS, and VIII B1, MYCIN

Brooke, S: Interactive Graphical  Representation of Knowledge: in Proceedings of the Alvey KBS Club  SIG on Explanation second workshop, 87 {have this}

Buchanan, B, Sutherland, G, &amp;  Feigenbaum, EA; Heuristic Dendral: a program for generating  explanatory hypotheses in organic chemistry: in Meltzer &amp; Michie,  eds, Machine Intelligence 4: Edinburgh University Press,  1969;

Buchanan, BG &amp; Feigenbaum, EA:  Dendral and Meta-Dendral: Their Applications Dimension: in  Artificial Intelligence 11, 1978

Davis, R, Buchanan, B and  Shortliffe, E: Production Rules as a Representation for a  Knowledge-Based Consultation Program: in Artificial Intelligence  8, 1977

Davis, R &amp; Lenat, D:  Knowledge-based systems in Artificial Intelligence; McGraw-Hill,  1982

especially part 2 chap 3 Hammond,  P, &amp; Sergot, M: A PROLOG Shell for Logic Based Expert Systems: in  Proceedings of Expert Systems 83: BCS

Martin, WA &amp; Fateman, RJ: The  Macsyma System: in Procedings of the 2nd Symposium on Symbolic  and Algebraic Manipulation: ACM: Los Angeles 1971

Michie, D: Game playing programs  and the conceptual interface: in Bramer, MA (ed): Computer Game  Playing theory and practice: Ellis Horwood, Chichester,  1983

Miller, Perry L: A Critiqueing  Approach to Expert Computer Advice: ATTENDING: Pitman Research  Notes in Artificial Intelligence 1, London, 1984

Mott, P &amp; Brooke, S: A Graphical  Inference Mechanism: in Expert Systems iv, 2, May 87

Pople, H E: The Formation of  Composite Hypotheses in Diagnostic Problem Solving - an Exercise  in Synthetic Reasoning in Papers presented at the 5th  International Joint Conference on Artificial Intelligence, MIT,  1977

Swartout, W: A Digitalis Therapy  Advisor with Explanations: in Proceedings of the 5th  International Joint Conference on Artificial Intelligence, MIT,  1977 {hav this}

Swartout, W R: XPLAIN: a System  for Creating and Explaining Expert Consulting Programs: in  Artificial Intelligence 21, 1983

Walker, A: Automatic Generation of  Explanations of Results from Knowledge Bases: Research Report  RJ3481, IBM Research Laboratory, San

Jose, California, 1982)

Walker, A et al, Knowledge Systems  and Prolog, Addison-Wesley, Reading (Mass.) 1987

Weiner, J L: BLAH, a system which  explains its reasoning: in Artificial Intelligence 15,  1980






