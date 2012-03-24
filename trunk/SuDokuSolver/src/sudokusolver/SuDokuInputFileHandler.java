package sudokusolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class contains basic file input handling mechanisms for the SuDoku
 * solver.
 * @author Princess Jane Generoso, Abraham Darius Llave, Lawrence Namuco, Rick Jason Obrero
 */
public class SuDokuInputFileHandler {
    //<editor-fold defaultstate="collapsed" desc="Class variables">
    // The file path of the project file.
    private String filepath;
    // The number of problems in the project file.
    private int problemCount;
    // The problem that will be solved by the solver in the project file.
    private SuDoku problem;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /***
     * The default constructor.
     * @param filepath The file path of the project input file.
     * @throws IOException When the file is not found.
     */
    public SuDokuInputFileHandler(String filepath) throws IOException {
        this.filepath = filepath;
        getProblemCount();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    /***
     * Returns the file path of the project file.
     * @return The file path of the project file.
     */
    public String getFilepath(){
        return this.filepath;
    }
    
    /***
     * Returns the number of problems in the project file.
     * @return The number of problems in the project file.
     * @throws IOException When the file is not found.
     */
    final int getProblemCount() throws IOException{
        if(this.filepath.isEmpty())
            throw new IllegalArgumentException("There is no filename yet.");
        else{
            Path file = Paths.get(this.filepath);
            BufferedReader reader =
                    Files.newBufferedReader(file, Charset.defaultCharset());
            this.problemCount = Integer.parseInt(reader.readLine());
            return this.problemCount;
        }
    }
    
    public SuDoku getProblem(int i) throws IOException, NumberFormatException{
        String line;
        int j = 0;
        int num = 0;
        int root = 0;
        boolean firstLine = true;
        
        //Gather the file.
        Path file = Paths.get(this.filepath);
        BufferedReader reader =
                Files.newBufferedReader(file, Charset.defaultCharset());
        
        //Gather the matrix.
        while ((line = reader.readLine()) != null && num <= this.problemCount) {
            if(firstLine){
                firstLine = false;
            }else{
                String[] contents = line.split(" ");
                if(contents.length == 1){
                    num++;
                    //Stop gathering at the ith matrix.
                    if(num == i + 1) break;
                    root = Integer.parseInt(contents[0]);
                    
                    this.problem = new SuDoku(root*root);
                    j = 0;
                }else{
                    for(int k = 0; k < root*root; k++)
                        this.problem.setXY(j, k, Integer.parseInt(contents[k]));
                    j++;
                }
            }
        }
        
        return this.problem;
    }
    //</editor-fold>
}
