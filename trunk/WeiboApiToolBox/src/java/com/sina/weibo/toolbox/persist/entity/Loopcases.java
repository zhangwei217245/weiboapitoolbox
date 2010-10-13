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
@Table(name = "loopcases")
@NamedQueries({
    @NamedQuery(name = "Loopcases.findAll", query = "SELECT l FROM Loopcases l"),
    @NamedQuery(name = "Loopcases.findByNumloopcaseid", query = "SELECT l FROM Loopcases l WHERE l.numloopcaseid = :numloopcaseid"),
    @NamedQuery(name = "Loopcases.findByVc2format", query = "SELECT l FROM Loopcases l WHERE l.vc2format = :vc2format"),
    @NamedQuery(name = "Loopcases.findByVc2priority", query = "SELECT l FROM Loopcases l WHERE l.vc2priority = :vc2priority"),
    @NamedQuery(name = "Loopcases.findByVc2desc", query = "SELECT l FROM Loopcases l WHERE l.vc2desc = :vc2desc"),
    @NamedQuery(name = "Loopcases.findByNumconfigid", query = "SELECT l FROM Loopcases l WHERE l.numconfigid = :numconfigid")})
public class Loopcases implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numloopcaseid", nullable = false)
    private Integer numloopcaseid;
    @Basic(optional = false)
    @Column(name = "vc2format", nullable = false, length = 20)
    private String vc2format;
    @Basic(optional = false)
    @Column(name = "vc2priority", nullable = false, length = 10)
    private String vc2priority;
    @Column(name = "vc2desc", length = 400)
    private String vc2desc;
    @Column(name = "numconfigid")
    private Integer numconfigid;

    public Loopcases() {
    }

    public Loopcases(Integer numloopcaseid) {
        this.numloopcaseid = numloopcaseid;
    }

    public Loopcases(Integer numloopcaseid, String vc2format, String vc2priority) {
        this.numloopcaseid = numloopcaseid;
        this.vc2format = vc2format;
        this.vc2priority = vc2priority;
    }

    public Integer getNumloopcaseid() {
        return numloopcaseid;
    }

    public void setNumloopcaseid(Integer numloopcaseid) {
        this.numloopcaseid = numloopcaseid;
    }

    public String getVc2format() {
        return vc2format;
    }

    public void setVc2format(String vc2format) {
        this.vc2format = vc2format;
    }

    public String getVc2priority() {
        return vc2priority;
    }

    public void setVc2priority(String vc2priority) {
        this.vc2priority = vc2priority;
    }

    public String getVc2desc() {
        return vc2desc;
    }

    public void setVc2desc(String vc2desc) {
        this.vc2desc = vc2desc;
    }

    public Integer getNumconfigid() {
        return numconfigid;
    }

    public void setNumconfigid(Integer numconfigid) {
        this.numconfigid = numconfigid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numloopcaseid != null ? numloopcaseid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loopcases)) {
            return false;
        }
        Loopcases other = (Loopcases) object;
        if ((this.numloopcaseid == null && other.numloopcaseid != null) || (this.numloopcaseid != null && !this.numloopcaseid.equals(other.numloopcaseid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.persist.entity.Loopcases[numloopcaseid=" + numloopcaseid + "]";
    }

}
