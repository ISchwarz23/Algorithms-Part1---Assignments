import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Execute selected tests from the given files.
 *
 * @author ISchwarz
 */
public class TestsFromFile {

    @Test
    public void puzzle00() {
        executeGenericTest(0);
    }

    @Test
    public void puzzle01() {
        executeGenericTest(1);
    }

    @Test
    public void puzzle02() {
        executeGenericTest(2);
    }

    @Test
    public void puzzle04() {
        executeGenericTest(4);
    }

    @Test
    public void puzzle05() {
        executeGenericTest(5);
    }

    @Test
    public void puzzle06() {
        executeGenericTest(6);
    }

    @Test
    public void puzzle07() {
        executeGenericTest(7);
    }

    @Test
    public void puzzle08() {
        executeGenericTest(8);
    }

    @Test
    public void puzzle09() {
        executeGenericTest(9);
    }

    @Test
    public void puzzle10() {
        executeGenericTest(10);
    }

    @Test
    public void puzzle11() {
        executeGenericTest(11);
    }

    @Test
    public void puzzle12() {
        executeGenericTest(12);
    }

    @Test
    public void puzzle13() {
        executeGenericTest(13);
    }

    private void executeGenericTest(int puzzle) {
        assertEquals(puzzle, new Solver(getBlockFromFile("puzzle" + ((puzzle < 10) ? "0" : "") + puzzle + ".txt")).moves());
    }

    private Board getBlockFromFile(String fileName) {
        In in = new In("test-input/" + fileName);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        return new Board(blocks);
    }

}
