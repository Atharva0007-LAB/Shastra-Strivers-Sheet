import java.util.*;

public class Solution {
    static class Meeting {
        int start;
        int end;
        Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Finds maximum meetings possible using a greedy approach.
     */
    public int maxMeetings(int[] start, int[] end, int n) {
        Meeting[] meetings = new Meeting[n];
        for (int i = 0; i < n; i++) {
            meetings[i] = new Meeting(start[i], end[i]);
        }

        // Sort meetings according to their end times in ascending order
        Arrays.sort(meetings, (a, b) -> Integer.compare(a.end, b.end));

        int count = 1;
        int lastEndTime = meetings[0].end;

        for (int i = 1; i < n; i++) {
            // A meeting can start strictly after the last one ends
            if (meetings[i].start > lastEndTime) {
                count++;
                lastEndTime = meetings[i].end;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] start = new int[n];
        int[] end = new int[n];

        for (int i = 0; i < n; i++) {
            start[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            end[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        int ans = sol.maxMeetings(start, end, n);
        System.out.println(ans);
        sc.close();
    }
}
