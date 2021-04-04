package net.tilialacus.rushhour;

import net.tilialacus.rushhour.Piece.Direction;
import net.tilialacus.rushhour.Piece.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static net.tilialacus.rushhour.Piece.Direction.HORIZONTAL;
import static net.tilialacus.rushhour.Piece.Direction.VERTICAL;

public class State {

    private static final int SIZE = 6;
    private static final Piece EMPTY = null;
    private Piece[] data = new Piece[SIZE * SIZE];
    private final Moves moves;

    private State() {
        moves = Moves.NONE;
    }

    public static State empty() {
        return new State();
    }

    private State copy(Move move) {
        return new State(this, move);
    }

    private State(State source, Move move) {
        this.moves = source.moves.add(move);
        System.arraycopy(source.data, 0, data, 0, data.length);
    }

    private void set(int x, int y, Piece type) {
        if (type != null && data[x+y*SIZE] != EMPTY) {
            throw new IllegalArgumentException("Conflict at (" + x + "," + y + ")");
        }
        data[x+y*SIZE] = type;
    }

    private Piece get(int x, int y) {
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

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                Piece c = data[x + y * SIZE];
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
                    Piece piece = get(x, y);
                    if (get(x + piece.getSize() - 1, y) == piece) { // horisontal
                        for (int i = x - 1; free(i, y); i--) {
                            states.add(this.copy(piece.left(x-i))
                                    .line(x, y, EMPTY, piece.getSize(), HORIZONTAL)
                                    .line(i, y, piece, piece.getSize(), HORIZONTAL)
                            );
                        }
                        for (int i = x + 1; free(i + piece.getSize() - 1, y); i++) {
                            states.add(this.copy(piece.right(i - x))
                                    .line(x, y, EMPTY, piece.getSize(), HORIZONTAL)
                                    .line(i, y, piece, piece.getSize(), HORIZONTAL)
                            );
                        }
                    } else if (get(x, y + piece.getSize() - 1) == piece) { // vertical
                        for (int i = y - 1; free(x, i); i--) {
                            states.add(this.copy(piece.up(y - i))
                                    .line(x, y, EMPTY, piece.getSize(), VERTICAL)
                                    .line(x, i, piece, piece.getSize(), VERTICAL)
                            );
                        }
                        for (int i = y + 1; free(x, i + piece.getSize() - 1); i++) {
                            states.add(this.copy(piece.down(i - y))
                                    .line(x, y, EMPTY, piece.getSize(), VERTICAL)
                                    .line(x, i, piece, piece.getSize(), VERTICAL)
                            );
                        }
                    }
                }
            }
        }
        return states;
    }

    public boolean isSolved() {
        return get(SIZE - 1, 2) == Piece.RED;
    }

    public State add(Piece piece, int x, int y, Direction direction) {
        return new State(this, null)
                .line(x, y, piece, piece.getSize(), direction);
    }

    private State line(int x, int y, Piece piece, int size, Direction direction) {
        for (int i = 0; i < size; i++) {
            if (direction == HORIZONTAL) {
                set(x + i, y, piece);
            } else {
                set(x, y + i, piece);
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

    public List<Move> getMoves() {
        return moves.getPath();
    }

    private static final class Moves {
        public static final Moves NONE = new Moves(null, null);
        private final Moves prev;
        private final Move move;

        private Moves(Moves prev, Move move) {
            this.prev = prev;
            this.move = move;
        }

        public Moves add(Move move) {
            return new Moves(this, move);
        }

        public List<Move> getPath() {
            List<Move> base = prev != null ? prev.getPath() : new LinkedList<>();
            base.add(this.move);
            return base;
        }
    }
}
