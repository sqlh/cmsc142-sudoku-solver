package xsudokusolver;

//<editor-fold defaultstate="collapsed" desc="Import statements">
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
//</editor-fold>

/**
 * This class presents the SudokuX Solver which intersperse some functions from
 * the existing Sudoku class. The main argument of the constructor of this class
 * is the filename of the project text file to be parsed.
 * 
 * 
 * Methods of this class will be explained thoroughly.
 * @author Princess Jane Generoso, Abraham Darius Llave, Lawrence Namuco, Rick Jason Obrero
 */
public class XSudokuSolver{
    //<editor-fold defaultstate="collapsed" desc="Class variables">
    //The number of Sudoku puzzles to be solved in the project text file.
    private int numbers;
    //The number of Sudoku solutions found in a problem in the project text file.
    private int sudokuSolutions = 0;
    //The number of SudokuX solutions found in a problem in the project text file.
    private int sudokuXSolutions = 0;
    //The number of SudokuY solutions found in a problem in the project text file.
    private int sudokuYSolutions = 0;
    //An instance of a Sudoku problem in the project file.
    private Sudoku array;
    //The absolute path of the project text file.
    private String filename;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /***
     * The default constructor of this class. The class automatically gathers
     * the number of problems that it will solve.
     * This class throws an <code>IOException</code> if the file is not found.
     * @param filename The absolute path of the project text file.
     */
    public XSudokuSolver(String filename) {
        this.filename = filename;
        try {
            parseNumberOnFile();
        } catch (IOException ex) {
            Logger.getLogger(XSudokuSolver.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="File handling methods">
    /***
     * This gets the number of problems that the <code>XSudokuSolver</code> will
     * solve.
     * @throws IllegalArgumentException This will be thrown if the <code>filename</code> class variable is not initialized.
     * @throws IOException This will be thrown if the path of the <code>filename</code> class variable is not found.
     */
    private void parseNumberOnFile()
            throws IllegalArgumentException, IOException{
        if(this.filename.isEmpty())
            throw new IllegalArgumentException("There is no filename yet.");
        else{
            Path file = Paths.get(this.filename);
            BufferedReader reader =
                    Files.newBufferedReader(file, Charset.defaultCharset());
            this.numbers = Integer.parseInt(reader.readLine());
        }
    }
    
        /***
     * This gathers the <code>i</code>th problem of the project text file, and
     * it makes the corresponding <code>Sudoku</code> array.
     * @param i The problem to be solved.
     * @throws IOException If the project text file is not found.
     * @throws NumberFormatException If the project file contains other characters than numbers.
     */
    private void getMatrix(int i) throws IOException, NumberFormatException{
        String line;
        int j = 0;
        int num = 0;
        int root = 0;
        boolean firstLine = true;
        
        //Gather the file.
        Path file = Paths.get(this.filename);
        BufferedReader reader =
                Files.newBufferedReader(file, Charset.defaultCharset());
        
        //Gather the matrix.
        while ((line = reader.readLine()) != null && num <= this.numbers) {
            if(firstLine){
                firstLine = false;
            }else{
                String[] contents = line.split(" ");
                if(contents.length == 1){
                    num++;
                    //Stop gathering at the ith matrix.
                    if(num == i + 1) break;
                    root = Integer.parseInt(contents[0]);
                    
                    this.array = new Sudoku(root*root);
                    j = 0;
                }else{
                    for(int k = 0; k < root*root; k++)
                        this.array.setXY(j, k, Integer.parseInt(contents[k]));
                    j++;
                }
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sudoku checker methods">
    /***
     * This checks if a Sudoku puzzle is a valid Sudoku puzzle or not.
     * @param puzzle The Sudoku puzzle to be checked.
     * @return If it is a Sudoku puzzle or not.
     */
    public boolean isSudoku(Sudoku puzzle){
        for(int i = 0; i < puzzle.getDimension(); i++)
            for(int j = 0; j < puzzle.getDimension(); j++)
                if(!isValid(i, j)) return false;
        return true;
    }
    
    /***
     * This checks if a Sudoku puzzle is a valid SudokuX puzzle or not.
     * @param puzzle The Sudoku puzzle to be checked.
     * @return If it is a SudokuX puzzle or not.
     */
    public boolean isSudokuX(Sudoku puzzle){
        boolean isSudoku = false;
        int dim = puzzle.getDimension();
        isSudoku = isSudoku(puzzle);
        if(!isSudoku) return false;
        for(int i = 0; i < dim; i++){
            int toCheck1 = puzzle.getXY(i, i);
            int toCheck2 = puzzle.getXY(i, dim - 1 - i);
            for(int k = 0; k < dim; k++){
                if(k != i){
                    if(toCheck1 == puzzle.getXY(k, k)) return false;
                    if(toCheck2 == puzzle.getXY(k, dim - 1 - k)) return false;
                }
            }
        }
        if(isSudoku) return true;
        return false;
    }
    
    /***
     * This checks if a Sudoku puzzle is a valid SudokuY puzzle or not.
     * @param puzzle The Sudoku puzzle to be checked.
     * @return If it is a SudokuY puzzle or not.
     */
    public boolean isSudokuY(Sudoku puzzle){
        boolean isSudoku = isSudoku(puzzle);
        int dim = puzzle.getDimension();
        if(dim % 2 == 0) return false;
        if(!isSudoku) return false;
        
        for(int i = 0; i <= dim / 2; i++){
            int toCheck1 = puzzle.getXY(i, i);
            int toCheck2 = puzzle.getXY(i, dim - 1 - i);
            for(int k = 0; k <= dim / 2; k++){
                if(k != i){
                    if(toCheck1 == puzzle.getXY(k, k)) return false;
                    if(toCheck2 == puzzle.getXY(k, dim - 1 - k)) return false;
                }
            }
        }
        for(int i = dim /  2; i < dim; i++){
            int toCheck1 = puzzle.getXY((dim / 2), i);
            for(int k = dim / 2; k < dim; k++){
                if(k != i){
                    if(toCheck1 == puzzle.getXY((dim / 2), k)) return false;
                }
            }
        }
        
        if(isSudoku) return true;
        return false;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Solver methods">    
    /***
     * Finds the coordinates of the nearest blanks of the <code>array</code>
     * class variable.
     * @return The coordinates of the nearest blanks of the array.
     */
    private Dimension findBlanks(){
        int row, col;
        Dimension dim = new Dimension(0,0);
        
        for (row = 0; row < this.array.getDimension(); ++row) {
            for (col = 0; col < this.array.getDimension(); ++col) {
                int value = this.array.getArray()[row][col];
                if (value == 0) {
                    dim.setSize(row, col);
                    return dim;
                }
            }
        }
        dim.setSize(this.array.getDimension(), this.array.getDimension());
        return dim;
    }
    
    /***
     * Checks if the Sudoku puzzle is still valid for the <code>i</code>th and
     * <code>j</code>th cell of the puzzle.
     * @param i Row of the Sudoku puzzle.
     * @param j Row of the Sudoku puzzle.
     * @return True if the Sudoku puzzle is still valid.
     */
    private boolean isValid(int i, int j) {
        for (int column = 0; column < this.array.getDimension(); column++)
            if (column != j && this.array.getXY(i, column) ==
                    this.array.getXY(i, j))
                return false;
        
        for (int row = 0; row < this.array.getDimension(); row++)
            if (row != i && this.array.getXY(row, j) == this.array.getXY(i, j))
                return false;
        
        int n = this.array.getRoot();
        for (int row = (i / n) * n; row < (i / n) * n + n; row++)
            for (int col = (j / n) * n; col < (j / n) * n + n; col++)
                if (row != i && col != j && this.array.getXY(row, col) ==
                        this.array.getXY(i, j))
                    return false;
        
        return true;
    }
    
    /***
     * Resets the <code>sudokuSolutions</code> and the
     * <code>sudokuXsolutions</code> and gathers the <code>num</code>th problem
     * of the project text file.
     * @param num The <code>num</code>th problem of the project text file.
     * @return True if there is at least one solution.
     */
    public boolean solve(int num){
        this.sudokuSolutions = 0;
        this.sudokuXSolutions = 0;
        try{
            getMatrix(num);
        }catch(IOException ex){
        }
        Sudoku puzzle = this.array;
        resolve(puzzle, 0);
        if(this.sudokuSolutions == 0) return false;
        return true;
    }
    
    /***
     * The recursive backtracking method which solves for the Sudoku puzzle. It
     * recurses on the blank cells, and tries every possible value for them.
     * @param puzzle The Sudoku puzzle.
     * @param depth The number of Sudoku grids that are solved.
     */
    private void resolve(Sudoku puzzle, int depth){
        Dimension emptyCell;
        if(depth > (int) Math.pow(this.array.getDimension(), 2))
            throw new ArrayIndexOutOfBoundsException("Recursion too deep.");
        
        //Backtracking part.
        for(int i = 0; i < this.array.getDimension(); i++)
            for(int j = 0; j < this.array.getDimension(); j++)
                if(this.array.getXY(i, j) != 0)
                    if(!isValid(i, j)) return;
        
        //Find empty cells.
        emptyCell = findBlanks();
        
        //If you cannot find any, a solution is found.
        if(emptyCell.height == this.array.getDimension() &&
                emptyCell.width == this.array.getDimension()){
            if(isSudoku(puzzle)){
                this.sudokuSolutions++;
                System.out.println("Solution "+this.sudokuSolutions+" found!");
            }
            if(isSudokuX(puzzle)){
                this.sudokuXSolutions++;
                System.out.println("SudokuX Solution "+
                        this.sudokuXSolutions+" found!");
            }
            if(isSudokuY(puzzle)){
                this.sudokuYSolutions++;
                System.out.println("SudokuY Solution "+
                        this.sudokuYSolutions+" found!");
            }
            printArray(puzzle);
            System.out.println();
            return;
        }
        
        //Else, fill the empty cell with values.
        for(int value = 1; value <= this.array.getDimension(); value++){
            puzzle = fill(puzzle, emptyCell, value);
            resolve(puzzle, depth + 1);
            puzzle = fill(puzzle, emptyCell, 0);
        }
    }
    
    /***
     * Fills <code>value</code> in the Sudoku puzzle in the
     * <code>emptyCell</code>th cell.
     * @param puzzle The Sudoku puzzle.
     * @param emptyCell The empty cell.
     * @param value The value which will be filled in the <code>emptyCell</code>.
     * @return The Sudoku puzzle.
     */
    public Sudoku fill(Sudoku puzzle, Dimension emptyCell, int value){
        puzzle.setXY(emptyCell.width, emptyCell.height, value);
        return puzzle;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and setter methods">
    /***
     * Returns the absolute path of the project text file.
     * @return The absolute path of the project text file.
     */
    public String getFilename() {
        return this.filename;
    }
    
    /***
     * Sets the absolute path of the project text file.
     * @param filename The absolute path of the project text file.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    /***
     * Returns the number of problems in the project text file.
     * @return The number of problems in the project text file.
     */
    public int getNumbers(){
        return this.numbers;
    }
    
    /***
     * Prints the contents of the <code>array</code> class variable.
     */
    public void printArray(){
        this.array.printArray();
    }
    
    /***
     * Prints the contents of the <code>array</code> formal parameter.
     * @param array
     */
    public void printArray(Sudoku array){
        array.printArray();
    }
    
    /***
     * Gets the number of the Sudoku solutions found. This will not return the
     * correct value without invoking the <code>solve</code> method first.
     * @return The number of correct Sudoku solutions.
     */
    public int getSudokuSolutions(){
        return this.sudokuSolutions;
    }
    
    /***
     * Gets the number of the SudokuX solutions found. This will not return the
     * correct value without invoking the <code>solve</code> method first.
     * @return The number of correct SudokuX solutions.
     */
    public int getSudokuXSolutions(){
        return this.sudokuXSolutions;
    }
    //</editor-fold>
}