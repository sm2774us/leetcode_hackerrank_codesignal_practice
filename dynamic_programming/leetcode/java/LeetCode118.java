import java.util.ArrayList;
import java.util.List;

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


// // Iterative Solution
// // TC: O(N^2) ; SC: O(N^2)
// public class Solution {
	// public List<List<Integer>> generate(int numRows) {
		// List<List<Integer>> triangle = new ArrayList<>();
		// for (int i = 0; i < numRows; i++) {
			// List<Integer> row = new ArrayList<>();
			// for (int j = 0; j <= i; j++) {
				// row.add(generateNum(triangle, i, j));
			// }
			// triangle.add(row);
		// }
		// return triangle;
	// }
    
	// public int generateNum(List<List<Integer>> triangle, int i, int j) {
		// if (j == 0 || j == i || i < 2) { 
			// return 1;
		// } else {
			// int a = triangle.get(i - 1).get(j - 1);
			// int b = triangle.get(i - 1).get(j);
			// return a + b;
		// }
	// }
// }