import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SearchTest {

    @Test
    public void shouldReturnFalseIfThereIsNoSolution() throws Exception {
        Search search = new Search(3, 3);

        assertThat(search.dfs(), is(false));
    }

    @Test
    public void shouldCheckIfValidSolution() throws Exception {
        Search search = new Search(6, 7);

        for (int i = 0; i < 6; i++) {
            search.placeTree(4, i);
        }
        for (int i = 0; i < 6; i++) {
            search.placeTree(i, 4);
        }

        long start = System.currentTimeMillis();
        search.startDFS();
        System.out.println("Took: " + (System.currentTimeMillis() - start) + "ms");

        assertThat(testValidity(search.game), is(true));
        assertThat(testValidityAgain(search.game), is(true));
    }

    @Test
    public void shouldTestOptimizedDfs() throws Exception {
        for (int i = 20; i < 45; i++) {
            Search dfs = new Search(i, i);

            long start = System.currentTimeMillis();
            dfs.startDFS();
            long timeTaken = System.currentTimeMillis() - start;
            System.out.printf("%d Took: %dms\n", i, timeTaken);

            assertThat(testValidity(dfs.game), is(true));
            assertThat(testValidityAgain(dfs.game), is(true));
        }
    }

    private boolean testValidityAgain(Board game) {
        int[][] board = game.board;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == game.lizard) {
                    if (!game.isSafe(row, col, false))
                        return false;
                }
            }
        }
        return true;
    }

    boolean testValidity(Board game) {
        int[][] board = game.board;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == game.lizard) {

                    for (int i = row - 1; i >= 0; i--) {
                        if (board[i][col] == game.tree)
                            break;
                        else if (board[i][col] == game.lizard)
                            return false;
                    }

                    for (int i = row + 1; i < board.length; i++) {
                        if (board[i][col] == game.tree)
                            break;
                        else if (board[i][col] == game.lizard)
                            return false;
                    }

                    for (int i = col - 1; i >= 0; i--) {
                        if (board[row][i] == game.tree)
                            break;
                        else if (board[row][i] == game.lizard)
                            return false;
                    }

                    for (int i = col + 1; i < board.length; i++) {
                        if (board[row][i] == game.tree)
                            break;
                        else if (board[row][i] == game.lizard)
                            return false;
                    }

                    for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
                        if (board[i][j] == game.tree)
                            break;
                        else if (board[i][j] == game.lizard)
                            return false;
                    }

                    for (int i = row + 1, j = col + 1; i < board.length && j < board.length; i++, j++) {
                        if (board[i][j] == game.tree)
                            break;
                        else if (board[i][j] == game.lizard)
                            return false;
                    }

                    for (int i = row + 1, j = col - 1; i < board.length && j >= 0; i++, j--) {
                        if (board[i][j] == game.tree)
                            break;
                        else if (board[i][j] == game.lizard)
                            return false;
                    }

                    for (int i = row - 1, j = col + 1; j < board.length && i >= 0; i--, j++) {
                        if (board[i][j] == game.tree)
                            break;
                        else if (board[i][j] == game.lizard)
                            return false;
                    }
                }
            }
        }
        return true;
    }
}