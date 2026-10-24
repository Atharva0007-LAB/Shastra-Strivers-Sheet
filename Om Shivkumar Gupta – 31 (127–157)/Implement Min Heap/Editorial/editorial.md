# 140. Implement Min Heap - Editorial

## Problem Statement
Implement a Min-Heap data structure from scratch supporting the following operations:
1. `push x`: Insert integer `x` into the min heap.
2. `pop`: Remove and return the minimum element from the min heap (or print `-1` if the heap is empty).
3. `top`: Return the minimum element without removing it (or print `-1` if the heap is empty).
4. `size`: Return the current number of elements in the heap.

---

## Sample Example

### Sample Input
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

### Sample Output
```text
2
2
4
1
4
```

### Explanation
* push(4) -> heap: [4]
* push(2) -> heap: [2, 4]
* top() -> returns 2
* pop() -> removes and returns 2 -> heap: [4]
* top() -> returns 4
* size() -> returns 1
* pop() -> removes and returns 4 -> heap: []

---

## Intuition
A Min-Heap is a complete binary tree where every parent node is less than or equal to its children.
It can be efficiently represented using a contiguous dynamic array (0-indexed):
- Left child of index `i` is `2*i + 1`
- Right child of index `i` is `2*i + 2`
- Parent of index `i` is `(i - 1) / 2`

This guarantees logarithmic insertions and extractions while keeping root lookup instantaneous.

## Approach
1. **`push(x)`**:
   - Append `x` to the end of the array.
   - Sift up (up-heapify) by repeatedly swapping with parent while `x < parent`. Time: `O(log N)`.
2. **`pop()`**:
   - If empty, return `-1`.
   - Save root value. Move the last element to the root position and remove the last index.
   - Sift down (down-heapify) by repeatedly swapping with the smaller child until heap property is restored. Time: `O(log N)`.
3. **`top()`**:
   - Return root `heap[0]` in `O(1)` (or `-1` if empty).
4. **`size()`**:
   - Return array size in `O(1)`.

---

## Complexity Analysis
* **Time Complexity:** push: O(log N), pop: O(log N), top: O(1), size: O(1). Overall O(Q log N).
* **Space Complexity:** O(N) to store up to N elements in the array list.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    static class MinHeap {
        private List<Integer> heap;

        public MinHeap() {
            heap = new ArrayList<>();
        }

        private int parent(int i) { return (i - 1) / 2; }
        private int leftChild(int i) { return 2 * i + 1; }
        private int rightChild(int i) { return 2 * i + 2; }

        private void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        public void push(int x) {
            heap.add(x);
            int current = heap.size() - 1;
            // Up-heapify
            while (current > 0 && heap.get(current) < heap.get(parent(current))) {
                swap(current, parent(current));
                current = parent(current);
            }
        }

        public int pop() {
            if (heap.isEmpty()) return -1;
            int minVal = heap.get(0);
            int lastVal = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, lastVal);
                // Down-heapify
                downHeapify(0);
            }
            return minVal;
        }

        private void downHeapify(int i) {
            int smallest = i;
            int left = leftChild(i);
            int right = rightChild(i);

            if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }
            if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }
            if (smallest != i) {
                swap(i, smallest);
                downHeapify(smallest);
            }
        }

        public int top() {
            if (heap.isEmpty()) return -1;
            return heap.get(0);
        }

        public int size() {
            return heap.size();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int q = sc.nextInt();
        MinHeap minHeap = new MinHeap();

        for (int i = 0; i < q; i++) {
            int type = sc.nextInt();
            if (type == 1) {
                int x = sc.nextInt();
                minHeap.push(x);
            } else if (type == 2) {
                System.out.println(minHeap.pop());
            } else if (type == 3) {
                System.out.println(minHeap.top());
            } else if (type == 4) {
                System.out.println(minHeap.size());
            }
        }
        sc.close();
    }
}
```
