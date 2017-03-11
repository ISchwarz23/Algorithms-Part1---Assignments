import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;


/**
 * Tests for the {@link Board}.
 *
 * @author ISchwarz
 */
public class BoardTest {

    private static final int[][] EXAMPLE_BLOCKS = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
    private static final int[][] GOAL_BLOCKS = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};


    @Test
    public void shouldGiveCorrectDimension() {
        assertEquals(3, new Board(EXAMPLE_BLOCKS).dimension());
    }

    @Test
    public void shouldGiveCorrectHammingValue() {
        assertEquals(5, new Board(EXAMPLE_BLOCKS).hamming());
    }

    @Test
    public void shouldGiveCorrectManhattenValue() {
        assertEquals(10, new Board(EXAMPLE_BLOCKS).manhattan());
    }

    @Test
    public void shouldRecognizeIfGivenFieldIsGoalField() {
        assertFalse(new Board(EXAMPLE_BLOCKS).isGoal());
        assertTrue(new Board(GOAL_BLOCKS).isGoal());
    }

    @Test
    public void shouldRecognizeEqualBoards() {
        assertFalse(new Board(EXAMPLE_BLOCKS).equals(null));
        assertFalse(new Board(EXAMPLE_BLOCKS).equals(new Board(GOAL_BLOCKS)));
        assertTrue(new Board(EXAMPLE_BLOCKS).equals(new Board(EXAMPLE_BLOCKS)));
    }

    @Test
    public void shouldGiveAllNeighbors() {
        int numberOfNeighbors = 0;
        for (Board neighbor : new Board(EXAMPLE_BLOCKS).neighbors()) {
            assertNotNull(neighbor);
            numberOfNeighbors++;
        }
        assertEquals(4, numberOfNeighbors);


        numberOfNeighbors = 0;
        for (Board neighbor : new Board(GOAL_BLOCKS).neighbors()) {
            assertNotNull(neighbor);
            numberOfNeighbors++;
        }
        assertEquals(2, numberOfNeighbors);
    }

    @Test
    public void shouldGiveTwin() {
        Board board = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        assertEquals(new Board(new int[][]{{2, 1, 3}, {4, 5, 6}, {7, 8, 0}}), board.twin());
    }

    @Test
    public void shouldGiveAlternativeTwin1() {
        Board board = new Board(new int[][]{{0, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        assertEquals(new Board(new int[][]{{0, 3, 2}, {4, 5, 6}, {7, 8, 9}}), board.twin());
    }

    @Test
    public void shouldGiveAlternativeTwin2() {
        Board board = new Board(new int[][]{{1, 0, 3}, {4, 5, 6}, {7, 8, 9}});
        assertEquals(new Board(new int[][]{{1, 0, 3}, {5, 4, 6}, {7, 8, 9}}), board.twin());
    }

}
