package net.tilialacus.rushhour;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import static java.util.Collections.emptyList;

public class Solver {
    private Queue<State> possible = new LinkedList();

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
        while (!possible.isEmpty()) {
            for (State option : possible.poll().options()) {
                if (option.isSolved()) {
                    return Collections.singletonList(option);
                } else {
                    add(option);
                }
            }
        }
        return emptyList();
    }
}
