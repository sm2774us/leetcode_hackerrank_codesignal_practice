// JS
// Iterative Solution
// TC: O(N^2) ; SC: O(N^2)
//var generate = function (numRows) {
let generate = numRows => {
  const dp = Array(numRows)
    .fill(null)
    .map((_, i) => Array(i + 1));

  for (let i = 0; i < dp.length; i++) {
    dp[i][0] = 1;
    dp[i][dp[i].length - 1] = 1;
  }

  for (let i = 2; i < dp.length; i++) {
    for (let j = 1; j < dp[i].length - 1; j++) {
      dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
    }
  }

  return dp;
};

//JS - OOP Style
// Iterative Solution
// TC: O(N^2) ; SC: O(N^2)
class Solution {
  generate(numRows) {
    const dp = Array(numRows)
      .fill(null)
      .map((_, i) => Array(i + 1));

    for (let i = 0; i < dp.length; i++) {
      dp[i][0] = 1;
      dp[i][dp[i].length - 1] = 1;
    }

    for (let i = 2; i < dp.length; i++) {
      for (let j = 1; j < dp[i].length - 1; j++) {
        dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
      }
    }

    return dp;
  }
}

const sol = new Solution();
const generate = sol.generate.bind(sol);

// TS
function generate(numRows: number): number[][] {
  const dp: number[][] = Array(numRows)
    .fill(null)
    .map((_, i) => Array(i + 1));

  for (let i = 0; i < dp.length; i++) {
    dp[i][0] = 1;
    dp[i][dp[i].length - 1] = 1;
  }

  for (let i = 2; i < dp.length; i++) {
    for (let j = 1; j < dp[i].length - 1; j++) {
      dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
    }
  }

  return dp;
}

// JS
// Recursive Solution
// TC: O(N^2) ; SC: O(N^2)
const generate = numRows => {
  const triangle = [];
  appendNextRow(triangle, [], numRows)
  return triangle;
};

const appendNextRow =(triangle, previousRow, numRows) => {
  if(triangle.length >= numRows) return;
  const newRow = [];
  for (let i = 0; i < previousRow.length + 1;i++){
    let val;
    if(i === 0 || i === previousRow.length + 1) val = 1;
    else {
      const aboveLeft = (!!previousRow[i-1] ? previousRow[i-1] : 0);
      const aboveRight = (!!previousRow[i] ? previousRow[i] : 0);
      val = aboveLeft + aboveRight;
    }
    newRow.push(val);
  }
  triangle.push(newRow);
  appendNextRow(triangle,newRow, numRows)
};

// JS
// Math-Based Solution: "n-choose-k formula for binomial coefficients"
// We calculate C(n, k) by this relation: C(n, k) = C(n, k-1) * ((n+1-k) / k), and C(n, 0) = 1
// TC: O(N^2) ; SC: O(N^2)
let generate = numRows => {
    const result = [],
          round = Math.round,
          floor = Math.floor;
    for (let n = 0; n < numRows; n++) {
        let inner = [1];
        let fl = floor((n+1) / 2);
        let [mid, odd] = (n + 1) & 1 ? [fl + 1, true] : [fl, false];
        
        for (let k = 1; k < mid; k++) {
            let prev = inner[k-1];
            inner.push(round(prev * ((n + 1 - k) / k)));
        }
        result.push(
            // Note: reverse is in-place, need to slice *first*.
            inner.concat(odd? inner.slice(0, -1).reverse() : inner.slice().reverse())
        );
    }
    return result;
};