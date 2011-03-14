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
import javax.annotation.Resource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 *
 * @author x-spirit
 */
public class FreeMarkerUtils extends FreeMarkerTemplateUtils{

    @Resource
    FreeMarkerConfigurer freeMarkerConfigurer;

    public void processTemplateIntoFile(Template template, Object model,String filepath,boolean append)
                    throws IOException, TemplateException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(filepath,append));
        template.process(model, pw);
    }
    public void processTemplateIntoFile(String tmplpath, Object model,String filepath, boolean append)throws IOException, TemplateException{
        PrintWriter pw = new PrintWriter(new FileOutputStream(filepath,append));

        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(tmplpath);
        template.process(model, pw);
    }

}
