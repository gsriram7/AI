class Board {

    int[][] board;
    int empty = 0;
    int tree = 1;
    int lizard = 2;

    Board(int size) {
        board = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i][j] = empty;
    }

    boolean isSafe(int row, int col) {
        if (board[row][col] != empty)
            return false;

        for (int c = col; c >= 0; c--) {
            if (board[row][c] == lizard)
                return false;
            else if (board[row][c] == tree)
                break;
        }

        for (int r = row; r >= 0; r--) {
            int currentValue = board[r][col];
            if (currentValue == lizard)
                return false;
            else if (currentValue == tree)
                break;
        }

        for (int r = row, c = col; r >= 0 && c >= 0; r--, c--) {
            int currentValue = board[r][c];
            if (currentValue == lizard)
                return false;
            else if (currentValue == tree)
                break;
        }

        for (int r = row, c = col; c >= 0 && r < board.length; r++, c--) {
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
