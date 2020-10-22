public class SortGrid 
{
    private static int compares = 0;
    private static int[][] grid;
    private static int[][] copyGrid;

    // PUBLIC METHODS
    public static int sort(int[][] thisGrid) 
    {
        compares = 0;
        int cols = thisGrid.length;
        int rows = thisGrid[0].length;
        grid = thisGrid;
        System.out.println("Cols: " + cols + "\nRows: " + rows);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print("" + grid[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
        /* sort each row */
        for (int row = 0; row < rows; row++) {
            quickSort(row, 0, cols - 1); // cols - 1 is last index of each row
//            System.out.println("AFTER");
//            System.out.print("[");
//            for (int i = 0; i < cols; i++) {
//                System.out.print("" + grid[row][i] + ", ");
//            }
//            System.out.print("]\n");
        }
        System.out.println("QUICKSORT COMPARES = " + compares);
        /* prints sorted grid */
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print("" + grid[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("------------------------");

        /* copy grid */
        int[][] copyofThisGrid = new int[cols][rows];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                copyofThisGrid[row][col] = grid[row][col];
            }
        }
        copyGrid = copyofThisGrid;



        /* MergeSort Rows */
        int interval = 2;
        int log2Rows = (int) Math.floor((Math.log(rows) / Math.log(2)));
        //System.out.println("divisible " + log2Rows + " times");
        for (int i = 0; i < log2Rows; i++) {

            for (int startRow = 0; startRow < Math.pow(2, log2Rows); startRow += interval) { //merge what can evenly be merged

                int endRow1 = startRow + (interval / 2) - 1; //last row before next subarray
                int endRow2 = startRow + (interval) - 1; //last row before next subarray
                //System.out.println("startRow1, endRow1: " + startRow + ", " + endRow1);
                //System.out.println("startRow2, endRow2: " + (startRow + (interval / 2)) + ", " + endRow2);
                merge(startRow, endRow1, startRow + (interval / 2), endRow2, cols);
            }
            //System.out.println();
            interval *= 2; //merging 2 so interval becomes larger
        }
        //System.out.println("FINISHED MAIN MERGE");
        for (int i = log2Rows; i < rows; i++) {
            merge(0, i - 1, i, i, cols);
        }


        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = copyGrid[row][col];
            }
        }
        //TO BE IMPLEMENTED
        return compares;
    }

    public static void merge(int startRowA1, int finalRowA1, int startRowA2, int finalRowA2, int maxCol) {
//        System.out.println("finalA1 =  " + finalRowA1 + "\nfinalA2 = " + finalRowA2);
        if ((startRowA1 > startRowA2) || ((finalRowA1 + 1) != startRowA2)) {
            System.out.println("BAD INPUT: ROW1 BIGGER THAN ROW2");
            return;
        }
        int a1Row = startRowA1;
        int a1Col = 0;
        int a2Row = startRowA2;
        int a2Col = 0;

        int totalChanges = maxCol * ((finalRowA1 - startRowA1) + (finalRowA2 - startRowA2) + 2); //add 2 because both are inclusive

        int currentCol = 0;
        int currentRow = startRowA1;

        for (int i = 0; i < totalChanges; i++) {

//                System.out.println("a1: " + a1Row + ", " + a1Col);
//                System.out.println("a2: " + a2Row + ", " + a2Col);
            if (a1Row > finalRowA1) {
                copyGrid[currentRow][currentCol] = grid[a2Row][a2Col];
                a2Col++;
            } else if (a2Row > finalRowA2) {
                copyGrid[currentRow][currentCol] = grid[a1Row][a1Col];
                a1Col++;
            } else if (lessThan(a1Row, a1Col, a2Row, a2Col)) {
                copyGrid[currentRow][currentCol] = grid[a1Row][a1Col];
                a1Col++;
            } else {
                copyGrid[currentRow][currentCol] = grid[a2Row][a2Col];
                a2Col++;
            }
            //System.out.print("" + copyGrid[currentRow][currentCol] + ' ');
            currentCol++;

            if (currentCol >= maxCol) {
                currentCol = 0;
                currentRow++;
                //System.out.println();
            }

            if (a1Col >= maxCol) {
                a1Col = 0;
                a1Row++;
            }
            if (a2Col >= maxCol) {
                a2Col = 0;
                a2Row++;
            }
        }

        for (int row = startRowA1; row <= finalRowA2; row++) {
            for (int col = 0; col < maxCol; col++) {
                grid[row][col] = copyGrid[row][col];
            }
        }
    }

    public static void quickSort(int row, int low, int high) {
        if ((low < 0) || (high < 0) || (high >= grid.length) || (low >= high)) {
            return;
        }

        swap(row, high, row, low + ((high - low) / 2));


        int pivotCol = high;
        int lessThan = low;

        for (int col = low; col < high; col++) {
            if (!greaterThan(row, col, row, pivotCol)) {
                swap(row, lessThan, row, col);
                lessThan++;
            }
        }
        swap(row, lessThan, row, pivotCol);

        quickSort(row, low, lessThan - 1);
        quickSort(row, lessThan + 1, high);
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
