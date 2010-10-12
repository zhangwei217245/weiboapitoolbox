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

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "apidef")
@NamedQueries({
    @NamedQuery(name = "Apidef.findAll", query = "SELECT a FROM Apidef a"),
    @NamedQuery(name = "Apidef.findByNumapiid", query = "SELECT a FROM Apidef a WHERE a.numapiid = :numapiid"),
    @NamedQuery(name = "Apidef.findByVc2identity", query = "SELECT a FROM Apidef a WHERE a.vc2identity = :vc2identity"),
    @NamedQuery(name = "Apidef.findByNumindex", query = "SELECT a FROM Apidef a WHERE a.numindex = :numindex"),
    @NamedQuery(name = "Apidef.findByVc2brief", query = "SELECT a FROM Apidef a WHERE a.vc2brief = :vc2brief"),
    @NamedQuery(name = "Apidef.findByVc2url", query = "SELECT a FROM Apidef a WHERE a.vc2url = :vc2url"),
    @NamedQuery(name = "Apidef.findByVc2desc", query = "SELECT a FROM Apidef a WHERE a.vc2desc = :vc2desc"),
    @NamedQuery(name = "Apidef.findByNumisneedauth", query = "SELECT a FROM Apidef a WHERE a.numisneedauth = :numisneedauth"),
    @NamedQuery(name = "Apidef.findByNumisratelimited", query = "SELECT a FROM Apidef a WHERE a.numisratelimited = :numisratelimited"),
    @NamedQuery(name = "Apidef.findByVc2enctype", query = "SELECT a FROM Apidef a WHERE a.vc2enctype = :vc2enctype"),
    @NamedQuery(name = "Apidef.findByNumstatus", query = "SELECT a FROM Apidef a WHERE a.numstatus = :numstatus"),
    @NamedQuery(name = "Apidef.findByNumcataid", query = "SELECT a FROM Apidef a WHERE a.numcataid = :numcataid"),
    @NamedQuery(name = "Apidef.findByDatcreate", query = "SELECT a FROM Apidef a WHERE a.datcreate = :datcreate"),
    @NamedQuery(name = "Apidef.findByNumcreateuid", query = "SELECT a FROM Apidef a WHERE a.numcreateuid = :numcreateuid"),
    @NamedQuery(name = "Apidef.findByDatelastmodify", query = "SELECT a FROM Apidef a WHERE a.datelastmodify = :datelastmodify"),
    @NamedQuery(name = "Apidef.findByNummodifyuid", query = "SELECT a FROM Apidef a WHERE a.nummodifyuid = :nummodifyuid")})
public class Apidef implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numapiid", nullable = false)
    private Integer numapiid;
    @Basic(optional = false)
    @Column(name = "vc2identity", nullable = false, length = 50)
    private String vc2identity;
    @Basic(optional = false)
    @Column(name = "numindex", nullable = false)
    private int numindex;
    @Basic(optional = false)
    @Column(name = "vc2brief", nullable = false, length = 100)
    private String vc2brief;
    @Basic(optional = false)
    @Column(name = "vc2url", nullable = false, length = 100)
    private String vc2url;
    @Column(name = "vc2desc", length = 1000)
    private String vc2desc;
    @Basic(optional = false)
    @Column(name = "numisneedauth", nullable = false)
    private int numisneedauth;
    @Basic(optional = false)
    @Column(name = "numisratelimited", nullable = false)
    private int numisratelimited;
    @Basic(optional = false)
    @Column(name = "vc2enctype", nullable = false, length = 100)
    private String vc2enctype;
    @Basic(optional = false)
    @Column(name = "numstatus", nullable = false)
    private int numstatus;
    @Column(name = "numcataid")
    private Integer numcataid;
    @Basic(optional = false)
    @Column(name = "datcreate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datcreate;
    @Basic(optional = false)
    @Column(name = "numcreateuid", nullable = false)
    private int numcreateuid;
    @Column(name = "datelastmodify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datelastmodify;
    @Column(name = "nummodifyuid")
    private Integer nummodifyuid;

    public Apidef() {
    }

    public Apidef(Integer numapiid) {
        this.numapiid = numapiid;
    }

    public Apidef(Integer numapiid, String vc2identity, int numindex, String vc2brief, String vc2url, int numisneedauth, int numisratelimited, String vc2enctype, int numstatus, Date datcreate, int numcreateuid) {
        this.numapiid = numapiid;
        this.vc2identity = vc2identity;
        this.numindex = numindex;
        this.vc2brief = vc2brief;
        this.vc2url = vc2url;
        this.numisneedauth = numisneedauth;
        this.numisratelimited = numisratelimited;
        this.vc2enctype = vc2enctype;
        this.numstatus = numstatus;
        this.datcreate = datcreate;
        this.numcreateuid = numcreateuid;
    }

    public Integer getNumapiid() {
        return numapiid;
    }

    public void setNumapiid(Integer numapiid) {
        this.numapiid = numapiid;
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

    public String getVc2brief() {
        return vc2brief;
    }

    public void setVc2brief(String vc2brief) {
        this.vc2brief = vc2brief;
    }

    public String getVc2url() {
        return vc2url;
    }

    public void setVc2url(String vc2url) {
        this.vc2url = vc2url;
    }

    public String getVc2desc() {
        return vc2desc;
    }

    public void setVc2desc(String vc2desc) {
        this.vc2desc = vc2desc;
    }

    public int getNumisneedauth() {
        return numisneedauth;
    }

    public void setNumisneedauth(int numisneedauth) {
        this.numisneedauth = numisneedauth;
    }

    public int getNumisratelimited() {
        return numisratelimited;
    }

    public void setNumisratelimited(int numisratelimited) {
        this.numisratelimited = numisratelimited;
    }

    public String getVc2enctype() {
        return vc2enctype;
    }

    public void setVc2enctype(String vc2enctype) {
        this.vc2enctype = vc2enctype;
    }

    public int getNumstatus() {
        return numstatus;
    }

    public void setNumstatus(int numstatus) {
        this.numstatus = numstatus;
    }

    public Integer getNumcataid() {
        return numcataid;
    }

    public void setNumcataid(Integer numcataid) {
        this.numcataid = numcataid;
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

    public Date getDatelastmodify() {
        return datelastmodify;
    }

    public void setDatelastmodify(Date datelastmodify) {
        this.datelastmodify = datelastmodify;
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
        hash += (numapiid != null ? numapiid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apidef)) {
            return false;
        }
        Apidef other = (Apidef) object;
        if ((this.numapiid == null && other.numapiid != null) || (this.numapiid != null && !this.numapiid.equals(other.numapiid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Apidef[numapiid=" + numapiid + "]";
    }

}
