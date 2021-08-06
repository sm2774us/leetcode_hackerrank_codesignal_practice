# Dynamic Programming | Number Tower
# [LC-118 : Pascal's Triangle](https://leetcode.com/problems/pascals-triangle/)

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

### Solution-2: Visual Explanation

* **1)** First Loop fill first and last value of each row:
```
[
  [ 1 ],
  [ 1, 1 ],
  [ 1, <1 empty item>, 1 ],
  [ 1, <2 empty items>, 1 ],
  [ 1, <3 empty items>, 1 ]
]
```

* **2)** Second loop fills the ones in the middle missing by adding `dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j]`.
```
[
  [1],
  [1, 1],
  [1, 2, 1],
  [1, 3, 3, 1],
  [1, 4, 6, 4, 1],
];
```

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

### Solution-3: Math-based Solution
According to the mathematical definition of Pascal's Triangle, each element is a **binomial coefficient** in the form of **`C(n, k)` (aka "n choose k")**.
**Note: This is also commonly referred to as the "n-choose-k formula for binomial coefficients".**

We calculate **`C(n, k)`** by this relation: **`C(n, k) = C(n, k-1) * (n+1-k) / k`**, and **`C(n, 0) = 1`**

or,

**`C(n, k)` and `C(n, k-1)` are referred to as: `(n choose k)` and `(n choose k-1)` respectively.**
**`(n choose k) = (n choose k-1) * ((n + 1 - k) / k)`**

---

And this is the entire formulation and its eventual simplication:

---

* Combination formula can be rewritten to decompose to as follows **`C(n, k) = C(n, k-1) * (n+1-k) / k`**:
```
C(n, k) = n! / (k!*(n-k)!) 
        = n! / ((k-1)!*(n-k+1)!) * (n-k+1)/k 
		= C(n, k-1) * ((n+1-k)/k)

# So Pascal's triangle number can be calculated by left number C(row, column) = C(row, column-1)*(row+1-column)/column.
```
#### Reference (for Math-based Solution):
* [Wikipedia - Pascal's Triangle - Calculating a Row or Diagonal By Itself](https://en.wikipedia.org/wiki/Pascal%27s_triangle#Calculating_a_row_or_diagonal_by_itself)
* [Wikipedia - Pascal's Triangle](https://en.wikipedia.org/wiki/Pascal's_triangle)
* [Wolfram - Pascal's Triangle](https://mathworld.wolfram.com/PascalsTriangle.html)
* [Wolfram - Binomial Coefficient](https://mathworld.wolfram.com/BinomialCoefficient.html)

## Time and Space Analysis
For all 3 (Recursive and Iterative and Math-based) Solutions,
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

* **TC: `O(n^2)`**
* **SC: `O(n^2)`**