import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if(points==null) throw new java.lang.IllegalArgumentException();
        int i,j,k,l;
        for(i=0;i<points.length;i++){
            if (points[i]==null){
                throw new java.lang.IllegalArgumentException();
            }
        }
        for(i=0;i<points.length;i++){
            for (j=0;j<points.length;j++){
                if(i==j) continue;
                 else
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

        segments = new LineSegment[testPoints.length*testPoints.length/2];

        Arrays.sort(testPoints);
        for(i=0;i<testPoints.length;i++)
            for(j=i+1;j<testPoints.length;j++)
                for (k=j+1;k<testPoints.length;k++)
                    for (l=k+1;l<testPoints.length;l++){
                        if(testPoints[i].slopeTo((testPoints[j]))==testPoints[j].slopeTo(testPoints[k]) &&testPoints[j].slopeTo(testPoints[k])==testPoints[k].slopeTo(testPoints[l]))
                            segments [numberOfSegments++]= new LineSegment(testPoints[i],testPoints[l]);

                    }

    }
    public  int numberOfSegments()        // the number of line segments
    {
        return numberOfSegments;
    }
    public LineSegment[] segments()                // the line segments
    {
        LineSegment[]resSegments = new LineSegment[numberOfSegments];
        for(int i = 0;i<numberOfSegments;i++)
            resSegments[i] = segments[i];
        return resSegments;
    }


    public static void main(String[] args) {
        // read the n points from a file
        try{
            FileInputStream input = new FileInputStream("/Users/mac/Desktop/Algorithms/collinear/rs1423.txt");
            System.setIn(input);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

//        In in = new In(args[0]);
//        int n = in.readInt();
        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
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
