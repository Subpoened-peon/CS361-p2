package fa.nfa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Stack;
import fa.State;

public class NFA implements NFAInterface {

    private TreeSet<Character> alphabet;
    private TreeSet<NFAState> states;
    private TreeSet<NFAState> finalStates;

    private NFAState start;

    public NFA() {
        alphabet = new TreeSet<Character>();
        states = new TreeSet<NFAState>();
        finalStates = new TreeSet<NFAState>();

        this.start = null;
    }

    public boolean addState(String name) {
        return states.add(new NFAState(name));
    }

    public boolean setFinal(String name) {
        boolean check = false;
        Iterator<NFAState> stCheck = states.iterator();
        while(stCheck.hasNext()) {
            if(stCheck.next().getName().equals(name)) {
                finalStates.add(new NFAState(name));
                check = true;
            }
        }
        return check;
    }

    public boolean setStart(String name) {
        boolean check = false;
        Iterator<NFAState> stCheck = states.iterator();

        while(stCheck.hasNext()) {
            NFAState current = stCheck.next();
            if(current.getName().equals(name)) {
                this.start = current;
                check = true;
            }
        }
        return check;
    }

   
    public void addSigma(char symbol) {
        alphabet.add(symbol);
    }



    public boolean accepts(String s) {
        //think of searching an NFA as a tree diagram, this is a layer of the tree
        TreeSet<NFAState> stateLayer = new TreeSet<NFAState>();
        stateLayer.add(start);
        stateLayer.addAll(eClosure(start));

        for(int i = 0; i < s.length(); i++) {

            char currentTransition = s.charAt(i);
            TreeSet<NFAState> newLayer = new TreeSet<NFAState>(); //which possible states can we transition to?
            for(NFAState state : stateLayer) {
                if(state.getTransitions().containsKey(currentTransition)) { //if there are transitions from the current state on the current symbol
                    newLayer.addAll(state.getTransitions().get(currentTransition)); //add the possible transitions
                }
            }
            /* This next part is goofy and inefficient, but it works. I made tempLayer as an object that looks at
             * which states are in new layer then adds the real states with working transitions. */
            TreeSet<NFAState> tempLayer = new TreeSet<NFAState>();
            for(NFAState state : newLayer) {
                for(NFAState realState : states) {
                    if(state.getName().equals(realState.getName())){
                        tempLayer.add(realState);
                        tempLayer.addAll(eClosure(realState)); //add the e-Closure of the new states
                    } 
                }
            }
            stateLayer = tempLayer; //now our new layer is complete and we can go on to the next one
        }
        
        //Did we finish on an end state? Let's see if the final layer of the tree contains an end state
        for(NFAState state : stateLayer) {
                if(isFinal(state.getName())) return true; 
        }

        return false;
    }
    
    public Set<Character> getSigma() {
        return this.alphabet;
    }

    public NFAState getState(String name) {
        for(NFAState state : states) {
            if(state.getName().equals(name)){
                return state;
            }
        }
        return null;
    }

    public boolean isFinal(String name) {
        boolean check = false;
        for(State s : finalStates) {
            if(s.getName().equals(name)) {
                check = true;
            }
        }
        return check;
    }

    public boolean isStart(String name) {
        return name.equals(start.getName());
    }

    public Set<NFAState> getToState(NFAState from, char onSymb) {
        Set<NFAState> delta = new TreeSet<>();

        if(states.contains(from)) {
            Map<Character, Set<NFAState>> transitions = from.getTransitions();
        if (transitions != null && transitions.containsKey(onSymb)) {
            for(NFAState TransitionStates : transitions.get(onSymb)) {
                     //This is to get the actual states with their myTransitions map copied
                for(NFAState state : states) {
                    if(TransitionStates.compareTo(state) == 0) {
                         TransitionStates = state;
                         break;
                    }
                    delta.add(TransitionStates);
                }
            }
                Set<NFAState> epsilonClosure = this.eClosure(from);
                if(epsilonClosure != null) {
                    delta.addAll(epsilonClosure);
                }
            }
        }

        return delta;
    }

