package xsudokusolver;

/**
 * This is a standard Sudoku class which stores the values for generic Sudoku
 * problems.
 * @author Princess Jane Generoso, Abraham Darius Llave, Lawrence Namuco, Rick Jason Obrero
 */
public class Sudoku {
    //<editor-fold defaultstate="collapsed" desc="Class variables">
    //The Sudoku problem.
    private int[][] array;
    //The dimension of the Sudoku problem.
    private int dimension;
    //The square root of the dimension of the Sudoku problem.
    private int root;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Default constructor">
    /***
     * The default constructor of the class.
     * @param dimension The dimension of the Sudoku problem.
     */
    public Sudoku (int dimension){
        this.dimension = dimension;
        this.root = (int) Math.sqrt(dimension);
        this.array = new int[dimension][dimension];
    }
    //</editor-fold>
    
    /***
     * Sets the <code>x</code>-<code>y</code> element of the <code>array</code>
     * to <code>val</code>.
     * @param x The row of <code>array</code>
     * @param y The column of <code>array</code>
     * @param val The value to be set on the <code>x</code>-<code>y</code> element of the <code>array</code>.
     */
    public void setXY(int x, int y, int val){
        this.array[x][y] = val;
    }
    
    /***
     * Returns the <code>x</code>-<code>y</code> element of the
     * <code>array</code>.
     * @param x The row of <code>array</code>
     * @param y The column of <code>array</code>
     * @return The <code>x</code>-<code>y</code> element of the <code>array</code>.
     */
    public int getXY(int x, int y){
        return this.array[x][y];
    }
    
    /***
     * Returns the Sudoku puzzle as a two-dimensional integer array.
     * @return The Sudoku puzzle.
     */
    public int[][] getArray(){
        return this.array;
    }
    
    /***
     * Returns the square root of the dimensions of the Sudoku puzzle.
     * @return The square root of the dimensions of the Sudoku puzzl
     */
    public int getRoot() {
        return this.root; 
    }
    
    /***
     * Returns the dimensions of the Sudoku puzzle.
     * @return The dimensions of the Sudoku puzzle.
     */
    public int getDimension() {
        return this.dimension;
    }
    
    /***
     * Prints the integer array as a Sudoku puzzle in standard output stream.
     */
    public void printArray(){
        for (int n = 0; n < this.dimension; ++n) {
            if (n % this.root == 0){
                for (int k = 0; k < this.dimension; k++)
                    System.out.print("---");
                System.out.println();
            }
            for (int j = 0; j < this.dimension; ++j) {
                if (j % this.root == 0) System.out.print("| ");
                System.out.print(this.array[n][j] == 0
                                 ? " "
                                 : Integer.toString(this.array[n][j]));

                System.out.print(' ');
            }
            System.out.println("|");
        }
        for (int k = 0; k < this.dimension; k++)
            System.out.print("---");
        System.out.println();
    }
}
