def simplex(uc, um, k):
    max_joy = 0
    curr_joy = 0
    
    for i in range(k + 1):
        for j in range(k + 1):
            if i + j <= k:
                curr_joy = i * uc + j * um # calc curr joy
                
                if (curr_joy > max_joy):
                    max_joy = curr_joy # update if curr is higher
    
    return max_joy

# reading inputs
uc = int(input())
um = int(input())
k = int(input())

print(simplex(uc, um, k))
