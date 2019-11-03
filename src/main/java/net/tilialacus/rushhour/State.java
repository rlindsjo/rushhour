package net.tilialacus.rushhour;

import java.util.HashSet;
import java.util.Set;

public class State {

    private static final int SIZE = 6;
    private static final char EMPTY = 0;
    private final State parent;
    private char[] data = new char[SIZE * SIZE];

    public State() {
        this.parent = null;
    }

    private State(State parent, int x , int y, int dx, int dy) {
        this.parent = parent;
        System.arraycopy(parent.data, 0, data, 0, data.length);

        char c = get(x + dx, y + dy);
        do {
            set(x,y, c);
            x += dx;
            y += dy;
        } while (get(x, y) != c);
        set(x, y, EMPTY);
    }

    public void set(int x, int y, char type) {
        data[x+y*SIZE] = type;
    }

    public char get(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return 0;
        }
        return data[x+y*SIZE];
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                char c = data[x + y * SIZE];
                if (c != EMPTY) {
                    b.append(c);
                } else {
                    b.append(' ');
                }
            }
            b.append('\n');
        }
        return b.toString();
    }

    public Set<State> options() {
        HashSet<State> states = new HashSet<>();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (data[x + y * SIZE] == 0) {
                    if (get(x-1, y) != EMPTY) {
                        State newState = new State(this, x, y, -1, 0);
                        states.add(newState);
                    }
                    if (get(x + 1, y) != EMPTY) {
                        State newState = new State(this, x, y, 1, 0);
                        states.add(newState);
                    }
                    if (get(x, y - 1) != EMPTY) {
                        State newState = new State(this, x, y, 0, -1);
                        states.add(newState);
                    }
                    if (get(x, y + 1) != EMPTY) {
                        State newState = new State(this, x, y, 0, 1);
                        states.add(newState);
                    }
                }
            }
        }
        return states;
    }
}
