import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static class Pair {
        int a, b;
        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    @SuppressWarnings("unchecked")
    private static int[] solve(int N, List<Pair> pairs) {
        List<Integer>[] adj = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adj[i] = new ArrayList<>();
        }
        int[] inDegree = new int[N];

        for (Pair p : pairs) {
            // b must be taken before a (b -> a)
            adj[p.b].add(p.a);
            inDegree[p.a]++;
        }

        int[] order = new int[N];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < N; i++) {
            if (inDegree[i] == 0) {
                order[tail++] = i;
            }
        }

        while (head < tail) {
            int u = order[head++];
            for (int v : adj[u]) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    order[tail++] = v;
                }
            }
        }

        if (tail == N) {
            return order;
        } else {
            return new int[0];
        }
    }

    private static void writeTestCase(File inDir, File outDir, int index, int N, List<Pair> pairs) throws Exception {
        String filename = String.format("%02d.txt", index);
        File inFile = new File(inDir, "input" + filename);
        File outFile = new File(outDir, "output" + filename);

        try (PrintWriter inPw = new PrintWriter(inFile)) {
            inPw.println(N + " " + pairs.size());
            for (Pair p : pairs) {
                inPw.println(p.a + " " + p.b);
            }
        }

        int[] order = solve(N, pairs);
        try (PrintWriter outPw = new PrintWriter(outFile)) {
            if (order.length == N) {
                for (int i = 0; i < N; i++) {
                    outPw.print(order[i]);
                    if (i + 1 < N) {
                        outPw.print(" ");
                    }
                }
            }
            outPw.println();
        }
    }

    private static List<Pair> generateGraph(int N, int targetM, boolean makeCyclic, Random rng) {
        List<Pair> pairs = new ArrayList<>();
        if (N <= 1 || targetM <= 0) return pairs;

        int[] perm = new int[N];
        for (int i = 0; i < N; i++) perm[i] = i;
        for (int i = N - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = perm[i];
            perm[i] = perm[j];
            perm[j] = tmp;
        }

        Set<Long> set = new HashSet<>();
        long maxPossible = ((long) N * (N - 1)) / 2;
        int actualM = (int) Math.min(targetM, Math.min(maxPossible, 5000));

        // In a valid DAG, perm[i] -> perm[j] where i < j (so perm[i] is taken before perm[j])
        // That means pair is: [a=perm[j], b=perm[i]]
        if (actualM >= N - 1 && rng.nextBoolean()) {
            for (int i = 0; i < N - 1 && pairs.size() < actualM; i++) {
                int b = perm[i];
                int a = perm[i + 1];
                long key = (((long) a) << 32) | (b & 0xFFFFFFFFL);
                if (set.add(key)) {
                    pairs.add(new Pair(a, b));
                }
            }
        }

        int forwardTarget = makeCyclic ? Math.max(1, actualM - 1 - rng.nextInt(3)) : actualM;
        int attempts = 0;
        while (pairs.size() < forwardTarget && attempts < forwardTarget * 15) {
            attempts++;
            int i = rng.nextInt(N);
            int j = rng.nextInt(N);
            if (i == j) continue;
            if (i > j) {
                int t = i;
                i = j;
                j = t;
            }
            int b = perm[i];
            int a = perm[j];
            long key = (((long) a) << 32) | (b & 0xFFFFFFFFL);
            if (set.add(key)) {
                pairs.add(new Pair(a, b));
            }
        }

        // If makeCyclic is true, add at least one back-edge (perm[j] before perm[i] where j > i)
        if (makeCyclic && N >= 2) {
            int backAttempts = 0;
            boolean cycleAdded = false;
            while (backAttempts < 500 && !cycleAdded) {
                backAttempts++;
                int i = rng.nextInt(N);
                int j = rng.nextInt(N);
                if (i == j) continue;
                if (i > j) {
                    int t = i;
                    i = j;
                    j = t;
                }
                int a = perm[i];
                int b = perm[j];
                long key = (((long) a) << 32) | (b & 0xFFFFFFFFL);
                if (set.add(key)) {
                    pairs.add(new Pair(a, b));
                    cycleAdded = true;
                }
            }

            if (!cycleAdded) {
                int a = perm[0];
                int b = perm[1];
                long key = (((long) a) << 32) | (b & 0xFFFFFFFFL);
                if (set.add(key)) {
                    pairs.add(new Pair(a, b));
                }
            }
        }

        return pairs;
    }

    public static void main(String[] args) {
        try {
            File baseDir = new File("d:/MF Shastra/shastra striverz/Course Schedule II/Testcases");
            File inDir = new File(baseDir, "Input");
            File outDir = new File(baseDir, "Output");

            inDir.mkdirs();
            outDir.mkdirs();

            Random rng = new Random(5052026L);

            // 00: Sample 1 (order: 0 1 2 3)
            List<Pair> p0 = Arrays.asList(
                new Pair(1, 0), new Pair(2, 1), new Pair(3, 2)
            );
            writeTestCase(inDir, outDir, 0, 4, p0);

            // 01: Sample 2 (order: empty)
            List<Pair> p1 = Arrays.asList(
                new Pair(0, 1), new Pair(3, 2), new Pair(1, 3), new Pair(3, 0)
            );
            writeTestCase(inDir, outDir, 1, 4, p1);

            // 02: Sample 3 (order: 0 1)
            List<Pair> p2 = Collections.singletonList(new Pair(1, 0));
            writeTestCase(inDir, outDir, 2, 2, p2);

            // 03: N=1, M=0 (order: 0)
            writeTestCase(inDir, outDir, 3, 1, Collections.emptyList());

            // 04: N=2, M=0 (order: 0 1)
            writeTestCase(inDir, outDir, 4, 2, Collections.emptyList());

            // 05: N=2, M=1 ([0, 1], order: 1 0)
            writeTestCase(inDir, outDir, 5, 2, Collections.singletonList(new Pair(0, 1)));

            // 06: N=2, M=2 ([1, 0], [0, 1], order: empty)
            writeTestCase(inDir, outDir, 6, 2, Arrays.asList(new Pair(1, 0), new Pair(0, 1)));

            // 07: N=3, M=0 (order: 0 1 2)
            writeTestCase(inDir, outDir, 7, 3, Collections.emptyList());

            // 08: N=3, M=2 ([1, 0], [2, 1], order: 0 1 2)
            writeTestCase(inDir, outDir, 8, 3, Arrays.asList(new Pair(1, 0), new Pair(2, 1)));

            // 09: N=3, M=3 ([1, 0], [2, 1], [0, 2], order: empty)
            writeTestCase(inDir, outDir, 9, 3, Arrays.asList(
                new Pair(1, 0), new Pair(2, 1), new Pair(0, 2)
            ));

            // 10: N=4 Diamond DAG (valid order)
            writeTestCase(inDir, outDir, 10, 4, Arrays.asList(
                new Pair(1, 0), new Pair(2, 0), new Pair(3, 1), new Pair(3, 2)
            ));

            // 11: N=4 Diamond with back-edge ([0, 3], order: empty)
            writeTestCase(inDir, outDir, 11, 4, Arrays.asList(
                new Pair(1, 0), new Pair(2, 0), new Pair(3, 1), new Pair(3, 2), new Pair(0, 3)
            ));

            // 12: N=5 Star Out-Tree (0 before all, order starts with 0)
            writeTestCase(inDir, outDir, 12, 5, Arrays.asList(
                new Pair(1, 0), new Pair(2, 0), new Pair(3, 0), new Pair(4, 0)
            ));

            // 13: N=5 Star In-Tree (all before 0, order ends with 0)
            writeTestCase(inDir, outDir, 13, 5, Arrays.asList(
                new Pair(0, 1), new Pair(0, 2), new Pair(0, 3), new Pair(0, 4)
            ));

            // 14: N=5 Star with cycle (order: empty)
            writeTestCase(inDir, outDir, 14, 5, Arrays.asList(
                new Pair(1, 0), new Pair(2, 0), new Pair(3, 0), new Pair(4, 0), new Pair(0, 1)
            ));

            // 15 - 34: Medium graphs (N = 10..100)
            for (int tc = 15; tc <= 34; tc++) {
                int N = 10 + (tc - 15) * 4 + rng.nextInt(5);
                int M = rng.nextInt(Math.min(N * 3 + 10, 250));
                boolean makeCyclic = (tc % 2 == 1);
                writeTestCase(inDir, outDir, tc, N, generateGraph(N, M, makeCyclic, rng));
            }

            // 35 - 69: Large graphs (N = 150..1000)
            for (int tc = 35; tc <= 69; tc++) {
                int N = 150 + (tc - 35) * 25 + rng.nextInt(15);
                int maxM = Math.min(3000, N * 3);
                int M = 50 + rng.nextInt(maxM);
                boolean makeCyclic = (tc % 2 == 0);
                writeTestCase(inDir, outDir, tc, N, generateGraph(N, M, makeCyclic, rng));
            }

            // 70 - 89: Very Large graphs (N = 1200..2000)
            for (int tc = 70; tc <= 89; tc++) {
                int N = 1200 + (tc - 70) * 40 + rng.nextInt(20);
                int M = 500 + rng.nextInt(4500);
                boolean makeCyclic = (tc % 2 == 1);
                writeTestCase(inDir, outDir, tc, N, generateGraph(N, M, makeCyclic, rng));
            }

            // 90 - 99: Extreme Maximum Constraints (N = 2000, M up to 5000)
            // 90: N=2000, M=0 (order: 0 1 ... 1999)
            writeTestCase(inDir, outDir, 90, 2000, Collections.emptyList());

            // 91: N=2000, M=1999 (long chain, valid order)
            List<Pair> p91 = new ArrayList<>();
            for (int i = 0; i < 1999; i++) {
                p91.add(new Pair(i + 1, i));
            }
            writeTestCase(inDir, outDir, 91, 2000, p91);

            // 92: N=2000, M=2000 (long chain + cycle, order: empty)
            List<Pair> p92 = new ArrayList<>(p91);
            p92.add(new Pair(0, 1999));
            writeTestCase(inDir, outDir, 92, 2000, p92);

            // 93: N=2000, M=2000 (long chain + small 2-cycle at end, order: empty)
            List<Pair> p93 = new ArrayList<>(p91);
            p93.add(new Pair(1998, 1999));
            writeTestCase(inDir, outDir, 93, 2000, p93);

            // 94: N=2000, M=1999 (star: 0 before all, valid order)
            List<Pair> p94 = new ArrayList<>();
            for (int i = 1; i < 2000; i++) {
                p94.add(new Pair(i, 0));
            }
            writeTestCase(inDir, outDir, 94, 2000, p94);

            // 95: N=2000, M=2000 (star + cycle, order: empty)
            List<Pair> p95 = new ArrayList<>(p94);
            p95.add(new Pair(0, 1999));
            writeTestCase(inDir, outDir, 95, 2000, p95);

            // 96: N=2000, M=1999 (binary heap tree, valid order)
            List<Pair> p96 = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                if (left < 2000) p96.add(new Pair(left, i));
                if (right < 2000) p96.add(new Pair(right, i));
            }
            writeTestCase(inDir, outDir, 96, 2000, p96);

            // 97: N=2000, M=2000 (binary heap tree + cycle, order: empty)
            List<Pair> p97 = new ArrayList<>(p96);
            p97.add(new Pair(0, 1999));
            writeTestCase(inDir, outDir, 97, 2000, p97);

            // 98: N=2000, M=5000 (dense random DAG, valid order)
            writeTestCase(inDir, outDir, 98, 2000, generateGraph(2000, 5000, false, rng));

            // 99: N=2000, M=5000 (dense random graph with cycle, order: empty)
            writeTestCase(inDir, outDir, 99, 2000, generateGraph(2000, 5000, true, rng));

            System.out.println("SUCCESS: Generated 100 testcases for Course Schedule II.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
