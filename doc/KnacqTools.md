# KnacqTools

## Background

KnacqTools ('Knowledge Acquisition Toolkit") was essentially a productisation of the ideas developed in [Arboretum](Arboretum.html). It was written in C, originally for Acorn's RISC OS operating system, and later ported to UNIX. The only major innovation of KnacqTools was that it was able to transform DTree knowledge structures into the rule languages of a number of contemporary 'expert system' inference engines.

Thus the expected use of KnacqTools was not to run an inference process itself (although of course it could do this), but to allow a knowledge engineer, using Peter Mott's 'elicitation by exception' technique, which I and others had polished in the field, to enter DTrees elicited from domain experts, compile these DTrees into production rules, and export those prodution rules to the selected expert system package for deployment.

KnacqTools was briefly successful between about 1990 and 1992. The commercially important UNIX port was never fully debugged.

## KnacqTools and explanation

While the KnacqTools engine had a complete implementation of Arboretum's explanation system and generated high quality explanations itself, the exported production rule sets (depending on the selected expert system package) was not necessarily capable of the same degree of explanation.

There were no new innovations in explanation, in KnacqTools.
