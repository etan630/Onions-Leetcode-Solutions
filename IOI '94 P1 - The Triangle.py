#triangle[i][j]  reps max path sum from top of traingle to element at pos i, j
# triangle i j <= triangle i j + max(triangle i + 1, j ; triangle [i + 2, j + 1])
#       start w curr val then take below left and below right to find max
# base case: last row of triangle
def max_sum(triangle, N):
    for i in range(N - 2, -1, -1):
        for j in range(i + 1):
            triangle[i][j] += max(triangle[i + 1][j], triangle[i + 1][j+1])
            
    return triangle[0][0]

# reading inputs
N = int(input().strip())
triangle = []
for _ in range(N):
    row = list(map(int, input().strip().split()))
    triangle.append(row)
    
print(max_sum(triangle, N))
