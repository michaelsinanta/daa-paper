import random, time

INF = 9999

random.seed(time.time())

def generate_graph_edge_list(N: int, E: int):
    adj_matrix = [ ([False for j in range(N+1)]) for i in range(N+1) ]
    edge_list = []
    while E > 0:
        u = random.randint(1, N)
        v = random.randint(1, N)
        if not adj_matrix[u][v] and u != v:
            edge_list.append((u, v))
            adj_matrix[u][v] = True
            adj_matrix[v][u] = True
            E -= 1
    return edge_list

def generate_graph_matrix(N: int, E: int):
    adj_matrix = [ ([INF for j in range(N+1)]) for i in range(N+1) ]
    while E > 0:
        u = random.randint(1, N)
        v = random.randint(1, N)
        w = random.randint(1, 100)
        if adj_matrix[u][v] >= INF and u != v:
            adj_matrix[u][v] = w
            adj_matrix[v][u] = w
            E -= 1
    return adj_matrix

# edge_list = generate_graph_matrix(1024, 10)

# output_string = ''
# for a, b in edge_list:
#     output_string += f'{a} {b}\n'

# with open('output/out1.txt', 'w') as file:
#     file.write(output_string)

adj_matrix =  generate_graph_matrix(1024, 10)

output_string = f'{1024}\n'
for i in range(1024):
    for j in range(1024):
        output_string += str(adj_matrix[i][j])
        if j < 1023:
            output_string += ' '
        else:
            output_string += '\n'

with open('output/out1.txt', 'w') as file:
    file.write(output_string)