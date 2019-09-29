import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class helloWorld {
    public static void main(String[] args) throws Exception{
        int numOfPts = 3000;
        double x = 0.0;
        double y = 0.0;
        ArrayList<String> lines = new ArrayList<String>();
        Path file = Paths.get("points.txt");
        for (int i = 0; i < numOfPts; i++) {
            x = x + 1;
            y = y + 1;
            String outputText = "" + round(x, 1) + "," + round(y,1);
            lines.add(outputText);
        }
        Files.write(file, lines, StandardCharsets.UTF_8);
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
