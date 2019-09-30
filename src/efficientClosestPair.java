import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class efficientClosestPair {
    public static void main(String[] args) throws Exception {
        URL url = bruteForceClosestPair.class.getResource("points.txt");
        File file = new File(url.getPath());
        Scanner scanNumOfLines = new Scanner(file);
        Scanner scanCoordinates = new Scanner(file).useDelimiter(",|\r\n"); //separates from ,'s and new lines
        Scanner userInput = new Scanner(System.in);

        //reads file that contains points and determines number of points
        int numOfPoints = 0;
        while(scanNumOfLines.hasNextLine()) {
            numOfPoints++;
            scanNumOfLines.nextLine();
        }
        scanNumOfLines.close();

        //populates 2D array of points from the text file
        double[][] pointsArray = new double[numOfPoints][2];
        for(int i = 0; i < numOfPoints; i++) {
            pointsArray[i][0] = scanCoordinates.nextDouble();
            pointsArray[i][1] = scanCoordinates.nextDouble();
        }
        scanCoordinates.close();

        //setting initial values
        System.out.println("Enter the number of m closest points: ");
        Globals.mClosest = userInput.nextInt();
        Globals.closestPointsArray = new double[Globals.mClosest][5]; //columns: x1, y1, x2, y2, distance
        Globals.comparisonCount = 0;

        //fill distance values with infinity
        for(int i = 0; i < Globals.mClosest; i++) {
            Globals.closestPointsArray[i][4] = Double.MAX_VALUE;
        }

        Arrays.sort(pointsArray, Comparator.comparingDouble(a -> a[0])); //sorts set of points by x-coord
        divide(pointsArray);

        for(int i = 0; i < Globals.mClosest; i++) {
            System.out.println("The closest points are (" + Globals.closestPointsArray[i][0]
                    + "," + Globals.closestPointsArray[i][1] + ") and ("
                    + Globals.closestPointsArray[i][2] + ","
                    + Globals.closestPointsArray[i][3] + ") with a distance of "
                    + Globals.closestPointsArray[i][4]);
        }
        System.out.println("Total number of comparisons: " + Globals.comparisonCount);
    }

    //Recursive divide
    public static void divide(double[][] pointsArray) {
        int numOfPoints = pointsArray.length;

        //Base case
        if(numOfPoints == 2 || numOfPoints == 3) {
            minDistance(pointsArray);
            Globals.comparisonCount++;
            return;
        }

        //Splitting array into half
        int midIndex = numOfPoints/2;
        double[][] leftArray = new double[midIndex][2];
        double[][] rightArray = new double[numOfPoints-midIndex][2];

        //Transferring values to sub-arrays
        for(int i = 0; i < midIndex; i++) {
            leftArray[i][0] = pointsArray[i][0];
            leftArray[i][1] = pointsArray[i][1];
        }
        for(int i = 0; i < numOfPoints-midIndex; i++) {
            rightArray[i][0] = pointsArray[midIndex+i][0];
            rightArray[i][1] = pointsArray[midIndex+i][1];
        }

        divide(leftArray);
        divide(rightArray);
        return;
    }

    public static void minDistance(double[][] pointsArray) {
        int numOfPoints = pointsArray.length;
        if(numOfPoints == 2) {
            double x1 = pointsArray[0][0];
            double y1 = pointsArray[0][1];
            double x2 = pointsArray[1][0];
            double y2 = pointsArray[1][1];
            //check if distance is smaller than largest distance in m closest points thus far
            if(distance(x1, y1, x2, y2) < Globals.closestPointsArray[Globals.mClosest-1][4]) {
                Globals.closestPointsArray[Globals.mClosest-1][0] = x1;
                Globals.closestPointsArray[Globals.mClosest-1][1] = y1;
                Globals.closestPointsArray[Globals.mClosest-1][2] = x2;
                Globals.closestPointsArray[Globals.mClosest-1][3] = y2;
                Globals.closestPointsArray[Globals.mClosest-1][4] = distance(x1, y1, x2, y2);
                Arrays.sort(Globals.closestPointsArray, Comparator.comparingDouble(a -> a[4])); //sort by distance
            }
        }
        else if(numOfPoints == 3) {
            double x1 = pointsArray[0][0];
            double y1 = pointsArray[0][1];
            double x2 = pointsArray[1][0];
            double y2 = pointsArray[1][1];
            double x3 = pointsArray[2][0];
            double y3 = pointsArray[2][1];
            if(distance(x1, y1, x2, y2) < Globals.closestPointsArray[Globals.mClosest-1][4]) {
                Globals.closestPointsArray[Globals.mClosest-1][0] = x1;
                Globals.closestPointsArray[Globals.mClosest-1][1] = y1;
                Globals.closestPointsArray[Globals.mClosest-1][2] = x2;
                Globals.closestPointsArray[Globals.mClosest-1][3] = y2;
                Globals.closestPointsArray[Globals.mClosest-1][4] = distance(x1, y1, x2, y2);
                Arrays.sort(Globals.closestPointsArray, Comparator.comparingDouble(a -> a[4])); //sort by distance
            }
            if(distance(x1, y1, x3, y3) < Globals.closestPointsArray[Globals.mClosest-1][4]) {
                Globals.closestPointsArray[Globals.mClosest-1][0] = x1;
                Globals.closestPointsArray[Globals.mClosest-1][1] = y1;
                Globals.closestPointsArray[Globals.mClosest-1][2] = x3;
                Globals.closestPointsArray[Globals.mClosest-1][3] = y3;
                Globals.closestPointsArray[Globals.mClosest-1][4] = distance(x1, y1, x3, y3);
                Arrays.sort(Globals.closestPointsArray, Comparator.comparingDouble(a -> a[4])); //sort by distance
            }
            if(distance(x2, y2, x3, y3) < Globals.closestPointsArray[Globals.mClosest-1][4]) {
                Globals.closestPointsArray[Globals.mClosest-1][0] = x2;
                Globals.closestPointsArray[Globals.mClosest-1][1] = y2;
                Globals.closestPointsArray[Globals.mClosest-1][2] = x3;
                Globals.closestPointsArray[Globals.mClosest-1][3] = y3;
                Globals.closestPointsArray[Globals.mClosest-1][4] = distance(x2, y2, x3, y3);
                Arrays.sort(Globals.closestPointsArray, Comparator.comparingDouble(a -> a[4])); //sort by distance
            }
        }
        return;
    }

    //helper function to calculate distance between two points
    public static double distance(double x1, double y1, double x2, double y2) {
        double distanceVal = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return distanceVal;
    }
}