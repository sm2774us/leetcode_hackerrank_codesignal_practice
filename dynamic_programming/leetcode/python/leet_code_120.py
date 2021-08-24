# Dynamic Programming
# TC: O(m*n) ; SC: O(1)

from typing import List

class Solution(object):

	# Bottom-Up Dynamic Programming w/ Auxillary Space ... i.e., Tabulation
	#
	# TC: O(N^2)
	# SC: O(N)
	#    
    def minimumTotalBottomUp(self, triangle: List[List[int]]) -> int:
        n = len(triangle)
        cur_row, prev_row = [0]*n, [0]*n
        prev_row[0] = triangle[0][0]  
        for level in range(1, n):
            for i in range(level+1):
                cur_row[i] = triangle[level][i] + min(prev_row[min(i, level-1)], prev_row[max(i-1,0)])
            cur_row, prev_row = prev_row, cur_row
        return min(prev_row)
    
	# Top-Down Dynamic Programming w/ Auxillary Space ... i.e., Memoization
	#
	# TC: O(N^2)
	# SC: O(N)
	#    
    def minimumTotalTopDown(self, triangle: List[List[int]]) -> int:
        n = len(triangle)
        cur_row, next_row = [0]*n, triangle[n-1]        
        for level in range(n-2,-1,-1):
            for i in range(level+1):
                cur_row[i] = triangle[level][i] + min(next_row[i], next_row[i+1])
            cur_row, next_row = next_row, cur_row
        return next_row[0]