package fa.nfa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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



    @Override
    public boolean accepts(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accepts'");
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

    @Override
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

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getToState'");
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        



    }

    @Override
    public int maxCopies(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'maxCopies'");
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