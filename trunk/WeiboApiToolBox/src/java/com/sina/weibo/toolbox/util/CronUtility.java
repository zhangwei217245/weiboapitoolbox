/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sina.weibo.toolbox.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author X-Spirit
 */
public class CronUtility {

    /**
     * 简单地根据CRON表达式计算出调度时间间隔的毫秒数,只考虑到小时
     * @param cronexp
     * @return
     */
    public static long getSimpleIntervalMills(String cronexp) {
        long rst = 0;
        String[] params = cronexp.split(" ");
        for (int i = 0; i < 3; i++) {
            if (params[i].contains("/")) {
                String s = params[i].split("/")[1];
                int interval = Integer.parseInt(s);
                if (i == 0) {
                    rst += interval * 1000;//Second
                } else if (i == 1) {
                    rst += interval * 60 * 1000;//Minute
                } else if (i == 2) {
                    rst += interval * 60 * 60 * 1000;//Hour
                }
            }
        }
        return rst;
    }

    public static void main(String[] args) {
        Calendar cr = Calendar.getInstance();
        cr.setTimeInMillis(System.currentTimeMillis());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cr.getTime()));
        long l = getSimpleIntervalMills("0 0/5 * * * ?");
        System.out.println(l);
        long ls = new CronUtility().getSimpleIntervalMills("0/5 * * * * ?");
        System.out.println(ls);
    }
}
