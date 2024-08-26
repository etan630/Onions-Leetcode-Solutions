def paths(H, W, grid):
    MOD = 10**9 + 7
    
    # create dp
    dp = [[0] * W for _ in range(H)]
    
    dp[0][0] = 1
    
    for i in range(H):
        for j in range(W):
            if grid[i][j] == '#':
                # no paths to blocked cell
                dp[i][j] = 0
            else:
                # add paths from the top cell if it exists
                if i > 0:
                    dp[i][j] += dp[i - 1][j] % MOD
                # paths from left if it exists
                if j > 0:
                    dp[i][j] += dp[i][j - 1] % MOD
                dp[i][j] %= MOD
    
    # num paths to bottom right
    return dp[H - 1][W - 1]
    

# read input
H, W = map(int, input().split())
grid = []
for _ in range(H):
    inputs = input()
    grid.append(inputs)

print(paths(H, W, grid))
