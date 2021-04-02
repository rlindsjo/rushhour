package net.tilialacus.rushhour;

import org.junit.Test;

import java.util.Collection;
import java.util.stream.Collectors;

import static net.tilialacus.rushhour.Car.Direction.HORIZONTAL;
import static net.tilialacus.rushhour.Car.Direction.VERTICAL;
import static net.tilialacus.rushhour.State.empty;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class StateTest {

    @Test
    public void setBoard() {
        State state = empty()
                .add(1,2 , Car.GRAY, HORIZONTAL);
        assertThat(state.toString(), is("      \n      \n GG   \n      \n      \n      \n"));
        assertFalse(state.isSolved());
    }

    @Test
    public void preventConflict() {

        State state = empty()
                .add(1,1 , Car.GRAY, HORIZONTAL);
        try {
            state.add(1, 0, Car.BROWN, VERTICAL);
            fail("Expected exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Conflict at (1,1)"));
        }
    }

    @Test
    public void solved() {
        State state = empty()
                .add(4,2 , Car.RED, HORIZONTAL);
        assertTrue(state.isSolved());
    }

    @Test
    public void freeMove() {
        State state = empty()
                .add(1,1 , Car.GRAY, HORIZONTAL)
                .add(0,1 , Car.BROWN, VERTICAL);

        Collection<State> options = state.options();

        assertThat(options.stream().map(State::toString).collect(Collectors.toSet()),
                hasItems(
                        "J     \nJGG   \n      \n      \n      \n      \n",
                        "      \n GG   \nJ     \nJ     \n      \n      \n",
                        "      \nJ GG  \nJ     \n      \n      \n      \n"
                        ));
    }

    @Test
    public void compares() {
        State state1 = empty()
                .add(1, 1, Car.GRAY, HORIZONTAL)
                .add(0, 1, Car.BROWN, VERTICAL);
        State state2 = empty()
                .add(1, 1, Car.GRAY, HORIZONTAL)
                .add(0, 1, Car.BROWN, VERTICAL);
        assertEquals(state1, state2);
    }
}
