### Problem 137: Group Words by Anagrams
**Difficulty:** Medium | **Tags:** String, Hash Table, Sorting

**Problem Statement:**
Given an array of strings `strs`, group the anagrams together.

An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

Output each anagram group on a separate line formatted as `[word1, word2, ...]`. The words within each group must be sorted alphabetically, and the groups themselves should be sorted in ascending order of their first word.

### Input Format
* The first line contains an integer `N`, representing the number of strings.
* The second line contains `N` space-separated strings.

### Output Format
* Print each grouped set of anagrams on a new line in the format `[word1, word2, ...]`.

### Constraints
* 1 <= N <= 10^4
* 0 <= strs[i].length <= 100
* strs[i] consists of lowercase English letters

### Example
#### Input:
```text
6
eat tea tan ate nat bat
```

#### Output:
```text
[ate, eat, tea]
[bat]
[nat, tan]
```

#### Explanation
* "eat", "tea", and "ate" are all anagrams of "aet".
* "bat" is alone.
* "tan" and "nat" are anagrams of "ant".
