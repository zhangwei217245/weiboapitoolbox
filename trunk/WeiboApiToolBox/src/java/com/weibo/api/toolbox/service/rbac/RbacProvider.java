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

    Set<Tcategory> getAllEnableCategories(Collection<Tmenuitem> menulst);

    List<Tmenuitem> getAllEnableMenuItems();

    Set<Tmenuitem> getUsersEnableMenuItems(Tuser user);

    Set<Tmenuitem> getUsersEnableMenuItems(Tuser user, Collection<Tmenuitem> menulst);

    Tuser login(String userName, String passWord);

}
