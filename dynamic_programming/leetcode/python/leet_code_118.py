from typing import List

# Recursive Solution
# TC: O(N^2) ; SC: O(N^2)
class Solution:
    def generate(self, n: int) -> List[List[int]]:        
        def helper(n):
            if n:
                helper(n-1)                 # generate above row first
                ans.append([1]*n)           # insert current row into triangle
                for i in range(1, n-1):     # update current row values using above row
                    ans[n-1][i] = ans[n-2][i] + ans[n-2][i-1]
        ans = []
        helper(n)
        return ans

# Iterative Solution
# TC: O(N^2) ; SC: O(N^2)
class Solution:
    def generate(self, n: int) -> List[List[int]]:
        ans = [[1]*i for i in range(1, n+1)]   # initialize triangle with all 1
        for i in range(1, n):
            for j in range(1, i):
                ans[i][j] = ans[i-1][j] + ans[i-1][j-1] # update each as sum of two elements from above row
        return ans