# Dynamic Programming | Number Tower
# [LC-931 : Minimum Falling Path Sum](https://leetcode.com/problems/minimum-falling-path-sum/)

## Approach
The goal is to reach the last row while accumulating minimum values. What are all the possible goals? 
All the columns in the last row can be considered as one of the goal. So we can simulate the path for each of these goals 
by tracing back the minimum path. This can be done using recursion and optimized using dynamic programming. 
Finally, we will pick the best among these goals.

## Tip:

Start with Top Down DP, it is conceptually easier, then flip to Bottom Up DP
which is generally faster. Ask below for more details on how to do this.

## Algorithm:

Try starting from every location in the top row.
At each iteration, there are at most 3 choices:

  1. move down left
  2. move down
  3. move down right

Try each option by recursively calling the helper function for each choice.
Then return the cost of the current location plus the choice with the minimum cost.
At the final row `i == len(matrix)` return 0 because there are no more squares to consider.

## Solutions
N = length of side of matrix

### 1. Recursion
Time complexity: `O(3^N)`
Space complexity: `O(N)`
---
#### Java Solution
---
```java
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
```

### 2. Memoization (Top-Down)
Time complexity: `O(N^2)`
Space complexity: `O(N^2)`
---
#### Java Solution
---
```java
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
```
---
#### Python Solution
---
```python
import functools
from typing import List

class Solution:
    def minFallingPathSum(self, matrix: List[List[int]]) -> int:
        @functools.lru_cache(None)
        def helper(i, j):
            if i == len(matrix):
                return 0
            return matrix[i][j] + min(helper(i+1, k) for k in range(max(0, j-1), min(len(matrix[0]), j+2)))
        return min(helper(0, k) for k in range(0, len(matrix[0])))


    def minFallingPathSum(self, A: List[List[int]]) -> int:
        n = len(A) # its a square so col and row will be of same size
        dp = [ [ float('inf') for i in range(n+1)] for j in range(n+1) ]
        def helper(A,i,j):
            if j<0 or i>=n or j>=n: 
                return float('inf')
            if i == n-1: # if you are at last column of zero row
                return A[i][j]
            if dp[i][j] != float('inf'):
                return dp[i][j]
            dp[i][j] = A[i][j] + min( helper(A,i+1,j-1), # can have three choice like a node having three children
                                     helper(A,i+1,j),                # (i, j)
                                     helper(A,i+1,j+1))            #  /   |     \
                                                                  #  /    |      \
														   #(i+1,j-1)  (i+1,j) (i+1,j+1)
		return dp[i][j]
        mini = float('inf')
        for i in range(n):
            mini = min(mini,helper(A,0,i)) # you can start from any column of zero row 
        return mini
```
---
#### JavaScript Solution
---
```JavaScript
/**
 * @param {number[][]} A
 * @return {number}
 */
let maxVal = Number.MAX_SAFE_INTEGER;
var minFallingPathSum = function(A) {
    
    let row = A.length;
    let col = A[0].length;
    
    let dp = [];
    
    for(let i = 0; i < row; i++){
        dp.push(new Array(col).fill(0));
    }
    
    let min = maxVal;
    
    let minVal = Number.MAX_SAFE_INTEGER;
    for(let i = 0; i < col; i++){
        min = Math.min( min, dfs(0,i,A[0][i],A,minVal,dp));
    }
    
    return min;
};


let dfs = function(i,j,val,A,minVal,dp){
    if(i < 0 || i > A.length-1 || j < 0 || j > A[0].length-1) return maxVal;
    
    if(i == A.length-1) return A[i][j];
    
    if(dp[i][j]!==0) return dp[i][j];
    
    let currentVal = 0;
    
    if(i==0) currentVal = val;
    else currentVal = val + A[i][j];
    
    //down
    let left = dfs(i+1,j,currentVal,A,minVal,dp);
    
    //down one step left
    let mid = dfs(i+1,j-1,currentVal,A,minVal,dp);
    
    //down one step right
    let right = dfs(i+1,j+1,currentVal,A,minVal,dp);
    
    
    if(left == maxVal  && mid == maxVal && right == maxVal) minVal= currentVal;
    else minVal = Math.min(left,mid,right) + A[i][j];
    
    dp[i][j] = minVal;
    
     return dp[i][j];    
}
```

