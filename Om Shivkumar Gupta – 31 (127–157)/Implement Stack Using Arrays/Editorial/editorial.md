# 144. Implement Stack Using Arrays - Editorial

## Problem Statement
Implement a Last-In-First-Out (LIFO) Stack using a contiguous array.
The stack should support the following operations:
1. `push x`: Insert integer `x` onto the top of the stack.
2. `pop`: Remove and print the top element of the stack (or print `-1` if the stack is empty).
3. `top`: Return the top element of the stack without removing it (or print `-1` if empty).
4. `isEmpty`: Print `1` if the stack is empty, else print `0`.

---

## Sample Example

### Sample Input
```text
6
1 10
1 20
3
2
4
2
```

### Sample Output
```text
20
20
0
10
```

### Explanation
* push(10) -> [10]
* push(20) -> [10, 20]
* top() -> 20
* pop() -> removes 20
* isEmpty() -> false (0)
* pop() -> removes 10

---

## Intuition
A stack operates in Last-In-First-Out (LIFO) order.
We maintain an array `arr[]` and a pointer `topIndex` initialized to `-1`.
- `push`: increment `topIndex` and store value.
- `pop`: return `arr[topIndex]` and decrement `topIndex`.
- `top`: return `arr[topIndex]`.
- `isEmpty`: check if `topIndex == -1`.
All operations run in strict O(1) time.

## Approach
1. Initialize an array of capacity `Q` and `topIndex = -1`.
2. For `push(x)`: set `arr[++topIndex] = x`.
3. For `pop()`: return `topIndex == -1 ? -1 : arr[topIndex--]`.
4. For `top()`: return `topIndex == -1 ? -1 : arr[topIndex]`.
5. For `isEmpty()`: return `topIndex == -1 ? 1 : 0`.

---

## Complexity Analysis
* **Time Complexity:** O(1) for each query, total O(Q).
* **Space Complexity:** O(Q) to store elements in the array.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    static class ArrayStack {
        private int[] arr;
        private int topIndex;

        public ArrayStack(int capacity) {
            arr = new int[capacity];
            topIndex = -1;
        }

        public void push(int x) {
            if (topIndex < arr.length - 1) {
                arr[++topIndex] = x;
            }
        }

        public int pop() {
            if (topIndex == -1) return -1;
            return arr[topIndex--];
        }

        public int top() {
            if (topIndex == -1) return -1;
            return arr[topIndex];
        }

        public int isEmpty() {
            return topIndex == -1 ? 1 : 0;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int q = sc.nextInt();
        ArrayStack stack = new ArrayStack(q + 10);

        for (int i = 0; i < q; i++) {
            int type = sc.nextInt();
            if (type == 1) {
                int x = sc.nextInt();
                stack.push(x);
            } else if (type == 2) {
                System.out.println(stack.pop());
            } else if (type == 3) {
                System.out.println(stack.top());
            } else if (type == 4) {
                System.out.println(stack.isEmpty());
            }
        }
        sc.close();
    }
}
```
