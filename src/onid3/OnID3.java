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
import com.on.clustering.BisectingKMeans;
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
        Dataset trainset = _parser.loadDataset("PlayTennis_Cluster.arff");
        Dataset answerset = _parser.loadDataset("PlayTennis_Cluster_ans.arff");
        BisectingKMeans bisectingKMeans = new BisectingKMeans(2, trainset);
        bisectingKMeans.doCluster();
        bisectingKMeans.printResult();
        System.out.println("Accuracy : " + bisectingKMeans.accuracy(answerset)*100 + "%" );
        
//        Parser _parser = new Parser();
//        Dataset trainset = _parser.loadDataset("trainingUTS.arff");
//        Dataset testset = _parser.loadDataset("test.arff");
//        ID3 id3 = new ID3(trainset);
//        System.out.println("ID3");
//        id3.GenerateModel();
//        System.out.println("Accuracy Test set : " + id3.accuration(trainset.getData()));
//        
//        System.out.println("");
//        System.out.println("ANN");
//        ANNClassifier ann = new ANNClassifier(trainset, ANNClassifier.Mode.INCREMENTAL);
//        ann.setLearningRate(0.25f);
//        ann.setMaxIteration(100);
//        ann.GenerateModel();
//        System.out.println("Accuracy Test set : " + ann.accuration(trainset.getData()));
//        
        
        /*Dataset _dataset = _parser.loadDataset("and.arff");
        System.out.println("(A)");
        System.out.println("INCREMENTAL");
        System.out.println("ANN for AND dataset with Linear Function : ");
        ANNClassifier ann = new ANNClassifier(_dataset, ANNClassifier.Mode.INCREMENTAL);
        ann.setReps(ANNClassifier.Rep.MODIF, new float[]{-1.0f, 1.0f});
        ann.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Sigmoid Function : ");
        ANNClassifier ann2 = new ANNClassifier(_dataset, ANNClassifier.Mode.INCREMENTAL, new Sig());
        ann2.setReps(ANNClassifier.Rep.MODIF, new float[]{-1.0f, 1.0f});
        ann2.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Sign Function : ");
        ANNClassifier ann3 = new ANNClassifier(_dataset, ANNClassifier.Mode.INCREMENTAL, new Sign());
        ann3.setReps(ANNClassifier.Rep.MODIF, new float[]{-1.0f, 1.0f});
        ann3.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Step Function : ");
        ANNClassifier ann4 = new ANNClassifier(_dataset, ANNClassifier.Mode.INCREMENTAL, new Step(0.0f));
        ann4.setReps(ANNClassifier.Rep.MODIF, new float[]{-1.0f, 1.0f});
        ann4.GenerateModel();
        System.out.println("");
        
        System.out.println("BATCH");
        System.out.println("ANN for AND dataset with Linear Function : ");
        ANNClassifier ann5 = new ANNClassifier(_dataset, ANNClassifier.Mode.BATCH);
        ann5.setReps(ANNClassifier.Rep.MODIF, new float[]{-1.0f, 1.0f});
        ann5.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Sigmoid Function : ");
        ANNClassifier ann6 = new ANNClassifier(_dataset, ANNClassifier.Mode.BATCH, new Sig());
        ann6.setReps(ANNClassifier.Rep.MODIF, new float[]{-1.0f, 1.0f});
        ann6.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Sign Function : ");
        ANNClassifier ann7 = new ANNClassifier(_dataset, ANNClassifier.Mode.BATCH, new Sign());
        ann7.setReps(ANNClassifier.Rep.MODIF, new float[]{-1.0f, 1.0f});
        ann7.GenerateModel();
        
        System.out.println("");
        System.out.println("ANN for AND dataset with Step Function : ");
        ANNClassifier ann8 = new ANNClassifier(_dataset, ANNClassifier.Mode.BATCH, new Step(0.0f));
        ann8.setReps(ANNClassifier.Rep.MODIF, new float[]{-1.0f, 1.0f});
        ann8.GenerateModel();
        System.out.println("");
        
        System.out.println("(B)");
        Dataset xnor_dataset = _parser.loadDataset("xnor.arff");
        System.out.println("ANN for XNOR Incremental, Sigmoid");
        ANNClassifier ann9 = new ANNClassifier(xnor_dataset, ANNClassifier.Mode.INCREMENTAL, new Sig());
        ann9.setReps(ANNClassifier.Rep.MODIF, new float[]{-1.0f,1.0f});
        ann9.setMinMSE(0.001f);
        ann9.GenerateModel();
        
        System.out.println("ANN for XNOR Batch, Sigmoid");
        ANNClassifier ann10 = new ANNClassifier(xnor_dataset, ANNClassifier.Mode.BATCH, new Sig());
        ann10.setReps(ANNClassifier.Rep.MODIF, new float[]{-1.0f,1.0f});
        ann10.setMinMSE(0.001f);
        ann10.GenerateModel();
        
        System.out.println("");
        
        System.out.println("(C)");
        System.out.println("ANN Multilayer XNOR with Backpropagation Algorithm");
        ANNMultiLayer annMultiLayer = new ANNMultiLayer(xnor_dataset, new int[]{2,1}, new Sig());
        annMultiLayer.GenerateModel();
        
        System.out.println("");
        System.out.println("(D)");
        System.out.println("Playtennis Step Incremental");
        Dataset playtennis = _parser.loadDataset("data1.arff");
        ANNClassifier ann11 = new ANNClassifier(playtennis, new Step(0.0f));
        ann11.GenerateModel();
        
        System.out.println("");
        System.out.println("(E)");
        System.out.println("Playtennis Biner Step Incremental");
        Dataset playtennis_biner = _parser.loadDataset("data1_biner.arff");
        ANNClassifier ann12 = new ANNClassifier(playtennis_biner, ANNClassifier.Mode.INCREMENTAL, new Step(0.5f));
        ann12.GenerateModel();
        
        System.out.println("(F)");
        System.out.println("Playtennis Biner Step Batch");
        ANNClassifier ann13 = new ANNClassifier(playtennis_biner, ANNClassifier.Mode.BATCH, new Step(0.0f));
        ann13.GenerateModel();
        
        System.out.println("(G)");
        System.out.println("Playtennis Biner Step Batch Backpropagation");
        ANNMultiLayer ann14 = new ANNMultiLayer(playtennis_biner, new int[]{5,1}, new Step(0.0f));
        ann14.setMinMSE(0.001f);
        ann14.setMaxIteration(3);
        ann14.GenerateModel();
        */
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
