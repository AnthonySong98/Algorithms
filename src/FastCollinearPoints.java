import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if(points.length==0) throw new java.lang.IllegalArgumentException();
        int i,j,k,l;
        for(i=0;i<points.length;i++){
            if (points[i]==null){
                throw new java.lang.IllegalArgumentException();
            }
        }
        for(i=0;i<points.length;i++){
            for (j=0;j<points.length;j++){
                if(i==j) continue;;
                {
                    if(points[i].slopeTo(points[j])==Double.NEGATIVE_INFINITY)
                        throw new java.lang.IllegalArgumentException();
                }
            }
        }

        Point []testPoints = new Point[points.length];
        for(i=0;i<points.length;i++){
            testPoints[i] = points[i];
        }

        numberOfSegments = 0;

        segments = new LineSegment[testPoints.length];

        Arrays.sort(testPoints);

        for(i=0;i<testPoints.length;i++){

        }

    }
    public  int numberOfSegments()        // the number of line segments
    {
        return 0;
    }
    public LineSegment[] segments()                // the line segments
    {
        return new LineSegment[2];
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
