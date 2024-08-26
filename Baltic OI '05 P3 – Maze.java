import java.io.*;
import java.util.*;

public class Main {
    // Class to represent an edge between two points
    static class Edge {
        int x, y;
        boolean c;

        Edge(int x, int y, boolean c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }

    static final int MN = 501;
    static List<Edge>[][] graph = new ArrayList[MN][MN];
    static int[][][] distance = new int[MN][MN][2];
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int columns = sc.nextInt();
        int rows = sc.nextInt();
        int startY = sc.nextInt();
        int startX = sc.nextInt();
        int endY = sc.nextInt();
        int endX = sc.nextInt();

        rows++;
        columns++;
        initializeGraph(rows, columns);
        sc.nextLine();
        readEdges(sc, rows, columns);
        bfs(startX, startY);
        printShortestDistance(endX, endY);
    }

    public static void addEdge(int a, int b, int c, int d, char edgeType) {
        if (edgeType == 'n') return; // No edge
        boolean isDiagonal = (edgeType == 'w');
        graph[a][b].add(new Edge(c, d, isDiagonal));
        graph[c][d].add(new Edge(a, b, isDiagonal));
    }

    public static void bfs(int startX, int startY) {
        Deque<Edge> queue = new ArrayDeque<>();
        for (int i = 0; i < 2; i++) {
            distance[startX][startY][i] = 0;
            queue.add(new Edge(startX, startY, i == 1));
        }

        while (!queue.isEmpty()) {
            Edge current = queue.poll();
            int nextDist = distance[current.x][current.y][current.c ? 1 : 0] + 1;

            for (Edge to : graph[current.x][current.y]) {
                if (to.c == current.c) continue;
                if (distance[to.x][to.y][to.c ? 1 : 0] == -1) {
                    distance[to.x][to.y][to.c ? 1 : 0] = nextDist;
                    queue.add(to);
                }
            }
        }
    }

    // init graph and arr
    private static void initializeGraph(int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                graph[i][j] = new ArrayList<>();
                Arrays.fill(distance[i][j], -1);
            }
        }
    }

    // adds edges to graph
    private static void readEdges(Scanner sc, int rows, int columns) {
        for (int i = 0; i < rows - 1; i++) {
            String line = sc.nextLine().strip();
            for (int j = 0; j < columns - 1; j++) {
                addEdge(i, j, i, j + 1, line.charAt(j));
            }

            line = sc.nextLine().strip();
            for (int j = 0; j < columns; j++) {
                addEdge(i, j, i + 1, j, line.charAt(2 * j));
            }
            for (int j = 1; j < columns; j++) {
                addEdge(i, j, i + 1, j - 1, line.charAt(2 * j - 1));
            }
        }

        String line = sc.nextLine().strip();
        for (int i = 0; i < columns - 1; i++) {
            addEdge(rows - 1, i, rows - 1, i + 1, line.charAt(i));
        }
    }

    // prints shortest dist to end point
    private static void printShortestDistance(int endX, int endY) {
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < 2; i++) {
            if (distance[endX][endY][i] != -1) {
                minDistance = Math.min(minDistance, distance[endX][endY][i]);
            }
        }
        System.out.print(minDistance == Integer.MAX_VALUE ? -1 : minDistance);
    }
}
