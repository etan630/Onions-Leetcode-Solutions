import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // read Q
        int Q = Integer.parseInt(br.readLine());
        StringBuilder out = new StringBuilder();
        
        
        // go through each pair -- binary search
        for (int i = 0; i < Q; i++) {
            String[] comparisons = br.readLine().trim().split(" ");
            int y = Integer.parseInt(comparisons[0]);
            int z = Integer.parseInt(comparisons[1]);
            
            double x = findX(y, z);
            
            out.append(String.format("%.10f\n", x));
        }
        System.out.print(out.toString());
    }
    
    private static double findX(int y, int z) {
        double low = 1.0;
        double high = Math.max(2.0, Math.pow(z, 1.0 / y));
        double eps = 1e-6;
        
        // binary search
        while (high - low >= eps) {
            double mid = (low + high) / 2.0;
            if (calcPower(mid, y) >= z) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return (low + high) / 2.0;
    }
    
    private static double calcPower(double x, int y) {
        double result = x;
        // up to Y levels
        for (int i = 1; i < y; i++) {
            result = Math.pow(x, result);
        }
        return result;
    }
}
