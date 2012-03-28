package sudokusolver;

import java.awt.Dimension;

/***
 * This class generates a standard SuDoku problem and places it in a text file.
 * This initializes the first number of grids at random and solves the puzzle by
 * the <code>SuDokuSolver</code> class. Branch and bound will be used to
 * generate the SuDoku puzzle with the leat number of clues that will obtain a
 * unique solution.
 * @author Princess Jane Generoso, Abraham Darius Llave, Lawrence Namuco, Rick Jason Obrero
 */
public class SuDokuProblemGenerator {
    private SuDoku problem;

    public SuDokuProblemGenerator(int dimension) {
        this.problem = new SuDoku(dimension);
        generateCompletePuzzle();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Cell checker methods">
    /***
     * This checks the i-jth cell of the SuDoku puzzle if it violates the SuDoku
     * property or not.
     * @param i The row
     * @param j The column
     * @return True if it does not violate the SuDoku property
     */
    private boolean checkCell(int i, int j){
        int dim = this.problem.getDimension();
        if(!checkColumn(i, j, 0, dim)) return false;
        if(!checkRow(i, j, dim)) return false;
        if(!checkGrid(i, j, dim)) return false;
        return true;
    }
    
    /***
     * This checks the i-jth cell of the SuDoku puzzle if it violates the SuDoku
     * property in its corresponding column.
     * @param i The row
     * @param j The column
     * @param start Starting row
     * @param end Ending row
     * @return True if it does not violate the SuDoku property
     */
    private boolean checkColumn(int i, int j, int start, int end){
        for (int column = start; column < end; column++)
            if (column != j && this.problem.getXY(i, column) ==
                    this.problem.getXY(i, j))
                return false;
        return true;
    }
    
    /***
     * This checks the i-jth cell of the SuDoku puzzle if it violates the Sudoku
     * property in its corresponding row.
     * @param i The row
     * @param j The column
     * @param dim The dimension of the SuDoku puzzle
     * @return True if it does not violate the SuDoku property
     */
    private boolean checkRow(int i, int j, int dim){
        for (int row = 0; row < dim; row++)
            if (row != i && this.problem.getXY(row, j) ==
                    this.problem.getXY(i, j))
                return false;
        return true;
    }
    
    /***
     * This checks the i-jth cell of the SuDoku puzzle if it violates the Sudoku
     * property in its corresponding grid.
     * @param i The row
     * @param j The column
     * @param dim The dimension of the SuDoku puzzle
     * @return True if it does not violate the SuDoku property
     */
    private boolean checkGrid(int i, int j, int dim){
        int d = this.problem.getDimension();
        int n = (int) Math.sqrt(d);
        for (int row = (i / n) * n; row < (i / n) * n + n; row++)
            for (int col = (j / n) * n; col < (j / n) * n + n; col++)
                if (row != i && col != j && this.problem.getXY(row, col) ==
                        this.problem.getXY(i, j))
                    return false;
        return true;
    }
    //</editor-fold>
    
    private void generateCompletePuzzle(){
        Dimension blankCell = new Dimension(0,0);
        SuDokuSolver solve;
        for(int i = 0; i < 1; i++){
            do{
                blankCell.height = (int) Math.random() * 15 % 9;
                blankCell.width = (int) Math.random() * 38 % 9;
            }while(this.problem.getXY(blankCell) != 0);
            
            do{
                int value = (int) Math.random() * 32 % 9;
                problem.setXY(blankCell, value);
            }while(!checkCell(blankCell.height, blankCell.width));
        }
        this.problem.printSuDoku();
        //solve = new SuDokuSolver(this.problem);
        //this.problem = solve.problemSolve();
    }
}