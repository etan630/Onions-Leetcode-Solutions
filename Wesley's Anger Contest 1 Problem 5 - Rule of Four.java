import java.io.*;
import java.util.*;

public class Main {
    // a bunch of static so i can call within diff methods
    static final int MAX_N = 50004;
    static ArrayList<Integer>[] adj =  new ArrayList[MAX_N];
    static ArrayList<Integer>[] adj2 =  new ArrayList[MAX_N];
    static BitSet[] closure = new BitSet[MAX_N];
    static BitSet force = new BitSet(MAX_N);
    static Stack<Integer> s = new  Stack<>();
    static int nums = 1;
    static int[] comp = new int[MAX_N];
    static int[] num = new int[MAX_N];
    static int[] low = new int[MAX_N];
    static boolean[] instack = new boolean[MAX_N];
    static int[] ans = new int[MAX_N];
    static boolean[] forced = new boolean[25004];
    static int comps = 0;
    static int N, M, K;
    
    // scc using targan algo
    public static void solveSCC(int v) {
        num[v] = low[v] = nums++;
        s.push(v);
        instack[v] = true;

        for (int u : adj[v]) {
            if (instack[u]) {
                low[v] = Math.min(low[v], num[u]);
            } else if (num[u] == 0) {
                solveSCC(u);
                low[v] = Math.min(low[v], low[u]);
            }
        }

        if (low[v] == num[v]) {
            while (true) {
                int u = s.pop();
                instack[u] = false;
                comp[u] = comps;
                if (u == v)
                    break;
            }

            comps++;
        }
    }
    
    // read input and init adj list
    public static void initializeAdjacencyLists(Scanner scanner, int N, int M) {
        for (int i = 0; i < MAX_N; i++) {
            adj[i] = new ArrayList<>();
            adj2[i] = new ArrayList<>();
            closure[i] = new BitSet(MAX_N);
        }

        for (int i = 0; i < K; i++) {
            int a = scanner.nextInt() - 1;
            forced[a] = true;
        }
        
        for (int i = 0; i < M; i++) {
            String s = scanner.next();
            int a = scanner.nextInt() - 1;
            int b = scanner.nextInt() - 1;
            if (s.charAt(0) == 'F') {
                adj[2 * a].add(2 * b);
                adj[2 * b].add(2 * a);
                adj[2 * a + 1].add(2 * b + 1);
                adj[2 * b + 1].add(2 * a + 1);
            } else if (s.charAt(0) == 'E') {
                adj[2 * a].add(2 * b + 1);
                adj[2 * b].add(2 * a + 1);
            } else if (s.charAt(0) == 'P') {
                adj[2 * a].add(2 * b + 1);
                adj[2 * b].add(2 * a + 1);
                adj[2 * a + 1].add(2 * b);
                adj[2 * b + 1].add(2 * a);
            } else {
                adj[2 * a + 1].add(2 * b);
                adj[2 * b + 1].add(2 * a);
            }
        }
    }
    
    // ompute transitive closure of scc
    public static void computeTransitiveClosure() {
        for (int i = 0; i < 2 * N; i++) {
            if (num[i] == 0) {
                solveSCC(i);
            }
        }

        for (int i = 0; i < 2 * N; i++) {
            for (int j : adj[i]) {
                adj2[comp[i]].add(comp[j]);
            }
        }

        for (int i = 0; i < comps; i++) {
            Collections.sort(adj2[i]);
            adj2[i] = new ArrayList<>(new LinkedHashSet<>(adj2[i]));
        }

        for (int i = 0; i < comps; i++) {
            closure[i].set(i);
            for (int j : adj2[i])
                closure[i].or(closure[j]);
        }
    }
    
    // print result
    public static void computeAndPrintResult(int N) {
        for (int i = 0; i < N; i++) {
            if (forced[i]) {
                force.or(closure[comp[2 * i]]);
            }
        }

        Arrays.fill(ans, -1);
        
        for (int i = 0; i < N; i++) {
            int a = comp[2 * i], b = comp[2 * i + 1];
            if ((force.get(a) && force.get(b)) || a == b) {
                System.out.println("NO");
                return;
            }
            if (force.get(a))
                ans[i] = 1;
            if (force.get(b))
                ans[i] = 0;
            if (closure[a].get(b))
                ans[i] = 0;
            if (closure[b].get(a))
                ans[i] = 1;
        }

        System.out.println("YES");

        for (int i = 0; i < N; i++) {
            if (ans[i] == -1)
                System.out.print("?");
            else
                System.out.print(ans[i]);
        }

        System.out.println();
    }
    
    // read input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        M = scanner.nextInt();
        K = scanner.nextInt();

        initializeAdjacencyLists(scanner, N, M);
        
        computeTransitiveClosure();
        
        computeAndPrintResult(N);
    }
}
