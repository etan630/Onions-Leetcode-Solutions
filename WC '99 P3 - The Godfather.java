import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;

        // until blank or neg number
        while ((line = in.readLine()) != null && !line.trim().isEmpty()) {
            float L = Float.parseFloat(line); 

            if (L < 0.0) {
                break; // break if neg
            }

            float area = calculateAreaUnderCurve(L); 
            printResult(area); 
        }
    }

    // monte carlo method
    private static float calculateAreaUnderCurve(float L) {
        Random rand = new Random();
        long countUnderCurve = 0; // points under curve

        for (int pts = 1; pts <= 30000; pts++) {
            float x = rand.nextFloat() * L; // x within 0, L
            float y = rand.nextFloat(); // y within 0, 1

            // check if below the curve y = e^(-x^2)
            if (y <= Math.exp(-x * x)) {
                countUnderCurve++;
            }
        }

        // area based on ratio under curve
        return L * countUnderCurve / 30000.0f;
    }
    
    private static void printResult(float area) {
        System.out.printf("%.2f\n", area);
    }
}
