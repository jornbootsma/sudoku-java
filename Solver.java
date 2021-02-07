import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {

    Sudoku sudoku;

    public Solver(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Solve the sudoku with a backtracking algorithm.
     * @return false if an error has occured in the sudoku, true otherwise.
     */
    public boolean solve() {
        for (int row = 0; row < sudoku.boardSize; row ++) {
            for (int column = 0; column < sudoku.boardSize; column ++) {
                if (sudoku.board[row][column] == sudoku.NO_VALUE) {
                    for (int k = sudoku.MIN_VALUE; k <= sudoku.MAX_VALUE; k++) {
                        sudoku.board[row][column] = k;
                        if (this.isValid(row, column) && this.solve()) {
                            return true;
                        }
                        sudoku.board[row][column] = sudoku.NO_VALUE;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if the current state of the sudoku is still valid.
     * @param row
     * @param column
     * @return true if there a no errors yet, false otherwise.
     */
    public boolean isValid(int row, int column) {
        return this.rowConstraint(row) && this.columnConstraint(column) && this.gridConstraint(row, column);
    }

    /**
     * Check the row constraint for a given row.
     * @param row
     * @return true if the row constraint is satisfied, false otherwise.
     */
    public boolean rowConstraint(int row) {
        int value;
        List<Integer> list = new ArrayList<>();
        for (int column = 0; column < sudoku.boardSize; column ++) {
            value = sudoku.board[row][column];
            if (value != sudoku.NO_VALUE) {
                list.add(value);
            }
            
            if (Collections.frequency(list, value) > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check the column constraint for a given column.
     * @param column
     * @return true if the column constraint is satisfied, false otherwise.
     */
    public boolean columnConstraint(int column) {
        int value;
        List<Integer> list = new ArrayList<>();
        for (int row = 0; row < sudoku.boardSize; row ++) {
            value = sudoku.board[row][column];
            if (value != sudoku.NO_VALUE) {
                list.add(value);
            }
            
            if (Collections.frequency(list, value) > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check the grid constraint for a given row and column.
     * @param row
     * @param column
     * @return true if the grid constraint is satisfied, false otherwise.
     */
    public boolean gridConstraint(int row, int column) {
        int rowLowerbound = sudoku.lowerbounds.get(row);
        int rowUpperbound = sudoku.upperbounds.get(row);
        int columnLowerbound = sudoku.lowerbounds.get(column);
        int columnUpperbound = sudoku.upperbounds.get(column);

        int value;
        List<Integer> list = new ArrayList<>();
        for (int i = rowLowerbound; i <= rowUpperbound; i++) {
            for (int j = columnLowerbound; j <= columnUpperbound; j ++) {
                value = sudoku.board[i][j];
                if (value != sudoku.NO_VALUE) {
                    list.add(value);
                }
                if (Collections.frequency(list, value) > 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
