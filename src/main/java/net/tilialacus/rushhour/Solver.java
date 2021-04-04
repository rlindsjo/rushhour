package net.tilialacus.rushhour;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Solver {

    private Queue<State> queue = new LinkedList();

    private Set<State> seen = new HashSet<>();

    public void add(State state) {
        if (!queue.contains(state)) {
            queue.add(state);
        }
    }

    public int getNrPaths() {
        return queue.size();
    }

    public List<State> solve() {
        while (true) {
            State shortest = queue.remove();
            for (State option : shortest.options()) {
                if (option.isSolved()) {
                    return Collections.singletonList(option);
                } else {
                    if (seen.add(option)) {
                        queue.add(option);
                    }
                }
            }
        }
    }
}
