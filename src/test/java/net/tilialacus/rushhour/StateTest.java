package net.tilialacus.rushhour;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StateTest {

    @Test
    public void setBoard() {
        State state = new State();
        state.set(1,2 , 'A');
        assertThat(state.toString(), is("      \n      \n A    \n      \n      \n      \n"));
    }
}
