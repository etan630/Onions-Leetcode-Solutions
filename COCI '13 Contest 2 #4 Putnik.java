import java.io.*;
import java.util.*;

public class Main {
    static int n; 
    static int[][] adj; // rep cost btwn pair of nodes
    static int[][] dp; // store min cost from one node to another
    
    static int minFlight(int left, int right) {
        if (right >= n || left >= n) {
            return 0; // base case -- indices go beyond # nodes
        }
        
        if (dp[left][right] != -1) {
            return dp[left][right]; // computed value if available
        }
        
        int nextNode = Math.max(left, right) + 1;
        if (nextNode >= n) {
            dp[left][right] = 0; 
        } else {
            // calc min cost by considering next node
            dp[left][right] = Math.min(
                minFlight(nextNode, right) + adj[left][nextNode],
                minFlight(left, nextNode) + adj[right][nextNode]
            );
        }
        
        return dp[left][right];
    }
    
    // read inputs
    static void readAndInitialize(BufferedReader br) throws IOException {
        StringTokenizer st;
        
        n = Integer.parseInt(br.readLine());
        adj = new int[n][n];
        dp = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                adj[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1; // init dp with -1 
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        readAndInitialize(br); 
        
        System.out.print(minFlight(0, 0)); 
    }
}
