# swaps 2 rows in a matrix
def swap(matrix, row1, row2, n):
    for i in range(n):
        matrix[row1][i], matrix[row2][i] = matrix[row2][i], matrix[row1][i]
    
# gaussian elimination on matrix inputs & solve linear system
def elimination(inputs):
    m = len(inputs)
    n = len(inputs[0]) - 1 # last col is constant
    
    # check if zero coefficient
    zero = True
    
    # forward elimin
    for i in range(m):
        max_row = i # piv row
        
        for k in range(i + 1, m):
            if abs(inputs[k][i]) > abs(inputs[max_row][i]):
                max_row = k
        
        # swap w max
        inputs[i], inputs[max_row] = inputs[max_row], inputs[i]
        
        # check no sol
        if all(inputs[i][j] == 0 for j in range(n)):
            if inputs[i][n] != 0:
                return "NO UNIQUE SOLUTION"
        else:
            zero = False
        
        #  check for zero coeff @ piv
        if abs(inputs[i][i]) < 1e-9 and zero:
            return "NO UNIQUE SOLUTION"
        
        # eliminate below
        for k in range(i + 1, m):
            factor = inputs[k][i] / inputs[i][i]
            for j in range(i, n + 1):
                inputs[k][j] -= factor * inputs[i][j]
    
    # back substitution 
    result = [0] * m
    for i in range(m - 1, -1, -1):
        if abs(inputs[i][i]) < 1e-9 and zero:
            return "NO UNIQUE SOLUTION"
        result[i] = inputs[i][n]
        for j in range(i + 1, m):
            result[i] -= inputs[i][j] * result[j]
        
        if inputs[i][i] == 0 and n + 1 != m:
            return "NO UNIQUE SOLUTION"
        else:
            if inputs[i][i] == 0:
                continue
            result[i] /= inputs[i][i]
    
    return result

# calc rank of matrix -- use gaussian & count # of nonzero rows
def rank(inputs):
    m = len(inputs)
    n = len(inputs[0])
    
    for row in range(min(m, n)):
        if inputs[row][row] != 0:
            # elimin all other rows
            for col in range(m):
                if col != row:
                    mult = inputs[col][row] / inputs[row][row]
                    for i in range(n):
                        inputs[col][i] -= mult * inputs[row][i]
        else:
            # handle no piv in curr row
            reduced = True
            for i in range(row + 1, m):
                if inputs[i][row] != 0:
                    swap(inputs, row, i, n)
                    reduced = False
                    break
            if reduced:
                n -= 1
                for i in range(m):
                    inputs[i][row] = inputs[i][n]
            row -= 1
    rank = 0
    for row in inputs:
        if any(row):
            rank += 1
    return rank

def check(n, m, inputs):
    # check matrix for cond that has unique sol
    matrix_co = [coeff[:n] for coeff in inputs]
    matrix_aug = inputs
    
    if m > n:
        if rank(matrix_co) == n:
            return None
        else:
            return "NO UNIQUE SOLUTION"
    if m < n:
        return "NO UNIQUE SOLUTION"
    else:
        return None

def solve(n, m, inputs):
    unique_check = check(n, m, inputs)
    if unique_check:
        return unique_check
        
    result = elimination(inputs)
    
    if isinstance(result, str):
        return result
    else:
        return ["{:.5f}".format(val) for val in result[:n]]
    

# read inputs
n, m = map(int, input().split())
inputs = []
for _ in range(m):
    data = list(map(int, input().split()))
    if len(data) != n + 1:
        break
    inputs.append(data)

result = solve(n, m, inputs)

if isinstance(result, str):
    print(result)
else:
    for val in result:
        print(val)
