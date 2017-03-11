import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Represents a puzzle Board.
 *
 * @author ISchwarz
 */
public class Board {

    private Board[] neighbors;
    private int[][] blocks;

    private int iTwinIndex = 0;
    private int jTwinIndex = 0;
    private int iTwinExchangeIndex = 0;
    private int jTwinExchangeIndex = 0;


    public Board(int[][] blocks) {          // construct a board from an N-by-N array of blocks
        this.blocks = blocks;               // (where blocks[i][j] = block in row i, column j)
    }

    private int[][] getBlocksCopy() {
        int[][] copy = new int[blocks.length][];
        for (int r = 0; r < blocks.length; r++) {
            copy[r] = blocks[r].clone();
        }
        return copy;
    }

    private void exchangeBlocks(int[][] blocks, int iFirstBlock, int jFirstBlock, int iSecondsBlock, int jSecondBlock) {
        int firstValue = blocks[iFirstBlock][jFirstBlock];
        blocks[iFirstBlock][jFirstBlock] = blocks[iSecondsBlock][jSecondBlock];
        blocks[iSecondsBlock][jSecondBlock] = firstValue;
    }

    public int dimension() {                // board dimension N
        return blocks.length;
    }

    public int hamming() {                  // number of blocks out of place
        int value = -1;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != (i * blocks.length + j + 1)) value++;
            }
        }
        return value;
    }

    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int value = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                int expectedValue = (i * blocks.length + j + 1);
                if (blocks[i][j] != expectedValue && blocks[i][j] != 0) {
                    int actualValue = blocks[i][j];
                    actualValue--;
                    int goalI = actualValue / dimension();
                    int goalJ = actualValue % dimension();
                    value += Math.abs(goalI - i) + Math.abs(goalJ - j);
                }
            }
        }
        return value;
    }

    public boolean isGoal() {               // is this board the goal board?
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != (i * blocks.length + j + 1) && (i != dimension() - 1 && j != dimension() - 1))
                    return false;
            }
        }
        return true;
    }

    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
        jTwinExchangeIndex++;
        if (jTwinExchangeIndex >= dimension()) {
            iTwinExchangeIndex++;
            jTwinExchangeIndex = 0;
            if (iTwinExchangeIndex >= dimension()) {
                jTwinIndex++;
                if (jTwinIndex >= dimension()) {
                    iTwinIndex++;
                    jTwinIndex = 0;
                    if (iTwinIndex >= dimension()) {
                        iTwinIndex = 0;
                    }
                }
                iTwinExchangeIndex = iTwinIndex;
                jTwinExchangeIndex = jTwinIndex+1;

                if (jTwinExchangeIndex >= dimension()) {
                    iTwinExchangeIndex++;
                    jTwinExchangeIndex = 0;
                }

                if(iTwinIndex == dimension()-1 && jTwinIndex == dimension()-1) {
                    iTwinIndex = 0;
                    jTwinIndex = 0;
                    iTwinExchangeIndex = 0;
                    jTwinExchangeIndex = 1;
                }
            }
        }

        int[][] twinBlocks = getBlocksCopy();
        exchangeBlocks(twinBlocks, iTwinIndex, jTwinIndex, iTwinExchangeIndex, jTwinExchangeIndex);

        return new Board(twinBlocks);
    }

    @Override
    public boolean equals(Object y) {       // does this board equal y?
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.blocks.length != that.blocks.length) return false;
        for (int i = 0; i < blocks.length; i++) {
            if (this.blocks[i].length != that.blocks[i].length) return false;
            for (int j = 0; j < blocks[i].length; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }

        return true;
    }

    public Iterator<Board> neighbors() {    // all neighboring boards
        if (neighbors == null) {
            findNeighbors();
        }
        return new NeighborIterator();
    }

    public String toString() {
        String boardString = blocks.length + "\n";

        for (int[] row : blocks) {
            for (int block : row) {
                boardString += " " + block;
            }
            boardString += "\n";
        }

        return boardString;
    }

    private void findNeighbors() {
        List<Board> neighbors = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (blocks[i][j] != 0) {
            j++;
            if (j >= dimension()) {
                i++;
                j = 0;
            }
        }

        if (i > 0) {
            int[][] neighborBlocks = getBlocksCopy();
            exchangeBlocks(neighborBlocks, i - 1, j, i, j);
            neighbors.add(new Board(neighborBlocks));
        }
        if (i < dimension() - 1) {
            int[][] neighborBlocks = getBlocksCopy();
            exchangeBlocks(neighborBlocks, i, j, i + 1, j);
            neighbors.add(new Board(neighborBlocks));
        }
        if (j > 0) {
            int[][] neighborBlocks = getBlocksCopy();
            exchangeBlocks(neighborBlocks, i, j - 1, i, j);
            neighbors.add(new Board(neighborBlocks));
        }
        if (j < dimension() - 1) {
            int[][] neighborBlocks = getBlocksCopy();
            exchangeBlocks(neighborBlocks, i, j, i, j + 1);
            neighbors.add(new Board(neighborBlocks));
        }

        this.neighbors = neighbors.toArray(new Board[neighbors.size()]);
    }


    private class NeighborIterator implements Iterator<Board> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < neighbors.length;
        }

        @Override
        public Board next() {
            return neighbors[index++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal of neighbors not supported.");
        }
    }

}
