# Editorial: Leaders in an Array


## Difficulty

Easy


## Prerequisites
- Arrays
- Right-to-Left Traversal


## Problem Statement

Given an array `nums`, return all elements that are greater than or equal to all elements to their right, preserving their original left-to-right order.


## Explanation & Approach

### Right-to-Left Scan ($O(n)$)

A brute-force approach checks all elements to the right for every element, taking $O(n^2)$ time.

Instead, notice that an element is a leader if and only if:
$$nums[i] \ge \max(nums[i + 1 \dots n - 1])$$

By scanning from right to left ($i = n - 1$ down to 0):
1. Maintain `maxRight`, initialized to $-\infty$.
2. For each element `nums[i]`:
   - If `nums[i] >= maxRight`:
     - `nums[i]` is a leader!
     - Add `nums[i]` to our list of leaders.
     - Update `maxRight = nums[i]`.
3. Since we traversed from right to left, the leaders are in reverse order. Reverse the list at the end to restore the original order.


## Java Solution

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = scanner.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) nums[i] = scanner.nextInt();

            List<Integer> leaders = new ArrayList<>();
            int maxRight = Integer.MIN_VALUE;

            for (int i = n - 1; i >= 0; i--) {
                if (nums[i] >= maxRight) {
                    leaders.add(nums[i]);
                    maxRight = nums[i];
                }
            }

            Collections.reverse(leaders);

            for (int i = 0; i < leaders.size(); i++) {
                sb.append(leaders.get(i));
                if (i != leaders.size() - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Scanning the array once takes $O(n)$, and reversing the leaders list takes at most $O(n)$.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space beyond the output list.
