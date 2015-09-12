package de.ingo.percolation;

/**
 * Created by Ingo on 12.09.2015.
 */
public class Percolation {

    private final boolean[][] field;

    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        field = new boolean[N][N];
    }

    public void open(int i, int j) {    // open site (row i, column j) if it is not open already
        field[i][j] = true;
    }

    public boolean isOpen(int i, int j) {   // is site (row i, column j) open?
        return field[i][j];
    }

    public boolean isFull(int i, int j) {   // is site (row i, column j) full?
        return !isOpen(i, j);
    }

    public boolean percolates() {   // does the system percolate?
        // TODO
        return false;
    }

}
