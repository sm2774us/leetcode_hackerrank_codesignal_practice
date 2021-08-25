# Dynamic Programming | Number Tower
# [LC-931 : Minimum Falling Path Sum](https://leetcode.com/problems/minimum-falling-path-sum/)
# How to Tackle A DP Problem - Mindset

## Orientation:

  * ___read the problem:___
    * square of integers (array of array)
    * find min sum path with constraints on choice
  * ___hypothesis:___ a 2d array A with size n*n where ??? < |A| < ???
  * ___conclusion:___ the minimum sum subarray (given the constraints)
  * ___categorize:___
    * combinatorial
  * ___search space:___
    * exponential? I think this is factorial because it is n choose k. Either way, brute force is not polynomial which leads me to believe a DP or Greedy solution may apply if we can prove optimal substructure
  * ___brainstorm:___
    * start with visualizing and stepping through an example to find brute force algorithm:
      * make the problem easier while you step through
        * what if there were no negative numbers?
        * what if you just chose a single column and returned the sum?
        * what if you didn't care about the min sum?
      * look at extreme examples
        * what is the smallest valid input?
        * what if all numbers are equal?
        * what if all min numbers form a diagonal
      * look at the problem backwards
        * go from a complete set to the starting point
    * evaluate DP and Greedy criteria
    * if not possible, come back to orientation...

## Visualize and Step Through Example:

  * input: A[[1,2,3],[4,5,6],[7,8,9]]
  * output: [1,4,7]


| i j | 0  | 1  | 2  |
| :-  | :- | :- | :- |
| 0	  | 1  | 2	| 3  |
| 1	  | 4  | 5	| 6  |
| 2	  | 7  | 8	| 9  |

## Make it easy - find the smallest sum column:
Specialize the problem and constrain it to make it easier and generalize from there.

```
Algorithm(A):
	minSum = Integer.MAX_VALUE
	for int i = 0 to i < A.length
		curSum = 0
		for int j = 0 to j < A.length
			curCum += A[i][j]
		minSum = Math.min(minSum, curSum)
	return minSum
```

## Make it easy - recast the problem slightly:
Instead of finding the min sum of an entire column, reframe the problem as: ___find the min sum of a column ending at position (i,j) in the grid A.___

## Look for optimal substructure:
Min sum ending at (0,0) is 1 + 4 + 7
Min sum ending at (1,0) is 4 + 7
Min sum ending at (2,0) is 7

## Add implied empty sets to make the recurrence more obvious:
Min sum ending at (0,0) is 1 + 4 + 7 + {}
Min sum ending at (1,0) is 4 + 7 + {}
Min sum ending at (2,0) is 7 + {}
Min sum ending at (3,0) is {}

Notice that when i > A.length, we return an empty set.

## Define min sum ending at position (i,j) recursively:
Min sum ending at (i,j) is:

  * i > A.length, return empty
  * else return A[i][j] + minSumEndingAt(A, i+1, j)

```
minSumEndingAt(A, i, j)
	if (i > A.length) return 0;
	return A[i][j] + minSumEndingAt(A, i+1, j);
```

## Generalize - add the choice requirement into the problem:
Find the min sum ending at i,j given the choice of an element in a column to the diagonal side of or below i,j.

We know that when the choice is constrained to just the cell directly below i,j then the min sum ending at i,j is the value at i,j plus the min sum of i+1,j. Therefore with the new requirement we can say that the min sum ending at i,j is the min of the min sum ending at all of the available choice cells:

min sum at i,j = **A[i][j] + Math.min(minSEA(A,i+1,j-1), minSEA(A,i+1,j), minSEA(A,i+1,j+1))**
also notice that when j < 0 or j > A.length we return a nil set

## Visualize as a tree:
Sometimes helps me later on if I visualize the recursion tree, abreviate min sum ending at as minSEA:


```
				 _____________minSEA(0,0)____________
				/                  |                 \
			   /                   |                  \
	minSEA(1,-1)                  minSEA(1,0)         minSEA(1,1)
```

tree continues like this for all cells. Leaves are outside the bounds of the grid and return a nil set.

## Define recursive brute force algorithm:

