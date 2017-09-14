import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BoardTest {

    private Board game;

    @Before
    public void setUp() throws Exception {
        game = new Board(4);
    }

    @Test
    public void shouldPassIfThereAreNoLizards() throws Exception {
        assertThat(game.isSafe(0, 0), is(true));
    }

    @Test
    public void shouldFailIfTriedToPlaceOnANonEmptyCell() throws Exception {
        game.board[0][0] = game.lizard;

        assertThat(game.isSafe(0, 0), is(false));
    }

    @Test
    public void shouldReturnFalseWhenRowWithOneLizard() throws Exception {
        game.board[0][0] = game.lizard;

        assertThat(game.isSafe(0, 1), is(false));
        assertThat(game.isSafe(0, 2), is(false));
        assertThat(game.isSafe(0, 3), is(false));
    }

    @Test
    public void shouldFailIfColumnHasLizard() throws Exception {
        game.board[0][0] = game.lizard;

        assertThat(game.isSafe(1, 0), is(false));
        assertThat(game.isSafe(2, 0), is(false));
        assertThat(game.isSafe(3, 0), is(false));
    }

    @Test
    public void shouldFailIfLeftDiagonalHasLizard() throws Exception {
        game.board[0][0] = game.lizard;

        assertThat(game.isSafe(1, 1), is(false));
        assertThat(game.isSafe(2, 2), is(false));
        assertThat(game.isSafe(3, 3), is(false));
    }

    @Test
    public void shouldFailIfRightDiagonalHasLizard() throws Exception {
        game.board[0][3] = game.lizard;

        assertThat(game.isSafe(1, 2), is(false));
        assertThat(game.isSafe(2, 1), is(false));
        assertThat(game.isSafe(3, 0), is(false));
    }

    @Test
    public void shouldFailIfRightRowHasLizard() throws Exception {
        game.board[0][0] = game.lizard;
        game.board[1][3] = game.lizard;

        assertThat(game.isSafe(1, 2), is(false));
    }

    @Test
    public void shouldFailIfBelowColumnHasLizard() throws Exception {
        game.board[2][2] = game.lizard;

        assertThat(game.isSafe(1, 2), is(false));
    }

    @Test
    public void shouldFailWhenLizardOnPath() throws Exception {
        game.board[0][1] = game.lizard;
        game.board[1][3] = game.lizard;

        assertThat(game.isSafe(2,0), is(true));
    }

    @Test
    public void shouldPassIfATreeInColumnBeforeLizard() throws Exception {
        game.board[0][2] = game.lizard;
        game.board[1][2] = game.tree;

        assertThat(game.isSafe(2,2), is(true));
    }

    @Test
    public void shouldPassIfATreeInRowBeforeLizard() throws Exception {
        game.board[1][0] = game.lizard;
        game.board[1][1] = game.tree;

        assertThat(game.isSafe(1,3), is(true));
    }

    @Test
    public void shouldPassIfATreeInLeftDiagonalBeforeLizard() throws Exception {
        game.board[0][0] = game.lizard;
        game.board[1][1] = game.tree;

        assertThat(game.isSafe(2,2), is(true));
    }

    @Test
    public void shouldPassIfATreeInRightDiagonalBeforeLizard() throws Exception {
        game.board[0][3] = game.lizard;
        game.board[1][2] = game.tree;

        assertThat(game.isSafe(2,1), is(true));
    }

    @Test
    public void shouldFailIfALizardInRightDiagonalBelow() throws Exception {
        game.board[3][2] = game.lizard;

        assertThat(game.isSafe(2,1), is(false));
    }

    @Test
    public void shouldFailIfALizardInLeftDiagonalBelow() throws Exception {
        game.board[3][0] = game.lizard;

        assertThat(game.isSafe(2,1), is(false));
    }

    @Test
    public void shouldPlaceTree() throws Exception {
        game.placeTreeIn(1, 1);

        assertThat(game.board[1][1], is(game.tree));
    }

    @Test
    public void shouldPlaceLizard() throws Exception {
        game.placeLizardIn(1, 1);

        assertThat(game.board[1][1], is(game.lizard));
    }

    @Test
    public void shouldRemoveLizard() throws Exception {
        game.placeLizardIn(1, 1);
        assertThat(game.board[1][1], is(game.lizard));

        game.removeLizardIn(1, 1);
        assertThat(game.board[1][1], is(game.empty));
    }
}