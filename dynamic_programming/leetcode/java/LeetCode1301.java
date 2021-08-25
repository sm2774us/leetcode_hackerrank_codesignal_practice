import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class Solution {
    private static final int MOD = 1_000_000_007;
	private static final int unit = 1_000_000_007;

    /**
     *  Solution 1. Memoization (Top-Down)
     *
     *  TC: O(N^2)
     *  SC: O(N^2)
     */
    public int[] pathsWithMaxScoreTopDown(List<String> board) {
        int i = board.size();
        int j = board.get(0).length();
        
        int[][][] dp = new int[i][j][2];
        boolean[][] visited = new boolean[i][j];
        
        dp[i-1][j-1][1] = 1;
        visited[i-1][j-1] = true;
        
        return helper(dp, board, 0, 0, visited);
    }
    
    private int[] helper(int[][][] dp, List<String> board, int i, int j, boolean[][] visited) {
        int[] r = new int[2];
        
        if (i < 0 || j < 0 || i >= dp.length || j >= dp[0].length || 
           board.get(i).charAt(j) == 'X')
            return r;
        
        if (visited[i][j])
            return dp[i][j];
        
        int[] a1 = helper(dp, board, i+1, j, visited);
        int[] a2 = helper(dp, board, i+1, j+1, visited);
        int[] a3 = helper(dp, board, i, j+1, visited);
        
        r[0] = Math.max(Math.max(a1[0], a2[0]), a3[0]);
        if (a1[0] == r[0]) r[1] = (r[1]+ a1[1]) % MOD;
        if (a2[0] == r[0]) r[1] = (r[1]+ a2[1]) % MOD;
        if (a3[0] == r[0]) r[1] = (r[1]+ a3[1]) % MOD;
        
        if (board.get(i).charAt(j) != 'E' && r[1] != 0)
            r[0] = (r[0] + (board.get(i).charAt(j) - '0')) % MOD;
        
        dp[i][j] = r;
        visited[i][j] = true;
        
        return r;
            
    }

    /**
     *  Solution 2. Tabulation (Bottom-Up)
     *
     *  TC: O(N^2)
     *  SC: O(N^2)
     */
    public int[] pathsWithMaxScoreBottomUp(List<String> board) {
        int d = board.size();

        int[][][] statusArray = new int[d][d][3]; // max, number of unit, mod
        statusArray[d - 1][d - 1][2] = 1;

        for (int r = d - 1; r >= 0; r--) {
            for (int c = d - 1; c >= 0; c--) {
                char ch = board.get(r).charAt(c);

                if (ch == 'X') {
                    continue;
                }

                int currentNumber = ch == 'E' ? 0 : ch - '0';
                int[] status = statusArray[r][c];

                if (r + 1 <= d - 1) {
                    check(status, currentNumber, statusArray[r + 1][c]);

                    if (c + 1 <= d - 1) {
                        check(status, currentNumber, statusArray[r + 1][c + 1]);
                    }
                } 
                
                if (c + 1 <= d - 1) {
                    check(status, currentNumber, statusArray[r][c + 1]);
                }
                
            }
        }

        return new int[]{statusArray[0][0][0], statusArray[0][0][2]};
    }

    private void check(int[] status, int currentNumber, int[] previousStatus) {
        if (previousStatus[1] == 0 && previousStatus[2] == 0) {
            return;
        }

        if (previousStatus[0] + currentNumber > status[0]) {
            status[0] = previousStatus[0] + currentNumber;
            status[1] = previousStatus[1];
            status[2] = previousStatus[2];
        } else if (previousStatus[0] + currentNumber == status[0]) {
            status[1] += previousStatus[1];
            
            if (unit - previousStatus[2] <= status[2]) {
                status[2] = status[2] - unit + previousStatus[2];
                status[1]++;
            } else {
                status[2] += previousStatus[2];
            }
        }
    }
	
    @Test
    public void test() {
        assertArrayEquals(new int[]{34, 3}, new Solution().pathsWithMaxScoreTopDown(Arrays.asList("E11345","X452XX","3X43X4","422812","284522","13422S")));
        assertArrayEquals(new int[]{7, 1}, new Solution().pathsWithMaxScoreTopDown(Arrays.asList("E23","2X2","12S")));
        assertArrayEquals(new int[]{4, 2}, new Solution().pathsWithMaxScoreTopDown(Arrays.asList("E12","1X1","21S")));
        assertArrayEquals(new int[]{0, 0}, new Solution().pathsWithMaxScoreTopDown(Arrays.asList("E11","XXX","11S")));

        assertArrayEquals(new int[]{34, 3}, new Solution().pathsWithMaxScoreBottomUp(Arrays.asList("E11345","X452XX","3X43X4","422812","284522","13422S")));
        assertArrayEquals(new int[]{7, 1}, new Solution().pathsWithMaxScoreBottomUp(Arrays.asList("E23","2X2","12S")));
        assertArrayEquals(new int[]{4, 2}, new Solution().pathsWithMaxScoreBottomUp(Arrays.asList("E12","1X1","21S")));
        assertArrayEquals(new int[]{0, 0}, new Solution().pathsWithMaxScoreBottomUp(Arrays.asList("E11","XXX","11S")));
    }	
}