import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int W = scanner.nextInt();
        int H = scanner.nextInt();
        int N = scanner.nextInt();
        
        int[][] plates = readPlates(scanner, N);
        
        System.out.print(min_waste(W, H, plates));
    }
    
    private static int[][] readPlates(Scanner scanner, int N) {
        int[][] plates = new int[N][2];
        for (int i = 0; i < N; i++) {
            plates[i][0] = scanner.nextInt(); // width 
            plates[i][1] = scanner.nextInt(); // height 
        }
        return plates;
    }
    
    private static int min_waste(int W, int H, int[][] plates) {
        int[][] dp = new int[W + 1][H + 1];
        
        // init dp
        for (int w = 1; w <= W; w++) {
            for (int h = 1; h <= H; h++) {
                dp[w][h] = w * h; // max waste init
                if (contains(plates, w, h)) {
                    dp[w][h] = 0; 
                }
                
                // split vertically
                for (int i = 1; i < w; i++) {
                    dp[w][h] = Math.min(dp[w][h], dp[i][h] + dp[w - i][h]);
                }
                
                // split horizontally
                for (int j = 1; j < h; j++) {
                    dp[w][h] = Math.min(dp[w][h], dp[w][j] + dp[w][h - j]);
                }
            }
        }
        return dp[W][H];
    }

    // checks if plate of given dimensions exists in list
    private static boolean contains(int[][] plates, int w, int h) {
        for (int[] plate : plates) {
            if (plate[0] == w && plate[1] == h) {
                return true;
            }
        }
        return false;
    }
}
