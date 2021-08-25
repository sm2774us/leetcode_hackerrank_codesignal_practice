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