# 148. Longest Palindromic Subsequence - Editorial

## Problem Statement
Given a string `s`, find the length of the longest palindromic subsequence in `s`.

A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.

---

## Sample Example

### Sample Input
```text
bbbab
```

### Sample Output
```text
4
```

### Explanation
* One possible longest palindromic subsequence is "bbbb", which has length 4.

---

## Intuition
A palindromic string reads the same forwards and backwards.
Therefore, the Longest Palindromic Subsequence (LPS) of a string `s` is equivalent to finding the Longest Common Subsequence (LCS) between `s` and its reverse `reverse(s)`!

## Approach
1. Let `rev` be the reverse of `s`.
2. Compute the LCS between `s` and `rev` using Dynamic Programming.
3. Use space optimization with two 1D rows (`prev` and `curr` of size `N + 1`):
   - If `s[i-1] == rev[j-1]`: `curr[j] = 1 + prev[j-1]`
   - Else: `curr[j] = max(prev[j], curr[j-1])`
4. Return `prev[N]`.

---

## Complexity Analysis
* **Time Complexity:** O(N^2) where N is the length of the string.
* **Space Complexity:** O(N) space using two 1D arrays for DP state storage.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    /**
     * Finds the length of the longest palindromic subsequence using Dynamic Programming.
     */
    public int longestPalindromeSubseq(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        int n = s.length();
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];

        // LCS between s and reverse(s)
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == rev.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String s = sc.nextLine().trim();

        Solution sol = new Solution();
        int ans = sol.longestPalindromeSubseq(s);
        System.out.println(ans);
        sc.close();
    }
}
```
