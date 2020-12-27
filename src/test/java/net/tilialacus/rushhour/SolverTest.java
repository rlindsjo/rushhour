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
                        "      \n      \n  FF  \n      \n      \n      \n",
                        "      \n      \n   FF \n      \n      \n      \n",
                        "      \n      \n    FF\n      \n      \n      \n"
                )));

    }
}
