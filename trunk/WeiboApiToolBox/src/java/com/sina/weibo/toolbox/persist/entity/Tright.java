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
@Table(name = "tright")
@NamedQueries({
    @NamedQuery(name = "Tright.findAll", query = "SELECT t FROM Tright t"),
    @NamedQuery(name = "Tright.findByNumrightid", query = "SELECT t FROM Tright t WHERE t.numrightid = :numrightid"),
    @NamedQuery(name = "Tright.findByVc2rightname", query = "SELECT t FROM Tright t WHERE t.vc2rightname = :vc2rightname"),
    @NamedQuery(name = "Tright.findByVc2path", query = "SELECT t FROM Tright t WHERE t.vc2path = :vc2path"),
    @NamedQuery(name = "Tright.findByNumgroupid", query = "SELECT t FROM Tright t WHERE t.numgroupid = :numgroupid")})
public class Tright implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numrightid", nullable = false)
    private Integer numrightid;
    @Basic(optional = false)
    @Column(name = "vc2rightname", nullable = false, length = 50)
    private String vc2rightname;
    @Basic(optional = false)
    @Column(name = "vc2path", nullable = false, length = 300)
    private String vc2path;
    @Basic(optional = false)
    @Column(name = "numgroupid", nullable = false)
    private int numgroupid;

    public Tright() {
    }

    public Tright(Integer numrightid) {
        this.numrightid = numrightid;
    }

    public Tright(Integer numrightid, String vc2rightname, String vc2path, int numgroupid) {
        this.numrightid = numrightid;
        this.vc2rightname = vc2rightname;
        this.vc2path = vc2path;
        this.numgroupid = numgroupid;
    }

    public Integer getNumrightid() {
        return numrightid;
    }

    public void setNumrightid(Integer numrightid) {
        this.numrightid = numrightid;
    }

    public String getVc2rightname() {
        return vc2rightname;
    }

    public void setVc2rightname(String vc2rightname) {
        this.vc2rightname = vc2rightname;
    }

    public String getVc2path() {
        return vc2path;
    }

    public void setVc2path(String vc2path) {
        this.vc2path = vc2path;
    }

    public int getNumgroupid() {
        return numgroupid;
    }

    public void setNumgroupid(int numgroupid) {
        this.numgroupid = numgroupid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numrightid != null ? numrightid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tright)) {
            return false;
        }
        Tright other = (Tright) object;
        if ((this.numrightid == null && other.numrightid != null) || (this.numrightid != null && !this.numrightid.equals(other.numrightid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.persist.entity.Tright[numrightid=" + numrightid + "]";
    }

}
