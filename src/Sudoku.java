public class Sudoku {
    private int[][] board = new int[9][9];

    /*I make this method public so that there is no unexpected behavior that could arise from public access to parameters*/
    /**
     * This method fills the sudoku board and
     * @Returns true if the board was filled and false if it could not be fully filled
     */

    public boolean fillBoard() {
        return fillBoard(0,0);
    }

    /*This method is the private fillBoard to allow for safe parameter control and recursion*/

    /**
     * This function recursively fills the sudoku board and
     */
    private boolean fillBoard(int row, int col) {
        /*Base case: if we have no more rows to go through, return true !*/
        if(row == 9)
            return true;
        if(col == 9) //If we have gone through the entire row, go to the start of the next row
            return fillBoard(row + 1, 0);

        /**
         * We randomly generate a number from 1-9 and store it into firstCandidate so that
         * we have an exit case within the while loop below.
         * After candidate is incremented we check to see if we have looped all the way back to the
         * first candidate number, which would mean that none of them were possible
         */

        int candidate = (int)(Math.random() * 9) + 1;
        int firstCandidate = candidate;

        while(board[row][col] == 0) { //while we haven't yet found a valid candidate

            if(isValid(candidate, row, col)) { //if candidate number is valid at the current coordinates
                board[row][col] = candidate;

                /**
                 * THE MOST IMPORTANT PART
                 * we check for fillBoard(row, col + 1), if there is no possible solution, we would get false.
                 * Therefore, if there is no possible solution in the next cell, we obviously don't want
                 * to keep our current candidate, so we reset the current cell to 0 to stay within the while loop
                 * and get the next candidate to continue the search.
                 */
                if(!fillBoard(row, col + 1)) {
                    board[row][col] = 0;
                }
                else {
                    break; //exits the loop to next recursive step as to avoid the candidateComparisons
                }
            }
            candidate = candidate == 9 ? 1 : candidate + 1; //if current candidate is not valid, try the next one !

            if(candidate == firstCandidate) //for the next candidate, if we have gone through all of the candidates
                return false; //there were no possible solutions, so backtrack
        }

        /**
         * We reach here when we have found a valid number for the current coordinates that doesn't lead to a dead end.
         * So, recurse.
         */
        return fillBoard(row, col + 1);

    }

    /*Returns true if the value given for the coordinates given is a valid, legal move in Sudoku*/
    private boolean isValid(int value, int row, int col) {

        //if value is already in the given row, return false
        for(int cell : board[row])
            if(cell == value)
                return false;

        //if value is already in the given col, return false
        for(int[] r : board)
            if(r[col] == value)
                return false;

        //if value is already in the current sub-grid, return false
        int subGridRow = row / 3; //gets the row of which sub-grid the coordinate is in
        int subGridCol = col / 3; //gets the col of which sub-grid the coordinate is in

        for(int i = 0; i < 3; i++) //sub-grid has three rows
            for(int j = 0; j < 3; j++) //and three cols
                if(board[i + subGridRow * 3][j + subGridCol * 3] == value) //starts at the top-left corner of the sub-grid then iterates through
                    return false;

        //if none of the above was found, it is a valid move
        return true;
    }

    /*Prints the board in its entirety*/
    public void printBoard() {
        for(int i = 0; i < board.length; i++) {
            if(i % 3 == 0) {
                System.out.println("—————————————————————————");
            }

            for(int j = 0; j < board[0].length; j++) {
                if(j % 3 == 0)
                    System.out.print("|");
                System.out.print(j % 3 == 1 ? board[i][j] : " " + board[i][j] + " ");
                if(j == board[0].length - 1)
                    System.out.println("|");
            }

            if(i == board.length - 1) {
                System.out.println("—————————————————————————");
            }

        }
    }

    /**
     * Prints a portion of the board based on the given difficulty
     * @Input: easy: 44% print rate
     *        medium: 38% print rate
     *        hard: 30% print rate
     * */
    public void printBoard(String difficulty) throws Exception {
        double printRate = 1;
        switch (difficulty.toLowerCase()) {
            case "easy":
                printRate = 0.44;
                break;
            case "medium":
                printRate = 0.38;
                break;
            case "hard":
                printRate = 0.30;
                break;
            default:
                throw new Exception(difficulty + " is not a valid input. Expected: easy, medium, or hard.");
        }
        for(int i = 0; i < board.length; i++) {
            if(i % 3 == 0) {
                System.out.println("—————————————————————————");
            }

            for(int j = 0; j < board[0].length; j++) {
                if(j % 3 == 0)
                    System.out.print("|");
                if(Math.random() < printRate)
                    System.out.print(j % 3 == 1 ? board[i][j] : " " + board[i][j] + " ");
                else
                    System.out.print(j % 3 == 1 ? " " : "   ");

                if(j == board[0].length - 1)
                    System.out.println("|");
            }

            if(i == board.length - 1) {
                System.out.println("—————————————————————————");
            }

        }
    }
    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();
        String difficulty = "Easy";

        sudoku.fillBoard();
        sudoku.printBoard();
        try {
            sudoku.printBoard(difficulty);
            sudoku.printBoard("hard");
            sudoku.printBoard("very super hard");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
