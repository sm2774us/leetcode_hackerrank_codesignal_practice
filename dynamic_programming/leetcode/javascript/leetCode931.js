/**
 *  Memoization (Top-Down)
 *
 *  TC: O(N^2)
 *  SC: O(N^2)
 */
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

/**
 *  Tabulation (Bottom-Up)
 *
 *  TC: O(N^2)
 *  SC: O(N^2)
 */

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

/**
 *  Tabulation (Bottom-Up) with Space Optimization
 *
 *  TC: O(N^2)
 *  SC: O(N)
 */
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