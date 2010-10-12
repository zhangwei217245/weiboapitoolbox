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
@Table(name = "reqparam")
@NamedQueries({
    @NamedQuery(name = "Reqparam.findAll", query = "SELECT r FROM Reqparam r"),
    @NamedQuery(name = "Reqparam.findByNumrequestid", query = "SELECT r FROM Reqparam r WHERE r.numrequestid = :numrequestid"),
    @NamedQuery(name = "Reqparam.findByVc2paramname", query = "SELECT r FROM Reqparam r WHERE r.vc2paramname = :vc2paramname"),
    @NamedQuery(name = "Reqparam.findByVc2paramvalue", query = "SELECT r FROM Reqparam r WHERE r.vc2paramvalue = :vc2paramvalue"),
    @NamedQuery(name = "Reqparam.findByNumreqparamid", query = "SELECT r FROM Reqparam r WHERE r.numreqparamid = :numreqparamid")})
public class Reqparam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "numrequestid", nullable = false)
    private int numrequestid;
    @Basic(optional = false)
    @Column(name = "vc2paramname", nullable = false, length = 100)
    private String vc2paramname;
    @Basic(optional = false)
    @Column(name = "vc2paramvalue", nullable = false, length = 100)
    private String vc2paramvalue;
    @Id
    @Basic(optional = false)
    @Column(name = "numreqparamid", nullable = false)
    private Long numreqparamid;

    public Reqparam() {
    }

    public Reqparam(Long numreqparamid) {
        this.numreqparamid = numreqparamid;
    }

    public Reqparam(Long numreqparamid, int numrequestid, String vc2paramname, String vc2paramvalue) {
        this.numreqparamid = numreqparamid;
        this.numrequestid = numrequestid;
        this.vc2paramname = vc2paramname;
        this.vc2paramvalue = vc2paramvalue;
    }

    public int getNumrequestid() {
        return numrequestid;
    }

    public void setNumrequestid(int numrequestid) {
        this.numrequestid = numrequestid;
    }

    public String getVc2paramname() {
        return vc2paramname;
    }

    public void setVc2paramname(String vc2paramname) {
        this.vc2paramname = vc2paramname;
    }

    public String getVc2paramvalue() {
        return vc2paramvalue;
    }

    public void setVc2paramvalue(String vc2paramvalue) {
        this.vc2paramvalue = vc2paramvalue;
    }

    public Long getNumreqparamid() {
        return numreqparamid;
    }

    public void setNumreqparamid(Long numreqparamid) {
        this.numreqparamid = numreqparamid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numreqparamid != null ? numreqparamid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reqparam)) {
            return false;
        }
        Reqparam other = (Reqparam) object;
        if ((this.numreqparamid == null && other.numreqparamid != null) || (this.numreqparamid != null && !this.numreqparamid.equals(other.numreqparamid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Reqparam[numreqparamid=" + numreqparamid + "]";
    }

}
