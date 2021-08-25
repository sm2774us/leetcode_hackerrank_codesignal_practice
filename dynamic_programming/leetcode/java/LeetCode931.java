/**
 *  Recursion
 * 
 *  TC: O(3^N)
 *  SC: O(N)
 */
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        // try all possible goals
        int minPath = Integer.MAX_VALUE;
        for (int col = 0; col < matrix[0].length; col++) {
            minPath = Math.min(minPath, recurse(matrix, matrix.length - 1, col));
        }
        
        return minPath;
    }
    
    private int recurse(int[][] matrix, int row, int col) {
        // base case
        if (row == 0) {
            return matrix[row][col];
        }
        
        // default value
        int res = Integer.MAX_VALUE;
        
        // path from top left
        if (col > 0) {
            res = Math.min(res, recurse(matrix, row - 1, col - 1));
        }
        
        // path from top
        res = Math.min(res, recurse(matrix, row - 1, col));
        
        // path from top right
        if (col < matrix[0].length - 1) {
            res = Math.min(res, recurse(matrix, row - 1, col + 1));
        }
        
        return res + matrix[row][col];
    }
}

/**
 *  Memoization (Top-Down)
 *
 *  TC: O(N^2)
 *  SC: O(N^2)
 */
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        // initialize dp table
        Integer[][] memo = new Integer[matrix.length][matrix.length];
        for (int col = 0; col < matrix.length; col++) {
            memo[0][col] = matrix[0][col];
        }
        
        // try all possible goals
        int minPath = Integer.MAX_VALUE;
        for (int col = 0; col < matrix[0].length; col++) {
            minPath = Math.min(minPath, recurse(matrix, matrix.length - 1, col, memo));
        }
        
        return minPath;
    }
    
    private int recurse(int[][] matrix, int row, int col, Integer[][]memo) {
        // check dp table
        if (memo[row][col] != null) {
            return memo[row][col];
        }
        
        // default value
        int res = Integer.MAX_VALUE;
        
        // path from top left
        if (col > 0) {
            res = Math.min(res, recurse(matrix, row - 1, col - 1, memo));
        }
        
        // path from top
        res = Math.min(res, recurse(matrix, row - 1, col, memo));
        
        // path from top right
        if (col < matrix[0].length - 1) {
            res = Math.min(res, recurse(matrix, row - 1, col + 1, memo));
        }
        
        // update dp table
        memo[row][col] = res + matrix[row][col];
        return memo[row][col];
    }
}

/**
 *  Tabulation (Bottom-Up)
 *
 *  TC: O(N^2)
 *  SC: O(N^2)
 */
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        // initialize dp table
        Integer[][] memo = new Integer[n][n];
        for (int col = 0; col < n; col++) {
            memo[0][col] = matrix[0][col];
        }
        
        // bottom-up dp
        for (int row = 1; row < n; row++) {
            for (int col = 0; col < n; col++) {
                // default value
                int res = Integer.MAX_VALUE;

                // path from top left
                if (col > 0) {
                    res = Math.min(res, memo[row - 1][col - 1]);
                }

                // path from top
                res = Math.min(res, memo[row - 1][col]);

                // path from top right
                if (col < n - 1) {
                    res = Math.min(res, memo[row - 1][col + 1]);
                }

                // update dp table
                memo[row][col] = res + matrix[row][col];
            }
        }
        
        // try all possible goals
        int minPath = Integer.MAX_VALUE;
        for (int col = 0; col < matrix[0].length; col++) {
            minPath = Math.min(minPath, memo[n - 1][col]);
        }
        
        return minPath;
    }
}

/**
 *  Tabulation (Bottom-Up) with Space Optimization
 *
 *  TC: O(N^2)
 *  SC: O(N)
 */
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        // initialize dp table
        Integer[] memo = new Integer[n];
        for (int col = 0; col < n; col++) {
            memo[col] = matrix[0][col];
        }
        
        // bottom-up dp
        for (int row = 1; row < n; row++) {
            Integer[] prevMemo = memo;
            memo = new Integer[n];
            
            for (int col = 0; col < n; col++) {
                // default value
                int res = Integer.MAX_VALUE;

                // path from top left
                if (col > 0) {
                    res = Math.min(res, prevMemo[col - 1]);
                }

                // path from top
                res = Math.min(res, prevMemo[col]);

                // path from top right
                if (col < n - 1) {
                    res = Math.min(res, prevMemo[col + 1]);
                }

                // update dp table
                memo[col] = res + matrix[row][col];
            }
        }
        
        // try all possible goals
        int minPath = Integer.MAX_VALUE;
        for (int col = 0; col < n; col++) {
            minPath = Math.min(minPath, memo[col]);
        }
        
        return minPath;
    }
}