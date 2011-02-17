/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.common;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.impl.CaptchaEngine;

/**
 *
 * @author x-spirit
 */
public class MyCaptchaEngine implements CaptchaEngine{
    private static DefaultManageableImageCaptchaService dmic =  new DefaultManageableImageCaptchaService();
    public byte[] generateCaptcha(Object o) {
        byte[] captchaChallengeAsJpeg = null;
       // the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
        // get the session id that will identify the generated captcha.
        //the same id must be used to validate the response, the session id is a good candidate!
        //String captchaId = httpServletRequest.getSession().getId();
        String captchaId = o.toString();
        // call the ImageCaptchaService getChallenge method
            BufferedImage challenge =
                    dmic.getImageChallengeForID(captchaId,
                            Locale.CHINESE);

            // a jpeg encoder
            JPEGImageEncoder jpegEncoder =
                    JPEGCodec.createJPEGEncoder(jpegOutputStream);
            jpegEncoder.encode(challenge);
            captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(MyCaptchaEngine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ImageFormatException ex) {
            Logger.getLogger(MyCaptchaEngine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException e) {

        } catch (CaptchaServiceException e) {

        }
        return captchaChallengeAsJpeg;
    }

}
