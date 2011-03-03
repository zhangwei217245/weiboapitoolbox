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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "tenumgroup")
public class Tenumgroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numenumgroupid", nullable = false)
    private Integer numenumgroupid;
    @Basic(optional = false)
    @Column(name = "vc2enumgroupname", nullable = false, length = 200)
    private String vc2enumgroupname;
    @Column(name = "vc2enumgroupdesc", length = 1000)
    private String vc2enumgroupdesc;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;
    @OneToMany(mappedBy = "numenumgroupid")
    private Set<Trequestparam> trequestparamSet;
    @OneToMany(mappedBy = "numenumgroupid")
    private Set<Sysparam> sysparamSet;
    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER,mappedBy = "numenumgroupid")
    private Set<Tenumvalues> tenumvaluesSet;
    @OneToMany(mappedBy = "numenumgroupid")
    private Set<Tstructfield> tstructfieldSet;
    @OneToMany(mappedBy = "numenumgroupid")
    private Set<Tresponse> tresponseSet;

    public Tenumgroup() {
    }

    public Tenumgroup(Integer numenumgroupid) {
        this.numenumgroupid = numenumgroupid;
    }

    public Tenumgroup(Integer numenumgroupid, String vc2enumgroupname,String vc2enumgroupdesc, int numenable) {
        this.numenumgroupid = numenumgroupid;
        this.vc2enumgroupname = vc2enumgroupname;
        this.vc2enumgroupdesc = vc2enumgroupdesc;
        this.numenable = numenable;
    }

    public Integer getNumenumgroupid() {
        return numenumgroupid;
    }

    public void setNumenumgroupid(Integer numenumgroupid) {
        this.numenumgroupid = numenumgroupid;
    }

    public String getVc2enumgroupname() {
        return vc2enumgroupname;
    }

    public void setVc2enumgroupname(String vc2enumgroupname) {
        this.vc2enumgroupname = vc2enumgroupname;
    }

    public String getVc2enumgroupdesc() {
        return vc2enumgroupdesc;
    }

    public void setVc2enumgroupdesc(String vc2enumgroupdesc) {
        this.vc2enumgroupdesc = vc2enumgroupdesc;
    }

    public int getNumenable() {
        return numenable;
    }

    public void setNumenable(int numenable) {
        this.numenable = numenable;
    }

    public Set<Trequestparam> getTrequestparamSet() {
        return trequestparamSet;
    }

    public void setTrequestparamSet(Set<Trequestparam> trequestparamSet) {
        this.trequestparamSet = trequestparamSet;
    }

    public Set<Sysparam> getSysparamSet() {
        return sysparamSet;
    }

    public void setSysparamSet(Set<Sysparam> sysparamSet) {
        this.sysparamSet = sysparamSet;
    }

    public Set<Tenumvalues> getTenumvaluesSet() {
        return tenumvaluesSet;
    }

    public void setTenumvaluesSet(Set<Tenumvalues> tenumvaluesSet) {
        this.tenumvaluesSet = tenumvaluesSet;
    }

    public Set<Tstructfield> getTstructfieldSet() {
        return tstructfieldSet;
    }

    public void setTstructfieldSet(Set<Tstructfield> tstructfieldSet) {
        this.tstructfieldSet = tstructfieldSet;
    }

    public Set<Tresponse> getTresponseSet() {
        return tresponseSet;
    }

    public void setTresponseSet(Set<Tresponse> tresponseSet) {
        this.tresponseSet = tresponseSet;
    }

    public String getEnableImg(){
        if (this.numenable<=0){
            return "/img/smallicons/wrong_small.png";
        }else{
            return "/img/smallicons/right_small.png";
        }
    }

    public boolean getIsEnable(){
        return this.getNumenable() > 0;
    }
    public void setIsEnable(boolean enable){
        this.setNumenable(enable?1:0);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numenumgroupid != null ? numenumgroupid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tenumgroup)) {
            return false;
        }
        Tenumgroup other = (Tenumgroup) object;
        if ((this.numenumgroupid == null && other.numenumgroupid != null) || (this.numenumgroupid != null && !this.numenumgroupid.equals(other.numenumgroupid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.zkoss.zkdemo.persist.entity.Tenumgroup[numenumgroupid=" + numenumgroupid + "]";
    }

}
