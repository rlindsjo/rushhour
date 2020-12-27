package net.tilialacus.rushhour;

import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static net.tilialacus.rushhour.State.empty;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class StateTest {

    @Test
    public void setBoard() {
        State state = empty()
                .add(1,2 , Cars.GRAY, true);
        assertThat(state.toString(), is("      \n      \n AA   \n      \n      \n      \n"));
        assertFalse(state.isSolved());
    }

    @Test
    public void solved() {
        State state = empty()
                .add(4,2 , Cars.RED, true);
        assertTrue(state.isSolved());
    }

    @Test
    public void freeMove() {
        State state = empty()
                .add(1,1 , Cars.GRAY, true)
                .add(0,1 , Cars.BROWN, false);

        Set<State> options = state.options();

        assertThat(options.stream().map(State::toString).collect(Collectors.toSet()),
                hasItems(
                        "B     \nBAA   \n      \n      \n      \n      \n",
                        "      \n AA   \nB     \nB     \n      \n      \n",
                        "      \nB AA  \nB     \n      \n      \n      \n"
                        ));
    }

    @Test
    public void compares() {
        State state1 = empty()
                .add(1, 1, Cars.GRAY, true)
                .add(0, 1, Cars.BROWN, false);
        State state2 = empty()
                .add(1, 1, Cars.GRAY, true)
                .add(0, 1, Cars.BROWN, false);
        assertEquals(state1, state2);
    }
}
