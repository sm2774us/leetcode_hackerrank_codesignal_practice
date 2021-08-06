# Dynamic Programming
# TC: O(n^2) (quadratic) ; SC: O(N^2)
class Solution:
    def getRow(self, rowIndex):
        
        #Initialise tri grid:
        rowIndex += 1 #Adjust for 1 indexing.
        tri = [[1]*x for x in range(1, rowIndex+1)] #Triangle grid of all 1's.
        
        #Edge cases:
        if rowIndex <= 2: #Just return row as initialised.
            return tri[rowIndex-1] #Beware we must undo our indexing adjustment.
        
        #Fill tri grid:
        for row in range(2, len(tri)): #First 2 rows are already correct. Skip.
            for i in range(1, len(tri[row])-1): #Indices skip edge cells.
                tri[row][i] = tri[row-1][i] + tri[row-1][i-1] #Apply formula.
            
        return tri[row] #Return requested row.
		

# Math-based Solution: "Calculating binomial coefficients"
# When we're talking about Pascal's Triangle, we're acctually discussing about grabbing a specific value, from a specific row.
# For example, when doing statistics, we often end up needing to compute "nCk" (Binomial Coefficients) which systematically resolve themselves to the "Kth value of the Nth row of Pascal's Triangle".
# The default algorithm for this is:
# C(n, k) = n! / (k!*(n-k)!)
# TC: O(n^2) (quadratic) ; SC: O(N^2)
#
# In older versions of python, you could instead define your own nCk helper function:
def getRow(self, rowIndex: int) -> List[int]:

    def nCk(n: int, k: int) -> int:
        return int(math.factorial(n) / (math.factorial(k) * math.factorial(n - k)))

    row = [nCk(rowIndex, k) for k in range(0, rowIndex + 1)]
    return row        

# In python 3 => just use math.comb()
import math
def getRow(self, rowIndex: int) -> List[int]:
    row = [math.comb(rowIndex, k) for k in range(0, rowIndex + 1)]
    return row

# Math-Based Solution: "n-choose-k formula"
# We can improve this calculation by using Combination Formula:
# C(n,k+1) = C(n,k) (n-k) / (k+1)
# TC: O(n) (quadratic) ; SC: O(1)
class Solution:
    def getRow(self, rowIndex: int) -> List[int]:
        triangle = [1]
        
        for i in range(1, rowIndex + 1):
            triangle.append(int(triangle[i - 1] * (rowIndex - (i - 1)) / i))
        
        return triangle

#There's some things we can do to above function to improve it's execution time.
#
#We don't need to compute the full row, it mirrors itself at the center. So, we only need to compute the first half.
#We shouldn't be calling append in the inner loop so many times. Instead of this we can pre-allocate the full row in one call, and index into it as we need.
#Implemention:
class Solution:
    def getRow(self, rowIndex: int) -> List[int]:
        triangle = [0] * (rowIndex + 1)
        triangle[0] = triangle[rowIndex] = 1
        
        for i in range(0, rowIndex >> 1):
            col_val = triangle[ i ] * (rowIndex - i) / (i + 1)
            triangle[i + 1]= triangle[rowIndex - 1 - i] = int(col_val)
