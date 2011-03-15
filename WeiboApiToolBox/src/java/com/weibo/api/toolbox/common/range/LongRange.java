/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.common.range;

/**
 *
 * @author x-spirit
 */
public class LongRange implements ParamRange<Long>{
    long min = Long.MIN_VALUE;
    long max = Long.MAX_VALUE;
    public LongRange(){
        
    }

    public LongRange(String range){
        String[] arr = range.split("~");
        this.min = Long.parseLong(arr[0]);
        this.max = Long.parseLong(arr[1]);
        if(this.max < this.min){
        	this.max = Long.MAX_VALUE;
        }
    }

    public LongRange(long min, long max) {
        this.min = min;
        this.max = max;
        if(this.max < this.min){
        	this.max = Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean isInRange(Long value) {
        long v = value.longValue();
        return (v>=min&&v<=max);
    }


    public String getBaseSample(){
        long d = getRandomValue();
        return String.valueOf(d);
    }

    public long getRandomValue(){
        int i = rdm.nextInt(100);
        long d = this.min+((long)i);
        while(!isInRange(d)){
            i = rdm.nextInt(100);
            d = this.min+((long)i);
        }
        return d;
    }

	@Override
	public String getDesc() {
		return min+"~"+max;
	}

}