### 3. Tabulation (Bottom-Up)
Time complexity: `O(N^2)`
Space complexity: `O(N^2)`
---
#### Java Solution
---
```java
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
```
---
#### Python Solution
---
```python
class Solution:
    def minFallingPathSum(self, matrix: List[List[int]]) -> int:
	    m, n = len(A), len(A[0])
        MAX = float('inf')
		
        # dp[i][j] - minimum falling path till grid[i][j], inclusive
        dp = [[0] * n for _ in range(m)]

        # copies A[0] into dp, careful about deepcopy or shallowcopy
        dp[0][:] = A[0][:]  # works here as copying List[int] (not a compound object)

        for i in range(1, m):
            for j in range(n):
                top_left = dp[i-1][j-1] if j-1 >= 0 else MAX
                top = dp[i-1][j]
                top_right = dp[i-1][j+1] if j+1 < n else MAX
                if j == 0 or j == n-1:      # first column or last column
                    dp[i][j] = min(top_left, top, top_right) + A[i][j]
                    continue

                dp[i][j] = min(top_left, top, top_right) + A[i][j]
        return min(dp[-1][:])
```
---
#### JavaScript Solution
---
```JavaScript
/**
 *  Why is this necessary ?
 *  ======================= 
 *    - fill().map(a=> new Array(N).fill(Infinity)) vs just .fill([]) or .fill(new Array())
 *
 *  The latter version will fail with some strange behavior, resulting in incorrectly accessing the dp matrix..
 *
 *  Array.fill will pass reference to this object rather than new instance of it for each position, 
 *  thus each update to dp[r][c] makes update to ALL rows... hence why mapping the undefined values to return a new array 
 *  will do the trick
 */
var minFallingPathSum = function(matrix) {
  const M = matrix.length
  const N = matrix[0].length
  const dp = new Array(M).fill().map(a=> new Array(N).fill(Infinity))
  dp[0] = matrix[0]
  for(let r=1;r<M;r++){
    for(let c=0;c<N;c++){
      const curr = matrix[r][c]
      const top= curr + dp[r-1][c]
      const topL= curr+ (dp[r-1][c-1] || Infinity)
      const topR= curr+ (dp[r-1][c+1] || Infinity)
      dp[r][c] = Math.min(top, topL, topR)
    }    
  }
  return Math.min(...dp[M-1])
};
```

### 4. Tabulation (Bottom-Up) with Space Optimization
Time complexity: `O(N^2)`
Space complexity: `O(N)`
---
#### Java Solution
---
```java
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
```
---
#### Python Solution
---
```python
class Solution:
    def minFallingPathSum(self, A: List[List[int]]) -> int:
	    m, n = len(A), len(A[0])
        MAX = float('inf')
			
        # dp[i] - minimum falling path till A[-1][j], inclusive
        dp = A[0][:]
        for i in range(1, m):
           top_left, top, top_right = MAX, MAX, MAX
           for j in range(n):
               top, top_right = dp[j], dp[j+1] if j+1 < n else MAX
               dp[j] = min(top_left, top, top_right) + A[i][j]
               top_left, top = top, top_right
                    
        return min(dp)
```
---
#### JavaScript Solution
---
```JavaScript
var minFallingPathSum = function(A) {
    const size = A.length;
    
    if (size === 1) return A[0][0];
    
    let prev = A[0];
    
    for (let i = 1; i < size; i++) {
        let next = [];
        
        for (let j = 0; j < size; j++) {
            const sum = A[i][j] + Math.min(
                j > 0 ? prev[j - 1] : Infinity,			// top_left
                prev[j],								// top
                j < size - 1 ? prev[j + 1] : Infinity	// top_right
            );
            
            next.push(sum);
        }
        
        prev = next;
    }
    
    return Math.min(...prev);
};
```