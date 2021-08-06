import java.util.ArrayList;
import java.util.List;

// Iterative Solution
// TC: O(N^2) ; SC: O(N^2)
public class Solution {
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
}

// Recursive Solution
// TC: O(N^2) ; SC: O(N^2)
public class Solution {
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
}


// Math-Based Solution: "n-choose-k formula for binomial coefficients"
// We calculate C(n, k) by this relation: C(n, k) = C(n, k-1) * ((n+1-k) / k), and C(n, 0) = 1
// TC: O(N^2) ; SC: O(N^2)
import java.util.List;
import java.util.LinkedList;

public List<List<Integer>> generate(int numRows) {
    List<List<Integer>> pascal = new LinkedList<List<Integer>>();
    for (int i = 0; i < numRows; ++i) {
        List<Integer> row = new LinkedList<Integer>();
        long curr = 1;  // the first one = C(n, 0) = 1
        row.add((int) curr);
        for (int j = 1; j <= i; ++j) {
            curr = curr * (i + 1 - j) / j;
            row.add((int) curr);
        }
        pascal.add(row);
    }
    return pascal;
}