import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(152);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] nums;

            if (tc == 0) {
                nums = new int[]{1, 3, 2, 3, 1};
            } else if (tc == 1) {
                nums = new int[]{2, 4, 3, 5, 1};
            } else if (tc == 2) {
                nums = new int[]{5};
            } else if (tc == 3) {
                nums = new int[]{1, 2, 3, 4, 5};
            } else if (tc == 4) {
                nums = new int[]{2147483647, 2147483647, -2147483648};
            } else {
                n = (tc < 40) ? 20 + rand.nextInt(30) : 500 + rand.nextInt(2000);
                nums = new int[n];
                for (int i = 0; i < n; i++) nums[i] = rand.nextInt(200000) - 100000;
            }

            n = nums.length;

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int[] copy = nums.clone();
            int ans = solve(copy, 0, n - 1);

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Reverse Pairs: 100 test cases generated.");
    }

    static int solve(int[] nums, int l, int r) {
        if (l >= r) return 0;
        int m = l + (r - l) / 2;
        int count = solve(nums, l, m) + solve(nums, m + 1, r);
        int j = m + 1;
        for (int i = l; i <= m; i++) {
            while (j <= r && (long)nums[i] > 2L * nums[j]) j++;
            count += (j - (m + 1));
        }
        Arrays.sort(nums, l, r + 1);
        return count;
    }
}
