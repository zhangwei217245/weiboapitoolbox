
package com.weibo.api.toolbox.common.range;

/**
 *
 * @author x-spirit
 * @author yuanming@staff.sina.com.cn
 */
public class IntRange implements ParamRange<Integer>{

    int min = Integer.MIN_VALUE;
    int max = Integer.MAX_VALUE;

    public IntRange(){
    }

    public IntRange(String range){
        String[] arr = range.split("~");
        this.min = Integer.parseInt(arr[0]);
        this.max = Integer.parseInt(arr[1]);
        if(this.max < this.min){
        	this.max = Integer.MAX_VALUE;
        }
    }
    
    public IntRange(int min, int max) {
        this.min = min;
        this.max = max;
        if(this.max < this.min){
        	this.max = Integer.MAX_VALUE;
        }
    }

    @Override
    public boolean isInRange(Integer value) {
        int v = value.intValue();
        return (v>=min&&v<=max);
    }

    public String getBaseSample(){
        int d = getRandomValue();
        return d+"";
    }

    public int getRandomValue(){
        int d = rdm.nextInt(max);
        while(!isInRange(d)){
            d = rdm.nextInt(max);
        }
        return d;
    }

	@Override
	public String getDesc() {
		return min+"~"+max;
	}

}
