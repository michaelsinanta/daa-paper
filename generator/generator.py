import math
import random
import os
import time

INF = 1000000000
random.seed(time.time())

def generate_graph_matrix(N: int, E: int):
    adj_matrix = [[INF for _ in range(N)] for _ in range(N)]
    while E > 0:
        u = random.randint(0, N-1)
        v = random.randint(0, N-1)
        w = random.randint(1, 100)
        if adj_matrix[u][v] >= INF and u != v:
            adj_matrix[u][v] = w
            adj_matrix[v][u] = w
            E -= 1
    for i in range(N):
        adj_matrix[i][i] = 0
    return adj_matrix

def generate_category(category_id: int, func):
    V_list = [20, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1024]
    adj_matrices = []
    for V in V_list:
        adj_matrices.append(generate_graph_matrix(V, func(V)))
    for idx, adj_matrix in enumerate(adj_matrices):
        output_string = f'{len(adj_matrix)}\n'
        for i in range(len(adj_matrix)):
            for j in range(len(adj_matrix)):
                output_string += str(adj_matrix[i][j])
                if j < len(adj_matrix) - 1:
                    output_string += ' '
                else:
                    output_string += '\n'
        file_path = f'tests/{category_id}/in{idx}.txt'
        os.makedirs(f'tests/{category_id}/', exist_ok=True)
        with open(file_path, 'w') as file:
            file.write(output_string)

generate_category(0, (lambda V: V // 4))
generate_category(1, lambda V: V // 2)
generate_category(2, lambda V: V)
generate_category(3, lambda V: 2 * V)
generate_category(4, lambda V: 4 * V)
generate_category(5, lambda V: V * int(math.log2(V)))
generate_category(6, lambda V: 2 * V * int(math.log2(V)))
generate_category(7, lambda V: V * V // int(math.log2(V)))
generate_category(8, lambda V: V * (V - 1) // 2)

# def save_matrices_to_files(adj_matrices, size, category):
#     folder_name = f"algos/{category}/input"
#     os.makedirs(folder_name, exist_ok=True)

#     for idx, adj_matrix in enumerate(adj_matrices):
#         output_string = f'{size}\n'
#         for i in range(size):
#             for j in range(size):
#                 output_string += str(adj_matrix[i][j])
#                 if j < size - 1:
#                     output_string += ' '
#                 else:
#                     output_string += '\n'

#         file_path = os.path.join(folder_name, f'in{idx}.txt')
#         with open(file_path, 'w') as file:
#             file.write(output_string)

# for category, size in enumerate(vrtx):
#     adj_matrices = [
#         generate_graph_matrix(size, size // 4),
#         generate_graph_matrix(size, size // 2),
#         generate_graph_matrix(size, size),
#         generate_graph_matrix(size, 2 * size),
#         generate_graph_matrix(size, 4 * size),
#         generate_graph_matrix(size, int(math.log2(size) * size)),
#         generate_graph_matrix(size, int(2 * size * math.log2(size))),
#         generate_graph_matrix(size, int(size * size // math.log2(size))),
#         generate_graph_matrix(size, size * (size - 1) // 2)
#     ]
#     save_matrices_to_files(adj_matrices, size, category)
