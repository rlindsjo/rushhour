package net.tilialacus.rushhour;

import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.tilialacus.rushhour.Car.Direction.HORIZONTAL;
import static net.tilialacus.rushhour.Car.Direction.VERTICAL;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SolverTest {
    @Test
    public void addToPaths() {
        Solver solver = new Solver(State.empty().add(0, 0, Car.GRAY, HORIZONTAL));
        assertThat(solver.getNrPaths(), is(1));
    }

    @Test
    public void findSolution() {
        Solver solver = new Solver(State.empty().add(2, 2, Car.RED, HORIZONTAL));

        List<State> solved = solver.solve();

        assertThat(solved.size(), is(1));
        assertThat(solved.get(0)
                        .getMoves()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(Car.Move::toString)
                        .collect(Collectors.joining(",")),
                is("X→2"));
    }

    @Test
    public void findHardSolution() {
        Solver solver = new Solver(State.empty()
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
                .add(3, 5, Car.OLIVE, HORIZONTAL)
        );

        List<State> solved = solver.solve();

        assertThat(solved.stream()
                        .map(State::getMoves)
                        .map(it -> it.stream()
                                .filter(Objects::nonNull)
                                .map(Car.Move::toString)
                                .collect(Collectors.joining(",")))
                        .collect(Collectors.toSet()),
                hasItems(
                        "B↓1,D↑2,X←1,A↓2,O←1,C←2,P↑1,E↑2,G↑1,I↑2,H→2,A↓1,B↓1,X→2,F↑3,X←2,A↑1,B↑1,H←3,B↓1,I↓2,E↓1,O→1,J←1,K←1,P↓2,C→1,A↑2,X→3,A↓1,O←1,E↑1,X→1",
                        "B↓1,D↑2,X←1,A↓2,O←1,C←2,P↑1,E↑2,G↑1,I↑2,H→2,A↓1,B↓1,X→2,F↑3,X←2,A↑1,B↑1,H←3,B↓1,I↓2,E↓1,O→1,J←1,K←1,P↓2,C→1,A↑2,X→3,A↓2,O←1,E↑1,X→1",
                        "B↓1,D↑2,X←1,A↓2,O←1,C←2,P↑1,E↑2,G↑1,I↑2,H→2,A↓1,B↓1,X→2,F↑3,X←2,A↑1,B↑1,H←3,B↓1,I↓1,E↓1,O→1,J←1,K←1,P↓2,C→1,A↑2,X→3,A↓1,O←1,E↑1,X→1",
                        "B↓1,D↑2,X←1,A↓2,O←1,C←2,P↑1,E↑2,G↑1,I↑2,H→2,A↓1,B↓1,X→2,F↑3,X←2,A↑1,B↑1,H←3,B↓1,I↓1,E↓1,O→1,J←1,K←1,P↓2,C→1,A↑2,X→3,A↓2,O←1,E↑1,X→1"
                )
        );
    }
}
