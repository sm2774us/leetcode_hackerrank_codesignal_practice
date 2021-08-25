from functools import lru_cache
class Solution:

    # Solution 1. Memoization (Top-Down)
    # TC: O(N^2)
    # SC: O(N^2)
    
    #def pathsWithMaxScore(self, grid: List[str]) -> List[int]:
	#    MOD = 10**9+7
    #    grid[0] = "0"+grid[0][1:]
    #    R, C = len(grid), len(grid[0])
    #    self.reachable = False
    #    @lru_cache(None)
    #    def dp(i, j):
    #        if i==R-1 and j==C-1:
    #            self.reachable=True
    #            return [0, 1]
    #        substates = [(i+1,j), (i,j+1),(i+1,j+1)]
    #        valid_substates = [[r,c] for r, c in substates if 0<=r<R and 0<=c<C and grid[r][c]!='X']
    #        if not valid_substates:return [0, 0]
    #        results = [dp(r, c) for r,c in valid_substates]
    #        max_path_sum, _ = max(results)
    #        path_cnt = sum(this_path_cnt for this_path_sum, this_path_cnt in results if this_path_sum==max_path_sum)
    #        return [max_path_sum+int(grid[i][j]), path_cnt%MOD]
    #    
    #    res = dp(0, 0)
    #    return res if self.reachable else [0,0]

    def pathsWithMaxScoreTopDown(self, board: List[str]) -> List[int]:
        n,coord=len(board)-1,[(0,-1),(-1,0),(-1,-1)]
        
        @lru_cache(None)
        def get_max(i,j):
            if i<0 or j<0: return -float('inf')
            if board[i][j]=='X': return -float('inf')
            if board[i][j]=='E': return 0
            if board[i][j]=='S': return max(get_max(i+x,j+y) for x,y in coord)
            return max(get_max(i+x,j+y) for x,y in coord)+int(board[i][j])
        
        total=(get_max(n,n))
        
        if total<0: return (0,0)
        
        @lru_cache(None)
        def get_path(total,i,j):
            if i<0 or j<0: return 0
            if board[i][j]=='X': return 0
            if board[i][j]=='E': return 1 if total==0 else 0
            if board[i][j]=='S': return sum(get_path(total,i+x,j+y) for x,y in coord)
            if total==get_max(i,j): return sum(get_path(total-int(board[i][j]),i+x,j+y)  for x,y in coord)
            return 0
        
        paths=get_path(total,n,n)%(10**9+7)
        
        return [total,paths]
        
    # Solution 2. Tabulation (Bottom-Up)
    # TC: O(N^2)
    # SC: O(N^2)
    def pathsWithMaxScoreBottomUp(self, board: List[str]) -> List[int]:
        # modulo constant defined by description
        constant = int( 1e9 ) + 7
        
        # dimensions of board (# of rows and # of columns)
        rows = len(board)
        cols = len(board[0])
        
        # a table for highest score path with Dynamic Progrmming
        score = [ [ 0 for y in range(cols+1) ] for x in range(rows+1) ]
        
        # a talbe for counter of highest score path with Dynamic Progrmming
        count_of_path = [ [ 0 for y in range(cols+1) ] for x in range(rows+1) ]
        
        # Initialization for start point:
        # Let 
        # score = 0 ( completed in variable declaration )
        # path count = 1
        count_of_path[cols-1][rows-1] = 1
        
        unreachable = set()
        
        # Update table from start point to end point
        for y in range(cols-1, -1, -1):
            for x in range(rows-1, -1, -1):
                
                if (y, x) == (cols-1, rows-1):
                    # start point, table value has been initialized as above.
                    continue
                
                if board[y][x] != 'X' and (y,x) not in unreachable:
                    # find highest score path, excluding obstable
                    
                    # possible candidate:
                    # reach [y][x] from going up, going left, going up-left
                    highest_score = max( score[y+1][x], score[y+1][x+1], score[y][x+1] )
                    
                    if (y, x) == (0, 0):
                        # destination
                        score[y][x] += highest_score
                    else:
                        # in the middle of path finding, update score table
                        score[y][x] += highest_score + int(board[y][x])
                    
                    
                    optimal_count = 0
                    # update path counter with highest score path
                    if highest_score == score[y+1][x]:
                        optimal_count += count_of_path[y+1][x]
                        
                    if highest_score == score[y+1][x+1]:
                        optimal_count += count_of_path[y+1][x+1]
                        
                    if highest_score == score[y][x+1]:
                        optimal_count += count_of_path[y][x+1]
                    
                    count_of_path[y][x] = optimal_count % constant
                
                else:
                    
                    # mark those girds blocked by obstacle 'X' as unreachable
                    if (y,x) not in unreachable:
                        unreachable.add( (y,x) )
                        
                    if (y == cols-1 and x >= 1) or ( (y+1,x-1) in unreachable and (y+1,x) in unreachable ):
                        # block by the right hand side 'X' on the last row, or
                        # block by the ┘ shape,  three 'X's on the bottom right
                        unreachable.add( (y,x-1) )
                        
            
        if count_of_path[0][0] == 0:
            # destination is out of reach
            return [0, 0]
        else:
            # destination is reachable
            return [score[0][0], count_of_path[0][0] ]    
