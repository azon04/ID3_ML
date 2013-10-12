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
public interface Classifier {
    
    public void GenerateModel();
    public void GenerateModelBySplit(int splitPercent);
    public void GenerateModelByKFold(int k);
    public void Classify(Dataset trainData);
    public float accuration(ArrayList<ArrayList<Integer>> data);
    public int getClsIdx();
    public void setClsIdx(int idx);
    
}
