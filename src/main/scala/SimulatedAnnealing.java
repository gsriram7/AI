import java.util.ArrayList;

public class SimulatedAnnealing {

    int currentScore;
    Board currentState;
    ArrayList<Cell> lizardsInBoard;
    ArrayList<Cell> newLizardsInBoard;
    int totalLizards;

    SimulatedAnnealing(Board board, int lizards) {
        currentState = board;
        lizardsInBoard = new ArrayList<>();
        newLizardsInBoard = new ArrayList<>();
        totalLizards = lizards;
        placeRandomLizards(lizards);
        currentScore = calculateScore(currentState, lizardsInBoard);
    }

    Cell getRandomFreeCell(Board b) {
        int N = b.board.length;
        Cell newCell = null;

        while (newCell == null) {
            int row = (int) (Math.random() * N);
            int col = (int) (Math.random() * N);
            if (b.board[row][col] == b.empty)
                newCell = new Cell(row, col);
        }

        return newCell;
    }

    void placeRandomLizards(int lizards) {

        while (lizards > 0) {
            Cell freeCell = getRandomFreeCell(currentState);
            lizardsInBoard.add(freeCell);
            currentState.placeLizardIn(freeCell.row, freeCell.col);
            lizards--;
        }
    }

    boolean probabilityFunction(int delta, double temperature) {
        if (delta < 0)
            return true;

        double climb = Math.exp(-delta / temperature);
        double randomWalk = Math.random();

        return randomWalk <= climb;
    }

    int calculateScore(Board board, ArrayList<Cell> lizardsPosition) {
        int conflicts = 0;
        for (Cell cell : lizardsPosition) {
            if (!board.isSafe(cell.row, cell.col, false))
                conflicts++;
        }
        return conflicts;
    }

    boolean simulate(double temperature, double coolingFactor) {
        if (currentScore == 0) return true;
        long startTime = System.nanoTime();
        while (temperature > 0) {
            Board newState = newChildren(currentState);
            int newScore = calculateScore(newState, newLizardsInBoard);

            if (newScore == 0) {
                acceptNewState(newState, newScore);
                return true;
            }

            int newConflicts = newScore - currentScore;

            if (probabilityFunction(newConflicts, temperature)) {
                acceptNewState(newState, newScore);
            }

            if (((double) (System.nanoTime() - startTime)/ 1000000000.0) >= 280) return currentScore == 0;
            temperature = temperature * coolingFactor;
        }
        return currentScore == 0;
    }

    void acceptNewState(Board newState, int newScore) {
        currentScore = newScore;
        currentState = newState;
        lizardsInBoard.clear();
        lizardsInBoard.addAll(newLizardsInBoard);
    }

    Board newChildren(Board board) {
        newLizardsInBoard.clear();
        Board copy = new Board(board);
        newLizardsInBoard.addAll(lizardsInBoard);

        int index = (int) (Math.random() * totalLizards);

        Cell randomLiz = newLizardsInBoard.get(index);

        copy.removeLizardIn(randomLiz.row, randomLiz.col);
        newLizardsInBoard.remove(index);

        Cell randomFreeCell = getRandomFreeCell(copy);
        newLizardsInBoard.add(randomFreeCell);
        copy.placeLizardIn(randomFreeCell.row, randomFreeCell.col);

        return copy;
    }

    public static void main(String[] args) {
        Board board = new Board(5);
        SimulatedAnnealing sa = new SimulatedAnnealing(board, 5);

        boolean isDone = sa.simulate(1000, 0.98);
        System.out.println(sa.currentState);
    }


}
