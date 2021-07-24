import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    //set variables.
    private int n;
    private final WeightedQuickUnionUF uf;
    private boolean[] site;
    private int toppoint;
    private int bottompoint;
    private int numOpen=0;
    private int index2;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n <= 0) throw new IllegalArgumentException();
        this.n=n;
        int n2=n*n;
        toppoint=n*n;
        bottompoint=n*n+1;
        this.uf=new WeightedQuickUnionUF(n2+2);//index n^2=top virtual point, n^2+1=bottom.
        this.site=new boolean[n*n+2];//false means not open
            site[toppoint]=true;
            site[bottompoint]=true;
    }
    private int getHash(int row,int col){
        return ((row-1)*n+(col-1));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();
        int index=getHash(row,col);
        //upper,lower,left,right point connectting to the index respectively.
        if (!site[index]){
            site[index]=true;
            numOpen+=1;
            if (validate(row-1,col)){//uppeer
                index2=getHash(row-1,col);
                if (site[index-n]){
                    uf.union(index2,index);
                }
            }
            else{
                uf.union(index,toppoint);
            }
            if (validate(row+1,col)){//lower
                index2=getHash(row+1,col);
                if (site[index+n]){
                    uf.union(index2,index);
                }
            }
            else{
                uf.union(index,bottompoint);
            }
            if (validate(row,col-1)){//left
                index2=getHash(row,col-1);
                if (site[index-1]){
                    uf.union(index2,index);
                }
            }
            if (validate(row,col+1)){//right
                index2=getHash(row,col+1);
                if (site[index+1]){
                    uf.union(index2,index);
                }
            }
        }
    }

    //judge argument range.
    private boolean validate(int n){
        if (n<0){
            return false;
//            throw new IllegalArgumentException();
        }
        else{
            return true;
        }
    }
    private boolean validate(int row, int col){
        if (row<1||col<1||row>n||col>n){
            return false;
//            throw new IllegalArgumentException();
        }
        else {
            return true;
        }
    }



    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();
        int index=getHash(row,col);
        return (site[index]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();
        int index=getHash(row,col);
        return uf.connected(index,toppoint);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.connected(toppoint,bottompoint);
    }
    // test client (optional)
    public static void main(String[] args){
//        int n=5;
//        Percolation pc=new Percolation(n);
//        for (int i=1;i<100;i+=1){
//            int row=(StdRandom.uniform(n)+1);
//            int col=(StdRandom.uniform(n)+1);
//            pc.open(row,col);
//            System.out.println(pc.numberOfOpenSites()+".");
//
//        }

    }
}
