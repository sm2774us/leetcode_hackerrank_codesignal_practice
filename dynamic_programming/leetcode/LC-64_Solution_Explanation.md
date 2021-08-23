# Dynamic Programming | Number Tower
# [LC-64 : Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)

## Solution Explanation
The first thing to realize is that this is a recursive problem because there is a recurrence relation. So we should first try to solve the sub-problem, which is to find the i-th row and j-th column of the triangle. We begin by finding the base case and recursive case, then work our way through to see if this problem is optimizable.
At each index (i, j) we know that we must have either come from the square above, or the square to the left. Therefore, we know that the minimum path to (i, j) will equal either the minimum path from (i - 1, j) or (i, j - 1), plus the value at grid[i][j].
Starting at index (0 , 0), we iterate over the grid, and at each index, overwrite (i, j) to the value of the minimum path, which we calculate with min(grid[i-1][j], grid[i][j-1]) + grid[i][j] We handle the cases where we are at the top-most row, and left-most column.


The idea is to use DP to build the minimum path to each individual index. 
We don't need to use any extra space as we can just use the input grid. Our initial loops will build the first row 
and first column. Since we can only move right and down we build the first row from left to right and the first column 
from top to bottom. Then we build the inside by adding the value at that specific index + the minimum of the value above 
or to the left of the current index. Our answer is at the bottom right. 
Here is a graphic using the input grid `[ [1, 3, 1], [1, 5, 1], [4, 2, 1] ]` as an example:

![lc-64-solution-explanation](./assets/lc-64-solution-explanation.png)

---
### Java Solution
---
```java
public  int minPathSum(int[][] grid) {
    
	// first column
    for(int i = 1; i < grid.length; i++)
    	grid[i][0] += grid[i - 1][0];

    // first row
    for(int i = 1; i < grid[0].length; i++)
    	grid[0][i] += grid[0][i - 1];

    for (int i = 1; i < grid.length; i++) {
    	for (int j = 1; j < grid[0].length; j++) {
    		grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
    	}
    }

    return grid[grid.length - 1][grid[0].length - 1];
}
```

More concise:

```Java
    public int minPathSum(int[][] grid) {
        for(int i=1; i<grid[0].length; i++)
            grid[0][i] += grid[0][i-1];
        
        for(int i = 1; i<grid.length; i++)
            for(int j = 0; j<grid[0].length; j++)
                grid[i][j] += Math.min(j==0?Integer.MAX_VALUE:grid[i][j-1] , grid[i-1][j]);

        return grid[grid.length-1][grid[0].length-1];
    }
```

---
### Python Solution
---

One way to solving this problem is by using **memorization**. We are trying to find the path to find the path to bottom right corner of the given matrix with the restriction of only moving right or down
.
.
___Simulation Of The Algorithm:___

Given Input:
  [1,3,1]
  [1,5,1]
  [4,2,1]
We will replace each cell with a value that shows the minimum cost it take to travel to such cell. The cost can be computed with following 4 cases:

___Case 1:___
```
For cell at index [0][0], the cost will be the value it self
```

___Case 2:___
```
'''For cell at the first row, the cost will be its own value plus value of left cell
so our first row will be updated to:'''
				[1,3,1]  ==========> [1,4,5]
```

___Case 3:___
```
'''For cell at the first column, the cost will be its own value plus value of above cell
so our first column will be updated to:'''
				[1]  ==========> [1]
				[1]  ==========> [2]
				[4]  ==========> [5]
```

___Case 4:___
```
'''This is the most important case, and will be the majority case for most of the time. 
For the rest of the cells, its value will be updated with following equation:'''
				matrix[i][j] += min( matrix[i-1][j], matrix[i][j-1] )
# So the minimum cost to get to each cell will be the cost it self + min(cost_above, cost_left)
		[1,4,5]                              [1,4,5]
		[2,5,1]    ========>                 [2,7,6]
	    [6,2,1]                              [6,8,7]
```

So the value at index [-1][-1] will be the optimal path with min cost.

```python
def minPathSum(self, grid: List[List[int]]) -> int:
        # no solution
        if not grid: return 0

        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if i == 0 and j == 0:
                    continue
                elif i == 0:
                    grid[i][j] += grid[i][j-1]
                elif j == 0:
                    grid[i][j] += grid[i-1][j]
                else:
                    grid[i][j] += min(grid[i][j-1], grid[i-1][j])
                                
        return grid[-1][-1]
```

More concise:

**Dynamic Programming: Using O(1) space**

Space complexity can be reduced to `O(1)` as grid can be reused as cost matrix
Notice how we iterate the two loops and the special condition we use for i=0

```python
class Solution(object):
    def minPathSum(self, grid):
        """
        :type grid: List[List[int]]
        :rtype: int
        """
        M, N = len(grid), len(grid[0])
        for i in range(M):
            grid[i][0] = grid[i][0] + grid[i-1][0] if i > 0 else grid[i][0]
            for j in range(1,N):
                grid[i][j] = min(grid[i-1][j], grid[i][j-1]) + grid[i][j] if i > 0 else grid[i][j-1]+grid[i][j]
        return grid[-1][-1]
```

---
### JavaScript Solution
---
```JavaScript
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
```

More concise:

```JavaScript
// make it 2 lines:
var minPathSum = function (grid) {
  grid.forEach((_, i) => grid[i].forEach((_, j) => grid[i][j] += (i == 0 ? grid[0][j - 1] | 0 : j == 0 ? grid[i - 1][0] | 0 : Math.min(grid[i - 1][j], grid[i][j - 1]))));
  return grid.pop().pop();
};
```
