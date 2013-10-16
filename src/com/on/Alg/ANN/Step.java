/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.on.Alg.ANN;

/**
 *
 * @author Nurul Fithria
 */
public class Step implements ActivationFunction {
    
    float threshold = 0.0f;
    
    public Step(float threshold) {
        this.threshold = threshold;
    }
    
    //@Override
    public float doFunction(int[] vector, float[] w, float threshold) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        float res = 0.0f;
        int val;
        for(int i=0; i< vector.length; i++) {
             res += (vector[i]*w[i]);
        }
            
        if (res >= threshold) {
            val = 1;
        } else {
            val = 0;
        }
        return val;
    }

    //@Override
    public float doFunction(float[] vector, float[] w, float threshold) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        float res = 0.0f;
        int val;
        for(int i=0; i< vector.length; i++) {
             res += (vector[i]*w[i]);
        }
            
        if (res >= threshold) {
            val = 1;
        } else {
            val = 0;
        }
        return val;
    }

    @Override
    public float doFunction(int[] vector, float[] w) {
        return doFunction(vector, w, threshold);
    }

    @Override
    public float doFunction(float[] vector, float[] w) {
        return doFunction(vector, w, threshold);
    }
    
}
