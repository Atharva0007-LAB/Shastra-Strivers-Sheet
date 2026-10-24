### Problem: Word Ladder II
**Difficulty:** Hard | **Tags:** Graphs, Breadth-First Search, Depth-First Search, Backtracking, String

**Problem Statement**
Given two distinct words `startWord` and `targetWord`, and a dictionary `wordList` containing $N$ unique words of equal length $M$.

Find **all shortest transformation sequence(s)** from `startWord` to `targetWord`.

A transformation sequence is a sequence of words `beginWord -> s1 -> s2 -> ... -> sk` such that:
* Every adjacent pair of words differs by exactly one letter.
* Every transformed word `s_i` ($1 \le i \le k$) must exist in `wordList` (including `targetWord`).
* `startWord` may or may not be part of `wordList`.
* All sequences must have the minimal possible length.

Return all shortest sequences. If no such sequence exists, return an empty list.

---

**Input Format**
* The first line contains `startWord`, `targetWord`, and an integer $N$ (number of words in `wordList`), separated by space.
* The next $N$ lines each contain a single string representing a word in `wordList`.

**Output Format**
* Print each shortest transformation sequence on a separate line with words separated by spaces.
* Sequences should be sorted in lexicographical order.
* If no transformation sequence exists, print an empty line.

---

**Constraints**
* $1 \le N \le 100$
* $1 \le M \le 10$ (where $M$ is the length of each word)
* `startWord.length == targetWord.length == word[i].length`
* `startWord != targetWord`
* All words consist of lowercase English letters only.
* All words in `wordList` are unique.

---

**Example 1**
**Input:**
```text
der dfs 5
des
der
dfr
dgt
dfs
```

**Output:**
```text
der des dfs
der dfr dfs
```

**Explanation:**
The length of the shortest transformation sequence is 3 words.
There are two distinct paths of length 3:
1. `"der" -> "des" -> "dfs"`
2. `"der" -> "dfr" -> "dfs"`

Both paths are printed in lexicographical order.

---

**Example 2**
**Input:**
```text
gedk geek 2
geek
gefk
```

**Output:**
```text
gedk geek
```

**Explanation:**
The only shortest transformation sequence is `"gedk" -> "geek"` with length 2.

---

**Example 3**
**Input:**
```text
abc xyz 4
abc
ayc
ayz
xyz
```

**Output:**
```text
abc ayc ayz xyz
```

**Explanation:**
The unique shortest sequence is `"abc" -> "ayc" -> "ayz" -> "xyz"`.
