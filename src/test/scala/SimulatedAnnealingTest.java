import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimulatedAnnealingTest {

    int size;
    Board board;

    @Before
    public void setUp() throws Exception {
        size = 6;
        board = new Board(size);
    }

    @Test
    public void initializeWithRandomPositionedLizards() throws Exception {
        SimulatedAnnealing sa = new SimulatedAnnealing(board, 6);

        ArrayList<Cell> lizardsInBoard = sa.lizardsInBoard;
        assertThat(lizardsInBoard.size(), is(6));
        for (Cell cell : lizardsInBoard) {
            assertThat(sa.currentState.board[cell.row][cell.col], is(sa.currentState.lizard));
        }
    }

    @Test
    public void shouldComputerScoreAfterInit() throws Exception {

        SimulatedAnnealing sa = new SimulatedAnnealing(board, size);

        assertThat(sa.currentScore, is(sa.calculateScore(sa.currentState, sa.lizardsInBoard)));
    }

    @Test
    public void shouldPlaceLizardsWhenThereAreTree() throws Exception {

        for (int i = 0; i < size; i++) {
            board.placeTreeIn(i, 3);
        }

        SimulatedAnnealing sa = new SimulatedAnnealing(board, size);

        ArrayList<Cell> lizardsInBoard = sa.lizardsInBoard;
        for (Cell cell : lizardsInBoard) {
            assertThat(cell.col!=3, is(true));
        }
    }

    @Test
    public void shouldReturnAcceptWhenDeltaIsNegative() throws Exception {
        SimulatedAnnealing sa = new SimulatedAnnealing(board, 6);

        assertThat(sa.probabilityFunction(-1, 10), is(true));
    }

    @Test
    public void shouldReturnFalseWhenDeltaIsVeryBadAndTempIsCool() throws Exception {
        SimulatedAnnealing sa = new SimulatedAnnealing(board, 6);

        assertThat(sa.probabilityFunction(4, 0.5), is(false));
    }

    @Test
    public void shouldReturnNumberOfConflictsInBoard() throws Exception {
        SimulatedAnnealing sa = new SimulatedAnnealing(board, 6);

        Board b = new Board(6);
        b.placeLizardIn(0, 0);
        b.placeLizardIn(1, 1);
        sa.newLizardsInBoard.add(new Cell(0, 0));
        sa.newLizardsInBoard.add(new Cell(1, 1));

        assertThat(sa.calculateScore(b, sa.newLizardsInBoard), is(2));
    }

    @Test
    public void shouldReturnRandomFreeCell() throws Exception {
        SimulatedAnnealing sa = new SimulatedAnnealing(board, 6);

        Cell cell = sa.getRandomFreeCell(sa.currentState);

        assertThat(sa.currentState.board[cell.row][cell.col], is(sa.currentState.empty));
    }

    @Test
    public void shouldReturnNewState() throws Exception {
        SimulatedAnnealing sa = new SimulatedAnnealing(board, 6);

        Board newState = sa.newChildren(sa.currentState);

        assertThat(diff(sa.lizardsInBoard, sa.newLizardsInBoard).size(), is(1));
        assertThat(diff(sa.newLizardsInBoard, sa.lizardsInBoard).size(), is(1));
        assertThat(sa.lizardsInBoard.containsAll(sa.newLizardsInBoard), is(false));
    }

    @Test
    public void shouldMoveLizardFromOldStateToNew() throws Exception {
        for (int i = 0; i < board.board.length; i++) {
            board.placeTreeIn(i, 4);
        }
        SimulatedAnnealing sa = new SimulatedAnnealing(board, 6);

        Board newState = sa.newChildren(sa.currentState);

        Cell removed = diff(sa.lizardsInBoard, sa.newLizardsInBoard).get(0);
        Cell added = diff(sa.newLizardsInBoard, sa.lizardsInBoard).get(0);

        assertThat(sa.currentState.board[added.row][added.col], is(sa.currentState.empty));
        assertThat(sa.currentState.board[removed.row][removed.col], is(sa.currentState.lizard));
        assertThat(newState.board[removed.row][removed.col], is(newState.empty));
        assertThat(newState.board[added.row][added.col], is(newState.lizard));
    }

    @Test
    public void shouldReturnSafeStatesForLizardsWithNoTrees() throws Exception {
        for (int i = 0; i < 6; i++) {
            board.placeTreeIn(i, 4);
        }

        for (int i = 0; i < 6; i++) {
            board.placeTreeIn(4, i);
        }

        SimulatedAnnealing sa = new SimulatedAnnealing(board, 7);

        long start = System.currentTimeMillis();
        boolean simulate = sa.simulate(1000, 0.98);
        long end = System.currentTimeMillis() - start;

        assertThat(simulate, is(true));
        assertThat(new SearchTest().testValidity(sa.currentState), is(true));
        System.out.printf("Took %d ms", end);
    }

    ArrayList<Cell> diff(List<Cell> a, List<Cell> b) {
        ArrayList<Cell> diffs = new ArrayList<>();
        for (Cell cell : a) {
            if (!b.contains(cell))
                diffs.add(cell);
        }
        return diffs;
    }
}