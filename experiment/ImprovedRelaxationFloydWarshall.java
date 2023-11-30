import java.util.ArrayList;

public class ImprovedRelaxationFloydWarshall extends APSP {
    public static int[][] improvedRelaxationFloydWarshall(int[][] adjMatrix) {
        int N = adjMatrix.length - 1;
        int[][] dist = new int[N+1][N+1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                dist[i][j] = adjMatrix[i][j];
            }
        }
        
        ArrayList<ArrayList<Integer>> in = new ArrayList<>();
        ArrayList<ArrayList<Integer>> out = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            in.add(new ArrayList<>());
            out.add(new ArrayList<>());
        }
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if ((i != j) && (dist[i][j] < INF)) {
                    in.get(j).add(i);
                    out.get(i).add(j);
                }
            }
        }
        
        for (int k = 1; k <= N; k++) {
            for (int i = 0; i < in.get(k).size(); i++) {
                for (int j = 0; j < out.get(k).size(); j++) {
                    int u = in.get(k).get(i);
                    int v = out.get(k).get(j);
                    if (dist[u][k] + dist[k][v] < dist[u][v]) {
                        if (dist[u][v] == INF) {
                            out.get(u).add(v);
                            in.get(v).add(u);
                        }
                        dist[u][v] = dist[u][k] + dist[k][v];
                    }
                }
            }
        }
        
        return dist;
    }
    
    // public static int[][] improvedRelaxationFloydWarshall(int[][] A) {
    //     int N = A.length - 1;
        
    //     int[] inc = new int[N+1];
    //     int[] outc = new int[N+1];
    //     int[][] inlist = new int[N+1][N+1];
    //     int[][] outlist = new int[N+1][N+1];
    //     int[] select_k = new int[N+1];
    //     int mininxout, mink;

    //     for (int i = 0; i < N; i++) {
    //         inc[i] = 0;
    //         outc[i] = 0;
    //         select_k[i] = 0;
    //     }

    //     for (int i = 0; i < N; i++) {
    //         for (int j = 0; j < N; j++) {
    //             if ((A[i][j] != 0) && (A[i][j] < INF)) {
    //                 inc[j]++;
    //                 outc[i]++;
    //                 inlist[j][inc[j] - 1] = i;
    //                 outlist[i][outc[i] - 1] = j;
    //             }
    //         }
    //     }

    //     for (int kk = 0; kk < N; kk++) {
    //         mink = -1;
    //         mininxout = 2 * N * N;
    //         for (int k = 0; k < N; k++) {
    //             if ((select_k[k] == 0) && (inc[k] * outc[k] < mininxout)) {
    //                 mink = k;
    //                 mininxout = inc[k] * outc[k];
    //             }
    //         }
    //         int k = mink;
    //         select_k[k] = 1;

    //         for (int i = 0; i < inc[k]; i++) {
    //             for (int j = 0; j < outc[k]; j++) {
    //                 if ((A[inlist[k][i]][k] + A[k][outlist[k][j]]) < A[inlist[k][i]][outlist[k][j]]) {
    //                     if (A[inlist[k][i]][outlist[k][j]] == INF) {
    //                         outc[inlist[k][i]]++;
    //                         outlist[inlist[k][i]][outc[inlist[k][i]] - 1] = outlist[k][j];
    //                         inc[outlist[k][j]]++;
    //                         inlist[outlist[k][j]][inc[outlist[k][j]] - 1] = inlist[k][i];
    //                     }
    //                     A[inlist[k][i]][outlist[k][j]] = A[inlist[k][i]][k] + A[k][outlist[k][j]];
    //                 }
    //             }
    //         }
    //     }

    //     return A;
    // }
    
    @Override
    public int[][] run(int[][] adjMatrix) {
        return improvedRelaxationFloydWarshall(adjMatrix);
    }
}