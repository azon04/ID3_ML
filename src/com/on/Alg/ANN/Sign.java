/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.on.Alg.ANN;

/**
 *
 * @author Nurul Fithria
 */
public class Sign implements ActivationFunction {

    @Override
    public float doFunction(int[] vector, float[] w) {
        float res = 0.0f;
        int val;
        for(int i=0; i< vector.length; i++) {
             res += (vector[i]*w[i]);
        }
            
        if (res > 0) {
            val = 1;
        } else {
            val = -1;
        }
        return val;
    }

    @Override
    public float doFunction(float[] vector, float[] w) {
        float res = 0.0f;
        int val;
        for(int i=0; i< vector.length; i++) {
             res += (vector[i]*w[i]);
        }
            
        if (res > 0) {
            val = 1;
        } else {
            val = -1;
        }
        return val;
    }
    
}
