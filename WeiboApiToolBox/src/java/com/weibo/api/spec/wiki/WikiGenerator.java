/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.spec.wiki;

import com.weibo.api.toolbox.persist.entity.Tspeccategory;

/**
 *
 * @author x-spirit
 */
public interface WikiGenerator {

    void generateMenuByParentCate(Tspeccategory pcate);
    void generateSpecWikiByCate(Tspeccategory cate);
}
