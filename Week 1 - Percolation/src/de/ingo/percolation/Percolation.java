package de.ingo.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * The class managing the percolation.
 * @author ISchwarz
 */
public class Percolation {

    private final boolean[][] grid;


    public Percolation(int n) { // create N-by-N grid, with all sites blocked
        if(n <=0) {
            throw new IllegalArgumentException("N must be at least 1");
        }
        grid = new boolean[n][n];
    }

    public void open(int i, int j) {    // open site (row i, column j) if it is not open already
        if(!isOpen(i, j)) {
            // TODO union
            grid[i - 1][j - 1] = true;
        }
    }

    public boolean isOpen(int i, int j) {   // is site (row i, column j) open?
        return grid[i-1][j-1];
    }

    public boolean isFull(int i, int j) {   // is site (row i, column j) connected to top?
        return false;
    }

    public boolean percolates() {   // does the system percolate?
        // TODO
        return false;
    }

}
