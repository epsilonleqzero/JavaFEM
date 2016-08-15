/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.javafem.beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import net.tedkwan.javafem.entity.Ode3D;
import net.tedkwan.javafemjni.TwoDimPhase;
import org.jblas.DoubleMatrix;


/**
 * Managed Bean for 3D phase planes.
 *
 *
 * This managed bean is the bean which creates the list of 3D ODEs and then
 * applies the RK45 method in C++.
 *
 * @author Ted Kwan
 */
@ManagedBean(name = "phase3SelectionBean")
@ViewScoped
public class Phase3SelectionBean implements Serializable {
    
    Ode3D odefun;
    List<Ode3D> odelist;
    private String xarpp ="";
    private String yarpp ="";
    private String uarpp ="";
    
    @PersistenceContext(unitName = "net.tedkwan_JavaFEM_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    /**
     * Default Constructor, makes the first plot available.
     */
    public Phase3SelectionBean(){
        makePhase3First();
    }
    
     /**
     * Initialization routine.
     *
     * This sets up the ODE list and then allows for selection.
     */
    @PostConstruct
    public void init(){
        Query query = em.createNamedQuery("Ode3D.findAll");
        odelist =(List<Ode3D>) query.getResultList();
        odefun=odelist.get(0);
    }
    
    /**
     * Persist to save to mysql.
     *
     * Not implemented currently.
     *
     * @param object object to persist.
     */
    public void persist(Object object) {
        em.persist(object);
    }

    /**
     * Creation of 3D phase plane.
     *
     * This method creates the strings needed to plot the phase plane in 3D.
     * This is public so that it can be called by the refresh button.
     *
     */
    public void makePhase3() {
        // Initialize JNI connection.
        TwoDimPhase phase = new TwoDimPhase();
        String odename=odefun.getName();
        // Set parameters.
        double [] initc= {0.2, odefun.getA().doubleValue(),
                odefun.getB().doubleValue(),
                odefun.getC().doubleValue(),1.0
                    ,50.0,Math.random(),
                    Math.random(),Math.random()};
        // Run the JNI method for RK45.
        double[] res = phase.rk4(initc,odename);
        // Clean up output.
        int r = res.length / 4;
        DoubleMatrix outmtx = new DoubleMatrix(r, 4, res);
        DoubleMatrix xpp = outmtx.getColumn(0);
        DoubleMatrix ypp = outmtx.getColumn(1);
        DoubleMatrix upp = outmtx.getColumn(2);
        // Convert vectors to JSON arrays.
        xarpp = convertMatrix((xpp));
        yarpp = convertMatrix((ypp));
        uarpp = convertMatrix((upp));
        
    }
    
    /**
     * Construct initial phase plane.
     *
     * This method constructs the phase plane that is seen when the page loads.
     * It makes a Lorenz attractor.
     */
    private void makePhase3First() {
        // Initialize JNI Connection.
        TwoDimPhase phase = new TwoDimPhase();
        double[] initc = {0.2, 2.6667,
            28.0, 10.0, -1.0, 50.0, 1.0,1.0,1.0};
        // Call JNI method for RK45.
        double[] res = phase.rk4(initc,"Lorenz");
        // Clean up output.
        int r = res.length / 4;
        DoubleMatrix outmtx = new DoubleMatrix(r, 4, res);
        DoubleMatrix xpp = outmtx.getColumn(0);
        DoubleMatrix ypp = outmtx.getColumn(1);
        DoubleMatrix upp = outmtx.getColumn(2);
        // Convert vectors to JSON arrays.
        xarpp = convertMatrix((xpp));
        yarpp = convertMatrix((ypp));
        uarpp = convertMatrix((upp));
    }

    /**
     * ConvertMatrix function.
     *
     * This function creates a string JSON representation of a DoubleMatrix when
     * it is a vector. It ensures that the output string is able to be read by 
     * plotly in javascript.
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
    
    /**
     * ConvertMatrices function.
     *
     * This function creates a string JSON representation of a DoubleMatrix. It
     * ensures that the output string is able to be read by plotly in
     * javascript.
     *
     *
     * @param conv String to convert.
     * @return String representation of DoubleMatrix in JSON array.
     */
    private String convertMatrices(DoubleMatrix conv) {
        StringBuilder sb = new StringBuilder();
        String nextline=System.lineSeparator();
        
        for (int i = 0; i < conv.rows; i++) {
            sb.append("[");
            for(int j=0;j<conv.columns-1;j++){
                sb.append(String.format("%.6f", conv.get(i,j)));
                if(j<3){
                    sb.append(",");
                }
                else{
                    sb.append(",");
                    sb.append(String.format("%.6f", conv.get(i,j)));
                }
            }
            sb.append("],");
            if(i<conv.rows-1){
                sb.append(nextline);
            }
        }
        return sb.toString();
    }

    public String getXarpp() {
        return xarpp;
    }

    public String getYarpp() {
        return yarpp;
    }
    
    public String getUarpp() {
        return uarpp;
    }

    public Ode3D getOdefun() {
        return odefun;
    }

    public void setOdefun(Ode3D odefun) {
        this.odefun = odefun;
    }

    public List<Ode3D> getOdelist() {
        return odelist;
    }  
}
