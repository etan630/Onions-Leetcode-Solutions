# use bfs to find moves from one position before moving on
def minMoves(x1, y1, x2, y2):
    from collections import deque
    # get moves knight can go from curr pos
    moves = [(2, 1), (2, -1), (-2, 1), (-2, -1), (1, 2), (1, -2), (-1, 2), (-1, -2)]
    queue = deque([(x1, y1, 0)])
    visited = set()
    visited.add((x1, y1))
    
    while queue:
        currX, currY, currDist = queue.popleft()
        if currX == x2 and currY == y2:
            return currDist
        for dx, dy in moves:
            nx, ny = currX + dx, currY + dy
            # test if knight in board
            if 1 <= nx <= 8 and 1 <= ny <= 8 and (nx, ny) not in visited:
                visited.add((nx, ny))
                queue.append((nx, ny, currDist + 1))
    return -1
    
startX, startY = map(int, input().split())
endX, endY = map(int, input().split())

result = minMoves(startX, startY, endX, endY)

print(result)
