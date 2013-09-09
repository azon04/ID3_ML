package com.on.Alg;


import java.util.Dictionary;
import onid3.Dataset;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ahmad Fauzan
 */
public class ID3 {
    TreeMap<Integer, DataGainAtribute> root = null;
    Dataset dataset; 
    int classIdx = 0;
    
    public ID3() {
        
    }
    
    public void GenerateModel() {
        
    }
    
    private int SelectAtribute(int[][] data) {
        int idx = 0;
        float maxGain = 0.0f;
        for(int i=0; i<data.length; i++) {
            if(i==classIdx)
                continue;
            float gain = Gain(data, i);
            if(maxGain < gain) {
                idx = i;
                maxGain = gain;
            }
        }
        return idx;
    }
    
    private float Entropy(int[][] data) {
        int[] classValueCount 
                = new int[dataset.getAttributes().get(classIdx).getValues().size()];
        for(int j=0; j<classValueCount.length; j++) {
            classValueCount[j] = 0;
        }
        
        for(int i =0; i<data.length; i++) {
            classValueCount[data[i][classIdx]]++;
        }
        
        float entropy = 0;
        for(int j=0; j<classValueCount.length; j++) {
            entropy += -classValueCount[j]/(float)data.length * Math.log(classValueCount[j]/(float)data.length);
        }
        
        return entropy;
    }
    
    private float Entropy(int[][] data, int attribute, int atribute_value) {
        int[] classValueCount 
                = new int[dataset.getAttributes().get(classIdx).getValues().size()];
        int dataCount = 0;
        for(int j=0; j<classValueCount.length; j++) {
            classValueCount[j] = 0;
        }
        
        for(int i =0; i<data.length; i++) {
            if(data[i][attribute]==atribute_value) {
                classValueCount[data[i][classIdx]]++;
                dataCount++;
            }
        }
        
        float entropy = 0;
        for(int j=0; j<classValueCount.length; j++) {
            entropy += -classValueCount[j]/(float)dataCount * Math.log(classValueCount[j]/(float)dataCount);
        }
       
        return entropy;
    }
    
    private float Gain(int[][] data, int attribute) {
        float gain = 0.0f;
        gain = Entropy(data);
        int numValue = dataset.getAttributes().get(attribute).getValues().size();
        for(int i=0; i<numValue; i++) {
            gain += Entropy(data, attribute, i);
        }
        return gain;
    }
    
    class DataGainAtribute {
        int attr_idx;
        int[] attr_value_count;
        float gain;
        int label;
    }
    
    public void Classify(Dataset trainData) {
        if(root == null) return;
        TreeMap<Integer,DataGainAtribute> node = root;
        while(node.data.label < 0) {
           // node = node.children.get(trainData.getValues().get(node.data.attr_idx));
        }
        //trainData.getValues().add(classIdx, node.data.label);
    }
    
    public void Classify(int[][] trainData) {
        
    }
    
}
