### Problem 155: Implement Stack using Queue
**Difficulty:** Easy | **Tags:** Stack, Queue, Data Structure

**Problem Statement:**
Implement a Last-In-First-Out (LIFO) stack using one or more queues.
The implemented stack should support:
1. `push x`: Pushes integer `x` onto the top of the stack.
2. `pop`: Removes the element on the top of the stack and returns it (or `-1` if empty).
3. `top`: Returns the element on the top of the stack without removing it (or `-1` if empty).
4. `empty`: Returns `1` if the stack is empty, `0` otherwise.

### Input Format
* The first line contains an integer `Q`, representing the number of queries.
* The next `Q` lines each contain an operation:
  - `1 x` for `push(x)`
  - `2` for `pop()`
  - `3` for `top()`
  - `4` for `empty()`

### Output Format
* For each query of type `2`, `3`, and `4`, print the result on a new line.

### Constraints
* 1 <= Q <= 10^5
* -10^9 <= x <= 10^9

### Example
#### Input:
```text
5
1 1
1 2
3
2
4
```

#### Output:
```text
2
2
0
```

#### Explanation
* push(1)
* push(2)
* top() -> returns 2
* pop() -> removes and returns 2
* empty() -> returns 0 (false)
