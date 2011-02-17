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
@Table(name = "tcategory")
@NamedQueries({
    @NamedQuery(name = "Tcategory.findAll", query = "SELECT t FROM Tcategory t"),
    @NamedQuery(name = "Tcategory.findByNumcateid", query = "SELECT t FROM Tcategory t WHERE t.numcateid = :numcateid"),
    @NamedQuery(name = "Tcategory.findByVc2catename", query = "SELECT t FROM Tcategory t WHERE t.vc2catename = :vc2catename"),
    @NamedQuery(name = "Tcategory.findByVc2catedesc", query = "SELECT t FROM Tcategory t WHERE t.vc2catedesc = :vc2catedesc"),
    @NamedQuery(name = "Tcategory.findByVc2catehref", query = "SELECT t FROM Tcategory t WHERE t.vc2catehref = :vc2catehref"),
    @NamedQuery(name = "Tcategory.findByVc2cateimg", query = "SELECT t FROM Tcategory t WHERE t.vc2cateimg = :vc2cateimg"),
    @NamedQuery(name = "Tcategory.findByNumenable", query = "SELECT t FROM Tcategory t WHERE t.numenable = :numenable"),
    @NamedQuery(name = "Tcategory.findByNumindex", query = "SELECT t FROM Tcategory t WHERE t.numindex = :numindex")})
public class Tcategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numcateid", nullable = false)
    private Integer numcateid;
    @Basic(optional = false)
    @Column(name = "vc2catename", nullable = false, length = 100)
    private String vc2catename;
    @Basic(optional = false)
    @Column(name = "vc2catedesc", nullable = false, length = 500)
    private String vc2catedesc;
    @Column(name = "vc2catehref", length = 45)
    private String vc2catehref;
    @Column(name = "vc2cateimg", length = 2000)
    private String vc2cateimg;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;
    @Basic(optional = false)
    @Column(name = "numindex", nullable = false)
    private int numindex;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numcateid")
    private Set<Tmenuitem> tmenuitemSet;

    public Tcategory() {
    }

    public Tcategory(Integer numcateid) {
        this.numcateid = numcateid;
    }

    public Tcategory(Integer numcateid, String vc2catename, String vc2catedesc, int numenable, int numindex) {
        this.numcateid = numcateid;
        this.vc2catename = vc2catename;
        this.vc2catedesc = vc2catedesc;
        this.numenable = numenable;
        this.numindex = numindex;
    }

    public Integer getNumcateid() {
        return numcateid;
    }

    public void setNumcateid(Integer numcateid) {
        this.numcateid = numcateid;
    }

    public String getVc2catename() {
        return vc2catename;
    }

    public void setVc2catename(String vc2catename) {
        this.vc2catename = vc2catename;
    }

    public String getVc2catedesc() {
        return vc2catedesc;
    }

    public void setVc2catedesc(String vc2catedesc) {
        this.vc2catedesc = vc2catedesc;
    }

    public String getVc2catehref() {
        return vc2catehref;
    }

    public void setVc2catehref(String vc2catehref) {
        this.vc2catehref = vc2catehref;
    }

    public String getVc2cateimg() {
        return vc2cateimg;
    }

    public void setVc2cateimg(String vc2cateimg) {
        this.vc2cateimg = vc2cateimg;
    }

    public int getNumenable() {
        return numenable;
    }

    public void setNumenable(int numenable) {
        this.numenable = numenable;
    }

    public int getNumindex() {
        return numindex;
    }

    public void setNumindex(int numindex) {
        this.numindex = numindex;
    }

    public Set<Tmenuitem> getTmenuitemSet() {
        return tmenuitemSet;
    }

    public void setTmenuitemSet(Set<Tmenuitem> tmenuitemSet) {
        this.tmenuitemSet = tmenuitemSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numcateid != null ? numcateid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tcategory)) {
            return false;
        }
        Tcategory other = (Tcategory) object;
        if ((this.numcateid == null && other.numcateid != null) || (this.numcateid != null && !this.numcateid.equals(other.numcateid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Tcategory[numcateid=" + numcateid + "]";
    }

}
