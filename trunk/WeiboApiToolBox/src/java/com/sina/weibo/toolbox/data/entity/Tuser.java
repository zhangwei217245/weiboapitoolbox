/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.data.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "tuser", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"vc2email"})})
@NamedQueries({
    @NamedQuery(name = "Tuser.findAll", query = "SELECT t FROM Tuser t"),
    @NamedQuery(name = "Tuser.findByNumuserid", query = "SELECT t FROM Tuser t WHERE t.numuserid = :numuserid"),
    @NamedQuery(name = "Tuser.findByVc2nickname", query = "SELECT t FROM Tuser t WHERE t.vc2nickname = :vc2nickname"),
    @NamedQuery(name = "Tuser.findByVc2email", query = "SELECT t FROM Tuser t WHERE t.vc2email = :vc2email"),
    @NamedQuery(name = "Tuser.findByVc2password", query = "SELECT t FROM Tuser t WHERE t.vc2password = :vc2password"),
    @NamedQuery(name = "Tuser.findByIsActive", query = "SELECT t FROM Tuser t WHERE t.isActive = :isActive"),
    @NamedQuery(name = "Tuser.findByIsSuper", query = "SELECT t FROM Tuser t WHERE t.isSuper = :isSuper"),
    @NamedQuery(name = "Tuser.findByDatlastlogin", query = "SELECT t FROM Tuser t WHERE t.datlastlogin = :datlastlogin"),
    @NamedQuery(name = "Tuser.findByDatjoined", query = "SELECT t FROM Tuser t WHERE t.datjoined = :datjoined"),
    @NamedQuery(name = "Tuser.findByNumgroupid", query = "SELECT t FROM Tuser t WHERE t.numgroupid = :numgroupid")})
public class Tuser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numuserid", nullable = false)
    private Integer numuserid;
    @Column(name = "vc2nickname", length = 50)
    private String vc2nickname;
    @Basic(optional = false)
    @Column(name = "vc2email", nullable = false, length = 200)
    private String vc2email;
    @Basic(optional = false)
    @Column(name = "vc2password", nullable = false, length = 50)
    private String vc2password;
    @Basic(optional = false)
    @Column(name = "is_active", nullable = false)
    private int isActive;
    @Basic(optional = false)
    @Column(name = "is_super", nullable = false)
    private int isSuper;
    @Column(name = "datlastlogin")
    @Temporal(TemporalType.DATE)
    private Date datlastlogin;
    @Column(name = "datjoined")
    @Temporal(TemporalType.DATE)
    private Date datjoined;
    @Basic(optional = false)
    @Column(name = "numgroupid", nullable = false)
    private int numgroupid;

    public Tuser() {
    }

    public Tuser(Integer numuserid) {
        this.numuserid = numuserid;
    }

    public Tuser(Integer numuserid, String vc2email, String vc2password, int isActive, int isSuper, int numgroupid) {
        this.numuserid = numuserid;
        this.vc2email = vc2email;
        this.vc2password = vc2password;
        this.isActive = isActive;
        this.isSuper = isSuper;
        this.numgroupid = numgroupid;
    }

    public Integer getNumuserid() {
        return numuserid;
    }

    public void setNumuserid(Integer numuserid) {
        this.numuserid = numuserid;
    }

    public String getVc2nickname() {
        return vc2nickname;
    }

    public void setVc2nickname(String vc2nickname) {
        this.vc2nickname = vc2nickname;
    }

    public String getVc2email() {
        return vc2email;
    }

    public void setVc2email(String vc2email) {
        this.vc2email = vc2email;
    }

    public String getVc2password() {
        return vc2password;
    }

    public void setVc2password(String vc2password) {
        this.vc2password = vc2password;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(int isSuper) {
        this.isSuper = isSuper;
    }

    public Date getDatlastlogin() {
        return datlastlogin;
    }

    public void setDatlastlogin(Date datlastlogin) {
        this.datlastlogin = datlastlogin;
    }

    public Date getDatjoined() {
        return datjoined;
    }

    public void setDatjoined(Date datjoined) {
        this.datjoined = datjoined;
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
        hash += (numuserid != null ? numuserid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tuser)) {
            return false;
        }
        Tuser other = (Tuser) object;
        if ((this.numuserid == null && other.numuserid != null) || (this.numuserid != null && !this.numuserid.equals(other.numuserid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Tuser[numuserid=" + numuserid + "]";
    }

}
