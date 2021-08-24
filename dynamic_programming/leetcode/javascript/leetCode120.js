/**
 *  Bottom-Up Dynamic Programming w/ Auxillary Space ... i.e., Tabulation
 *
 *  TC: O(N^2)
 *  SC: O(N)
 */
var minimumTotal = function(triangle) {
  let rows = triangle.length;
  let columns = triangle[rows - 1].length;
  let dp = [...triangle];
  for(let i = rows - 2; i >= 0; i--) {
    for(let j = 0; j < dp[i].length; j++) {
      dp[i][j] = Math.min(dp[i+1][j], dp[i+1][j+1]) + triangle[i][j];
    }
  }
  return dp[0][0];
};

/**
 *  Top-Down Dynamic Programming w/ Auxillary Space ... i.e., Memoization
 *
 *  TC: O(N^2)
 *  SC: O(N)
 */
var minimumTotal = function(triangle) {
  let rows = triangle.length;
  let columns = triangle[rows - 1].length;
  let dp = Array.from({length: rows}).map(item => Array.from({length: columns}).fill(Number.MAX_SAFE_INTEGER));
  dp[0][0] = triangle[0][0];
  for(let i = 1; i < rows; i++) {
    for(let j = 0; j <= i; j++) {
      if(j === 0) {
        dp[i][j] = dp[i-1][j] + triangle[i][j];
      } else {
        dp[i][j] = Math.min(dp[i-1][j-1], dp[i-1][j]) + triangle[i][j];
      }
    }
  }  
  return Math.min(...dp[columns-1]);
};