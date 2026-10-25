# Editorial: Sort Colors


## Difficulty

Medium


## Prerequisites
- Arrays
- Dutch National Flag Algorithm (Three-Way Partitioning)


## Problem Statement

Given an array `nums` consisting only of `0`s, `1`s, and `2`s, sort the array in-place in a single pass using $O(1)$ extra space.


## Explanation & Approach

### Dutch National Flag Algorithm

This classic problem was formulated by Edsger Dijkstra. We can partition the array into four regions using three pointers: `low`, `mid`, and `high`:
1. `nums[0 ... low - 1]`: all `0`s
2. `nums[low ... mid - 1]`: all `1`s
3. `nums[mid ... high]`: unsorted / unknown elements
4. `nums[high + 1 ... n - 1]`: all `2`s

### Steps:
Initially:
- `low = 0`
- `mid = 0`
- `high = n - 1`

While `mid <= high`:
1. **If `nums[mid] == 0`**:
   - Swap `nums[low]` and `nums[mid]`.
   - Increment both `low++` and `mid++` (since `nums[low]` is now guaranteed to be 0 and `nums[mid]` receives a 1 or 0 that was already checked).
2. **If `nums[mid] == 1`**:
   - Element is already in the correct relative zone. Just advance `mid++`.
3. **If `nums[mid] == 2`**:
   - Swap `nums[mid]` and `nums[high]`.
   - Decrement `high--`.
   - **Do not** advance `mid` yet, because the element swapped from `high` has not been inspected yet!


## Example

### Input:

```text
2
6
2 0 2 1 1 0
3
2 0 1
```

### Trace:

Test case 1 (`[2, 0, 2, 1, 1, 0]`):
- `mid=0, high=5`: nums[0] == 2 -> swap(0, 5), high=4 -> `[0, 0, 2, 1, 1, 2]`
- `mid=0, high=4`: nums[0] == 0 -> swap(0, 0), low=1, mid=1 -> `[0, 0, 2, 1, 1, 2]`
- `mid=1, high=4`: nums[1] == 0 -> swap(1, 1), low=2, mid=2 -> `[0, 0, 2, 1, 1, 2]`
- `mid=2, high=4`: nums[2] == 2 -> swap(2, 4), high=3 -> `[0, 0, 1, 1, 2, 2]`
- `mid=2, high=3`: nums[2] == 1 -> mid=3
- `mid=3, high=3`: nums[3] == 1 -> mid=4
Loop terminates (`mid > high`).
Result: `0 0 1 1 2 2`.

Output:
```text
0 0 1 1 2 2
0 1 2
```


## Java Solution

```java
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

            int low = 0, mid = 0, high = n - 1;
            while (mid <= high) {
                if (nums[mid] == 0) {
                    swap(nums, low++, mid++);
                } else if (nums[mid] == 1) {
                    mid++;
                } else {
                    swap(nums, mid, high--);
                }
            }

            for (int i = 0; i < n; i++) {
                sb.append(nums[i]);
                if (i != n - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
```


## Time Complexity

Every step either increments `mid` or decrements `high`, which reduces the distance between `mid` and `high`. Hence the loop runs at most $n$ times.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ extra space, as the array is partitioned in-place.
