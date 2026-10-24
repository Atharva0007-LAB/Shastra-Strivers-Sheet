### Problem: Word Ladder I
**Difficulty:** Hard | **Tags:** Graphs, Breadth-First Search, Hash Table, String

**Problem Statement**
Given two distinct words `startWord` and `targetWord`, and a dictionary `wordList` containing $N$ unique words of equal length $M$.

Find the **length of the shortest transformation sequence** from `startWord` to `targetWord`.

A transformation sequence is a sequence of words `beginWord -> s1 -> s2 -> ... -> sk` such that:
* Every adjacent pair of words differs by exactly one letter.
* Every transformed word `s_i` ($1 \le i \le k$) must exist in `wordList`. Note that `targetWord` must be in `wordList`.
* `startWord` does not need to be in `wordList`.
* The length of the sequence is the total number of words in it ($k + 1$).

If no such transformation sequence is possible, return `0`.

---

**Input Format**
* The first line contains `startWord`, `targetWord`, and an integer $N$ (number of words in `wordList`), separated by space.
* The next $N$ lines each contain a single string representing a word in `wordList`.

**Output Format**
* Print a single integer representing the length of the shortest transformation sequence from `startWord` to `targetWord`, or `0` if no such sequence exists.

---

**Constraints**
* $1 \le N \le 1000$
* $1 \le M \le 10$ (where $M$ is the length of all words)
* `startWord.length == targetWord.length == word[i].length`
* `startWord != targetWord`
* All words consist of lowercase English alphabets only.
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
3
```

**Explanation:**
The shortest transformation sequence is `"der" -> "dfr" -> "dfs"`.
The sequence contains 3 words, so the length is `3`.

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
2
```

**Explanation:**
The shortest transformation sequence is `"gedk" -> "geek"`.
The sequence contains 2 words, so the output is `2`.

---

**Example 3**
**Input:**
```text
hit cog 5
hot
dot
dog
lot
log
```

**Output:**
```text
0
```

**Explanation:**
The `targetWord` `"cog"` is not in `wordList`, so it is impossible to transform into it.
Output is `0`.
