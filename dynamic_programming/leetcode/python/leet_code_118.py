from typing import List

# Iterative Solution
# TC: O(N^2) ; SC: O(N^2)
class Solution:
    def generate(self, n: int) -> List[List[int]]:
        ans = [[1]*i for i in range(1, n+1)]   # initialize triangle with all 1
        for i in range(1, n):
            for j in range(1, i):
                ans[i][j] = ans[i-1][j] + ans[i-1][j-1] # update each as sum of two elements from above row
        return ans
        
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

# Math-Based Solution: "n-choose-k formula for binomial coefficients"
# We calculate C(n, k) by this relation: C(n, k) = C(n, k-1) * ((n+1-k) / k), and C(n, 0) = 1
# TC: O(N^2) ; SC: O(N^2)
#
# Note: itertools.accumulate() => Argument: p[,func]
#                                 Results : p0, p0+p1, p0+p1+p2, …
#                                 Example : accumulate([1,2,3,4,5]) --> 1  3   , 6    , 10     , 15
#                                                                       1, 1+2 , 1+2+3, 1+2+3+4, 1+2+3+4+5
from itertools import accumulate
class Solution:
   # Source: https://stackoverflow.com/a/3025547
   def bin_coeff(self, n, k):
       """
       A fast way to calculate binomial coefficients by Andrew Dalke (contrib).
       """
       if 0 <= k <= n:
           ntok = 1
           ktok = 1
           for t in range(1, min(k, n - k) + 1):
               ntok *= n
               ktok *= t
               n -= 1
           return ntok // ktok
       else:
           return 0
		
   def generate(self, numRows: int) -> List[List[int]]:
       results = []
       for row in range(0, numRows):
           next_row = [self.bin_coeff(row, i) for i in range(0, row + 1)]
           results.append(next_row)
       return results

   # # Only works w/ Python 3.8 and above
   # # the initial argument to itertools.accumulate() was only introduced in Python 3.8
   # def generate(self, numRows: int) -> List[List[int]]:
       # return [[*accumulate(range(1, i), lambda s, j: s*(i-j)//j, initial=1)] for i in range(1, numRows+1)]