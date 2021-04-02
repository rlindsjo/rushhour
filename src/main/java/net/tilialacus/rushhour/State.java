package net.tilialacus.rushhour;

import net.tilialacus.rushhour.Car.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static net.tilialacus.rushhour.Car.Direction.HORIZONTAL;
import static net.tilialacus.rushhour.Car.Direction.VERTICAL;

public class State {

    private static final int SIZE = 6;
    private static final Car EMPTY = null;
    private final State parent;
    private Car[] data = new Car[SIZE * SIZE];
    private String description;

    private State() {
        this.parent = null;
    }

    public static State empty() {
        return new State();
    }

    private State copy(String description) {
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

    private void set(int x, int y, Car type) {
        if (type != null && data[x+y*SIZE] != EMPTY) {
            throw new IllegalArgumentException("Conflict at (" + x + "," + y + ")");
        }
        data[x+y*SIZE] = type;
    }

    private Car get(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return EMPTY;
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
                Car c = data[x + y * SIZE];
                if (c != EMPTY) {
                    b.append(c.identifier());
                } else {
                    b.append(' ');
                }
            }
            b.append('\n');
        }
        return b.toString();
    }

    public Collection<State> options() {
        Collection<State> states = new ArrayList<>();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (data[x + y * SIZE] != EMPTY) {
                    Car car = get(x, y);
                    if (get(x + car.getSize() - 1, y) == car) { // horisontal
                        for (int i = x - 1; free(i, y); i--) {
                            states.add(this.copy(car.identifier() + "\u2190" + (x-i))
                                    .line(x, y, EMPTY, car.getSize(), HORIZONTAL)
                                    .line(i, y, car, car.getSize(), HORIZONTAL)
                            );
                        }
                        for (int i = x + 1; free(i + car.getSize() - 1, y); i++) {
                            states.add(this.copy(car.identifier() + "\u2192" + (i - x))
                                    .line(x, y, EMPTY, car.getSize(), HORIZONTAL)
                                    .line(i, y, car, car.getSize(), HORIZONTAL)
                            );
                        }
                    } else if (get(x, y + car.getSize() - 1) == car) { // vertical
                        for (int i = y - 1; free(x, i); i--) {
                            states.add(this.copy(car.identifier() + "\u2191" + (y - i))
                                    .line(x, y, EMPTY, car.getSize(), VERTICAL)
                                    .line(x, i, car, car.getSize(), VERTICAL)
                            );
                        }
                        for (int i = y + 1; free(x, i + car.getSize() - 1); i++) {
                            states.add(this.copy(car.identifier() + "\u2193" + (i - y))
                                    .line(x, y, EMPTY, car.getSize(), VERTICAL)
                                    .line(x, i, car, car.getSize(), VERTICAL)
                            );
                        }
                    }
                }
            }
        }
        return states;
    }

    public boolean isSolved() {
        return get(SIZE - 1, 2) == Car.RED;
    }

    public State add(int x, int y, Car car, Direction direction) {
        return new State(this, false)
                .line(x, y, car, car.getSize(), direction);
    }

    private State line(int x, int y, Car car, int size, Direction direction) {
        for (int i = 0; i < size; i++) {
            if (direction == HORIZONTAL) {
                set(x + i, y, car);
            } else {
                set(x, y + i, car);
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

    public List<State> getPath() {
        List<State> base = parent != null ? parent.getPath() : new LinkedList<>();
        base.add(this);
        return base;
    }
}
