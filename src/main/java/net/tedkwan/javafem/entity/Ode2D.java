/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tedkwan.javafem.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author devils
 */
@Entity
@Table(name = "ode_2D")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ode2D.findAll", query = "SELECT o FROM Ode2D o"),
    @NamedQuery(name = "Ode2D.findById", query = "SELECT o FROM Ode2D o WHERE o.id = :id"),
    @NamedQuery(name = "Ode2D.findByName", query = "SELECT o FROM Ode2D o WHERE o.name = :name"),
    @NamedQuery(name = "Ode2D.findByA", query = "SELECT o FROM Ode2D o WHERE o.a = :a"),
    @NamedQuery(name = "Ode2D.findByB", query = "SELECT o FROM Ode2D o WHERE o.b = :b"),
    @NamedQuery(name = "Ode2D.findByC", query = "SELECT o FROM Ode2D o WHERE o.c = :c"),
    @NamedQuery(name = "Ode2D.findByD", query = "SELECT o FROM Ode2D o WHERE o.d = :d"),
    @NamedQuery(name = "Ode2D.findByX0", query = "SELECT o FROM Ode2D o WHERE o.x0 = :x0"),
    @NamedQuery(name = "Ode2D.findByY0", query = "SELECT o FROM Ode2D o WHERE o.y0 = :y0")})
public class Ode2D implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "a")
    private BigDecimal a;
    @Column(name = "b")
    private BigDecimal b;
    @Column(name = "c")
    private BigDecimal c;
    @Column(name = "d")
    private BigDecimal d;
    @Basic(optional = false)
    @NotNull
    @Column(name = "x0")
    private BigDecimal x0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "y0")
    private BigDecimal y0;

    public Ode2D() {
    }

    public Ode2D(Integer id) {
        this.id = id;
    }

    public Ode2D(Integer id, String name, BigDecimal x0, BigDecimal y0) {
        this.id = id;
        this.name = name;
        this.x0 = x0;
        this.y0 = y0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getA() {
        return a;
    }

    public void setA(BigDecimal a) {
        this.a = a;
    }

    public BigDecimal getB() {
        return b;
    }

    public void setB(BigDecimal b) {
        this.b = b;
    }

    public BigDecimal getC() {
        return c;
    }

    public void setC(BigDecimal c) {
        this.c = c;
    }

    public BigDecimal getD() {
        return d;
    }

    public void setD(BigDecimal d) {
        this.d = d;
    }

    public BigDecimal getX0() {
        return x0;
    }

    public void setX0(BigDecimal x0) {
        this.x0 = x0;
    }

    public BigDecimal getY0() {
        return y0;
    }

    public void setY0(BigDecimal y0) {
        this.y0 = y0;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ode2D)) {
            return false;
        }
        Ode2D other = (Ode2D) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.tedkwan.javafem.entity.Ode2D[ id=" + id + " ]";
    }
    
}
