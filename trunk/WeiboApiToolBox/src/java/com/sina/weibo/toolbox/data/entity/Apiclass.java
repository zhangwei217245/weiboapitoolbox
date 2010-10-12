/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.data.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "apiclass", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"vc2identity"})})
@NamedQueries({
    @NamedQuery(name = "Apiclass.findAll", query = "SELECT a FROM Apiclass a"),
    @NamedQuery(name = "Apiclass.findByNumclassid", query = "SELECT a FROM Apiclass a WHERE a.numclassid = :numclassid"),
    @NamedQuery(name = "Apiclass.findByVc2identity", query = "SELECT a FROM Apiclass a WHERE a.vc2identity = :vc2identity"),
    @NamedQuery(name = "Apiclass.findByNumindex", query = "SELECT a FROM Apiclass a WHERE a.numindex = :numindex"),
    @NamedQuery(name = "Apiclass.findByVc2desc", query = "SELECT a FROM Apiclass a WHERE a.vc2desc = :vc2desc"),
    @NamedQuery(name = "Apiclass.findByNumstatus", query = "SELECT a FROM Apiclass a WHERE a.numstatus = :numstatus"),
    @NamedQuery(name = "Apiclass.findByNumversionid", query = "SELECT a FROM Apiclass a WHERE a.numversionid = :numversionid"),
    @NamedQuery(name = "Apiclass.findByDatcreate", query = "SELECT a FROM Apiclass a WHERE a.datcreate = :datcreate"),
    @NamedQuery(name = "Apiclass.findByNumcreateuid", query = "SELECT a FROM Apiclass a WHERE a.numcreateuid = :numcreateuid"),
    @NamedQuery(name = "Apiclass.findByDatlastmodify", query = "SELECT a FROM Apiclass a WHERE a.datlastmodify = :datlastmodify"),
    @NamedQuery(name = "Apiclass.findByNummodifyuid", query = "SELECT a FROM Apiclass a WHERE a.nummodifyuid = :nummodifyuid")})
public class Apiclass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numclassid", nullable = false)
    private Integer numclassid;
    @Basic(optional = false)
    @Column(name = "vc2identity", nullable = false, length = 50)
    private String vc2identity;
    @Basic(optional = false)
    @Column(name = "numindex", nullable = false)
    private int numindex;
    @Basic(optional = false)
    @Column(name = "vc2desc", nullable = false, length = 500)
    private String vc2desc;
    @Basic(optional = false)
    @Column(name = "numstatus", nullable = false)
    private int numstatus;
    @Column(name = "numversionid")
    private Integer numversionid;
    @Basic(optional = false)
    @Column(name = "datcreate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datcreate;
    @Basic(optional = false)
    @Column(name = "numcreateuid", nullable = false)
    private int numcreateuid;
    @Column(name = "datlastmodify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datlastmodify;
    @Column(name = "nummodifyuid")
    private Integer nummodifyuid;

    public Apiclass() {
    }

    public Apiclass(Integer numclassid) {
        this.numclassid = numclassid;
    }

    public Apiclass(Integer numclassid, String vc2identity, int numindex, String vc2desc, int numstatus, Date datcreate, int numcreateuid) {
        this.numclassid = numclassid;
        this.vc2identity = vc2identity;
        this.numindex = numindex;
        this.vc2desc = vc2desc;
        this.numstatus = numstatus;
        this.datcreate = datcreate;
        this.numcreateuid = numcreateuid;
    }

    public Integer getNumclassid() {
        return numclassid;
    }

    public void setNumclassid(Integer numclassid) {
        this.numclassid = numclassid;
    }

    public String getVc2identity() {
        return vc2identity;
    }

    public void setVc2identity(String vc2identity) {
        this.vc2identity = vc2identity;
    }

    public int getNumindex() {
        return numindex;
    }

    public void setNumindex(int numindex) {
        this.numindex = numindex;
    }

    public String getVc2desc() {
        return vc2desc;
    }

    public void setVc2desc(String vc2desc) {
        this.vc2desc = vc2desc;
    }

    public int getNumstatus() {
        return numstatus;
    }

    public void setNumstatus(int numstatus) {
        this.numstatus = numstatus;
    }

    public Integer getNumversionid() {
        return numversionid;
    }

    public void setNumversionid(Integer numversionid) {
        this.numversionid = numversionid;
    }

    public Date getDatcreate() {
        return datcreate;
    }

    public void setDatcreate(Date datcreate) {
        this.datcreate = datcreate;
    }

    public int getNumcreateuid() {
        return numcreateuid;
    }

    public void setNumcreateuid(int numcreateuid) {
        this.numcreateuid = numcreateuid;
    }

    public Date getDatlastmodify() {
        return datlastmodify;
    }

    public void setDatlastmodify(Date datlastmodify) {
        this.datlastmodify = datlastmodify;
    }

    public Integer getNummodifyuid() {
        return nummodifyuid;
    }

    public void setNummodifyuid(Integer nummodifyuid) {
        this.nummodifyuid = nummodifyuid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numclassid != null ? numclassid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apiclass)) {
            return false;
        }
        Apiclass other = (Apiclass) object;
        if ((this.numclassid == null && other.numclassid != null) || (this.numclassid != null && !this.numclassid.equals(other.numclassid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Apiclass[numclassid=" + numclassid + "]";
    }

}
