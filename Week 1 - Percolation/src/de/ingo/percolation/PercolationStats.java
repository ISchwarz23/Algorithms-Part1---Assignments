package de.ingo.percolation;

/**
 * Created by Ingo on 12.09.2015.
 */
public class PercolationStats {

    public PercolationStats(int N, int T) {     // perform T independent experiments on an N-by-N grid
        if(N <= 0) {
            throw new IllegalArgumentException("The grid size must be bigger than zero");
        }
        if(T <= 0) {
            throw new IllegalArgumentException("The number of experiments must be bigger than zero");
        }

        for(int i=0; i<T; i++) {
            Percolation percolation = new Percolation(N);
            // TODO
        }
    }

    public double mean() {         // sample mean of percolation threshold
        return 0;
    }

    public double stddev() {          // sample standard deviation of percolation threshold
        return 0;
    }

    public double confidenceLo() {         // low  endpoint of 95% confidence interval
        return 0;
    }

    public double confidenceHi() {          // high endpoint of 95% confidence interval
        return 0;
    }

    public static void main(String[] args) { // test client (described below)
        int N = Integer.valueOf(args[0]);
        int T = Integer.valueOf(args[1]);

        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }

}
