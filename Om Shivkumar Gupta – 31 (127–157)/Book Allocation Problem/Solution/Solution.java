import java.util.Scanner;

public class Solution {
    private int countStudents(long[] arr, long maxPages) {
        int students = 1;
        long pagesStudent = 0;

        for (long pages : arr) {
            if (pagesStudent + pages <= maxPages) {
                pagesStudent += pages;
            } else {
                students++;
                pagesStudent = pages;
            }
        }
        return students;
    }

    public long findPages(long[] arr, int n, int m) {
        if (m > n) return -1;

        long low = 0;
        long high = 0;

        for (long pages : arr) {
            low = Math.max(low, pages);
            high += pages;
        }

        long ans = high;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            int students = countStudents(arr, mid);

            if (students <= m) {
                ans = mid;
                high = mid - 1; // Try to minimize further
            } else {
                low = mid + 1;  // Not enough students, page limit too small
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int m = sc.nextInt();
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextLong();

        Solution sol = new Solution();
        long ans = sol.findPages(arr, n, m);
        System.out.println(ans);
        sc.close();
    }
}
