package com.weibo.api.toolbox.common.range;

import java.util.Random;

/**
 * 
 * @author x-spirit
 * @author yuanming@staff.sina.com.cn
 */
public interface ParamRange<T> {
	Random rdm = new Random();

	public boolean isInRange(T value);

//	public String getNumricRangeDesc();

//	public String getEnumRangeDesc();

//	public String getRegExpDesc();
	
	public String getDesc();

	public String getBaseSample();
}
