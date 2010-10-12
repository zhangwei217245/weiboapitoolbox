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
@Table(name = "tgroup")
@NamedQueries({
    @NamedQuery(name = "Tgroup.findAll", query = "SELECT t FROM Tgroup t"),
    @NamedQuery(name = "Tgroup.findByNumgroupid", query = "SELECT t FROM Tgroup t WHERE t.numgroupid = :numgroupid"),
    @NamedQuery(name = "Tgroup.findByVc2groupname", query = "SELECT t FROM Tgroup t WHERE t.vc2groupname = :vc2groupname")})
public class Tgroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numgroupid", nullable = false)
    private Integer numgroupid;
    @Column(name = "vc2groupname", length = 50)
    private String vc2groupname;

    public Tgroup() {
    }

    public Tgroup(Integer numgroupid) {
        this.numgroupid = numgroupid;
    }

    public Integer getNumgroupid() {
        return numgroupid;
    }

    public void setNumgroupid(Integer numgroupid) {
        this.numgroupid = numgroupid;
    }

    public String getVc2groupname() {
        return vc2groupname;
    }

    public void setVc2groupname(String vc2groupname) {
        this.vc2groupname = vc2groupname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numgroupid != null ? numgroupid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tgroup)) {
            return false;
        }
        Tgroup other = (Tgroup) object;
        if ((this.numgroupid == null && other.numgroupid != null) || (this.numgroupid != null && !this.numgroupid.equals(other.numgroupid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Tgroup[numgroupid=" + numgroupid + "]";
    }

}
