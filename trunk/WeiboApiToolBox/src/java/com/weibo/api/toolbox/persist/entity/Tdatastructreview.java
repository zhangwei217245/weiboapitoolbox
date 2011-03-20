/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.persist.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "tdatastructreview")
@NamedQueries({
    @NamedQuery(name = "Tdatastructreview.findAll", query = "SELECT t FROM Tdatastructreview t"),
    @NamedQuery(name = "Tdatastructreview.findByNumreviewid", query = "SELECT t FROM Tdatastructreview t WHERE t.numreviewid = :numreviewid"),
    @NamedQuery(name = "Tdatastructreview.findByVc2reviewmsg", query = "SELECT t FROM Tdatastructreview t WHERE t.vc2reviewmsg = :vc2reviewmsg"),
    @NamedQuery(name = "Tdatastructreview.findByNumshipped", query = "SELECT t FROM Tdatastructreview t WHERE t.numshipped = :numshipped"),
    @NamedQuery(name = "Tdatastructreview.findByDatreviewtime", query = "SELECT t FROM Tdatastructreview t WHERE t.datreviewtime = :datreviewtime")})
public class Tdatastructreview implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numreviewid", nullable = false)
    private Integer numreviewid;
    @Basic(optional = false)
    @Column(name = "vc2reviewmsg", nullable = false, length = 5000)
    private String vc2reviewmsg;
    @Basic(optional = false)
    @Column(name = "numshipped", nullable = false)
    private int numshipped;
    @Basic(optional = false)
    @Column(name = "datreviewtime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datreviewtime;
    @JoinColumn(name = "numreviewerid", referencedColumnName = "numuserid", nullable = false)
    @ManyToOne(optional = false)
    private Tuser numreviewerid;
    @JoinColumn(name = "numdatastructid", referencedColumnName = "numdatastructid", nullable = false)
    @ManyToOne(optional = false)
    private Tdatastruct numdatastructid;

    public Tdatastructreview() {
    }

    public Tdatastructreview(Integer numreviewid) {
        this.numreviewid = numreviewid;
    }

    public Tdatastructreview(Integer numreviewid, String vc2reviewmsg, int numshipped, Date datreviewtime) {
        this.numreviewid = numreviewid;
        this.vc2reviewmsg = vc2reviewmsg;
        this.numshipped = numshipped;
        this.datreviewtime = datreviewtime;
    }

    public Integer getNumreviewid() {
        return numreviewid;
    }

    public void setNumreviewid(Integer numreviewid) {
        this.numreviewid = numreviewid;
    }

    public String getVc2reviewmsg() {
        return vc2reviewmsg;
    }

    public void setVc2reviewmsg(String vc2reviewmsg) {
        this.vc2reviewmsg = vc2reviewmsg;
    }

    public int getNumshipped() {
        return numshipped;
    }

    public void setNumshipped(int numshipped) {
        this.numshipped = numshipped;
    }

    public Date getDatreviewtime() {
        return datreviewtime;
    }

    public void setDatreviewtime(Date datreviewtime) {
        this.datreviewtime = datreviewtime;
    }

    public Tuser getNumreviewerid() {
        return numreviewerid;
    }

    public void setNumreviewerid(Tuser numreviewerid) {
        this.numreviewerid = numreviewerid;
    }

    public Tdatastruct getNumdatastructid() {
        return numdatastructid;
    }

    public void setNumdatastructid(Tdatastruct numdatastructid) {
        this.numdatastructid = numdatastructid;
    }

    public boolean getIsShipped(){
        return this.getNumshipped() > 0;
    }

    public void setIsShipped(boolean shipped){
        this.setNumshipped(shipped?1:0);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numreviewid != null ? numreviewid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tdatastructreview)) {
            return false;
        }
        Tdatastructreview other = (Tdatastructreview) object;
        if ((this.numreviewid == null && other.numreviewid != null) || (this.numreviewid != null && !this.numreviewid.equals(other.numreviewid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zkoss.zkdemo.persist.Tdatastructreview[numreviewid=" + numreviewid + "]";
    }

}
