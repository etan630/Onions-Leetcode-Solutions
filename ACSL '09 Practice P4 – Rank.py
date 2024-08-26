from collections import defaultdict

def build_graph(results):
    graph = defaultdict(list)
    
    # graph based on game results
    for a, b, s_a, s_b in results:
        if s_a > s_b:
            graph[a].append(b)
        elif s_b > s_a:
            graph[b].append(a)
    
    return graph

def detect_cycles(graph, N):
    visited = [False] * (N + 1)
    rec_stack = [False] * (N + 1)
    cycle_nodes = set()
    
    def dfs(v):
        visited[v] = True
        rec_stack[v] = True
        has_cycle = False

        # check neighbors of current node
        for neighbor in graph[v]:
            if not visited[neighbor]:
                if dfs(neighbor):
                    has_cycle = True
                    cycle_nodes.add(neighbor)
            elif rec_stack[neighbor]:
                has_cycle = True
                cycle_nodes.add(neighbor)

        rec_stack[v] = False
        if has_cycle:
            cycle_nodes.add(v)
        return v in cycle_nodes
    
    for node in range(1, N + 1):
        if not visited[node]:
            dfs(node)
    return len(cycle_nodes)

def numPlayers(N, K, results):
    graph = build_graph(results)
    return detect_cycles(graph, N)

# N = num players K = num games played
N, K = map(int, input().split())
results = []
for x in range(K):
    a, b, s_a, s_b = map(int, input().split())
    results.append((a, b, s_a, s_b))

print(numPlayers(N, K, results))
