package com.weibo.api.toolbox.common.range;

import java.text.DecimalFormat;

/**
 *
 * @author x-spirit
 */
public class DoubleRange implements ParamRange<Double>{
    double min = Double.MIN_VALUE;
    double max = Double.MAX_VALUE;

    public DoubleRange(){

    }
    
    public DoubleRange(String range){
        String[] arr = range.split("~");
        this.min = Double.parseDouble(arr[0]);
        this.max = Double.parseDouble(arr[1]);
        if(this.max < this.min){
        	this.max = Double.MAX_VALUE;
        }
    }

    public DoubleRange(double min, double max) {
        this.min = min;
        this.max = max;
        if(this.max < this.min){
        	this.max = Double.MAX_VALUE;
        }
    }

    @Override
    public boolean isInRange(Double value) {
        double v = value.doubleValue();
        //FIXME 比较?
        return (v>=min&&v<=max);
    }

    
    public boolean isValidate(String value){
    	try{
    		double v = Double.parseDouble(value);
    		return this.isInRange(v);
    	}catch(Exception e){
    		return false;
    	}
    }

    public String getBaseSample(){
        double d = getRandomValue();
        DecimalFormat nf = new DecimalFormat("######.000");
        return nf.format(d);
    }

    public double getRandomValue(){
        int i = rdm.nextInt(100);
        double d = this.min+((double)i);
        while(!isInRange(d)){
            i = rdm.nextInt(100);
            d = this.min+((double)i);
        }
        return d;
    }

	@Override
	public String getDesc() {
		return  min+"~"+max;
	}
}
