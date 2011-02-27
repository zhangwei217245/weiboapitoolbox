/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.spec.basic;

/**
 *
 * @author x-spirit
 */
public class BaseArgument {
    private String hostBase;
    private String docBase;
    private String wikiBase;
    private String specBase;
    private String schemaBase;
    

    public String getDocBase() {
        return docBase;
    }

    public void setDocBase(String docBase) {
        this.docBase = docBase;
    }

    public String getSchemaBase() {
        return schemaBase;
    }

    public void setSchemaBase(String schemaBase) {
        this.schemaBase = schemaBase;
    }

    public String getSpecBase() {
        return specBase;
    }

    public void setSpecBase(String specBase) {
        this.specBase = specBase;
    }

    public String getWikiBase() {
        return wikiBase;
    }

    public void setWikiBase(String wikiBase) {
        this.wikiBase = wikiBase;
    }

    public String getHostBase() {
        return hostBase;
    }

    public void setHostBase(String hostBase) {
        this.hostBase = hostBase;
    }
}
