# Editorial: Rearrange Array Elements by Sign


## Difficulty

Medium


## Prerequisites
- Arrays
- Two Pointers


## Problem Statement

Given an array of even length with an equal number of positive and negative integers, rearrange it so that the signs alternate starting with a positive number, while keeping the original relative order of the positive numbers and of the negative numbers.


## Explanation & Approach

### Step 1: Fixed Slots for Each Sign

The answer must start with a positive number and alternate signs. So positive numbers go to the even indices 0, 2, 4, ... and negative numbers go to the odd indices 1, 3, 5, ... There are exactly n / 2 of each, so these slots are filled perfectly.

### Step 2: Fill the Slots in One Pass

Keep two write pointers, `pos = 0` for the next positive slot and `neg = 1` for the next negative slot. Scan the array from left to right. If the element is positive, write it at `res[pos]` and move `pos` by 2. If it is negative, write it at `res[neg]` and move `neg` by 2.

### Step 3: Order Is Preserved Automatically

Because the array is scanned from left to right and each sign is written to increasing slots, the positive numbers keep their original relative order, and so do the negative numbers. No sorting or extra step is needed.


## Example

### Input:

```text
2
6
3 1 -2 -5 2 -4
2
-1 1
```

### Trace:

Test case 1 (`3 1 -2 -5 2 -4`), pos = 0, neg = 1:
- 3 is positive, res[0] = 3, pos = 2
- 1 is positive, res[2] = 1, pos = 4
- -2 is negative, res[1] = -2, neg = 3
- -5 is negative, res[3] = -5, neg = 5
- 2 is positive, res[4] = 2, pos = 6
- -4 is negative, res[5] = -4, neg = 7

Result: `3 -2 1 -5 2 -4`

Test case 2 (`-1 1`):
- -1 is negative, res[1] = -1
- 1 is positive, res[0] = 1

Result: `1 -1`

Output:
```text
3 -2 1 -5 2 -4
1 -1
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
            int[] res = new int[n];

            int pos = 0; // next even index, for positive numbers
            int neg = 1; // next odd index, for negative numbers
            for (int i = 0; i < n; i++) {
                int x = scanner.nextInt();
                if (x > 0) {
                    res[pos] = x;
                    pos += 2;
                } else {
                    res[neg] = x;
                    neg += 2;
                }
            }

            for (int i = 0; i < n; i++) {
                sb.append(res[i]);
                sb.append(i != n - 1 ? " " : "\n");
            }
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Let $n$ be the size of the array in a test case. Each element is read once and written once.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(n)$ for the result array of a test case.
