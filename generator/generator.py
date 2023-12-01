import math
import random
import os
import time

INF = 1000000000
random.seed(time.time())

def generate_graph_matrix(N: int, E: int):
    if E != N * (N - 1):
        adj_matrix = [[INF for _ in range(N)] for _ in range(N)]
        while E > 0:
            # print(E)
            u = random.randint(0, N-1)
            v = random.randint(0, N-1)
            w = random.randint(1, 100)
            if adj_matrix[u][v] >= INF and u != v:
                # adj_matrix[u][v] = w
                # adj_matrix[v][u] = w
                adj_matrix[u][v] = w
                E -= 1
    else:
        adj_matrix = [[random.randint(1, 100) for _ in range(N)] for _ in range(N)]
    for i in range(N):
        adj_matrix[i][i] = 0
    return adj_matrix

def generate_category(category_id: int, func):
    print(category_id)
    V_list = [100, 200, 300, 400, 500, 600, 700, 800, 900, 1024]
    V_list.extend([random.randint(20, 1024) for _ in range(20)])
    # print(V_list)
    adj_matrices = []
    for V in V_list:
        adj_matrices.append(generate_graph_matrix(V, func(V)))
    # print(len(adj_matrices))
    for idx, adj_matrix in enumerate(adj_matrices):
        # print(idx)
        output_string = f'{len(adj_matrix)}\n'
        for i in range(len(adj_matrix)):
            for j in range(len(adj_matrix)):
                output_string += str(adj_matrix[i][j])
                if j < len(adj_matrix) - 1:
                    output_string += ' '
                else:
                    output_string += '\n'
        # print('HOOO')
        file_path = f'../experiment/tests/{category_id}/in{idx}.txt'
        os.makedirs(f'../experiment/tests/{category_id}/', exist_ok=True)
        with open(file_path, 'w') as file:
            file.write(output_string)
        # print('OWOOo')

generate_category(0, (lambda V: V // 2))
generate_category(1, lambda V: V)
generate_category(2, lambda V: 2 * V)
generate_category(3, lambda V: 4 * V)
generate_category(4, lambda V: 8 * V)
generate_category(5, lambda V: 2 * V * int(math.log2(V)))
generate_category(6, lambda V: 4 * V * int(math.log2(V)))
generate_category(7, lambda V: 2 * V * V // int(math.log2(V)))
generate_category(8, lambda V: V * (V - 1))

# for i in range(12):
#     generate_category(i, lambda V: V * (V-1) // 2^(11-i))
