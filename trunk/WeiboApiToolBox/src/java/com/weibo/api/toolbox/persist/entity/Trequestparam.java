/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.persist.entity;

import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.common.enumerations.ParamStyle;
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
@Table(name = "trequestparam")
@NamedQueries({
    @NamedQuery(name = "Trequestparam.findAll", query = "SELECT t FROM Trequestparam t"),
    @NamedQuery(name = "Trequestparam.findByNumparamid", query = "SELECT t FROM Trequestparam t WHERE t.numparamid = :numparamid"),
    @NamedQuery(name = "Trequestparam.findByVc2paramname", query = "SELECT t FROM Trequestparam t WHERE t.vc2paramname = :vc2paramname"),
    @NamedQuery(name = "Trequestparam.findByNumrequired", query = "SELECT t FROM Trequestparam t WHERE t.numrequired = :numrequired"),
    @NamedQuery(name = "Trequestparam.findByNumstyle", query = "SELECT t FROM Trequestparam t WHERE t.numstyle = :numstyle"),
    @NamedQuery(name = "Trequestparam.findByNumdatatype", query = "SELECT t FROM Trequestparam t WHERE t.numdatatype = :numdatatype"),
    @NamedQuery(name = "Trequestparam.findByVc2desc", query = "SELECT t FROM Trequestparam t WHERE t.vc2desc = :vc2desc"),
    @NamedQuery(name = "Trequestparam.findByVc2range", query = "SELECT t FROM Trequestparam t WHERE t.vc2range = :vc2range"),
    @NamedQuery(name = "Trequestparam.findByVc2defaultvalue", query = "SELECT t FROM Trequestparam t WHERE t.vc2defaultvalue = :vc2defaultvalue"),
    @NamedQuery(name = "Trequestparam.findByVc2demovalue", query = "SELECT t FROM Trequestparam t WHERE t.vc2demovalue = :vc2demovalue"),
    @NamedQuery(name = "Trequestparam.findByNumenable", query = "SELECT t FROM Trequestparam t WHERE t.numenable = :numenable")})
public class Trequestparam implements Serializable {
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
    @JoinColumn(name = "numenumgroupid", referencedColumnName = "numenumgroupid")
    @ManyToOne
    private Tenumgroup numenumgroupid;
    @JoinColumn(name = "numspecid", referencedColumnName = "numspecid")
    @ManyToOne
    private Tspec numspecid;

    public Trequestparam() {
    }

    public Trequestparam(Integer numparamid) {
        this.numparamid = numparamid;
    }

    public Trequestparam(Integer numparamid, String vc2paramname, int numrequired, int numstyle, int numdatatype, String vc2desc, int numenable) {
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

    public Tspec getNumspecid() {
        return numspecid;
    }

    public void setNumspecid(Tspec numspecid) {
        this.numspecid = numspecid;
    }

    /**
     * resolve enumerations below
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numparamid != null ? numparamid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trequestparam)) {
            return false;
        }
        Trequestparam other = (Trequestparam) object;
        if ((this.numparamid == null && other.numparamid != null) || (this.numparamid != null && !this.numparamid.equals(other.numparamid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Trequestparam[numparamid=" + numparamid + "]";
    }

}
