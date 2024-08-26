import java.io.*;
import java.util.*;

public class Main {
    private static int min_path(int N, int K, int[] h) {
        //init dp
        int[] dp = new int[N + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[1] = 0; // shortest path is 0 at first stone
        
        // find min cost
        for (int i = 2; i < N + 1; i++) {
            for (int j = Math.max(1, i - K); j < i; j++) {
                dp[i] = Math.min(dp[i], dp[j] + Math.abs(h[i - 1] - h[j - 1]));
            }
        }
        return dp[N];
    }
    public static void main(String[] args) {
        // read inputs and call dp func
        Scanner scanner = new Scanner(System.in);
        
        int N = scanner.nextInt();
        int K = scanner.nextInt();
        int[] h = new int[N];
        for (int i = 0; i < N; i++) {
            h[i] = scanner.nextInt();
        }
        
        System.out.print(min_path(N, K, h));
    }
}
