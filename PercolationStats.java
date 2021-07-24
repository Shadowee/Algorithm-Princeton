import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private int trials,n;
     private final double consttime=1.96;
     private int [] t;
     private double [] dt;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        this.n=n;
        this.trials=trials;
        this.t=new int [trials] ;
        this.dt=new double [trials];
        if (n<=0||trials<=0){
            throw new IllegalArgumentException();
        }
        //experiment percolation
        for (int i=0;i<trials;i=i+1){
            Percolation pr = new Percolation(n) ;
            while (!pr.percolates()){
                int row=(StdRandom.uniform(n)+1);
                int col=(StdRandom.uniform(n)+1);
                if (!pr.isOpen(row,col)){
//                    System.out.println(pr.isOpen(row,col)+" "+row+"_"+col);
                    pr.open(row,col);
                }
            }
            int numOpen=pr.numberOfOpenSites();
//            System.out.println(t);
//            System.out.println(numOpen);
            t[i]=numOpen;
//            System.out.println(((double)numOpen)/(n*n));
            dt[i]=((double)numOpen)/(n*n);
        }
//        for (int i:t){
//            System.out.print(i+" ");
//        }
//        System.out.println();
//        for (double i:dt){
//            System.out.print(i+" ");
//        }

    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(dt);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(dt);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        double xbar=mean();
        return xbar-consttime*stddev()/((double)Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        double xbar=mean();
//        System.out.println((Math.sqrt(trials)));
        return xbar+consttime*stddev()/(Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args){
        int numSites = Integer.parseInt(args[0]);
        int numTrials = Integer.parseInt(args[1]);
//        int numSites = 200;
//        int numTrials =100;
        PercolationStats prlt=new PercolationStats(numSites,numTrials);



        StdOut.println ("mean                    = "+prlt.mean());
        StdOut.println ("stddev                  = "+prlt.stddev());
        StdOut.println ("95% confidence interval = "+prlt.confidenceLo()+","+prlt.confidenceHi());
            }

}
