package fa.nfa;

import fa.State;
import java.util.*;

public class NFAState extends State{
    
    private Map<Character, Set<State>> myTransitions;
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
        this.myTransitions = new HashMap<Character, Set<State>>();
    }
    /**
     * adds a transition to this NFAState
     * @param Character Symbol that causes the transition into the other state
     * @param nfaState State that is transitioned into from this state
     */
    public void addTransition(char symbol, Set<State> nfaState) {
        this.myTransitions.put(symbol, nfaState);
    }


}
