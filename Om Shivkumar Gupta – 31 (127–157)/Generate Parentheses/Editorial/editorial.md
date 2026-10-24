# 153. Generate Parentheses - Editorial

## Problem Statement
Given `N` pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

Print each valid combination on a separate line, sorted in lexicographical order.

---

## Sample Example

### Sample Input
```text
3
```

### Sample Output
```text
((()))
(()())
(())()
()(())
()()()
```

### Explanation
* All 5 valid combinations of 3 pairs of parentheses are generated in lexicographical order.

---

## Intuition
A string of parentheses is well-formed if and only if:
1. The total number of `(` equals `N` and total `)` equals `N`.
2. At every prefix of the string, the number of closing parentheses `)` never exceeds the number of opening parentheses `(`.

Using Backtracking, we construct the string character by character while maintaining these invariants.

## Approach
1. Maintain counts of `open` and `close` parentheses used so far.
2. If string length reaches `2 * N`, add current string to results.
3. If `open < N`, append `(`, recurse with `open + 1`, and backtrack.
4. If `close < open`, append `)`, recurse with `close + 1`, and backtrack.
5. Return the list of combinations.

---

## Complexity Analysis
* **Time Complexity:** O(4^N / sqrt(N)) which corresponds to the N-th Catalan number C_N.
* **Space Complexity:** O(N) for the recursion stack and string builder.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private void backtrack(int open, int close, int n, StringBuilder sb, List<String> result) {
        if (sb.length() == 2 * n) {
            result.add(sb.toString());
            return;
        }

        // Can add open parenthesis if remaining open count < n
        if (open < n) {
            sb.append('(');
            backtrack(open + 1, close, n, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }

        // Can add close parenthesis only if close < open
        if (close < open) {
            sb.append(')');
            backtrack(open, close + 1, n, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(0, 0, n, new StringBuilder(), result);
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        Solution sol = new Solution();
        List<String> ans = sol.generateParenthesis(n);

        for (String s : ans) {
            System.out.println(s);
        }
        sc.close();
    }
}
```
