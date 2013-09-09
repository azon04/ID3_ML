/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onid3;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Nurul Fithria
 */
public class OnID3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        
        Parser _parser = new Parser();
        Dataset _dataset = _parser.loadDataset("tes.arff");
        _dataset.PrintDataset();
        
    }
}
