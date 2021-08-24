# Dynamic Programming | Number Tower
# [LC-120 : Triangle](https://leetcode.com/problems/triangle/)

We are given a triangle. We start at the top of the triangle and want to move to the bottom with minimum possible sum. We can either move down to the same index i or to the index i + 1.

## ✔️ Solution - I (Bottom-Up Dynamic Programming w/ Auxillary Space)

We can easily see that directly just choosing the minimum value in the next row(amongst `triangle[nextRow][i]` and `triangle[nextRow][i+1]`) won't fetch us the optimal final result since it maybe the case that the latter values of previous chosen path turn out to be huge.

We need to observe that to get the minimum possible sum, **we must use a path that has Optimal Value for each intermediate stop in the path**. Thus, we can use **Dynamic Programming** to find the optimal value to reach each position of the triangle level by level. We can do this by accumulating the sum of path(or more specifically sum of values of optimal stops in a path) for each cell of a level from top to the bottom of triangle.

We are given that, at each cell in the triangle, we can move to the next row using two choices -

  1. Move to the same index `i`.
  2. Move to the next index `i + 1`.

Since we are following **a bottom-up approach, the above can also be interpreted as** :-

For cell in the triangle, we could have reached here either from the previous row/level either from -

  1. the same index `i`, or
  2. the index `i - 1`

So, obviously the optimal choice to arrive at the current position in triangle would be to come from the cell having minimum value of these two choices.

We will keep adding the result from the lower level to the next/higher level by each time choosing the optimal cell to arrive at the current cell. Finally, we can return the minimum value that we get at the bottom-most row of the triangle. Here, no auxillary space is used and I have modified the `triangle` itself to achieve a space complexity of `O(1)`.

**NOTE: Modifying input makes thread unsafe, unless asked to do so by the interviewer(or allowed to do so). So go with the auxiliary space solutions (III & IV).**

### C++
```cpp
int minimumTotal(vector<vector<int>>& triangle) {
	// start from level 1 till the bottom-most level. Each time determine the best path to arrive at current cell
	for(int level = 1; level < size(triangle); level++) 
		for(int i = 0; i <= level; i++)  // triangle[level].size() == level + 1 (level starts from 0)
			// for the current level: 
			triangle[level][i] += min(triangle[level - 1][min(i, (int)size(triangle[level - 1]) - 1)], // we can either come from previous level and same index
			                          triangle[level - 1][max(i - 1, 0)]); // or the previous level and index-1
	// finally return the last element of the bottom level
	return *min_element(begin(triangle.back()), end(triangle.back())); 
}
```
---
### Python
```python
from typing import List

def minimumTotal(self, triangle: List[List[int]]) -> int:
    for level in range(1, len(triangle)):
        for i in range(level+1):
            triangle[level][i] += min(triangle[level-1][min(i, level-1)], triangle[level-1][max(i-1,0)])
    return min(triangle[-1])
```

**Time Complexity :** `O(N^2)`, where `N` are the total number of levels in triangle.
**Space Complexity :** `O(1)`

The `min` and `max` in the above code are used to do bound-checks.

## ✔️ Solution - II (In-Place Top-Down Dynamic Programming)

We chose to go from top-level to the bottom-level of triangle in the previous approach. We can also choose to start from the bottom of triangle and move all the way to the top. We will again follow the same DP strategy as used in the above solution.

At each cell of the triangle, we could have moved here from the below level in 2 ways, either from -

  1. the same index i in below row, or
  2. the index i+1.

And again, we will choose the minimum of these two to arrive at the optimal solution. Finally at last, we will reach at triangle[0][0], which will hold the optimal (minimum) sum of path.

Actually, this approach will make the code a lot more clean and concise by avoiding the need of bound checks.

**C++**
```cpp
int minimumTotal(vector<vector<int>>& triangle) {
	for(int level = size(triangle) - 2; level >= 0; level--) // start from bottom-1 level
		for(int i = 0; i <= level; i++)
			 // for every cell: we could have moved here from same index or index+1 of next level
			triangle[level][i] += min(triangle[level + 1][i], triangle[level + 1][i + 1]);
	return triangle[0][0]; // lastly t[0][0] will contain accumulated sum of minimum path
}
```
---
**Python**
```python
def minimumTotal(self, triangle: List[List[int]]) -> int:
    for level in range(len(triangle)-2,-1,-1):
        for i in range(level+1):
            triangle[level][i] += min(triangle[level+1][i], triangle[level+1][i+1])
    return triangle[0][0]
```

**Time Complexity :** `O(N^2)`
**Space Complexity :** `O(1)`

**NOTE: Modifying input makes thread unsafe, unless asked to do so by the interviewer(or allowed to do so). So go with the auxiliary space solutions (III & IV).**

## Solution - III (Bottom-Up Dynamic Programming w/ Auxillary Space ... i.e., Tabulation)

More often than not, you would not be allowed to modify the given input. In such a situation, we can obviously opt for making a copy of the given input 
(triangle in this case). This would lead to a space complexity of `O(N^2)`. I won't show this solution since the only change needed in above solutions would be adding 
the line `vector<vector<int>>dp(triangle)`; and replacing `triangle` with `dp` (Or better yet just pass triangle by value instead of reference & keep using that itself 🤷‍♂️).

Here, I will present a **solution with linear space complexity without input modification**. We can observe in the above solutions that we really ever **access only two rows of the input at the same time**. So, we can just maintain two rows and alternate between those two in our loop.

