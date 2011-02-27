/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.persist.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author X-Spirit
 */
@Entity
@Table(name = "terrorcode")
public class Terrorcode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numerrorid", nullable = false)
    private Integer numerrorid;
    @Basic(optional = false)
    @Column(name = "vc2errorcode", nullable = false, length = 500)
    private String vc2errorcode;
    @Basic(optional = false)
    @Column(name = "vc2httpcode", nullable = false, length = 20)
    private String vc2httpcode;
    @Basic(optional = false)
    @Column(name = "vc2errmsg", nullable = false, length = 100)
    private String vc2errmsg;
    @Column(name = "vc2cnmsg", length = 200)
    private String vc2cnmsg;
    @Basic(optional = false)
    @Column(name = "vc2desc", nullable = false, length = 300)
    private String vc2desc;
    @Column(name = "vc2detail", length = 500)
    private String vc2detail;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;
    @JoinColumn(name = "numspecid", referencedColumnName = "numspecid")
    @ManyToOne
    private Tspec numspecid;

    public Terrorcode() {
    }

    public Terrorcode(Integer numerrorid) {
        this.numerrorid = numerrorid;
    }

    public Terrorcode(Integer numerrorid, String vc2errorcode, String vc2httpcode, String vc2errmsg, String vc2desc, int numenable) {
        this.numerrorid = numerrorid;
        this.vc2errorcode = vc2errorcode;
        this.vc2httpcode = vc2httpcode;
        this.vc2errmsg = vc2errmsg;
        this.vc2desc = vc2desc;
        this.numenable = numenable;
    }

    public Integer getNumerrorid() {
        return numerrorid;
    }

    public void setNumerrorid(Integer numerrorid) {
        this.numerrorid = numerrorid;
    }

    public String getVc2errorcode() {
        return vc2errorcode;
    }

    public void setVc2errorcode(String vc2errorcode) {
        this.vc2errorcode = vc2errorcode;
    }

    public String getVc2httpcode() {
        return vc2httpcode;
    }

    public void setVc2httpcode(String vc2httpcode) {
        this.vc2httpcode = vc2httpcode;
    }

    public String getVc2errmsg() {
        return vc2errmsg;
    }

    public void setVc2errmsg(String vc2errmsg) {
        this.vc2errmsg = vc2errmsg;
    }

    public String getVc2cnmsg() {
        return vc2cnmsg;
    }

    public void setVc2cnmsg(String vc2cnmsg) {
        this.vc2cnmsg = vc2cnmsg;
    }

    public String getVc2desc() {
        return vc2desc;
    }

    public void setVc2desc(String vc2desc) {
        this.vc2desc = vc2desc;
    }

    public String getVc2detail() {
        return vc2detail;
    }

    public void setVc2detail(String vc2detail) {
        this.vc2detail = vc2detail;
    }

    public int getNumenable() {
        return numenable;
    }

    public void setNumenable(int numenable) {
        this.numenable = numenable;
    }

    public Tspec getNumspecid() {
        return numspecid;
    }

    public void setNumspecid(Tspec numspecid) {
        this.numspecid = numspecid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numerrorid != null ? numerrorid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Terrorcode)) {
            return false;
        }
        Terrorcode other = (Terrorcode) object;
        if ((this.numerrorid == null && other.numerrorid != null) || (this.numerrorid != null && !this.numerrorid.equals(other.numerrorid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Terrorcode[numerrorid=" + numerrorid + "]";
    }

}
