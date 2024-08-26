import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // read inputs
        int N = scanner.nextInt();
        int W = scanner.nextInt();
        int[][] items = readItems(scanner, N);
        
        System.out.print(knapsack(N, W, items));
    }

    private static int[][] readItems(Scanner scanner, int N) {
        int[][] items = new int[N][2];
        for (int i = 0; i < N; i++) {
            items[i][0] = scanner.nextInt(); // weight
            items[i][1] = scanner.nextInt(); // value
        }
        return items;
    }

    private static long knapsack(int N, int W, int[][] items) {
        long[] dp = new long[W + 1];
        
        // sort in descending order
        Arrays.sort(items, (a, b) -> Integer.compare(b[1], a[1]));
        
        // update dp to find the maximum value at each weight 
        for (int i = 0; i < N; i++) {
            for (int j = W; j >= items[i][0]; j--) {
                dp[j] = Math.max(dp[j], dp[j - items[i][0]] + items[i][1]);
            }
        }
        return dp[W];
    }
}
