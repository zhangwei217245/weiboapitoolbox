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
@Table(name = "loopconfig")
@NamedQueries({
    @NamedQuery(name = "Loopconfig.findAll", query = "SELECT l FROM Loopconfig l"),
    @NamedQuery(name = "Loopconfig.findByNumconfigid", query = "SELECT l FROM Loopconfig l WHERE l.numconfigid = :numconfigid"),
    @NamedQuery(name = "Loopconfig.findByFilename", query = "SELECT l FROM Loopconfig l WHERE l.filename = :filename"),
    @NamedQuery(name = "Loopconfig.findByDatupdated", query = "SELECT l FROM Loopconfig l WHERE l.datupdated = :datupdated"),
    @NamedQuery(name = "Loopconfig.findByNumispublic", query = "SELECT l FROM Loopconfig l WHERE l.numispublic = :numispublic"),
    @NamedQuery(name = "Loopconfig.findByNumownerid", query = "SELECT l FROM Loopconfig l WHERE l.numownerid = :numownerid")})
public class Loopconfig implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numconfigid", nullable = false)
    private Integer numconfigid;
    @Basic(optional = false)
    @Column(name = "filename", nullable = false, length = 100)
    private String filename;
    @Basic(optional = false)
    @Column(name = "datupdated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datupdated;
    @Basic(optional = false)
    @Column(name = "numispublic", nullable = false)
    private int numispublic;
    @Column(name = "numownerid")
    private Integer numownerid;

    public Loopconfig() {
    }

    public Loopconfig(Integer numconfigid) {
        this.numconfigid = numconfigid;
    }

    public Loopconfig(Integer numconfigid, String filename, Date datupdated, int numispublic) {
        this.numconfigid = numconfigid;
        this.filename = filename;
        this.datupdated = datupdated;
        this.numispublic = numispublic;
    }

    public Integer getNumconfigid() {
        return numconfigid;
    }

    public void setNumconfigid(Integer numconfigid) {
        this.numconfigid = numconfigid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getDatupdated() {
        return datupdated;
    }

    public void setDatupdated(Date datupdated) {
        this.datupdated = datupdated;
    }

    public int getNumispublic() {
        return numispublic;
    }

    public void setNumispublic(int numispublic) {
        this.numispublic = numispublic;
    }

    public Integer getNumownerid() {
        return numownerid;
    }

    public void setNumownerid(Integer numownerid) {
        this.numownerid = numownerid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numconfigid != null ? numconfigid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loopconfig)) {
            return false;
        }
        Loopconfig other = (Loopconfig) object;
        if ((this.numconfigid == null && other.numconfigid != null) || (this.numconfigid != null && !this.numconfigid.equals(other.numconfigid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.persist.entity.Loopconfig[numconfigid=" + numconfigid + "]";
    }

}
