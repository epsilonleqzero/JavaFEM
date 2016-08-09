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
@Table(name = "ode_3D")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ode3D.findAll", query = "SELECT o FROM Ode3D o"),
    @NamedQuery(name = "Ode3D.findById", query = "SELECT o FROM Ode3D o WHERE o.id = :id"),
    @NamedQuery(name = "Ode3D.findByName", query = "SELECT o FROM Ode3D o WHERE o.name = :name"),
    @NamedQuery(name = "Ode3D.findByA", query = "SELECT o FROM Ode3D o WHERE o.a = :a"),
    @NamedQuery(name = "Ode3D.findByB", query = "SELECT o FROM Ode3D o WHERE o.b = :b"),
    @NamedQuery(name = "Ode3D.findByC", query = "SELECT o FROM Ode3D o WHERE o.c = :c"),
    @NamedQuery(name = "Ode3D.findByX0", query = "SELECT o FROM Ode3D o WHERE o.x0 = :x0"),
    @NamedQuery(name = "Ode3D.findByY0", query = "SELECT o FROM Ode3D o WHERE o.y0 = :y0"),
    @NamedQuery(name = "Ode3D.findByZ0", query = "SELECT o FROM Ode3D o WHERE o.z0 = :z0")})
public class Ode3D implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "x0")
    private BigDecimal x0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "y0")
    private BigDecimal y0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "z0")
    private BigDecimal z0;

    public Ode3D() {
    }

    public Ode3D(Integer id) {
        this.id = id;
    }

    public Ode3D(Integer id, String name, BigDecimal x0, BigDecimal y0, BigDecimal z0) {
        this.id = id;
        this.name = name;
        this.x0 = x0;
        this.y0 = y0;
        this.z0 = z0;
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

    public BigDecimal getZ0() {
        return z0;
    }

    public void setZ0(BigDecimal z0) {
        this.z0 = z0;
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
        if (!(object instanceof Ode3D)) {
            return false;
        }
        Ode3D other = (Ode3D) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.tedkwan.javafem.entity.Ode3D[ id=" + id + " ]";
    }
    
}
