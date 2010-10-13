/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.persist.entity;

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

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "apiversion")
@NamedQueries({
    @NamedQuery(name = "Apiversion.findAll", query = "SELECT a FROM Apiversion a"),
    @NamedQuery(name = "Apiversion.findByNumversionid", query = "SELECT a FROM Apiversion a WHERE a.numversionid = :numversionid"),
    @NamedQuery(name = "Apiversion.findByVc2value", query = "SELECT a FROM Apiversion a WHERE a.vc2value = :vc2value"),
    @NamedQuery(name = "Apiversion.findByNumstatus", query = "SELECT a FROM Apiversion a WHERE a.numstatus = :numstatus"),
    @NamedQuery(name = "Apiversion.findByDatcreatedate", query = "SELECT a FROM Apiversion a WHERE a.datcreatedate = :datcreatedate"),
    @NamedQuery(name = "Apiversion.findByNumcreateuid", query = "SELECT a FROM Apiversion a WHERE a.numcreateuid = :numcreateuid"),
    @NamedQuery(name = "Apiversion.findByDatlastmodify", query = "SELECT a FROM Apiversion a WHERE a.datlastmodify = :datlastmodify"),
    @NamedQuery(name = "Apiversion.findByNummodifyuid", query = "SELECT a FROM Apiversion a WHERE a.nummodifyuid = :nummodifyuid")})
public class Apiversion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numversionid", nullable = false)
    private Integer numversionid;
    @Basic(optional = false)
    @Column(name = "vc2value", nullable = false, length = 20)
    private String vc2value;
    @Basic(optional = false)
    @Column(name = "numstatus", nullable = false)
    private int numstatus;
    @Basic(optional = false)
    @Column(name = "datcreatedate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datcreatedate;
    @Basic(optional = false)
    @Column(name = "numcreateuid", nullable = false)
    private int numcreateuid;
    @Column(name = "datlastmodify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datlastmodify;
    @Column(name = "nummodifyuid")
    private Integer nummodifyuid;

    public Apiversion() {
    }

    public Apiversion(Integer numversionid) {
        this.numversionid = numversionid;
    }

    public Apiversion(Integer numversionid, String vc2value, int numstatus, Date datcreatedate, int numcreateuid) {
        this.numversionid = numversionid;
        this.vc2value = vc2value;
        this.numstatus = numstatus;
        this.datcreatedate = datcreatedate;
        this.numcreateuid = numcreateuid;
    }

    public Integer getNumversionid() {
        return numversionid;
    }

    public void setNumversionid(Integer numversionid) {
        this.numversionid = numversionid;
    }

    public String getVc2value() {
        return vc2value;
    }

    public void setVc2value(String vc2value) {
        this.vc2value = vc2value;
    }

    public int getNumstatus() {
        return numstatus;
    }

    public void setNumstatus(int numstatus) {
        this.numstatus = numstatus;
    }

    public Date getDatcreatedate() {
        return datcreatedate;
    }

    public void setDatcreatedate(Date datcreatedate) {
        this.datcreatedate = datcreatedate;
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
        hash += (numversionid != null ? numversionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apiversion)) {
            return false;
        }
        Apiversion other = (Apiversion) object;
        if ((this.numversionid == null && other.numversionid != null) || (this.numversionid != null && !this.numversionid.equals(other.numversionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.persist.entity.Apiversion[numversionid=" + numversionid + "]";
    }

}
