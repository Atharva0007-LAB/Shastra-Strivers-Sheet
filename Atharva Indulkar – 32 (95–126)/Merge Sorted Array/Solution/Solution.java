// Merge Sorted Array - Solution/Solution.java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[] nums1 = new int[m];
        int[] nums2 = new int[n];
        for (int i = 0; i < m; i++) nums1[i] = scanner.nextInt();
        for (int j = 0; j < n; j++) nums2[j] = scanner.nextInt();

        StringBuilder sb = new StringBuilder();
        int i = 0, j = 0;
        boolean first = true;

        while (i < m && j < n) {
            if (!first) sb.append(" ");
            if (nums1[i] <= nums2[j]) {
                sb.append(nums1[i++]);
            } else {
                sb.append(nums2[j++]);
            }
            first = false;
        }

        while (i < m) {
            if (!first) sb.append(" ");
            sb.append(nums1[i++]);
            first = false;
        }

        while (j < n) {
            if (!first) sb.append(" ");
            sb.append(nums2[j++]);
            first = false;
        }

        System.out.println(sb);
    }
}
