import java.io.*;
import java.util.*;

public class Main {
    // stores results
    private static Map<Integer, Integer> first = new HashMap<>();
    private static Map<Integer, Integer> second = new HashMap<>();
    // size of problem
    private static int N;

    // read input and run code
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();

        runSimulation(scanner);
    }

    private static void runSimulation(Scanner scanner) {
        Random random = new Random();

        int res1, res2; // stores results
        int j = random.nextInt(N) + 1;
        int q = random.nextInt(N) + 1;

        for (int i = 0; i < 10000; i++) {
            outputInteraction(j, q);

            res1 = scanner.nextInt();
            res2 = scanner.nextInt();

            updateResults(res1, res2, j, q);
            
            // results same terminate
            if (res1 == res2) {
                return;
            }

            if (checkForSolution()) {
                return;
            }

            j = random.nextInt(N) + 1;
            q = random.nextInt(N) + 1;
        }
    }

    private static void outputInteraction(int j, int q) {
        System.out.println(j + " " + q);
        System.out.flush();
    }

    private static void updateResults(int res1, int res2, int j, int q) {
        first.put(res1, j);
        second.put(res2, q);
    }

    private static boolean checkForSolution() {
        for (Map.Entry<Integer, Integer> z : first.entrySet()) {
            for (Map.Entry<Integer, Integer> t : second.entrySet()) {
                // if same print indices
                if (z.getKey().equals(t.getKey())) {
                    System.out.println(z.getValue() + " " + t.getValue());
                    return true;
                }
            }
        }
        // no solution found
        return false;
    }
}
