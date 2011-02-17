/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.page.spec;

import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.service.spec.CategoryProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zul.AbstractTreeModel;

/**
 *
 * @author x-spirit
 */
public class SpecCategoryTreeModel extends AbstractTreeModel {

    private static final long serialVersionUID = 7603430853493214902L;
    CategoryProvider cp = (CategoryProvider) SpringUtil.getBean("categoryProvider");

    Map<Tspeccategory,List<Tspeccategory>> specCateMap = new ConcurrentHashMap<Tspeccategory, List<Tspeccategory>>();
    
    public SpecCategoryTreeModel(Object root) {
        super(root);
    }

    public boolean isLeaf(Object o) {
        Tspeccategory pcate = (Tspeccategory) o;
        List<Tspeccategory> subcates = specCateMap.get(pcate);
        if (subcates==null){
            boolean isEager = false;
            try {
                isEager = pcate.getTspeccategorySet()!=null&&pcate.getTspeccategorySet().size()>0;
            } catch (Exception e) {
            }
            if (!isEager) {
                return cp.getCateChildrenCount(pcate)<=0;
            }else{
                subcates = new ArrayList<Tspeccategory>();
                subcates.addAll(pcate.getTspeccategorySet());
            }
            specCateMap.put(pcate, subcates);
        }
        return subcates.isEmpty();
    }

    public Object getChild(Object o, int i) {
        Tspeccategory pcate = (Tspeccategory) o;
        List<Tspeccategory> subcates = specCateMap.get(pcate);
        if (subcates==null){
            boolean isEager = false;
            try {
                isEager = pcate.getTspeccategorySet()!=null&&pcate.getTspeccategorySet().size()>0;
            } catch (Exception e) {
            }
            if (!isEager) {
                subcates = cp.getCateChildren(pcate);
            }else{
                subcates = new ArrayList<Tspeccategory>();
                subcates.addAll(pcate.getTspeccategorySet());
            }
            if (subcates !=null && subcates.size()>0){
                specCateMap.put(pcate, subcates);
            }
        }
        return subcates.get(i);
    }

    public int getChildCount(Object o) {
        Tspeccategory pcate = (Tspeccategory) o;
        List<Tspeccategory> subcates = specCateMap.get(pcate);
        if (subcates==null){
            boolean isEager = false;
            try {
                isEager = pcate.getTspeccategorySet()!=null&&pcate.getTspeccategorySet().size()>0;
            } catch (Exception e) {
            }
            if (!isEager) {
                subcates = cp.getCateChildren(pcate);
            }else{
                subcates = new ArrayList<Tspeccategory>();
                subcates.addAll(pcate.getTspeccategorySet());
            }
            if (subcates !=null && subcates.size()>0){
                specCateMap.put(pcate, subcates);
            }
        }
        return subcates.size();
    }
}
