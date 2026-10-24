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
