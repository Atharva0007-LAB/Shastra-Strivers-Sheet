import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(143);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int v, e;
            List<int[]> edges = new ArrayList<>();

            if (tc == 0) {
                v = 4;
                edges.add(new int[]{0, 1}); edges.add(new int[]{1, 2});
                edges.add(new int[]{2, 0}); edges.add(new int[]{1, 3});
            } else if (tc == 1) {
                v = 2; edges.add(new int[]{0, 1});
            } else if (tc == 2) {
                // Cycle: no bridges
                v = 4;
                edges.add(new int[]{0, 1}); edges.add(new int[]{1, 2});
                edges.add(new int[]{2, 3}); edges.add(new int[]{3, 0});
            } else if (tc == 3) {
                // Linear tree: all edges are bridges
                v = 5;
                for (int i = 0; i < 4; i++) edges.add(new int[]{i, i + 1});
            } else if (tc == 4) {
                v = 6;
                edges.add(new int[]{0, 1}); edges.add(new int[]{1, 2}); edges.add(new int[]{2, 0});
                edges.add(new int[]{2, 3});
                edges.add(new int[]{3, 4}); edges.add(new int[]{4, 5}); edges.add(new int[]{5, 3});
            } else {
                v = (tc < 40) ? 15 + rand.nextInt(25) : 100 + rand.nextInt(200);
                // Connected backbone
                for (int i = 1; i < v; i++) edges.add(new int[]{rand.nextInt(i), i});
                int extra = v / 2 + rand.nextInt(v);
                for (int i = 0; i < extra; i++) {
                    int a = rand.nextInt(v), b = rand.nextInt(v);
                    if (a != b) edges.add(new int[]{a, b});
                }
            }

            e = edges.size();

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(v + " " + e);
            for (int[] edge : edges) inPw.println(edge[0] + " " + edge[1]);
            inPw.close();

            // Tarjan solve
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i < v; i++) adj.add(new ArrayList<>());
            for (int[] ed : edges) {
                adj.get(ed[0]).add(ed[1]);
                adj.get(ed[1]).add(ed[0]);
            }

            int[] tin = new int[v], low = new int[v];
            boolean[] vis = new boolean[v];
            List<int[]> ans = new ArrayList<>();
            int[] timer = {1};

            for (int i = 0; i < v; i++) {
                if (!vis[i]) dfs(i, -1, tin, low, vis, timer, adj, ans);
            }

            Collections.sort(ans, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            if (ans.isEmpty()) {
                outPw.println("-1");
            } else {
                for (int[] b : ans) outPw.println(b[0] + " " + b[1]);
            }
            outPw.close();
        }
        System.out.println("Bridges in Graph: 100 test cases generated.");
    }

    static void dfs(int u, int p, int[] tin, int[] low, boolean[] vis, int[] timer, List<List<Integer>> adj, List<int[]> ans) {
        vis[u] = true;
        tin[u] = low[u] = timer[0]++;
        for (int v : adj.get(u)) {
            if (v == p) continue;
            if (vis[v]) {
                low[u] = Math.min(low[u], tin[v]);
            } else {
                dfs(v, u, tin, low, vis, timer, adj, ans);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > tin[u]) ans.add(new int[]{Math.min(u, v), Math.max(u, v)});
            }
        }
    }
}
