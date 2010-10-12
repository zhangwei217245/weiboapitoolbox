/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.data.entity;

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
@Table(name = "apimethodref")
@NamedQueries({
    @NamedQuery(name = "Apimethodref.findAll", query = "SELECT a FROM Apimethodref a"),
    @NamedQuery(name = "Apimethodref.findByNummethodrefid", query = "SELECT a FROM Apimethodref a WHERE a.nummethodrefid = :nummethodrefid"),
    @NamedQuery(name = "Apimethodref.findByVc2method", query = "SELECT a FROM Apimethodref a WHERE a.vc2method = :vc2method"),
    @NamedQuery(name = "Apimethodref.findByNummethodcode", query = "SELECT a FROM Apimethodref a WHERE a.nummethodcode = :nummethodcode"),
    @NamedQuery(name = "Apimethodref.findByNumapiid", query = "SELECT a FROM Apimethodref a WHERE a.numapiid = :numapiid")})
public class Apimethodref implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "nummethodrefid", nullable = false)
    private Integer nummethodrefid;
    @Basic(optional = false)
    @Column(name = "vc2method", nullable = false, length = 20)
    private String vc2method;
    @Basic(optional = false)
    @Column(name = "nummethodcode", nullable = false)
    private int nummethodcode;
    @Basic(optional = false)
    @Column(name = "numapiid", nullable = false)
    private int numapiid;

    public Apimethodref() {
    }

    public Apimethodref(Integer nummethodrefid) {
        this.nummethodrefid = nummethodrefid;
    }

    public Apimethodref(Integer nummethodrefid, String vc2method, int nummethodcode, int numapiid) {
        this.nummethodrefid = nummethodrefid;
        this.vc2method = vc2method;
        this.nummethodcode = nummethodcode;
        this.numapiid = numapiid;
    }

    public Integer getNummethodrefid() {
        return nummethodrefid;
    }

    public void setNummethodrefid(Integer nummethodrefid) {
        this.nummethodrefid = nummethodrefid;
    }

    public String getVc2method() {
        return vc2method;
    }

    public void setVc2method(String vc2method) {
        this.vc2method = vc2method;
    }

    public int getNummethodcode() {
        return nummethodcode;
    }

    public void setNummethodcode(int nummethodcode) {
        this.nummethodcode = nummethodcode;
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
        hash += (nummethodrefid != null ? nummethodrefid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apimethodref)) {
            return false;
        }
        Apimethodref other = (Apimethodref) object;
        if ((this.nummethodrefid == null && other.nummethodrefid != null) || (this.nummethodrefid != null && !this.nummethodrefid.equals(other.nummethodrefid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Apimethodref[nummethodrefid=" + nummethodrefid + "]";
    }

}
