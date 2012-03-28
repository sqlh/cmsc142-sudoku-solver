package sudokusolver;

import java.awt.Dimension;

/***
 * This class is the basic SuDoku puzzle class. This emulates the SuDoku puzzle
 * into a three-dimensional array.
 * @author Princess Jane Generoso, Abraham Darius Llave, Lawrence Namuco, Rick Jason Obrero
 */
public class SuDoku {
    //<editor-fold defaultstate="collapsed" desc="Class variables">
    // The integer array which contains the SuDoku puzzle and its candidates.
    private Integer[][][] array;
    // The dimensions of the SuDoku puzzle.
    private int dimension;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constrcutor">
    /**
     * The defult constructor.
     * @param dimension The dimension of the SuDoku puzzle
     */
    public SuDoku(int dimension){
        this.array = new Integer[dimension][dimension][dimension+1];
        this.dimension = dimension;
        for(int i = 0; i < dimension; i++)
            for(int j = 0; j < dimension; j++)
                setXY(i, j, 0);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter and setter methods">
    /**
     * Sets the x-yth cell of the SuDoku puzzle to the value.
     * @param x The row of the cell
     * @param y The column of the cell
     * @param value The value to plug in the cell
     */
    public final void setXY(int x, int y, int value){
        this.array[x][y][0] = value;
    }
    
    /**
     * Sets the x-th cell of the SuDoku puzzle to the value.
     * @param x The cell
     * @param value The value to plug in the cell
     */
    public void setXY(Dimension x, int value){
        this.array[x.height][x.width][0] = value;
    }
    
    /**
     * Gets the value of the x-yth cell of the SuDoku puzzle.
     * @param x The row of the cell
     * @param y The column of the cell
     * @return The value of the x-yth cell
     */
    public int getXY(int x, int y){
        return this.array[x][y][0];
    }
    
    /**
     * Gets the value of the x-th cell of the SuDoku puzzle.
     * @param x The cell
     * @return The value of the x-th cell
     */
    public int getXY(Dimension x){
        return this.array[x.height][x.width][0];
    }
    
    /**
     * Returns the dimension of the SuDoku puzzle.
     * @return The dimension of the SuDoku puzzle.
     */
    public int getDimension(){
        return this.dimension;
    }
    
    /**
     * Returns the SuDoku puzzle in a two-dimensional array.
     * @return The SuDoku puzzle
     */
    public Integer[][] getArray(){
        Integer[][] arr = new Integer[this.dimension][this.dimension];
        for(int i = 0; i < this.dimension; i++){
            for(int j = 0; j < this.dimension; j++){
                arr[i][j] = getXY(i, j);
            }
        }
        return arr;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Puzzle printer">
    /**
     * Prints the SuDoku puzzle in standard output.
     */
    public void printSuDoku(){
        int root = (int) Math.sqrt(this.dimension);
        for(int i = 0; i < this.dimension; ++i){
            if(i % root == 0){
                for(int g = 0; g < this.dimension+1; ++g)
                    System.out.print("----");
                System.out.println("");
            }
            for(int j = 0; j < this.dimension; ++j){
                if(j % root == 0) System.out.print("|");
                System.out.print(" ");
                if(this.array[i][j][0] == 0) System.out.print("  ");
                else{
                    if(this.array[i][j][0].toString().length() == 1){
                        System.out.print("0"+this.array[i][j][0].toString());
                    }else{
                        System.out.print(this.array[i][j][0].toString());
                    }
                }
                System.out.print(" ");
            }
            System.out.println("|");
        }
        for (int k = 0; k < this.dimension+1; k++)
            System.out.print("----");
        System.out.println();
    }
    //</editor-fold>
}