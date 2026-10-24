import java.util.Scanner;

public class Solution {
    /**
     * Computes all-pairs shortest paths using the Floyd-Warshall Algorithm.
     * 
     * @param matrix Adjacency matrix of size V x V where -1 denotes no edge.
     */
    public void shortestDistance(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Replace unreachable (-1) with a large sentinel value,
        // while keeping 0 on the diagonal.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == -1) {
                    matrix[i][j] = (int) 1e9;
                }
                if (i == j) {
                    matrix[i][j] = 0;
                }
            }
        }

        // Step 2: Floyd-Warshall dynamic programming step
        // Try every vertex k as an intermediate point between i and j
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][k] < 1e9 && matrix[k][j] < 1e9) {
                        matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                    }
                }
            }
        }

        // Step 3: Convert unreachable entries back to -1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] >= 1e9) {
                    matrix[i][j] = -1;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        Solution sol = new Solution();
        sol.shortestDistance(matrix);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + (j == n - 1 ? "" : " "));
            }
            System.out.println();
        }
        sc.close();
    }
}
