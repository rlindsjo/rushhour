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
    private final Move move;

    private State() {
        this.parent = null;
        move = null;
    }

    public static State empty() {
        return new State();
    }

    private State copy(Move move) {
        return new State(this, move);
    }

    private State(State source, Move move) {
        this.move = move;
        this.parent = move != null ? source : null;
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

    public Move getMove() {
        return move;
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
                            states.add(this.copy(Move.left(car, x-i))
                                    .line(x, y, EMPTY, car.getSize(), HORIZONTAL)
                                    .line(i, y, car, car.getSize(), HORIZONTAL)
                            );
                        }
                        for (int i = x + 1; free(i + car.getSize() - 1, y); i++) {
                            states.add(this.copy(Move.right(car, i - x))
                                    .line(x, y, EMPTY, car.getSize(), HORIZONTAL)
                                    .line(i, y, car, car.getSize(), HORIZONTAL)
                            );
                        }
                    } else if (get(x, y + car.getSize() - 1) == car) { // vertical
                        for (int i = y - 1; free(x, i); i--) {
                            states.add(this.copy(Move.up(car, y - i))
                                    .line(x, y, EMPTY, car.getSize(), VERTICAL)
                                    .line(x, i, car, car.getSize(), VERTICAL)
                            );
                        }
                        for (int i = y + 1; free(x, i + car.getSize() - 1); i++) {
                            states.add(this.copy(Move.down(car, i - y))
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
        return new State(this, null)
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

    public static class Move {
        private final Car car;
        private final char direction;
        private final int steps;

        public static Move left(Car car, int steps) {
            return new Move(car, '\u2190', steps);
        }

        public static Move right(Car car, int steps) {
            return new Move(car, '\u2192', steps);
        }

        public static Move up(Car car, int steps) {
            return new Move(car, '\u2191', steps);
        }

        public static Move down(Car car, int steps) {
            return new Move(car, '\u2193', steps);
        }

        private Move(Car car, char direction, int steps) {
            this.car = car;
            this.direction = direction;
            this.steps = steps;
        }

        @Override
        public String toString() {
            return new StringBuilder().append(car.identifier()).append(direction).append(steps).toString();
        }
    }
}
