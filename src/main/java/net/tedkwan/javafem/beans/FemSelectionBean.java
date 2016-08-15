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
import net.tedkwan.javafem.entity.PdePoisson;
import net.tedkwan.javafemjni.FemPoisson;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;


/**
 * Managed Bean for the Finite Element Method.
 *
 *
 * This managed bean is the bean which creates the list of PDEs to be solved
 * using the finite element method in C++. It calls the JNI connector to have
 * C++ run the numerical computations.
 *
 * @author Ted Kwan
 */
@ManagedBean(name = "femSelectionBean")
@ViewScoped
public class FemSelectionBean implements Serializable {
    
    PdePoisson pdefun;
    List<PdePoisson> pdelist;
    private String xarpp ="";
    private String yarpp ="";
    private String uarpp ="";
    
    @PersistenceContext(unitName = "net.tedkwan_JavaFEM_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    
    /**
     * Default Constructor, makes the first plot available.
     */
    public FemSelectionBean(){
        calculateFEMfirst();
    }
    
    /**
     * Initialization routine.
     *
     * This sets up the PDE list and then allows for selection.
     */
    @PostConstruct
    public void init(){
        Query query = em.createNamedQuery("PdePoisson.findAll");
        pdelist =(List<PdePoisson>) query.getResultList();
        pdefun=pdelist.get(0);
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
     * Creation of PDE solution.
     *
     * This method solve the PDE and creates the strings needed to plot it using
     * plotly. This is public so that it can be called by the refresh button.
     *
     */
    public void calculateFEM(){
        // Initialize JNI connection.
        FemPoisson femp=new FemPoisson();
        double [] initc={pdefun.getX0().doubleValue(),
            pdefun.getX1().doubleValue(),pdefun.getY0().doubleValue()
                ,pdefun.getY1().doubleValue(),pdefun.getH().doubleValue()};
        // Time the method.
        long startt=System.nanoTime();
        // Run the finite element method from C++.
        double[] res = femp.cFEM(initc,pdefun.getName());
        long endt=System.nanoTime();
        long totalt=endt-startt;
        double fullt= (double) totalt / 1000000000.0;
        // Fix the output.
        int r=res.length/3;
        DoubleMatrix outmtx = new DoubleMatrix(r, 3, res);
        
        System.out.println("Elapsed time is " + fullt +" seconds");
        DoubleMatrix u=outmtx.getColumn(2);
        DoubleMatrix x=outmtx.getColumn(0);
        DoubleMatrix y=outmtx.getColumn(1);
        // Convert vectors to JSON.
        xarpp = convertMatrix((x));
        yarpp = convertMatrix((y));
        uarpp = convertMatrix(u);
    }
    
    /**
     * Construct initial PDE solution.
     *
     * This method constructs the finite element solution that is seen 
     * when the page loads.
     * 
     * It approximates u=sin(2pix)cos(2piy).
     * 
     */
    private void calculateFEMfirst(){
        // Initialize JNI connection.
        FemPoisson femp=new FemPoisson();
        double [] initc={0.0,1.0,0.0,1.0,0.1};
        // Time the method.
        long startt=System.nanoTime();
        // Run the FEM subroutine in C++.
        double[] res = femp.cFEM(initc,"Cosine");
        long endt=System.nanoTime();
        long totalt=endt-startt;
        double fullt= (double) totalt / 1000000000.0;
        // Fixup the output.
        int r=res.length/3;
        DoubleMatrix outmtx = new DoubleMatrix(r, 3, res);
        System.out.println("Elapsed time is " + fullt +" seconds");
        DoubleMatrix u=outmtx.getColumn(2);
        DoubleMatrix x=outmtx.getColumn(0);
        DoubleMatrix y=outmtx.getColumn(1);
        // Convert vectors to JSON arrays.
        xarpp = convertMatrix(MatrixFunctions.abs(x));
        yarpp = convertMatrix(MatrixFunctions.abs(y));
        uarpp = convertMatrix(u);
    }

    /**
     * ConvertMatrix function.
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
    
    /**
     * ConvertMatrix function.
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
        //sb.append(String.format("%.6f", conv.get(conv.length - 1)));
        
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

    public PdePoisson getPdefun() {
        return pdefun;
    }

    public void setPdefun(PdePoisson pdefun) {
        this.pdefun = pdefun;
    }

    public List<PdePoisson> getPdelist() {
        return pdelist;
    }  
}
