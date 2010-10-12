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
@Table(name = "tmenu")
@NamedQueries({
    @NamedQuery(name = "Tmenu.findAll", query = "SELECT t FROM Tmenu t"),
    @NamedQuery(name = "Tmenu.findByNummenuid", query = "SELECT t FROM Tmenu t WHERE t.nummenuid = :nummenuid"),
    @NamedQuery(name = "Tmenu.findByVc2label", query = "SELECT t FROM Tmenu t WHERE t.vc2label = :vc2label"),
    @NamedQuery(name = "Tmenu.findByVc2imgurl", query = "SELECT t FROM Tmenu t WHERE t.vc2imgurl = :vc2imgurl"),
    @NamedQuery(name = "Tmenu.findByVc2href", query = "SELECT t FROM Tmenu t WHERE t.vc2href = :vc2href"),
    @NamedQuery(name = "Tmenu.findByNumpmenuid", query = "SELECT t FROM Tmenu t WHERE t.numpmenuid = :numpmenuid"),
    @NamedQuery(name = "Tmenu.findByNumgroupid", query = "SELECT t FROM Tmenu t WHERE t.numgroupid = :numgroupid")})
public class Tmenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "nummenuid", nullable = false)
    private Integer nummenuid;
    @Basic(optional = false)
    @Column(name = "vc2label", nullable = false, length = 50)
    private String vc2label;
    @Column(name = "vc2imgurl", length = 200)
    private String vc2imgurl;
    @Column(name = "vc2href", length = 200)
    private String vc2href;
    @Column(name = "numpmenuid")
    private Integer numpmenuid;
    @Basic(optional = false)
    @Column(name = "numgroupid", nullable = false)
    private int numgroupid;

    public Tmenu() {
    }

    public Tmenu(Integer nummenuid) {
        this.nummenuid = nummenuid;
    }

    public Tmenu(Integer nummenuid, String vc2label, int numgroupid) {
        this.nummenuid = nummenuid;
        this.vc2label = vc2label;
        this.numgroupid = numgroupid;
    }

    public Integer getNummenuid() {
        return nummenuid;
    }

    public void setNummenuid(Integer nummenuid) {
        this.nummenuid = nummenuid;
    }

    public String getVc2label() {
        return vc2label;
    }

    public void setVc2label(String vc2label) {
        this.vc2label = vc2label;
    }

    public String getVc2imgurl() {
        return vc2imgurl;
    }

    public void setVc2imgurl(String vc2imgurl) {
        this.vc2imgurl = vc2imgurl;
    }

    public String getVc2href() {
        return vc2href;
    }

    public void setVc2href(String vc2href) {
        this.vc2href = vc2href;
    }

    public Integer getNumpmenuid() {
        return numpmenuid;
    }

    public void setNumpmenuid(Integer numpmenuid) {
        this.numpmenuid = numpmenuid;
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
        hash += (nummenuid != null ? nummenuid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tmenu)) {
            return false;
        }
        Tmenu other = (Tmenu) object;
        if ((this.nummenuid == null && other.nummenuid != null) || (this.nummenuid != null && !this.nummenuid.equals(other.nummenuid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Tmenu[nummenuid=" + nummenuid + "]";
    }

}
