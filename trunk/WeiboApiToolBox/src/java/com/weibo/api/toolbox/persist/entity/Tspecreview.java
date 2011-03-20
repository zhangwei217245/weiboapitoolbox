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
@Table(name = "tspecreview")
@NamedQueries({
    @NamedQuery(name = "Tspecreview.findAll", query = "SELECT t FROM Tspecreview t"),
    @NamedQuery(name = "Tspecreview.findByNumreviewid", query = "SELECT t FROM Tspecreview t WHERE t.numreviewid = :numreviewid"),
    @NamedQuery(name = "Tspecreview.findByVc2reviewmsg", query = "SELECT t FROM Tspecreview t WHERE t.vc2reviewmsg = :vc2reviewmsg"),
    @NamedQuery(name = "Tspecreview.findByNumshipped", query = "SELECT t FROM Tspecreview t WHERE t.numshipped = :numshipped"),
    @NamedQuery(name = "Tspecreview.findByDatreviewtime", query = "SELECT t FROM Tspecreview t WHERE t.datreviewtime = :datreviewtime")})
public class Tspecreview implements Serializable {
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
    @JoinColumn(name = "numspecid", referencedColumnName = "numspecid", nullable = false)
    @ManyToOne(optional = false)
    private Tspec numspecid;

    public Tspecreview() {
    }

    public Tspecreview(Integer numreviewid) {
        this.numreviewid = numreviewid;
    }

    public Tspecreview(Integer numreviewid, String vc2reviewmsg, int numshipped, Date datreviewtime) {
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

    public Tspec getNumspecid() {
        return numspecid;
    }

    public void setNumspecid(Tspec numspecid) {
        this.numspecid = numspecid;
    }

    public boolean getIsShipped(){
        return this.getNumshipped() > 0;
    }

    public void setIsShipped(boolean shipped){
        this.setNumshipped(shipped?1:0);
    }

    public String getImgShipped(){
        if (this.getNumshipped()>0){
            return "/img/smallicons/right_small.png";
        }else{
            return "/img/smallicons/wrong_small.png";
        }
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
        if (!(object instanceof Tspecreview)) {
            return false;
        }
        Tspecreview other = (Tspecreview) object;
        if ((this.numreviewid == null && other.numreviewid != null) || (this.numreviewid != null && !this.numreviewid.equals(other.numreviewid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zkoss.zkdemo.persist.Tspecreview[numreviewid=" + numreviewid + "]";
    }

}
