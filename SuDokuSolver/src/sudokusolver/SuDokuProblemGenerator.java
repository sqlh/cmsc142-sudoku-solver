package sudokusolver;

import java.awt.Point;   
import java.util.ArrayList;
import java.util.Random;
/***
 * This class generates a standard SuDoku problem and places it in a text file.
 * This initializes the first number of grids at random and solves the puzzle by
 * the <code>SuDokuSolver</code> class. Branch and bound will be used to
 * generate the SuDoku puzzle with the leat number of clues that will obtain a
 * unique solution.
 * @author Princess Jane Generoso, Abraham Darius Llave, Lawrence Namuco, Rick Jason Obrero
 */

public class SuDokuProblemGenerator{
    private int[][] sudoku;
    private Random ran;
    private ArrayList<Integer> array;
    private int size;
    private int regionSize;
    private SuDoku puzzle;
    private SuDokuSolver solver;
   
    SuDokuProblemGenerator(int size){
        regionSize = size;
        this.size = size * size;
        ran = new Random();
        array = new ArrayList<Integer>();
    }

    public void generate(boolean bool){
        int current = 0;
        int[] temp = new int[size];
	sudoku = new int[size][size];	
	while(current < size){
            temp[current]++;
            if(genRow(current)){
		current++;
            continue;
           }
	if(temp[current] < regionSize * regionSize * regionSize * 2) continue;
        if(bool) 
            while(current % regionSize != 0) temp[current--] = 0;			
        temp[current] = 0;
        }
        
        int k;
        int l;
        int m;
        int n;
        int counter = 0;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
               k = ran.nextInt(size);
               l = ran.nextInt(size);
     
               
               
                if (k == l || k<l)
                {
                        sudoku[i][j] = 0;
                      
                }
               
                
            }
	}
        
        convertPuzzle();
    }

    private boolean genRow(int row){
        
        for(int col = 0; col < size; col++) {
            if(fillArrayList(row, col) == 0) return false;
            sudoku[row][col] = array.remove(ran.nextInt(array.size()));
        }
        return true;
    }
    
    
    
    private int fillArrayList(int row, int col){
	boolean[] available = new boolean[size];
	//flags
	for(int i = 0; i < size; i++) available[i] = true;
        for(int i = 0; i < row; i++) available[sudoku[i][col]] = false;
        for(int i = 0; i < col; i++) available[sudoku[row][i]] = false;
        Point rowRange = getRegionRowsOrCols(row);
	Point colRange = getRegionRowsOrCols(col);
	for(int i = rowRange.x; i < row; i++)
            for(int j = colRange.x; j <= colRange.y; j++)
                available[sudoku[i][j]] = false;
        array.clear();
        for(int i = 0; i < size; i++)
            if(available[i])
                array.add(i);
        return array.size();
    }
        
    private Point getRegionRowsOrCols(int rowOrCol){
        int x = (rowOrCol / regionSize) * regionSize;
        int y = x + regionSize - 1;
        Point point = new Point(x,y);
        return point;
    }
    
    public int[][] getSudoku(){
        return sudoku;
    }
    
    @Override
    public String toString(){
	StringBuffer buffer = new StringBuffer(size * size * size);
        buffer.append('+');
	for(int i = 0; i < size * 2 + size - 2; i++) buffer.append('-');
        if(size >= 16)
            for(int i = 0; i < regionSize * 2 + 4; i++) buffer.append('-');
        buffer.append('+');
        String dash = new String(buffer);
        buffer.append("\n|");
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(size >= 16){
                    if(sudoku[i][j] < 16)
                        buffer.append(' ');
                }
                buffer.append(' ');
                buffer.append(sudoku[i][j]);
                if(j % regionSize == regionSize - 1)
                    buffer.append(" | ");
            }
            if(i % regionSize == regionSize -1) buffer.append('\n').append(dash);
            buffer.append('\n');
            if(i < size -1) buffer.append('|');
        }
        return new String(buffer);
    }
    
    
    
    private void convertPuzzle(){
        this.puzzle = new SuDoku(this.size);
        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                this.puzzle.setXY(i, j, this.sudoku[i][j]);
            }
        }
                    //this.puzzle.printSuDoku();

        SuDokuSolver ss = new SuDokuSolver(this.puzzle);
        int numberofsolutions = 0;
        int counter = 0;
        int count = 1;
        boolean cell = false;
        int container = 1;
        
        
        
        while(numberofsolutions!=1)
        {
            count = 1;
            container = 0;
            int k = ran.nextInt(size);
            int l = ran.nextInt(size);
            while(container != 0)
            {
                        k = ran.nextInt(size);
                        l = ran.nextInt(size);
                        container = (puzzle.getXY(k,l));
                        
                        System.out.print(container+"\n");
                        if(container == 0)
                        {

                            break;
                            
                        }

            }
                while(count != 9 || cell != true)
                {
                    
                    ss.setCellValue(k,l,count);
                    this.puzzle.setXY(k,l, count);
                    cell = ss.checkCell(k, l);
                    System.out.print(cell+" "+l+" "+k + "\n");
                    
                    //circuit breaker
                    if(cell) break;
                    count++;
                }
                
               numberofsolutions = ss.problemSolve();
               System.out.print("Number of Solutions : " + numberofsolutions + "\n");
               this.puzzle.printSuDoku();
               if(numberofsolutions == 1)
                {
                    break;
                }
               else if (numberofsolutions==0)
               {
                   ss.setCellValue(k, l, 0);
                   this.puzzle.setXY(k, l, 0);
                   k = ran.nextInt(size);
                   l = ran.nextInt(size);
                   ss.setCellValue(k, l, 0);
                   this.puzzle.setXY(k, l, 0);
                   k = ran.nextInt(size);
                   l = ran.nextInt(size);
                   ss.setCellValue(k, l, 0);
                   this.puzzle.setXY(k, l, 0);
                   
               }
               
            }
            
    
}
    
}