/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.util;

import com.keypoint.PngEncoder;
import java.awt.image.BufferedImage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author x-spirit
 */
public class ChartUtility {
    public static final String ChartImageKey="ChartImage";
    public static final String ImageMapKey="imagemap0";

    public static void printSessionChartAsPNG(HttpServletRequest request,HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        BufferedImage chartImage = (BufferedImage) session.getAttribute(ChartImageKey);
        // set the content type so the browser can see this as a picture
        response.setContentType("image/png");
        // send the picture
        PngEncoder encoder = new PngEncoder(chartImage, false, 0, 9);
        response.getOutputStream().write(encoder.pngEncode());
        session.setAttribute(ChartImageKey, null);
    }
    
}
