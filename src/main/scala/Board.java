import java.util.Arrays;

class Board {

    int[][] board;
    int empty = 0;
    int lizard = 1;
    int tree = 2;

    Board(int size) {
        board = new int[size][size];
    }

    Board(Board board){
        int N = board.board.length;;
        this.board = new int[N][N];
        for (int i = 0; i < N; i++)
            System.arraycopy(board.board[i], 0, this.board[i], 0, N);
    }

    boolean isSafe(int row, int col, boolean checkCurrentCell) {
        if (checkCurrentCell && board[row][col] != empty)
            return false;

        for (int c = col - 1; c >= 0; c--) {
            if (board[row][c] == lizard)
                return false;
            else if (board[row][c] == tree)
                break;
        }

        for (int c = col + 1; c < board.length; c++) {
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

        for (int r = row + 1; r < board.length; r++) {
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

        for (int r = row + 1, c = col + 1; r < board.length && c < board.length; r++, c++) {
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

        for (int r = row + 1, c = col - 1; r < board.length && c >= 0; r++, c--) {
            int currentValue = board[r][c];
            if (currentValue == lizard)
                return false;
            else if (currentValue == tree)
                break;
        }

        return true;
    }

    boolean isSafe(int row, int col){
        return isSafe(row, col, true);
    }

    void placeTreeIn(int row, int col) {
        board[row][col] = tree;
    }

    void placeLizardIn(int row, int col) {
        board[row][col] = lizard;
    }

    void removeLizardIn(int row, int col) {
        board[row][col] = empty;
    }

    @Override
    public String toString() {
        String boardView = "Board:";
        for (int[] row : board) {
            boardView = boardView + "\n" + Arrays.toString(row);
        }
        return boardView;
    }
}
