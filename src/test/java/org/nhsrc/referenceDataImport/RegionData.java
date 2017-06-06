package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.State;

import java.util.HashMap;
import java.util.Map;

public class RegionData {
    private Map<String, State> states;

    public RegionData() {
        this.states = new HashMap<>();
    }

    public State addState(State state) {
        State existingState = states.getOrDefault(state.getName(), new State(state.getName()));
        existingState.addDistricts(state.getDistricts());
        this.states.put(existingState.getName(), existingState);
        return existingState;
    }

    public Map<String, State> getStates() {
        return states;
    }
}
