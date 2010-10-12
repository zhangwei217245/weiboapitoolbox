/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.data.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author x-spirit
 */
@Entity
@Table(name = "loopaccount")
@NamedQueries({
    @NamedQuery(name = "Loopaccount.findAll", query = "SELECT l FROM Loopaccount l"),
    @NamedQuery(name = "Loopaccount.findByNumaccountid", query = "SELECT l FROM Loopaccount l WHERE l.numaccountid = :numaccountid"),
    @NamedQuery(name = "Loopaccount.findByVc2label", query = "SELECT l FROM Loopaccount l WHERE l.vc2label = :vc2label"),
    @NamedQuery(name = "Loopaccount.findByVc2accountuid", query = "SELECT l FROM Loopaccount l WHERE l.vc2accountuid = :vc2accountuid"),
    @NamedQuery(name = "Loopaccount.findByVc2screenName", query = "SELECT l FROM Loopaccount l WHERE l.vc2screenName = :vc2screenName"),
    @NamedQuery(name = "Loopaccount.findByVc2loginname", query = "SELECT l FROM Loopaccount l WHERE l.vc2loginname = :vc2loginname"),
    @NamedQuery(name = "Loopaccount.findByVc2password", query = "SELECT l FROM Loopaccount l WHERE l.vc2password = :vc2password"),
    @NamedQuery(name = "Loopaccount.findByVc2oauthtoken", query = "SELECT l FROM Loopaccount l WHERE l.vc2oauthtoken = :vc2oauthtoken"),
    @NamedQuery(name = "Loopaccount.findByVc2oauthsecret", query = "SELECT l FROM Loopaccount l WHERE l.vc2oauthsecret = :vc2oauthsecret")})
public class Loopaccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numaccountid", nullable = false)
    private Integer numaccountid;
    @Column(name = "vc2label", length = 50)
    private String vc2label;
    @Column(name = "vc2accountuid", length = 50)
    private String vc2accountuid;
    @Column(name = "vc2screen_name", length = 100)
    private String vc2screenName;
    @Column(name = "vc2loginname", length = 200)
    private String vc2loginname;
    @Column(name = "vc2password", length = 100)
    private String vc2password;
    @Column(name = "vc2oauthtoken", length = 50)
    private String vc2oauthtoken;
    @Column(name = "vc2oauthsecret", length = 50)
    private String vc2oauthsecret;

    public Loopaccount() {
    }

    public Loopaccount(Integer numaccountid) {
        this.numaccountid = numaccountid;
    }

    public Integer getNumaccountid() {
        return numaccountid;
    }

    public void setNumaccountid(Integer numaccountid) {
        this.numaccountid = numaccountid;
    }

    public String getVc2label() {
        return vc2label;
    }

    public void setVc2label(String vc2label) {
        this.vc2label = vc2label;
    }

    public String getVc2accountuid() {
        return vc2accountuid;
    }

    public void setVc2accountuid(String vc2accountuid) {
        this.vc2accountuid = vc2accountuid;
    }

    public String getVc2screenName() {
        return vc2screenName;
    }

    public void setVc2screenName(String vc2screenName) {
        this.vc2screenName = vc2screenName;
    }

    public String getVc2loginname() {
        return vc2loginname;
    }

    public void setVc2loginname(String vc2loginname) {
        this.vc2loginname = vc2loginname;
    }

    public String getVc2password() {
        return vc2password;
    }

    public void setVc2password(String vc2password) {
        this.vc2password = vc2password;
    }

    public String getVc2oauthtoken() {
        return vc2oauthtoken;
    }

    public void setVc2oauthtoken(String vc2oauthtoken) {
        this.vc2oauthtoken = vc2oauthtoken;
    }

    public String getVc2oauthsecret() {
        return vc2oauthsecret;
    }

    public void setVc2oauthsecret(String vc2oauthsecret) {
        this.vc2oauthsecret = vc2oauthsecret;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numaccountid != null ? numaccountid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loopaccount)) {
            return false;
        }
        Loopaccount other = (Loopaccount) object;
        if ((this.numaccountid == null && other.numaccountid != null) || (this.numaccountid != null && !this.numaccountid.equals(other.numaccountid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Loopaccount[numaccountid=" + numaccountid + "]";
    }

}