    public Set<NFAState> eClosure(NFAState s) {
        Set<NFAState> eClosures = new TreeSet<>();

        Stack<NFAState> eStack = new Stack<>();

        //Starting the stack with NFAState s
        eStack.push(s);
        while(!eStack.isEmpty()) {
            NFAState currState = eStack.pop();
            if (!eClosures.contains(currState)) {
                eClosures.add(currState);
                Set<NFAState> eTransitions = currState.getTransitions().get('e');
                if(eTransitions != null) {
                    Iterator<NFAState> eIterator = eTransitions.iterator();
                    while(eIterator.hasNext()) {
                        NFAState nextState = eIterator.next();

                        //This is to get the actual states with their myTransitions map copied
                        for(NFAState state : states) {
                            if(nextState.compareTo(state) == 0) {
                                nextState = state;
                                break;
                            }
                        }
                        if(!eClosures.contains(nextState)) {
                            eStack.push(nextState);
                        }
                    }
                }
            }
        }
        return eClosures;
    }

    public int maxCopies(String s) {
        int maxCopies = 1;
        //think of searching an NFA as a tree diagram, this is a layer of the tree
        /*This method is almost identical to accepts, except it returns a number that is equal to the max
            number of states that can occur in a layer.
        */
        TreeSet<NFAState> stateLayer = new TreeSet<NFAState>();
        stateLayer.add(start);
        stateLayer.addAll(eClosure(start));
        maxCopies = Math.max(maxCopies, stateLayer.size());
        for(int i = 0; i < s.length(); i++) {
            System.out.println("Step " + i + ": Active States: " + stateLayer + " copies: " + stateLayer.size());
            char currentTransition = s.charAt(i);
            TreeSet<NFAState> newLayer = new TreeSet<NFAState>(); //which possible states can we transition to?
            for(NFAState state : stateLayer) {
                if(state.getTransitions().containsKey(currentTransition)) { //if there are transitions from the current state on the current symbol
                    newLayer.addAll(state.getTransitions().get(currentTransition)); //add the possible transitions
                }
            }
            TreeSet<NFAState> tempLayer = new TreeSet<NFAState>();
            for(NFAState state : newLayer) {
                for(NFAState realState : states) {
                    if(state.getName().equals(realState.getName())){
                        tempLayer.add(realState);
                        tempLayer.addAll(eClosure(realState)); //add the e-Closure of the new states
                    } 
                }
            }
            stateLayer = tempLayer; 
            maxCopies = Math.max(maxCopies, stateLayer.size());
        }
        return maxCopies;
    }

    public boolean addTransition(String fromState, Set<String> toStateStrings, char onSymb) {
        Set<NFAState> toStates = new TreeSet<NFAState>();
        for(String stateString : toStateStrings) {
            toStates.add(new NFAState(stateString));
        }
        for(NFAState state : toStates) {
            if(!states.contains(state)) return false;
        }

        if(states.contains(new NFAState(fromState)) && (alphabet.contains(onSymb) || onSymb == 'e')) {
            Iterator<NFAState> stCheck = states.iterator();
            while(stCheck.hasNext()) {
                NFAState current = stCheck.next();
                if(current.getName().equals(fromState)) {
                    current.addTransition(onSymb, toStates);
                    return true;
                }
            }
        } 
        return false;
    }

    //if there are any e transitions OR if there are multiple transitions on the same symbol
    public boolean isDFA() {
        for(NFAState state : states) {
            Map<Character, Set<NFAState>> currtransitions = state.getTransitions();
            if (currtransitions.keySet().contains(Character.valueOf('e'))) return false;
            for(Character tran : alphabet) {
                if(currtransitions.containsKey(tran) && currtransitions.get(tran).size() > 1) {
                    return false;
                }
            }
        }
        return true;
    }

}