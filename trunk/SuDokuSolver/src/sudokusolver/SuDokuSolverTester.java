package sudokusolver;

import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class is needed to test the SuDoku solver.
 * @author Rick Jason Obrero
 */
public class SuDokuSolverTester {
    public static void main(String[] args) throws IOException, 
            IllegalArgumentException {
        String filename = null;
        if(args.length == 0){
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Text Files", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                filename = chooser.getSelectedFile().getAbsolutePath();
            }
        }else{
            filename = args[0];
        }
        
        SuDokuSolver xs = new SuDokuSolver(filename);
        //Modify the lines below to obtain problem.
        //for(int i = 1; i <= xs.getSuDokuProblemCount(); i++){
        //    System.out.println("Problem "+i+":");
        //    xs.solve(i, 0, 0, 0, null);
        //}
        xs.solve(1, 1, 0, 0, "C:\\Users\\intel\\Documents\\output.txt");
    }
}
