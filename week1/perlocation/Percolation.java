import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] grid;
    private int n;
    private WeightedQuickUnionUF uf;
    private int openSitesNum;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be greater than 0");
        this.grid = new int[n][n];
        this.openSitesNum = 0;
        this.n = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!checkRange(row, col)) throw new IllegalArgumentException("Index out if range!");

        if (!isOpen(row, col)) {
            this.grid[row - 1][col - 1] = 1;
            this.openSitesNum++;
            int index = col + (row - 1) * n;
            if (row == 1) {
                uf.union(index, 0);
            }
            else if (isOpen(row - 1, col)) {
                int up = index - n;
                uf.union(index, up);
            }
            if (row == n) {
                uf.union(n * n + 1, index);
            }
            else if (row < n && isOpen(row + 1, col)) {
                int down = index + n;
                uf.union(index, down);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                int left = index - 1;
                uf.union(index, left);
            }
            if (col < n && isOpen(row, col + 1)) {
                int right = index + 1;
                uf.union(index, right);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!checkRange(row, col)) throw new IllegalArgumentException("Index out if range!");
        return this.grid[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!checkRange(row, col)) throw new IllegalArgumentException("Index out if range!");
        int index = col + (row - 1) * n;
        return uf.connected(0, index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSitesNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, n * n + 1);
    }

    private boolean checkRange(int row, int col) {
        return row >= 1 && col >= 1 && row <= n && col <= n;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
