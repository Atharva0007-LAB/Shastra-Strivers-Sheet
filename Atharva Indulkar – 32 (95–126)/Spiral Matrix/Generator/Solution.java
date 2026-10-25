// Spiral Matrix - Solution/Solution.java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        int top = 0, bottom = m - 1, left = 0, right = n - 1;
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) {
                if (!first) sb.append(" ");
                sb.append(matrix[top][j]);
                first = false;
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                if (!first) sb.append(" ");
                sb.append(matrix[i][right]);
                first = false;
            }
            right--;

            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    if (!first) sb.append(" ");
                    sb.append(matrix[bottom][j]);
                    first = false;
                }
                bottom--;
            }

            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    if (!first) sb.append(" ");
                    sb.append(matrix[i][left]);
                    first = false;
                }
                left++;
            }
        }
        System.out.println(sb);
    }
}
