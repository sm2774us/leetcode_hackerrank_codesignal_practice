# Dynamic Programming | Number Tower
# [LC-119 : Pascal's Triangle II](https://leetcode.com/problems/pascals-triangle-ii/)

## Solution Explanation
The first thing to realize is that this is a recursive problem because there is a recurrence relation. So we should first try to solve the sub-problem, which is to find the i-th row and j-th column of the triangle. We begin by finding the base case and recursive case, then work our way through to see if this problem is optimizable.

**Base Case**
---
We can begin by defining a function `f(i, j)` which returns the _i-th row_ and _j-th column_ of the pascal's triangle. We see from the triangle's pattern that the beginning and end of the triangle always has values of 1. So it follows that `f(i, j) = 1` when `j = 1` or `j = i`.

**Recursive Case**
---
We also know from the description that the current _i-th row_ and _j-th column_ is the sum of the two values above in the previous row. This gives: `f(i, j) = f(i - 1, j - 1) + f(i - 1, j)`

### Using Dynamic Programming
---
```java
public List<Integer> getRow(int rowIndex) {
    List<Integer> row = new ArrayList<>();
    if(rowIndex < 0) return row;
    
    row.add(1);
    for(int i=1; i<=rowIndex; i++) {
        for(int j=i-1; j>0; j--) {
            row.set(j, (row.get(j-1) + row.get(j)));
        }
        row.add(1);
    }
    
    return row;
}
```

### Using Dynamic Programming without using set() of ArrayList
---
```java
public List<Integer> getRow(int rowIndex) {
      Integer[] row =  new Integer[rowIndex + 1];
      Arrays.fill(row, 0);
      row[0] = 1;
      for(int i = 1; i < rowIndex + 1; i++)
        for(int j = i; j >= 1; j--)
          row[j] += row[j - 1];
      return Arrays.asList(row);
}
```

## Time and Space Analysis
* **TC: `O(n^2)`**
* **SC: `O(n^2)`**

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


```java
class Solution {
    private int nChooseK(long n, long k) {
        if(k == 0) return 1;
        return (int) (nChooseK(n, k-1) * (n-k+1) / k);
    }
	
    public List<Integer> getRow(int rowIndex) {
        List<Integer> ret = new ArrayList<Integer>(rowIndex+1);
        for(int i = 0 ; i <= rowIndex ; i++) {
        	ret.add(nChooseK(rowIndex, i));
        }
        return ret;
    }
}

// Calculating binomial coefficients
//class Solution {   
//    public long binomialCoeff(int n, int r) {
//        long binomial = 1;
//        
//        if(r > n-r) r = n-r;
//        
//        for(int i=0; i<r; i++) {
//            binomial *= (n-i);
//            binomial /= (i+1);
//        }
//        
//        return binomial;
//    }
//
//    public List<Integer> getRow(int rowIndex) {
//        List<Integer> row = new ArrayList<>();
//        if(rowIndex < 0) return row;
//        
//        if(rowIndex == 0) {
//            row.add(1);
//            return row;
//        }
//        
//        row.add(1);
//        for(int i=1; i<rowIndex; i++) {
//            row.add((int)binomialCoeff(rowIndex, i));
//        }
//        row.add(1);
//        
//        return row;
//    }
//}
```

## Time and Space Analysis
* **TC: `O(n)`**
* **SC: `O(1)`**