public class SortGrid 
{
    private static int compares = 0;
    private static int[][] grid;

    // PUBLIC METHODS
    public static int sort(int[][] thisGrid) 
    {
        compares = 0;

        int cols = thisGrid.length;
        int rows = thisGrid[0].length;
        System.out.println(thisGrid[0].length);
        int[][] copyofThisGrid = new int[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

            }
        }


        //TO BE IMPLEMENTED
        return compares;
    }

    public static void quick3WaySort(int[][] thisGrid, int row, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivot = thisGrid[row][high];

        int lessThan = low;
        int equal = low;

        for (int col = low; col <= high; col++) {
            if (thisGrid[row][col] < pivot) {
                swap(row, lessThan, row, col);
                lessThan++;
                equal++;
            } else if (thisGrid[row][col] == pivot) {
                swap(row, equal, row, col);
                equal++;
            }
        }

        quick3WaySort(thisGrid, row, low, lessThan);
        quick3WaySort(thisGrid, row, lessThan + 1, equal);
        quick3WaySort(thisGrid, row, equal + 1, high);
        return;
    }
    //  HELPER METHODS 
    // returns true if value at (r1, c1) is less
    // than value at (r2, c2) and false otherwise;
    // counts as 1 compare
    private static boolean lessThan(int r1, int c1, int r2, int c2) 
    {
        compares++;
        
        if(grid[r1][c1] < grid[r2][c2])
            return true;
        
        return false;
    }

    // returns true if value at (r1, c1) is greater than
    // value at (r2, c2) and false otherwise;
    // counts as 1 compare
    private static boolean greaterThan(int r1, int c1, int r2, int c2) 
    {
        compares++;
        
        if(grid[r1][c1] > grid[r2][c2])
            return true;
        
        return false;
    }
    
    // swaps values in the grid
    // at (r1, c1) and (r2, c2);
    // assumes that the parameters
    // are within the bounds
    private static void swap(int r1, int c1, int r2, int c2) 
    {
        int temp = grid[r1][c1];
        grid[r1][c1] = grid[r2][c2];
        grid[r2][c2] = temp;
    }
}
