
package net.tedkwan.javafem.beans;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.jblas.DoubleMatrix;

/**
 * Managed Bean for the site.
 *
 *
 * This managed bean is the bean which controls the main site operation.
 * Currently it is unused, but when more functionality is added, it will
 * be needed.
 *
 * @author Ted Kwan
 */
@ManagedBean(name = "femSessionBean")
@SessionScoped
public class femSessionBean implements Serializable {
    

    private double x1=0.0;
    private double x2=1.0;
    private double y1=0.0;
    private double y2=1.0;
    private String xar="";
    private String yar="";
    private String uar="";
    private double h=0.1;
    private double hpp=0.125;
    
    /**
     * Creates a new instance of femSessionBean
     */
    public femSessionBean() {
        
    }
    
    /**
     * ConvertMatrix function.
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
        return sb.toString();
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
