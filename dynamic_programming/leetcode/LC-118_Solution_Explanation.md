# Dynamic Programming | Number Tower
# [LC-118 : Pascal's Triangle](https://leetcode.com/discuss/general-discussion/592146/dynamic-programming-summary)

## Solution Explanation
The first thing to realize is that this is a recursive problem because there is a recurrence relation. So we should first try to solve the sub-problem, which is to find the i-th row and j-th column of the triangle. We begin by finding the base case and recursive case, then work our way through to see if this problem is optimizable.

**Base Case**
---
We can begin by defining a function `f(i, j)` which returns the _i-th row_ and _j-th column_ of the pascal's triangle. We see from the triangle's pattern that the beginning and end of the triangle always has values of 1. So it follows that `f(i, j) = 1` when `j = 1` or `j = i`.

**Recursive Case**
---
We also know from the description that the current _i-th row_ and _j-th column_ is the sum of the two values above in the previous row. This gives: `f(i, j) = f(i - 1, j - 1) + f(i - 1, j)`

### Soluton-1: Recursive Solution
---
We can apply the same way of finding the recursive and base case to create a recursive solution that solves and adds each row to the triangle.

```java
public List<List<Integer>> generate(int numRows) {
	List<List<Integer>> triangle = new ArrayList<>();
	generateRow(0, numRows, triangle);
	return triangle;
}

public void generateRow(int i, int numRows, List<List<Integer>> triangle) {
	if (i >= numRows) {
		return;
	} else {
		List<Integer> row = new ArrayList<>();
		for (int j = 0; j <= i; j++) {
			row.add(generateNum(triangle, i, j));
		}
		triangle.add(row);
		generateRow(i + 1, numRows, triangle);
	}
}

public int generateNum(List<List<Integer>> triangle, int i, int j) {
	if (j == 0 || j == i || i < 2) { 
		return 1;
	} else {
		int a = triangle.get(i - 1).get(j - 1);
		int b = triangle.get(i - 1).get(j);
		return a + b;
	}
}
```

### Solution-2: Iterative Solution
---
We can also easily, change this into an iterative solution by using a loop. This solution is asymptotically the same, but does not have a recursion stack. The recursive solution might be a bit easier to read.

```java
public List<List<Integer>> generate(int numRows) {
	List<List<Integer>> triangle = new ArrayList<>();
	for (int i = 0; i < numRows; i++) {
		List<Integer> row = new ArrayList<>();
		for (int j = 0; j <= i; j++) {
			row.add(generateNum(triangle, i, j));
		}
		triangle.add(row);
	}
	return triangle;
}
    
public int generateNum(List<List<Integer>> triangle, int i, int j) {
	if (j == 0 || j == i || i < 2) { 
		return 1;
	} else {
		int a = triangle.get(i - 1).get(j - 1);
		int b = triangle.get(i - 1).get(j);
		return a + b;
	}
}
```

## Time and Space Analysis
For both Recursive and Iterative Solutions,
we see that in each case it generates each row in the triangle through sums of two values in the previous row. 
It does not need to recalculate it's results unlike the top down approach. 
Each row requires about `c(n - 1)` time because there are `n - 1` values in the previous row. There are n rows in the triangle.

Time Complexity: `θ(n^2)`

Space Complexity: θ(n^2)

There are `n - 1 + n - 2 + n - 3 ... + 1` values stored.
With Gauss Sum:
```
n (n + 1) / 2
= θ(n^2)
```

**TC: `O(n^2)`**
**SC: `O(n^2)`**