// Rearrange Array Elements by Sign - Solution/Solution.java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] res = new int[n];

        int pos = 0; // next even index, for positive numbers
        int neg = 1; // next odd index, for negative numbers
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            if (x > 0) {
                res[pos] = x;
                pos += 2;
            } else {
                res[neg] = x;
                neg += 2;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(res[i]);
            if (i != n - 1) sb.append(" ");
        }
        System.out.println(sb);
    }
}