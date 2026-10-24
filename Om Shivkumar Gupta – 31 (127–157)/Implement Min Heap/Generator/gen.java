import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(140);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int q;
            List<String> queries = new ArrayList<>();

            if (tc == 0) {
                queries.add("1 4"); queries.add("1 2"); queries.add("3");
                queries.add("2"); queries.add("3"); queries.add("4"); queries.add("2");
            } else if (tc == 1) {
                // Empty heap checks
                queries.add("3"); queries.add("2"); queries.add("4");
            } else if (tc == 2) {
                queries.add("1 10"); queries.add("1 20"); queries.add("1 5");
                queries.add("3"); queries.add("2"); queries.add("2"); queries.add("2"); queries.add("2");
            } else if (tc == 3) {
                // Single element repeatedly
                for (int i = 0; i < 5; i++) {
                    queries.add("1 42");
                    queries.add("3");
                    queries.add("2");
                }
            } else if (tc == 4) {
                queries.add("1 -5"); queries.add("1 -10"); queries.add("1 0");
                queries.add("3"); queries.add("4"); queries.add("2"); queries.add("2");
            } else {
                q = (tc < 40) ? 200 + rand.nextInt(300) : 1000 + rand.nextInt(3000);
                for (int i = 0; i < q; i++) {
                    int op = 1 + rand.nextInt(4);
                    if (op == 1) {
                        queries.add("1 " + (rand.nextInt(100000) - 50000));
                    } else {
                        queries.add(String.valueOf(op));
                    }
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(queries.size());
            for (String line : queries) inPw.println(line);
            inPw.close();

            // Simulate with PriorityQueue
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));

            for (String line : queries) {
                String[] parts = line.split(" ");
                int type = Integer.parseInt(parts[0]);
                if (type == 1) {
                    pq.add(Integer.parseInt(parts[1]));
                } else if (type == 2) {
                    outPw.println(pq.isEmpty() ? -1 : pq.poll());
                } else if (type == 3) {
                    outPw.println(pq.isEmpty() ? -1 : pq.peek());
                } else if (type == 4) {
                    outPw.println(pq.size());
                }
            }
            outPw.close();
        }
        System.out.println("Implement Min Heap: 100 test cases generated.");
    }
}
