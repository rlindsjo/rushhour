package net.tilialacus.rushhour;

import java.util.LinkedList;
import java.util.Queue;

public class Solver {

    private Queue queue = new LinkedList();

    public void add(State state) {
        if (!queue.contains(state)) {
            queue.add(state);
        }
    }

    public int getNrPaths() {
        return queue.size();
    }
}
