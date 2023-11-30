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
            for (int i = 1; i <= in.get(k).size(); i++) {
                for (int j = 1; j <= out.get(k).size(); j++) {
                    int u = in.get(k).get(i);
                    int v = out.get(k).get(j);
                    if (dist[u][k] + dist[k][v] < dist[u][v]) {
                        if (dist[u][v] == INF) {
                            out.get(u).add(v);
                            in.get(v).add(u);
                        }
                    }
                    dist[u][v] = dist[u][k] + dist[k][v];
                }
            }
        }
        
        return dist;
    }
    
    @Override
    public int[][] run(int[][] adjMatrix) {
        return improvedRelaxationFloydWarshall(adjMatrix);
    }
}