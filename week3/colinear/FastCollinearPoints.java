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
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private Point[] points;
    private List<LineSegment> lineSegments;

    public FastCollinearPoints(
            Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            this.points[i] = points[i];
        }
        checkDuplicates(this.points);
        this.lineSegments = new ArrayList<>();

        for (int i = 0; i < this.points.length - 1; i++) {
            Double[] slopes = new Double[i];
            Point[] pointsLeft = new Point[this.points.length - i - 1];
            for (int s = 0; s < i; s++) {
                slopes[s] = this.points[i].slopeTo(this.points[s]);
            }

            for (int p = 0; p < this.points.length - 1 - i; p++) {
                pointsLeft[p] = this.points[i + p + 1];
            }

            Merge.sort(slopes);

            Arrays.sort(pointsLeft, this.points[i].slopeOrder());
            addSegment(this.points[i], slopes, pointsLeft);
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

    private void addSegment(Point point, Double[] slopes, Point[] pointsLeft) {
        int count = 1;
        double prevSlope = point.slopeTo(pointsLeft[0]);
        for (int i = 1; i < pointsLeft.length; i++) {
            double curSlope = point.slopeTo(pointsLeft[i]);

            if (curSlope != prevSlope) {
                if (count >= 3 && !isCoveredByExistingSlopes(slopes, prevSlope)) {
                    this.lineSegments.add(new LineSegment(point, pointsLeft[i - 1]));
                }
                count = 1;
            }
            else {
                count++;
            }

            prevSlope = curSlope;
        }

        if (count >= 3 && !isCoveredByExistingSlopes(slopes, prevSlope)) {
            this.lineSegments.add(new LineSegment(point, pointsLeft[pointsLeft.length - 1]));
        }
    }

    private boolean isCoveredByExistingSlopes(Double[] slopes, double target) {
        int lo = 0, hi = slopes.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (slopes[mid] == target) return true;
            else if (slopes[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return false;
    }

    public int numberOfSegments() {
        return this.lineSegments.size();
    }

    public LineSegment[] segments() {
        return this.lineSegments.toArray(new LineSegment[this.lineSegments.size()]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
