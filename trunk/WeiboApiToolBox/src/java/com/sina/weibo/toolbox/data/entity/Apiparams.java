/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.data.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "apiparams")
@NamedQueries({
    @NamedQuery(name = "Apiparams.findAll", query = "SELECT a FROM Apiparams a"),
    @NamedQuery(name = "Apiparams.findByNumparamid", query = "SELECT a FROM Apiparams a WHERE a.numparamid = :numparamid"),
    @NamedQuery(name = "Apiparams.findByVc2paramname", query = "SELECT a FROM Apiparams a WHERE a.vc2paramname = :vc2paramname"),
    @NamedQuery(name = "Apiparams.findByNumrequired", query = "SELECT a FROM Apiparams a WHERE a.numrequired = :numrequired"),
    @NamedQuery(name = "Apiparams.findByNumisrest", query = "SELECT a FROM Apiparams a WHERE a.numisrest = :numisrest"),
    @NamedQuery(name = "Apiparams.findByVc2paramdesc", query = "SELECT a FROM Apiparams a WHERE a.vc2paramdesc = :vc2paramdesc"),
    @NamedQuery(name = "Apiparams.findByNumapiid", query = "SELECT a FROM Apiparams a WHERE a.numapiid = :numapiid")})
public class Apiparams implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numparamid", nullable = false)
    private Integer numparamid;
    @Basic(optional = false)
    @Column(name = "vc2paramname", nullable = false, length = 50)
    private String vc2paramname;
    @Basic(optional = false)
    @Column(name = "numrequired", nullable = false)
    private int numrequired;
    @Basic(optional = false)
    @Column(name = "numisrest", nullable = false)
    private int numisrest;
    @Basic(optional = false)
    @Column(name = "vc2paramdesc", nullable = false, length = 1000)
    private String vc2paramdesc;
    @Basic(optional = false)
    @Column(name = "numapiid", nullable = false)
    private int numapiid;

    public Apiparams() {
    }

    public Apiparams(Integer numparamid) {
        this.numparamid = numparamid;
    }

    public Apiparams(Integer numparamid, String vc2paramname, int numrequired, int numisrest, String vc2paramdesc, int numapiid) {
        this.numparamid = numparamid;
        this.vc2paramname = vc2paramname;
        this.numrequired = numrequired;
        this.numisrest = numisrest;
        this.vc2paramdesc = vc2paramdesc;
        this.numapiid = numapiid;
    }

    public Integer getNumparamid() {
        return numparamid;
    }

    public void setNumparamid(Integer numparamid) {
        this.numparamid = numparamid;
    }

    public String getVc2paramname() {
        return vc2paramname;
    }

    public void setVc2paramname(String vc2paramname) {
        this.vc2paramname = vc2paramname;
    }

    public int getNumrequired() {
        return numrequired;
    }

    public void setNumrequired(int numrequired) {
        this.numrequired = numrequired;
    }

    public int getNumisrest() {
        return numisrest;
    }

    public void setNumisrest(int numisrest) {
        this.numisrest = numisrest;
    }

    public String getVc2paramdesc() {
        return vc2paramdesc;
    }

    public void setVc2paramdesc(String vc2paramdesc) {
        this.vc2paramdesc = vc2paramdesc;
    }

    public int getNumapiid() {
        return numapiid;
    }

    public void setNumapiid(int numapiid) {
        this.numapiid = numapiid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numparamid != null ? numparamid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apiparams)) {
            return false;
        }
        Apiparams other = (Apiparams) object;
        if ((this.numparamid == null && other.numparamid != null) || (this.numparamid != null && !this.numparamid.equals(other.numparamid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Apiparams[numparamid=" + numparamid + "]";
    }

}
