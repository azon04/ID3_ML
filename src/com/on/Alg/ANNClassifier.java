/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.on.Alg;

import com.on.Alg.ANN.ActivationFunction;
import com.on.Alg.ANN.Linear;
import java.util.ArrayList;
import onid3.Dataset;

/**
 *
 * @author Ahmad Fauzan
 */
public class ANNClassifier implements Classifier {

    public enum Mode {

        INCREMENTAL, BATCH
    };
    private Mode mode = Mode.INCREMENTAL;
    private Dataset dataset;
    private float[] weight;
    private float[] dw;
    private ActivationFunction function;
    private int maxIteration = 10;
    private float learningRate = 0.1f;
    private float minMSE = 0f;
    private int classIdx = 0;
    private float MSE = 1000.0f;

    public ANNClassifier(Dataset _dataset) {
        this.dataset = _dataset;
        weight = new float[this.dataset.getAttributes().size()];
        dw = new float[this.dataset.getAttributes().size()];
        classIdx = dataset.getAttributes().size() - 1;
        function = new Linear();
    }

    public ANNClassifier(Dataset _dataset, Mode mode) {
        this.dataset = _dataset;
        weight = new float[this.dataset.getAttributes().size()];
        this.mode = mode;
        dw = new float[this.dataset.getAttributes().size()];
        classIdx = dataset.getAttributes().size() - 1;
        function = new Linear();
    }

    private void calculateMSE() {
        float sum = 0.0f;
        for (int i = 0; i < dataset.getData().size(); i++) {
            int[] values = new int[dataset.getData().get(i).size()];
            values[0] = 1;
            int idx = 1;
            for (int j = 1; j <= values.length; j++) {
                if ((j - 1) != classIdx) {
                    values[idx++] = dataset.getData().get(i).get(j - 1).intValue() == 0 ? -1 : 1;
                }
            }

            float o = function.doFunction(values, weight);
            float t = dataset.getData().get(i).get(classIdx).intValue() == 0 ? -1 : 1;

            sum += (t - o) * (t - o);
        }

        MSE = sum / dataset.getData().size();
    }

    @Override
    public void GenerateModel() {
        if (mode == Mode.BATCH) {
            int iter = 0;
            for (int i = 0; i < dw.length; i++) {
                dw[i] = 0.0f;
            }
            for (int i = 0; i < weight.length; i++) {
                weight[i] = 0.5f;
            }
            while (iter < maxIteration && MSE > minMSE) {
                for (int i = 0; i < weight.length; i++) {
                    weight[i] += dw[i];
                }

                for (int i = 0; i < dw.length; i++) {
                    dw[i] = 0.0f;
                }


                for (int i = 0; i < dataset.getData().size(); i++) {

                    int[] values = new int[dataset.getData().get(i).size()];
                    values[0] = 1;
                    int idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            values[idx++] = dataset.getData().get(i).get(j - 1).intValue() == 0 ? -1 : 1;
                        }
                    }

                    float o = function.doFunction(values, weight);
                    float t = dataset.getData().get(i).get(classIdx).intValue() == 0 ? -1 : 1;

                    // Bias
                    dw[0] = dw[0] + learningRate * (t - o) * 1;
                    idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            dw[idx] = dw[idx] + learningRate * (t - o) * (dataset.getData().get(i).get(j - 1).intValue() == 0 ? -1 : 1);
                            idx++;
                        }
                    }
                    
                }
                iter++;
                calculateMSE();
                for (int i = 0; i < weight.length; i++) {
                    System.out.print(weight[i] + ",");
                }
                
                System.out.println("MSE = " + MSE);
            }
        } else if(mode == Mode.INCREMENTAL) {
            int iter = 0;
            for (int i = 0; i < dw.length; i++) {
                dw[i] = 0.0f;
            }
            for (int i = 0; i < weight.length; i++) {
                weight[i] = 0.5f;
            }
            while (iter < maxIteration && MSE > minMSE) {
                for (int i = 0; i < dataset.getData().size(); i++) {
                    for (int j = 0; j < weight.length; j++) {
                        weight[j] += dw[j];
                    }
                    int[] values = new int[dataset.getData().get(i).size()];
                    values[0] = 1;
                    int idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            values[idx++] = dataset.getData().get(i).get(j - 1).intValue() == 0 ? -1 : 1;
                        }
                    }

                    float o = function.doFunction(values, weight);
                    float t = dataset.getData().get(i).get(classIdx).intValue() == 0 ? -1 : 1;

                    // Bias
                    dw[0] = learningRate * (t - o) * 1;
                    idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            dw[idx] = learningRate * (t - o) * (dataset.getData().get(i).get(j - 1).intValue() == 0 ? -1 : 1);
                            idx++;
                        }
                    }
                    for (int j = 0; j < weight.length; j++) {
                        System.out.print(weight[j] + ",");
                    }
                    System.out.println("");
                }
                iter++;
                calculateMSE();
                
                System.out.println("MSE = " + MSE);
            }
        }
    }

    @Override
    public void GenerateModelBySplit(int splitPercent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void GenerateModelByKFold(int k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Classify(Dataset trainData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float accuration(ArrayList<ArrayList<Integer>> data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getClsIdx() {
        return classIdx;
    }

    @Override
    public void setClsIdx(int idx) {
        classIdx = idx;
    }
}
