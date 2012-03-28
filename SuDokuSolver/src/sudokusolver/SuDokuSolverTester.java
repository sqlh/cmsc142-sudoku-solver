package sudokusolver;

import java.io.IOException;

/**
 * This class is needed to test the SuDoku solver.
 * @author Rick Jason Obrero
 */
public class SuDokuSolverTester {
    public static void main(String[] args) throws IllegalArgumentException, 
            IOException {
//        int generateProblem = Integer.parseInt(args[0]);
//        int problemNumber = Integer.parseInt(args[1]);
//        int SuDoku = Integer.parseInt(args[2]);
//        int SuDokuX = Integer.parseInt(args[3]);
//        int SuDokuY = Integer.parseInt(args[4]);
//        String fileIn = args[5];
//        String fileOut = args[6];
        
        int generateProblem = 0;
        int problemNumber = 1;
        int SuDoku = 1;
        int SuDokuX = 0;
        int SuDokuY = 0;
        String fileIn = "C:\\Users\\intel\\Documents\\puzzles\\newspaper.txt";
        String fileOut = "C:\\Users\\intel\\Documents\\puzzles\\output.txt";
        
        switch (generateProblem){
            case 0:
                SuDokuSolver xs = new SuDokuSolver(fileIn);
                xs.solve(problemNumber, SuDoku, SuDokuX, SuDokuY, fileOut);
            break;
            case 1:
                SuDokuProblemGenerator sg = new SuDokuProblemGenerator(3);
            break;
            default:
                throw new IllegalArgumentException("Wrong argument! [0,1]");
        }
    }
}