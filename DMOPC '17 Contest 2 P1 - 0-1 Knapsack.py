def knapsack(inputs):
    pos = []
    for c, v in inputs:
        if v > 0:
            pos.append((c, v))
    
    # get min size
    min_size = 0
    for c, _ in pos:
        min_size += c
    
    return min_size

# reading inputs 
N = int(input())
inputs = []
for _ in range(N):
    c, v = map(int, input().split())
    inputs.append((c, v))

print(knapsack(inputs))
