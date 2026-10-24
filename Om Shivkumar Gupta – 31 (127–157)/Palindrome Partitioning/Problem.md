### Problem 136: Palindrome Partitioning
**Difficulty:** Medium | **Tags:** String, Backtracking, Dynamic Programming

**Problem Statement:**
Given a string `s`, partition `s` such that every substring of the partition is a palindrome.

Return all possible palindrome partitionings of `s`.

Output each partition on a new line in the format `[part1, part2, ...]`, with partitions sorted in lexicographical order.

### Input Format
* A single line containing the string `s`.

### Output Format
* Print each palindrome partition on a separate line formatted as `[substring1, substring2, ...]`.

### Constraints
* 1 <= s.length <= 16
* s contains only lowercase English letters

### Example
#### Input:
```text
aab
```

#### Output:
```text
[a, a, b]
[aa, b]
```

#### Explanation
* The possible palindrome partitions are:
  - ["a", "a", "b"] where "a", "a", "b" are palindromes.
  - ["aa", "b"] where "aa" and "b" are palindromes.
