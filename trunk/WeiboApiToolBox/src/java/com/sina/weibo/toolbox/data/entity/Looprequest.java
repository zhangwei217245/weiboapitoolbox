/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.data.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "looprequest")
@NamedQueries({
    @NamedQuery(name = "Looprequest.findAll", query = "SELECT l FROM Looprequest l"),
    @NamedQuery(name = "Looprequest.findByNumrequestid", query = "SELECT l FROM Looprequest l WHERE l.numrequestid = :numrequestid"),
    @NamedQuery(name = "Looprequest.findByVc2classname", query = "SELECT l FROM Looprequest l WHERE l.vc2classname = :vc2classname"),
    @NamedQuery(name = "Looprequest.findByNumdelayedMills", query = "SELECT l FROM Looprequest l WHERE l.numdelayedMills = :numdelayedMills"),
    @NamedQuery(name = "Looprequest.findByVc2requestdesc", query = "SELECT l FROM Looprequest l WHERE l.vc2requestdesc = :vc2requestdesc"),
    @NamedQuery(name = "Looprequest.findByVc2url", query = "SELECT l FROM Looprequest l WHERE l.vc2url = :vc2url"),
    @NamedQuery(name = "Looprequest.findByVc2method", query = "SELECT l FROM Looprequest l WHERE l.vc2method = :vc2method"),
    @NamedQuery(name = "Looprequest.findByVc2authtype", query = "SELECT l FROM Looprequest l WHERE l.vc2authtype = :vc2authtype"),
    @NamedQuery(name = "Looprequest.findByNumaccountid", query = "SELECT l FROM Looprequest l WHERE l.numaccountid = :numaccountid"),
    @NamedQuery(name = "Looprequest.findByVc2enctype", query = "SELECT l FROM Looprequest l WHERE l.vc2enctype = :vc2enctype"),
    @NamedQuery(name = "Looprequest.findByNumloopcaseid", query = "SELECT l FROM Looprequest l WHERE l.numloopcaseid = :numloopcaseid")})
public class Looprequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numrequestid", nullable = false)
    private Integer numrequestid;
    @Column(name = "vc2classname", length = 200)
    private String vc2classname;
    @Column(name = "numdelayedMills")
    private Integer numdelayedMills;
    @Column(name = "vc2requestdesc", length = 200)
    private String vc2requestdesc;
    @Column(name = "vc2url", length = 50)
    private String vc2url;
    @Column(name = "vc2method", length = 10)
    private String vc2method;
    @Column(name = "vc2authtype", length = 10)
    private String vc2authtype;
    @Column(name = "numaccountid")
    private Integer numaccountid;
    @Column(name = "vc2enctype", length = 100)
    private String vc2enctype;
    @Basic(optional = false)
    @Column(name = "numloopcaseid", nullable = false)
    private int numloopcaseid;

    public Looprequest() {
    }

    public Looprequest(Integer numrequestid) {
        this.numrequestid = numrequestid;
    }

    public Looprequest(Integer numrequestid, int numloopcaseid) {
        this.numrequestid = numrequestid;
        this.numloopcaseid = numloopcaseid;
    }

    public Integer getNumrequestid() {
        return numrequestid;
    }

    public void setNumrequestid(Integer numrequestid) {
        this.numrequestid = numrequestid;
    }

    public String getVc2classname() {
        return vc2classname;
    }

    public void setVc2classname(String vc2classname) {
        this.vc2classname = vc2classname;
    }

    public Integer getNumdelayedMills() {
        return numdelayedMills;
    }

    public void setNumdelayedMills(Integer numdelayedMills) {
        this.numdelayedMills = numdelayedMills;
    }

    public String getVc2requestdesc() {
        return vc2requestdesc;
    }

    public void setVc2requestdesc(String vc2requestdesc) {
        this.vc2requestdesc = vc2requestdesc;
    }

    public String getVc2url() {
        return vc2url;
    }

    public void setVc2url(String vc2url) {
        this.vc2url = vc2url;
    }

    public String getVc2method() {
        return vc2method;
    }

    public void setVc2method(String vc2method) {
        this.vc2method = vc2method;
    }

    public String getVc2authtype() {
        return vc2authtype;
    }

    public void setVc2authtype(String vc2authtype) {
        this.vc2authtype = vc2authtype;
    }

    public Integer getNumaccountid() {
        return numaccountid;
    }

    public void setNumaccountid(Integer numaccountid) {
        this.numaccountid = numaccountid;
    }

    public String getVc2enctype() {
        return vc2enctype;
    }

    public void setVc2enctype(String vc2enctype) {
        this.vc2enctype = vc2enctype;
    }

    public int getNumloopcaseid() {
        return numloopcaseid;
    }

    public void setNumloopcaseid(int numloopcaseid) {
        this.numloopcaseid = numloopcaseid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numrequestid != null ? numrequestid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Looprequest)) {
            return false;
        }
        Looprequest other = (Looprequest) object;
        if ((this.numrequestid == null && other.numrequestid != null) || (this.numrequestid != null && !this.numrequestid.equals(other.numrequestid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Looprequest[numrequestid=" + numrequestid + "]";
    }

}
