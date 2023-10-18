package fa.nfa;

import fa.State;
import java.util.*;

public class NFAState extends State implements Comparable<NFAState>{
    
    private Map<Character, Set<NFAState>> myTransitions;
    /**
     * Empty constructor for NFAState
     */
    public NFAState() {
        myTransitions = null;
    }
    /**
     * Constructor that calls States constructor to create an NFAState with
     * the provided name
     * @param name name for the state
     */
    public NFAState(String name) {
        super(name);
        this.myTransitions = new HashMap<Character, Set<NFAState>>();
    }
    /**
     * adds a transition to this NFAState
     * @param Character Symbol that causes the transition into the other state
     * @param nfaState State that is transitioned into from this state
     */
    public void addTransition(char symbol, Set<NFAState> toStates) {
        if (!this.myTransitions.containsKey(symbol)) {
            this.myTransitions.put(symbol, toStates);
        } else {
            for(NFAState state : toStates) this.myTransitions.get(symbol).add(state);
        }
    }
    /**
     * returns the map of transitions that this NFAState contains
     * @return the transitions of the NFAState
     */
    public Map<Character, Set<NFAState>> getTransitions() {
        return this.myTransitions;
    }
    /**
     * Compares this NFAs name to the one placed inside the parameter
     */
    public int compareTo(NFAState o) {
       return this.getName().compareTo(o.getName());
    }


}
