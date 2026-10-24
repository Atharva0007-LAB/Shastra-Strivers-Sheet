import java.util.Scanner;

public class Solution {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    /**
     * Rotates linked list to the right by K places in O(N) time and O(1) space.
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        // Step 1: Calculate the length and locate the tail node
        ListNode tail = head;
        int length = 1;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Step 2: Make the list circular
        tail.next = head;

        // Step 3: Find the new tail at position (length - (k % length) - 1)
        k = k % length;
        int stepsToNewTail = length - k;
        ListNode newTail = tail;
        while (stepsToNewTail-- > 0) {
            newTail = newTail.next;
        }

        // Step 4: Break the circle
        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();

        if (n == 0) return;

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int i = 0; i < n; i++) {
            curr.next = new ListNode(sc.nextInt());
            curr = curr.next;
        }

        Solution sol = new Solution();
        ListNode rotatedHead = sol.rotateRight(dummy.next, k);

        curr = rotatedHead;
        while (curr != null) {
            System.out.print(curr.val + (curr.next == null ? "" : " "));
            curr = curr.next;
        }
        System.out.println();
        sc.close();
    }
}
