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
@Table(name = "reqassert")
@NamedQueries({
    @NamedQuery(name = "Reqassert.findAll", query = "SELECT r FROM Reqassert r"),
    @NamedQuery(name = "Reqassert.findByNumrequestid", query = "SELECT r FROM Reqassert r WHERE r.numrequestid = :numrequestid"),
    @NamedQuery(name = "Reqassert.findByVc2path", query = "SELECT r FROM Reqassert r WHERE r.vc2path = :vc2path"),
    @NamedQuery(name = "Reqassert.findByVc2pathtype", query = "SELECT r FROM Reqassert r WHERE r.vc2pathtype = :vc2pathtype"),
    @NamedQuery(name = "Reqassert.findByVc2relation", query = "SELECT r FROM Reqassert r WHERE r.vc2relation = :vc2relation"),
    @NamedQuery(name = "Reqassert.findByVc2expvalueexp", query = "SELECT r FROM Reqassert r WHERE r.vc2expvalueexp = :vc2expvalueexp"),
    @NamedQuery(name = "Reqassert.findByVc2errmsg", query = "SELECT r FROM Reqassert r WHERE r.vc2errmsg = :vc2errmsg"),
    @NamedQuery(name = "Reqassert.findByNumreqassertid", query = "SELECT r FROM Reqassert r WHERE r.numreqassertid = :numreqassertid")})
public class Reqassert implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "numrequestid", nullable = false)
    private int numrequestid;
    @Basic(optional = false)
    @Column(name = "vc2path", nullable = false, length = 100)
    private String vc2path;
    @Basic(optional = false)
    @Column(name = "vc2pathtype", nullable = false, length = 1)
    private String vc2pathtype;
    @Basic(optional = false)
    @Column(name = "vc2relation", nullable = false, length = 10)
    private String vc2relation;
    @Basic(optional = false)
    @Column(name = "vc2expvalueexp", nullable = false, length = 100)
    private String vc2expvalueexp;
    @Basic(optional = false)
    @Column(name = "vc2errmsg", nullable = false, length = 100)
    private String vc2errmsg;
    @Id
    @Basic(optional = false)
    @Column(name = "numreqassertid", nullable = false)
    private Long numreqassertid;

    public Reqassert() {
    }

    public Reqassert(Long numreqassertid) {
        this.numreqassertid = numreqassertid;
    }

    public Reqassert(Long numreqassertid, int numrequestid, String vc2path, String vc2pathtype, String vc2relation, String vc2expvalueexp, String vc2errmsg) {
        this.numreqassertid = numreqassertid;
        this.numrequestid = numrequestid;
        this.vc2path = vc2path;
        this.vc2pathtype = vc2pathtype;
        this.vc2relation = vc2relation;
        this.vc2expvalueexp = vc2expvalueexp;
        this.vc2errmsg = vc2errmsg;
    }

    public int getNumrequestid() {
        return numrequestid;
    }

    public void setNumrequestid(int numrequestid) {
        this.numrequestid = numrequestid;
    }

    public String getVc2path() {
        return vc2path;
    }

    public void setVc2path(String vc2path) {
        this.vc2path = vc2path;
    }

    public String getVc2pathtype() {
        return vc2pathtype;
    }

    public void setVc2pathtype(String vc2pathtype) {
        this.vc2pathtype = vc2pathtype;
    }

    public String getVc2relation() {
        return vc2relation;
    }

    public void setVc2relation(String vc2relation) {
        this.vc2relation = vc2relation;
    }

    public String getVc2expvalueexp() {
        return vc2expvalueexp;
    }

    public void setVc2expvalueexp(String vc2expvalueexp) {
        this.vc2expvalueexp = vc2expvalueexp;
    }

    public String getVc2errmsg() {
        return vc2errmsg;
    }

    public void setVc2errmsg(String vc2errmsg) {
        this.vc2errmsg = vc2errmsg;
    }

    public Long getNumreqassertid() {
        return numreqassertid;
    }

    public void setNumreqassertid(Long numreqassertid) {
        this.numreqassertid = numreqassertid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numreqassertid != null ? numreqassertid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reqassert)) {
            return false;
        }
        Reqassert other = (Reqassert) object;
        if ((this.numreqassertid == null && other.numreqassertid != null) || (this.numreqassertid != null && !this.numreqassertid.equals(other.numreqassertid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Reqassert[numreqassertid=" + numreqassertid + "]";
    }

}
