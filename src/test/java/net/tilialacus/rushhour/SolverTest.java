package net.tilialacus.rushhour;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SolverTest {

    private Solver solver = new Solver();

    @Test
    public void addToPaths() {
        solver.add(State.empty().add(0, 0, Cars.GRAY, true));
        assertThat(solver.getNrPaths(), is(1));
    }

}