I have used `level & 1` in the below solution to alternate between these two rows of `dp`. It's very common way to go when we are converting from 2d DP to linear space. If you are not comfortable with it, you can also choose to maintain two separate rows and swap them at end of each iteration.

All the other things and idea remains the same as in the `solution - I`

**C++**
```cpp
int minimumTotal(vector<vector<int>>& triangle) {
	int n = size(triangle), level = 1;
	vector<vector<int> > dp(2, vector<int>(n, INT_MAX));
	dp[0][0]=triangle[0][0];  // assign top-most row to dp[0] as we will be starting from level 1
	for(; level < n; level++) 
		for(int i = 0; i <= level; i++)
			dp[level & 1][i] = triangle[level][i] + min(dp[(level - 1) & 1][min(i, n - 1)], dp[(level - 1) & 1][max(i - 1, 0)]); 
	level--; // level becomes n after for loop ends. We need minimum value from level - 1
	return *min_element(begin(dp[level & 1]), end(dp[level & 1])); 
}
```
---
**Python**

The below solution is using the two separate rows method that I described above and swapping after each iteration to alternate between them -

```python
def minimumTotal(self, triangle: List[List[int]]) -> int:
	n = len(triangle)
	cur_row, prev_row = [0]*n, [0]*n
	prev_row[0] = triangle[0][0]  
	for level in range(1, n):
		for i in range(level+1):
			cur_row[i] = triangle[level][i] + min(prev_row[min(i, level-1)], prev_row[max(i-1,0)])
		cur_row, prev_row = prev_row, cur_row
	return min(prev_row)
```
---
**Java**

```java
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        // create dp table with size of the largest column (which is number of rows)
        int[] table = new int[triangle.size() + 1];
        Arrays.fill(table, 0);
                
        // iterate over the entire triangle from bottom to top
        for (int row = triangle.size() - 1; row >= 0; row--) {
            for (int col = 0; col <= row; col++) {
                // either go to [row + 1, col] or [row + 1, col + 1]
                int remain = Math.min(table[col],
                                      table[col + 1]);
                
                // combine answer with current value
                table[col] = triangle.get(row).get(col) + remain;
            }
        }
        
        return table[0];
    }
}
```
---
**JavaScript
```
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
```

**Time Complexity :** `O(N^2)`
**Space Complexity :** `O(N)`

## Solution - IV (Top-Down Dynamic Programming w/ Auxillary Space ... i.e., Memoization)

Here is the Top-Down version of `Solution - II`, without input array modification and using linear auxillary space.

**C++**
```cpp
int minimumTotal(vector<vector<int>>& triangle) {
	int n = size(triangle), level = n - 1;
	vector<vector<int> > dp(2, vector<int>(n, 0));
	dp[level-- & 1] = triangle[n - 1];
	for(; level >= 0; level--)
		for(int i = 0; i <= level; i++)
			dp[level & 1][i] = triangle[level][i] + min(dp[(level + 1) & 1][i], dp[(level + 1) & 1][i + 1]);
	return dp[0][0];
}
```
---
**Python**

Again, I have used to two separate rows method here -

```python
def minimumTotal(self, triangle: List[List[int]]) -> int:
    n = len(triangle)
    cur_row, next_row = [0]*n, triangle[n-1]        
    for level in range(n-2,-1,-1):
        for i in range(level+1):
            cur_row[i] = triangle[level][i] + min(next_row[i], next_row[i+1])
        cur_row, next_row = next_row, cur_row
    return next_row[0]
```
---
**Java**

```java
/**
 *  Top-Down: A BFS based approach.
 */
import java.util.Deque;
import java.util.LinkedList;
import jave.util.List;

public class Triangle{
    public int minimumTotal(List<List<Integer>> triangle) {
        Deque<Integer> queue = new LinkedList<Integer>();
        int count=triangle.size();
        queue.add(triangle.get(0).get(0));
        for (int i=1;i<count;i++){
            List<Integer> list= triangle.get(i);
            for (int j=0;j<=i;j++){
        	    int min=0;
                if (j==0)
            	    min=list.get(0)+queue.peekFirst();               	
                else if (j==i)
            	    min =list.get(j)+queue.pollFirst();              	
                else
            	    min = Math.min(queue.pollFirst(),queue.peekFirst())+list.get(j);              	               
                queue.addLast(min);
            }
        }
        int result=Integer.MAX_VALUE;
        for (int i=0;i<count;i++)
    	    result=Math.min(result, queue.pollFirst());
        return result;
    }
}

/**
 *  Top-Down: Memoization.
 */
import jave.util.List;

public class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int m = triangle.size();
        // use null to distinguish whether it is empty
        Integer[][] memo = new Integer[m][m];
        List<Integer> bottom = triangle.get(m-1);
        for (int i=0;i<bottom.size();i++){
            memo[m-1][i] = bottom.get(i);
        }
        
        return getMemo(0,0,memo,triangle);
    }
    
    private int getMemo(int x, int y, Integer[][]memo, List<List<Integer>> triangle ){
        if (x>=triangle.size() || y >=triangle.get(x).size())
            return 0;
        // Key point
        if (memo[x][y] != null)
            return memo[x][y];
        
        int self = triangle.get(x).get(y);
        int left = getMemo(x+1,y,memo,triangle) + self;
        int right = getMemo(x+1,y+1,memo,triangle) + self;
        
        return memo[x][y] = Math.min(left,right);
    }
}
```
---
**JavaScript
```
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
```

**Time Complexity :** `O(N^2)`
**Space Complexity :** `O(N)`