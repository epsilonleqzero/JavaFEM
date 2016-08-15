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
@Table(name = "pde_poisson")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PdePoisson.findAll", query = "SELECT p FROM PdePoisson p"),
    @NamedQuery(name = "PdePoisson.findById", query = "SELECT p FROM PdePoisson p WHERE p.id = :id"),
    @NamedQuery(name = "PdePoisson.findByName", query = "SELECT p FROM PdePoisson p WHERE p.name = :name"),
    @NamedQuery(name = "PdePoisson.findByX0", query = "SELECT p FROM PdePoisson p WHERE p.x0 = :x0"),
    @NamedQuery(name = "PdePoisson.findByX1", query = "SELECT p FROM PdePoisson p WHERE p.x1 = :x1"),
    @NamedQuery(name = "PdePoisson.findByY0", query = "SELECT p FROM PdePoisson p WHERE p.y0 = :y0"),
    @NamedQuery(name = "PdePoisson.findByY1", query = "SELECT p FROM PdePoisson p WHERE p.y1 = :y1"),
    @NamedQuery(name = "PdePoisson.findByH", query = "SELECT p FROM PdePoisson p WHERE p.h = :h"),
    @NamedQuery(name = "PdePoisson.findByEquation", query = "SELECT p FROM PdePoisson p WHERE p.equation = :equation")})
public class PdePoisson implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "x0")
    private BigDecimal x0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "x1")
    private BigDecimal x1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "y0")
    private BigDecimal y0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "y1")
    private BigDecimal y1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "h")
    private BigDecimal h;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "equation")
    private String equation;

    public PdePoisson() {
    }

    public PdePoisson(Integer id) {
        this.id = id;
    }

    public PdePoisson(Integer id, String name, BigDecimal x0, BigDecimal x1, BigDecimal y0, BigDecimal y1, BigDecimal h, String equation) {
        this.id = id;
        this.name = name;
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
        this.h = h;
        this.equation = equation;
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

    public BigDecimal getX0() {
        return x0;
    }

    public void setX0(BigDecimal x0) {
        this.x0 = x0;
    }

    public BigDecimal getX1() {
        return x1;
    }

    public void setX1(BigDecimal x1) {
        this.x1 = x1;
    }

    public BigDecimal getY0() {
        return y0;
    }

    public void setY0(BigDecimal y0) {
        this.y0 = y0;
    }

    public BigDecimal getY1() {
        return y1;
    }

    public void setY1(BigDecimal y1) {
        this.y1 = y1;
    }

    public BigDecimal getH() {
        return h;
    }

    public void setH(BigDecimal h) {
        this.h = h;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
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
        if (!(object instanceof PdePoisson)) {
            return false;
        }
        PdePoisson other = (PdePoisson) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.tedkwan.javafem.entity.PdePoisson[ id=" + id + " ]";
    }
    
}
