import java.util.Scanner;

public class Solution {
    private void swapIfGreater(long[] arr1, long[] arr2, int ind1, int ind2) {
        if (arr1[ind1] > arr2[ind2]) {
            long temp = arr1[ind1];
            arr1[ind1] = arr2[ind2];
            arr2[ind2] = temp;
        }
    }

    /**
     * Merges arr1 and arr2 in O((N+M) log(N+M)) time and O(1) space using the Gap Method (Shell Sort).
     */
    public void merge(long[] arr1, long[] arr2, int n, int m) {
        int len = n + m;
        int gap = (len / 2) + (len % 2); // ceil(len / 2)

        while (gap > 0) {
            int left = 0;
            int right = left + gap;

            while (right < len) {
                // Case 1: left in arr1, right in arr2
                if (left < n && right >= n) {
                    swapIfGreater(arr1, arr2, left, right - n);
                }
                // Case 2: both in arr2
                else if (left >= n) {
                    swapIfGreater(arr2, arr2, left - n, right - n);
                }
                // Case 3: both in arr1
                else {
                    swapIfGreater(arr1, arr1, left, right);
                }
                left++;
                right++;
            }

            if (gap == 1) break;
            gap = (gap / 2) + (gap % 2);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int m = sc.nextInt();

        long[] arr1 = new long[n];
        long[] arr2 = new long[m];

        for (int i = 0; i < n; i++) arr1[i] = sc.nextLong();
        for (int j = 0; j < m; j++) arr2[j] = sc.nextLong();

        Solution sol = new Solution();
        sol.merge(arr1, arr2, n, m);

        for (int i = 0; i < n; i++) {
            System.out.print(arr1[i] + (i == n - 1 ? "" : " "));
        }
        System.out.println();

        for (int j = 0; j < m; j++) {
            System.out.print(arr2[j] + (j == m - 1 ? "" : " "));
        }
        System.out.println();
        sc.close();
    }
}
