/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onid3;

import com.on.Alg.ID3;
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
        ID3 id3 = new ID3(_dataset);
        
        System.out.println("");
        System.out.println("==============");
        System.out.println("Full Model");
        System.out.println("==============");
        id3.GenerateModel();
        
        System.out.println("");
        System.out.println("==============");
        System.out.println("Slit Model : 66 %");
        System.out.println("==============");
        //id3.showGainCalculation = false;
        id3.GenerateModelBySplit(66);
        
        System.out.println("");
        System.out.println("==============");
        System.out.println("k-fold Model : 3");
        System.out.println("==============");
        //id3.showGainCalculation = false;
        id3.GenerateModelByKFold(3);
    }
}
