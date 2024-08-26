import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();

        int[][] adjacencyMatrix = readGraph(N, M, scanner);

        int[] shortestDistances = dijkstra(N, adjacencyMatrix);

        printShortestDistances(N, shortestDistances);
    }

    // read graph input ret adj 
    private static int[][] readGraph(int N, int M, Scanner scanner) {
        int[][] matrix = new int[N + 1][N + 1];
        // init w max val
        for (int[] row : matrix) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // set dist to 0
        for (int i = 1; i <= N; i++) {
            matrix[i][i] = 0;
        }

        // get smallest weight
        for (int i = 0; i < M; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int weight = scanner.nextInt();
            matrix[u][v] = Math.min(matrix[u][v], weight);
            matrix[v][u] = Math.min(matrix[v][u], weight);
        }

        return matrix;
    }

    // dijstra :D
    private static int[] dijkstra(int N, int[][] adjacencyMatrix) {
        int[] distances = new int[N + 1];
        boolean[] processed = new boolean[N + 1];

        // init distances with maximum values
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[1] = 0;

        // iterates over nodes to find shortest paths
        for (int i = 1; i <= N; i++) {
            int currentNode = findClosestUnprocessedNode(N, distances, processed);
            if (currentNode == -1) break;

            processed[currentNode] = true;

            // update distances of the neighboring nodes
            for (int j = 1; j <= N; j++) {
                if (adjacencyMatrix[currentNode][j] != Integer.MAX_VALUE) {
                    distances[j] = Math.min(distances[j], distances[currentNode] + adjacencyMatrix[currentNode][j]);
                }
            }
        }

        return distances;
    }

    private static int findClosestUnprocessedNode(int N, int[] distances, boolean[] processed) {
        int closestNode = -1;
        int smallestDistance = Integer.MAX_VALUE;

        // finds the closest unprocessed node based on dist
        for (int i = 1; i <= N; i++) {
            if (!processed[i] && distances[i] < smallestDistance) {
                smallestDistance = distances[i];
                closestNode = i;
            }
        }

        return closestNode;
    }

    // prints shortest distances from node 1 to others
    private static void printShortestDistances(int N, int[] distances) {
        for (int i = 1; i <= N; i++) {
            if (distances[i] == Integer.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(distances[i]);
            }
        }
    }
}
