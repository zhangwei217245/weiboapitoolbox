/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.service.spec;

import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import java.util.List;

/**
 *
 * @author x-spirit
 */
public interface CategoryProvider {

    List<Tspeccategory> getAllSubCate();
    
    List<Tspeccategory> getCateChildren(Tspeccategory pkg);

    List<Tspeccategory> getCateLevelOne();

    void persistCategory(Tspeccategory speccate);

    Tspeccategory getRootCate();

    int getCateChildrenCount(Tspeccategory pkg);
}
