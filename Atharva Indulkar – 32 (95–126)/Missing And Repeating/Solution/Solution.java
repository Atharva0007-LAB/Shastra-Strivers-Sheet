// Missing And Repeating - Solution/Solution.java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long s = 0;
        long s2 = 0;

        for (int i = 0; i < n; i++) {
            long x = scanner.nextLong();
            s += x;
            s2 += x * x;
        }

        long sn = (long) n * (n + 1) / 2;
        long s2n = (long) n * (n + 1) * (2L * n + 1) / 6;

        long diff1 = s - sn;
        long diff2 = s2 - s2n;
        long sum = diff2 / diff1;

        long b = (diff1 + sum) / 2;
        long a = sum - b;

        System.out.println(b + " " + a);
    }
}
