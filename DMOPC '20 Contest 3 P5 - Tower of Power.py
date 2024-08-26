def mod_mult(a, b, mod):
    result = a * b
    if result >= mod:
        result = (result % mod) + mod
    return result

# (base ^ exp) % mod
def mod_exp(base, exponent, mod):
    if exponent == 0:
        return 1
    if exponent % 2 == 0:
        temp = mod_exp(base, exponent // 2, mod)
        return mod_mult(temp, temp, mod)
    temp = mod_exp(base, exponent - 1, mod)
    return mod_mult(temp, base, mod)

# counts # of pos int <= n that are rel prime
def euler(n):
    result = n
    p = 2
    while p * p <= n:
        if n % p == 0:
            while n % p == 0:
                n //= p
            result -= result // p
        p += 1
    if n > 1:
        result -= result // n
    return result

def tower_power(blocks, N, M):
    moduli = [None] * N
    moduli[0] = M
    temp = M
    for i in range(1, N):
        temp = euler(temp)
        moduli[i] = temp
    result = blocks[-1]
    for i in range(N - 2, -1, -1):
        result = mod_exp(blocks[i], result, moduli[i])
    
    print(result % M)

# read iputs
N, M = map(int, input().split())
blocks = list(map(int, input().split()))

tower_power(blocks, N, M)
