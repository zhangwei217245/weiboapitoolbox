package com.weibo.api.toolbox.persist.entity;

import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.common.enumerations.ParamStyle;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author X-Spirit
 */
@Entity
@Table(name = "sysparam")
public class Sysparam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numparamid", nullable = false)
    private Integer numparamid;
    @Basic(optional = false)
    @Column(name = "vc2paramname", nullable = false, length = 200)
    private String vc2paramname;
    @Basic(optional = false)
    @Column(name = "numrequired", nullable = false)
    private int numrequired;
    @Basic(optional = false)
    @Column(name = "numrepeating", nullable = false)
    private int numrepeating;
    @Basic(optional = false)
    @Column(name = "numstyle", nullable = false)
    private int numstyle;
    @Basic(optional = false)
    @Column(name = "numdatatype", nullable = false)
    private int numdatatype;
    @Basic(optional = false)
    @Column(name = "vc2desc", nullable = false, length = 2000)
    private String vc2desc;
    @Column(name = "vc2range", length = 500)
    private String vc2range;
    @Column(name = "vc2defaultvalue", length = 500)
    private String vc2defaultvalue;
    @Column(name = "vc2demovalue", length = 500)
    private String vc2demovalue;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;
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
    @JoinColumn(name = "numenumgroupid", referencedColumnName = "numenumgroupid")
    @ManyToOne
    private Tenumgroup numenumgroupid;
    @JoinColumn(name = "numcateid", referencedColumnName = "numcateid", nullable = false)
    @ManyToOne(optional = false)
    private Tspeccategory numcateid;

    public Sysparam() {
    }

    public Sysparam(Integer numparamid) {
        this.numparamid = numparamid;
    }

    public Sysparam(Integer numparamid, String vc2paramname, int numrequired, int numstyle, int numdatatype, String vc2desc, int numenable) {
        this.numparamid = numparamid;
        this.vc2paramname = vc2paramname;
        this.numrequired = numrequired;
        this.numstyle = numstyle;
        this.numdatatype = numdatatype;
        this.vc2desc = vc2desc;
        this.numenable = numenable;
    }

    public Integer getNumparamid() {
        return numparamid;
    }

    public void setNumparamid(Integer numparamid) {
        this.numparamid = numparamid;
    }

    public String getVc2paramname() {
        return vc2paramname;
    }

    public void setVc2paramname(String vc2paramname) {
        this.vc2paramname = vc2paramname;
    }

    public int getNumrequired() {
        return numrequired;
    }

    public void setNumrequired(int numrequired) {
        this.numrequired = numrequired;
    }
    
    public int getNumrepeating() {
        return numrepeating;
    }

    public void setNumrepeating(int numrepeating) {
        this.numrepeating = numrepeating;
    }

    public int getNumstyle() {
        return numstyle;
    }

    public void setNumstyle(int numstyle) {
        this.numstyle = numstyle;
    }

    public int getNumdatatype() {
        return numdatatype;
    }

    public void setNumdatatype(int numdatatype) {
        this.numdatatype = numdatatype;
    }

    public String getVc2desc() {
        return vc2desc;
    }

    public void setVc2desc(String vc2desc) {
        this.vc2desc = vc2desc;
    }

    public String getVc2range() {
        return vc2range;
    }

    public void setVc2range(String vc2range) {
        this.vc2range = vc2range;
    }

    public String getVc2defaultvalue() {
        return vc2defaultvalue;
    }

    public void setVc2defaultvalue(String vc2defaultvalue) {
        this.vc2defaultvalue = vc2defaultvalue;
    }

    public String getVc2demovalue() {
        return vc2demovalue;
    }

    public void setVc2demovalue(String vc2demovalue) {
        this.vc2demovalue = vc2demovalue;
    }

    public int getNumenable() {
        return numenable;
    }

    public void setNumenable(int numenable) {
        this.numenable = numenable;
    }

    public Tenumgroup getNumenumgroupid() {
        return numenumgroupid;
    }

    public void setNumenumgroupid(Tenumgroup numenumgroupid) {
        this.numenumgroupid = numenumgroupid;
    }

    public Tspeccategory getNumcateid() {
        return numcateid;
    }

    public void setNumcateid(Tspeccategory numcateid) {
        this.numcateid = numcateid;
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

    /**
     * Resolve Enum Values
     */
    public ParamStyle getEnumParamStyle(){
        return ParamStyle.getValueById(this.getNumstyle());
    }
    public void setEnumParamStyle(ParamStyle enm){
        this.setNumstyle(enm.getId());
    }
    public DataTypes getEnumDataTypes(){
        return DataTypes.getValueById(this.getNumdatatype());
    }
    public void setEnumDataTypes(DataTypes enm){
        this.setNumdatatype(enm.getId());
    }
    public boolean getIsRequired(){
        return this.getNumrequired() > 0;
    }
    public void setIsRequired(boolean required){
        this.setNumrequired(required?1:0);
    }
    public boolean getIsRepeating(){
        return this.getNumrepeating() > 0;
    }
    public void setIsRepeating(boolean isRepeating){
        this.setNumrepeating(isRepeating?1:0);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numparamid != null ? numparamid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sysparam)) {
            return false;
        }
        Sysparam other = (Sysparam) object;
        if ((this.numparamid == null && other.numparamid != null) || (this.numparamid != null && !this.numparamid.equals(other.numparamid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Sysparam[numparamid=" + numparamid + "]";
    }

}
