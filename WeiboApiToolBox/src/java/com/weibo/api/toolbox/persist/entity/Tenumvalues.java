/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.persist.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "tenumvalues")
@NamedQueries({
    @NamedQuery(name = "Tenumvalues.findAll", query = "SELECT t FROM Tenumvalues t"),
    @NamedQuery(name = "Tenumvalues.findByNumenumvalueid", query = "SELECT t FROM Tenumvalues t WHERE t.numenumvalueid = :numenumvalueid"),
    @NamedQuery(name = "Tenumvalues.findByVc2enumvalue", query = "SELECT t FROM Tenumvalues t WHERE t.vc2enumvalue = :vc2enumvalue"),
    @NamedQuery(name = "Tenumvalues.findByVc2enumdesc", query = "SELECT t FROM Tenumvalues t WHERE t.vc2enumdesc = :vc2enumdesc")})
public class Tenumvalues implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numenumvalueid", nullable = false)
    private Integer numenumvalueid;
    @Basic(optional = false)
    @Column(name = "vc2enumvalue", nullable = false, length = 200)
    private String vc2enumvalue;
    @Basic(optional = false)
    @Column(name = "vc2enumdesc", nullable = false, length = 2000)
    private String vc2enumdesc;
    @JoinColumn(name = "numenumgroupid", referencedColumnName = "numenumgroupid", nullable = false)
    @ManyToOne(optional = false)
    private Tenumgroup numenumgroupid;

    public Tenumvalues() {
    }

    public Tenumvalues(Integer numenumvalueid) {
        this.numenumvalueid = numenumvalueid;
    }

    public Tenumvalues(Integer numenumvalueid, String vc2enumvalue, String vc2enumdesc) {
        this.numenumvalueid = numenumvalueid;
        this.vc2enumvalue = vc2enumvalue;
        this.vc2enumdesc = vc2enumdesc;
    }

    public Integer getNumenumvalueid() {
        return numenumvalueid;
    }

    public void setNumenumvalueid(Integer numenumvalueid) {
        this.numenumvalueid = numenumvalueid;
    }

    public String getVc2enumvalue() {
        return vc2enumvalue;
    }

    public void setVc2enumvalue(String vc2enumvalue) {
        this.vc2enumvalue = vc2enumvalue;
    }

    public String getVc2enumdesc() {
        return vc2enumdesc;
    }

    public void setVc2enumdesc(String vc2enumdesc) {
        this.vc2enumdesc = vc2enumdesc;
    }

    public Tenumgroup getNumenumgroupid() {
        return numenumgroupid;
    }

    public void setNumenumgroupid(Tenumgroup numenumgroupid) {
        this.numenumgroupid = numenumgroupid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numenumvalueid != null ? numenumvalueid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tenumvalues)) {
            return false;
        }
        Tenumvalues other = (Tenumvalues) object;
        if ((this.numenumvalueid == null && other.numenumvalueid != null) || (this.numenumvalueid != null && !this.numenumvalueid.equals(other.numenumvalueid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zkoss.zkdemo.persist.entity.Tenumvalues[numenumvalueid=" + numenumvalueid + "]";
    }

}
