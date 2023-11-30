import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    private static final int[] TEST_CASES = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10};
    
    public static APSP getAPSP(String algoName) {
        if (algoName.equals("Floyd Warshall")) {
            return new FloydWarshall();
        } else if (algoName.equals("Improved Relaxation Floyd Warshall"))  {
            return new ImprovedRelaxationFloydWarshall();
        } else if (algoName.equals("Improved Selection Floyd Warshall")) {
            return new ImprovedSelectionFloydWarshall();
        } else if (algoName.equals("Johnson")) {
            return new Johnson();
        } else if (algoName.equals("Dijkstra |V| Times")) {
            return new DijkstraVTimes();
        }
        return null;
    }
    
    public static boolean compareSolution(int[][] dist1, int[][] dist2) {
        for (int i = 1; i < dist1.length; i++) {
            for (int j = 1; j < dist1.length; j++) {
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
            String logFile = "log.txt";
            FileWriter writer = new FileWriter(logFile);
            for (int i = 1; i < solution.length; i++) {
                for (int j = 1; j < solution.length; j++) {
                    writer.append(solution[i][j] + " ");
                }
                writer.append("\n");
            }
            writer.append("\n");
            for (int i = 1; i < dist.length; i++) {
                for (int j = 1; j < dist.length; j++) {
                    writer.append(dist[i][j] + " ");
                }
                writer.append("\n");
            }
            writer.close();
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
                
                String inputPath = "tests/" + category + "/in" + cnt + ".txt";
                // String inputPath = "tests/" + category + "/in" + ".txt";
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
                
                int[][] solution = new int[V+1][V+1];
                solution = getAPSP("Johnson").run(adjMatrix);
                
                // String outputPath = "tests/output/" + category + "/out" + cnt + ".txt";
                // Scanner scannerOutput = new Scanner(new File(outputPath));
                // int[][] solution = new int[V+1][V+1];
                // for (int i = 0; i <= V; i++) {
                //     for (int j = 0; j <= V; j++) {
                //         solution[i][j] = scannerOutput.nextInt();
                //     }
                // }
                // scannerOutput.close();
                
                runningTime[cnt] = new long[]{
                    test("Floyd Warshall", adjMatrix, solution),
                    test("Improved Relaxation Floyd Warshall", adjMatrix, solution),
                    test("Improved Selection Floyd Warshall", adjMatrix, solution),
                    test("Johnson", adjMatrix, solution),
                    test("Dijkstra |V| Times", adjMatrix, solution),
                };
                cnt++;
            }
            
            String outputPath = "result/" + category + ".csv";
            File file = new File(outputPath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.append("\"Floyd Warshall\",\"Improved Relaxation Floyd Warshall\",\"Improved Selection Floyd Warshall\",\"Johnson\",\"Dijkstra |V| Times\"\n");
            for (int i = 0; i < runningTime.length; i++) {
                for (int j = 0; j < 5; j++) {
                    if (j > 0) {
                        writer.append(",");
                    }
                    writer.append(Long.toString(runningTime[i][j]));
                }
                writer.append("\n");
            }
            writer.close();
            
            category++;
        }
    }
}
