# model after 2sat problem 
def illuminate(n, r, l, positions):
    from collections import defaultdict, deque
    
    #map positions
    row_map = defaultdict(list)
    column_map = defaultdict(list)
    
    # set maps to row and col along w index
    for index, (row, col) in enumerate(positions):
        row_map[row].append((col, index))
        column_map[col].append((row, index))
    
    # graph for 2sat -- graph is implication inverse is transpose
    graph = defaultdict(list)
    inverse_graph = defaultdict(list)
    
    # 2sat stuff -- x is true not y must be false
    def add_implication(x, not_y):
        graph[x].append(not_y)
        graph[not_y ^ 1].append(x ^ 1)
        inverse_graph[not_y].append(x)
        inverse_graph[x ^ 1].append(not_y ^ 1)
    
    #create implication based on on overlaps
    for key, lamps in row_map.items():
        lamps.sort()
        for i in range(len(lamps) - 1):
            for j in range(i + 1, len(lamps)):
                # check if lamps could overlap
                if lamps[j][0] - lamps[i][0] <= 2 * r:
                    idx1 = lamps[i][1]
                    idx2 = lamps[j][1]
                    # if lamp 1 lights row then 2 must light column
                    add_implication(2*idx1, 2*idx2 + 1)
                    add_implication(2*idx2, 2*idx1 + 1)
    
    # check overlap in same col
    for key, lamps in column_map.items():
        lamps.sort()
        for i in range(len(lamps) - 1):
            for j in range(i + 1, len(lamps)):
                if lamps[j][0] - lamps[i][0] <= 2 * r:
                    idx1 = lamps[i][1]
                    idx2 = lamps[j][1]
                    # If lamp 1 lights column then 2 must light row
                    add_implication(2*idx1 + 1, 2*idx2)
                    add_implication(2*idx2 + 1, 2*idx1)

    # find scc --order vertices by finish times
    order = []
    visited = [False] * 2 * l
    # revised dfs call to be iterative w stack to avoid deep recursion
    def dfs(v):
        stack = [v]
        while stack:
            node = stack.pop()
            if not visited[node]:
                visited[node] = True
                order.append(node)
                for to in graph[node]:
                    if not visited[to]:
                        stack.append(to)
    
    for i in range(2 * l):
        if not visited[i]:
            dfs(i)
            
    # transpose graph
    component = [-1] * (2 * l)
    current_component = 0
    def reverse_dfs(v):
        stack = [v]
        while stack:
            node = stack.pop()
            if component[node] == -1:
                component[node] = current_component
                for to in inverse_graph[node]:
                    if component[to] == -1:
                        stack.append(to)
    
    visited = [False] * 2 * l
    order.reverse()
    for v in order:
        if component[v] == -1:
            reverse_dfs(v)
            current_component += 1
    
    # check var and neg are in same component
    for i in range(l):
        if component[2 * i] == component[2 * i + 1]:
            return "NO"
    
    return "YES"


#reading input
n, r, l = map(int, input().split())
positions = []
for x in range(l):
    row, col = map(int, input().split())
    positions.append((row, col))
    
print(illuminate(n, r, l, positions))
