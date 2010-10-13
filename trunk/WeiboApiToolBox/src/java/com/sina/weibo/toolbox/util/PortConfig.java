/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sina.weibo.toolbox.util;

/**
 *
 * @author X-Spirit
 */
public class PortConfig {

    private int httpPort ;
    private int httpsPort ;

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public void setHttpsPort(int httpsPort) {
        this.httpsPort = httpsPort;
    }

    

    public int getHttpPort() {
        return httpPort;
    }

    public int getHttpsPort() {
        return httpsPort;
    }
}
