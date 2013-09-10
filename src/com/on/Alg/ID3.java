package com.on.Alg;


import java.util.ArrayList;
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
    
    public ID3(Dataset _dataset) {
        classIdx = _dataset.getAttributes().size()-1;
        dataset = _dataset;
    }
    
    public void GenerateModel() {
        ArrayList<Integer> atributes = new ArrayList<>();
        for(int i=0; i<dataset.getAttributes().size(); i++) {
            if(i==classIdx) continue;
            atributes.add(i);
        }
        root = OnID3(this.dataset.getData(), atributes);
        root.PrintTree();
    }
    
    private TreeMap<Integer, DataGainAtribute> OnID3(ArrayList<ArrayList<Integer>> samples, ArrayList<Integer> attributes) {
        TreeMap<Integer, DataGainAtribute> node = null;
        node = new TreeMap<Integer, DataGainAtribute>();
        node.data = new DataGainAtribute();
        for(int i=0; i<dataset.getAttributes().get(classIdx).getValues().size(); i++) {
            if(IsAll(samples, classIdx, i)) {
                node.data.label = i;
                return node;
            }
        }
        if(attributes.isEmpty()) {
            node.data.label = mostCommonValueOfClass(samples);
        } else {
            int bestAtribIdx = SelectAtribute(samples,attributes);
            node.data.attr_idx = bestAtribIdx;
            for(int value =0
                    ; value <dataset.getAttributes().get(bestAtribIdx).getValues().size()
                    ; value++) {
                ArrayList<ArrayList<Integer>> sample_vi = selectWhere(samples, bestAtribIdx, value);
                if(sample_vi.isEmpty()) {
                    TreeMap<Integer, DataGainAtribute> newNode = new TreeMap<>();
                    newNode.data = new DataGainAtribute();
                    newNode.data.label = mostCommonValueOfClass(samples);
                    node.AddChildren(value, newNode);
                } else {
                    attributes.remove(new Integer(bestAtribIdx));
                    
                    TreeMap<Integer, DataGainAtribute> newNode = 
                            OnID3(sample_vi, attributes);
                    attributes.add(bestAtribIdx);
                    node.AddChildren(value, newNode);
                }
            }
        }
        return node;
    }
    
    private ArrayList<ArrayList<Integer>> selectWhere(ArrayList<ArrayList<Integer>> sample, int atribute, int value) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for(int i=0; i<sample.size(); i++) {
            if(sample.get(i).get(atribute).intValue() == value) {
                res.add(sample.get(i));
            }
        }
        return res;
    }
    private int mostCommonValueOfClass(ArrayList<ArrayList<Integer>> samples) {
        int[] classValueCount 
                = new int[dataset.getAttributes().get(classIdx).getValues().size()];
        for(int j=0; j<classValueCount.length; j++) {
            classValueCount[j] = 0;
        }
        
        for(int i =0; i<samples.size(); i++) {
            classValueCount[samples.get(i).get(classIdx)]++;
        }
        
        int idx =0;
        int maxCount = classValueCount[0];
        for(int i=1; i<classValueCount.length; i++) {
            if(maxCount < classValueCount[i]) {
                maxCount = classValueCount[i];
                idx = i;
            }
        }
        
        return idx;
    }
    
    private boolean IsAll(ArrayList<ArrayList<Integer>> samples, int atribute, int value) {
        for(int i=0; i<samples.size(); i++) {
            if(samples.get(i).get(atribute).intValue() != value) {
                return false;
            }
        }
        return true;
    }
    
    private int SelectAtribute(ArrayList<ArrayList<Integer>> data) {
        int idx = 0;
        float maxGain = 0.0f;
        for(int i=0; i<dataset.getAttributes().size(); i++) {
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
    
    private int SelectAtribute(ArrayList<ArrayList<Integer>> data, ArrayList<Integer> atributes) {
        int idx = atributes.get(0).intValue();
        float maxGain = Gain(data, atributes.get(0).intValue());
        for(int i=1; i<atributes.size(); i++) {
            float gain = Gain(data, atributes.get(i).intValue());
            if(maxGain < gain) {
                idx = atributes.get(i).intValue();
                maxGain = gain;
            }
        }
        return idx;
    }
    
    private float Entropy(ArrayList<ArrayList<Integer>>  data) {
        int[] classValueCount 
                = new int[dataset.getAttributes().get(classIdx).getValues().size()];
        for(int j=0; j<classValueCount.length; j++) {
            classValueCount[j] = 0;
        }
        
        for(int i =0; i<data.size(); i++) {
            classValueCount[data.get(i).get(classIdx)]++;
        }
        
        float entropy = 0;
        for(int j=0; j<classValueCount.length; j++) {
            entropy += -classValueCount[j]/(float)data.size() * log2(classValueCount[j]/(float)data.size());
        }
        
        return entropy;
    }
    
    private double log2(double number) {
        return Math.log(number)/Math.log(2);
    }
    
    private float Entropy(ArrayList<ArrayList<Integer>>  data, int attribute, int atribute_value) {
        int[] classValueCount 
                = new int[dataset.getAttributes().get(classIdx).getValues().size()];
        int dataCount = 0;
        for(int j=0; j<classValueCount.length; j++) {
            classValueCount[j] = 0;
        }
        
        for(int i =0; i<data.size(); i++) {
            if(data.get(i).get(attribute).intValue() ==atribute_value) {
                classValueCount[data.get(i).get(classIdx)]++;
                dataCount++;
            }
        }
        
        float entropy = 0;
        for(int j=0; j<classValueCount.length; j++) {
            if(classValueCount[j] == 0) continue;
            entropy += -classValueCount[j]/(float)dataCount * log2(classValueCount[j]/(float)dataCount);
        }
        entropy *= dataCount/(float)data.size();
        return entropy;
    }
    
    private float Gain(ArrayList<ArrayList<Integer>>  data, int attribute) {
        float gain;
        gain = Entropy(data);
        int numValue = dataset.getAttributes().get(attribute).getValues().size();
        for(int i=0; i<numValue; i++) {
            gain -= Entropy(data, attribute, i);
        }
        return gain;
    }
    
    class DataGainAtribute {
        int attr_idx;
        int[] attr_value_count;
        float gain;
        int label = -1;

        @Override
        public String toString() {
            String s = "atribute idx : " + attr_idx;
            if(label >= 0) s = "label : " + label;
            return s; //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public void Classify(Dataset trainData) {
        if(root == null) return;
        for(int i=0; i<trainData.getData().size();i++) {
        TreeMap<Integer,DataGainAtribute> node = root;
            while(node.data.label < 0) {
               node = node.children.get(trainData.getRecord(i).get(node.data.attr_idx));
            }
            trainData.getRecord(i).add(classIdx, node.data.label);
        }
    }
    
    public void Classify(int[][] trainData) {
        
    }
    
}
