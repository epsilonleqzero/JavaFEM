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
public class mtxFunBd {
    
    
    public mtxFunBd(){
        
    }
    
    /**
     * Boundary Function.
     * 
     * @param x Input vector for the boundary.
     * @return u for the values of u at the boundary.
     */
    public DoubleMatrix compute(DoubleMatrix x){
        double pi=Math.PI;
        DoubleMatrix y=(MatrixFunctions.sin(x.getColumn(0).mul(pi).mul(2.0)))
                      .mul(MatrixFunctions.cos(x.getColumn(1).mul(pi).mul(2.0)));
        return y;
    }
}
