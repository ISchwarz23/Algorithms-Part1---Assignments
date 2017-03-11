import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Represents a puzzle Board.
 *
 * @author ISchwarz
 */
public class Board {

    private Board[] neighbors;
    private int[][] tiles;


    public Board(int[][] blocks) {          // construct a board from an N-by-N array of tiles
        this.tiles = copy(blocks);          // (where tiles[i][j] = block in row i, column j)
    }

    private int[][] copy(int[][] arrayToCopy) {
        int[][] copy = new int[arrayToCopy.length][];
        for (int r = 0; r < arrayToCopy.length; r++) {
            copy[r] = arrayToCopy[r].clone();
        }
        return copy;
    }

    private void exchangeBlocks(int[][] blocks, int iFirstBlock, int jFirstBlock, int iSecondsBlock, int jSecondBlock) {
        int firstValue = blocks[iFirstBlock][jFirstBlock];
        blocks[iFirstBlock][jFirstBlock] = blocks[iSecondsBlock][jSecondBlock];
        blocks[iSecondsBlock][jSecondBlock] = firstValue;
    }

    public int dimension() {                // board dimension N
        return tiles.length;
    }

    public int hamming() {                  // number of tiles out of place
        int value = -1;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != (i * tiles.length + j + 1)) value++;
            }
        }
        return value;
    }

    public int manhattan() {                // sum of Manhattan distances between tiles and goal
        int value = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int expectedValue = (i * tiles.length + j + 1);
                if (tiles[i][j] != expectedValue && tiles[i][j] != 0) {
                    int actualValue = tiles[i][j];
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
        return hamming() == 0;
    }

    public Board twin() {                   // a board that is obtained by exchanging any pair of tiles
        int[][] twinBlocks = copy(tiles);

        int i = 0;
        int j = 0;
        while (twinBlocks[i][j] == 0 || twinBlocks[i][j + 1] == 0) {
            j++;
            if (j >= twinBlocks.length - 1) {
                i++;
                j = 0;
            }
        }

        exchangeBlocks(twinBlocks, i, j, i, j + 1);
        return new Board(twinBlocks);
    }

    @Override
    public boolean equals(Object y) {       // does this board equal y?
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.tiles.length != that.tiles.length) return false;
        for (int i = 0; i < tiles.length; i++) {
            if (this.tiles[i].length != that.tiles[i].length) return false;
            for (int j = 0; j < tiles[i].length; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {    // all neighboring boards
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                if (neighbors == null) {
                    findNeighbors();
                }
                return new NeighborIterator();
            }
        };
    }

    public String toString() {
        StringBuilder boardStringBuilder = new StringBuilder(tiles.length + "\n");

        for (int[] row : tiles) {
            for (int block : row) {
                boardStringBuilder.append(" ");
                boardStringBuilder.append(block);
            }
            boardStringBuilder.append("\n");
        }

        return boardStringBuilder.toString();
    }

    private void findNeighbors() {
        List<Board> foundNeighbors = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (tiles[i][j] != 0) {
            j++;
            if (j >= dimension()) {
                i++;
                j = 0;
            }
        }

        if (i > 0) {
            int[][] neighborTiles = copy(tiles);
            exchangeBlocks(neighborTiles, i - 1, j, i, j);
            foundNeighbors.add(new Board(neighborTiles));
        }
        if (i < dimension() - 1) {
            int[][] neighborTiles = copy(tiles);
            exchangeBlocks(neighborTiles, i, j, i + 1, j);
            foundNeighbors.add(new Board(neighborTiles));
        }
        if (j > 0) {
            int[][] neighborTiles = copy(tiles);
            exchangeBlocks(neighborTiles, i, j - 1, i, j);
            foundNeighbors.add(new Board(neighborTiles));
        }
        if (j < dimension() - 1) {
            int[][] neighborTiles = copy(tiles);
            exchangeBlocks(neighborTiles, i, j, i, j + 1);
            foundNeighbors.add(new Board(neighborTiles));
        }

        neighbors = foundNeighbors.toArray(new Board[foundNeighbors.size()]);
    }


    private class NeighborIterator implements Iterator<Board> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < neighbors.length;
        }

        @Override
        public Board next() {
            if (hasNext()) {
                return neighbors[index++];
            } else {
                throw new NoSuchElementException("There is no next neighbor.");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removal of neighbors not supported.");
        }
    }

}
