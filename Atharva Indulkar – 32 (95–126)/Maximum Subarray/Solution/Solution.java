// Maximum Subarray - Solution/Solution.java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long maxSum = Long.MIN_VALUE;
        long curSum = 0;

        for (int i = 0; i < n; i++) {
            long x = scanner.nextLong();
            curSum += x;
            if (curSum > maxSum) {
                maxSum = curSum;
            }
            if (curSum < 0) {
                curSum = 0;
            }
        }

        System.out.println(maxSum);
    }
}
