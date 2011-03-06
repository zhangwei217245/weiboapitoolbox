/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.util;

import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tenumgroup;
import com.weibo.api.toolbox.persist.entity.Tuser;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author x-spirit
 */
public class ToolBoxUtil {

    public static boolean isEmpty(String s) {
        return s == null || s.length() <= 0;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean isEmpty(Collection lst) {
        return lst == null || lst.size() <= 0;
    }

    public static boolean isNotEmpty(Collection lst) {
        return lst != null && lst.size() > 0;
    }

    public static Tdatastruct getNonStruct(){
        IJpaDaoService jpaDaoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");
        return jpaDaoService.findOneEntityById(Tdatastruct.class, 1);
    }

    public static Tenumgroup getNonEnumGroup(){
        IJpaDaoService jpaDaoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");
        return jpaDaoService.findOneEntityById(Tenumgroup.class, 1);
    }

    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] subfiles = file.listFiles();
            for (File f : subfiles) {
                deleteFile(f);
            }
        }
        System.out.println("Delete: " + file.getName());
        file.delete();
    }

    public static Tuser getCurrentUser(){
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
        HttpSession sess = request.getSession();
        if (sess != null) {
            return (Tuser) request.getSession().getAttribute("user");
        }
        return null;
    }

    public static void  setCreateUserInfo(Object obj){
        try {
            Field fd_datcreated = obj.getClass().getDeclaredField("datcreated");
            fd_datcreated.setAccessible(true);
            fd_datcreated.set(obj, new Date());
            Field fd_numcreateduser = obj.getClass().getDeclaredField("numcreateduser");
            fd_numcreateduser.setAccessible(true);
            fd_numcreateduser.set(obj, getCurrentUser());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ToolBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ToolBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(ToolBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ToolBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setUpdateUserInfo(Object obj){
        try {
            Field fd_datupdated = obj.getClass().getDeclaredField("datupdated");
            fd_datupdated.setAccessible(true);
            fd_datupdated.set(obj, new Date());
            Field fd_numupdateduser = obj.getClass().getDeclaredField("numupdateduser");
            fd_numupdateduser.setAccessible(true);
            fd_numupdateduser.set(obj, getCurrentUser());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ToolBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ToolBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(ToolBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ToolBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
