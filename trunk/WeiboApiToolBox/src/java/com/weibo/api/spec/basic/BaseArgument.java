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
    private String fileBase;
    private String wikiBase;
    private String specBase;
    private String schemaBase;
    private String allDocDir;
    

    public String getFileBase() {
        return fileBase;
    }

    public void setFileBase(String fileBase) {
        this.fileBase = fileBase;
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

    public String getAllDocDir() {
        return allDocDir;
    }

    public void setAllDocDir(String allDocDir) {
        this.allDocDir = allDocDir;
    }

    public String getWadlBaseURI(){
        return getHostBase()
                +"/"+getAllDocDir()
                +"/"+getSpecBase();
    }
    public String getWadlFileBaseDir(){
        return getFileBase()
                +"/"+getAllDocDir()
                +"/"+getSpecBase();
    }
    public String getSchemaBaseURI(){
        return getHostBase()
                +"/"+getAllDocDir()
                +"/"+getSchemaBase();
    }

    public String getSchemaFileBaseDir(){
        return getFileBase()
                +"/"+getAllDocDir()
                +"/"+getSchemaBase();
    }
}
