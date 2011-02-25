/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.persist.entity;

import com.weibo.api.toolbox.common.enumerations.ContentType;
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
@Table(name = "tresponse")
@NamedQueries({
    @NamedQuery(name = "Tresponse.findAll", query = "SELECT t FROM Tresponse t"),
    @NamedQuery(name = "Tresponse.findByNumresponseid", query = "SELECT t FROM Tresponse t WHERE t.numresponseid = :numresponseid"),
    @NamedQuery(name = "Tresponse.findByVc2responsename", query = "SELECT t FROM Tresponse t WHERE t.vc2responsename = :vc2responsename"),
    @NamedQuery(name = "Tresponse.findByNumdatatype", query = "SELECT t FROM Tresponse t WHERE t.numdatatype = :numdatatype"),
    @NamedQuery(name = "Tresponse.findByNumrequired", query = "SELECT t FROM Tresponse t WHERE t.numrequired = :numrequired"),
    @NamedQuery(name = "Tresponse.findByVc2desc", query = "SELECT t FROM Tresponse t WHERE t.vc2desc = :vc2desc"),
    @NamedQuery(name = "Tresponse.findByVc2demovalue", query = "SELECT t FROM Tresponse t WHERE t.vc2demovalue = :vc2demovalue")})
public class Tresponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numresponseid", nullable = false)
    private Integer numresponseid;
    @Basic(optional = false)
    @Column(name = "vc2responsename", nullable = false, length = 200)
    private String vc2responsename;
    @Basic(optional = false)
    @Column(name = "numdatatype", nullable = false)
    private int numdatatype;
    @Basic(optional = false)
    @Column(name = "numrequired")
    private int numrequired;
    @Basic(optional = false)
    @Column(name = "numcontenttype")
    private int numcontenttype;
    @Basic(optional = false)
    @Column(name = "vc2desc", nullable = false, length = 5000)
    private String vc2desc;
    @Column(name = "vc2demovalue", length = 45)
    private String vc2demovalue;
    @JoinColumn(name = "numdatastructid", referencedColumnName = "numdatastructid")
    @ManyToOne
    private Tdatastruct numdatastructid;
    @JoinColumn(name = "numspecid", referencedColumnName = "numspecid", nullable = false)
    @ManyToOne(optional = false)
    private Tspec numspecid;

    public Tresponse() {
    }

    public Tresponse(Integer numresponseid) {
        this.numresponseid = numresponseid;
    }

    public Tresponse(Integer numresponseid, String vc2responsename, int numdatatype,int numrequired,int numcontenttype, String vc2desc) {
        this.numresponseid = numresponseid;
        this.vc2responsename = vc2responsename;
        this.numdatatype = numdatatype;
        this.vc2desc = vc2desc;
        this.numrequired = numrequired;
        this.numcontenttype = numcontenttype;
    }

    public Integer getNumresponseid() {
        return numresponseid;
    }

    public void setNumresponseid(Integer numresponseid) {
        this.numresponseid = numresponseid;
    }

    public String getVc2responsename() {
        return vc2responsename;
    }

    public void setVc2responsename(String vc2responsename) {
        this.vc2responsename = vc2responsename;
    }

    public int getNumdatatype() {
        return numdatatype;
    }

    public void setNumdatatype(int numdatatype) {
        this.numdatatype = numdatatype;
    }

    public int getNumrequired() {
        return numrequired;
    }

    public void setNumrequired(int numrequired) {
        this.numrequired = numrequired;
    }

    public String getVc2desc() {
        return vc2desc;
    }

    public void setVc2desc(String vc2desc) {
        this.vc2desc = vc2desc;
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

    public Tspec getNumspecid() {
        return numspecid;
    }

    public void setNumspecid(Tspec numspecid) {
        this.numspecid = numspecid;
    }

    public int getNumcontenttype() {
        return numcontenttype;
    }

    public void setNumcontenttype(int numcontenttype) {
        this.numcontenttype = numcontenttype;
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
    public ContentType getEnumContentType(){
        return ContentType.getValueById(this.numcontenttype);
    }
    public void setEnumContentType(ContentType ctntype){
        this.numcontenttype = ctntype.getId();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numresponseid != null ? numresponseid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tresponse)) {
            return false;
        }
        Tresponse other = (Tresponse) object;
        if ((this.numresponseid == null && other.numresponseid != null) || (this.numresponseid != null && !this.numresponseid.equals(other.numresponseid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Tresponse[numresponseid=" + numresponseid + "]";
    }

}
