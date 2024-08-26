def min_cost(N, h):
    # n small
    if N == 2:
        return abs(h[1] - h[0])
    
    # init dp
    dp = [0] * N
    dp[0] = 0
    dp[1] = abs(h[1] - h[0])
    
    # compute dp
    for i in range(2, N):
        i_minus_1 = dp[i - 1] + abs(h[i] - h[i - 1])
        i_minus_2 = dp[i - 2] + abs(h[i] - h[i - 2])
        dp[i] = min(i_minus_1, i_minus_2)
    
    return dp[-1]

# reading inputs
N = int(input().strip())
h = list(map(int, input().strip().split()))

print(min_cost(N, h))
