import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FloydWarshall extends APSP {
    final static int INF = 1000000000;
    final static int MAXN = 1025;
    
    private static int[][] floydWarshall(int N, int[][] graph) {
        int[][] dist = new int[MAXN][MAXN];

        // Initialize the solution matrix same as input graph matrix.
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                dist[i][j] = graph[i][j];

        // Adding vertices individually to the set of intermediate vertices.
        for (int k = 0; k < N; k++) {
            // Pick all vertices as source one by one
            for (int i = 0; i < N; i++) {
                // Pick all vertices as destination for the above picked source
                for (int j = 0; j < N; j++) {
                    // If vertex k is on the shortest path from i to j,
                    // then update the value of dist[i][j]
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }

        return dist;
    }

    private static void printSolution(int[][] dist, int N) {
        System.out.println("NORMAL FW");
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (dist[i][j] != INF)
                    System.out.print(dist[i][j] + " ");
                else
                    System.out.print("X ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        int N = input.nextInt();
        int[][] A = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = input.nextInt();
            }
        }

        long startTime = System.nanoTime(); // Mark the start time
        int[][] result = floydWarshall(N, A);
        long endTime = System.nanoTime(); // Mark the end time

        // Print the shortest distance matrix
        printSolution(result, N);
        long executionTime = endTime - startTime; // Calculate the execution time
        System.out.println("Execution Time: " + executionTime + " nano seconds");
        
        input.close();
    }
    
    @Override
    public int[][] run(int[][] adjMatrix) {
        return null;
    }
}
