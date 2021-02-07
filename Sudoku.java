import java.util.HashMap;

public class Sudoku {
    
    final int NO_VALUE = 0;
    final int MIN_VALUE = 1;
    final int MAX_VALUE = 9;
    final int GRID_SIZE = 3;

    HashMap<Integer, Integer> lowerbounds;
    HashMap<Integer, Integer> upperbounds;
    
    public final int boardSize;
    
    int[][] emptyBoard = {
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 } 
      };
      int[][] board;

    public Sudoku() {
        this.board = emptyBoard;
        this.boardSize = this.board.length;
        this.lowerbounds = new HashMap<Integer, Integer>();
        this.upperbounds = new HashMap<Integer, Integer>();
    }

    /**
     * Initial the starting board and the grid bounds of the sudoku.
     * @param initialSudoku
     */
    public void initializeSudoku(String initialSudoku) {
        if (!this.boardIsFilled()) {
            this.fillBoard(initialSudoku);
        }
        this.fillGridBounds();
    }

    /**
     * A grid of the sudoku consist of a square of 3x3.
     * If you fill in a number in row index 3 and column index 4 for example,
     * you are only interested in the corresponding grid in the middle of the sudoku.
     * Therefore, you have to find the grid bounds to check the required part of the sudoku.
     * In this example, the lowerbounds are 3 for both the row and the column.
     * The upperbounds are 5 for both the row and the column.
     */
    public void fillGridBounds() {
        this.fillLowerbounds();
        this.fillUpperbounds();
    }

    /**
     * Find and fill in the lowerbounds of the grids for each integer index of the sudoku.
     */
    public void fillLowerbounds() {
        int lowerbound = 0;
        for (int i = 0; i < this.boardSize; i++) {
            this.lowerbounds.put(i, lowerbound);
            if ((i+1) % GRID_SIZE == 0) {
                lowerbound += GRID_SIZE;
            }
        }
    }

    /**
     * Find and fill in the upperbounds of the grids for each integer index of the sudoku.
     */
    public void fillUpperbounds() {
        int upperbound = GRID_SIZE - 1;
        for (int i = 0; i < this.boardSize; i++) {
            this.upperbounds.put(i, upperbound);
            if ((i+1) % GRID_SIZE == 0) {
                upperbound += GRID_SIZE;
            }
        }
    }

    /**
     * Check if the board is already filled by searching for a value greater than 0.
     * @return true if the board is filled, false otherwise.
     */
    public boolean boardIsFilled() {
        for (int row = 0; row < this.boardSize; row++) {
            for (int column = 0; column < this.boardSize; column++){
                if (this.board[row][column] > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Fill the sudoku board with the given initialBoard.
     * @param initialBoard
     */
    public void fillBoard(String initialBoard) {
        int index = 0;
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++){
                int newVal = Integer.parseInt(String.valueOf(initialBoard.charAt(index)));
                if (newVal > 0) {
                    this.board[row][column] = newVal;
                }
                index += 1;
            }
        }
    }

    /**
     * Prints the current state of the sudoku.
     */
    public void printBoard() {
        for (int row = 0; row < boardSize; row++) {
            String rowToPrint = "|";
            if (row % GRID_SIZE == 0) {
                System.out.println("-------------");
            }
            for (int column = 0; column < boardSize; column++){
                if (this.board[row][column] == 0) {
                    rowToPrint += "-";
                } else {
                    rowToPrint += this.board[row][column];
                }
                if ((column + 1) % GRID_SIZE == 0) {
                    rowToPrint += "|";
                }
            }
            System.out.println(rowToPrint);
        }
    }

    /**
     * Calls the Solver class to solve the sudoku.
     */
    public void solve() {
        Solver solver = new Solver(this);
        solver.solve();
    }

    public static void main(String[] args) {
        String initialSudoku = "000820090500000000308040007100000040006402503000090010093004000004035200000700900";
        Sudoku sudoku = new Sudoku();
        if (args.length > 0 && args[0].length() == sudoku.boardSize * sudoku.boardSize) {
            initialSudoku = args[0];
        }
        
        sudoku.initializeSudoku(initialSudoku);

        System.out.println("Initial state:");
        sudoku.printBoard();
        sudoku.solve();

        System.out.println("Solved:");
        sudoku.printBoard();
    }
}
