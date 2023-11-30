import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static final int[] TEST_CASES = new int[]{1, 1, 1};
    
    public static APSP getAPSP(String algoName) {
        if (algoName.equals("Floyd Warshall")) {
            return new FloydWarshall();
        } else if (algoName.equals("Improved Floyd Warshall"))  {
            return new ImprovedFloydWarshall();
        } else if (algoName.equals("More Improved Floyd Warshall")) {
            return new MoreImprovedFloydWarshall();
        } else if (algoName.equals("Johnson")) {
            return new Johnson();
        } else if (algoName.equals("Dijkstra |V| Times")) {
            return new DijkstraVTimes();
        }
        return null;
    }
    
    public static boolean compareSolution(int[][] dist1, int[][] dist2) {
        for (int i = 0; i < dist1.length; i++) {
            for (int j = 0; j < dist1.length; i++) {
                if (dist1[i][j] != dist2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static long test(String algoName, int[][] adjMatrix, int[][] solution) throws Exception {
        APSP algo = getAPSP(algoName);
        long startTime = System.nanoTime();
        int[][] dist = algo.run(adjMatrix);
        long endTime = System.nanoTime();
        
        if (!compareSolution(dist, solution)) {
            throw new Exception(algoName + " algorithm is not correct");
        }
        return endTime - startTime;
    }
    
    public static void main(String[] args) throws Exception {
        int category = 0;
        for (int categoryTests: TEST_CASES) {
            int cnt = 0;
            long[][] runningTime = new long[categoryTests][5];
            while (cnt < categoryTests) {
                System.out.println("Running category " + category + " on test " + cnt);
                
                String inputPath = "tests/input/" + category + "/in" + cnt + ".txt";
                Scanner scannerInput = new Scanner(new File(inputPath));
                int V = scannerInput.nextInt();
                int[][] adjMatrix = new int[V+1][V+1];
                for (int i = 0; i <= V; i++) {
                    for (int j = 0; j <= V; j++) {
                        if (i == 0 || j == 0) {
                            adjMatrix[i][j] = APSP.INF;
                        } else {
                            adjMatrix[i][j] = scannerInput.nextInt();
                        }
                    }
                }
                scannerInput.close();
                
                String outputPath = "tests/output/" + category + "/out" + cnt + ".txt";
                Scanner scannerOutput = new Scanner(new File(outputPath));
                int[][] solution = new int[V+1][V+1];
                for (int i = 0; i <= V; i++) {
                    for (int j = 0; j <= V; j++) {
                        solution[i][j] = scannerOutput.nextInt();
                    }
                }
                scannerOutput.close();
                
                runningTime[cnt] = new long[]{
                    test("Floyd Warshall", adjMatrix, solution),
                    test("Improved Floyd Warshall", adjMatrix, solution),
                    test("More Improved Floyd Warshall", adjMatrix, solution),
                    test("Johnson", adjMatrix, solution),
                    test("Dijkstra |V| Times", adjMatrix, solution),
                };
                
                cnt++;
            }
            category++;
        }
    }
}
