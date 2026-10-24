import java.io.*;
import java.util.*;

public class gen {
    static class Edge implements Comparable<Edge> {
        int u, v, w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
        public int compareTo(Edge o) { return Integer.compare(this.w, o.w); }
    }

    static class DSU {
        int[] parent;
        DSU(int n) { parent = new int[n]; for (int i = 0; i < n; i++) parent[i] = i; }
        int find(int i) { return parent[i] == i ? i : (parent[i] = find(parent[i])); }
        boolean union(int i, int j) {
            int rI = find(i), rJ = find(j);
            if (rI == rJ) return false;
            parent[rI] = rJ;
            return true;
        }
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) {
            baseDir = "../TestCases";
        }
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random rand = new Random(132);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int v, e;
            List<Edge> edgeList = new ArrayList<>();

            if (tc == 0) {
                v = 3; e = 3;
                edgeList.add(new Edge(0, 1, 5));
                edgeList.add(new Edge(1, 2, 3));
                edgeList.add(new Edge(0, 2, 1));
            } else if (tc == 1) {
                v = 2; e = 1;
                edgeList.add(new Edge(0, 1, 10));
            } else if (tc == 2) {
                v = 4; e = 5;
                edgeList.add(new Edge(0, 1, 10));
                edgeList.add(new Edge(0, 2, 6));
                edgeList.add(new Edge(0, 3, 5));
                edgeList.add(new Edge(1, 3, 15));
                edgeList.add(new Edge(2, 3, 4));
            } else if (tc == 3) {
                v = 4; e = 4;
                edgeList.add(new Edge(0, 1, -2));
                edgeList.add(new Edge(1, 2, 3));
                edgeList.add(new Edge(2, 3, -5));
                edgeList.add(new Edge(0, 3, 4));
            } else if (tc == 4) {
                v = 5; e = 7;
                edgeList.add(new Edge(0, 1, 2));
                edgeList.add(new Edge(0, 3, 6));
                edgeList.add(new Edge(1, 2, 3));
                edgeList.add(new Edge(1, 3, 8));
                edgeList.add(new Edge(1, 4, 5));
                edgeList.add(new Edge(2, 4, 7));
                edgeList.add(new Edge(3, 4, 9));
            } else {
                if (tc < 30) v = 10 + rand.nextInt(20);
                else if (tc < 70) v = 50 + rand.nextInt(100);
                else v = 200 + rand.nextInt(300);

                // Build a connected backbone tree first
                for (int i = 1; i < v; i++) {
                    int p = rand.nextInt(i);
                    edgeList.add(new Edge(p, i, rand.nextInt(500) - 100));
                }

                // Add random extra edges
                int extra = v + rand.nextInt(v * 2);
                for (int i = 0; i < extra; i++) {
                    int a = rand.nextInt(v);
                    int b = rand.nextInt(v);
                    if (a != b) edgeList.add(new Edge(a, b, rand.nextInt(1000) - 200));
                }
                e = edgeList.size();
            }

            e = edgeList.size();

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(v + " " + e);
            for (Edge edge : edgeList) {
                inPw.println(edge.u + " " + edge.v + " " + edge.w);
            }
            inPw.close();

            // Kruskal MST
            Collections.sort(edgeList);
            DSU dsu = new DSU(v);
            long mstSum = 0;
            int count = 0;
            for (Edge edge : edgeList) {
                if (dsu.union(edge.u, edge.v)) {
                    mstSum += edge.w;
                    count++;
                    if (count == v - 1) break;
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(mstSum);
            outPw.close();
        }
        System.out.println("Find the MST Weight: 100 test cases generated.");
    }
}
