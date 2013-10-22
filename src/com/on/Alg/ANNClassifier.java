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

    public enum Rep {

        INDEX, MODIF, BINER
    };
    Rep reps = Rep.INDEX;
    float[] repModif = null;
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

    public ANNClassifier(Dataset _dataset, ActivationFunction function) {
        this.dataset = _dataset;
        weight = new float[this.dataset.getAttributes().size()];
        dw = new float[this.dataset.getAttributes().size()];
        classIdx = dataset.getAttributes().size() - 1;
        this.function = function;
    }

    public ANNClassifier(Dataset _dataset, Mode mode, ActivationFunction function) {
        this.dataset = _dataset;
        weight = new float[this.dataset.getAttributes().size()];
        this.mode = mode;
        dw = new float[this.dataset.getAttributes().size()];
        classIdx = dataset.getAttributes().size() - 1;
        this.function = function;
    }

    private void calculateMSE() {
        float sum = 0.0f;
        for (int i = 0; i < dataset.getData().size(); i++) {
            int[] values = new int[dataset.getData().get(i).size()];
            values[0] = 1;
            int idx = 1;
            for (int j = 1; j <= values.length; j++) {
                if ((j - 1) != classIdx) {
                    if (reps == Rep.INDEX) {
                        values[idx++] = dataset.getData().get(i).get(j - 1).intValue();
                    } else if (reps == Rep.MODIF) {
                        values[idx++] = (int) repModif[dataset.getData().get(i).get(j - 1).intValue()];
                    } else {
                        //values[idx++] 
                    }
                }
            }

            float o = function.doFunction(values, weight);
            float t = 0;
            if (reps == Rep.INDEX) {
                t = dataset.getData().get(i).get(classIdx).intValue();
            } else if (reps == Rep.MODIF) {
                t = repModif[dataset.getData().get(i).get(classIdx).intValue()];
            } else {
                //t 
            }
            sum += (t - o) * (t - o);
        }

        MSE = sum / dataset.getData().size();
    }

    public void setMinMSE(float minMSE) {
        this.minMSE = minMSE;
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
                System.out.println("Iterasi " + (iter + 1));
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
                            if (reps == Rep.INDEX) {
                                values[idx++] = dataset.getData().get(i).get(j - 1).intValue();
                            } else if (reps == Rep.MODIF) {
                                values[idx++] = (int) repModif[dataset.getData().get(i).get(j - 1).intValue()];
                            } else {
                                //values[idx++] 
                            }
                        }
                    }

                    float o = function.doFunction(values, weight);
                    float t = 0;
                    if (reps == Rep.INDEX) {
                        t = dataset.getData().get(i).get(classIdx).intValue();
                    } else if (reps == Rep.MODIF) {
                        t = repModif[dataset.getData().get(i).get(classIdx).intValue()];
                    } else {
                        //t 
                    }
                    // Bias
                    dw[0] = dw[0] + learningRate * (t - o) * 1;
                    idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            float val = 0;
                            if (reps == Rep.INDEX) {
                                val = dataset.getData().get(i).get(j - 1).intValue();
                            } else if (reps == Rep.MODIF) {
                                val = repModif[dataset.getData().get(i).get(j - 1).intValue()];
                            } else {
                                //t 
                            }
                            dw[idx] = dw[idx] + learningRate * (t - o) * val;
                            idx++;
                        }
                    }
                    
                    for (int j = 0; j < values.length; j++) {
                        System.out.print(values[j] + "\t|");
                    }
                    for (int j = 0; j < weight.length; j++) {
                        System.out.print("w" + j +"=" + weight[j] + "\t|");
                    }
                    for (int j = 0; j < dw.length; j++) {
                        System.out.print("dw" + j +"=" + dw[j] + "\t|");
                    }
                    System.out.print("o = " + o + "\t|");
                    System.out.println("t = " + t + "\t|");

                }
                iter++;
                calculateMSE();
                
                System.out.println("MSE = " + MSE);
            }
        } else if (mode == Mode.INCREMENTAL) {
            int iter = 0;
            for (int i = 0; i < dw.length; i++) {
                dw[i] = 0.0f;
            }
            for (int i = 0; i < weight.length; i++) {
                weight[i] = 0.5f;
            }
            while (iter < maxIteration && MSE > minMSE) {
                System.out.println("Iterasi " + (iter + 1));
                for (int i = 0; i < dataset.getData().size(); i++) {
                    for (int j = 0; j < weight.length; j++) {
                        weight[j] += dw[j];
                    }
                    int[] values = new int[dataset.getData().get(i).size()];
                    values[0] = 1;
                    int idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            if (reps == Rep.INDEX) {
                                values[idx++] = dataset.getData().get(i).get(j - 1).intValue();
                            } else if (reps == Rep.MODIF) {
                                values[idx++] = (int) repModif[dataset.getData().get(i).get(j - 1).intValue()];
                            } else {
                                //values[idx++] 
                            }
                        }
                    }

                    float o = function.doFunction(values, weight);
                    float t = 0;
                    if (reps == Rep.INDEX) {
                        t = dataset.getData().get(i).get(classIdx).intValue();
                    } else if (reps == Rep.MODIF) {
                        t = repModif[dataset.getData().get(i).get(classIdx).intValue()];
                    } else {
                        //t 
                    }
                    // Bias
                    dw[0] = learningRate * (t - o) * 1;
                    idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            float val = 0;
                            if (reps == Rep.INDEX) {
                                val = dataset.getData().get(i).get(j - 1).intValue();
                            } else if (reps == Rep.MODIF) {
                                val = repModif[dataset.getData().get(i).get(j - 1).intValue()];
                            } else {
                                //t 
                            }
                            
                            dw[idx] = learningRate * (t - o) * val;
                        }
                    }
                    for (int j = 0; j < values.length; j++) {
                        System.out.print(values[j] + "\t|");
                    }
                    for (int j = 0; j < weight.length; j++) {
                        System.out.print("w" + j +"=" + weight[j] + "\t|");
                    }
                    for (int j = 0; j < dw.length; j++) {
                        System.out.print("dw" + j +"=" + dw[j] + "\t|");
                    }
                    System.out.print("o = " + o + "\t|");
                    System.out.println("t = " + t + "\t|");
                }
                iter++;
                calculateMSE();

                System.out.println("MSE = " + MSE);
            }
        }
        System.out.println("Accuracy : " + (accuration(dataset.getData()) * 100) + "%");
    }

    @Override
    public void GenerateModelBySplit(int splitPercent) {
        ArrayList<ArrayList<Integer>> trainSet;
        ArrayList<ArrayList<Integer>> testSet;
        int trainSize = this.dataset.getData().size() * splitPercent / 100;

        trainSet = new ArrayList<>(this.dataset.getData().subList(0, trainSize));
        testSet = new ArrayList<>(this.dataset.getData().subList(trainSize + 1, this.dataset.getData().size()));

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


                for (int i = 0; i < trainSet.size(); i++) {

                    int[] values = new int[trainSet.get(i).size()];
                    values[0] = 1;
                    int idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            if (reps == Rep.INDEX) {
                                values[idx++] = dataset.getData().get(i).get(j - 1).intValue();
                            } else if (reps == Rep.MODIF) {
                                values[idx++] = (int) repModif[dataset.getData().get(i).get(j - 1).intValue()];
                            } else {
                                //values[idx++] 
                            }
                        }
                    }

                    float o = function.doFunction(values, weight);
                    float t = 0;
                    if (reps == Rep.INDEX) {
                        t = dataset.getData().get(i).get(classIdx).intValue();
                    } else if (reps == Rep.MODIF) {
                        t = repModif[dataset.getData().get(i).get(classIdx).intValue()];
                    } else {
                        //t 
                    }
                    // Bias
                    dw[0] = dw[0] + learningRate * (t - o) * 1;
                    idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            dw[idx] = dw[idx] + learningRate * (t - o) * (trainSet.get(i).get(j - 1).intValue() == 0 ? -1 : 1);
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
        } else if (mode == Mode.INCREMENTAL) {
            int iter = 0;
            for (int i = 0; i < dw.length; i++) {
                dw[i] = 0.0f;
            }
            for (int i = 0; i < weight.length; i++) {
                weight[i] = 0.5f;
            }
            while (iter < maxIteration && MSE > minMSE) {
                for (int i = 0; i < trainSet.size(); i++) {
                    for (int j = 0; j < weight.length; j++) {
                        weight[j] += dw[j];
                    }
                    int[] values = new int[trainSet.get(i).size()];
                    values[0] = 1;
                    int idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            if (reps == Rep.INDEX) {
                                values[idx++] = dataset.getData().get(i).get(j - 1).intValue();
                            } else if (reps == Rep.MODIF) {
                                values[idx++] = (int) repModif[dataset.getData().get(i).get(j - 1).intValue()];
                            } else {
                                //values[idx++] 
                            }
                        }
                    }

                    float o = function.doFunction(values, weight);
                    float t = 0;
                    if (reps == Rep.INDEX) {
                        t = dataset.getData().get(i).get(classIdx).intValue();
                    } else if (reps == Rep.MODIF) {
                        t = repModif[dataset.getData().get(i).get(classIdx).intValue()];
                    } else {
                        //t 
                    }
                    // Bias
                    dw[0] = learningRate * (t - o) * 1;
                    idx = 1;
                    for (int j = 1; j <= values.length; j++) {
                        if ((j - 1) != classIdx) {
                            dw[idx] = learningRate * (t - o) * (trainSet.get(i).get(j - 1).intValue() == 0 ? -1 : 1);
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
        System.out.println("Accuracy : " + (accuration(testSet) * 100) + "%");
    }

    @Override
    public void GenerateModelByKFold(int k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Classify(Dataset trainData) {
        for (int i = 0; i < trainData.getData().size(); i++) {
            int[] values = new int[trainData.getData().get(i).size()];
            values[0] = 1;
            int idx = 1;
            for (int j = 1; j <= values.length; j++) {
                if ((j - 1) != classIdx) {
                    if (reps == Rep.INDEX) {
                        values[idx++] = dataset.getData().get(i).get(j - 1).intValue();
                    } else if (reps == Rep.MODIF) {
                        values[idx++] = (int) repModif[dataset.getData().get(i).get(j - 1).intValue()];
                    } else {
                        //values[idx++] 
                    }
                }
            }

            float o = function.doFunction(values, weight);
            int output = (int) Math.round(o);
            output = output == -1 ? 0 : 1;
            trainData.getRecord(i).add(classIdx, output);
        }
    }

    @Override
    public float accuration(ArrayList<ArrayList<Integer>> data) {
        int countSame = 0;
        for (int i = 0; i < data.size(); i++) {
            int[] values = new int[data.get(i).size()];
            values[0] = 1;
            int idx = 1;
            for (int j = 1; j <= values.length; j++) {
                if ((j - 1) != classIdx) {
                    if (reps == Rep.INDEX) {
                        values[idx++] = dataset.getData().get(i).get(j - 1).intValue();
                    } else if (reps == Rep.MODIF) {
                        values[idx++] = (int) repModif[dataset.getData().get(i).get(j - 1).intValue()];
                    } else {
                        //values[idx++] 
                    }
                }
            }

            float o = function.doFunction(values, weight);
            int output = (int) Math.round(o);
            output = output == -1 ? 0 : 1;

            if (output == data.get(i).get(classIdx).intValue()) {
                countSame++;
            }
        }
        return countSame / (float) data.size();
    }

    @Override
    public int getClsIdx() {
        return classIdx;
    }

    @Override
    public void setClsIdx(int idx) {
        classIdx = idx;
    }

    public void setReps(Rep reps) {
        this.reps = reps;
    }

    public void setReps(Rep reps, float[] repModif) {
        this.reps = reps;
        this.repModif = repModif;
    }
}
