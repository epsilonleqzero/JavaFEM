/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.javafem.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.tedkwan.javafem.entity.PdePoisson;

/**
 *
 * @author devils
 */
@Stateless
public class PdePoissonFacade extends AbstractFacade<PdePoisson> {

    @PersistenceContext(unitName = "net.tedkwan_JavaFEM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PdePoissonFacade() {
        super(PdePoisson.class);
    }
    
}
