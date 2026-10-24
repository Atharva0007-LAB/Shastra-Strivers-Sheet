import java.util.*;

public class Solution {
    /**
     * Finds the kth element of two sorted arrays in O(log(min(m, n))).
     */
    public long kthElement(int[] arr1, int[] arr2, int m, int n, int k) {
        if (m > n) return kthElement(arr2, arr1, n, m, k);

        int low = Math.max(0, k - n);
        int high = Math.min(k, m);

        while (low <= high) {
            int mid1 = (low + high) / 2;
            int mid2 = k - mid1;

            int l1 = (mid1 > 0) ? arr1[mid1 - 1] : Integer.MIN_VALUE;
            int l2 = (mid2 > 0) ? arr2[mid2 - 1] : Integer.MIN_VALUE;
            int r1 = (mid1 < m) ? arr1[mid1] : Integer.MAX_VALUE;
            int r2 = (mid2 < n) ? arr2[mid2] : Integer.MAX_VALUE;

            if (l1 <= r2 && l2 <= r1) {
                return Math.max(l1, l2);
            } else if (l1 > r2) {
                high = mid1 - 1;
            } else {
                low = mid1 + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int m = sc.nextInt();
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] arr1 = new int[m];
        for (int i = 0; i < m; i++) arr1[i] = sc.nextInt();
        int[] arr2 = new int[n];
        for (int i = 0; i < n; i++) arr2[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.kthElement(arr1, arr2, m, n, k));
        sc.close();
    }
}
