package net.tilialacus.rushhour;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.tilialacus.rushhour.Piece.Direction.HORIZONTAL;
import static net.tilialacus.rushhour.Piece.Direction.VERTICAL;

public class Main {
    public static void main(String[] args) {
        State state = State.empty()
                .add(Piece.RED, 0, 2, HORIZONTAL)
                .add(Piece.LIGHT_GREEN, 0, 3, HORIZONTAL)
                .add(Piece.BROWN, 0, 4, VERTICAL)
                .add(Piece.GREEN, 1, 4, VERTICAL)
                .add(Piece.YELLOW, 2, 0, VERTICAL)
                .add(Piece.PINK, 2, 2, VERTICAL)
                .add(Piece.BEIGE, 2, 4, HORIZONTAL)
                .add(Piece.PURPLE, 2, 5, HORIZONTAL)
                .add(Piece.LAVENDEL, 3, 0, HORIZONTAL)
                .add(Piece.OLIVE, 3, 1, VERTICAL)
                .add(Piece.ORANGE, 3, 3, HORIZONTAL)
                .add(Piece.BLUE, 5, 2, VERTICAL);
        Solver solver = new Solver(state);

        List<State> solved = solver.solve();

        System.err.println(
                solved.stream()
                .map(State::getMoves)
                .map( it -> it.stream()
                        .filter(Objects::nonNull)
                        .map(Piece.Move::toString)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n"))
        );
    }
}
