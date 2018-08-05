import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Percolation {
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF weightedQuickUnionUF2;
    private int grid_size;
    private boolean [][]grid;
    private int num_of_open_sites;
    public Percolation(int n)           // create n-by-n grid, with all sites blocked
    {
        if(n<=0) throw new IllegalArgumentException();
        grid_size = n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n*n+2);
        weightedQuickUnionUF2 = new WeightedQuickUnionUF(n*n+1);
        grid = new boolean[n+1][n+1];
        num_of_open_sites = 0;
    }
    public boolean validIndices(int row, int col){
        if(row>=1&&row<=grid_size&&col>=1&&col<=grid_size) return true;
        else return false;
    }
    public    void open(int row, int col) // open site (row, col) if it is not open already
    {
        if(validIndices(row,col)==false) throw new java.lang.IllegalArgumentException();
        if(grid[row][col]==false&&validIndices(row,col)) {
            grid[row][col]=true;
            num_of_open_sites++;
            {
                if(row>1&&isOpen(row-1,col)) {
                    weightedQuickUnionUF.union(grid_size*(row-1-1)+col,grid_size*(row-1)+col);
                    weightedQuickUnionUF2.union(grid_size*(row-1-1)+col,grid_size*(row-1)+col);
                }
                if(col>1&&isOpen(row,col-1)) {
                    weightedQuickUnionUF.union(grid_size*(row-1)+col-1,grid_size*(row-1)+col);
                    weightedQuickUnionUF2.union(grid_size*(row-1)+col-1,grid_size*(row-1)+col);
                }
                if(col<grid_size&&isOpen(row,col+1)) {
                    weightedQuickUnionUF.union(grid_size*(row-1)+col+1,grid_size*(row-1)+col);
                    weightedQuickUnionUF2.union(grid_size*(row-1)+col+1,grid_size*(row-1)+col);

                }
                if(row<grid_size&&isOpen(row+1,col)) {
                    weightedQuickUnionUF.union(grid_size*(row+1-1)+col,grid_size*(row-1)+col);
                    weightedQuickUnionUF2.union(grid_size*(row+1-1)+col,grid_size*(row-1)+col);
                }
            }
            if(row==1) {
                weightedQuickUnionUF.union(0,grid_size*(row-1)+col);
                weightedQuickUnionUF2.union(0,grid_size*(row-1)+col);
            }
            if(row==grid_size) weightedQuickUnionUF.union(grid_size*grid_size+1,grid_size*(row-1)+col);
        }
    }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if(validIndices(row,col)==false) throw new java.lang.IllegalArgumentException();
        return grid[row][col]==true?true:false;
    }
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if(validIndices(row,col)==false) throw new java.lang.IllegalArgumentException();
        return grid[row][col]==true&&weightedQuickUnionUF2.connected(0,grid_size*(row-1)+col)?true:false;
    }
    public     int numberOfOpenSites()       // number of open sites
    {
       return num_of_open_sites;
    }
    public boolean percolates()              // does the system percolate?
    {
        if(weightedQuickUnionUF.connected(0,grid_size*grid_size+1)) return true;
        else return false;
    }

    public static void main(String[] args)   // test client (optional)
    {
        try{
            FileInputStream input = new FileInputStream("/Users/mac/Desktop/Algorithms/percolation/greeting57.txt");
            System.setIn(input);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()){
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            percolation.open(i,j);
        }
        System.out.println(percolation.percolates());
        System.out.println(percolation.numberOfOpenSites());
    }
}
