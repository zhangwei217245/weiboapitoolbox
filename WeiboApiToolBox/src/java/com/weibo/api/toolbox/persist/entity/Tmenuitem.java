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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author X-Spirit
 */
@Entity
@Table(name = "tmenuitem")
public class Tmenuitem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numitemid", nullable = false)
    private Integer numitemid;
    @Basic(optional = false)
    @Column(name = "vc2itemname", nullable = false, length = 100)
    private String vc2itemname;
    @Basic(optional = false)
    @Column(name = "vc2itemurl", nullable = false, length = 1000)
    private String vc2itemurl;
    @Basic(optional = false)
    @Column(name = "vc2itemdesc", nullable = false, length = 500)
    private String vc2itemdesc;
    @Column(name = "vc2itemimg", length = 2000)
    private String vc2itemimg;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;
    @Basic(optional = false)
    @Column(name = "numindex", nullable = false)
    private int numindex;
    @ManyToMany(mappedBy = "tmenuitemSet",fetch=FetchType.EAGER)
    private Set<Tgroup> tgroupSet;
    @JoinColumn(name = "numcateid", referencedColumnName = "numcateid", nullable = false)
    @ManyToOne(optional = false)
    private Tcategory numcateid;

    public Tmenuitem() {
    }

    public Tmenuitem(Integer numitemid) {
        this.numitemid = numitemid;
    }

    public Tmenuitem(Integer numitemid, String vc2itemname, String vc2itemurl, String vc2itemdesc, int numenable, int numindex) {
        this.numitemid = numitemid;
        this.vc2itemname = vc2itemname;
        this.vc2itemurl = vc2itemurl;
        this.vc2itemdesc = vc2itemdesc;
        this.numenable = numenable;
        this.numindex = numindex;
    }

    public Integer getNumitemid() {
        return numitemid;
    }

    public void setNumitemid(Integer numitemid) {
        this.numitemid = numitemid;
    }

    public String getVc2itemname() {
        return vc2itemname;
    }

    public void setVc2itemname(String vc2itemname) {
        this.vc2itemname = vc2itemname;
    }

    public String getVc2itemurl() {
        return vc2itemurl;
    }

    public void setVc2itemurl(String vc2itemurl) {
        this.vc2itemurl = vc2itemurl;
    }

    public String getVc2itemdesc() {
        return vc2itemdesc;
    }

    public void setVc2itemdesc(String vc2itemdesc) {
        this.vc2itemdesc = vc2itemdesc;
    }

    public String getVc2itemimg() {
        return vc2itemimg;
    }

    public void setVc2itemimg(String vc2itemimg) {
        this.vc2itemimg = vc2itemimg;
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

    public Set<Tgroup> getTgroupSet() {
        return tgroupSet;
    }

    public void setTgroupSet(Set<Tgroup> tgroupSet) {
        this.tgroupSet = tgroupSet;
    }

    public Tcategory getNumcateid() {
        return numcateid;
    }

    public void setNumcateid(Tcategory numcateid) {
        this.numcateid = numcateid;
    }

    public boolean getIsEnable(){
        return this.numenable>0;
    }
    public void setIsEnable(boolean enable){
        this.numenable = (enable?1:0);
    }
    public String getImgEnable(){
        if (this.numenable>0){
            return "/img/smallicons/right_small.png";
        }else{
            return "/img/smallicons/wrong_small.png";
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numitemid != null ? numitemid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tmenuitem)) {
            return false;
        }
        Tmenuitem other = (Tmenuitem) object;
        if ((this.numitemid == null && other.numitemid != null) || (this.numitemid != null && !this.numitemid.equals(other.numitemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Tmenuitem[numitemid=" + numitemid + "]";
    }

}
