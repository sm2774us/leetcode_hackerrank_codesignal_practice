/**
 *  Tabulation (Bottom-Up)
 *
 *  TC: O(N^2)
 *  SC: O(N^2)
 */
class Solution {
    public int minFallingPathSum(int[][] arr) {
        // time: O(m * n) 98.91%
        // space: O(1) 100%
        if (arr == null || arr.length == 0) return 0;
        int m = arr.length, n = arr[0].length;
        int fm = 0, sm = 0, pos = -1;
        for (int i = 0; i < m; i++) {
            int fm2 = Integer.MAX_VALUE, sm2 = Integer.MAX_VALUE, pos2 = -1;
            for (int j = 0; j < n; j++) {
                int cur = arr[i][j] + (j == pos ? sm : fm); // 当前的dp[i][j]
                if (cur < fm2) {
                    pos2 = j;
                    sm2 = fm2;
                    fm2 = cur;
                } else {
                    sm2 = Math.min(cur, sm2);
                }
            }
            fm = fm2; 
            sm = sm2;
            pos = pos2;
        }
        return fm;
    }
}