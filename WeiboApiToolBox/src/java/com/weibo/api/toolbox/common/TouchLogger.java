/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.common;

import com.opensymphony.clickstream.Clickstream;
import com.opensymphony.clickstream.ClickstreamRequest;
import com.opensymphony.clickstream.logger.ClickstreamLogger;
import com.weibo.api.toolbox.persist.entity.Tuser;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.zkoss.util.logging.Log;


/**
 *
 * @author x-spirit
 */
public class TouchLogger implements ClickstreamLogger {

    private static final Log log = Log.lookup(TouchLogger.class);

    /**
     * 由于行为采集将是一个比较消耗资源的操作(用户量大，点击量大)，所以在优化方面
     * 可以考虑以下方法：
     * 1.每web服务器将日志信息记录在本地
     * 2.对于用户每次登录，并不是每作一次操作都要进行一次db的数据插入，而是当该用户的session过期的时候再进行，这样避免
     * 多次的数据库操作。但是考虑到数据采集的真实性需要在每次点击的时候将点击时间进行记录
     *
     */
    public void log(Clickstream cs) {
        log.info("session超时,记录日志中～～～");
        List list = cs.getStream();
        HttpSession session = cs.getSession();
        Tuser user = (Tuser) session.getAttribute("user");
        String userName = user.getVc2realname();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            ClickstreamRequest cr = (ClickstreamRequest) iter.next();
            String servletPath = null;
            if (cr.getQueryString() == null) {
                servletPath = cr.getRequestURI();
            } else {
                servletPath = cr.getRequestURI() + "?" + cr.getQueryString();
            }
            String ip = cr.getRemoteUser();
            log.info("记录日志中：userName:" + userName + ",IP:" + ip + ",servletPath:" + servletPath);
        }
    }
}
