# Dynamic Programming | Number Tower
# [LC-1289 : Minimum Falling Path Sum II](https://leetcode.com/problems/minimum-falling-path-sum-ii/)

## Approach
Think about the most straightforward solution: find the minimum element in the first row, add it to the minimum element in the second row, and so on.

That should work, unless two minimum elements are in the same column. To account for that, let's remember the position `pos` of the minimum element `fm`. Also, we need to remember the second minimum element `sm`.

For the next row, we will use `fm` for columns different than `pos`, and `sm` otherwise.

## Similar to:
[LC-265 : Paint House II](https://kennyzhuang.gitbooks.io/leetcode-lock/content/265_paint_house_ii.html)

### 1. Tabulation (Bottom-Up)
Time complexity: `O(N^2)`
Space complexity: `O(1)`
---
#### C++ Solution
---
```cpp
int minFallingPathSum(vector<vector<int>>& arr) {
    int fm = 0, sm = 0, pos = -1;
    for (auto i = 0; i < arr.size(); ++i) {
        auto fm2 = INT_MAX, sm2 = INT_MAX, pos2 = -1;
        for (auto j = 0; j < arr[i].size(); ++j) {
            auto mn = j != pos ? fm : sm;
            if (arr[i][j] + mn < fm2) {
                sm2 = fm2;
                fm2 = arr[i][j] + mn;
                pos2 = j;
            } else sm2 = min(sm2, arr[i][j] + mn);
        }
        fm = fm2, sm = sm2, pos = pos2;
    }
    return fm;
}
```
---
#### Java Solution
---
```java
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
```
---
#### Python Solution
---
```python
from typing import List
class Solution:
    def minFallingPathSum(A):
        rows = len(A)
        cols = len(A[0])
        prv_row_min1 = prv_row_min2 = 0
        prev_pos1 = -1
        for i in range(rows):
            curr_row_min1 = curr_row_min2 = float('inf')
            for j in range(cols):
                if prev_pos1 != j:
                    min_val = prv_row_min1
                else:
                    min_val = prv_row_min2
            
                if min_val + A[i][j] < curr_row_min1:
                    curr_row_min2 = curr_row_min1
                    curr_row_min1 = min_val + A[i][j]
                    curr_pos = j
                else:
                    curr_row_min2 = min(curr_row_min2, min_val+A[i][j])
        
            prv_row_min1, prv_row_min2 = curr_row_min1, curr_row_min2
            prev_pos1 = curr_pos
    
        return prv_row_min1
```
---
#### JavaScript Solution
---
```JavaScript
/** 3: Bottom-up Approach O(mn)
 * @param {number[][]} A
 * @return {number}
 */
const minFallingPathSum = function (A) {
  let min1 = [0, null] // [value, column]
  let min2 = [0, null]
  for (let row = A.length - 1; row >= 0; row--) {
    let current1 = null
    let current2 = null
    for (let column = 0; column < A[row].length; column++) {
      let currentSum = A[row][column]
      if (column !== min1[1]) {
        currentSum += min1[0]
      } else {
        currentSum += min2[0]
      }
      if (current1 === null || currentSum < current1[0]) {
        current2 = current1
        current1 = [currentSum, column]
      } else if (current2 === null || currentSum < current2[0]) {
        current2 = [currentSum, column]
      }
    }
    min1 = current1
    min2 = current2
  }
  return min1[0]
}
```
