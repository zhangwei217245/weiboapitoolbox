/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author x-spirit
 */
public class FreeMarkerTemplateUtils extends org.springframework.ui.freemarker.FreeMarkerTemplateUtils {

    public static void processTemplateIntoFile(Template template, Object model, String filepath, boolean append)
            throws IOException, TemplateException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(filepath, append));
        template.process(model, pw);
    }
}
