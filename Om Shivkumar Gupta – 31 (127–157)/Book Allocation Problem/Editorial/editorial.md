# 154. Book Allocation Problem - Editorial

## Problem Statement
Given an array `arr` of `N` integers where `arr[i]` represents the number of pages in the `i-th` book, and an integer `M` representing the number of students.

Allocate books such that:
1. Each student gets at least one book.
2. Each student is assigned a contiguous segment of books.
3. The maximum number of pages allocated to any student is minimized.

If it is impossible to allocate books (e.g. `M > N`), return `-1`.

---

## Sample Example

### Sample Input
```text
4 2
12 34 67 90
```

### Sample Output
```text
113
```

### Explanation
* Possible contiguous allocations for 2 students:
  - [12] and [34, 67, 90] -> max pages = 191
  - [12, 34] and [67, 90] -> max pages = 157
  - [12, 34, 67] and [90] -> max pages = 113
* The minimum of maximum pages is 113.

---

## Intuition
This is a classic "Minimize the Maximum" problem.
Notice the monotonic property:
If a page barrier `P` allows allocating the books to $le M$ students, any barrier $> P$ will also be feasible.
If barrier `P` requires $> M$ students, any barrier $< P$ will require even more students.
Thus, we can apply Binary Search on the Answer between `max(arr)` and `sum(arr)`.

## Approach
1. If `M > N`, allocation is impossible; return `-1`.
2. Compute:
   - `low = max(arr)` (a student must at least hold the largest book)
   - `high = sum(arr)` (one student gets all books)
3. For midpoint `mid`:
   - Greedily count the number of students required using `countStudents(arr, mid)`.
   - If `students <= M`: record `ans = mid` and search left half (`high = mid - 1`).
   - Else: search right half (`low = mid + 1`).
4. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log(sum(arr) - max(arr))) as each binary search step takes O(N) linear scan.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    private int countStudents(long[] arr, long maxPages) {
        int students = 1;
        long pagesStudent = 0;

        for (long pages : arr) {
            if (pagesStudent + pages <= maxPages) {
                pagesStudent += pages;
            } else {
                students++;
                pagesStudent = pages;
            }
        }
        return students;
    }

    public long findPages(long[] arr, int n, int m) {
        if (m > n) return -1;

        long low = 0;
        long high = 0;

        for (long pages : arr) {
            low = Math.max(low, pages);
            high += pages;
        }

        long ans = high;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            int students = countStudents(arr, mid);

            if (students <= m) {
                ans = mid;
                high = mid - 1; // Try to minimize further
            } else {
                low = mid + 1;  // Not enough students, page limit too small
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int m = sc.nextInt();
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextLong();

        Solution sol = new Solution();
        long ans = sol.findPages(arr, n, m);
        System.out.println(ans);
        sc.close();
    }
}
```
