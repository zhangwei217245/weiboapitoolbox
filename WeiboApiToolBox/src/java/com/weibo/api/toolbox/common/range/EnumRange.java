package com.weibo.api.toolbox.common.range;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author x-spirit
 * @author yuanming@staff.sina.com.cn
 */
public class EnumRange implements ParamRange<String> {
	String[] enums;
	Set<String> set = new HashSet<String>();

	public EnumRange(String[] values) {
		this.enums = values;
		set.addAll(Arrays.asList(this.enums));
	}

	public EnumRange(String values) {
		this(values, ",");
	}

	public EnumRange(String values, String sepStr) {
		String[] vals = values.split(sepStr);
		enums = new String[vals.length];
		for (int i = 0; i < enums.length; i++) {
			enums[i] = vals[i].trim();
		}
		set.addAll(Arrays.asList(this.enums));
	}

	@Override
	public boolean isInRange(String value) {
		return set.contains(value);
	}

	@Override
	public String getBaseSample() {
		return enums[0];
	}

	public String getRandomValue() {
		int idx = rdm.nextInt(enums.length);
		while (idx < 0) {
			idx = rdm.nextInt(enums.length);
		}
		return enums[idx];
	}

	@Override
	public String getDesc() {
		StringBuilder rst = new StringBuilder("可选值：");
		int i = 0;
		for (String s : enums) {
			i++;
			rst.append(s);
			if (i < enums.length) {
				rst.append(",");
			}
		}
		return rst.toString();
	}

}