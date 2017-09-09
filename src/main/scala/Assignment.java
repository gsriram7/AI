class Board {

    int[][] board;
    int empty = 0;
    int lizard = 1;
    int tree = 2;

    Board(int size) {
        board = new int[size][size];
    }

    boolean isSafe(int row, int col) {
        if (board[row][col] != empty)
            return false;

        for (int c = col - 1; c >= 0; c--) {
            if (board[row][c] == lizard)
                return false;
            else if (board[row][c] == tree)
                break;
        }

        for (int r = row - 1; r >= 0; r--) {
            int currentValue = board[r][col];
            if (currentValue == lizard)
                return false;
            else if (currentValue == tree)
                break;
        }

        for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--) {
            int currentValue = board[r][c];
            if (currentValue == lizard)
                return false;
            else if (currentValue == tree)
                break;
        }

        for (int r = row - 1, c = col + 1; r >= 0 && c < board.length; r--, c++) {
            int currentValue = board[r][c];
            if (currentValue == lizard)
                return false;
            else if (currentValue == tree)
                break;
        }

        return true;
    }

}

public class Assignment {

    public static void main(String[] args) {
        Board b = new Board(10);

        System.out.println();
    }

}
