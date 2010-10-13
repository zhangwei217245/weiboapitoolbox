/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.persist.entity;

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
@Table(name = "reqvaluekept")
@NamedQueries({
    @NamedQuery(name = "Reqvaluekept.findAll", query = "SELECT r FROM Reqvaluekept r"),
    @NamedQuery(name = "Reqvaluekept.findByNumrequestid", query = "SELECT r FROM Reqvaluekept r WHERE r.numrequestid = :numrequestid"),
    @NamedQuery(name = "Reqvaluekept.findByVc2sessionkey", query = "SELECT r FROM Reqvaluekept r WHERE r.vc2sessionkey = :vc2sessionkey"),
    @NamedQuery(name = "Reqvaluekept.findByVc2valuepath", query = "SELECT r FROM Reqvaluekept r WHERE r.vc2valuepath = :vc2valuepath"),
    @NamedQuery(name = "Reqvaluekept.findByNumreqvaluekeptid", query = "SELECT r FROM Reqvaluekept r WHERE r.numreqvaluekeptid = :numreqvaluekeptid")})
public class Reqvaluekept implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "numrequestid", nullable = false)
    private int numrequestid;
    @Basic(optional = false)
    @Column(name = "vc2sessionkey", nullable = false, length = 50)
    private String vc2sessionkey;
    @Basic(optional = false)
    @Column(name = "vc2valuepath", nullable = false, length = 50)
    private String vc2valuepath;
    @Id
    @Basic(optional = false)
    @Column(name = "numreqvaluekeptid", nullable = false)
    private Long numreqvaluekeptid;

    public Reqvaluekept() {
    }

    public Reqvaluekept(Long numreqvaluekeptid) {
        this.numreqvaluekeptid = numreqvaluekeptid;
    }

    public Reqvaluekept(Long numreqvaluekeptid, int numrequestid, String vc2sessionkey, String vc2valuepath) {
        this.numreqvaluekeptid = numreqvaluekeptid;
        this.numrequestid = numrequestid;
        this.vc2sessionkey = vc2sessionkey;
        this.vc2valuepath = vc2valuepath;
    }

    public int getNumrequestid() {
        return numrequestid;
    }

    public void setNumrequestid(int numrequestid) {
        this.numrequestid = numrequestid;
    }

    public String getVc2sessionkey() {
        return vc2sessionkey;
    }

    public void setVc2sessionkey(String vc2sessionkey) {
        this.vc2sessionkey = vc2sessionkey;
    }

    public String getVc2valuepath() {
        return vc2valuepath;
    }

    public void setVc2valuepath(String vc2valuepath) {
        this.vc2valuepath = vc2valuepath;
    }

    public Long getNumreqvaluekeptid() {
        return numreqvaluekeptid;
    }

    public void setNumreqvaluekeptid(Long numreqvaluekeptid) {
        this.numreqvaluekeptid = numreqvaluekeptid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numreqvaluekeptid != null ? numreqvaluekeptid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reqvaluekept)) {
            return false;
        }
        Reqvaluekept other = (Reqvaluekept) object;
        if ((this.numreqvaluekeptid == null && other.numreqvaluekeptid != null) || (this.numreqvaluekeptid != null && !this.numreqvaluekeptid.equals(other.numreqvaluekeptid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.persist.entity.Reqvaluekept[numreqvaluekeptid=" + numreqvaluekeptid + "]";
    }

}
