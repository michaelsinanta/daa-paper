import java.util.ArrayList;
import java.util.PriorityQueue;

class EdgeListPair {
    public int u;
    public int v;
    public int w;
    
    public EdgeListPair(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}

class AdjListPair {
    public int v;
    public int w;
    
    public AdjListPair(int v, int w) {
        this.v = v;
        this.w = w;
    }
}

class DijkstraPair implements Comparable<DijkstraPair> {
    public int u;
    public int dist;
    
    public DijkstraPair(int u, int dist) {
        this.u = u;
        this.dist = dist;
    }
    
    public int compareTo(DijkstraPair other) {
        return this.dist - other.dist;
    }
}

public class Johnson extends APSP {
    private int[] bellmanFord(int V, ArrayList<EdgeListPair> edgeList, int start) {
        int[] dist = new int[V+1];
        for (int i = 0; i < V+1; i++) {
            dist[i] = INF;
        }
        dist[start] = 0;
        for (int i = 1; i < V; i++) {
            for (EdgeListPair edge: edgeList) {
                if (dist[edge.v] > dist[edge.u] + edge.w) {
                    dist[edge.v] = dist[edge.u] + edge.w;
                }
            }
        }
        return dist;
    }
    
    private boolean hasNegativeCycle(int V, ArrayList<EdgeListPair> edgeList, int[] dist) {
        for (EdgeListPair edge: edgeList) {
            if (dist[edge.v] > dist[edge.u] + edge.w) {
                return true;
            }
        }
        return false;
    }
    
    // TODO: inf set to 10^9 then pastiin gaada yang overflow
    private int[] dijkstra(ArrayList<ArrayList<AdjListPair>> adjList, int start) {
        int V = adjList.size() - 1;
        int[] dist = new int[V+1];
        for (int i = 0; i <= V; i++) {
            dist[i] = INF;
        }
        boolean[] visited = new boolean[V+1];
        PriorityQueue<DijkstraPair> pq = new PriorityQueue<>();
        pq.add(new DijkstraPair(start, 0));
        dist[start] = 0;
        while(pq.size() > 0) {
            DijkstraPair cur = pq.remove();
            if (!visited[cur.u]) {
                visited[cur.u] = true;
                for (AdjListPair pair: adjList.get(cur.u)) {
                    if (dist[cur.u] + pair.w < dist[pair.v]) {
                        dist[pair.v] = dist[cur.u] + pair.w;
                        pq.add(new DijkstraPair(pair.v, dist[pair.v]));
                    }
                }
            }
        }
        return dist;
    }
    
    public int[][] johnson(int[][] adjMatrix) {
        // Menambahkan vertex s
        int N = adjMatrix.length;
        int V = N-1;
        for (int i = 0; i < N; i++) {
            adjMatrix[0][i] = 0;
        }
        
        // Konversi adjacency matrix ke edge list untuk Bellman-Ford
        ArrayList<EdgeListPair> edgeList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j && adjMatrix[i][j] < INF) {
                    edgeList.add(new EdgeListPair(i, j, adjMatrix[i][j]));
                }
            }
        }
        
        // Reweighting
        int[] h = bellmanFord(V, edgeList, 0);
        if (hasNegativeCycle(V, edgeList, h)) {
            return null;
        }
        ArrayList<ArrayList<AdjListPair>> adjList = new ArrayList<>();
        adjList.add(null);
        for (int i = 1; i < N; i++) {
            adjList.add(new ArrayList<>());
            for (int j = 1; j < N; j++) {
                if (i != j && adjMatrix[i][j] < INF) {
                    adjList.get(i).add(new AdjListPair(j, adjMatrix[i][j] + h[i] - h[j]));
                }
            }
        }
        
        // Pencarian Shortest Path
        int[][] dist = new int[N][];
        for (int u = 1; u < N; u++) {
            dist[u] = dijkstra(adjList, u);
            for (int v = 1; v < N; v++) {
                if (dist[u][v] < INF) {
                    dist[u][v] = dist[u][v] + h[v] - h[u];
                }
            }
        }
        
        return dist;
    }
    
    @Override
    public int[][] run(int[][] adjMatrix) {
        return johnson(adjMatrix);
    }
}