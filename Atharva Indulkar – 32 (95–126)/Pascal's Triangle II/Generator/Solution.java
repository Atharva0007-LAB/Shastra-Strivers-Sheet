// Pascal's Triangle II - Solution/Solution.java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rowIndex = scanner.nextInt();
        long val = 1;
        StringBuilder sb = new StringBuilder();
        sb.append(1);
        for (int i = 1; i <= rowIndex; i++) {
            val = val * (rowIndex - i + 1) / i;
            sb.append(" ").append(val);
        }
        System.out.println(sb);
    }
}
