### Problem 140: Implement Min Heap
**Difficulty:** Medium | **Tags:** Heap, Data Structure

**Problem Statement:**
Implement a Min-Heap data structure from scratch supporting the following operations:
1. `push x`: Insert integer `x` into the min heap.
2. `pop`: Remove and return the minimum element from the min heap (or print `-1` if the heap is empty).
3. `top`: Return the minimum element without removing it (or print `-1` if the heap is empty).
4. `size`: Return the current number of elements in the heap.

### Input Format
* The first line contains an integer `Q`, representing the number of queries.
* The next `Q` lines each contain a query:
  - `1 x` for `push(x)`
  - `2` for `pop()`
  - `3` for `top()`
  - `4` for `size()`

### Output Format
* For each query of type `2`, `3`, and `4`, print the result on a new line.

### Constraints
* 1 <= Q <= 10^5
* -10^9 <= x <= 10^9

### Example
#### Input:
```text
7
1 4
1 2
3
2
3
4
2
```

#### Output:
```text
2
2
4
1
4
```

#### Explanation
* push(4) -> heap: [4]
* push(2) -> heap: [2, 4]
* top() -> returns 2
* pop() -> removes and returns 2 -> heap: [4]
* top() -> returns 4
* size() -> returns 1
* pop() -> removes and returns 4 -> heap: []
