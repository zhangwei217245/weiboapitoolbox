/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.persist.entity;

import com.weibo.api.toolbox.common.enumerations.DataTypes;
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
 * @author X-Spirit
 */
@Entity
@Table(name = "tstructfield")
public class Tstructfield implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numfieldid", nullable = false)
    private Integer numfieldid;
    @Basic(optional = false)
    @Column(name = "vc2fieldname", nullable = false, length = 100)
    private String vc2fieldname;
    @Basic(optional = false)
    @Column(name = "numdatatype", nullable = false)
    private int numdatatype;
    @Basic(optional = false)
    @Column(name = "numrequired", nullable = false)
    private int numrequired;
    @Basic(optional = false)
    @Column(name = "numprivate", nullable = false)
    private int numprivate;
    @Basic(optional = false)
    @Column(name = "vc2desc", nullable = false, length = 3000)
    private String vc2desc;
    @Column(name = "vc2demovalue", length = 500)
    private String vc2demovalue;
    @JoinColumn(name = "numparentdatastructid", referencedColumnName = "numdatastructid", nullable = false)
    @ManyToOne(optional = false)
    private Tdatastruct numparentdatastructid;
    @JoinColumn(name = "numdatastructid", referencedColumnName = "numdatastructid")
    @ManyToOne
    private Tdatastruct numdatastructid;
    @JoinColumn(name = "numenumgroupid", referencedColumnName = "numenumgroupid")
    @ManyToOne
    private Tenumgroup numenumgroupid;

    public Tstructfield() {
    }

    public Tstructfield(Integer numfieldid) {
        this.numfieldid = numfieldid;
    }

    public Tstructfield(Integer numfieldid, String vc2fieldname, int numdatatype, String vc2desc) {
        this.numfieldid = numfieldid;
        this.vc2fieldname = vc2fieldname;
        this.numdatatype = numdatatype;
        this.vc2desc = vc2desc;
        this.numrequired = 0;
        this.numprivate = 0;
    }

    public Integer getNumfieldid() {
        return numfieldid;
    }

    public void setNumfieldid(Integer numfieldid) {
        this.numfieldid = numfieldid;
    }

    public String getVc2fieldname() {
        return vc2fieldname;
    }

    public void setVc2fieldname(String vc2fieldname) {
        this.vc2fieldname = vc2fieldname;
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

    public int getNumrequired() {
        return numrequired;
    }

    public void setNumrequired(int numrequired) {
        this.numrequired = numrequired;
    }

    public int getNumprivate() {
        return numprivate;
    }

    public void setNumprivate(int numprivate) {
        this.numprivate = numprivate;
    }

    public String getVc2demovalue() {
        return vc2demovalue;
    }

    public void setVc2demovalue(String vc2demovalue) {
        this.vc2demovalue = vc2demovalue;
    }

    public Tdatastruct getNumdatastructid() {
        return numdatastructid;
    }

    public void setNumdatastructid(Tdatastruct numdatastructid) {
        this.numdatastructid = numdatastructid;
    }

    public Tdatastruct getNumparentdatastructid() {
        return numparentdatastructid;
    }

    public void setNumparentdatastructid(Tdatastruct numparentdatastructid) {
        this.numparentdatastructid = numparentdatastructid;
    }

    public Tenumgroup getNumenumgroupid() {
        return numenumgroupid;
    }

    public void setNumenumgroupid(Tenumgroup numenumgroupid) {
        this.numenumgroupid = numenumgroupid;
    }

    /**
     * Resolve Enum Values
     */
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
    public boolean getIsPrivate(){
        return this.numprivate==1;
    }

    public void setIsPrivate(boolean isprivate){
        this.numprivate=isprivate?1:0;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numfieldid != null ? numfieldid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tstructfield)) {
            return false;
        }
        Tstructfield other = (Tstructfield) object;
        if ((this.numfieldid == null && other.numfieldid != null) || (this.numfieldid != null && !this.numfieldid.equals(other.numfieldid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Tstructfield[numfieldid=" + numfieldid + "]";
    }

}
