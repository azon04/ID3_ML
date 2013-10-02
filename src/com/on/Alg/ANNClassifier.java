/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.on.Alg;

import java.util.ArrayList;
import onid3.Dataset;

/**
 *
 * @author Ahmad Fauzan
 */
public class ANNClassifier implements Classifier{

    @Override
    public void GenerateModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
