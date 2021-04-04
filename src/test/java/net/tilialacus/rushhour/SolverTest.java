package net.tilialacus.rushhour;

import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.tilialacus.rushhour.Piece.Direction.HORIZONTAL;
import static net.tilialacus.rushhour.Piece.Direction.VERTICAL;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SolverTest {
    @Test
    public void addToPaths() {
        Solver solver = new Solver(State.empty().add(Piece.GRAY, 0, 0, HORIZONTAL));
        assertThat(solver.getNrPaths(), is(1));
    }

    @Test
    public void findSolution() {
        Solver solver = new Solver(State.empty().add(Piece.RED, 2, 2, HORIZONTAL));

        List<State> solved = solver.solve();

        assertThat(solved.size(), is(1));
        assertThat(solved.get(0)
                        .getMoves()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(Piece.Move::toString)
                        .collect(Collectors.joining(",")),
                is("X→2"));
    }

    @Test
    public void findHardSolution() {
        Solver solver = new Solver(State.empty()
                .add(Piece.RED, 1, 2, HORIZONTAL)
                .add(Piece.LIGHT_GREEN, 2, 0, VERTICAL)
                .add(Piece.DARK_YELLOW, 3, 0, HORIZONTAL)
                .add(Piece.ORANGE, 3, 1, VERTICAL)
                .add(Piece.LIGHT_BLUE, 4, 1, HORIZONTAL)
                .add(Piece.PINK, 0, 2, VERTICAL)
                .add(Piece.LAVENDEL, 4, 2, VERTICAL)
                .add(Piece.PURPLE, 5, 2, VERTICAL)
                .add(Piece.GREEN, 1, 3, VERTICAL)
                .add(Piece.GRAY, 0, 4, VERTICAL)
                .add(Piece.BEIGE, 2, 4, HORIZONTAL)
                .add(Piece.YELLOW, 5, 4, VERTICAL)
                .add(Piece.BROWN, 1, 5, HORIZONTAL)
                .add(Piece.OLIVE, 3, 5, HORIZONTAL)
        );

        List<State> solved = solver.solve();

        assertThat(solved.stream()
                        .map(State::getMoves)
                        .map(it -> it.stream()
                                .filter(Objects::nonNull)
                                .map(Piece.Move::toString)
                                .collect(Collectors.joining(",")))
                        .collect(Collectors.toSet()),
                hasItems(
                        "B↓1,D↑2,X←1,A↓2,O←1,C←2,P↑1,E↑2,G↑1,I↑2,H→2,A↓1,B↓1,X→2,F↑3,X←2,A↑1,B↑1,H←3,B↓1,I↓1,E↓1,O→1,J←1,K←1,P↓2,C→1,A↑2,X→3,A↓1,O←1,E↑1,X→1"
                )
        );
    }
}
