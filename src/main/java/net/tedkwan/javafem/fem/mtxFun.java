/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.javafem.fem;

import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

/**
 *
 * @author Devils
 */
public class mtxFun {
    
    
    public mtxFun(){
        
    }
    
    
    /**
     * This is the overloaded method which computes the matrix function for the
     * right hand side.
     * 
     * @param x input vector (current location).
     * @return output vector representing right hand side at x.
     */
    public DoubleMatrix compute(DoubleMatrix x){
        double pi=Math.PI;
        DoubleMatrix y=(MatrixFunctions.sin(x.getColumn(0).mul(pi).mul(2.0)))
                      .mul(MatrixFunctions.cos(x.getColumn(1).mul(pi).mul(2.0)))
                      .mul(8.0*Math.pow(pi,2.0));
        return y;
    }
}
