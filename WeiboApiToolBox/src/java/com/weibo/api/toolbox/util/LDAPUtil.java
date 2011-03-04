/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.util;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author x-spirit
 */
public class LDAPUtil {
    //环境设定

    private Hashtable env = null;
    //目录
    DirContext ctx = null;
    //是否能login
    boolean bLogin = false;
    //更改是否成功
    boolean getAttr = false;

    public LDAPUtil(String name, String password) {
        env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://10.210.96.10");
        env.put(Context.SECURITY_AUTHENTICATION, "Simple");
        env.put(Context.SECURITY_PRINCIPAL, "uid=" + name + ",ou=people,o=staff.sina.com.cn,o=usergroup");
        env.put(Context.SECURITY_CREDENTIALS, password);
    }//end ADAuth()

    public LDAPUtil() {
        this("Administrator", "xxxxxxx");
    }

    public boolean checkAuth() {


        try {

            ctx = new InitialDirContext(env);

            bLogin = true;

        } catch (javax.naming.AuthenticationException authe) {

            bLogin = false;

        } catch (Exception e) {
        } finally {
            try {
                ctx.close();

            } catch (Exception Ignore) {
            }
        }
        return bLogin;
    }

    public boolean getAttribute(String a) {

        //设定要取得的attribute
        String[] attrIds = {"displayName"};

        try {

            ctx = new InitialDirContext(env);
            Attributes Atb = ctx.getAttributes("cn=users,DC=EEE,DC=abc,DC=com,DC=tw", attrIds);
            NamingEnumeration<? extends Attribute> all = Atb.getAll();
            while(all.hasMore()){
                System.out.println(all.next().get());
            }
            getAttr = true;

        } catch (javax.naming.AuthenticationException authe) {

            getAttr = false;

        } catch (Exception e) {


            getAttr = false;
            System.out.println(e);

        } finally {

            try {
                ctx.close();

            } catch (Exception Ignore) {
            }
        }
        return getAttr;
    }

    public static void main(String[] args) {
        LDAPUtil lDAPUtil = new LDAPUtil("zhangwei13", "WDMMQSN@19840217");
        boolean auth = lDAPUtil.checkAuth();
        System.out.println(auth);
        boolean attribute = lDAPUtil.getAttribute("a");
    }
}
