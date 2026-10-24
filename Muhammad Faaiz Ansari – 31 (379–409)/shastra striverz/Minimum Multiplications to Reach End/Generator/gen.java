import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static final int MOD = 100000;

    private static int solve(int n, int start, int end, int[] arr) {
        if (start == end) return 0;

        boolean[] seen = new boolean[MOD];
        int[] distinct = new int[n];
        int count = 0;
        for (int x : arr) {
            int mVal = x % MOD;
            if (!seen[mVal]) {
                seen[mVal] = true;
                distinct[count++] = mVal;
            }
        }

        int[] dist = new int[MOD];
        Arrays.fill(dist, -1);

        int[] queue = new int[MOD];
        int head = 0, tail = 0;

        dist[start] = 0;
        queue[tail++] = start;

        while (head < tail) {
            int u = queue[head++];
            int d = dist[u];

            for (int i = 0; i < count; i++) {
                int next = (int) (((long) u * distinct[i]) % MOD);
                if (dist[next] == -1) {
                    dist[next] = d + 1;
                    if (next == end) return dist[next];
                    queue[tail++] = next;
                }
            }
        }
        return -1;
    }

    private static void writeTestCase(int index, int start, int end, int[] arr) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        String inFileName = String.format("input%02d.txt", index);
        String outFileName = String.format("output%02d.txt", index);

        File inFile = new File(inputDir, inFileName);
        File outFile = new File(outputDir, outFileName);

        int n = arr.length;
        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(n);
            pw.println(start + " " + end);
            for (int i = 0; i < n; i++) {
                pw.print(arr[i]);
                if (i < n - 1) pw.print(" ");
            }
            pw.println();
        }

        int ans = solve(n, start, end, arr);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5232026L);
        int tc = 0;

        // Sample 1: arr = [2, 5, 7], start = 3, end = 30 -> 2
        writeTestCase(tc++, 3, 30, new int[]{2, 5, 7});

        // Sample 2: arr = [3, 4, 65], start = 7, end = 66175 -> 4
        writeTestCase(tc++, 7, 66175, new int[]{3, 4, 65});

        // Sample 3: arr = [3, 4, 65], start = 7, end = 21 -> 1
        writeTestCase(tc++, 7, 21, new int[]{3, 4, 65});

        // TC 03: start == end
        writeTestCase(tc++, 1234, 1234, new int[]{2, 3, 5});

        // TC 04: arr = [1], impossible to reach end
        writeTestCase(tc++, 5, 50, new int[]{1});

        // TC 05: Single multiplier, 1 step
        writeTestCase(tc++, 2, 4, new int[]{2});

        // TC 06: Power of 10 reach
        writeTestCase(tc++, 1, 1000, new int[]{10});

        // TC 07: Parity mismatch: even start, even multipliers, odd end -> -1
        writeTestCase(tc++, 2, 33333, new int[]{2, 4, 6, 8, 10});

        // TC 08: Divisibility by 5 mismatch
        writeTestCase(tc++, 5, 12, new int[]{5, 25, 125});

        // TC 09: Reachable with 0 modulo (start=8, end=0 with factors)
        writeTestCase(tc++, 8, 0, new int[]{5, 25, 10});

        // TC 10: Huge single factor
        writeTestCase(tc++, 3, (3 * 9999) % MOD, new int[]{9999});

        // TC 11: Array with duplicates
        writeTestCase(tc++, 3, 27, new int[]{3, 3, 3, 3, 3});

        // TC 12: 1-step reach with large array
        writeTestCase(tc++, 15, 30, new int[]{7, 11, 2, 13, 17});

        // TC 13: 2-step reach
        writeTestCase(tc++, 2, 18, new int[]{3, 7});

        // TC 14: 3-step reach
        writeTestCase(tc++, 2, 54, new int[]{3, 5});

        // TC 15 - 24: Coprime multipliers with varying step counts
        for (int k = 0; k < 10; k++) {
            int[] coprimes = {3, 7, 11, 13, 17, 19, 23};
            int start = rng.nextInt(90000) + 1;
            // simulate a few steps to pick guaranteed reachable end
            int steps = (k % 4) + 2;
            int cur = start;
            for (int s = 0; s < steps; s++) {
                cur = (int) (((long) cur * coprimes[rng.nextInt(coprimes.length)]) % MOD);
            }
            writeTestCase(tc++, start, cur, coprimes);
        }

        // TC 25 - 34: Unreachable cases
        for (int k = 0; k < 10; k++) {
            // start is even, all multipliers even, end is odd
            int start = (rng.nextInt(40000) + 1) * 2;
            int end = (rng.nextInt(40000) + 1) * 2 + 1;
            int mLen = 5 + k;
            int[] arr = new int[mLen];
            for (int i = 0; i < mLen; i++) arr[i] = (rng.nextInt(500) + 1) * 2;
            writeTestCase(tc++, start, end, arr);
        }

        // TC 35 - 44: Deep paths requiring 5 to 8 steps
        for (int k = 0; k < 10; k++) {
            int[] smallMults = {2, 3};
            int start = 1;
            int cur = start;
            int targetSteps = 5 + (k % 4);
            for (int s = 0; s < targetSteps; s++) {
                cur = (int) (((long) cur * smallMults[s % 2]) % MOD);
            }
            writeTestCase(tc++, start, cur, smallMults);
        }

        // TC 45 - 64: Medium random arrays (n: 10 to 50)
        for (int k = 0; k < 20; k++) {
            int n = 10 + k * 2;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rng.nextInt(1000) + 2;
            int start = rng.nextInt(90000) + 1;
            // half reachable, half random end
            int end;
            if (k % 2 == 0) {
                int cur = start;
                int steps = rng.nextInt(4) + 1;
                for (int s = 0; s < steps; s++) cur = (int) (((long) cur * arr[rng.nextInt(n)]) % MOD);
                end = cur;
            } else {
                end = rng.nextInt(90000) + 1;
            }
            writeTestCase(tc++, start, end, arr);
        }

        // TC 65 - 74: Larger multipliers in [1000, 10000]
        for (int k = 0; k < 10; k++) {
            int n = 20 + k * 5;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rng.nextInt(9000) + 1000;
            int start = rng.nextInt(90000) + 1;
            int cur = start;
            int steps = rng.nextInt(3) + 1;
            for (int s = 0; s < steps; s++) cur = (int) (((long) cur * arr[rng.nextInt(n)]) % MOD);
            writeTestCase(tc++, start, cur, arr);
        }

        // TC 75 - 84: Prime numbers as multipliers
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71};
        for (int k = 0; k < 10; k++) {
            int subsetLen = 5 + k;
            int[] arr = Arrays.copyOf(primes, subsetLen);
            int start = rng.nextInt(50000) + 1;
            int cur = start;
            for (int s = 0; s < 3 + (k % 3); s++) cur = (int) (((long) cur * arr[rng.nextInt(subsetLen)]) % MOD);
            writeTestCase(tc++, start, cur, arr);
        }

        // TC 85 - 94: High n arrays (n: 500 to 5000)
        for (int k = 0; k < 10; k++) {
            int n = 500 + k * 500;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rng.nextInt(10000) + 1;
            int start = rng.nextInt(90000) + 1;
            int cur = start;
            for (int s = 0; s < 2; s++) cur = (int) (((long) cur * arr[rng.nextInt(n)]) % MOD);
            writeTestCase(tc++, start, cur, arr);
        }

        // TC 95 - 99: Max constraint stress tests (n = 10^4)
        // TC 95: n=10000 random elements in [1, 10000]
        {
            int n = 10000;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rng.nextInt(10000) + 1;
            int start = 3;
            int cur = start;
            for (int s = 0; s < 3; s++) cur = (int) (((long) cur * arr[rng.nextInt(n)]) % MOD);
            writeTestCase(tc++, start, cur, arr);
        }

        // TC 96: n=10000 all even elements, start=2, end=1 (unreachable)
        {
            int n = 10000;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = (rng.nextInt(5000) + 1) * 2;
            writeTestCase(tc++, 2, 1, arr);
        }

        // TC 97: n=10000 all identical elements (redundancy test)
        {
            int n = 10000;
            int[] arr = new int[n];
            Arrays.fill(arr, 3);
            int start = 1;
            int cur = start;
            for (int s = 0; s < 4; s++) cur = (int) (((long) cur * 3) % MOD);
            writeTestCase(tc++, start, cur, arr);
        }

        // TC 98: n=10000 all multiples of 5, start=5, end=7 (unreachable)
        {
            int n = 10000;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = (rng.nextInt(2000) + 1) * 5;
            writeTestCase(tc++, 5, 7, arr);
        }

        // TC 99: n=10000 consecutive integers 1 to 10000
        {
            int n = 10000;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = i + 1;
            writeTestCase(tc++, 7, 77777, arr);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Minimum Multiplications to Reach End.");
    }
}
