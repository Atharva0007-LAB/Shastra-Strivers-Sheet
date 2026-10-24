# 155. Implement Stack using Queue - Editorial

## Problem Statement
Implement a Last-In-First-Out (LIFO) stack using one or more queues.
The implemented stack should support:
1. `push x`: Pushes integer `x` onto the top of the stack.
2. `pop`: Removes the element on the top of the stack and returns it (or `-1` if empty).
3. `top`: Returns the element on the top of the stack without removing it (or `-1` if empty).
4. `empty`: Returns `1` if the stack is empty, `0` otherwise.

---

## Sample Example

### Sample Input
```text
5
1 1
1 2
3
2
4
```

### Sample Output
```text
2
2
0
```

### Explanation
* push(1)
* push(2)
* top() -> returns 2
* pop() -> removes and returns 2
* empty() -> returns 0 (false)

---

## Intuition
A queue operates in First-In-First-Out (FIFO) order, while a stack operates in Last-In-First-Out (LIFO) order.
We can implement a stack using a SINGLE Queue:
Every time an element `x` is pushed:
1. Enqueue `x` at the back.
2. Dequeue all earlier `size - 1` elements and enqueue them back behind `x`.
Now `x` sits at the front of the queue, making `pop()` and `top()` instantaneous O(1) operations!

## Approach
1. Use a single `LinkedList<Integer>` queue.
2. `push(x)`:
   - `q.add(x)`
   - Repeat `q.size() - 1` times: `q.add(q.remove())`.
3. `pop()`: return `q.isEmpty() ? -1 : q.remove()`.
4. `top()`: return `q.isEmpty() ? -1 : q.peek()`.
5. `empty()`: return `q.isEmpty() ? 1 : 0`.

---

## Complexity Analysis
* **Time Complexity:** push: O(N), pop: O(1), top: O(1), empty: O(1).
* **Space Complexity:** O(N) to store elements in the queue.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    static class MyStack {
        private Queue<Integer> q;

        public MyStack() {
            q = new LinkedList<>();
        }

        public void push(int x) {
            q.add(x);
            // Rotate the previously added elements to the back of the queue
            for (int i = 0; i < q.size() - 1; i++) {
                q.add(q.remove());
            }
        }

        public int pop() {
            if (q.isEmpty()) return -1;
            return q.remove();
        }

        public int top() {
            if (q.isEmpty()) return -1;
            return q.peek();
        }

        public int empty() {
            return q.isEmpty() ? 1 : 0;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int numQueries = sc.nextInt();
        MyStack stack = new MyStack();

        for (int i = 0; i < numQueries; i++) {
            int type = sc.nextInt();
            if (type == 1) {
                int x = sc.nextInt();
                stack.push(x);
            } else if (type == 2) {
                System.out.println(stack.pop());
            } else if (type == 3) {
                System.out.println(stack.top());
            } else if (type == 4) {
                System.out.println(stack.empty());
            }
        }
        sc.close();
    }
}
```
