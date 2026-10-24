import java.util.*;

public class Solution {
    private int countStudents(int[] arr, long maxPages) {
        int students = 1;
        long pages = 0;
        for (int p : arr) {
            if (pages + p > maxPages) {
                students++;
                pages = p;
            } else {
                pages += p;
            }
        }
        return students;
    }

    public long findPages(int[] arr, int n, int m) {
        if (m > n) return -1;

        long low = 0;
        long high = 0;
        for (int p : arr) {
            low = Math.max(low, p);
            high += p;
        }

        long ans = high;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (countStudents(arr, mid) <= m) {
                ans = mid;
                high = mid - 1; // Try smaller maximum
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.findPages(arr, n, m));
        sc.close();
    }
}
