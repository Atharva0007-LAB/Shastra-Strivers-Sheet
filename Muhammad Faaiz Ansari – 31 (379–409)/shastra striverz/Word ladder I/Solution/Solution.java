import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Solution {

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

        public String nextToken() throws IOException {
            int c = read();
            while (c <= 32) {
                if (c == -1) return null;
                c = read();
            }
            StringBuilder sb = new StringBuilder();
            while (c > 32) {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }

        public int nextInt() throws IOException {
            String s = nextToken();
            if (s == null) return -1;
            return Integer.parseInt(s);
        }
    }

    public static int wordLadderLength(String startWord, String targetWord, String[] wordList, int N) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < N; i++) {
            set.add(wordList[i]);
        }

        if (!set.contains(targetWord)) {
            return 0;
        }

        Queue<String> wordQ = new ArrayDeque<>();
        Queue<Integer> levelQ = new ArrayDeque<>();

        wordQ.add(startWord);
        levelQ.add(1);
        set.remove(startWord);

        while (!wordQ.isEmpty()) {
            String word = wordQ.poll();
            int level = levelQ.poll();

            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char orig = chars[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == orig) continue;
                    chars[i] = c;
                    String nextWord = new String(chars);

                    if (set.contains(nextWord)) {
                        if (nextWord.equals(targetWord)) {
                            return level + 1;
                        }
                        set.remove(nextWord);
                        wordQ.add(nextWord);
                        levelQ.add(level + 1);
                    }
                }
                chars[i] = orig;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        try {
            FastScanner sc = new FastScanner(System.in);
            String startWord = sc.nextToken();
            if (startWord == null) return;
            String targetWord = sc.nextToken();
            int N = sc.nextInt();

            String[] wordList = new String[N];
            for (int i = 0; i < N; i++) {
                wordList[i] = sc.nextToken();
            }

            int ans = wordLadderLength(startWord, targetWord, wordList, N);

            PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
            out.println(ans);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
