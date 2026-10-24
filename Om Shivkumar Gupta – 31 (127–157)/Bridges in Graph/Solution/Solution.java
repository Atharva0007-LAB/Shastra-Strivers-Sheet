import java.util.*;

public class Solution {
    private int timer = 1;

    private void dfs(int u, int p, int[] tin, int[] low, boolean[] vis, List<List<Integer>> adj, List<List<Integer>> bridges) {
        vis[u] = true;
        tin[u] = low[u] = timer++;

        for (int v : adj.get(u)) {
            if (v == p) continue; // Skip edge to parent

            if (vis[v]) {
                // Back-edge: update lowest time
                low[u] = Math.min(low[u], tin[v]);
            } else {
                // Forward edge
                dfs(v, u, tin, low, vis, adj, bridges);
                low[u] = Math.min(low[u], low[v]);

                // Bridge condition
                if (low[v] > tin[u]) {
                    List<Integer> bridge = new ArrayList<>();
                    bridge.add(Math.min(u, v));
                    bridge.add(Math.max(u, v));
                    bridges.add(bridge);
                }
            }
        }
    }

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (List<Integer> edge : connections) {
            int u = edge.get(0);
            int v = edge.get(1);
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] tin = new int[n];
        int[] low = new int[n];
        boolean[] vis = new boolean[n];
        List<List<Integer>> bridges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                dfs(i, -1, tin, low, vis, adj, bridges);
            }
        }

        // Sort bridges lexicographically
        Collections.sort(bridges, (a, b) -> {
            if (!a.get(0).equals(b.get(0))) return Integer.compare(a.get(0), b.get(0));
            return Integer.compare(a.get(1), b.get(1));
        });

        return bridges;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int v = sc.nextInt();
        int e = sc.nextInt();

        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt();
            int to = sc.nextInt();
            connections.add(Arrays.asList(u, to));
        }

        Solution sol = new Solution();
        List<List<Integer>> bridges = sol.criticalConnections(v, connections);

        if (bridges.isEmpty()) {
            System.out.println("-1");
        } else {
            for (List<Integer> b : bridges) {
                System.out.println(b.get(0) + " " + b.get(1));
            }
        }
        sc.close();
    }
}
