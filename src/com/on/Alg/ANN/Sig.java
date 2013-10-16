/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.on.Alg.ANN;
/**
 *
 * @author Nurul Fithria
 */
public class Sig implements ActivationFunction {

    @Override
    public float doFunction(int[] vector, float[] w) {
        float res = 0.0f;
        float val;
        for(int i=0; i< vector.length; i++) {
             res += (vector[i]*w[i]);
        }
            
        val = (float)(1/(1+Math.pow(Math.E, -(res))));
        return val;
    }

    @Override
    public float doFunction(float[] vector, float[] w) {
        float res = 0.0f;
        float val;
        for(int i=0; i< vector.length; i++) {
             res += (vector[i]*w[i]);
        }
            
        val = (float)(1/(1+Math.pow(Math.E, -(res))));
        return val;
    }
    
}
