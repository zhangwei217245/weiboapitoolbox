/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.service.rbac;

import static com.weibo.api.toolbox.util.ToolBoxUtil.*;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tcategory;
import com.weibo.api.toolbox.persist.entity.Tgroup;
import com.weibo.api.toolbox.persist.entity.Tmenuitem;
import com.weibo.api.toolbox.persist.entity.Tuser;
import com.weibo.api.toolbox.persist.qlgenerator.JPQLGenerator;
import com.weibo.api.toolbox.persist.qlgenerator.QLGenerator;
import com.weibo.api.toolbox.util.LDAPUtil;
import com.weibo.api.toolbox.util.MD5Util;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author x-spirit
 */
@Service("rbacProvider")
public class RbacProviderImpl implements RbacProvider {

    @Resource
    IJpaDaoService jpaDaoService;
    @Resource
    QLGenerator jpqlGenerator;

    public List<Tcategory> getAllCateList() {
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("t").from("Tcategory t").orderBy("t.numindex", "ASC");
        return jpaDaoService.findEntities(qlgen.toString(), null, true, -1, -1);
    }

    public List<Tmenuitem> getAllMenuByCate(Tcategory cate) {
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("t").from("Tmenuitem t").where(null, "t.numcateid = :numcateid").orderBy("t.numindex", "ASC");
        Map param = new HashMap();
        param.put("numcateid", cate);
        return jpaDaoService.findEntities(qlgen.toString(), param, true, -1, -1);
    }

    public Tuser login(String userName, String passWord) {
        jpqlGenerator.init();
        Map<String, Object> param = new HashMap<String, Object>();
        jpqlGenerator.select("t").from("Tuser t");
        boolean ldapAuth = false;
        if (userName.toLowerCase().contains("@staff.sina")) {
            ldapAuth = new LDAPUtil(userName.substring(0, userName.indexOf('@')), passWord).checkAuth();
        }
        if (ldapAuth) {
            param.put("vc2email", userName);
            jpqlGenerator.where("AND", "t.vc2email = :vc2email");
        } else {
            jpqlGenerator.where(null, "t.vc2password = :vc2password");
            param.put("vc2password", MD5Util.md5Digest(passWord));
            if (userName.contains("@")) {
                param.put("vc2email", userName);
                jpqlGenerator.where("AND", "t.vc2email = :vc2email");
            } else {
                param.put("vc2username", userName);
                jpqlGenerator.where("AND", "t.vc2username = :vc2username");
            }
        }
        List userlst = jpaDaoService.findEntities(jpqlGenerator.toString(), param, true, -1, -1);
        if (isNotEmpty(userlst)) {
            return (Tuser) userlst.get(0);
        }
        return null;
    }

    public List<Tmenuitem> getAllEnableMenuItems() {
        String ql = "SELECT t FROM Tmenuitem t WHERE t.numenable = :numenable";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("numenable", 1);
        return jpaDaoService.findEntities(ql, param, true, -1, -1);
    }

    public Set<Tcategory> getAllEnableCategories(Collection<Tmenuitem> menulst) {
        Set<Tcategory> categories = new HashSet<Tcategory>();
        if (isNotEmpty(menulst)) {
            for (Tmenuitem menu : menulst) {
                Tcategory numcateid = menu.getNumcateid();
                if (numcateid.getNumenable() == 1) {
                    categories.add(numcateid);
                }
            }
        }
        return categories;
    }

    public Set<Tmenuitem> getUsersEnableMenuItems(Tuser user) {
        List<Tmenuitem> menulst = getAllEnableMenuItems();
        Set<Tmenuitem> userMenus = getUsersEnableMenuItems(user, menulst);
        return userMenus;
    }

    public Set<Tmenuitem> getUsersEnableMenuItems(Tuser user, Collection<Tmenuitem> menulst) {
        Set<Tmenuitem> userMenus = new HashSet<Tmenuitem>();
        if (isNotEmpty(menulst)) {
            for (Tmenuitem menu : menulst) {
                if (user.getNumsupervisor() <= 0) {
                    Set<Tgroup> usergroup = user.getTgroupSet();
                    Set<Tgroup> menugroup = menu.getTgroupSet();
                    for (Tgroup ugroup : usergroup) {
                        if (menugroup.contains(ugroup)) {
                            userMenus.add(menu);
                        }
                    }
                } else {
                    userMenus.add(menu);
                }
            }
        }
        return userMenus;
    }
}
