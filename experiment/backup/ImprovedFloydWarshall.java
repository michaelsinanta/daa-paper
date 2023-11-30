public class ImprovedFloydWarshall extends APSP {
    public static int[][] improvedFloydWarshall(int[][] A) {
        int N = A.length - 1;
        
        int[] inc = new int[N+1];
        int[] outc = new int[N+1];
        int[][] inlist = new int[N+1][N+1];
        int[][] outlist = new int[N+1][N+1];
        int[] select_k = new int[N+1];
        int mininxout, mink;

        for (int i = 0; i < N; i++) {
            inc[i] = 0;
            outc[i] = 0;
            select_k[i] = 0;
        }

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

        for (int kk = 0; kk < N; kk++) {
            mink = -1;
            mininxout = 2 * N * N;
            for (int k = 0; k < N; k++) {
                if ((select_k[k] == 0) && (inc[k] * outc[k] < mininxout)) {
                    mink = k;
                    mininxout = inc[k] * outc[k];
                }
            }
            int k = mink;
            select_k[k] = 1;

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
    
    @Override
    public int[][] run(int[][] adjMatrix) {
        return improvedFloydWarshall(adjMatrix);
    }
}