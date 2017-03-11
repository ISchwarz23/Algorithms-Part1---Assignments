import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Tests for the {@link Solver}.
 *
 * @author ISchwarz
 */
public class SolverTest {

    private static final Board NON_SOLVABLE_BOARD = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}});
    private static final Board SOLVABLE_BOARD = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
    private static final Board SOLVED_BOARD = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});


    @Test
    public void shouldFindFastestSolution() {
        Solver solver = new Solver(SOLVABLE_BOARD);
        assertEquals(4, solver.moves());
    }

    @Test
    public void shouldGiveAllStepsFromStartToSolution() {
        Solver solver = new Solver(SOLVABLE_BOARD);

        int currentStep = 0;
        for (Board board : solver.solution()) {
            switch (currentStep) {
                case 0:
                    assertTrue(board.equals(SOLVABLE_BOARD));
                    break;
                case 1:
                    assertTrue(board.equals(new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}})));
                    break;
                case 2:
                    assertTrue(board.equals(new Board(new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}})));
                    break;
                case 3:
                    assertTrue(board.equals(new Board(new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}})));
                    break;
                case 4:
                    assertTrue(board.equals(SOLVED_BOARD));
                    break;
                default:
                    fail("Too many steps given");

            }
            currentStep++;
        }

        assertEquals(5, currentStep);
    }

    @Test
    public void shouldGiveNoMovesForSolvedBoard() {
        assertEquals(0, new Solver(SOLVED_BOARD).moves());
    }

    @Test
    public void shouldRecognizeIfBoardIsSolvable() {
        assertTrue(new Solver(SOLVABLE_BOARD).isSolvable());
        assertFalse(new Solver(NON_SOLVABLE_BOARD).isSolvable());
    }

}
