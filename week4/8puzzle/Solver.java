/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int numMoves;
        private SearchNode prev;
        private int priority;
        private boolean twin;
        private int distance;

        public SearchNode(Board board, int numMoves, SearchNode prev, boolean twin) {
            this.board = board;
            this.numMoves = numMoves;
            this.prev = prev;
            this.priority = numMoves + board.manhattan();
            this.twin = twin;
            this.distance = board.manhattan();
        }

        public int compareTo(SearchNode that) {
            if (that.priority == this.priority) {
                return Integer.compare(this.distance, that.distance);
            }
            else {
                return Integer.compare(this.priority, that.priority);
            }
        }
    }

    private boolean solvable;
    private int moves;
    private Iterable<Board> boards;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> pq = new MinPQ<>();

        SearchNode first = new SearchNode(initial, 0, null, false);
        SearchNode firstTwin = new SearchNode(initial.twin(), 0, null, true);
        pq.insert(first);
        pq.insert(firstTwin);
        SearchNode curNode = pq.delMin();
        Board board = curNode.board;
        while (!board.isGoal()) {
            for (Board neighbor : board.neighbors()) {
                if (curNode.prev == null || !neighbor.equals(curNode.prev.board)) {
                    pq.insert(
                            new SearchNode(neighbor, curNode.numMoves + 1, curNode, curNode.twin));
                }
            }
            curNode = pq.delMin();
            board = curNode.board;
        }

        solvable = !curNode.twin;
        if (!solvable) {
            moves = -1;
            boards = null;
        }
        else {
            List<Board> list = new ArrayList<>();
            while (curNode != null) {
                list.add(curNode.board);
                curNode = curNode.prev;
            }
            moves = list.size() - 1;
            Collections.reverse(list);
            boards = list;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return this.boards;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
