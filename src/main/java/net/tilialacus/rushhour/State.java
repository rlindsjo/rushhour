package net.tilialacus.rushhour;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class State {

    private static final int SIZE = 6;
    private static final char EMPTY = 0;
    private final State parent;
    private char[] data = new char[SIZE * SIZE];

    public State() {
        this.parent = null;
    }

    public static State empty() {
        return new State();
    }

    public State copy() {
        return new State(this, true);
    }

    private State(State source, boolean link) {
        this.parent = link ? source : null;
        System.arraycopy(source.data, 0, data, 0, data.length);
    }

    public State set(int x, int y, char type) {
        data[x+y*SIZE] = type;
        return this;
    }

    public char get(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return 0;
        }
        return data[x+y*SIZE];
    }

    private boolean free(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false;
        }
        return data[x+y*SIZE] == EMPTY;
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
                if (data[x + y * SIZE] != EMPTY) {
                    Cars car = Cars.resolve(get(x, y));
                    if (get(x + car.getSize() - 1, y) == car.identifier()) { // horisontal
                        if (free(x - 1, y)) {
                            states.add(this.copy()
                                    .set(x - 1, y, car.identifier())
                                    .set(x + car.getSize() - 1, y, EMPTY)
                            );
                        }
                        if (free( x + car.getSize(), y)) {
                            states.add(this.copy()
                                    .set(x , y, EMPTY)
                                    .set(x + car.getSize(), y, car.identifier())
                            );
                        }
                    } else if (get(x, y + car.getSize() - 1) == car.identifier()) { // vertical
                        if (free(x, y - 1)) {
                            states.add(this.copy()
                                    .set(x, y - 1, car.identifier())
                                    .set(x, y + car.getSize() - 1, EMPTY)
                            );
                        }
                        if (free(x, y + car.getSize())) {
                            states.add(this.copy()
                                    .set(x, y, EMPTY)
                                    .set(x, y + car.getSize(), car.identifier())
                            );
                        }
                    }
                }
            }
        }
        return states;
    }

    public boolean isSolved() {
        return data[SIZE - 1 + 2 * SIZE ] == Cars.RED.identifier();
    }

    public State add(int x, int y, Cars car, boolean horisontal) {
        State newState = new State(this, false);
        for (int i = 0; i < car.getSize(); i++) {
            if (horisontal) {
                newState.set(x + i, y, car.identifier());
            } else {
                newState.set(x, y + i, car.identifier());
            }
        }
        return newState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Arrays.equals(data, state.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    public State getParent() {
        return parent;
    }
}
