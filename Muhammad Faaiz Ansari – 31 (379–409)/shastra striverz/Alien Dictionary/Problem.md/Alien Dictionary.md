### Problem: Alien Dictionary
**Difficulty:** Hard | **Tags:** Graphs, Topological Sort, Breadth-First Search, Depth-First Search, String

**Problem Statement**
Given a sorted dictionary of an alien language having $N$ words and $K$ starting alphabets of the standard English alphabet (from `'a'` up to the $K$-th alphabet letter).

Find the **order of characters** in the alien language.

* There may be multiple valid character orderings for a particular test case; any valid ordering is accepted.
* If the given arrangement of words is inconsistent with any possible letter ordering (contains a contradiction/cycle or a prefix violation such as `"abc"` appearing before `"ab"`), output an empty line (or empty string).

---

**Input Format**
* The first line contains two space-separated integers $N$ (number of words) and $K$ (number of distinct letters from `'a'`).
* The next $N$ lines each contain a single string representing a word in the sorted alien dictionary.

**Output Format**
* Print $K$ space-separated characters representing a valid character ordering in the alien language.
* If no valid ordering exists, print an empty line.

---

**Constraints**
* $1 \le N \le 300$
* $1 \le K \le 26$
* $1 \le \text{length of each word} \le 50$
* Each word contains only the first $K$ lowercase English letters (`'a'` to the $K$-th letter).

---

**Example 1**
**Input:**
```text
5 4
baa
abcd
abca
cab
cad
```

**Output:**
```text
b d a c
```

**Explanation:**
Compare consecutive words by their first differing character:
* `"baa"` and `"abcd"` first differ at index 0: `'b'` vs `'a'`. Since `"baa"` appears first, `'b'` comes before `'a'`.
* `"abcd"` and `"abca"` first differ at index 3: `'d'` vs `'a'`. Therefore, `'d'` comes before `'a'`.
* `"abca"` and `"cab"` first differ at index 0: `'a'` vs `'c'`. Therefore, `'a'` comes before `'c'`.
* `"cab"` and `"cad"` first differ at index 2: `'b'` vs `'d'`. Therefore, `'b'` comes before `'d'`.

Combining all constraints gives:
* `'b'` comes before `'d'`
* `'d'` comes before `'a'`
* `'a'` comes before `'c'`

A valid topological ordering is: `b d a c`.

---

**Example 2**
**Input:**
```text
3 3
caa
aaa
aab
```

**Output:**
```text
c a b
```

**Explanation:**
* Comparing `"caa"` and `"aaa"` gives `'c'` before `'a'`.
* Comparing `"aaa"` and `"aab"` gives `'a'` before `'b'`.
* A valid ordering is: `c a b`.

---

**Example 3**
**Input:**
```text
3 3
abc
bca
cab
```

**Output:**
```text
a b c
```

**Explanation:**
* `"abc"` and `"bca"` differ at index 0: `'a'` before `'b'`.
* `"bca"` and `"cab"` differ at index 0: `'b'` before `'c'`.
* A valid ordering is: `a b c`.
