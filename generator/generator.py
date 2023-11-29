import math
import random
import os
import time

INF = 9999
random.seed(time.time())

def generate_graph_matrix(N: int, E: int):
    adj_matrix = [[INF for _ in range(N + 1)] for _ in range(N + 1)]
    while E > 0:
        u = random.randint(1, N)
        v = random.randint(1, N)
        w = random.randint(1, 100)
        if adj_matrix[u][v] >= INF and u != v:
            adj_matrix[u][v] = w
            adj_matrix[v][u] = w
            E -= 1
    return adj_matrix

vrtx = [20, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1024]

def save_matrices_to_files(adj_matrices, size, category):
    folder_name = f"algos/{category}/input"
    os.makedirs(folder_name, exist_ok=True)

    for idx, adj_matrix in enumerate(adj_matrices):
        output_string = f'{size}\n'
        for i in range(size):
            for j in range(size):
                output_string += str(adj_matrix[i][j])
                if j < size - 1:
                    output_string += ' '
                else:
                    output_string += '\n'

        file_path = os.path.join(folder_name, f'in{idx}.txt')
        with open(file_path, 'w') as file:
            file.write(output_string)

for category, size in enumerate(vrtx):
    adj_matrices = [
        generate_graph_matrix(size, size // 4),
        generate_graph_matrix(size, size // 2),
        generate_graph_matrix(size, size),
        generate_graph_matrix(size, 2 * size),
        generate_graph_matrix(size, 4 * size),
        generate_graph_matrix(size, int(math.log2(size) * size)),
        generate_graph_matrix(size, int(2 * size * math.log2(size))),
        generate_graph_matrix(size, int(size * size // math.log2(size))),
        generate_graph_matrix(size, size * (size - 1) // 2)
    ]
    save_matrices_to_files(adj_matrices, size, category)
