import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board(4);
    }

    @Test
    public void shouldReturnFalseWhenTryingToPlaceOnANonEmptyCell() throws Exception {
        board.board[0][0] = board.lizard;

        assertThat(board.isSafe(0, 0), is(false));
    }

    @Test
    public void testRowWithOneLizard() throws Exception {
        board.board[0][0] = board.lizard;

        assertThat(board.isSafe(0, 1), is(false));
        assertThat(board.isSafe(0, 2), is(false));
        assertThat(board.isSafe(0, 3), is(false));
    }

    @Test
    public void testColumnWithOneLizard() throws Exception {
        board.board[0][0] = board.lizard;

        assertThat(board.isSafe(1, 0), is(false));
        assertThat(board.isSafe(2, 0), is(false));
        assertThat(board.isSafe(3, 0), is(false));
    }

    @Test
    public void testUpperLeftDiagonalWithOneLizard() throws Exception {
        board.board[0][0] = board.lizard;

        assertThat(board.isSafe(1, 1), is(false));
        assertThat(board.isSafe(2, 2), is(false));
        assertThat(board.isSafe(3, 3), is(false));
    }

    @Test
    public void testUpperRightDiagonalWithOneLizard() throws Exception {
        board.board[0][3] = board.lizard;

        assertThat(board.isSafe(1, 2), is(false));
        assertThat(board.isSafe(2, 1), is(false));
        assertThat(board.isSafe(3, 0), is(false));
    }

}