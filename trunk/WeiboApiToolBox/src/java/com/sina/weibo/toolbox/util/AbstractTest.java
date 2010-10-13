/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sina.weibo.toolbox.util;

/**
 *
 * @author X-Spirit
 */
public class AbstractTest {

    static {
        System.out.println("FC static init");
    }

    {
        System.out.println("FC block init");
    }

    public AbstractTest() {
        System.out.println("FC constructor init");
    }
}
