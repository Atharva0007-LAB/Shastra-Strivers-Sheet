import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(150);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] arr, dep;

            if (tc == 0) {
                n = 6;
                arr = new int[]{900, 940, 950, 1100, 1500, 1800};
                dep = new int[]{910, 1200, 1120, 1130, 1900, 2000};
            } else if (tc == 1) {
                n = 1; arr = new int[]{1000}; dep = new int[]{1030};
            } else if (tc == 2) {
                n = 3; arr = new int[]{900, 910, 920}; dep = new int[]{905, 915, 925};
            } else if (tc == 3) {
                // All overlapping
                n = 4; arr = new int[]{100, 100, 100, 100}; dep = new int[]{500, 500, 500, 500};
            } else if (tc == 4) {
                n = 5; arr = new int[]{100, 200, 300, 400, 500}; dep = new int[]{150, 250, 350, 450, 550};
            } else {
                n = (tc < 40) ? 20 + rand.nextInt(30) : 500 + rand.nextInt(1500);
                arr = new int[n];
                dep = new int[n];
                for (int i = 0; i < n; i++) {
                    int a = rand.nextInt(800000);
                    int dur = 1 + rand.nextInt(500);
                    arr[i] = a;
                    dep[i] = a + dur;
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(arr[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            for (int i = 0; i < n; i++) inPw.print(dep[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            // Optimal solve
            int[] sa = arr.clone(), sd = dep.clone();
            Arrays.sort(sa); Arrays.sort(sd);
            int cur = 0, mx = 0, i = 0, j = 0;
            while (i < n && j < n) {
                if (sa[i] <= sd[j]) { cur++; mx = Math.max(mx, cur); i++; }
                else { cur--; j++; }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(mx);
            outPw.close();
        }
        System.out.println("Platforms Required: 100 test cases generated.");
    }
}
