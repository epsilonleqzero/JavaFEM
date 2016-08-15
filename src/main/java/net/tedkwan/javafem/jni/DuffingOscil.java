package net.tedkwan.javafem.jni;

/**
 * 
 * @deprecated This package was replaced by a separate library to keep thread
 * safety.
 *
 * @author Ted Kwan 
 * 
 */
@Deprecated
public class DuffingOscil {
    
    static {
        System.loadLibrary("rk45c");
    }

    /**
     * Pars has 5 fields:
     * 
     * gamma - index 0.
     * h - index 1.
     * tf - index 2.
     * x0 - index 3.
     * y0 - index 4.
     * 
     * 
     * @param pars input array.
     * @return double array with all values.
     */
    public native double[] rk4(double[] pars);
    
    public DuffingOscil(){
        
    }
    
}
