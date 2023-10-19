#CS 361: Project 2 (Non-Deterministic Finite Automata)  
* Authors: Nicholas Merritt, Kai Sorensen
* Class: CS361 Section 001
* Semester: Fall 2023

## Overview
This program emulates a Non-deterministic finite automata. Allowing users to create and test NFA's

## Reflection
Biggest issue that we ran into in this project, and possibly an issue we faced in the first one, was figuring out how objects pass their information between classes. For instance, if you called getTransitions on the first state, it succeeded in returning a list of all tranitions the state had. If you were to follow those transitions and attempt it again from an NFAState reached from there, it would return null. We figured out that we had to copy these NFAStates from our original set to get the correct information from their instance variables.

If I could go back in time, back to the first project, I would totally place the map inside the DFAState. It worked so well in this project. Getting the transitions from each individual NFAState rather than some map that held every single transition worked much better. 

## Compiling and Using

To run this code, open the main project directory in a linux terminal.

Compilation command:
```
find . -name "*.java" -exec javac {} \;
```

To run the tester, locate the "test" folder within the main directory and run the following command:
```
java NFATest
``` 

## Sources Used