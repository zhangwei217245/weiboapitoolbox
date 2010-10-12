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
@Table(name = "globalparam")
@NamedQueries({
    @NamedQuery(name = "Globalparam.findAll", query = "SELECT g FROM Globalparam g"),
    @NamedQuery(name = "Globalparam.findByVc2paramname", query = "SELECT g FROM Globalparam g WHERE g.vc2paramname = :vc2paramname"),
    @NamedQuery(name = "Globalparam.findByVc2paramvalue", query = "SELECT g FROM Globalparam g WHERE g.vc2paramvalue = :vc2paramvalue"),
    @NamedQuery(name = "Globalparam.findByNumconfigid", query = "SELECT g FROM Globalparam g WHERE g.numconfigid = :numconfigid"),
    @NamedQuery(name = "Globalparam.findByNumgloblalparamid", query = "SELECT g FROM Globalparam g WHERE g.numgloblalparamid = :numgloblalparamid")})
public class Globalparam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "vc2paramname", nullable = false, length = 100)
    private String vc2paramname;
    @Basic(optional = false)
    @Column(name = "vc2paramvalue", nullable = false, length = 500)
    private String vc2paramvalue;
    @Basic(optional = false)
    @Column(name = "numconfigid", nullable = false)
    private int numconfigid;
    @Id
    @Basic(optional = false)
    @Column(name = "numgloblalparamid", nullable = false)
    private Long numgloblalparamid;

    public Globalparam() {
    }

    public Globalparam(Long numgloblalparamid) {
        this.numgloblalparamid = numgloblalparamid;
    }

    public Globalparam(Long numgloblalparamid, String vc2paramname, String vc2paramvalue, int numconfigid) {
        this.numgloblalparamid = numgloblalparamid;
        this.vc2paramname = vc2paramname;
        this.vc2paramvalue = vc2paramvalue;
        this.numconfigid = numconfigid;
    }

    public String getVc2paramname() {
        return vc2paramname;
    }

    public void setVc2paramname(String vc2paramname) {
        this.vc2paramname = vc2paramname;
    }

    public String getVc2paramvalue() {
        return vc2paramvalue;
    }

    public void setVc2paramvalue(String vc2paramvalue) {
        this.vc2paramvalue = vc2paramvalue;
    }

    public int getNumconfigid() {
        return numconfigid;
    }

    public void setNumconfigid(int numconfigid) {
        this.numconfigid = numconfigid;
    }

    public Long getNumgloblalparamid() {
        return numgloblalparamid;
    }

    public void setNumgloblalparamid(Long numgloblalparamid) {
        this.numgloblalparamid = numgloblalparamid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numgloblalparamid != null ? numgloblalparamid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Globalparam)) {
            return false;
        }
        Globalparam other = (Globalparam) object;
        if ((this.numgloblalparamid == null && other.numgloblalparamid != null) || (this.numgloblalparamid != null && !this.numgloblalparamid.equals(other.numgloblalparamid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sina.weibo.toolbox.data.entity.Globalparam[numgloblalparamid=" + numgloblalparamid + "]";
    }

}
