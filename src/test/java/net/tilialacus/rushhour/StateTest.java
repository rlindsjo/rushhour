package net.tilialacus.rushhour;

import org.junit.Test;

import java.util.Collection;
import java.util.stream.Collectors;

import static net.tilialacus.rushhour.Piece.Direction.HORIZONTAL;
import static net.tilialacus.rushhour.Piece.Direction.VERTICAL;
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
                .add(Piece.GRAY, 1,2 , HORIZONTAL);
        assertThat(state.toString(), is("      \n      \n GG   \n      \n      \n      \n"));
        assertFalse(state.isSolved());
    }

    @Test
    public void preventConflict() {

        State state = empty()
                .add(Piece.GRAY, 1,1 , HORIZONTAL);
        try {
            state.add(Piece.BROWN, 1, 0, VERTICAL);
            fail("Expected exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Conflict at (1,1)"));
        }
    }

    @Test
    public void solved() {
        State state = empty()
                .add(Piece.RED, 4,2 , HORIZONTAL);
        assertTrue(state.isSolved());
    }

    @Test
    public void freeMove() {
        State state = empty()
                .add(Piece.GRAY, 1,1 , HORIZONTAL)
                .add(Piece.BROWN, 0,1 , VERTICAL);

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
                .add(Piece.GRAY, 1, 1, HORIZONTAL)
                .add(Piece.BROWN, 0, 1, VERTICAL);
        State state2 = empty()
                .add(Piece.GRAY, 1, 1, HORIZONTAL)
                .add(Piece.BROWN, 0, 1, VERTICAL);
        assertEquals(state1, state2);
    }
}
