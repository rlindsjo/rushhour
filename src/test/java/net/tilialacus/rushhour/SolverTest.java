package net.tilialacus.rushhour;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SolverTest {

    private Solver solver = new Solver();

    @Test
    public void addToPaths() {
        solver.add(State.empty().add(0, 0, Cars.GRAY, true));
        assertThat(solver.getNrPaths(), is(1));
    }

    @Test
    public void noDuplicates() {
        solver.add(State.empty().add(0, 0, Cars.GRAY, true));
        solver.add(State.empty().add(0, 0, Cars.GRAY, true));
        assertThat(solver.getNrPaths(), is(1));
    }

    @Test
    public void findSolution() {
        solver.add(State.empty().add(2, 2, Cars.RED, true));

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
                .add(1, 2, Cars.RED, true)
                .add(2, 0, Cars.LIGHT_GREEN, false)
                .add(3, 0, Cars.DARK_YELLOW, true)
                .add(3, 1, Cars.ORANGE, false)
                .add(4, 1, Cars.LIGHT_BLUE, true)
                .add(0, 2, Cars.PINK, false)
                .add(4, 2, Cars.LAVENDEL, false)
                .add(5, 2, Cars.PURPLE, false)
                .add(1, 3, Cars.GREEN, false)
                .add(0, 4, Cars.GRAY, false)
                .add(2, 4, Cars.BEIGE, true)
                .add(5, 4, Cars.YELLOW, false)
                .add(1, 5, Cars.BROWN, true)
                .add(3, 5, Cars.OLIVE, true)
      );

        List<State> solved = solver.solve();

        for (int i = 0; i < solved.size(); i++) {
            System.err.println(i + " " + solved.get(i).getDescription());
            System.err.println(solved.get(i));
        }
    }
}
