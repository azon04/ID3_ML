/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onid3;

import com.on.Alg.ANN.Sig;
import com.on.Alg.ANN.Sign;
import com.on.Alg.ANN.Step;
import com.on.Alg.ANNClassifier;
import com.on.Alg.ANNMultiLayer;
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
        Dataset _dataset = _parser.loadDataset("and.arff");
        
        System.out.println("INCREMENTAL");
        System.out.println("ANN for AND dataset with Linear Function : ");
        ANNClassifier ann = new ANNClassifier(_dataset, ANNClassifier.Mode.INCREMENTAL);
        ann.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Sigmoid Function : ");
        ANNClassifier ann2 = new ANNClassifier(_dataset, ANNClassifier.Mode.INCREMENTAL, new Sig());
        ann2.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Sign Function : ");
        ANNClassifier ann3 = new ANNClassifier(_dataset, ANNClassifier.Mode.INCREMENTAL, new Sign());
        ann3.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Step Function : ");
        ANNClassifier ann4 = new ANNClassifier(_dataset, ANNClassifier.Mode.INCREMENTAL, new Step(0.0f));
        ann4.GenerateModel();
        System.out.println("");
        
        System.out.println("BATCH");
        System.out.println("ANN for AND dataset with Linear Function : ");
        ANNClassifier ann5 = new ANNClassifier(_dataset, ANNClassifier.Mode.BATCH);
        ann5.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Sigmoid Function : ");
        ANNClassifier ann6 = new ANNClassifier(_dataset, ANNClassifier.Mode.BATCH, new Sig());
        ann6.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Sign Function : ");
        ANNClassifier ann7 = new ANNClassifier(_dataset, ANNClassifier.Mode.BATCH, new Sign());
        ann7.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Step Function : ");
        ANNClassifier ann8 = new ANNClassifier(_dataset, ANNClassifier.Mode.BATCH, new Step(0.0f));
        ann8.GenerateModel();
        System.out.println("");
        
        System.out.println("ANN Multilayer with Backpropagation Algorithm");
        Dataset xnor_dataset = _parser.loadDataset("xnor.arff");
        ANNMultiLayer annMultiLayer = new ANNMultiLayer(xnor_dataset, new int[]{2,1}, new Sig());
        annMultiLayer.GenerateModel();
        
        //ann.GenerateModelBySplit(66);
        /* ModelData 1 */
        /*Dataset _dataset = _parser.loadDataset("data1.arff");
        _dataset.PrintDataset();
        ID3 id3 = new ID3(_dataset);
        
        System.out.println("Model data 1");
        System.out.println("==============");
        System.out.println("Full Model");
        System.out.println("==============");
        id3.GenerateModel();
        
        /* ModelData 2 */
        //Dataset _dataset2 = _parser.loadDataset("data2.arff");
        //_dataset2.PrintDataset();
        /*ID3 id3_2 = new ID3(_dataset2);
        
        System.out.println("Model Data ke 2");
        System.out.println("==============");
        System.out.println("Full Model");
        System.out.println("==============");
        id3_2.GenerateModel();
        
        
        /* Hasil Klasifikasi */
        /*Dataset _dataset3 = _parser.loadDataset("data3.arff");
        System.out.println("Hasil Klasifikasi Data 3 ke Model 1");
        System.out.println(id3.accuration(_dataset3.getData()) * 100 + "%");
        
        _dataset3.PrintDataset();
        
        System.out.println("Hasil Klasifikasi Data 3 ke Model 2");
        System.out.println(id3_2.accuration(_dataset3.getData())* 100 + "%");
        
        _dataset3.PrintDataset();
        
        System.out.println("Monk Problem 1");
        System.out.println("=================");
        System.out.println("Model Tree");
        System.out.println("");
        Dataset _datatrain1 =  _parser.loadDataset("monks-1.test");
        ID3 monkModel1 = new ID3(_datatrain1);
        monkModel1.classIdx = 0;
        monkModel1.GenerateModel();
        
        Dataset _datatest1 = _parser.loadDataset("monks-1.test");
        System.out.println("");
        System.out.println("Accuracy Test : " + monkModel1.accuration(_datatest1.getData())*100 + "%");
        
        System.out.println("====================");
        System.out.println("Monk Problem 2");
        System.out.println("=================");
        System.out.println("Model Tree");
        System.out.println("");
        Dataset _datatrain2 =  _parser.loadDataset("monks-2.train");
        ID3 monkModel2 = new ID3(_datatrain2);
        monkModel2.classIdx = 0;
        monkModel2.GenerateModel();
        
        Dataset _datatest2 = _parser.loadDataset("monks-2.test");
        System.out.println("");
        System.out.println("Accuracy Test : " + monkModel2.accuration(_datatest2.getData())*100 + "%");
        
        System.out.println("=================");
        System.out.println("Monk Problem 3");
        System.out.println("=================");
        System.out.println("Model Tree");
        System.out.println("");
        Dataset _datatrain3 =  _parser.loadDataset("monks-3.train");
        ID3 monkModel3 = new ID3(_datatrain3);
        monkModel3.classIdx = 0;
        monkModel3.GenerateModel();
        
        Dataset _datatest3 = _parser.loadDataset("monks-3.test");
        System.out.println("");
        System.out.println("Accuracy Test : " + monkModel3.accuration(_datatest3.getData())*100 + "%");
        */
    }
}
