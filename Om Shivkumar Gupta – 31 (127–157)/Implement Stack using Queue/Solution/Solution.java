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
