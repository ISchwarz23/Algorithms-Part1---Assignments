import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * The class managing the percolation.
 *
 * @author ISchwarz
 */
public class Percolation {

    private final WeightedQuickUnionUF quickUnionStructure;

    private int gridSize;
    private final boolean[][] grid;

    private int virtualTopSite;
    private int virtualBottomSite;


    public Percolation(int n) { // create N-by-N grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException("N must be at least 1");
        }
        gridSize = n;
        grid = new boolean[n][n];

        quickUnionStructure = new WeightedQuickUnionUF(n * n + 2);

        virtualTopSite = 0;
        virtualBottomSite = n * n + 1;

        connectTopRowWithVirtualTopSite();
        connectBottomRowWithVirtualBottomSite();
    }

    private void connectTopRowWithVirtualTopSite() {
        for(int i=1; i<=gridSize; i++) {
            int fieldIndex = getFieldIndexInQuickUnionStructure(1, i);
            quickUnionStructure.union(virtualTopSite, fieldIndex);
        }
    }

    private void connectBottomRowWithVirtualBottomSite() {
        for(int i=1; i<=gridSize; i++) {
            int fieldIndex = getFieldIndexInQuickUnionStructure(gridSize, i);
            quickUnionStructure.union(virtualBottomSite, fieldIndex);
        }
    }

    public void open(int i, int j) {    // open site (row i, column j) if it is not open already
        if (!isOpen(i, j)) {
            int fieldIndex = getFieldIndexInQuickUnionStructure(i, j);
            connectIfOpen(fieldIndex, i - 1, j);
            connectIfOpen(fieldIndex, i + 1, j);
            connectIfOpen(fieldIndex, i, j - 1);
            connectIfOpen(fieldIndex, i, j + 1);
            grid[i - 1][j - 1] = true;
        }
    }

    public boolean isOpen(int i, int j) {   // is site (row i, column j) open?
        return grid[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {   // is site (row i, column j) connected to top?
        if(isOpen(i, j)) {
            int fieldIndex = getFieldIndexInQuickUnionStructure(i, j);
            return quickUnionStructure.connected(virtualTopSite, fieldIndex);
        }
        return false;
    }

    public boolean percolates() {   // does the system percolate?
        return quickUnionStructure.connected(virtualTopSite, virtualBottomSite);
    }

    private void connectIfOpen(int fieldIndex, int i, int j) {
        try {
            if (isOpen(i, j)) {
                int neighbourFieldIndex = getFieldIndexInQuickUnionStructure(i, j);
                quickUnionStructure.union(neighbourFieldIndex, fieldIndex);
            }
        } catch (IndexOutOfBoundsException e) {
            // don't connect field with field outside grid
        }
    }

    private int getFieldIndexInQuickUnionStructure(int i, int j) {
        return (i-1) * gridSize + j;
    }

}
