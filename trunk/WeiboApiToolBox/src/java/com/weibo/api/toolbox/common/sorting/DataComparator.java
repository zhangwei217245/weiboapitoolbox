/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.common.sorting;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author x-spirit
 */
public abstract class DataComparator implements Comparator, Serializable{

    private static final long serialVersionUID = -8998126188005498421L;
    private boolean asc = true;
    private int type = 0;

    public DataComparator(boolean asc, int type) {
        this.asc = asc;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public abstract int compare(Object o1, Object o2);
}
