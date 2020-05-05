# Scratchpad: Working notes, not part of the main document

## A supporting context

Bialowieza is probably never going to be part of an actual game engine, and is probably always just an experimentation platform/proof of concept demonstrator. However, it would be good to get up to a point where we could

1. Analyse a corpus of text to extract knowledge;
2. Query that knowledge in a reasonable verisimilitude of natural language;
3. Accept those queries through a spoken word rather than typed interface;
4. Speak the responses in reasonably natural language.

Components which would help with this (I don't need to reinvent **all** the wheels:

### Text analysis

(Supports both 1. and 2. above):

1. [OpenNLP](https://github.com/dakrone/clojure-opennlp)
2. [Agents for Actors](https://github.com/mwkuster/agents-for-actors)

### Speech recognition

1. [speech-recognition](https://github.com/klutometis/speech-recognition)
2.

### Text to speech

