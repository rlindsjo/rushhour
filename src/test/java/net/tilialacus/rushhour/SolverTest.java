package net.tilialacus.rushhour;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.tilialacus.rushhour.Cars.Direction.HORIZONTAL;
import static net.tilialacus.rushhour.Cars.Direction.VERTICAL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SolverTest {

    private Solver solver = new Solver();

    @Test
    public void addToPaths() {
        solver.add(State.empty().add(0, 0, Cars.GRAY, HORIZONTAL));
        assertThat(solver.getNrPaths(), is(1));
    }

    @Test
    public void noDuplicates() {
        solver.add(State.empty().add(0, 0, Cars.GRAY, HORIZONTAL));
        solver.add(State.empty().add(0, 0, Cars.GRAY, HORIZONTAL));
        assertThat(solver.getNrPaths(), is(1));
    }

    @Test
    public void findSolution() {
        solver.add(State.empty().add(2, 2, Cars.RED, HORIZONTAL));

        List<State> solved = solver.solve();

        assertThat(solved.stream().map(State::toString).collect(Collectors.toList()),
                is(Arrays.asList(
                        "      \n      \n  XX  \n      \n      \n      \n",
                        "      \n      \n    XX\n      \n      \n      \n"
                )));

    }

    @Test
    public void findHardSolution() {
        solver.add(State.empty()
                .add(1, 2, Cars.RED, HORIZONTAL)
                .add(2, 0, Cars.LIGHT_GREEN, VERTICAL)
                .add(3, 0, Cars.DARK_YELLOW, HORIZONTAL)
                .add(3, 1, Cars.ORANGE, VERTICAL)
                .add(4, 1, Cars.LIGHT_BLUE, HORIZONTAL)
                .add(0, 2, Cars.PINK, VERTICAL)
                .add(4, 2, Cars.LAVENDEL, VERTICAL)
                .add(5, 2, Cars.PURPLE, VERTICAL)
                .add(1, 3, Cars.GREEN, VERTICAL)
                .add(0, 4, Cars.GRAY, VERTICAL)
                .add(2, 4, Cars.BEIGE, HORIZONTAL)
                .add(5, 4, Cars.YELLOW, VERTICAL)
                .add(1, 5, Cars.BROWN, HORIZONTAL)
                .add(3, 5, Cars.OLIVE, HORIZONTAL)
      );

        List<State> solved = solver.solve();

        assertThat(solved.stream()
                        .map(State::getDescription)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(",")),
                is("B↓1,D↑2,X←1,G↑1,J←1,A↓2,K←1,O←1,C←2,P↑1,E↑2,I↑2,H→2,B↓1,A↓1,X→2,F↑3,X←2,A↑1,B↑1,H←3,P↓2,I↓1,E↓1,C→1,B↓1,O→1,A↑2,X→3,A↓2,O←1,E↑1,X→1")
        );
    }
}
