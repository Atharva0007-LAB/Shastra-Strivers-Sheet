### Problem 144: Implement Stack Using Arrays
**Difficulty:** Easy | **Tags:** Stack, Array, Data Structure

**Problem Statement:**
Implement a Last-In-First-Out (LIFO) Stack using a contiguous array.
The stack should support the following operations:
1. `push x`: Insert integer `x` onto the top of the stack.
2. `pop`: Remove and print the top element of the stack (or print `-1` if the stack is empty).
3. `top`: Return the top element of the stack without removing it (or print `-1` if empty).
4. `isEmpty`: Print `1` if the stack is empty, else print `0`.

### Input Format
* The first line contains an integer `Q`, representing the number of operations.
* The next `Q` lines each contain an operation:
  - `1 x` for `push(x)`
  - `2` for `pop()`
  - `3` for `top()`
  - `4` for `isEmpty()`

### Output Format
* For each operation of type `2`, `3`, and `4`, print the output on a new line.

### Constraints
* 1 <= Q <= 10^5
* -10^9 <= x <= 10^9

### Example
#### Input:
```text
6
1 10
1 20
3
2
4
2
```

#### Output:
```text
20
20
0
10
```

#### Explanation
* push(10) -> [10]
* push(20) -> [10, 20]
* top() -> 20
* pop() -> removes 20
* isEmpty() -> false (0)
* pop() -> removes 10
