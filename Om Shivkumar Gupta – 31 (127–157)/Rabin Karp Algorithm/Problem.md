### Problem 141: Rabin Karp Algorithm
**Difficulty:** Medium | **Tags:** String, Rolling Hash, Pattern Matching

**Problem Statement:**
Given two strings `text` and `pattern`, implement the Rabin-Karp pattern matching algorithm to find all occurrences of `pattern` in `text`.

Print all starting indices (0-indexed) where `pattern` occurs in `text` space-separated on a single line. If the pattern does not occur in the text, print `-1`.

### Input Format
* The first line contains the string `text`.
* The second line contains the string `pattern`.

### Output Format
* Print space-separated 0-based starting indices where `pattern` is found, or `-1` if not found.

### Constraints
* 1 <= text.length <= 10^5
* 1 <= pattern.length <= text.length
* text and pattern consist of lowercase and uppercase English letters and digits

### Example
#### Input:
```text
GEEKS FOR GEEKS
GEEK
```

#### Output:
```text
0 10
```

#### Explanation
* 'GEEK' appears at index 0 ('GEEK'S FOR GEEKS)
* 'GEEK' also appears at index 10 (GEEKS FOR 'GEEK'S)