```
minSumEndingAt(A, i, j)
	if (i > A.length || j < 0 || j > A.length) return nil
	return A[i][j] + Math.min(minSumEndingAt(A,i+1,j-1), minSumEndingAt(A,i+1,j), minSumEndingAt(A,i+1,j))
```

## Analyze:

  * **time:** we know the search space is non-polynomial, I think it's factorial O(n!) because it looks like n choose k
  * we do a few extra recursions to make the recursive function easier to write but I'm not too worried about this

## Check DP / Greedy criteria to see if we can optimize:

  * does the problem have optimal substructure? yes, the optimal solution for the min sum ending at (i,j) contains the optimal solution to the min sum ending at one of the three cells below (i,j)
  * have we defined the problem recursively? yes, above
  * is there symmetry in the search space that we can exploit? yes, solutions to problems depend on the same solutions to sub problems which we can double check by looking at a table of optimal solutions:

| **min ending at cell:** | **min subset:**	| **resursive solution:**                         |
| :-                      | :-              | :-                                              |
| (2,0)	                  | [7]	            |                                                 |
| (1,0)	                  | [**4,7**]       | 4 + min (minSEA(2,0),minSEA(2,1)                |
| (0,0)	                  | [1,**4,7**]	    | 1 + min (minSEA(1,0),minSEA(0,1)                |
| (2,1)	                  | [8]		        |                                                 |
| (1,1)	                  | [5,7]	        | 5 + min (minSEA(2,0), minSEA(2,1), minSEA(2,2)) |
| (0,1)	                  | [2,**4,7**]	    | 2 + min (minSEA(1,0), minSEA(1,1), minSEA(1,2)) |
| (2,2)	                  | [9]	            |                                                 |
| (1,2)	                  | [6,8]	        | 6 + min (minSEA(2,1), minSEA(2,2))              |
| (0,2)	                  | [3,5,7]	        | 3 + min (minSEA(1,1), minSEA(1,2))              |

## Looks like DP applies, add memoization to optimize the recursive algorithm:
Basically the same as our algorithm but use a hash to store the computed min sum ending at i,j:

```
hash[][]
minSumEndingAt(A,i,j)
	if (i > A.length || j < 0 || j > A.length) return nil
	if (hash[i][j] != nil) return hash[i][j]

	hash[i][j] = A[i][j] + Math.min(minSumEndingAt(A,i+1,j-1), minSumEndingAt(A,i+1,j), minSumEndingAt(A,i+1,j))
	return hash[i][j]
```

## Analyze:

  * **time:** we now only compute the value of a sub problem once and so the number of computations is now linear with regards to the number of cells. **O(N^2)**
  * **space:** we need space for the hash proportional to the number of cells in the input and we need space for the recursion tree proportional to the height of the tree which is A.length or just **O(N^2)**

Assumption: NxN matrix.

## Transpose into Java:
break out some smaller problems that I need to solve as I transpose:

  * how to define the base case where i or j is out of bounds? easiest way I can think of is to use Integer.MAX_VALUE as we're calculating min sums, will need to assume that the sum will be less than that so that I can test if a sub problem yields MAX_VALUE, and count it as 0 in my sum calculation.
  * how to initialize the hash? easiest way I can think of is to assume the min sum will always be greater than 0

make assumptions to make the code easier to write:

  * 0 < minSumEndingAt(i,j) < Integer.MAX_VALUE

```java
class Solution {
    int[][] hash;
    
    public int minFallingPathSum(int[][] A) {
        int min = Integer.MAX_VALUE;
        hash = new int[A.length][A.length];
        for (int j = 0; j < A.length; j++) {
            min = Math.min(min, minSumEndingHere(A, 0, j));
        }
        return min;
    }
    
    private int minSumEndingHere(int[][] A, int i, int j) {
        if      (j < 0 || j > A.length - 1 || i > A.length - 1) return Integer.MAX_VALUE;
        else if (hash[i][j] != 0)                               return hash[i][j];
        
        int left    = minSumEndingHere(A, i+1, j-1);
        int mid     = minSumEndingHere(A, i+1, j);
        int right   = minSumEndingHere(A, i+1, j+1);
        
        int res = min(left, mid, right);
        
        hash[i][j] = A[i][j] + (res == Integer.MAX_VALUE ? 0 : res);
        return hash[i][j];
    }
    
    private int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }
}
```