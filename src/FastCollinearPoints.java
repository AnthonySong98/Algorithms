import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

        segments = new LineSegment[testPoints.length*testPoints.length/2];

        Arrays.sort(testPoints);

        for(i=0;i<testPoints.length;i++){
            Point tempPoints[] = new Point[testPoints.length-1];
            int pos = 0;
            for(j=0;j<testPoints.length;j++){
                if(i==j) continue;
                tempPoints[pos++] = testPoints[j];
            }
            Arrays.sort(tempPoints,testPoints[i].slopeOrder());
            for(j=0;j<tempPoints.length-2;j++){
                if(testPoints[i].slopeTo(tempPoints[j])==testPoints[i].slopeTo(tempPoints[j+1])
                        &&testPoints[i].slopeTo(tempPoints[j+1])==testPoints[i].slopeTo(tempPoints[j+2]))
                {
                    int s = j;double SLOPE = testPoints[i].slopeTo(tempPoints[j]);j=j+3;
                    while(j<tempPoints.length&&testPoints[i].slopeTo(tempPoints[j])==SLOPE){
                        j = j + 1;
                    }
                    if(j<=tempPoints.length){
                        /*把testpoint[i],testpoint[s]到testpoint[j-1]存一下，排一下*/
                        Point[] line = new Point[j-1-s+1+1];
                        line[0] = testPoints[i];
                        for (int r = 1;r<=j-s;r++) line[r] = tempPoints[s+r-1];
                        Arrays.sort(line);
                        segments[numberOfSegments++] = new LineSegment(line[0],line[j-s]);
                        j = j - 1;
                    }
                    else {
                        break;
                    }
                }

            }




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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
