/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.javafem.beans;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import net.tedkwan.javafemjni.TwoDimPhase;
import org.jblas.DoubleMatrix;
import org.primefaces.context.RequestContext;

/**
 *
 * @author devils
 */
@ManagedBean(name = "phase3Bean")
@RequestScoped
public class Phase3Bean {
    
    public Phase3Bean(){
        makeLorenz();
        System.out.println("Made phase 3");
    }

    private String xarpp = "";
    private String yarpp = "";
    private String xarppl = "";
    private String yarppl = "";
    private String uarpp = "";

    private void makeLorenz() {
        //System.out.println(System.getProperty("java.library.path"));
        //DuffingOscil duff = new DuffingOscil();
        TwoDimPhase phase = new TwoDimPhase();
        double[] initc = {0.2, (8.0 / 3.0), 28.0, 10.0, 0.0, 20.0, 1.3, 1.6, 10.6};
        double[] res = phase.rk4(initc, "lorenz");
        int r = res.length / 4;
        System.out.println(res.length);
        DoubleMatrix outmtx = new DoubleMatrix(r, 4, res);
        DoubleMatrix xpp = outmtx.getColumn(0);
        DoubleMatrix ypp = outmtx.getColumn(1);
        DoubleMatrix upp = outmtx.getColumn(2);
        xarppl = convertMatrix((xpp));
        yarppl = convertMatrix((ypp));
        uarpp = convertMatrix(upp);
    }

    private void makeDuffing() {
        //System.out.println(System.getProperty("java.library.path"));
        TwoDimPhase phase = new TwoDimPhase();
//        String odename=odefun.getName();
        double[] initc = {0.05, 0.8,
            1.0, 0.3, -1.0, 20.0, 0.7, 0.6};
//        if(odename.contains("uffi")){
//            double [] initcduff= {0.05, odefun.getA().doubleValue(),
//                odefun.getB().doubleValue(),
//                odefun.getC().doubleValue(),odefun.getD().doubleValue()
//                    ,20.0,odefun.getX0().doubleValue(),
//                    odefun.getY0().doubleValue()};
//            initc=initcduff;
//        }else{
//            double [] initcvan= {0.05, odefun.getA().doubleValue(),
//            1.0, 0.3,-1.0,20.0,odefun.getX0().doubleValue(),odefun.getY0().doubleValue()};
//            initc=initcvan;
//        }
        double[] res = phase.rk4(initc, "duffing");
        int r = res.length / 3;
        //System.out.println(res.length);
        DoubleMatrix outmtx = new DoubleMatrix(r, 3, res);
        DoubleMatrix xpp = outmtx.getColumn(0);
        DoubleMatrix ypp = outmtx.getColumn(1);
        xarpp = convertMatrix((xpp));
        yarpp = convertMatrix((ypp));
        System.out.println("Done.");
    }
    public void showPP3d() {
        makeLorenz();
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", true);
        options.put("draggable", true);
        options.put("modal", true);
        //makeDuffing(odefun);
        RequestContext context=RequestContext.getCurrentInstance();
        context.openDialog("showPP3d", options, null);
    }

    /**
     * ConverMatrix function.
     *
     * This function creates a string JSON representation of a DoubleMatrix. It
     * ensures that the output string is able to be read by plotly in
     * javascript.
     *
     *
     * @param conv String to convert.
     * @return String representation of DoubleMatrix in JSON array.
     */
    private String convertMatrix(DoubleMatrix conv) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < conv.length - 1; i++) {
            sb.append(String.format("%.6f", conv.get(i)));
            sb.append(",");
        }
        sb.append(String.format("%.6f", conv.get(conv.length - 1)));
        sb.append("]");
        return sb.toString();
    }

    public String getXarpp() {
        return xarpp;
    }

    public String getYarpp() {
        return yarpp;
    }

    public String getXarppl() {
        return xarppl;
    }

    public String getYarppl() {
        return yarppl;
    }

    public String getUarpp() {
        return uarpp;
    }
    
    
}
