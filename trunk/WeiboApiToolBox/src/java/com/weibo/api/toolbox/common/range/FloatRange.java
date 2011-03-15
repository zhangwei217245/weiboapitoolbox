package com.weibo.api.toolbox.common.range;

import java.text.DecimalFormat;

/**
 *
 * @author x-spirit
 */
public class FloatRange implements ParamRange<Float>{
    
    float min = Float.MIN_VALUE;
    float max = Float.MAX_VALUE;

    public FloatRange(){
        
    }

    public FloatRange(String range){
        String[] arr = range.split("~");
        this.min = Float.parseFloat(arr[0]);
        this.max = Float.parseFloat(arr[1]);
        if(this.max < this.min){
        	this.max = Float.MAX_VALUE;
        }
    }
    
    public FloatRange(float min, float max) {
        this.min = min;
        this.max = max;
        if(this.max < this.min){
        	this.max = Float.MAX_VALUE;
        }
    }
    

    @Override
    public boolean isInRange(Float value) {
        float v = value.floatValue();
        return (v>=min&&v<=max);
    }


    public String getEnumRangeDesc() {
        return "";
    }

    public String getRegExpDesc() {
        return "";
    }

    public String getBaseSample(){
        float d = getRandomValue();
        DecimalFormat nf = new DecimalFormat("######.000");
        return nf.format(d);
    }

    public float getRandomValue(){
        int i = rdm.nextInt(100);
        float d = this.min+((float)i);
        while(!isInRange(d)){
            i = rdm.nextInt(100);
            d = this.min+((float)i);
        }
        return d;
    }

	@Override
	public String getDesc() {
		return min+"~"+max;
	}

}
