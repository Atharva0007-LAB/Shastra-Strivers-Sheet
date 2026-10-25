// Maximum Product Subarray - Solution/Solution.java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = scanner.nextInt();

        long maxProd = nums[0];
        long minProd = nums[0];
        long ans = nums[0];

        for (int i = 1; i < n; i++) {
            int x = nums[i];
            if (x < 0) {
                long tmp = maxProd;
                maxProd = minProd;
                minProd = tmp;
            }
            maxProd = Math.max((long) x, maxProd * x);
            minProd = Math.min((long) x, minProd * x);
            ans = Math.max(ans, maxProd);
        }

        System.out.println(ans);
    }
}
