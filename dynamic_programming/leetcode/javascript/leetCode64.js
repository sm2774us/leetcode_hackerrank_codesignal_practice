/*  cause every cell can only get path from its left cell or above cell, 
 *  so the minimun val of grid[i][j] is `Math.min(grid[i - 1][j], grid[i][j - 1])` + its own val
 *  let's begin:
 *  first line / first col: add left / above val resepectively,  so:
 *  [1, 3, 1]     [1, 4, 5]
 *  [1, 5, 1] ->  [2, 5, 1]
 *  [4, 2, 1]     [6, 2, 1]
 *  all other cells: add the lesser val of its left / above cell
 *  [1, 4, 5]     [1, 4, 5]
 *  [2, 5, 1] ->  [2, 7, 6]
 *  [6, 2, 1]     [6, 8, 7]
 *  the answer is the last val of the last row,  enjoy~
 */
var minPathSum = function(grid) {
  grid.forEach((_, i) => {
    grid[i].forEach((_, j) => {
      grid[i][j] += (i == 0 ? grid[0][j - 1] | 0 : j == 0 ? grid[i - 1][0] | 0 : Math.min(grid[i - 1][j], grid[i][j - 1]))
    });
  });
  return grid.pop().pop();
};

/**
 * More concise:
 * make it 2 lines:
 */
var minPathSumConcise = function (grid) {
  grid.forEach((_, i) => grid[i].forEach((_, j) => grid[i][j] += (i == 0 ? grid[0][j - 1] | 0 : j == 0 ? grid[i - 1][0] | 0 : Math.min(grid[i - 1][j], grid[i][j - 1]))));
  return grid.pop().pop();
};