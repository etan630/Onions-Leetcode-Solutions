def largest_square(n, grid):
    if n == 0:
        return 0
    
    # init dp
    dp = [[0] * n for _ in range(n)]
    max_square = 0
    count_max_square = 0
    
    #fill dp
    for i in range(n):
        for j in range(n):
            if grid[i][j] == 1:
                if i == 0 or j == 0:
                    dp[i][j] = 1
                else:
                    dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j- 1]) + 1
                
                if dp[i][j] > max_square:
                    max_square = dp[i][j]
                    count_max_square = 1
                elif dp[i][j] == max_square:
                    count_max_square += 1
    
    return max_square * count_max_square

# reading inputs
n = int(input())
grid = []
for _ in range(n):
    inputs = input().split()
    grid.append([int(num) for num in inputs])
    
print(largest_square(n, grid))

# dp notes
# cell has 0 then dp[i][j] will be 0 -- cant end valid square at a defective cell
# cell has 1 then dp is min of dp[i-1][j], dp[i][j - 1], dp[i - 1][j - 1] + 1
# this will rep largest square wos bottom right corner is i, j
