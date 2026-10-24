import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.Arrays;

public class Solution {

    static final int MOD = 100000;

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[32768];
        private int head = 0;
        private int tail = 0;

        public FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (head >= tail) {
                head = 0;
                tail = in.read(buffer, 0, buffer.length);
                if (tail <= 0) return -1;
            }
            return buffer[head++];
        }

        public int nextInt() throws IOException {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            boolean neg = false;
            if (c == '-') {
                neg = true;
                c = read();
            }
            int res = 0;
            while (c >= '0' && c <= '9') {
                res = res * 10 + (c - '0');
                c = read();
            }
            return neg ? -res : res;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int n = fs.nextInt();
        if (n == -1) {
            out.flush();
            return;
        }

        int start = fs.nextInt();
        int end = fs.nextInt();

        boolean[] seen = new boolean[MOD];
        int[] tempArr = new int[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            int val = fs.nextInt();
            int mVal = val % MOD;
            if (!seen[mVal]) {
                seen[mVal] = true;
                tempArr[count++] = mVal;
            }
        }

        int[] arr = Arrays.copyOf(tempArr, count);

        if (start == end) {
            out.println(0);
            out.flush();
            return;
        }

        int[] dist = new int[MOD];
        Arrays.fill(dist, -1);

        int[] queue = new int[MOD];
        int head = 0;
        int tail = 0;

        dist[start] = 0;
        queue[tail++] = start;

        int ans = -1;
        while (head < tail) {
            int u = queue[head++];
            int d = dist[u];

            for (int i = 0; i < count; i++) {
                int next = (int) (((long) u * arr[i]) % MOD);

                if (dist[next] == -1) {
                    dist[next] = d + 1;
                    if (next == end) {
                        ans = dist[next];
                        head = tail; // early exit
                        break;
                    }
                    queue[tail++] = next;
                }
            }
        }

        out.println(ans);
        out.flush();
    }
}
