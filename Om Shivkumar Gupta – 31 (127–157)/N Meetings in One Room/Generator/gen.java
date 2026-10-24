import java.io.*;
import java.util.*;

public class gen {
    static class Meeting {
        int start, end;
        Meeting(int s, int e) { this.start = s; this.end = e; }
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) {
            baseDir = "../TestCases";
        }
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random rand = new Random(133);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] start, end;

            if (tc == 0) {
                start = new int[]{1, 3, 0, 5, 8, 5};
                end   = new int[]{2, 4, 6, 7, 9, 9};
            } else if (tc == 1) {
                start = new int[]{1};
                end   = new int[]{2};
            } else if (tc == 2) {
                start = new int[]{1, 2, 3};
                end   = new int[]{10, 10, 10};
            } else if (tc == 3) {
                start = new int[]{1, 3, 5, 7};
                end   = new int[]{2, 4, 6, 8};
            } else if (tc == 4) {
                start = new int[]{1, 2, 0, 6, 3, 5, 8};
                end   = new int[]{3, 5, 6, 7, 9, 9, 10};
            } else {
                if (tc < 25) n = 20 + rand.nextInt(30);
                else if (tc < 65) n = 500 + rand.nextInt(1000);
                else n = 10000 + rand.nextInt(15000);

                start = new int[n];
                end = new int[n];

                int cur = 0;
                for (int i = 0; i < n; i++) {
                    int s = rand.nextInt(1000000);
                    int dur = 1 + rand.nextInt(500);
                    start[i] = s;
                    end[i] = s + dur;
                }
            }

            n = start.length;

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(start[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            for (int i = 0; i < n; i++) inPw.print(end[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            // Optimal Greedy
            Meeting[] m = new Meeting[n];
            for (int i = 0; i < n; i++) m[i] = new Meeting(start[i], end[i]);
            Arrays.sort(m, (a, b) -> Integer.compare(a.end, b.end));

            int count = 1;
            int lastEnd = m[0].end;
            for (int i = 1; i < n; i++) {
                if (m[i].start > lastEnd) {
                    count++;
                    lastEnd = m[i].end;
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(count);
            outPw.close();
        }
        System.out.println("N Meetings in One Room: 100 test cases generated.");
    }
}
