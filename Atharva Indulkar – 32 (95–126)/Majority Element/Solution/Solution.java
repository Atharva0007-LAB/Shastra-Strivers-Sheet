// Majority Element - Solution/Solution.java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int candidate = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            if (count == 0) {
                candidate = x;
            }
            if (x == candidate) {
                count++;
            } else {
                count--;
            }
        }

        System.out.println(candidate);
    }
}