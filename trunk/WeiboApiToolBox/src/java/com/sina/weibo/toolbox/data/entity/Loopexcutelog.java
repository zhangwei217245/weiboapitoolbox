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
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "loopexcutelog")
@NamedQueries({
    @NamedQuery(name = "Loopexcutelog.findAll", query = "SELECT l FROM Loopexcutelog l"),
    @NamedQuery(name = "Loopexcutelog.findByDatexcute", query = "SELECT l FROM Loopexcutelog l WHERE l.datexcute = :datexcute"),
    @NamedQuery(name = "Loopexcutelog.findByNumrequestid", query = "SELECT l FROM Loopexcutelog l WHERE l.numrequestid = :numrequestid"),
    @NamedQuery(name = "Loopexcutelog.findByVc2testresult", query = "SELECT l FROM Loopexcutelog l WHERE l.vc2testresult = :vc2testresult"),
    @NamedQuery(name = "Loopexcutelog.findByVc2testuri", query = "SELECT l FROM Loopexcutelog l WHERE l.vc2testuri = :vc2testuri"),
    @NamedQuery(name = "Loopexcutelog.findByNumduration", query = "SELECT l FROM Loopexcutelog l WHERE l.numduration = :numduration"),
    @NamedQuery(name = "Loopexcutelog.findByNumpredelayed", query = "SELECT l FROM Loopexcutelog l WHERE l.numpredelayed = :numpredelayed"),
    @NamedQuery(name = "Loopexcutelog.findByNumretrycount", query = "SELECT l FROM Loopexcutelog l WHERE l.numretrycount = :numretrycount"),
    @NamedQuery(name = "Loopexcutelog.findByNumstatuscode", query = "SELECT l FROM Loopexcutelog l WHERE l.numstatuscode = :numstatuscode"),
    @NamedQuery(name = "Loopexcutelog.findByNumexecutelogid", query = "SELECT l FROM Loopexcutelog l WHERE l.numexecutelogid = :numexecutelogid")})
public class Loopexcutelog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "datexcute", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datexcute;
    @Basic(optional = false)
    @Column(name = "numrequestid", nullable = false)
    private int numrequestid;
    @Basic(optional = false)
    @Column(name = "vc2testresult", nullable = false, length = 100)
    private String vc2testresult;
    @Basic(optional = false)
    @Column(name = "vc2testuri", nullable = false, length = 200)
    private String vc2testuri;
    @Basic(optional = false)
    @Column(name = "numduration", nullable = false)
    private int numduration;
    @Basic(optional = false)
    @Column(name = "numpredelayed", nullable = false)
    private int numpredelayed;
    @Basic(optional = false)
    @Column(name = "numretrycount", nullable = false)
    private int numretrycount;
    @Basic(optional = false)
    @Column(name = "numstatuscode", nullable = false)
    private int numstatuscode;
    @Lob
    @Column(name = "blobrequest")
    private byte[] blobrequest;
    @Lob
    @Column(name = "blobresponse")
    private byte[] blobresponse;
    @Id
    @Basic(optional = false)
    @Column(name = "numexecutelogid", nullable = false)
    private Long numexecutelogid;

    public Loopexcutelog() {
    }

    public Loopexcutelog(Long numexecutelogid) {
        this.numexecutelogid = numexecutelogid;
    }

    public Loopexcutelog(Long numexecutelogid, Date datexcute, int numrequestid, String vc2testresult, String vc2testuri, int numduration, int numpredelayed, int numretrycount, int numstatuscode) {
        this.numexecutelogid = numexecutelogid;
        this.datexcute = datexcute;
        this.numrequestid = numrequestid;
        this.vc2testresult = vc2testresult;
        this.vc2testuri = vc2testuri;
        this.numduration = numduration;
        this.numpredelayed = numpredelayed;
        this.numretrycount = numretrycount;
        this.numstatuscode = numstatuscode;
    }

    public Date getDatexcute() {
        return datexcute;
    }

    public void setDatexcute(Date datexcute) {
        this.datexcute = datexcute;
    }

    public int getNumrequestid() {
        return numrequestid;
    }

    public void setNumrequestid(int numrequestid) {
        this.numrequestid = numrequestid;
    }

    public String getVc2testresult() {
        return vc2testresult;
    }

    public void setVc2testresult(String vc2testresult) {
        this.vc2testresult = vc2testresult;
    }

    public String getVc2testuri() {
        return vc2testuri;
    }

    public void setVc2testuri(String vc2testuri) {
        this.vc2testuri = vc2testuri;
    }

    public int getNumduration() {
        return numduration;
    }

    public void setNumduration(int numduration) {
        this.numduration = numduration;
    }

    public int getNumpredelayed() {
        return numpredelayed;
    }

    public void setNumpredelayed(int numpredelayed) {
        this.numpredelayed = numpredelayed;
    }

    public int getNumretrycount() {
        return numretrycount;
    }

    public void setNumretrycount(int numretrycount) {
        this.numretrycount = numretrycount;
    }

    public int getNumstatuscode() {
        return numstatuscode;
    }

    public void setNumstatuscode(int numstatuscode) {
        this.numstatuscode = numstatuscode;
    }

    public byte[] getBlobrequest() {
        return blobrequest;
    }

    public void setBlobrequest(byte[] blobrequest) {
        this.blobrequest = blobrequest;
    }

    public byte[] getBlobresponse() {
        return blobresponse;
    }

    public void setBlobresponse(byte[] blobresponse) {
        this.blobresponse = blobresponse;
    }

    public Long getNumexecutelogid() {
        return numexecutelogid;
    }

    public void setNumexecutelogid(Long numexecutelogid) {
        this.numexecutelogid = numexecutelogid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numexecutelogid != null ? numexecutelogid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loopexcutelog)) {
            return false;
        }
        Loopexcutelog other = (Loopexcutelog) object;
        if ((this.numexecutelogid == null && other.numexecutelogid != null) || (this.numexecutelogid != null && !this.numexecutelogid.equals(other.numexecutelogid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Loopexcutelog[numexecutelogid=" + numexecutelogid + "]";
    }

}
