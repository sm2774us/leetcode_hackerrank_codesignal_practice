import functools
from typing import List

class Solution(object):

	# Memoization (Top-Down)
	# 
 	# TC: O(N^2)
 	# SC: O(N^2)
 	# 
    def minFallingPathSumTopDownUsingBuiltinLruCache(self, matrix: List[List[int]]) -> int:
        @functools.lru_cache(None)
        def helper(i, j):
            if i == len(matrix):
                return 0
            return matrix[i][j] + min(helper(i+1, k) for k in range(max(0, j-1), min(len(matrix[0]), j+2)))
        return min(helper(0, k) for k in range(0, len(matrix[0])))

    
    def minFallingPathSumTopDown(self, A: List[List[int]]) -> int:
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
        
	# Tabulation (Bottom-Up)
	#
	# TC: O(N^2)
	# SC: O(N^2)
	#
    def minFallingPathSumBottomUp(self, matrix: List[List[int]]) -> int:
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
        
 	# Tabulation (Bottom-Up) with Space Optimization
	#
	# TC: O(N^2)
	# SC: O(N)
	#
    def minFallingPathSumBottomUpSpaceOptimized(self, A: List[List[int]]) -> int:
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
