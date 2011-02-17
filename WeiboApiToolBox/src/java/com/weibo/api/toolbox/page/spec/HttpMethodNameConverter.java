/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.page.spec;

import com.weibo.api.toolbox.common.enumerations.HttpMethod;
import com.weibo.api.toolbox.persist.entity.Tspec;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listcell;

/**
 *
 * @author X-Spirit
 */
public class HttpMethodNameConverter implements TypeConverter{

    public Object coerceToUi(Object val, Component cmpnt) {
        Listcell lstcell = (Listcell)cmpnt;
        Tspec spec = (Tspec)val;
        HttpMethod[] arrm = HttpMethod.getMultiValueByIds(spec.getVc2httpmethod(), ",");
        StringBuilder sb = new StringBuilder();
        int i =0 ;
        for (HttpMethod m : arrm){
            if (++i>1){
                sb.append(",");
            }
            sb.append(m.getName());
        }
        return sb.toString();
    }

    public Object coerceToBean(Object o, Component cmpnt) {
        return null;
    }

}
