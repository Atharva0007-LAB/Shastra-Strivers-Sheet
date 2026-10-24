import java.util.Scanner;

public class Solution {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    /**
     * Finds the middle node using Tortoise and Hare (Slow and Fast pointers).
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        if (n == 0) return;

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int i = 0; i < n; i++) {
            curr.next = new ListNode(sc.nextInt());
            curr = curr.next;
        }

        Solution sol = new Solution();
        ListNode mid = sol.middleNode(dummy.next);

        curr = mid;
        while (curr != null) {
            System.out.print(curr.val + (curr.next == null ? "" : " "));
            curr = curr.next;
        }
        System.out.println();
        sc.close();
    }
}
