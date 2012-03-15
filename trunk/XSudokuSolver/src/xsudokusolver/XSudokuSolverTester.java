package xsudokusolver;

//<editor-fold defaultstate="collapsed" desc="Import statements">
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
//</editor-fold>

/***
 * This main class tests the Sudoku solver. This contains a
 * <code>JFileChooser</code> which selects the file to be parsed.
 * @author Princess Jane Generoso, Abraham Darius Llave, Lawrence Namuco, Rick Jason Obrero
 */
public class XSudokuSolverTester {
    /**
     * The main logic of the program.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
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
        
        XSudokuSolver xs = new XSudokuSolver(filename);
        //Modify the lines below to obtain problem.
        //for(int i = 1; i <= xs.getNumbers(); i++){
        //   xs.solve(i);
        //}
        xs.solve(2);
    }
}