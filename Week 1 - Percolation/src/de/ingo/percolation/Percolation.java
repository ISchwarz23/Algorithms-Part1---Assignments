package de.ingo.percolation;

/**
 * Created by Ingo on 12.09.2015.
 */
public class Percolation {

    private final boolean[][] field;

    public Percolation(int n) { // create N-by-N grid, with all sites blocked
        if(n <=0) {
            throw new IllegalArgumentException("N must be at least 1");
        }
        field = new boolean[n][n];
    }

    public void open(int i, int j) {    // open site (row i, column j) if it is not open already
        field[i-1][j-1] = true;
    }

    public boolean isOpen(int i, int j) {   // is site (row i, column j) open?
        return field[i-1][j-1];
    }

    public boolean isFull(int i, int j) {   // is site (row i, column j) full?
        return !isOpen(i, j);
    }

    public boolean percolates() {   // does the system percolate?
        // TODO
        return false;
    }

}
