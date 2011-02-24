/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.persist.entity;

import com.weibo.api.toolbox.common.enumerations.AcceptType;
import com.weibo.api.toolbox.common.enumerations.ApiStatus;
import com.weibo.api.toolbox.common.enumerations.ApiType;
import com.weibo.api.toolbox.common.enumerations.AuthType;
import com.weibo.api.toolbox.common.enumerations.ContentType;
import com.weibo.api.toolbox.common.enumerations.HttpMethod;
import com.weibo.api.toolbox.common.enumerations.RateLimit;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author X-Spirit
 */
@Entity
@Table(name = "tspec")
@NamedQueries({
    @NamedQuery(name = "Tspec.findAll", query = "SELECT t FROM Tspec t"),
    @NamedQuery(name = "Tspec.findByNumspecid", query = "SELECT t FROM Tspec t WHERE t.numspecid = :numspecid"),
    @NamedQuery(name = "Tspec.findByVc2version", query = "SELECT t FROM Tspec t WHERE t.vc2version = :vc2version"),
    @NamedQuery(name = "Tspec.findByVc2mainresource", query = "SELECT t FROM Tspec t WHERE t.vc2mainresource = :vc2mainresource"),
    @NamedQuery(name = "Tspec.findByVc2subresource", query = "SELECT t FROM Tspec t WHERE t.vc2subresource = :vc2subresource"),
    @NamedQuery(name = "Tspec.findByVc2httpmethod", query = "SELECT t FROM Tspec t WHERE t.vc2httpmethod = :vc2httpmethod"),
    @NamedQuery(name = "Tspec.findByNumreqaccept", query = "SELECT t FROM Tspec t WHERE t.numreqaccept = :numreqaccept"),
    @NamedQuery(name = "Tspec.findByVc2respcontenttype", query = "SELECT t FROM Tspec t WHERE t.vc2respcontenttype = :vc2respcontenttype"),
    @NamedQuery(name = "Tspec.findByNumauthtype", query = "SELECT t FROM Tspec t WHERE t.numauthtype = :numauthtype"),
    @NamedQuery(name = "Tspec.findByNumratelimittype", query = "SELECT t FROM Tspec t WHERE t.numratelimittype = :numratelimittype"),
    @NamedQuery(name = "Tspec.findByNumcateindex", query = "SELECT t FROM Tspec t WHERE t.numcateindex = :numcateindex"),
    @NamedQuery(name = "Tspec.findByVc2shortdesc", query = "SELECT t FROM Tspec t WHERE t.vc2shortdesc = :vc2shortdesc"),
    @NamedQuery(name = "Tspec.findByVc2maindesc", query = "SELECT t FROM Tspec t WHERE t.vc2maindesc = :vc2maindesc"),
    @NamedQuery(name = "Tspec.findByVc2cautions", query = "SELECT t FROM Tspec t WHERE t.vc2cautions = :vc2cautions"),
    @NamedQuery(name = "Tspec.findByNumenable", query = "SELECT t FROM Tspec t WHERE t.numenable = :numenable"),
    @NamedQuery(name = "Tspec.findByNumstatus", query = "SELECT t FROM Tspec t WHERE t.numstatus = :numstatus"),
    @NamedQuery(name = "Tspec.findByNumapitype", query = "SELECT t FROM Tspec t WHERE t.numapitype = :numapitype")})
