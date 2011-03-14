package com.weibo.api.spec.wiki;

import com.weibo.api.spec.basic.BaseArgument;
import com.weibo.api.toolbox.common.enumerations.HttpMethod;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tresponse;
import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.persist.qlgenerator.JPQLGenerator;
import com.weibo.api.toolbox.persist.qlgenerator.QLGenerator;
import com.weibo.api.toolbox.util.FreeMarkerUtils;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;


/**
 *
 * @author x-spirit
 */
public class WikiGeneratorImpl implements WikiGenerator{

    @Resource
    IJpaDaoService jpaDaoService;
    @Resource
    FreeMarkerUtils freeMarkerUtil;
    @Resource
    BaseArgument baseArgument;

    public void generateSpecWikiByCate(Tspeccategory cate){

        if (cate.getNumparentcateid().getNumcateid()==1){
            QLGenerator qlgen = new JPQLGenerator();
            qlgen.select("subcate").from("Tspeccategory subcate");
            qlgen.where(null, "subcate.numparentcateid = :pcate").where(null, "subcate.numenable=1");
            Map param = new HashMap();
            param.put("pcate", cate);
            List<Tspeccategory> subcateList = jpaDaoService.findEntities(qlgen.toString(), param, true, -1, -1);
            for(Tspeccategory subcate : subcateList){
                processSpecWikiBySubCate(subcate);
            }
        } else {
            processSpecWikiBySubCate(cate);
        }
    }

    private void processSpecWikiBySubCate(Tspeccategory subcate) {
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("spec").from("Tspec spec");
        qlgen.where(null, "spec.numcateid = :numcateid").where(null, "spec.numenable=1");
        Map param = new HashMap();
        param.put("numcateid", subcate);
        List<Tspec> speclist = jpaDaoService.findEntities(qlgen.toString(), param, true, -1, -1);
        if (ToolBoxUtil.isNotEmpty(speclist)){
            for (Tspec spec : speclist) {
                renderWikiForEachSpec(spec);
            }
        }
    }

    public String renderWikiForEachSpec(Tspec spec) {
        try {
            Map param = new HashMap();
            param.put("spec", spec);
            String outpath = getWikiOutPath(spec);
            Configuration cfg = new Configuration();
            String tempdir = "/WEB-INF/template";
            tempdir = Executions.getCurrent().getDesktop().getWebApp().getRealPath(tempdir);
            cfg.setDirectoryForTemplateLoading(new File(tempdir));
            Template template = cfg.getTemplate("wiki.ftl");
            processFormat(param,spec);
            processMethod(param,spec);
            freeMarkerUtil.processTemplateIntoFile(template, param, outpath, false);
            return outpath;
        } catch (IOException ex) {
            Logger.getLogger(WikiGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TemplateException ex) {
            Logger.getLogger(WikiGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void processFormat(Map param, Tspec spec) {
        List<Tresponse> tresponseSet = spec.getTresponseSet();
        StringBuilder sb = new StringBuilder();
        if (ToolBoxUtil.isNotEmpty(tresponseSet)){
            for (Tresponse tres : tresponseSet) {
                String name = tres.getEnumContentType().getName();
                sb.append(name).append(",");
            }
        }
        param.put("format", sb.substring(0, sb.length()-1));
    }

    private void processMethod(Map param, Tspec spec) {
        HttpMethod[] enumHttpMethod = spec.getEnumHttpMethod();
        StringBuilder sb = new StringBuilder();
        for (HttpMethod httpMethod : enumHttpMethod) {
            sb.append(httpMethod.getName()).append(",");
        }
        param.put("httpMethod", sb.substring(0, sb.length()-1));
    }

    public String getWikiOutPath(Tspec spec){
        return baseArgument.getWikiFileBaseDir() + "/" + spec.getVc2version()
                     + "/" + spec.getNumcateid().getNumparentcateid().getVc2catename()
                     + "/" + spec.getNumcateid().getVc2catename()
                     + "/" + spec.getEnumApiType().getName()
                     + "/" + spec.getEnumApiStatus().getName()
                     + "/" + spec.getNumspecid()+"_"+spec.getResourcePath()+".wiki";
    }
    
    public void generateMenuByParentCate(Tspeccategory pcate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
