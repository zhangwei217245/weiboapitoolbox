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
@Table(name = "tbuttons")
@NamedQueries({
    @NamedQuery(name = "Tbuttons.findAll", query = "SELECT t FROM Tbuttons t"),
    @NamedQuery(name = "Tbuttons.findByNumbuttonid", query = "SELECT t FROM Tbuttons t WHERE t.numbuttonid = :numbuttonid"),
    @NamedQuery(name = "Tbuttons.findByVc2label", query = "SELECT t FROM Tbuttons t WHERE t.vc2label = :vc2label"),
    @NamedQuery(name = "Tbuttons.findByVc2imgurl", query = "SELECT t FROM Tbuttons t WHERE t.vc2imgurl = :vc2imgurl"),
    @NamedQuery(name = "Tbuttons.findByVc2href", query = "SELECT t FROM Tbuttons t WHERE t.vc2href = :vc2href"),
    @NamedQuery(name = "Tbuttons.findByNumgroupid", query = "SELECT t FROM Tbuttons t WHERE t.numgroupid = :numgroupid")})
public class Tbuttons implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numbuttonid", nullable = false)
    private Integer numbuttonid;
    @Column(name = "vc2label", length = 50)
    private String vc2label;
    @Column(name = "vc2imgurl", length = 200)
    private String vc2imgurl;
    @Column(name = "vc2href", length = 200)
    private String vc2href;
    @Basic(optional = false)
    @Column(name = "numgroupid", nullable = false)
    private int numgroupid;

    public Tbuttons() {
    }

    public Tbuttons(Integer numbuttonid) {
        this.numbuttonid = numbuttonid;
    }

    public Tbuttons(Integer numbuttonid, int numgroupid) {
        this.numbuttonid = numbuttonid;
        this.numgroupid = numgroupid;
    }

    public Integer getNumbuttonid() {
        return numbuttonid;
    }

    public void setNumbuttonid(Integer numbuttonid) {
        this.numbuttonid = numbuttonid;
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

    public int getNumgroupid() {
        return numgroupid;
    }

    public void setNumgroupid(int numgroupid) {
        this.numgroupid = numgroupid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numbuttonid != null ? numbuttonid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tbuttons)) {
            return false;
        }
        Tbuttons other = (Tbuttons) object;
        if ((this.numbuttonid == null && other.numbuttonid != null) || (this.numbuttonid != null && !this.numbuttonid.equals(other.numbuttonid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Tbuttons[numbuttonid=" + numbuttonid + "]";
    }

}
