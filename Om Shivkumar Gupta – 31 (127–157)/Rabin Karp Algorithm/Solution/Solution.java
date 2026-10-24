import java.util.*;

public class Solution {
    private static final int BASE = 256;
    private static final int MOD = 1000000007;

    /**
     * Finds all pattern match starting positions using Rabin-Karp rolling hash.
     */
    public List<Integer> search(String pattern, String text) {
        List<Integer> matches = new ArrayList<>();
        int m = pattern.length();
        int n = text.length();
        if (m > n) return matches;

        long pHash = 0; // hash for pattern
        long tHash = 0; // hash for text window
        long h = 1;     // BASE^(m-1) % MOD

        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % MOD;
        }

        // Calculate initial hash value of pattern and first text window
        for (int i = 0; i < m; i++) {
            pHash = (pHash * BASE + pattern.charAt(i)) % MOD;
            tHash = (tHash * BASE + text.charAt(i)) % MOD;
        }

        // Slide the pattern over text
        for (int i = 0; i <= n - m; i++) {
            // Check if hash values match
            if (pHash == tHash) {
                // Verify characters to avoid collision false positive
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) matches.add(i);
            }

            // Calculate hash value for next window
            if (i < n - m) {
                tHash = (BASE * (tHash - text.charAt(i) * h) + text.charAt(i + m)) % MOD;
                if (tHash < 0) tHash = (tHash + MOD);
            }
        }

        return matches;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String text = sc.nextLine();
        if (!sc.hasNextLine()) return;
        String pattern = sc.nextLine();

        Solution sol = new Solution();
        List<Integer> indices = sol.search(pattern, text);

        if (indices.isEmpty()) {
            System.out.println("-1");
        } else {
            for (int i = 0; i < indices.size(); i++) {
                System.out.print(indices.get(i) + (i == indices.size() - 1 ? "" : " "));
            }
            System.out.println();
        }
        sc.close();
    }
}
