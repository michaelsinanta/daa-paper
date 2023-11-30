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

public class DijkstraVTimes extends APSP {
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
    
    public int[][] dijkstraVTimes(int[][] adjMatrix) {
        // Menambahkan vertex s
        int V = adjMatrix.length - 1;
        
        ArrayList<ArrayList<AdjListPair>> adjList = new ArrayList<>();
        adjList.add(null);
        for (int i = 1; i <= V; i++) {
            adjList.add(new ArrayList<>());
            for (int j = 1; j <= V; j++) {
                if (i != j && adjMatrix[i][j] < INF) {
                    adjList.get(i).add(new AdjListPair(j, adjMatrix[i][j]));
                }
            }
        }
        
        // Pencarian Shortest Path
        int[][] dist = new int[V+1][];
        for (int u = 1; u <= V; u++) {
            dist[u] = dijkstra(adjList, u);
        }
        
        return dist;
    }
    
    @Override
    public int[][] run(int[][] adjMatrix) {
        return dijkstraVTimes(adjMatrix);
    }
}