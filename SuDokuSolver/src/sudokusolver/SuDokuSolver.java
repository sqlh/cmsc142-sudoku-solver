package sudokusolver;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class SuDokuSolver {
    //<editor-fold defaultstate="collapsed" desc="Class variables">
    // The SuDoku problem
    private SuDoku problem;
    // A solution to the SuDoku problem
    private SuDoku solution;
    // The project file handler
    private SuDokuInputFileHandler sifh;
    // The number of SuDoku solutions
    private int SuDokuSolutions;
    // The number of SuDokuX solutions
    private int SuDokuXSolutions;
    // The number of SuDokuY solutions
    private int SuDokuYSolutions;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /***
     * The default constructor
     * @param filepath The file path of the project file
     * @throws IOException When the file does not exist
     */
    public SuDokuSolver(String filepath) throws IOException{
        this.sifh = new SuDokuInputFileHandler(filepath);
        this.SuDokuSolutions = 0;
        this.SuDokuXSolutions = 0;
        this.SuDokuYSolutions = 0;
    }
    //</editor-fold>
    
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
    
    /***
     * This checks the diagonal-forming cells of a SuDoku puzzle from zero up to
     * <code>dim</code>.
     * @param dim The dimensions on where to check the diagonals.
     * @return True of it does not violate the SuDoku property.
     */
    private boolean checkDiagonals(int dim){
        for(int i = 0; i < dim; i++){
            int toCheck1 = this.problem.getXY(i, i);
            int toCheck2 = this.problem.getXY(i, dim - 1 - i);
            for(int k = 0; k < dim; k++){
                if(k != i){
                    if(toCheck1 == this.problem.getXY(k, k)) return false;
                    if(toCheck2 == this.problem.getXY(k, dim - 1 - k))
                        return false;
                }
            }
        }
        return true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Puzzle checker methods">
    /***
     * Checks if <code>problem</code> is a valid SuDoku puzzle.
     * @return True if <code>problem</code> is a valid SuDoku puzzle
     */
    public boolean isSuDoku(){
        for(int i = 0; i < this.problem.getDimension(); i++)
            for(int j = 0; j < this.problem.getDimension(); j++)
                if(!checkCell(i, j)) return false;
        return true;
    }
    
    /***
     * Checks if <code>problem</code> is a valid SuDokuX puzzle.
     * @return True if <code>problem</code> is a valid SuDokuX puzzle
     */
    public boolean isSuDokuX(){
        int dim = this.problem.getDimension();
        if(!checkDiagonals(dim)) return false;
        if(!isSuDoku()) return false;
        return true;
    }
    
    /***
     * Checks if <code>problem</code> is a valid SuDokuY puzzle.
     * @return True if <code>problem</code> is a valid SuDokuY puzzle
     */
    public boolean isSuDokuY(){
        int dim = this.problem.getDimension();
        if(dim % 2 == 0) return false;
        if(!checkDiagonals(dim/2)) return false;
        for(int i = 0; i < dim / 2 + 1; i++)
            for(int j = 0; j < dim / 2 + 1; j++){
                if(!checkColumn(i, j, dim / 2 + 1, dim)) return false;
            }
        if(!isSuDoku()) return false;
        return true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Solver methods">
    /***
     * This method finds the next blank cell of the puzzle.
     * @return The next blank cell of the puzzle.
     */
    private Dimension findNextBlank(){
        int row, col;
        Dimension dim = new Dimension(0,0);
        
        for (row = 0; row < this.problem.getDimension(); ++row) {
            for (col = 0; col < this.problem.getDimension(); ++col) {
                int value = this.problem.getXY(row, col);
                if (value == 0) {
                    dim.setSize(row, col);
                    return dim;
                }
            }
        }
        dim.setSize(this.problem.getDimension(), this.problem.getDimension());
        return dim;
    }
    
    /***
     * This finds the <code>SuDoku</code>th or the <code>SuDokuX</code>th or the
     * <code>SuDokuY</code>th solution of the <code>problem</code>. The problem
     * is obtained by finding the <code>x</code>th problem of the project file
     * found in the <code>filepath</code>. The arguments are considered invalid
     * if there are two are more non-zero values for the <code>SuDoku</code>,
     * <code>SuDokuX</code> and <code>SuDokuY</code> variables.
     * @param x The problem number
     * @param SuDoku The SuDoku solution number
     * @param SuDokuX The SuDokuX solution number
     * @param SuDokuY The SuDokuY solution number
     * @param filepath The file path of the project file.
     * @throws IOException When the file is not found.
     * @throws IllegalArgumentException When the arguments are invalid.
     */
    public void solve(int x, int SuDoku, int SuDokuX, int SuDokuY,
            String filepath) throws IOException, IllegalArgumentException{
        // The output stream.
        PrintStream out =
                new PrintStream(new FileOutputStream(new File(filepath)));
        // The flag variable for the number of non-zero variables.
        int flag = 0;
        
        // Re-initialization of class variables.
        this.SuDokuSolutions = 0;
        this.SuDokuXSolutions = 0;
        this.SuDokuYSolutions = 0;
        this.problem = this.sifh.getProblem(x);
        this.solution = new SuDoku(this.problem.getDimension());
        
        //Determining non-zero values.
        //Printing on the buffer follows.
        if(SuDoku != 0) flag++;
        if(SuDokuX != 0) flag++;
        if(SuDokuY != 0) flag++;
        if(flag > 1){
            if(filepath == null)
                throw new IllegalArgumentException("Should only have one "
                        + "non-zero argument.");
            else{
                System.setOut(out);
                System.out.println("Argument invalid.");
                System.out.println(0);
                System.out.println(0);
                System.out.println(0);
            }
        }
        
        //Recursive computation of the solution. The printing of summary
        //follows.
        recurse(0, SuDoku, SuDokuX, SuDokuY);
        printSummary(x);
        
        //This is generated when the user-suppllied inputs exceed that of the
        //total number of solutions. Else the array is printed in the specified
        //buffer.
        if(filepath == null){
            if(this.SuDokuSolutions < SuDoku ||
                    this.SuDokuXSolutions < SuDokuX ||
                    this.SuDokuYSolutions < SuDokuY)
                throw new IllegalArgumentException("Your argument is invalid.");
            else this.solution.printSuDoku();
        }else{
            System.setOut(out);
            if(this.SuDokuSolutions < SuDoku ||
                    this.SuDokuXSolutions < SuDokuX ||
                    this.SuDokuYSolutions < SuDokuY){
                System.out.println("Argument invalid.");
                System.out.println(this.SuDokuSolutions);
                System.out.println(this.SuDokuXSolutions);
                System.out.println(this.SuDokuYSolutions);
            }else{
                for(int i = 0; i < this.solution.getDimension(); i++){
                    for(int j = 0; j < this.solution.getDimension(); j++){
                        System.out.print(this.solution.getXY(i, j)+" ");
                    }
                    System.out.println("");
                }
            }
        }
    }
    
    /***
     * This is the recursive computation for the solution of the SuDoku problem.
     * The solution will be written in <code>solution</code> when the desired
     * puzzle number is obtained.
     * @param depth The recursion depth. It must not exceed the square of the dimension.
     * @param SuDoku The SuDoku solution number
     * @param SuDokuX The SuDokuX solution number
     * @param SuDokuY The SuDokuY solution number
     */
    private void recurse(int depth, int SuDoku, int SuDokuX, int SuDokuY){
        Dimension emptyCell;
        if(depth > (int) Math.pow(this.problem.getDimension(), 2))
            throw new ArrayIndexOutOfBoundsException("Recursion too deep.");
        
        //Backtracking part.
        for(int i = 0; i < this.problem.getDimension(); i++)
            for(int j = 0; j < this.problem.getDimension(); j++)
                if(this.problem.getXY(i, j) != 0)
                    if(!checkCell(i, j)) return;
        
        //Find next empty cell.
        emptyCell = findNextBlank();
        
        //If you cannot find any, a solution is found.
        if(emptyCell.height == this.problem.getDimension() &&
                emptyCell.width == this.problem.getDimension()){
            if(isSuDoku()) this.SuDokuSolutions++;
            if(isSuDokuX()) this.SuDokuXSolutions++;
            if(isSuDokuY()) this.SuDokuYSolutions++;
            if(this.SuDokuSolutions == SuDoku ||
                    this.SuDokuXSolutions == SuDokuX ||
                    this.SuDokuYSolutions == SuDokuY){
                for(int i = 0; i < this.problem.getDimension(); i++)
                    for(int j = 0; j < this.problem.getDimension(); j++)
                        this.solution.setXY(i, j, this.problem.getXY(i, j));
            }
            return;
        }
        
        //Else, fill the empty cell with values.
        for(int value = 1; value <= this.problem.getDimension(); value++){
            this.problem.setXY(emptyCell.width, emptyCell.height, value);
            recurse(depth, SuDoku, SuDokuX, SuDokuY);
            this.problem.setXY(emptyCell.width, emptyCell.height, 0);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    /***
     * Prints the summary of the <code>x</code>th problem of the project file.
     * @param x The problem number
     * @throws IOException When the file is not found.
     */
    public void printSummary(int x) throws IOException{
        System.out.println("Filename: "+this.sifh.getFilepath());
        System.out.println("Problem "+x+" out of "+this.sifh.getProblemCount());
        System.out.println("SuDoku solutions: "+getSuDokuSolutions());
        System.out.println("SuDokuX solutions: "+getSuDokuXSolutions());
        System.out.println("SuDokuY solutions: "+getSuDokuYSolutions());
    }
    
    /***
     * Returns the number of problems in the project file.
     * @return The number of problems in the project file.
     * @throws IOException When the file is not found.
     */
    public int getSuDokuProblemCount() throws IOException{
        return sifh.getProblemCount();
    }
    
    /***
     * Returns the number of SuDoku solutions found in the <code>problem</code>.
     * @return The number of SuDoku solutions.
     */
    public int getSuDokuSolutions() {
        return SuDokuSolutions;
    }
    
    /***
     * Returns the number of SuDokuX solutions found in the
     * <code>problem</code>.
     * @return The number of SuDokuX solutions.
     */
    public int getSuDokuXSolutions() {
        return SuDokuXSolutions;
    }
    
    /***
     * Returns the number of SuDokuY solutions found in the
     * <code>problem</code>.
     * @return The number of SuDokuY solutions.
     */
    public int getSuDokuYSolutions() {
        return SuDokuYSolutions;
    }
    //</editor-fold>
}