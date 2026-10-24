import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = scanner.nextInt();
            int sum = 0;
            for (int i = 0; i < n; i++) sum += scanner.nextInt();

            int expected = n * (n + 1) / 2; // sum of 0, 1, ..., n
            sb.append(expected - sum).append("\n");
        }
        System.out.print(sb);
    }
}
