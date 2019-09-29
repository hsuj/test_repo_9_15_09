import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class bruteForceClosestPair {
    public static void main(String[] args) throws Exception {
        URL url = bruteForceClosestPair.class.getResource("points.txt");
        File file = new File(url.getPath());
        Scanner scanNumOfLines = new Scanner(file);
        Scanner scanCoordinates = new Scanner(file).useDelimiter(",|\r\n"); //separates from ,'s and new lines

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
        double minX1 = Double.MAX_VALUE;
        double minY1 = Double.MAX_VALUE;
        double minX2 = Double.MAX_VALUE;
        double minY2 = Double.MAX_VALUE;
        double minDistance = Double.MAX_VALUE;
        int comparisonCount = 0;

        //brute-force algorithm to determine closest pair of points
        for(int i = 0; i < pointsArray.length - 1; i++) {
            for(int j = i + 1; j < pointsArray.length; j++) {
                double x1 = pointsArray[i][0];
                double y1 = pointsArray[i][1];
                double x2 = pointsArray[j][0];
                double y2 = pointsArray[j][1];
                if(distance(x1, y1, x2, y2) < minDistance) {
                    minDistance = distance(x1, y1, x2, y2);
                    minX1 = x1;
                    minY1 = y1;
                    minX2 = x2;
                    minY2 = y2;
                }
                comparisonCount++;
            }
        }

        System.out.println("The closest points are (" + minX1 + "," + minY1
                + ") and (" + minX2 + "," + minY2 + ") with a distance of " + minDistance);
        System.out.println("Total number of comparisons: " + comparisonCount);
    }

    //helper function to calculate distance between two points
    public static double distance(double x1, double y1, double x2, double y2) {
            double distanceVal = Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
        return distanceVal;
    }
}
