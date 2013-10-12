/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.on.Alg.ANN;

/**
 *
 * @author Ahmad Fauzan
 */
public class Linear implements ActivationFunction{

    @Override
    public float doFunction(int[] vector, float[] w) {
        float res = 0.0f;
        for(int i=0; i< vector.length; i++) {
            res += (vector[i]*w[i]);
        }
        return res;
    }
    
}
