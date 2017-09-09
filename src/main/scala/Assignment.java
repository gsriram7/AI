import java.util.Scanner;
import java.util.Stack;

public class Assignment {

    final int lizards;
    Board game;
    Stack<Cell> trace = new Stack<>();
    Stack<Cell> result = new Stack<>();

    Assignment(int size, int lizards) {
        game = new Board(size);
        this.lizards = lizards;
    }

    void placeTree(int row, int col) {
        game.placeTreeIn(row, col);
    }

    boolean dfs() {
        boolean complete = false;
        int remainingLizards = this.lizards;

        while (!trace.empty()) {
            if (remainingLizards == 0)
                return true;

            Cell cell = trace.pop();
            int row = cell.row;
            int col = cell.col;

            if (game.isSafe(row, col)) {
                remainingLizards--;
                pushNextStatesOf(cell);
                result.push(cell);
                game.placeLizardIn(row, col);
            }

            else {
                if (trace.peek().row <= result.peek().row) {
                    remainingLizards++;
                    Cell popped = result.pop();
                    game.removeLizardIn(popped.row, popped.col);
                }
            }

        }

        return remainingLizards == 0;
    }

    void pushNextStatesOf(Cell cell) {
        int nextRow = cell.row + 1;
        if (nextRow < game.board.length) {
            for (int col = game.board.length - 1; col >= 0; col--) {
                if (game.board[nextRow][col] != game.tree)
                    trace.push(new Cell(nextRow, col));
            }
        }
    }

    void startDFS() {
        for (int col = game.board.length - 1; col >= 0 ; col--) {
            trace.push(new Cell(0, col));
        }
        System.out.println(dfs());
        System.out.println(game.toString());
    }

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);

        String algorithm = kb.nextLine();
        int dimension = kb.nextInt();
        int liz = kb.nextInt();
        kb.nextLine();

        Assignment assignment = new Assignment(dimension, liz);

        for (int i = 0; i < dimension; i++) {
            String row = kb.nextLine();
            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == '2')
                    assignment.placeTree(i, j);
            }
        }

        kb.close();

        assignment.startDFS();

    }


}
