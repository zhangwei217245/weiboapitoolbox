/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.persist.entity;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author X-Spirit
 */
@Entity
@Table(name = "tdatastruct")
public class Tdatastruct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numdatastructid", nullable = false)
    private Integer numdatastructid;
    @Basic(optional = false)
    @Column(name = "vc2structname", nullable = false, length = 200)
    private String vc2structname;
    @Basic(optional = false)
    @Column(name = "vc2desc", nullable = false, length = 2000)
    private String vc2desc;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;
    @Column(name = "vc2version", length = 20)
    private String vc2version;
    
    @OneToMany(mappedBy = "numinheritfrom")
    private Set<Tdatastruct> datastructExtendedSet;
    @JoinColumn(name = "numinheritfrom", referencedColumnName = "numdatastructid")
    @ManyToOne
    private Tdatastruct numinheritfrom;

    @OneToMany(mappedBy = "numdatastructid")
    private Set<Tresponse> tresponseSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numparentdatastructid", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Tstructfield> tstructfieldSet;
    @OneToMany(mappedBy = "numdatastructid")
    private Set<Tstructfield> tstructtypefieldSet;

    public Tdatastruct() {
    }

    public Tdatastruct(Integer numdatastructid) {
        this.numdatastructid = numdatastructid;
    }

    public Tdatastruct(Integer numdatastructid, String vc2structname,String vc2desc, int numenable) {
        this.numdatastructid = numdatastructid;
        this.vc2structname = vc2structname;
        this.vc2desc = vc2desc;
        this.numenable = numenable;
    }

    public Integer getNumdatastructid() {
        return numdatastructid;
    }

    public void setNumdatastructid(Integer numdatastructid) {
        this.numdatastructid = numdatastructid;
    }

    public String getVc2structname() {
        return vc2structname;
    }

    public void setVc2structname(String vc2structname) {
        this.vc2structname = vc2structname;
    }

    public String getVc2desc() {
        return vc2desc;
    }

    public void setVc2desc(String vc2desc) {
        this.vc2desc = vc2desc;
    }

    public int getNumenable() {
        return numenable;
    }

    public void setNumenable(int numenable) {
        this.numenable = numenable;
    }

    public String getVc2version() {
        return vc2version;
    }

    public void setVc2version(String vc2version) {
        this.vc2version = vc2version;
    }

    public Set<Tresponse> getTresponseSet() {
        return tresponseSet;
    }

    public void setTresponseSet(Set<Tresponse> tresponseSet) {
        this.tresponseSet = tresponseSet;
    }

    public List<Tstructfield> getTstructfieldSet() {
        return tstructfieldSet;
    }

    public void setTstructfieldSet(List<Tstructfield> tstructfieldSet) {
        this.tstructfieldSet = tstructfieldSet;
    }

    public Set<Tstructfield> getTstructtypefieldSet() {
        return tstructtypefieldSet;
    }

    public void setTstructtypefieldSet(Set<Tstructfield> tstructtypefieldSet) {
        this.tstructtypefieldSet = tstructtypefieldSet;
    }

    public Tdatastruct getNuminheritfrom() {
        return numinheritfrom;
    }

    public void setNuminheritfrom(Tdatastruct numinheritfrom) {
        this.numinheritfrom = numinheritfrom;
    }

    public Set<Tdatastruct> getDatastructExtendedSet() {
        return datastructExtendedSet;
    }

    public void setDatastructExtendedSet(Set<Tdatastruct> datastructExtendedSet) {
        this.datastructExtendedSet = datastructExtendedSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numdatastructid != null ? numdatastructid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tdatastruct)) {
            return false;
        }
        Tdatastruct other = (Tdatastruct) object;
        if ((this.numdatastructid == null && other.numdatastructid != null) || (this.numdatastructid != null && !this.numdatastructid.equals(other.numdatastructid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Tdatastruct[numdatastructid=" + numdatastructid + "]";
    }
}
