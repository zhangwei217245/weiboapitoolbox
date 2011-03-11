/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.service.rbac;

import com.weibo.api.toolbox.persist.entity.Tcategory;
import com.weibo.api.toolbox.persist.entity.Tmenuitem;
import com.weibo.api.toolbox.persist.entity.Tuser;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 * @author x-spirit
 */
public interface RbacProvider {

    List<Tcategory> getAllCateList();

    List<Tmenuitem> getAllMenuByCate(Tcategory cate);

    Set<Tcategory> getAllEnableCategories(Collection<Tmenuitem> menulst);

    List<Tmenuitem> getAllEnableMenuItems();

    Set<Tmenuitem> getUsersEnableMenuItems(Tuser user);

    Set<Tmenuitem> getUsersEnableMenuItems(Tuser user, Collection<Tmenuitem> menulst);

    Tuser login(String userName, String passWord);

    void updateMenuIndex(Tmenuitem menu, int oldindex,int newindex);

    void changeMenuCate(Tcategory cate,Tmenuitem menu);

    int getNextIndexForCate();

    int getNextIndexForMenu(Tcategory cate);

    boolean checkDuplicatedIndexForCate(Tcategory cate,int index);

    boolean checkDuplicatedIndexForMenu(Tmenuitem menu,int index);
}
