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
