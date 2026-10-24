# 156. Z function - Editorial

## Problem Statement
Given a string `s` of length `N`, compute its Z-array.
The `Z` array for string `s` is an array where `Z[i]` equals the length of the longest common prefix between `s` and the suffix of `s` starting at index `i`.
By standard convention, `Z[0] = 0`.

Print the `Z` array space-separated on a single line.

---

## Sample Example

### Sample Input
```text
aabxaabxcaabxaabxay
```

### Sample Output
```text
0 1 0 0 4 1 0 0 0 8 1 0 0 5 1 0 0 1 0
```

### Explanation
* Z[0] = 0
* At index 1 ("abxaab..."), longest prefix match with "aab..." is "a" -> length 1.
* At index 4 ("aabxcaab..."), longest match is "aabx" -> length 4.
* At index 9 ("aabxaabxay"), longest match is "aabxaabx" -> length 8.

---

## Intuition
A brute-force calculation compares each suffix with the prefix, taking O(N^2) time.

The Z-Algorithm achieves linear O(N) time by maintaining an active match interval `[L, R]`, which represents the segment of `s` with the furthest reaching right boundary `R` that matches a prefix of `s`.
When evaluating index `i`:
- If `i <= R`, by definition `s[i...R]` matches `s[i - L...R - L]`. Therefore, `Z[i]` is at least `min(R - i + 1, Z[i - L])`.
- We only need to perform character-by-character comparisons beyond `R`, expanding the interval and moving `R` strictly forward.
Because `R` only advances, total character comparisons are bounded by 2N.

## Approach
1. Initialize `Z` array of size `N` with `Z[0] = 0`.
2. Maintain `L = 0` and `R = 0`.
3. For `i` from `1` to `N - 1`:
   - If `i <= R`: `Z[i] = min(R - i + 1, Z[i - L])`.
   - While `i + Z[i] < N` and `s[Z[i]] == s[i + Z[i]]`: increment `Z[i]`.
   - If `i + Z[i] - 1 > R`: update `L = i` and `R = i + Z[i] - 1`.
4. Return `Z`.

---

## Complexity Analysis
* **Time Complexity:** O(N) linear time because each character comparison either advances R or terminates the inner while loop.
* **Space Complexity:** O(N) to store the Z array.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    /**
     * Computes the Z-array in linear O(N) time.
     */
    public int[] zFunction(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;

        for (int i = 1; i < n; i++) {
            if (i <= r) {
                z[i] = Math.min(r - i + 1, z[i - l]);
            }
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }

        return z;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String s = sc.nextLine().trim();

        Solution sol = new Solution();
        int[] z = sol.zFunction(s);

        for (int i = 0; i < z.length; i++) {
            System.out.print(z[i] + (i == z.length - 1 ? "" : " "));
        }
        System.out.println();
        sc.close();
    }
}
```
