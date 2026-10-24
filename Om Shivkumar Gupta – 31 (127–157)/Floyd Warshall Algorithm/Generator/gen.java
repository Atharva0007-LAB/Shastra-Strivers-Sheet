import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) {
            baseDir = "../TestCases";
        }
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random rand = new Random(127);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[][] mat;

            if (tc == 0) {
                n = 4;
                mat = new int[][]{
                    {0, 2, -1, -1},
                    {1, 0, 3, -1},
                    {-1, -1, 0, -1},
                    {3, 5, 4, 0}
                };
            } else if (tc == 1) {
                n = 1;
                mat = new int[][]{{0}};
            } else if (tc == 2) {
                n = 3;
                mat = new int[][]{
                    {0, -1, -1},
                    {-1, 0, -1},
                    {-1, -1, 0}
                };
            } else if (tc == 3) {
                n = 4;
                mat = new int[][]{
                    {0, 3, -1, -1},
                    {-1, 0, 4, -1},
                    {-1, -1, 0, 2},
                    {-1, -1, -1, 0}
                };
            } else if (tc == 4) {
                n = 5;
                mat = new int[][]{
                    {0, 4, -1, 5, -1},
                    {-1, 0, 1, -1, 6},
                    {2, -1, 0, 3, -1},
                    {-1, -1, 1, 0, 2},
                    {1, -1, -1, -1, 0}
                };
            } else {
                // Higher stress test cases (tc 5 to 99)
                if (tc < 20) n = 5 + rand.nextInt(6);
                else if (tc < 50) n = 12 + rand.nextInt(10);
                else if (tc < 80) n = 25 + rand.nextInt(15);
                else n = 45 + rand.nextInt(20);

                mat = new int[n][n];
                double density = (tc % 2 == 0) ? 0.35 : 0.65;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i == j) {
                            mat[i][j] = 0;
                        } else {
                            if (rand.nextDouble() < density) {
                                mat[i][j] = 1 + rand.nextInt(500);
                            } else {
                                mat[i][j] = -1;
                            }
                        }
                    }
                }
            }

            // Write input
            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    inPw.print(mat[i][j] + (j == n - 1 ? "" : " "));
                }
                inPw.println();
            }
            inPw.close();

            // Compute optimal Floyd Warshall answer
            int[][] ans = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    ans[i][j] = (mat[i][j] == -1) ? (int)1e9 : mat[i][j];
                    if (i == j) ans[i][j] = 0;
                }
            }

            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (ans[i][k] < 1e9 && ans[k][j] < 1e9) {
                            ans[i][j] = Math.min(ans[i][j], ans[i][k] + ans[k][j]);
                        }
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (ans[i][j] >= 1e9) ans[i][j] = -1;
                }
            }

            // Write output
            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    outPw.print(ans[i][j] + (j == n - 1 ? "" : " "));
                }
                outPw.println();
            }
            outPw.close();
        }
        System.out.println("Floyd Warshall: 100 test cases generated.");
    }
}
