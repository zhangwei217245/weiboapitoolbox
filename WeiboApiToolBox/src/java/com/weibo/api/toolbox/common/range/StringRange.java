package com.weibo.api.toolbox.common.range;

import java.util.regex.Pattern;


import nl.flotsam.xeger.Xeger;

/**
 *
 * @author x-spirit
 * @author yuanming@staff.sina.com.cn
 */
public class StringRange implements ParamRange<String> {

    private static char ch[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
        '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
        'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
        'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
        'z'};
    private String regExp;
    int min = -1;
    int max = -1;
    


    public StringRange(int minlength, int maxlength) {
        this.min = minlength;
        this.max = maxlength;
    }

    public StringRange(String regExp) {
        this.regExp = regExp;
    }

    @Override
    public boolean isInRange(String value) {
        boolean rst = true;
        int len = value.length();
        if (this.min >= 0 ) {
            rst = (len >= min);
            if(this.max >= 0){
            	rst = ((len<=max)&&rst);
            }
        }else if (this.regExp != null) {
            rst = Pattern.matches(regExp, value.toString());
        }
        return rst;
    }


    public String getBaseSample() {
        return "abc";
    }

    public String getRandomValue() {
        String randomStr = null;
        if (regExp != null && regExp.length() > 0) {
            Xeger generator = new Xeger(regExp);
            randomStr = generator.generate();
        } else {
            int poolen = ch.length;
            int length = max > 0 ? max : 1;
            char[] strbase = new char[length];
            for (int i = 0; i < length; i++) {
                strbase[i] = ch[rdm.nextInt(poolen)];
            }
            randomStr = new String(strbase);
        }
        return randomStr;
    }

	@Override
	public String getDesc() {
		if(this.regExp!=null){
			return "regexp:"+this.regExp;
		}
		return "str length：" + min + "~" + max;
	}
	

}
