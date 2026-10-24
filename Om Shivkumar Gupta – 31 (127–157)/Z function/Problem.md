### Problem 156: Z function
**Difficulty:** Medium | **Tags:** String, Z Algorithm, Pattern Matching

**Problem Statement:**
Given a string `s` of length `N`, compute its Z-array.
The `Z` array for string `s` is an array where `Z[i]` equals the length of the longest common prefix between `s` and the suffix of `s` starting at index `i`.
By standard convention, `Z[0] = 0`.

Print the `Z` array space-separated on a single line.

### Input Format
* A single line containing the string `s`.

### Output Format
* Print `N` space-separated integers representing the Z-array.

### Constraints
* 1 <= s.length <= 10^5
* s consists of lowercase English characters

### Example
#### Input:
```text
aabxaabxcaabxaabxay
```

#### Output:
```text
0 1 0 0 4 1 0 0 0 8 1 0 0 5 1 0 0 1 0
```

#### Explanation
* Z[0] = 0
* At index 1 ("abxaab..."), longest prefix match with "aab..." is "a" -> length 1.
* At index 4 ("aabxcaab..."), longest match is "aabx" -> length 4.
* At index 9 ("aabxaabxay"), longest match is "aabxaabx" -> length 8.
