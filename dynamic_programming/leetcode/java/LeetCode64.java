// TC: O(M*N) ; SC: O(1)
public class Solution {
	public  int minPathSum(int[][] grid) {
    
		// first column
		for(int i = 1; i < grid.length; i++)
			grid[i][0] += grid[i - 1][0];

		// first row
		for(int i = 1; i < grid[0].length; i++)
			grid[0][i] += grid[0][i - 1];

		for (int i = 1; i < grid.length; i++) {
			for (int j = 1; j < grid[0].length; j++) {
				grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
			}
		}

		return grid[grid.length - 1][grid[0].length - 1];
	}

    public int minPathSumConcise(int[][] grid) {
        for(int i=1; i<grid[0].length; i++)
            grid[0][i] += grid[0][i-1];
        
        for(int i = 1; i<grid.length; i++)
            for(int j = 0; j<grid[0].length; j++)
                grid[i][j] += Math.min(j==0?Integer.MAX_VALUE:grid[i][j-1] , grid[i-1][j]);

        return grid[grid.length-1][grid[0].length-1];
    }	
	
}