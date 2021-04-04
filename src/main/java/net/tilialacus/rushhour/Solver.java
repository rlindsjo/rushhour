package net.tilialacus.rushhour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Solver {
    private List<State> possible = new LinkedList();

    private Set<State> seen = new HashSet<>();

    public Solver(State state) {
        add(state);
    }

    private void add(State state) {
        if (seen.add(state)) {
            possible.add(state);
        }
    }

    public int getNrPaths() {
        return possible.size();
    }

    public List<State> solve() {
        List<State> result = new ArrayList<>();

        do {
            List<State> sameDistance = possible;
            possible = new LinkedList<>();

            // No solutions?
            if (sameDistance.isEmpty()) {
                return Collections.emptyList();
            }
            for (State o : sameDistance) {
                for (State option : o.options()) {
                    if (option.isSolved()) {
                        result.add(option);
                    } else {
                        add(option);
                    }
                }
            }
        } while (result.isEmpty());
        return result;
    }
}
