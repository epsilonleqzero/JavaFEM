/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.javafem.beans;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import net.tedkwan.javafem.fem.FEM;
import net.tedkwan.javafem.fem.mtxFun;
import net.tedkwan.javafem.fem.mtxFunBd;
import net.tedkwan.javafemjni.TwoDimPhase;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Devils
 */

@ManagedBean(name = "femSessionBean")
@SessionScoped
public class femSessionBean implements Serializable {

    private double x1=0.0;
    private double x2=1.0;
    private double y1=0.0;
    private double y2=1.0;
    private DoubleMatrix u;
    private DoubleMatrix x;
    private DoubleMatrix y;
    private DoubleMatrix upp;
    private String xar="";
    private String yar="";
    private String uar="";
    private String xarpp="";
    private String yarpp="";
    private String uarpp="";
    private double h=0.125;
    private double hpp=0.05;
    /**
     * Creates a new instance of femSessionBean
     */
    public femSessionBean() {
        calculateFEM();
        makeDuffing();
    }
    
    
    /**
     * Make a 2 dim phase plane
     * 
     */
    private void makeDuffing(){
        //System.out.println(System.getProperty("java.library.path"));
        TwoDimPhase phase=new TwoDimPhase();
        double[] initc = {0.05, 0.1, 1, 0.3,-1.0,30.0,0.3,0.6};
        double[] res = phase.rk4(initc,"duffing");
        int r = res.length / 3;
        System.out.println(res.length);
        DoubleMatrix outmtx = new DoubleMatrix(r, 3, res);
        DoubleMatrix xpp = outmtx.getColumn(0);
        DoubleMatrix ypp = outmtx.getColumn(1);
        xarpp=convertMatrix((xpp));
        yarpp=convertMatrix((ypp));
        System.out.println("Done.");
    }
    
    private void makeLorenz(){
        System.out.println(System.getProperty("java.library.path"));
        //DuffingOscil duff = new DuffingOscil();
        TwoDimPhase phase=new TwoDimPhase();
        double[] initc = {0.1, 0.1, 1, 0.3,-1.0,30.0,0.3,0.6};
        double[] res = phase.rk4(initc,"lorenz");
        int r = res.length / 4;
        System.out.println(res.length);
        DoubleMatrix outmtx = new DoubleMatrix(r, 4, res);
        DoubleMatrix xpp = outmtx.getColumn(0);
        DoubleMatrix ypp = outmtx.getColumn(1);
        upp = outmtx.getColumn(2);
        xarpp=convertMatrix((xpp));
        yarpp=convertMatrix((ypp));
        uarpp=convertMatrix(upp);
        
    }
    
    public void showPP() {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", true);
        options.put("draggable", true);
        options.put("modal", true);
        makeDuffing();
        RequestContext context=RequestContext.getCurrentInstance();
        context.openDialog("showPP", options, null);
    }
    
    private void calculateFEM(){
        mtxFun f=new mtxFun();
        mtxFunBd g=new mtxFunBd();
        long startt=System.nanoTime();
        FEM fem=new FEM(x1,x2,y1,y2,h,f,g);
        long endt=System.nanoTime();
        long totalt=endt-startt;
        double fullt= (double) totalt / 1000000000.0;
        System.out.println("Elapsed time is " + fullt +" seconds");
        u=fem.getU();
        DoubleMatrix nodes=fem.getNodes();
        x=nodes.getColumn(0);
        y=nodes.getColumn(1);
        xar=convertMatrix(MatrixFunctions.abs(x));
        yar=convertMatrix(MatrixFunctions.abs(y));
        uar=convertMatrix(u);
        
    }
    
    /**
     * ConverMatrix function.
     * 
     * This function creates a string JSON representation of 
     * a DoubleMatrix. It ensures that the output string is 
     * able to be read by plotly in javascript.
     * 
     * 
     * @param conv String to convert.
     * @return String representation of DoubleMatrix in JSON array.
     */
    private String convertMatrix(DoubleMatrix conv){
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for(int i=0;i<conv.length-1;i++){
            sb.append(String.format("%.6f",conv.get(i)));
            sb.append(",");
        }
        sb.append(String.format("%.6f",conv.get(conv.length-1)));
        sb.append("]");
        //System.out.println(sb.toString());
        return sb.toString();
    }
    
    public void showPlot() {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", true);
        options.put("draggable", true);
        options.put("modal", true);
        calculateFEM();
        RequestContext context=RequestContext.getCurrentInstance();
        context.openDialog("showPlot", options, null);
    }
    /**
     * onClose function.
     * 
     * Closes the plot dialog window after viewing the file.
     * 
     */
    public void onClose(){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Plot Closed", "You have closed the plot");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public DoubleMatrix getUpp() {
        return upp;
    }

    public void setUpp(DoubleMatrix upp) {
        this.upp = upp;
    }

    public String getXarpp() {
        return xarpp;
    }

    public void setXarpp(String xarpp) {
        this.xarpp = xarpp;
    }

    public String getYarpp() {
        return yarpp;
    }

    public void setYarpp(String yarpp) {
        this.yarpp = yarpp;
    }

    public String getUarpp() {
        return uarpp;
    }

    public void setUarpp(String uarpp) {
        this.uarpp = uarpp;
    }

    public double getHpp() {
        return hpp;
    }

    public void setHpp(double hpp) {
        this.hpp = hpp;
    }
    

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public DoubleMatrix getU() {
        return u;
    }

    public DoubleMatrix getX() {
        return x;
    }

    public DoubleMatrix getY() {
        return y;
    }

    public String getXar() {
        return xar;
    }

    public String getYar() {
        return yar;
    }

    public String getUar() {
        return uar;
    }
    
}
