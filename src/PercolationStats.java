import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;



public class PercolationStats {
    private double []threehold;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if(n<=0||trials<=0) throw new IllegalArgumentException();
        threehold = new double[trials];
        int i,j;
        for(i=0;i<trials;i++){
            Percolation experiment =new Percolation(n);
            while (experiment.percolates()==false){
                int _row = StdRandom.uniform(1,n+1);
                int _col = StdRandom.uniform(1,n+1);
                if(!experiment.isOpen(_row,_col)){
                    experiment.open(_row,_col);
                }
                else continue;
            }
            threehold[i]=(double)experiment.numberOfOpenSites()/(n*n);
        }

        mean = StdStats.mean(threehold);
        stddev = StdStats.stddev(threehold);
        confidenceLo = mean-1.96*stddev/Math.sqrt(threehold.length);
        confidenceHi = mean+1.96*stddev/Math.sqrt(threehold.length);

    }
    public double mean()                          // sample mean of percolation threshold
    {

        return mean;
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {

        return stddev;
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return confidenceLo;
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return confidenceHi;
    }

    public static void main(String[] args)        // test client (described below)
    {
        int n = StdIn.readInt();
        int trails = StdIn.readInt();
        PercolationStats test = new PercolationStats(n,trails);
        System.out.println("mean = "+test.mean());
        System.out.println("stddev = "+test.stddev());
        System.out.println("95% confidence interval = ["+test.confidenceLo()+" , "+test.confidenceHi()+"]");


    }
}
