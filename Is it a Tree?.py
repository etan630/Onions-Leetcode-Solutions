def isTree(tree):
    n = len(tree)
    num_edges = 0
    
    # count edges
    for i in range(n):
        for j in range(i, n):
            if tree[i][j] != tree[j][i]:
                return "No"
            if i != j and tree[i][j] == 1:
                num_edges += 1
    # prop of tree i think? 
    if num_edges != n - 1:
        return "No"
    
    # BFS :DD ???? might not be best sol??? idk...
    visited = [False] * n
    queue = [0]
    visited[0] = True
    parent = [-1] * n
    
    while queue:
        u = queue.pop(0)
        for v in range(n):
            if tree[u][v] == 1:
                if not visited[v]:
                    visited[v] = True
                    parent[v] = u
                    queue.append(v)
                elif parent[u] != v:
                    return "No"
    
    #graph connected??
    if not all(visited):
        return "No"
    
    return "Yes"
    

# getting tree
results = []
for x in range(4):
    a, b, c, d = map(int, input().split())
    results.append((a, b, c, d))

print(isTree(results))