public class Tspec implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numspecid", nullable = false)
    private Integer numspecid;
    @Column(name = "vc2version", length = 20)
    private String vc2version;
    @Basic(optional = false)
    @Column(name = "vc2mainresource", nullable = false, length = 200)
    private String vc2mainresource;
    @Column(name = "vc2subresource", length = 200)
    private String vc2subresource;
    @Basic(optional = false)
    @Column(name = "vc2httpmethod", nullable = false, length = 20)
    private String vc2httpmethod;
    @Basic(optional = false)
    @Column(name = "numreqaccept", nullable = false)
    private int numreqaccept;
    @Basic(optional = false)
    @Column(name = "vc2respcontenttype", nullable = false, length = 20)
    private String vc2respcontenttype;
    @Basic(optional = false)
    @Column(name = "numauthtype", nullable = false)
    private int numauthtype;
    @Basic(optional = false)
    @Column(name = "numratelimittype", nullable = false)
    private int numratelimittype;
    @Basic(optional = false)
    @Column(name = "numcateindex", nullable = false)
    private int numcateindex;
    @Basic(optional = false)
    @Column(name = "vc2shortdesc", nullable = false, length = 400)
    private String vc2shortdesc;
    @Column(name = "vc2maindesc", length = 2000)
    private String vc2maindesc;
    @Column(name = "vc2cautions", length = 5000)
    private String vc2cautions;
    @Basic(optional = false)
    @Column(name = "numenable", nullable = false)
    private int numenable;
    @Basic(optional = false)
    @Column(name = "numstatus", nullable = false)
    private int numstatus;
    @Basic(optional = false)
    @Column(name = "numapitype", nullable = false)
    private int numapitype;
    @Basic(optional = false)
    @Column(name = "vc2specname", nullable = false, length = 200)
    private String vc2specname;
    @OneToMany(mappedBy = "numspecid",cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Trequestparam> trequestparamSet;
    @OneToMany(mappedBy = "numspecid",cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Terrorcode> terrorcodeSet;
    @JoinColumn(name = "numcateid", referencedColumnName = "numcateid", nullable = false)
    @ManyToOne(optional = false)
    private Tspeccategory numcateid;
    @JoinColumn(name = "numbaseurlid", referencedColumnName = "numbaseurlid", nullable = false)
    @ManyToOne(optional = false)
    private Baseurl numbaseurlid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numspecid",fetch=FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Tresponse> tresponseSet;

    @JoinTable(name = "tspec_ref_tspec", joinColumns = {
        @JoinColumn(name = "numspecid", referencedColumnName = "numspecid", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "numspecid_ref", referencedColumnName = "numspecid", nullable = false)})
    @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    private Set<Tspec> tspecSetOnWho;
    
    @ManyToMany(mappedBy = "tspecSetOnWho",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    private Set<Tspec> tspecSetOnMe;
    

    public Tspec() {
    }

    public Tspec(Integer numspecid) {
        this.numspecid = numspecid;
    }

    public Tspec(Integer numspecid, String vc2mainresource, String vc2httpmethod, int numreqaccept, String vc2respcontenttype, int numauthtype, int numratelimittype, int numcateindex, String vc2shortdesc, int numenable, int numstatus, int numapitype) {
        this.numspecid = numspecid;
        this.vc2mainresource = vc2mainresource;
        this.vc2httpmethod = vc2httpmethod;
        this.numreqaccept = numreqaccept;
        this.vc2respcontenttype = vc2respcontenttype;
        this.numauthtype = numauthtype;
        this.numratelimittype = numratelimittype;
        this.numcateindex = numcateindex;
        this.vc2shortdesc = vc2shortdesc;
        this.numenable = numenable;
        this.numstatus = numstatus;
        this.numapitype = numapitype;
    }

    public Integer getNumspecid() {
        return numspecid;
    }

    public void setNumspecid(Integer numspecid) {
        this.numspecid = numspecid;
    }

    public String getVc2version() {
        return vc2version;
    }

    public void setVc2version(String vc2version) {
        this.vc2version = vc2version;
    }

    public String getVc2mainresource() {
        return vc2mainresource;
    }

    public void setVc2mainresource(String vc2mainresource) {
        this.vc2mainresource = vc2mainresource;
    }

    public String getVc2subresource() {
        return vc2subresource;
    }

    public void setVc2subresource(String vc2subresource) {
        this.vc2subresource = vc2subresource;
    }

    public String getVc2httpmethod() {
        return vc2httpmethod;
    }

    public void setVc2httpmethod(String vc2httpmethod) {
        this.vc2httpmethod = vc2httpmethod;
    }

    public int getNumreqaccept() {
        return numreqaccept;
    }

    public void setNumreqaccept(int numreqaccept) {
        this.numreqaccept = numreqaccept;
    }

    public String getVc2respcontenttype() {
        return vc2respcontenttype;
    }

    public void setVc2respcontenttype(String vc2respcontenttype) {
        this.vc2respcontenttype = vc2respcontenttype;
    }

    public int getNumauthtype() {
        return numauthtype;
    }

    public void setNumauthtype(int numauthtype) {
        this.numauthtype = numauthtype;
    }

    public int getNumratelimittype() {
        return numratelimittype;
    }

    public void setNumratelimittype(int numratelimittype) {
        this.numratelimittype = numratelimittype;
    }

    public int getNumcateindex() {
        return numcateindex;
    }

    public void setNumcateindex(int numcateindex) {
        this.numcateindex = numcateindex;
    }

    public String getVc2shortdesc() {
        return vc2shortdesc;
    }

    public void setVc2shortdesc(String vc2shortdesc) {
        this.vc2shortdesc = vc2shortdesc;
    }

    public String getVc2maindesc() {
        return vc2maindesc;
    }

    public void setVc2maindesc(String vc2maindesc) {
        this.vc2maindesc = vc2maindesc;
    }

    public String getVc2cautions() {
        return vc2cautions;
    }

    public void setVc2cautions(String vc2cautions) {
        this.vc2cautions = vc2cautions;
    }

    public int getNumenable() {
        return numenable;
    }

    public void setNumenable(int numenable) {
        this.numenable = numenable;
    }

    public int getNumstatus() {
        return numstatus;
    }

    public void setNumstatus(int numstatus) {
        this.numstatus = numstatus;
    }

    public int getNumapitype() {
        return numapitype;
    }

    public void setNumapitype(int numapitype) {
        this.numapitype = numapitype;
    }

    public String getVc2specname() {
        return vc2specname;
    }

    public void setVc2specname(String vc2specname) {
        this.vc2specname = vc2specname;
    }

    public List<Trequestparam> getTrequestparamSet() {
        return trequestparamSet;
    }

    public void setTrequestparamSet(List<Trequestparam> trequestparamSet) {
        this.trequestparamSet = trequestparamSet;
    }

    public List<Terrorcode> getTerrorcodeSet() {
        return terrorcodeSet;
    }

    public void setTerrorcodeSet(List<Terrorcode> terrorcodeSet) {
        this.terrorcodeSet = terrorcodeSet;
    }

    public Tspeccategory getNumcateid() {
        return numcateid;
    }

    public void setNumcateid(Tspeccategory numcateid) {
        this.numcateid = numcateid;
    }

    public Baseurl getNumbaseurlid() {
        return numbaseurlid;
    }

    public void setNumbaseurlid(Baseurl numbaseurlid) {
        this.numbaseurlid = numbaseurlid;
    }

    public List<Tresponse> getTresponseSet() {
        return tresponseSet;
    }

    public void setTresponseSet(List<Tresponse> tresponseSet) {
        this.tresponseSet = tresponseSet;
    }

    public Set<Tspec> getTspecSetOnMe() {
        return tspecSetOnMe;
    }

    public void setTspecSetOnMe(Set<Tspec> tspecSetOnMe) {
        this.tspecSetOnMe = tspecSetOnMe;
    }

    public Set<Tspec> getTspecSetOnWho() {
        return tspecSetOnWho;
    }

    public void setTspecSetOnWho(Set<Tspec> tspecSetOnWho) {
        this.tspecSetOnWho = tspecSetOnWho;
    }

    public String getResourcePath(){
        return (this.vc2version==null?"":this.vc2version)
                + (this.vc2mainresource==null?"":"/"+this.vc2mainresource)
                + (this.vc2subresource==null?"":"/"+this.vc2subresource);
    }

    public String getSpecTitle(){
        return "当前编辑：" + (this.numspecid==null?"":"#"+this.numspecid+" - ") + getResourcePath();
    }

    /**
     *  here comes the Enumerations Converter below
     */
    public AcceptType getEnumAcceptType(){
        return AcceptType.getValueById(this.getNumreqaccept());
    }
    public void setEnumAcceptType(AcceptType enm){
        this.setNumreqaccept(enm.getId());
    }
    public ApiStatus getEnumApiStatus(){
        return ApiStatus.getValueById(this.getNumstatus());
    }
    public void setEnumApiStatus(ApiStatus enm){
        this.setNumstatus(enm.getId());
    }
    public ApiType getEnumApiType(){
        return ApiType.getValueById(this.getNumapitype());
    }
    public void setEnumApiType(ApiType enm){
        this.setNumapitype(enm.getId());
    }
    public AuthType getEnumAuthType(){
        return AuthType.getValueById(this.getNumauthtype());
    }
    public void setEnumAuthType(AuthType enm){
        this.setNumauthtype(enm.getId());
    }
    public ContentType[] getEnumContentType(){
        return ContentType.getMultiValueByIds(this.getVc2respcontenttype(),",");
    }
    public void setEnumContentType(ContentType[] enmlst){
        this.setVc2respcontenttype(ContentType.getMultiIds(enmlst, ","));
    }
    public HttpMethod[] getEnumHttpMethod(){
        return HttpMethod.getMultiValueByIds(this.getVc2httpmethod(),",");
    }
    public void setEnumHttpMethod(HttpMethod[] enmlst){
        this.setVc2httpmethod(HttpMethod.getMultiIds(enmlst, ","));
    }
    public RateLimit getEnumRateLimit(){
        return RateLimit.getValueById(this.getNumratelimittype());
    }
    public void setEnumRateLimit(RateLimit enm){
        this.setNumratelimittype(enm.getId());
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numspecid != null ? numspecid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tspec)) {
            return false;
        }
        Tspec other = (Tspec) object;
        if ((this.numspecid == null && other.numspecid != null) || (this.numspecid != null && !this.numspecid.equals(other.numspecid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.weibo.api.toolbox.persist.entity.Tspec[numspecid=" + numspecid + "]";
    }

}
