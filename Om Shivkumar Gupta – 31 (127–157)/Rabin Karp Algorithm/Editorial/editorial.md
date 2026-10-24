# 141. Rabin Karp Algorithm - Editorial

## Problem Statement
Given two strings `text` and `pattern`, implement the Rabin-Karp pattern matching algorithm to find all occurrences of `pattern` in `text`.

Print all starting indices (0-indexed) where `pattern` occurs in `text` space-separated on a single line. If the pattern does not occur in the text, print `-1`.

---

## Sample Example

### Sample Input
```text
GEEKS FOR GEEKS
GEEK
```

### Sample Output
```text
0 10
```

### Explanation
* 'GEEK' appears at index 0 ('GEEK'S FOR GEEKS)
* 'GEEK' also appears at index 10 (GEEKS FOR 'GEEK'S)

---

## Intuition
A brute-force string matching algorithm checks every substring of length M in the text of length N, leading to O(N * M) worst-case time complexity.

Rabin-Karp optimizes this using a Rolling Hash:
Instead of comparing all characters directly, it computes a hash value for the pattern and for each window of length M in the text.
Sliding the window by 1 character only takes O(1) time because:
`new_hash = (BASE * (old_hash - text[i] * BASE^(M-1)) + text[i+M]) % MOD`
When the window hash equals the pattern hash, we perform a character check to eliminate hash collisions.

## Approach
1. Compute `h = BASE^(M-1) % MOD`.
2. Compute the initial hash of `pattern` and `text[0...M-1]`.
3. Slide the window from index `i = 0` to `N - M`:
   - If `pHash == tHash`, verify character by character. If verified, record index `i`.
   - Update `tHash` for the next window in `O(1)`:
     `tHash = (BASE * (tHash - text[i] * h) + text[i + M]) % MOD`
     If negative, add `MOD`.
4. If no indices are found, output `-1`.

---

## Complexity Analysis
* **Time Complexity:** O(N + M) average time complexity; O(N * M) worst-case in extreme collision scenarios.
* **Space Complexity:** O(1) auxiliary space beyond the output list.

---

## Reference Implementation

### Java Solution
```java
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
```
