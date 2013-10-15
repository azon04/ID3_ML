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
public class ANNMultiLayer implements Classifier {
    
    float w[][][]; // layer-sel-weight
    private ActivationFunction function;
    private int maxIteration = 10;
    private float learningRate = 0.1f;
    private float minMSE = 0f;
    private int classIdx = 0;
    private float MSE = 1000.0f;
    private Dataset dataset;
    
    public ANNMultiLayer(Dataset _dataset, int[] selsConfiguration) {
        w = new float[selsConfiguration.length][][];
        w[0] = new float[selsConfiguration[0]][];
        for(int i=0; i<w[0].length; i++) {
            w[0][i] = new float[_dataset.getAttributes().size()];
        }
        for(int i=1; i<selsConfiguration.length; i++) {
            for(int j=0; j<selsConfiguration[i]; j++) {
                w[i] = new float[selsConfiguration[i]][w[i-1].length+1];
            }
        }
        
        function = new Linear();
        classIdx = _dataset.getData().size() - 2;
        this.dataset = _dataset;
    }
    
    private void calculateMSE() {
        float sum = 0.0f;
        for (int i = 0; i < dataset.getData().size(); i++) {
            float[][] output = new float[w.length][];
            for(int j=0; j<output.length; j++) {
                output[j] = new float[w[j].length];
            } 
            
            int[] values = new int[dataset.getData().get(i).size()];
            values[0] = 1;
            int idx = 1;
            for (int j = 1; j <= values.length; j++) {
                if ((j - 1) != classIdx) {
                    values[idx++] = dataset.getData().get(i).get(j - 1).intValue();
                }
            }
            for(int j=0; j<w[0].length; j++) {
                output[0][j] = function.doFunction(values, w[0][j]);
            }
            
            for(int j=1; j<w.length; j++) {
                for(int k = 0; k<w[j].length; k++) {
                    float[] vector = new float[output[j-1].length+1];
                    vector[0] = 1;
                    for(int iter = 1; iter <output[j-1].length+1; iter++)
                        vector[iter] = output[j-1][iter-1];
                    output[j][k] = function.doFunction(vector, w[j][k]);
                }
            }
            
            float t = dataset.getData().get(i).get(classIdx).intValue();

            sum += (t - output[w.length-1][0]) * (t - output[w.length-1][0]);
        }

        MSE = sum / dataset.getData().size();
    }
    
    @Override
    public void GenerateModel() {
        int iter = 0;
        float[][] delta = new float[w.length][];
        for(int i=0; i<w.length; i++) {
            delta[i] = new float[w[i].length];
        }
        
        for(int i=0; i<w.length; i++) {
                for(int j=0; j<w[i].length; j++) {
                    for(int k=0; k<w[i][j].length; k++) {
                        w[i][j][k] = 0.05f;
                    }
                }
            }
        
        while(iter < maxIteration && MSE > minMSE) {
            System.out.println("Iterasi : " + (iter+1));
            for (int i = 0; i < dataset.getData().size(); i++) {
                float[][] output = new float[w.length][];
                for(int j=0; j<output.length; j++) {
                    output[j] = new float[w[j].length];
                } 

                int[] values = new int[dataset.getData().get(i).size()];
                values[0] = 1;
                int idx = 1;
                for (int j = 1; j <= values.length; j++) {
                    if ((j - 1) != classIdx) {
                        values[idx++] = dataset.getData().get(i).get(j - 1).intValue();
                     }
                }
                for(int j=0; j<w[0].length; j++) {
                    output[0][j] = function.doFunction(values, w[0][j]);
                }

                for(int j=1; j<w.length; j++) {
                    for(int k = 0; k<w[j].length; k++) {
                        float[] vector = new float[output[j-1].length+1];
                        vector[0] = 1;
                        for(int iter1 = 1; iter1 <output[j-1].length+1; iter1++)
                            vector[iter1] = output[j-1][iter1-1];
                        output[j][k] = function.doFunction(vector, w[j][k]);
                    }
                }

                float t = dataset.getData().get(i).get(classIdx).intValue();
                
                for(int j=0; j < w[w.length-1].length; j++) {
                    delta[w.length-1][j] = output[w.length-1][j]* (1-output[w.length-1][j]) * (t - output[w.length-1][j]);
                }
                
                for(int j=w.length-1; j >= 0; j--) {
                    for(int k=0; k<w[j].length-1; k++) {
                        float sum = 0;
                        for(int l=0; l<w[j+1].length; l++) {
                            sum += w[j+1][l][k+1]*delta[j+1][l];
                        }
                        delta[j][k] = output[j][k]*(1-output[j][k])*sum;
                    }
                }
                
                for(int iter1 = 0; iter1 < w[0].length; iter1++) {
                    w[0][iter1][0] = w[0][iter1][0] + learningRate*delta[0][iter1]*1;
                    for(int iter2=1; iter2<w[0][iter1].length; iter2++) {
                        w[0][iter1][iter2] = w[0][iter1][iter2] + learningRate*delta[0][iter1]*values[iter2];
                    }
                }
                
                for(int iter0 = 1; iter0<w.length; iter0++) {
                    for(int iter1 = 0; iter1 < w[iter0].length; iter1++) {
                        w[iter0][iter1][0] = w[iter0][iter1][0] + learningRate*delta[0][iter1]*1;
                        for(int iter2=1; iter2<w[iter0][iter1].length; iter2++) {
                            w[iter0][iter1][iter2] = w[iter0][iter1][iter2] + learningRate*delta[0][iter1]*output[iter0-1][iter2-1];
                        }
                    }
                }
                
            }
            calculateMSE();
            iter++;
            
            for(int i=0; i<w.length; i++) {
                for(int j=0; j<w[i].length; j++) {
                    System.out.print("sel<"+(i+1)+","+(j+1)+">:");
                    for(int k=0; k<w[i][j].length; k++) {
                        System.out.print("w<"+(j+1)+","+(k+1)+">="+w[i][j][k]+"|");
                    }
                    System.out.println("MSE =" + MSE);
                }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setClsIdx(int idx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
