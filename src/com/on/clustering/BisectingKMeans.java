/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.on.clustering;

import java.util.ArrayList;
import java.util.List;
import onid3.Dataset;

/**
 *
 * @author Ahmad Fauzan
 */
public class BisectingKMeans {
    Dataset dataset;
    int nCluster;
    List<Cluster> result;
    
    public BisectingKMeans(int n, Dataset dataset) {
        nCluster = n;
        this.dataset = dataset;
        result = new ArrayList<Cluster>();
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
    
    public void doCluster() {
        result.clear();
        Cluster cInit = new Cluster(dataset.getData());
        result.add(cInit);
        
        while(result.size() < nCluster) {
            Cluster c = result.get(0);
            bisecting(c.getInstances());
            result.remove(c);
        }
    }
    
    private void bisecting(ArrayList<ArrayList<Integer>> data) {
        ArrayList<Integer> cL = data.get(0);
        ArrayList<Integer> w = calculateCentroid(data);
        ArrayList<Integer> cR = minus(w,minus(cL,w));
        
        Cluster mL = new Cluster(),mR = new Cluster();
        
        ArrayList<Integer> wL = null,wR = null;
        
        do {
            if(wL != null) {
                cL = wL;
                cR = wR;
            }
            for(int i=0 ; i<data.size(); i++) {
                float distanceLeft = distance(data.get(i), cL);
                float distanceRight = distance(data.get(i), cR);
                if(distanceLeft <= distanceRight) {
                    mL.addInstance(data.get(i));
                } else {
                    mR.addInstance(data.get(i));
                }
            }
            wL = calculateCentroid(mL.getInstances());
            wR = calculateCentroid(mR.getInstances());
        } while(!same(wL,cL) || same(wR,cR));
        
        result.add(mR);
        result.add(mL);
    }
    
    private float distance(ArrayList<Integer> data1, ArrayList<Integer> data2) {
        float res = 0.0f;
        for(int i=0; i<data1.size(); i++) {
            res += (data1.get(i).intValue()-data2.get(i).intValue())*(data1.get(i).intValue()-data2.get(i).intValue());
        }
        res = (float) Math.sqrt(res);
        return res;
    }
    
    private boolean same(ArrayList<Integer> data1, ArrayList<Integer> data2) {
        for(int i=0; i < data1.size(); i++) {
            if(data1.get(i).intValue() != data2.get(i).intValue()) {
                return false;
            }
        }
        return true;
    }
    private ArrayList<Integer> minus(ArrayList<Integer> data1, ArrayList<Integer> data2) {
        ArrayList<Integer> result = new ArrayList<>();
        for(int i=0; i < data1.size(); i++) {
            result.add(i, data1.get(i) - data2.get(i));
        }
        return result;
    }
    
    private ArrayList<Integer> calculateCentroid(ArrayList<ArrayList<Integer>> data) {
        float[] tmp = new float[data.get(0).size()];
        for(int i=0; i < tmp.length; i++) {
            tmp[i] = 0.0f;
        }
        
        for(int i=0; i < data.size(); i++) {
            for(int j=0; j<data.get(i).size(); j++) {
                tmp[j] += data.get(i).get(j).intValue();
            }
        }
        
        ArrayList<Integer> result = new ArrayList<>();
        for(int i=0; i < tmp.length; i++) {
            int means = (int) Math.round(tmp[i]/data.size());
            result.add(i,new Integer(means));
        }
        
        return result;
    }
    
    private class Cluster {
        String name;
        ArrayList<ArrayList<Integer>> instances;

        public Cluster() {
            instances = new ArrayList<>();
        }
        
        public Cluster(ArrayList<ArrayList<Integer>> instances) {
            this.instances = instances;
        }
        
        public void addInstance(ArrayList<Integer> instance) {
            instances.add(instance);
        }

        public ArrayList<ArrayList<Integer>> getInstances() {
            return instances;
        }
        
    }
}
