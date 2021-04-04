package net.tilialacus.rushhour;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.tilialacus.rushhour.Car.Direction.HORIZONTAL;
import static net.tilialacus.rushhour.Car.Direction.VERTICAL;

public class Main {
    public static void main(String[] args) {
        State state = State.empty()
                .add(1, 2, Car.RED, HORIZONTAL)
                .add(2, 0, Car.LIGHT_GREEN, VERTICAL)
                .add(3, 0, Car.DARK_YELLOW, HORIZONTAL)
                .add(3, 1, Car.ORANGE, VERTICAL)
                .add(4, 1, Car.LIGHT_BLUE, HORIZONTAL)
                .add(0, 2, Car.PINK, VERTICAL)
                .add(4, 2, Car.LAVENDEL, VERTICAL)
                .add(5, 2, Car.PURPLE, VERTICAL)
                .add(1, 3, Car.GREEN, VERTICAL)
                .add(0, 4, Car.GRAY, VERTICAL)
                .add(2, 4, Car.BEIGE, HORIZONTAL)
                .add(5, 4, Car.YELLOW, VERTICAL)
                .add(1, 5, Car.BROWN, HORIZONTAL)
                .add(3, 5, Car.OLIVE, HORIZONTAL);
        Solver solver = new Solver(state);

        List<State> solved = solver.solve();

        System.err.println(
                solved.stream()
                .map(State::getPath)
                .map( it -> it.stream()
                        .map(State::getMove)
                        .filter(Objects::nonNull)
                        .map(Car.Move::toString)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n"))
        );
    }
}
