import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Performace test for the {@link Solver}.
 *
 * @author ISchwarz
 */
public class PerformanceTest {

    @Test
    public void puzzle20() {
        executeGenericTest(20);
    }

    @Test
    public void puzzle21() {
        executeGenericTest(21);
    }

    @Test
    public void puzzle22() {
        executeGenericTest(22);
    }

    @Test
    public void puzzle23() {
        executeGenericTest(23);
    }

    @Test
    public void puzzle24() {
        executeGenericTest(24);
    }

    @Test
    public void puzzle25() {
        executeGenericTest(25);
    }

    @Test
    public void puzzle26() {
        executeGenericTest(26);
    }

    @Test
    public void puzzle27() {
        executeGenericTest(27);
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
