package net.tilialacus.rushhour;

import net.tilialacus.rushhour.Cars.Direction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static net.tilialacus.rushhour.Cars.Direction.HORIZONTAL;
import static net.tilialacus.rushhour.Cars.Direction.VERTICAL;

public class State {

    private static final int SIZE = 6;
    private static final char EMPTY = 0;
    private final State parent;
    private char[] data = new char[SIZE * SIZE];
    private String description;

    public State() {
        this.parent = null;
    }

    public static State empty() {
        return new State();
    }

    public State copy(String description) {
        return new State(this, true).description(description);
    }

    private State description(String description) {
        this.description = description;
        return this;
    }

    private State(State source, boolean link) {
        this.parent = link ? source : null;
        System.arraycopy(source.data, 0, data, 0, data.length);
    }

    public State set(int x, int y, char type) {
        if (type != EMPTY && data[x+y*SIZE] != 0) {
            throw new IllegalArgumentException("Conflict at (" + x + "," + y + ")");
        }
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

    public String getDescription() {
        return description;
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
                        for (int i = x - 1; free(i, y); i--) {
                            states.add(this.copy(car.identifier() + "\u2190" + (x-i))
                                    .line(x, y, EMPTY, car.getSize(), HORIZONTAL)
                                    .line(i, y, car.identifier(), car.getSize(), HORIZONTAL)
                            );
                        }
                        for (int i = x + 1; free(i + car.getSize() - 1, y); i++) {
                            states.add(this.copy(car.identifier() + "\u2192" + (i - x))
                                    .line(x, y, EMPTY, car.getSize(), HORIZONTAL)
                                    .line(i, y, car.identifier(), car.getSize(), HORIZONTAL)
                            );
                        }
                    } else if (get(x, y + car.getSize() - 1) == car.identifier()) { // vertical
                        for (int i = y - 1; free(x, i); i--) {
                            states.add(this.copy(car.identifier() + "\u2191" + (y - i))
                                    .line(x, y, EMPTY, car.getSize(), VERTICAL)
                                    .line(x, i, car.identifier(), car.getSize(), VERTICAL)
                            );
                        }
                        for (int i = y + 1; free(x, i + car.getSize() - 1); i++) {
                            states.add(this.copy(car.identifier() + "\u2193" + (i - y))
                                    .line(x, y, EMPTY, car.getSize(), VERTICAL)
                                    .line(x, i, car.identifier(), car.getSize(), VERTICAL)
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

    public State add(int x, int y, Cars car, Direction direction) {
        return new State(this, false)
                .line(x, y, car.identifier(), car.getSize(), direction);
    }

    private State line(int x, int y, char identifier, int size, Direction direction) {
        for (int i = 0; i < size; i++) {
            if (direction == HORIZONTAL) {
                set(x + i, y, identifier);
            } else {
                set(x, y + i, identifier);
            }
        }
        return this;
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
