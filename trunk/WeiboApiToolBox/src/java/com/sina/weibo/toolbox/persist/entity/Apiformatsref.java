/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.persist.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "apiformatsref")
@NamedQueries({
    @NamedQuery(name = "Apiformatsref.findAll", query = "SELECT a FROM Apiformatsref a"),
    @NamedQuery(name = "Apiformatsref.findByNumformatrefid", query = "SELECT a FROM Apiformatsref a WHERE a.numformatrefid = :numformatrefid"),
    @NamedQuery(name = "Apiformatsref.findByVc2format", query = "SELECT a FROM Apiformatsref a WHERE a.vc2format = :vc2format"),
    @NamedQuery(name = "Apiformatsref.findByNumformatcode", query = "SELECT a FROM Apiformatsref a WHERE a.numformatcode = :numformatcode"),
    @NamedQuery(name = "Apiformatsref.findByNumapiid", query = "SELECT a FROM Apiformatsref a WHERE a.numapiid = :numapiid")})
public class Apiformatsref implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numformatrefid", nullable = false)
    private Integer numformatrefid;
    @Basic(optional = false)
    @Column(name = "vc2format", nullable = false, length = 20)
    private String vc2format;
    @Basic(optional = false)
    @Column(name = "numformatcode", nullable = false)
    private int numformatcode;
    @Basic(optional = false)
    @Column(name = "numapiid", nullable = false)
    private int numapiid;

    public Apiformatsref() {
    }

    public Apiformatsref(Integer numformatrefid) {
        this.numformatrefid = numformatrefid;
    }

    public Apiformatsref(Integer numformatrefid, String vc2format, int numformatcode, int numapiid) {
        this.numformatrefid = numformatrefid;
        this.vc2format = vc2format;
        this.numformatcode = numformatcode;
        this.numapiid = numapiid;
    }

    public Integer getNumformatrefid() {
        return numformatrefid;
    }

    public void setNumformatrefid(Integer numformatrefid) {
        this.numformatrefid = numformatrefid;
    }

    public String getVc2format() {
        return vc2format;
    }

    public void setVc2format(String vc2format) {
        this.vc2format = vc2format;
    }

    public int getNumformatcode() {
        return numformatcode;
    }

    public void setNumformatcode(int numformatcode) {
        this.numformatcode = numformatcode;
    }

    public int getNumapiid() {
        return numapiid;
    }

    public void setNumapiid(int numapiid) {
        this.numapiid = numapiid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numformatrefid != null ? numformatrefid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apiformatsref)) {
            return false;
        }
        Apiformatsref other = (Apiformatsref) object;
        if ((this.numformatrefid == null && other.numformatrefid != null) || (this.numformatrefid != null && !this.numformatrefid.equals(other.numformatrefid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.persist.entity.Apiformatsref[numformatrefid=" + numformatrefid + "]";
    }

}
