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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "sysenums", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"vc2enumgroup"})})
@NamedQueries({
    @NamedQuery(name = "Sysenums.findAll", query = "SELECT s FROM Sysenums s"),
    @NamedQuery(name = "Sysenums.findByNumenumid", query = "SELECT s FROM Sysenums s WHERE s.numenumid = :numenumid"),
    @NamedQuery(name = "Sysenums.findByVc2enumgroup", query = "SELECT s FROM Sysenums s WHERE s.vc2enumgroup = :vc2enumgroup"),
    @NamedQuery(name = "Sysenums.findByNumenumidx", query = "SELECT s FROM Sysenums s WHERE s.numenumidx = :numenumidx"),
    @NamedQuery(name = "Sysenums.findByVc2enumvalue", query = "SELECT s FROM Sysenums s WHERE s.vc2enumvalue = :vc2enumvalue"),
    @NamedQuery(name = "Sysenums.findByVc2enumname", query = "SELECT s FROM Sysenums s WHERE s.vc2enumname = :vc2enumname"),
    @NamedQuery(name = "Sysenums.findByNumenable", query = "SELECT s FROM Sysenums s WHERE s.numenable = :numenable")})
public class Sysenums implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numenumid", nullable = false)
    private Integer numenumid;
    @Basic(optional = false)
    @Column(name = "vc2enumgroup", nullable = false, length = 200)
    private String vc2enumgroup;
    @Basic(optional = false)
    @Column(name = "numenumidx", nullable = false)
    private int numenumidx;
    @Basic(optional = false)
    @Column(name = "vc2enumvalue", nullable = false, length = 500)
    private String vc2enumvalue;
    @Basic(optional = false)
    @Column(name = "vc2enumname", nullable = false, length = 500)
    private String vc2enumname;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;

    public Sysenums() {
    }

    public Sysenums(Integer numenumid) {
        this.numenumid = numenumid;
    }

    public Sysenums(Integer numenumid, String vc2enumgroup, int numenumidx, String vc2enumvalue, String vc2enumname, int numenable) {
        this.numenumid = numenumid;
        this.vc2enumgroup = vc2enumgroup;
        this.numenumidx = numenumidx;
        this.vc2enumvalue = vc2enumvalue;
        this.vc2enumname = vc2enumname;
        this.numenable = numenable;
    }

    public Integer getNumenumid() {
        return numenumid;
    }

    public void setNumenumid(Integer numenumid) {
        this.numenumid = numenumid;
    }

    public String getVc2enumgroup() {
        return vc2enumgroup;
    }

    public void setVc2enumgroup(String vc2enumgroup) {
        this.vc2enumgroup = vc2enumgroup;
    }

    public int getNumenumidx() {
        return numenumidx;
    }

    public void setNumenumidx(int numenumidx) {
        this.numenumidx = numenumidx;
    }

    public String getVc2enumvalue() {
        return vc2enumvalue;
    }

    public void setVc2enumvalue(String vc2enumvalue) {
        this.vc2enumvalue = vc2enumvalue;
    }

    public String getVc2enumname() {
        return vc2enumname;
    }

    public void setVc2enumname(String vc2enumname) {
        this.vc2enumname = vc2enumname;
    }

    public int getNumenable() {
        return numenable;
    }

    public void setNumenable(int numenable) {
        this.numenable = numenable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numenumid != null ? numenumid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sysenums)) {
            return false;
        }
        Sysenums other = (Sysenums) object;
        if ((this.numenumid == null && other.numenumid != null) || (this.numenumid != null && !this.numenumid.equals(other.numenumid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zkoss.zkdemo.persist.entity.Sysenums[numenumid=" + numenumid + "]";
    }

}
