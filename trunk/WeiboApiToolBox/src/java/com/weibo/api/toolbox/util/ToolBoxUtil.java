/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.util;

import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.spring.SpringUtil;

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
}
