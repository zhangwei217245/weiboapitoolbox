/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.persist.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author X-Spirit
 */
@Entity
@Table(name = "baseurl")
public class Baseurl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numbaseurlid", nullable = false)
    private Integer numbaseurlid;
    @Basic(optional = false)
    @Column(name = "vc2baseurl", nullable = false, length = 500)
    private String vc2baseurl;
    @Column(name = "vc2desc", length = 1000)
    private String vc2desc;
    @Basic(optional = false)
    @Column(name = "numindex", nullable = false)
    private int numindex;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numbaseurlid")
    private Set<Tspec> tspecSet;

    public Baseurl() {
    }

    public Baseurl(Integer numbaseurlid) {
        this.numbaseurlid = numbaseurlid;
    }

    public Baseurl(Integer numbaseurlid, String vc2baseurl, int numindex) {
        this.numbaseurlid = numbaseurlid;
        this.vc2baseurl = vc2baseurl;
        this.numindex = numindex;
    }

    public Integer getNumbaseurlid() {
        return numbaseurlid;
    }

    public void setNumbaseurlid(Integer numbaseurlid) {
        this.numbaseurlid = numbaseurlid;
    }

    public String getVc2baseurl() {
        return vc2baseurl;
    }

    public void setVc2baseurl(String vc2baseurl) {
        this.vc2baseurl = vc2baseurl;
    }

    public String getVc2desc() {
        return vc2desc;
    }

    public void setVc2desc(String vc2desc) {
        this.vc2desc = vc2desc;
    }

    public int getNumindex() {
        return numindex;
    }

    public void setNumindex(int numindex) {
        this.numindex = numindex;
    }

    public Set<Tspec> getTspecSet() {
        return tspecSet;
    }

    public void setTspecSet(Set<Tspec> tspecSet) {
        this.tspecSet = tspecSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numbaseurlid != null ? numbaseurlid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Baseurl)) {
            return false;
        }
        Baseurl other = (Baseurl) object;
        if ((this.numbaseurlid == null && other.numbaseurlid != null) || (this.numbaseurlid != null && !this.numbaseurlid.equals(other.numbaseurlid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Baseurl[numbaseurlid=" + numbaseurlid + "]";
    }

}
