package com.weibo.api.toolbox.common.range;

/**
 *
 * @author x-spirit
 */
public class InfinitRange implements ParamRange<Object> {

    @Override
    public boolean isInRange(Object value) {
        return true;
    }

    public String getNumricRangeDesc() {
        return "范围：任意值";
    }

    public String getEnumRangeDesc() {
        return "范围：任意值";
    }

    public String getRegExpDesc() {
        return "范围：任意值";
    }

    public String getBaseSample() {
        return "Data_For_Testing";
    }

    @Override
    public String getDesc() {
        return "anything";
    }
}
