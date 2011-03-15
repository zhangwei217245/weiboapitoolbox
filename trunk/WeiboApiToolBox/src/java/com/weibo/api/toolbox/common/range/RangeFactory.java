/**
 * 
 */
package com.weibo.api.toolbox.common.range;

import com.weibo.api.toolbox.common.enumerations.DataTypes;
import org.zkoss.lang.Strings;

/**
 * @author yuanming@staff.sina.com.cn
 *
 */
public class RangeFactory {

    public static ParamRange getRangeInstance(DataTypes type, String range) {
        if (Strings.isEmpty(range) || Strings.isBlank(range)) {
            return null;
        }
        ParamRange result = null;
        switch (type) {
            case INT:
                result = new IntRange(range.trim());
                break;
            case INT64:
                result = new LongRange(range.trim());
                break;
            case FLOAT:
                result = new FloatRange(range.trim());
                break;
            case FLOAT64:
                result = new DoubleRange(range.trim());
                break;
            case STRING:
                result = new StringRange(range.trim());
                break;
            case ENUM:
                result = new EnumRange(range.trim());
                break;
            default:
                result = new InfinitRange();
        }
        return result;
    }
}
