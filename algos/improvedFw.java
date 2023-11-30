import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class improvedFw{
    public static final int MAXN = 1025;
    public static final int INF = 9999;

    public static int[][] new_fw(int N, int[][] A) {
        int[] inc = new int[MAXN]; int[] outc = new int[MAXN];
        int[][] inlist = new int[MAXN][MAXN]; int[][] outlist = new int[MAXN][MAXN];
        int[] select_k = new int[MAXN];
        int mininxout, mink;

        for (int i = 0; i < N; i++) {
            inc[i] = 0;
            outc[i] = 0;
            select_k[i] = 0;
        }

        // Generate initial inlist and outlist for each vertex
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((A[i][j] != 0) && (A[i][j] < INF)) {
                    inc[j]++;
                    outc[i]++;
                    inlist[j][inc[j] - 1] = i;
                    outlist[i][outc[i] - 1] = j;
                }
            }
        }

        // The outer loop
        for (int kk = 0; kk < N; kk++) {
            mink = -1;
            mininxout = 2 * N * N;
            // Choost the "best" k
            for (int k = 0; k < N; k++) {
                if ((select_k[k] == 0) && (inc[k] * outc[k] < mininxout)) {
                    mink = k;
                    mininxout = inc[k] * outc[k];
                }
            }
            int k = mink; // "best" k
            select_k[k] = 1; // remove selected vertex

            // explore only useful relaxation attempts
            for (int i = 0; i < inc[k]; i++) {
                for (int j = 0; j < outc[k]; j++) {
                    if ((A[inlist[k][i]][k] + A[k][outlist[k][j]]) < A[inlist[k][i]][outlist[k][j]]) {
                        if (A[inlist[k][i]][outlist[k][j]] == INF) {
                            outc[inlist[k][i]]++;
                            outlist[inlist[k][i]][outc[inlist[k][i]] - 1] = outlist[k][j];
                            inc[outlist[k][j]]++;
                            inlist[outlist[k][j]][inc[outlist[k][j]] - 1] = inlist[k][i];
                        }
                        A[inlist[k][i]][outlist[k][j]] = A[inlist[k][i]][k] + A[k][outlist[k][j]];
                    }
                }
            }
        }

        return A;
    }

    public static void main(String[] args) {
        // Modify this line to specify the path to your input file
        String filePath = "out1.txt";

        File file = new File(filePath);
        FileInputStream inputStream;
        Scanner scanner = null;
        try {
            inputStream = new FileInputStream(file);
            scanner = new Scanner(inputStream);
            
            int N = scanner.nextInt();
            int[][] A = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    A[i][j] = scanner.nextInt();
                }
            }

            long startTime = System.nanoTime(); // Mark the start time
            int[][] result = new_fw(N, A);
            long endTime = System.nanoTime(); // Mark the end time

            // System.out.println("Improved FW APSP");
            // for (int i = 0; i < N; i++) {
            //     for (int j = 0; j < N; j++) {
            //         if (result[i][j] != INF) {
            //             System.out.print(result[i][j] + " ");
            //         } else {
            //             System.out.print("X ");
            //         }
            //     }
            //     System.out.println();
            // }
            long executionTime = endTime - startTime; // Calculate the execution time

            System.out.println("Execution Time: " + executionTime + " nano seconds");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}