import java.io.*;
import java.util.*;

public class Main {
    // edge class
    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int M = scanner.nextInt();

        List<List<Edge>> graph = createGraph(N, M, scanner);

        int[] indegree = computeIndegrees(N, graph);

        List<Integer> topOrder = findTopologicalOrder(N, indegree, graph);

        // check for a cycle in graph
        if (topOrder.size() != N) {
            System.out.print("-1");
            return;
        }

        int[] results = computeLongestPath(N, topOrder, graph);

        printResults(results);
    }

    // creates graph from input
    private static List<List<Edge>> createGraph(int N, int M, Scanner scanner) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < M; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int d = scanner.nextInt();
            graph.get(a).add(new Edge(b, d));
        }
        return graph;
    }

    private static int[] computeIndegrees(int N, List<List<Edge>> graph) {
        int[] indegree = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            for (Edge edge : graph.get(i)) {
                indegree[edge.to]++;
            }
        }
        return indegree;
    }

    private static List<Integer> findTopologicalOrder(int N, int[] indegree, List<List<Edge>> graph) {
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }
        List<Integer> topOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            topOrder.add(node);
            for (Edge edge : graph.get(node)) {
                indegree[edge.to]--;
                if (indegree[edge.to] == 0) {
                    queue.add(edge.to);
                }
            }
        }
        return topOrder;
    }

    // finds longest path
    private static int[] computeLongestPath(int N, List<Integer> topOrder, List<List<Edge>> graph) {
        int[] distance = new int[N + 1];
        int[] count = new int[N + 1];
        Arrays.fill(distance, Integer.MIN_VALUE);
        distance[1] = 0;
        count[1] = 1;

        for (int u : topOrder) {
            if (u == N) break;
            for (Edge edge : graph.get(u)) {
                if (distance[edge.to] < distance[u] + edge.weight) {
                    distance[edge.to] = distance[u] + edge.weight;
                    count[edge.to] = count[u] + 1;
                } else if (distance[edge.to] == distance[u] + edge.weight) {
                    count[edge.to] = Math.max(count[edge.to], count[u] + 1);
                }
            }
        }
        return new int[]{distance[N], count[N]};
    }

    private static void printResults(int[] results) {
        if (results[0] == Integer.MIN_VALUE) {
            System.out.println("-1");
        } else {
            System.out.print(results[0] + " " + results[1]);
        }
    }
}
