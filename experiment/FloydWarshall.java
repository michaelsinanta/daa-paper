public class FloydWarshall extends APSP {
    private static int[][] floydWarshall(int[][] adjMatrix) {
        int N = adjMatrix.length - 1;
        int[][] dist = new int[N+1][N+1];
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                dist[i][j] = adjMatrix[i][j];
            }
        }
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        return dist;
    }
    
    @Override
    public int[][] run(int[][] adjMatrix) {
        return floydWarshall(adjMatrix);
    }
}
