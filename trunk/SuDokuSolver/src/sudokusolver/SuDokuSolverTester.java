package sudokusolver;

import java.io.IOException;

/**
 * This class is needed to test the SuDoku solver.
 * @author Rick Jason Obrero
 */
public class SuDokuSolverTester {
    public static void main(String[] args) throws IllegalArgumentException, 
            IOException {
        int generateProblem = Integer.parseInt(args[0]);
        int problemNumber = Integer.parseInt(args[1]);
        int SuDoku = Integer.parseInt(args[2]);
        int SuDokuX = Integer.parseInt(args[3]);
        int SuDokuY = Integer.parseInt(args[4]);
        String fileIn = args[5];
        String fileOut = args[6];
        
        switch (generateProblem){
            case 0:
                SuDokuSolver xs = new SuDokuSolver(fileIn);
                xs.solve(1, SuDoku, SuDokuX, SuDokuY, fileOut);
            break;
            case 1:
                SuDokuProblemGenerator sg = new SuDokuProblemGenerator(3);
            break;
            default:
                throw new IllegalArgumentException("Wrong argument! [0,1]");
        }
    }
}