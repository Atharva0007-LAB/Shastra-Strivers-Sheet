# 136. Palindrome Partitioning - Editorial

## Problem Statement
Given a string `s`, partition `s` such that every substring of the partition is a palindrome.

Return all possible palindrome partitionings of `s`.

Output each partition on a new line in the format `[part1, part2, ...]`, with partitions sorted in lexicographical order.

---

## Sample Example

### Sample Input
```text
aab
```

### Sample Output
```text
[a, a, b]
[aa, b]
```

### Explanation
* The possible palindrome partitions are:
  - ["a", "a", "b"] where "a", "a", "b" are palindromes.
  - ["aa", "b"] where "aa" and "b" are palindromes.

---

## Intuition
To partition the string such that every substring is a palindrome, we can recursively decide where to place the partition boundaries:
Starting at index `start`, test each ending position `end` from `start` to `s.length() - 1`.
If the prefix substring `s[start...end]` is a palindrome, we can place a cut right after `end` and recursively partition the remainder `s[end+1...]`.

## Approach
1. Helper function `isPalindrome(s, left, right)` checks if substring from `left` to `right` is a palindrome using two pointers.
2. Backtracking function `backtrack(start, currentList)`:
   - If `start == s.length()`, a valid full partition has been formed; add a copy of `currentList` to `result`.
   - For each `end` from `start` to `s.length() - 1`:
     - If `isPalindrome(s, start, end)`:
       - Add `s.substring(start, end + 1)` to `currentList`.
       - Recurse on `backtrack(end + 1, currentList)`.
       - Backtrack by removing the last substring.
3. Return `result`.

---

## Complexity Analysis
* **Time Complexity:** O(2^N * N) in the worst case (e.g. all characters identical), where 2^(N-1) partitions are possible and checking/copying takes O(N).
* **Space Complexity:** O(N) auxiliary space for the recursion depth.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    /**
     * Finds all palindrome partitions using backtracking.
     */
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrack(0, s, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int start, String s, List<String> current, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end)) {
                current.add(s.substring(start, end + 1));
                backtrack(end + 1, s, current, result);
                current.remove(current.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String s = sc.nextLine().trim();
        if (s.isEmpty()) return;

        Solution sol = new Solution();
        List<List<String>> partitions = sol.partition(s);

        for (List<String> part : partitions) {
            System.out.print("[");
            for (int i = 0; i < part.size(); i++) {
                System.out.print(part.get(i) + (i == part.size() - 1 ? "" : ", "));
            }
            System.out.println("]");
        }
        sc.close();
    }
}
```
