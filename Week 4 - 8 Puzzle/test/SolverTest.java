import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Tests for the {@link Solver}.
 *
 * @author ISchwarz
 */
public class SolverTest {

    private static final Board EXAMPLE_BOARD = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});


    @Test
    public void shouldFindFastestSolution() {
        Solver solver = new Solver(EXAMPLE_BOARD);
        assertEquals(4, solver.moves());
    }

    @Test
    public void shouldGiveAllStepsFromStartToSolution() {
        Solver solver = new Solver(EXAMPLE_BOARD);

        int currentStep = 0;
        for (Board board : solver.solution()) {
            switch (currentStep) {
                case 0: assertTrue(board.equals(EXAMPLE_BOARD));
                    break;
                case 1: assertTrue(board.equals(new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}})));
                    break;
                case 2: assertTrue(board.equals(new Board(new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}})));
                    break;
                case 3: assertTrue(board.equals(new Board(new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}})));
                    break;
                case 4: assertTrue(board.equals(new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}})));
                    break;
                default:
                    fail("Too many steps given");

            }
            currentStep++;
        }

        assertEquals(5, currentStep);
    }

}
