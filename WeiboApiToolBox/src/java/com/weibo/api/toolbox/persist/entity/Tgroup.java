/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.persist.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author X-Spirit
 */
@Entity
@Table(name = "tgroup")
public class Tgroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numgroupid", nullable = false)
    private Integer numgroupid;
    @Basic(optional = false)
    @Column(name = "vc2groupname", nullable = false, length = 200)
    private String vc2groupname;
    @Basic(optional = false)
    @Column(name = "vc2groupdesc", nullable = false, length = 200)
    private String vc2groupdesc;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;
    @JoinTable(name = "tgroup_menuitem_relation", joinColumns = {
        @JoinColumn(name = "numgroupid", referencedColumnName = "numgroupid", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "numitemid", referencedColumnName = "numitemid", nullable = false)})
    @ManyToMany(cascade = CascadeType.MERGE,fetch=FetchType.EAGER)
    private Set<Tmenuitem> tmenuitemSet = new HashSet<Tmenuitem>();
    @JoinTable(name = "tgroup_user_relation", joinColumns = {
        @JoinColumn(name = "numgroupid", referencedColumnName = "numgroupid", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "numuserid", referencedColumnName = "numuserid", nullable = false)})
    @ManyToMany(cascade = CascadeType.MERGE,fetch=FetchType.EAGER)
    private Set<Tuser> tuserSet = new HashSet<Tuser>();

    public Tgroup() {
    }

    public Tgroup(Integer numgroupid) {
        this.numgroupid = numgroupid;
    }

    public Tgroup(Integer numgroupid, String vc2groupname, String vc2groupdesc, int numenable) {
        this.numgroupid = numgroupid;
        this.vc2groupname = vc2groupname;
        this.vc2groupdesc = vc2groupdesc;
        this.numenable = numenable;
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

    public String getVc2groupdesc() {
        return vc2groupdesc;
    }

    public void setVc2groupdesc(String vc2groupdesc) {
        this.vc2groupdesc = vc2groupdesc;
    }

    public int getNumenable() {
        return numenable;
    }

    public void setNumenable(int numenable) {
        this.numenable = numenable;
    }

    public Set<Tmenuitem> getTmenuitemSet() {
        return tmenuitemSet;
    }

    public void setTmenuitemSet(Set<Tmenuitem> tmenuitemSet) {
        this.tmenuitemSet = tmenuitemSet;
    }

    public Set<Tuser> getTuserSet() {
        return tuserSet;
    }

    public void setTuserSet(Set<Tuser> tuserSet) {
        this.tuserSet = tuserSet;
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
        return "com.weibo.api.toolbox.persist.entity.Tgroup[numgroupid=" + numgroupid + "]";
    }

}
