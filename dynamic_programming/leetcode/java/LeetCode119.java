import java.util.ArrayList;
import java.util.List;

// Using Dynamic Programming
// TC: O(N^2) ; SC: O(N^2)
public class Solution {

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
}

// Using Dynamic Programming without using set() of ArrayList
// TC: O(N^2) ; SC: O(N^2)
import java.util.Arrays;
public class Solution {

    public List<Integer> getRow(int rowIndex) {
        Integer[] row =  new Integer[rowIndex + 1];
        Arrays.fill(row, 0);
        row[0] = 1;
        for(int i = 1; i < rowIndex + 1; i++)
            for(int j = i; j >= 1; j--)
                row[j] += row[j - 1];
        return Arrays.asList(row);
    }

}

// Math-Based Solution: "n-choose-k formula ( or Combination Formula )"
// We calculate C(n, k) by this relation: C(n, k) = C(n, k-1) * ((n+1-k) / k), and C(n, 0) = 1
// TC: O(N) ; SC: O(1)
public class Solution {
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

// Math-based Solution: "Calculating binomial coefficients"
// According to the mathematical definition of Pascal's Triangle, each element is a **binomial coefficient** in the form of **`C(n, k)` (aka "n choose k")**.
// We calculate C(n, k) by this relation: C(n, k) = n! / (k!*(n-k)!)
// TC: O(N^2) ; SC: O(N^2)
public class Solution {   
   public long binomialCoeff(int n, int r) {
       long binomial = 1;
       
       if(r > n-r) r = n-r;
       
       for(int i=0; i<r; i++) {
           binomial *= (n-i);
           binomial /= (i+1);
       }
       
       return binomial;
   }

   public List<Integer> getRow(int rowIndex) {
       List<Integer> row = new ArrayList<>();
       if(rowIndex < 0) return row;
       
       if(rowIndex == 0) {
           row.add(1);
           return row;
       }
       
       row.add(1);
       for(int i=1; i<rowIndex; i++) {
           row.add((int)binomialCoeff(rowIndex, i));
       }
       row.add(1);
       
       return row;
   }
}