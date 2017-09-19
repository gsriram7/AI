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

    Assignment(Board board, int lizards) {
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
                pushNextStatesOf();
                result.push(cell);
            } else if (row <= result.peek().row) {
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

    void pushNextStatesOf() {
        for (int row = game.board.length - 1; row >= 0; row--) {
            for (int col = game.board.length - 1; col >= 0; col--) {
                if (game.isSafe(row, col))
                    trace.push(new Cell(row, col));
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
