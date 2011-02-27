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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author X-Spirit
 */
@Entity
@Table(name = "tuser", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"vc2email"}),
    @UniqueConstraint(columnNames = {"vc2username"})})
public class Tuser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numuserid", nullable = false)
    private Integer numuserid;
    @Basic(optional = false)
    @Column(name = "vc2email", nullable = false, length = 200)
    private String vc2email;
    @Basic(optional = false)
    @Column(name = "vc2username", nullable = false, length = 200)
    private String vc2username;
    @Basic(optional = false)
    @Column(name = "vc2realname", nullable = false, length = 200)
    private String vc2realname;
    @Basic(optional = false)
    @Column(name = "vc2password", nullable = false, length = 100)
    private String vc2password;
    @Basic(optional = false)
    @Column(name = "vc2pwdstr", nullable = false, length = 100)
    private String vc2pwdstr;
    @Basic(optional = false)
    @Column(name = "vc2phone", nullable = false, length = 100)
    private String vc2phone;
    @Basic(optional = false)
    @Column(name = "vc2department", nullable = false, length = 200)
    private String vc2department;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;
    @Basic(optional = false)
    @Column(name = "numsupervisor", nullable = false)
    private int numsupervisor;
    @ManyToMany(mappedBy = "tuserSet",cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    private Set<Tgroup> tgroupSet = new HashSet<Tgroup>();

    public Tuser() {
    }

    public Tuser(Integer numuserid) {
        this.numuserid = numuserid;
    }

    public Tuser(Integer numuserid, String vc2email, String vc2username, String vc2realname, String vc2password, String vc2pwdstr, int numenable, int numsupervisor) {
        this.numuserid = numuserid;
        this.vc2email = vc2email;
        this.vc2username = vc2username;
        this.vc2realname = vc2realname;
        this.vc2password = vc2password;
        this.numenable = numenable;
        this.vc2pwdstr = vc2pwdstr;
        this.numsupervisor = numsupervisor;
    }

    public Integer getNumuserid() {
        return numuserid;
    }

    public void setNumuserid(Integer numuserid) {
        this.numuserid = numuserid;
    }

    public String getVc2email() {
        return vc2email;
    }

    public void setVc2email(String vc2email) {
        this.vc2email = vc2email;
    }

    public String getVc2username() {
        return vc2username;
    }

    public void setVc2username(String vc2username) {
        this.vc2username = vc2username;
    }

    public String getVc2realname() {
        return vc2realname;
    }

    public void setVc2realname(String vc2realname) {
        this.vc2realname = vc2realname;
    }

    public String getVc2password() {
        return vc2password;
    }

    public void setVc2password(String vc2password) {
        this.vc2password = vc2password;
    }

    public int getNumenable() {
        return numenable;
    }

    public void setNumenable(int numenable) {
        this.numenable = numenable;
    }

    public int getNumsupervisor() {
        return numsupervisor;
    }

    public void setNumsupervisor(int numsupervisor) {
        this.numsupervisor = numsupervisor;
    }

    public String getVc2department() {
        return vc2department;
    }

    public void setVc2department(String vc2department) {
        this.vc2department = vc2department;
    }

    public String getVc2phone() {
        return vc2phone;
    }

    public void setVc2phone(String vc2phone) {
        this.vc2phone = vc2phone;
    }

    public String getVc2pwdstr() {
        return vc2pwdstr;
    }

    public void setVc2pwdstr(String vc2pwdstr) {
        this.vc2pwdstr = vc2pwdstr;
    }

    public Set<Tgroup> getTgroupSet() {
        return tgroupSet;
    }

    public void setTgroupSet(Set<Tgroup> tgroupSet) {
        this.tgroupSet = tgroupSet;
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
        return "com.weibo.api.toolbox.persist.entity.Tuser[numuserid=" + numuserid + "]";
    }

}
