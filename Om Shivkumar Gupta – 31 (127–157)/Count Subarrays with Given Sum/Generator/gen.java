import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(127);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, k;
            int[] nums;

            if (tc == 0) {
                n = 3; k = 2;
                nums = new int[]{1, 1, 1};
            } else if (tc == 1) {
                n = 3; k = 3;
                nums = new int[]{1, 2, 3};
            } else if (tc == 2) {
                n = 1; k = 5;
                nums = new int[]{5};
            } else if (tc == 3) {
                n = 4; k = 0;
                nums = new int[]{1, -1, 0, 2};
            } else if (tc == 4) {
                n = 5; k = -2;
                nums = new int[]{-1, -1, 1, -1, -1};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                k = rand.nextInt(200) - 100;
                nums = new int[n];
                for (int i = 0; i < n; i++) {
                    nums[i] = rand.nextInt(201) - 100;
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + k);
            for (int i = 0; i < n; i++) inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int count = 0;
            long cur = 0;
            Map<Long, Integer> map = new HashMap<>();
            map.put(0L, 1);
            for (int x : nums) {
                cur += x;
                count += map.getOrDefault(cur - k, 0);
                map.put(cur, map.getOrDefault(cur, 0) + 1);
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(count);
            outPw.close();
        }
        System.out.println("Problem 127 generated 100 test cases.");
    }
}
