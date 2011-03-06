package com.weibo.api.toolbox.persist.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author X-Spirit
 */
@Entity
@Table(name = "tspeccategory")
public class Tspeccategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numcateid", nullable = false)
    private Integer numcateid;
    @Basic(optional = false)
    @Column(name = "vc2catename", nullable = false, length = 200)
    private String vc2catename;
    @Basic(optional = false)
    @Column(name = "numindex", nullable = false)
    private int numindex;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;
    @Basic(optional = false)
    @Column(name = "vc2desc", nullable = false, length = 2000)
    private String vc2desc;
    @Column(name = "datcreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datcreated;
    @Column(name = "datupdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datupdated;
    @JoinColumn(name = "numupdateduser", referencedColumnName = "numuserid")
    @ManyToOne
    private Tuser numupdateduser;
    @JoinColumn(name = "numcreateduser", referencedColumnName = "numuserid")
    @ManyToOne
    private Tuser numcreateduser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numcateid")
    private Set<Sysparam> sysparamSet;
    @OneToMany(mappedBy = "numparentcateid" , fetch=FetchType.EAGER)
    private Set<Tspeccategory> tspeccategorySet;
    @JoinColumn(name = "numparentcateid", referencedColumnName = "numcateid")
    @ManyToOne
    private Tspeccategory numparentcateid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numcateid")
    private Set<Tspec> tspecSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numcateid")
    private Set<Syserror> syserrorSet;

    public Tspeccategory() {
    }

    public Tspeccategory(Integer numcateid) {
        this.numcateid = numcateid;
    }

    public Tspeccategory(Integer numcateid, String vc2catename, int numindex, int numenable, String vc2desc) {
        this.numcateid = numcateid;
        this.vc2catename = vc2catename;
        this.numindex = numindex;
        this.numenable = numenable;
        this.vc2desc = vc2desc;
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

    public int getNumindex() {
        return numindex;
    }

    public void setNumindex(int numindex) {
        this.numindex = numindex;
    }

    public int getNumenable() {
        return numenable;
    }

    public void setNumenable(int numenable) {
        this.numenable = numenable;
    }

    public String getVc2desc() {
        return vc2desc;
    }

    public void setVc2desc(String vc2desc) {
        this.vc2desc = vc2desc;
    }

    public Set<Sysparam> getSysparamSet() {
        return sysparamSet;
    }

    public void setSysparamSet(Set<Sysparam> sysparamSet) {
        this.sysparamSet = sysparamSet;
    }

    public Set<Tspeccategory> getTspeccategorySet() {
        return tspeccategorySet;
    }

    public void setTspeccategorySet(Set<Tspeccategory> tspeccategorySet) {
        this.tspeccategorySet = tspeccategorySet;
    }

    public Tspeccategory getNumparentcateid() {
        return numparentcateid;
    }

    public void setNumparentcateid(Tspeccategory numparentcateid) {
        this.numparentcateid = numparentcateid;
    }

    public Set<Tspec> getTspecSet() {
        return tspecSet;
    }

    public void setTspecSet(Set<Tspec> tspecSet) {
        this.tspecSet = tspecSet;
    }

    public Set<Syserror> getSyserrorSet() {
        return syserrorSet;
    }

    public void setSyserrorSet(Set<Syserror> syserrorSet) {
        this.syserrorSet = syserrorSet;
    }

    public Date getDatcreated() {
        return datcreated;
    }

    public void setDatcreated(Date datcreated) {
        this.datcreated = datcreated;
    }

    public Date getDatupdated() {
        return datupdated;
    }

    public void setDatupdated(Date datupdated) {
        this.datupdated = datupdated;
    }

    public Tuser getNumcreateduser() {
        return numcreateduser;
    }

    public void setNumcreateduser(Tuser numcreateduser) {
        this.numcreateduser = numcreateduser;
    }

    public Tuser getNumupdateduser() {
        return numupdateduser;
    }

    public void setNumupdateduser(Tuser numupdateduser) {
        this.numupdateduser = numupdateduser;
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
        if (!(object instanceof Tspeccategory)) {
            return false;
        }
        Tspeccategory other = (Tspeccategory) object;
        if ((this.numcateid == null && other.numcateid != null) || (this.numcateid != null && !this.numcateid.equals(other.numcateid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Tspeccategory[numcateid=" + numcateid + "]";
    }

}
