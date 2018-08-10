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
  //  private Point[][] helper;
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if(points == null) throw new java.lang.IllegalArgumentException();
        int i,j,k,l;
        for(i=0;i<points.length;i++){
            if (points[i]==null){
                throw new java.lang.IllegalArgumentException();
            }
        }

        Point []testPoints = new Point[points.length];
        for(i=0;i<points.length;i++){
            testPoints[i] = points[i];
        }

        numberOfSegments = 0;

        segments = new LineSegment[testPoints.length*testPoints.length/2];

     //   helper = new Point[2][testPoints.length*testPoints.length/2];

        Arrays.sort(testPoints);

        for (i = 0; i < testPoints.length-1; i++){
            if(testPoints[i].compareTo(testPoints[i+1])==0) throw new java.lang.IllegalArgumentException();
        }


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
                        //LineSegment se = new LineSegment(line[0],line[j-s]);
                        if(line[0].compareTo(testPoints[i])==0) segments[numberOfSegments++] = new LineSegment(line[0],line[j-s]);
//                        helper[0][numberOfSegments-1] = line[0];
//                        helper[1][numberOfSegments-1] = line[j-s];
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
        if(numberOfSegments == 0) return new LineSegment[0];
        LineSegment[]resSegments = new LineSegment[numberOfSegments];
        for(int i = 0;i<numberOfSegments;i++)
            resSegments[i] = segments[i];
        return resSegments;
/*

        int i,j,k;
        for (i = 0;i<numberOfSegments-1;i++){
            for(j=i+1;j<numberOfSegments;j++){
                if(helper[0][i].compareTo(helper[0][j])==1){
                    Point temp1 = helper[0][i];
                    helper[0][i] = helper[0][j];
                    helper[0][j] = temp1;
                    temp1 = helper[1][i];
                    helper[1][i] = helper[1][j];
                    helper[1][j] = temp1;
                }
                if(helper[0][i].compareTo(helper[0][j])==0){
                    if(helper[1][i].compareTo(helper[1][j])==1){
                        Point temp1 = helper[0][i];
                        helper[0][i] = helper[0][j];
                        helper[0][j] = temp1;
                        temp1 = helper[1][i];
                        helper[1][i] = helper[1][j];
                        helper[1][j] = temp1;
                    }
                }

            }
        }

        i=0;j=0;
        while (j<numberOfSegments&&helper[0][i].compareTo(helper[0][j])==0&&helper[1][i].compareTo(helper[1][j])==0)
            j++;
        int cnt =0 ;
        if(numberOfSegments == 0) return new LineSegment[0];
        if(helper[0][0]!=null)
        resSegments[cnt++]=new LineSegment(helper[0][0],helper[1][0]);

        if (j<numberOfSegments) resSegments[cnt++]=new LineSegment(helper[0][j],helper[1][j]);
        while (j<numberOfSegments){
            i = j;
            while (j<numberOfSegments&&helper[0][i].compareTo(helper[0][j])==0&&helper[1][i].compareTo(helper[1][j])==0){
                j++;
            }
            if (j<numberOfSegments){
                resSegments[cnt++]=new LineSegment(helper[0][j],helper[1][j]);
            }
        }

        LineSegment[] res = new LineSegment[cnt];
        for(i=0;i<cnt;i++)
            res[i] = resSegments[i];
        numberOfSegments = cnt ;

        return res;
*/

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
