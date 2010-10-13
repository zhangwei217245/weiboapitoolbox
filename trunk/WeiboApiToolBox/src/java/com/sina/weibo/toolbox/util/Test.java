/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sina.weibo.toolbox.util;

/**
 *
 * @author X-Spirit
 */
public class Test extends AbstractTest {

    private int a = 0;
    private static int b = 0;

    static {
        System.out.println("CC static init");
    }

    {
        System.out.println("CC block init");
    }

    public Test() {
        System.out.println("CC constructor init");
    }

    public static void main(String[] args) {
        System.out.println("ProgramStart");
        new Test();
    }

    protected static class InnerOne {

        public static int cal() {
            return b;
        }
    }
}
