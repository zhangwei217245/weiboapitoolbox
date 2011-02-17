package com.weibo.api.toolbox.page.spec;

import com.weibo.api.toolbox.persist.entity.Tspec;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import static com.weibo.api.toolbox.util.ToolBoxUtil.*;

/**
 *
 * @author x-spirit
 */
public class ResourceNameConvertor implements TypeConverter {

    public Object coerceToUi(Object val, Component cmpnt) {
        Tspec spec = (Tspec) val;
        String cmpntName = cmpnt.getClass().getName();
        String rst = (cmpntName.equals("org.zkoss.zul.Window")?"#"+spec.getNumspecid()+" - ":"")
                + (isEmpty(spec.getVc2version())?"":spec.getVc2version())
                + (isEmpty(spec.getVc2mainresource())?"":"/" + spec.getVc2mainresource())
                + (isEmpty(spec.getVc2subresource())?"":"/" + spec.getVc2subresource());
        return rst;
    }

    public Object coerceToBean(Object o, Component cmpnt) {
        return null;
    }
}
