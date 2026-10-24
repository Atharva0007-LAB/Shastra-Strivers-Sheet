import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(151);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] nums;

            if (tc == 0) {
                n = 4; nums = new int[]{1, 2, 3, 1};
            } else if (tc == 1) {
                n = 7; nums = new int[]{1, 2, 1, 3, 5, 6, 4};
            } else if (tc == 2) {
                n = 1; nums = new int[]{10};
            } else if (tc == 3) {
                n = 2; nums = new int[]{1, 2};
            } else if (tc == 4) {
                n = 2; nums = new int[]{2, 1};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                nums = new int[n];
                int cur = 10;
                nums[0] = cur;
                for (int i = 1; i < n; i++) {
                    int diff = 1 + rand.nextInt(5);
                    if (rand.nextBoolean()) cur += diff;
                    else cur -= diff;
                    nums[i] = cur;
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int peak = 0;
            if (n == 1) peak = 0;
            else if (nums[0] > nums[1]) peak = 0;
            else if (nums[n - 1] > nums[n - 2]) peak = n - 1;
            else {
                int low = 1, high = n - 2;
                while (low <= high) {
                    int mid = low + (high - low) / 2;
                    if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) { peak = mid; break; }
                    else if (nums[mid] > nums[mid - 1]) low = mid + 1;
                    else high = mid - 1;
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(peak);
            outPw.close();
        }
        System.out.println("Problem 151 generated 100 test cases.");
    }
}
