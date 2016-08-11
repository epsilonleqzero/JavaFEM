/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.javafem.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import net.tedkwan.javafem.entity.Ode2D;
import net.tedkwan.javafemjni.TwoDimPhase;
import org.jblas.DoubleMatrix;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;


/**
 *
 * @author devils
 */
@ManagedBean(name = "phase2SelectionBean")
@ViewScoped
public class Phase2SelectionBean implements Serializable {
    
    Ode2D odefun;
    List<Ode2D> odelist;
    private LineChartModel lineModel;
    @PersistenceContext(unitName = "net.tedkwan_JavaFEM_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public Phase2SelectionBean(){
        makeLorenz();
    }
    
    @PostConstruct
    public void init(){
        Query query = em.createNamedQuery("Ode2D.findAll");
        odelist =(List<Ode2D>) query.getResultList();
        odefun=odelist.get(0);
        lineModel= new LineChartModel(); 
    }

    private String xarpp ="";
    private String yarpp ="";
    private String xarppl ="";
    private String yarppl = "";
    private String uarpp = "";
    private String googdata = "";
    
    public void persist(Object object) {
        em.persist(object);
    }
    public Ode2D RetrieveDefaultOde2D(){
        Query query = em.createNamedQuery("Ode2D.findById").setParameter("id", 1);
        odefun=(Ode2D) query.getSingleResult();
        return odefun;
    }
    public List<Ode2D> RetrieveOde2Dlist(){
        Query query = em.createNamedQuery("Ode2D.findAll");
        return query.getResultList();
    }

    private void makeLorenz() {
        System.out.println(System.getProperty("java.library.path"));
        //DuffingOscil duff = new DuffingOscil();
        TwoDimPhase phase = new TwoDimPhase();
        double[] initc = {0.2, (8.0 / 3.0), 28.0, 10.0, 0.0, 20.0, 1.3, 1.6, 3.6};
        double[] res = phase.rk4(initc, "lorenz");
        int r = res.length / 4;
        System.out.println(res.length);
        DoubleMatrix outmtx = new DoubleMatrix(r, 4, res);
        DoubleMatrix xpp = outmtx.getColumn(0);
        DoubleMatrix ypp = outmtx.getColumn(1);
        DoubleMatrix upp = outmtx.getColumn(2);
//        xarppl = convertMatrix((xpp));
//        yarppl = convertMatrix((ypp));
//        uarpp = convertMatrix(upp);
        googdata=convertMatrices(outmtx);
    }

    private void makeDuffing() {
        //System.out.println(System.getProperty("java.library.path"));
        TwoDimPhase phase = new TwoDimPhase();
        String odename=odefun.getName();
        double [] initc;
//        double[] initc = {0.05, 0.8,
//            1.0, 0.3, -1.0, 20.0, 0.7, 0.6};
        if(odename.contains("uffi")){
            double [] initcduff= {0.05, odefun.getA().doubleValue(),
                odefun.getB().doubleValue(),
                odefun.getC().doubleValue(),odefun.getD().doubleValue()
                    ,20.0,odefun.getX0().doubleValue(),
                    odefun.getY0().doubleValue()};
            initc=initcduff;
        }else{
            double [] initcvan= {0.05, odefun.getA().doubleValue(),
            1.0, 0.3,-1.0,20.0,odefun.getX0().doubleValue(),odefun.getY0().doubleValue()};
            initc=initcvan;
        }
        double[] res = phase.rk4(initc,odefun.getName());
        int r = res.length / 3;
        //System.out.println(res.length);
        DoubleMatrix outmtx = new DoubleMatrix(r, 3, res);
        DoubleMatrix xpp = outmtx.getColumn(0);
        DoubleMatrix ypp = outmtx.getColumn(1);
        xarpp = convertMatrix((xpp));
        yarpp = convertMatrix((ypp));
        System.out.println("Done.");
        LineChartSeries duffseries=new LineChartSeries();
        int i=0;
        while(i<xpp.rows){
            i+=5;
        }
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
    
    public void showPPsecond() {
        makeDuffing();
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", true);
        options.put("draggable", true);
        options.put("modal", true);
        RequestContext context=RequestContext.getCurrentInstance();
        context.openDialog("showPPsecond", options, null);
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
    
    public String onFlowProcess(FlowEvent event){
        makeLorenz();
        return event.getNewStep();
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

    public Ode2D getOdefun() {
        return odefun;
    }

    public void setOdefun(Ode2D odefun) {
        this.odefun = odefun;
    }

    public List<Ode2D> getOdelist() {
        return odelist;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public String getGoogdata() {
        return googdata;
    }
    
    
   
}
