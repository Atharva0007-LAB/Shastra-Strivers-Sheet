### Problem 154: Book Allocation Problem
**Difficulty:** Hard | **Tags:** Array, Binary Search

**Problem Statement:**
Given an array `arr` of `N` integers where `arr[i]` represents the number of pages in the `i-th` book, and an integer `M` representing the number of students.

Allocate books such that:
1. Each student gets at least one book.
2. Each student is assigned a contiguous segment of books.
3. The maximum number of pages allocated to any student is minimized.

If it is impossible to allocate books (e.g. `M > N`), return `-1`.

### Input Format
* The first line contains two integers `N` and `M`.
* The second line contains `N` space-separated integers representing the array `arr`.

### Output Format
* Print a single integer representing the minimized maximum number of pages, or `-1`.

### Constraints
* 1 <= N <= 10^5
* 1 <= M <= 10^5
* 1 <= arr[i] <= 10^9

### Example
#### Input:
```text
4 2
12 34 67 90
```

#### Output:
```text
113
```

#### Explanation
* Possible contiguous allocations for 2 students:
  - [12] and [34, 67, 90] -> max pages = 191
  - [12, 34] and [67, 90] -> max pages = 157
  - [12, 34, 67] and [90] -> max pages = 113
* The minimum of maximum pages is 113.
