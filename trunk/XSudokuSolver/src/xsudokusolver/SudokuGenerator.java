package xsudokusolver;

import java.util.*;
import java.awt.Point;   

public class SudokuGenerator 
	{
		private int[][] sudoku;
		private Random ran;
		private ArrayList<Integer> array;

		private int size;
		private int regionSize;

		SudokuGenerator(int size) 
			{
				regionSize = size;
				this.size = size * size;
				ran = new Random();
				array = new ArrayList<Integer>();
			}

    public void generate(boolean bool) 
		{
			int current = 0;
			int[] temp = new int[size];
			sudoku = new int[size][size];
		
			while(current < size) 
				{
					temp[current]++;
					if(genRow(current)) 
						{
							current++;
							continue;
						}
			
					if(temp[current] < regionSize * regionSize * regionSize * 2) 
						{
							continue;
						}
						
					if(bool) 
					while(current % regionSize != 0) 
						{
							temp[current--] = 0;
						}
				
					temp[current] = 0;
					if(bool)
					System.out.println(". Starting over with row: "  + (current+1) + ".");
				}
				
			for(int i = 0; i < size; i++) 
				{
					for(int j = 0; j < size; j++) 
						{
							sudoku[i][j]++;
						}
				}
		}

		private boolean genRow(int row) 
			{
				for(int col = 0; col < size; col++) {
				if(fillArrayList(row, col) == 0) 
					{
						return false;
					}
				sudoku[row][col] = array.remove(ran.nextInt(array.size()));
			}
			return true;
		}
	
	private int fillArrayList(int row, int col) 
            {
				boolean[] available = new boolean[size];
				//flags
				for(int i = 0; i < size; i++)
					available[i] = true;

				for(int i = 0; i < row; i++)
					available[sudoku[i][col]] = false;

				for(int i = 0; i < col; i++)
					available[sudoku[row][i]] = false;
		
				Point rowRange = getRegionRowsOrCols(row);
				Point colRange = getRegionRowsOrCols(col);
		
				for(int i = rowRange.x; i < row; i++) 
                    {
						for(int j = colRange.x; j <= colRange.y; j++)
                            {
								available[sudoku[i][j]] = false;
                            }
                    }
		
				array.clear();
				for(int i = 0; i < size; i++) 
                    {
						if(available[i])
						array.add(i);
                    }
		
				return array.size();
            }
        
    private Point getRegionRowsOrCols(int rowOrCol) 
        {
            int x = (rowOrCol / regionSize) * regionSize;
            int y = x + regionSize - 1;
            Point point = new Point(x,y);
            return point;
        }

	public int[][] getSudoku() 
            {
				return sudoku;
            }
	
	public String toString() 
            {
				StringBuffer buffer = new StringBuffer(size * size * size);
				buffer.append('+');
				for(int i = 0; i < size * 2 + size - 2; i++) 
					{
						buffer.append('-');
					}
			
				if(size >= 16) 
					{
						for(int i = 0; i < regionSize * 2 + 4; i++)
							buffer.append('-');
					}
			
				buffer.append('+');
				String dash = new String(buffer);
				buffer.append("\n|");
			
				for(int i = 0; i < size; i++) 
						{        
							for(int j = 0; j < size; j++) 
								{    	
									if(size >= 16)
										{
											if(sudoku[i][j] < 16)
											buffer.append(' ');
										}
									buffer.append(' ');
									buffer.append(Integer.toHexString((sudoku[i][j])).toUpperCase()); 
									if(j % regionSize == regionSize - 1)
										buffer.append(" | ");
								}
							
							if(i % regionSize == regionSize -1) 
								{
									buffer.append('\n').append(dash);
								}
				
							buffer.append('\n');
							
							if(i < size -1)
								buffer.append('|');
						}
				
				return new String(buffer);
			}
    
	
}
