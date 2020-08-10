/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {

    private Point[] points;
    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            this.points[i] = points[i];
        }

        checkDuplicates(this.points);
        this.lineSegments = new ArrayList<>();
        for (int p = 0; p < this.points.length; p++) {
            for (int q = p + 1; q < this.points.length; q++) {
                for (int r = q + 1; r < this.points.length; r++) {
                    if (this.points[p].slopeTo(this.points[q]) == this.points[p]
                            .slopeTo(this.points[r])) {
                        for (int s = r + 1; s < this.points.length; s++) {
                            if (this.points[p].slopeTo(this.points[q]) == this.points[p]
                                    .slopeTo(this.points[s])) {
                                lineSegments.add(new LineSegment(this.points[p], this.points[s]));
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkDuplicates(Point[] points) {
        Merge.sort(points);

        for (int i = 1; i < points.length; i++) {
            if (points[i].slopeTo(points[i - 1]) == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return this.lineSegments.size();
    }

    public LineSegment[] segments() {
        return this.lineSegments.toArray(new LineSegment[numberOfSegments()]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
