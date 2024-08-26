import math

def calculate_min_vertices(M):
    # initial estimate using the quadratic formula
    V = (1 + math.sqrt(1 + 8 * M)) / 2
    minimum = math.ceil(V)
    
    while minimum * (minimum - 1) // 2 < M:
        minimum += 1
    
    return minimum

# returns of minimum vertices for each test case
def min_vertex(T, edges):
    output = []
    for M in edges:
        output.append(calculate_min_vertices(M))
    return output

def read_input_and_process():
    T = int(input())
    edges = []
    for _ in range(T):
        edge = int(input())
        edges.append(edge)
    
    result = min_vertex(T, edges)
    for res in result:
        print(res)

# run program
read_input_and_process()
