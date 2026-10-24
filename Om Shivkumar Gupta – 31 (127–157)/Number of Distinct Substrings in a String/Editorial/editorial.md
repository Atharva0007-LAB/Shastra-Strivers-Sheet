# 145. Number of Distinct Substrings in a String - Editorial

## Problem Statement
Given a string `s` of length `N` consisting of lowercase English letters, compute the total number of distinct non-empty substrings present in `s`.

A substring is a contiguous sequence of characters within a string.

---

## Sample Example

### Sample Input
```text
ababa
```

### Sample Output
```text
9
```

### Explanation
* The distinct non-empty substrings of "ababa" are:
  - Length 1: "a", "b"
  - Length 2: "ab", "ba"
  - Length 3: "aba", "bab"
  - Length 4: "abab", "baba"
  - Length 5: "ababa"
* Total distinct substrings = 9.

---

## Intuition
A brute-force approach generates all O(N^2) substrings and stores them in a HashSet<String>, which takes O(N^3) time due to string copying and hashing overhead.

By utilizing a Trie (Prefix Tree):
Every substring of `s` is a prefix of some suffix of `s`.
If we insert all suffixes of `s` into a Trie, each unique substring corresponds to exactly one path from the root.
Therefore, the number of distinct substrings is simply the total number of newly created nodes across all insertions!

## Approach
1. Initialize an empty Trie root.
2. Initialize `count = 0`.
3. Loop `i` from `0` to `N - 1`:
   - Start at `node = root`.
   - Loop `j` from `i` to `N - 1`:
     - If `node` does not have a link for `s[j]`:
       - Increment `count`.
       - Create a new Trie node.
     - Advance `node = node.get(s[j])`.
4. Return `count`.

---

## Complexity Analysis
* **Time Complexity:** O(N^2) where N is the length of the string.
* **Space Complexity:** O(N^2 * 26) worst-case to store the Trie nodes.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    static class Node {
        Node[] links = new Node[26];

        boolean containsKey(char ch) {
            return links[ch - 'a'] != null;
        }

        Node get(char ch) {
            return links[ch - 'a'];
        }

        void put(char ch, Node node) {
            links[ch - 'a'] = node;
        }
    }

    /**
     * Counts distinct substrings using a Trie in O(N^2) time.
     */
    public int countDistinctSubstrings(String s) {
        Node root = new Node();
        int count = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            Node node = root;
            for (int j = i; j < n; j++) {
                char ch = s.charAt(j);
                if (!node.containsKey(ch)) {
                    count++; // Each new Trie node represents a brand new unique substring
                    node.put(ch, new Node());
                }
                node = node.get(ch);
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        String s = sc.nextLine().trim();

        Solution sol = new Solution();
        int ans = sol.countDistinctSubstrings(s);
        System.out.println(ans);
        sc.close();
    }
}
```
