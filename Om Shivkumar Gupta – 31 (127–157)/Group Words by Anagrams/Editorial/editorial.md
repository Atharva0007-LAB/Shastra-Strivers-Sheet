# 137. Group Words by Anagrams - Editorial

## Problem Statement
Given an array of strings `strs`, group the anagrams together.

An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

Output each anagram group on a separate line formatted as `[word1, word2, ...]`. The words within each group must be sorted alphabetically, and the groups themselves should be sorted in ascending order of their first word.

---

## Sample Example

### Sample Input
```text
6
eat tea tan ate nat bat
```

### Sample Output
```text
[ate, eat, tea]
[bat]
[nat, tan]
```

### Explanation
* "eat", "tea", and "ate" are all anagrams of "aet".
* "bat" is alone.
* "tan" and "nat" are anagrams of "ant".

---

## Intuition
Two strings are anagrams if and only if they contain the exact same characters with identical frequencies.

Therefore, if we sort the characters of each string alphabetically:
`"eat" -> "aet"`
`"tea" -> "aet"`
`"ate" -> "aet"`
Every anagram maps to the exact same canonical key. We can use this sorted representation as the key in a Hash Map to group all matching words.

## Approach
1. Initialize a hash map mapping `String -> List<String>`.
2. For each word in `strs`:
   - Convert the word into a character array and sort it.
   - Construct a string from the sorted characters as `key`.
   - Append the original word to the list mapped to `key`.
3. Extract each group of anagrams:
   - Sort words within each group alphabetically.
   - Add group to `result`.
4. Sort the groups by their first word to maintain a deterministic output.
5. Return `result`.

---

## Complexity Analysis
* **Time Complexity:** O(N * K log K) where N is number of words and K is max word length.
* **Space Complexity:** O(N * K) to store the hash map and grouped lists.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    /**
     * Groups words that are anagrams of each other.
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String sortedKey = new String(chars);

            map.putIfAbsent(sortedKey, new ArrayList<>());
            map.get(sortedKey).add(s);
        }

        List<List<String>> result = new ArrayList<>();
        for (List<String> group : map.values()) {
            Collections.sort(group);
            result.add(group);
        }

        // Sort groups by their first element for deterministic order
        Collections.sort(result, (a, b) -> a.get(0).compareTo(b.get(0)));

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        String[] strs = new String[n];
        for (int i = 0; i < n; i++) {
            strs[i] = sc.next();
        }

        Solution sol = new Solution();
        List<List<String>> grouped = sol.groupAnagrams(strs);

        for (List<String> group : grouped) {
            System.out.print("[");
            for (int i = 0; i < group.size(); i++) {
                System.out.print(group.get(i) + (i == group.size() - 1 ? "" : ", "));
            }
            System.out.println("]");
        }
        sc.close();
    }
}
```
