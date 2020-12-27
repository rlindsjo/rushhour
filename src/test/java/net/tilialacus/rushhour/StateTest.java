package net.tilialacus.rushhour;

import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StateTest {

    @Test
    public void setBoard() {
        State state = new State();
        state.set(1,2 , Cars.GRAY.identifier());
        assertThat(state.toString(), is("      \n      \n A    \n      \n      \n      \n"));
    }

    @Test
    public void freeMove() {
        State state = new State();
        state.set(1,2 , Cars.GRAY.identifier());
        state.set(0,2 , Cars.BROWN.identifier());

        Set<State> options = state.options();

        assertThat(options.stream().map(State::toString).collect(Collectors.toSet()),
                hasItems(
                        "      \n      \nB A   \n      \n      \n      \n",
                        "      \n A    \nB     \n      \n      \n      \n",
                        "      \n      \nB     \n A    \n      \n      \n",
                        "      \nB     \n A    \n      \n      \n      \n",
                        "      \n      \n A    \nB     \n      \n      \n"
                        ));
    }
}
