/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(tiles[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) continue;
                if (tiles[i][j] != i * n + j + 1) {
                    res++;
                }
            }
        }
        return res;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                int correctInd = tiles[i][j] - 1;
                int x = correctInd / n;
                int y = correctInd % n;
                res += Math.abs(x - i) + Math.abs(y - j);
            }
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(that.tiles, this.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int x = -1, y = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        int[][] dirs = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
        for (int[] d : dirs) {
            int nx = x + d[0];
            int ny = y + d[1];
            if (nx >= 0 && ny >= 0 && nx < n && ny < n) {
                neighbors.add(getNeighborBoard(x, y, nx, ny, tiles));
            }
        }
        return neighbors;
    }

    private Board getNeighborBoard(int x, int y, int nx, int ny, int[][] oldTiles) {
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newTiles[i][j] = oldTiles[i][j];
            }
        }
        int tmp = newTiles[x][y];
        newTiles[x][y] = newTiles[nx][ny];
        newTiles[nx][ny] = tmp;
        return new Board(newTiles);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board newBoard = null;
        for (int i = 0; i < n * n - 1; i++) {
            int x = i / n;
            int y = i % n;
            int nx = (i + 1) / n;
            int ny = (i + 1) % n;
            if (tiles[x][y] != 0 && tiles[nx][ny] != 0) {
                newBoard = getNeighborBoard(x, y, nx, ny, tiles);
                break;
            }
        }
        return newBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        System.out.println(initial.manhattan());

    }

}
