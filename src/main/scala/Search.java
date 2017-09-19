import java.util.Stack;

class Search {

    final int lizards;
    Board game;
    Stack<Cell> trace = new Stack<>();
    Stack<Cell> result = new Stack<>();
    boolean isOptimized = false;

    Search(int size, int lizards) {
        game = new Board(size);
        this.lizards = lizards;
    }

    Search(Board board, int lizards) {
        game = board;
        this.lizards = lizards;
    }

    void placeTree(int row, int col) {
        game.placeTreeIn(row, col);
    }

    boolean dfs() {
        boolean complete = false;
        int remainingLizards = this.lizards;
        int iter = 0;

        while (!trace.empty()) {
            iter++;
            if (remainingLizards == 0)
                return true;

            Cell cell = trace.pop();
            int row = cell.row;
            int col = cell.col;

            if (game.isSafe(row, col)) {
                game.placeLizardIn(row, col);
                remainingLizards--;
                if (isOptimized) pushNextStates(cell); else pushNextStates();
                result.push(cell);
            } else if (!result.isEmpty() && row <= result.peek().row) {
                while (!result.isEmpty() && row <= result.peek().row) {
                    remainingLizards++;
                    Cell popped = result.pop();
                    game.removeLizardIn(popped.row, popped.col);
                }
                trace.push(cell);
            }

        }

        return remainingLizards == 0;
    }

    void pushNextStates() {
        for (int row = game.board.length - 1; row >= 0; row--) {
            for (int col = game.board.length - 1; col >= 0; col--) {
                if (game.isSafe(row, col))
                    trace.push(new Cell(row, col));
            }
        }
    }

    void pushNextStates(Cell cell) {
        int nextRow = cell.row + 1;
        if (nextRow < game.board.length) {
            for (int col = game.board.length - 1; col >= 0; col--) {
                if (game.isSafe(nextRow, col))
                    trace.push(new Cell(nextRow, col));
            }
        }
    }

    boolean startDFS() {
        for (int row = game.board.length - 1; row >= 0; row--) {
            for (int col = game.board.length - 1; col >= 0; col--) {
                trace.push(new Cell(row, col));
            }
        }
        return dfs();
    }

}
