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

            int m, n;
            int[] nums1, nums2;

            if (tc == 0) {
                m = 2; n = 2;
                nums1 = new int[]{1, 3}; nums2 = new int[]{2, 4};
            } else if (tc == 1) {
                m = 2; n = 1;
                nums1 = new int[]{1, 2}; nums2 = new int[]{3};
            } else if (tc == 2) {
                m = 0; n = 2;
                nums1 = new int[]{}; nums2 = new int[]{2, 3};
            } else if (tc == 3) {
                m = 1; n = 1;
                nums1 = new int[]{5}; nums2 = new int[]{10};
            } else if (tc == 4) {
                m = 3; n = 3;
                nums1 = new int[]{1, 5, 9}; nums2 = new int[]{2, 6, 10};
            } else {
                if (tc < 25) { m = 5 + rand.nextInt(20); n = 5 + rand.nextInt(20); }
                else if (tc < 65) { m = 500 + rand.nextInt(1000); n = 500 + rand.nextInt(1000); }
                else { m = 10000 + rand.nextInt(15000); n = 10000 + rand.nextInt(15000); }

                nums1 = new int[m];
                int cur = 0;
                for (int i = 0; i < m; i++) { cur += rand.nextInt(3); nums1[i] = cur; }
                nums2 = new int[n];
                cur = 0;
                for (int i = 0; i < n; i++) { cur += rand.nextInt(3); nums2[i] = cur; }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(m + " " + n);
            for (int i = 0; i < m; i++) inPw.print(nums1[i] + (i == m - 1 ? "" : " "));
            if (m > 0) inPw.println();
            for (int i = 0; i < n; i++) inPw.print(nums2[i] + (i == n - 1 ? "" : " "));
            if (n > 0) inPw.println();
            inPw.close();

            int[] merged = new int[m + n];
            int i = 0, j = 0, k = 0;
            while (i < m && j < n) {
                if (nums1[i] <= nums2[j]) merged[k++] = nums1[i++];
                else merged[k++] = nums2[j++];
            }
            while (i < m) merged[k++] = nums1[i++];
            while (j < n) merged[k++] = nums2[j++];

            double ans;
            int total = m + n;
            if (total % 2 == 1) ans = merged[total / 2];
            else ans = (merged[total / 2 - 1] + merged[total / 2]) / 2.0;

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(String.format(Locale.US, "%.5f", ans));
            outPw.close();
        }
        System.out.println("Problem 152 generated 100 test cases.");
    }
}
