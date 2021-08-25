/**
 *  Bottom-Up Dynamic Programming w/ Auxillary Space ... i.e., Tabulation
 *
 *  TC: O(N^2)
 *  SC: O(N)
 */
import java.util.List;

public class BottomUpSolution {
    public int minimumTotal(List<List<Integer>> triangle) {
        // create dp table with size of the largest column (which is number of rows)
        int[] table = new int[triangle.size() + 1];
        Arrays.fill(table, 0);
                
        // iterate over the entire triangle from bottom to top
        for (int row = triangle.size() - 1; row >= 0; row--) {
            for (int col = 0; col <= row; col++) {
                // either go to [row + 1, col] or [row + 1, col + 1]
                int remain = Math.min(table[col],
                                      table[col + 1]);
                
                // combine answer with current value
                table[col] = triangle.get(row).get(col) + remain;
            }
        }
        
        return table[0];
    }
}

/**
 *  Top-Down Dynamic Programming w/ Auxillary Space ... i.e., Memoization
 *
 *  TC: O(N^2)
 *  SC: O(N)
 */
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class TopDownSolutionOne {
    public int minimumTotal(List<List<Integer>> triangle) {
        Deque<Integer> queue = new LinkedList<Integer>();
        int count=triangle.size();
        queue.add(triangle.get(0).get(0));
        for (int i=1;i<count;i++){
            List<Integer> list= triangle.get(i);
            for (int j=0;j<=i;j++){
        	    int min=0;
                if (j==0)
            	    min=list.get(0)+queue.peekFirst();               	
                else if (j==i)
            	    min =list.get(j)+queue.pollFirst();              	
                else
            	    min = Math.min(queue.pollFirst(),queue.peekFirst())+list.get(j);              	               
                queue.addLast(min);
            }
        }
        int result=Integer.MAX_VALUE;
        for (int i=0;i<count;i++)
    	    result=Math.min(result, queue.pollFirst());
        return result;
    }	
}

/**
 *  Top-Down: Memoization.
 */
import java.util.List;

public class TopDownSolutionTwo {
    public int minimumTotal(List<List<Integer>> triangle) {
        int m = triangle.size();
        // use null to distinguish whether it is empty
        Integer[][] memo = new Integer[m][m];
        List<Integer> bottom = triangle.get(m-1);
        for (int i=0;i<bottom.size();i++){
            memo[m-1][i] = bottom.get(i);
        }
        
        return getMemo(0,0,memo,triangle);
    }
    
    private int getMemo(int x, int y, Integer[][]memo, List<List<Integer>> triangle ){
        if (x>=triangle.size() || y >=triangle.get(x).size())
            return 0;
        // Key point
        if (memo[x][y] != null)
            return memo[x][y];
        
        int self = triangle.get(x).get(y);
        int left = getMemo(x+1,y,memo,triangle) + self;
        int right = getMemo(x+1,y+1,memo,triangle) + self;
        
        return memo[x][y] = Math.min(left,right);
    }
}