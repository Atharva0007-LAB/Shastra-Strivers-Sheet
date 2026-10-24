### Problem 145: Number of Distinct Substrings in a String
**Difficulty:** Medium | **Tags:** String, Trie

**Problem Statement:**
Given a string `s` of length `N` consisting of lowercase English letters, compute the total number of distinct non-empty substrings present in `s`.

A substring is a contiguous sequence of characters within a string.

### Input Format
* A single line containing the string `s`.

### Output Format
* Print a single integer representing the count of distinct non-empty substrings.

### Constraints
* 1 <= s.length <= 1000
* s consists of lowercase English characters ('a' - 'z')

### Example
#### Input:
```text
ababa
```

#### Output:
```text
9
```

#### Explanation
* The distinct non-empty substrings of "ababa" are:
  - Length 1: "a", "b"
  - Length 2: "ab", "ba"
  - Length 3: "aba", "bab"
  - Length 4: "abab", "baba"
  - Length 5: "ababa"
* Total distinct substrings = 9.
