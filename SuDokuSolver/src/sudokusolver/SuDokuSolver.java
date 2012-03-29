package sudokusolver;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/***
 * This class is used to solve a SuDoku puzzle. It also checks if a solution for
 * a puzzle is a SuDokuX, SuDokuY, or just a SuDoku solution. The solutions can
 * be outputted in a text file or in standard output, depending on the value of
 * the <code>filepath</code> class variable.
 * @author Princess Jane Generoso, Abraham Darius Llave, Lawrence Namuco, Rick Jason Obrero
 */
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
    // The SuDoku solution the user want
    private int userSuDokuSolutions;
    // The SuDokuX solution the user wants
    private int userSuDokuXSolutions;
    // he SuDokuY solution the user wantss
    private int userSuDokuYSolutions;
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
    
    public SuDokuSolver(SuDoku puzzle){
        this.SuDokuSolutions = 0;
        this.SuDokuXSolutions = 0;
        this.SuDokuYSolutions = 0;
        this.problem = new SuDoku(puzzle.getDimension());
        this.solution = new SuDoku(puzzle.getDimension());
        for(int i = 0; i < puzzle.getDimension(); i++)
            for(int j = 0; j < puzzle.getDimension(); j++)
                this.problem.setXY(i, j, puzzle.getXY(i, j));
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Cell checker methods">
    public void setCellValue(int x, int y, int value){
        this.problem.setXY(x, y, value);
    }   
    /***
     * This checks the i-jth cell of the SuDoku puzzle if it violates the SuDoku
     * property or not.
     * @param i The row
     * @param j The column
     * @return True if it$ does not violate the SuDoku property
     */
    public boolean checkCell(int i, int j){
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
     * This method is used by the <code>SuDokuProblemGenerator</code> to solve
     * for the random problem that is given. It returns the solved problem.
     * @return The solved SuDoku problem
     */
    public int problemSolve(){
        this.userSuDokuSolutions = 15;
        this.userSuDokuXSolutions = 0;
        this.userSuDokuYSolutions = 0;
        
        recurse(0);
        return this.SuDokuSolutions;
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
        PrintStream out = null;
        // The flag variable for the number of non-zero variables.
        int flag = 0;
        String tab = "     ";
        
        // Re-initialization of class variables.
        this.SuDokuSolutions = 0;
        this.SuDokuXSolutions = 0;
        this.SuDokuYSolutions = 0;
        this.problem = this.sifh.getProblem(x);
        this.solution = new SuDoku(this.problem.getDimension());
        
        //Initialization of the buffer variable
        if(filepath != null)
            out = new PrintStream(new FileOutputStream(new File(filepath)));
        
        //Reassignment of solution flags
        this.userSuDokuSolutions = SuDoku;
        this.userSuDokuXSolutions = SuDokuX;
        this.userSuDokuYSolutions = SuDokuY;
        
        //Determining non-zero values.
        //Printing on the buffer follows.
        if(SuDoku != 0) flag++;
        if(SuDokuX != 0) flag++;
        if(SuDokuY != 0) flag++;
        System.setOut(out);
        if(flag > 1){
            if(filepath == null){
                throw new IllegalArgumentException("<warning>Should only have one "
                        + "non-zero argument.</warning>");
            }else{                
                //System.out.println("<warning>Argument invalid.</warning>");
            }
        }
        
        //Recursive computation of the solution. The printing of summary
        //follows.
        recurse(0);
        
        
        //Abe 28mar-1219 | Generating solution anyway
       /* this.userSuDokuSolutions = 0;
        this.userSuDokuXSolutions = 0;
        this.userSuDokuYSolutions = 0;
        */
       
        
        //This is generated when the user-suppllied inputs exceed that of the
        //total number of solutions. Else the array is printed in the specified
        //buffer.
        if(filepath == null){
            if(this.SuDokuSolutions <= SuDoku ||
                    this.SuDokuXSolutions <= SuDokuX ||
                    this.SuDokuYSolutions <= SuDokuY)
                throw new IllegalArgumentException("<argument_invalid>1</argument_invalid>");
            else{
                printSummary(x);
                //this.solution.printSuDoku();
            }
        }else{
            //output XML header
            System.setOut(out);
            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
            System.out.println("<response>");            
            if(this.SuDokuSolutions <= SuDoku ||
                    this.SuDokuXSolutions <= SuDokuX ||
                    this.SuDokuYSolutions <= SuDokuY){
                 printSummary(x);                
               /* System.out.println(this.SuDokuSolutions);
                System.out.println(this.SuDokuXSolutions);
                System.out.println(this.SuDokuYSolutions);*/
            }else{
                /* So if solution count is over sudoku{" "|x|y}-th solution,
                 * just show the solution?
                 */               
               System.out.println( "<error>" );
               System.out.println( "<number>2</number>" );
               System.out.println( "<text>ARGUMENT INVALID</text>" );
               System.out.println( "</error>" );			
            }
        }
        System.out.println("</response>");
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
    private void recurse(int depth){
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
            if(this.SuDokuSolutions == this.userSuDokuSolutions ||
                    this.SuDokuXSolutions == this.SuDokuXSolutions ||
                    this.SuDokuYSolutions == this.userSuDokuYSolutions){
                for(int i = 0; i < this.problem.getDimension(); i++)
                    for(int j = 0; j < this.problem.getDimension(); j++)
                        this.solution.setXY(i, j, this.problem.getXY(i, j));
            }
            return;
        }
        
        if((this.SuDokuSolutions != 0 && this.SuDokuSolutions == 
                this.userSuDokuSolutions) ||
                (this.SuDokuXSolutions == this.userSuDokuXSolutions && 
                this.SuDokuXSolutions != 0)
                || (this.SuDokuYSolutions == this.userSuDokuYSolutions && 
                this.SuDokuYSolutions != 0) || this.SuDokuSolutions > 1000)
            return;
        
        //Else, fill the empty cell with values.
        for(int value = 1; value <= this.problem.getDimension(); value++){
            this.problem.setXY(emptyCell.width, emptyCell.height, value);
            recurse(depth);
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
        String tab = "     ";
        String matrix = "";
            System.out.println( "<error>" );
            System.out.println( "<number>0</number>" );
            System.out.println( "<text>NO_ERROR/SUCCESS</text>" );
            System.out.println( "</error>" );     
            System.out.println("<solution>");
            System.out.println( tab + "<filename>" + this.sifh.getFilepath() +"</filename>");
            System.out.println( tab + "<dimension>" + this.solution.getDimension() + "</dimension>");
            System.out.println( tab + "<problem>");
            System.out.println( tab + tab + "<order>" +  x + "</order>");
            System.out.println( tab + tab + "<count>" +  this.sifh.getProblemCount() + "</count>");
            System.out.println( tab + "</problem>");
            System.out.println( tab + "<s_orig>" +  getSuDokuSolutions() + "</s_orig>");
            System.out.println( tab + "<s_x>" + getSuDokuXSolutions() + "</s_x>");
            System.out.println( tab + "<s_y>" + getSuDokuYSolutions() + "</s_y>");
            System.out.print( tab + "<matrix>");
            if( getSuDokuSolutions() > 0 )
            {
            for(int i = 0, dim = this.solution.getDimension(); i < dim; i++){                
                for(int j = 0; j < dim; j++){
                   matrix += (this.solution.getXY(i, j)+"-");
                }                
            }            
            System.out.print( matrix.substring(0, matrix.length()-1 ));
            }
            System.out.println( "</matrix>" );
            System.out.println("</solution>");
            System.out.println("</xml>");
        
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