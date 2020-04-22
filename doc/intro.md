## Introduction to Wildwood

I started building Wildwood nearly forty years ago on InterLisp-D workstations.
Then, because of changing academic projects, I lost access to those machines,
and the project was effectively abandoned. But, I've kept thinking about it; it
has cool ideas.

### Explicable inference

Wildwood was a follow on from ideas developed in Arboretum, an inference system
based on a novel propositional logic using defaults. Arboretum was documented in
our paper

[Mott, P & Brooke, S: A graphical inference mechanism : Expert Systems Volume 4, Issue 2, May 1987, Pages 106-117]
(https://onlinelibrary.wiley.com/doi/epdf/10.1111/j.1468-0394.1987.tb00133.x)

Two things were key about this system: first, we had a systematic mechanism for
eliciting knowledge from domain experts into visual representations which it
was easy for those experts to validate, and second, the system could easily
generate high quality natural language explanations of its decisions, which
could be understood (and therefore be challenged) by ordinary people

This explicability was, I felt, a key value. Wildwood, while being able to infer
over much broader and more messy domains, should be at least as transparent
and easy to understand as Arboretum.

### Game theoretic reasoning

The insight which is central to the design of Wildwood is that human argument
does not seek to preserve truth, it seeks to be hegemonic: to persuade the
auditor of the argument of the advocate.

Consequently, an inference process should be a set of at least two arguing
processes, each of whom takes a different initial view and seeks to defend it
using a system of legal moves.

### Against truth

Wildwood was originally intended to be a part of my (unfinished) thesis,
[Against Truth](AgainstTruth.html), which is included in this archive for
your amusement.
