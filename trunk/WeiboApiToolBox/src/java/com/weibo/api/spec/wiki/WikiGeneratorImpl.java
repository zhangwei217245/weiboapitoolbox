package com.weibo.api.spec.wiki;

import com.weibo.api.spec.basic.BaseArgument;
import com.weibo.api.toolbox.common.enumerations.AcceptType;
import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.common.enumerations.HttpMethod;
import com.weibo.api.toolbox.common.range.DoubleRange;
import com.weibo.api.toolbox.common.range.FloatRange;
import com.weibo.api.toolbox.common.range.IntRange;
import com.weibo.api.toolbox.common.range.LongRange;
import com.weibo.api.toolbox.common.range.RangeFactory;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Sysparam;
import com.weibo.api.toolbox.persist.entity.Trequestparam;
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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author x-spirit
 */
public class WikiGeneratorImpl implements WikiGenerator {

    @Resource
    IJpaDaoService jpaDaoService;
    @Resource
    FreeMarkerUtils freeMarkerUtil;
    @Resource
    BaseArgument baseArgument;

    public void generateSpecWikiByCate(Tspeccategory cate) {

        if (cate.getNumparentcateid().getNumcateid() == 1) {
            QLGenerator qlgen = new JPQLGenerator();
            qlgen.select("subcate").from("Tspeccategory subcate");
            qlgen.where(null, "subcate.numparentcateid = :pcate").where(null, "subcate.numenable=1");
            Map param = new HashMap();
            param.put("pcate", cate);
            List<Tspeccategory> subcateList = jpaDaoService.findEntities(qlgen.toString(), param, true, -1, -1);
            for (Tspeccategory subcate : subcateList) {
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
        if (ToolBoxUtil.isNotEmpty(speclist)) {
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
            processFormat(param, spec);
            processMethod(param, spec);
            processSysparam(param, spec);
            processSyserror(param, spec);
            processCurlDemo(param, spec);
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
        if (ToolBoxUtil.isNotEmpty(tresponseSet)) {
            for (Tresponse tres : tresponseSet) {
                String name = tres.getEnumContentType().getName();
                sb.append(name).append(",");
            }
        }
        param.put("format", sb.substring(0, sb.length() - 1));
    }

    private void processMethod(Map param, Tspec spec) {
        HttpMethod[] enumHttpMethod = spec.getEnumHttpMethod();
        StringBuilder sb = new StringBuilder();
        for (HttpMethod httpMethod : enumHttpMethod) {
            sb.append(httpMethod.getName()).append(",");
        }
        param.put("httpMethod", sb.substring(0, sb.length() - 1));
    }

    private void processSysparam(Map param, Tspec spec) {
        Tspeccategory numparentcateid = spec.getNumcateid().getNumparentcateid();
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("s").from("Sysparam s").where(null, "s.numenable=1");
        qlgen.where(null, "s.numcateid = :numcateid");
        Map queryparams = new HashMap();
        queryparams.put("numcateid", numparentcateid);
        List sysParams = jpaDaoService.findEntities(qlgen.toString(), queryparams, true, -1, -1);
        param.put("sysparam", sysParams);
    }

    private void processSyserror(Map param, Tspec spec) {
        Tspeccategory numparentcateid = spec.getNumcateid().getNumparentcateid();
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("s").from("Syserror s").where(null, "s.numenable=1");
        qlgen.where(null, "s.numcateid = :numcateid");
        Map queryparams = new HashMap();
        queryparams.put("numcateid", numparentcateid);
        List sysParams = jpaDaoService.findEntities(qlgen.toString(), queryparams, true, -1, -1);
        param.put("syserror", sysParams);
    }

    private void processCurlDemo(Map param, Tspec spec) {
        HttpMethod[] enumHttpMethod = spec.getEnumHttpMethod();
        StringBuilder qpsb = new StringBuilder();
        getQueryParamString(qpsb,param,spec);
        for (HttpMethod httpMethod : enumHttpMethod) {
            StringBuilder sb = new StringBuilder();
            StringBuilder ppsb = new StringBuilder();
            switch (httpMethod) {
                case GET:
                    sb.append("\"");
                    sb.append(spec.getNumbaseurlid().getVc2baseurl());
                    sb.append("/").append(spec.getResourcePath());
                    sb.append("?").append(qpsb.deleteCharAt(qpsb.length()-1));
                    sb.append("\"");
                    param.put("CURL_GET", sb.toString());
                    break;
                case POST:
                    if (spec.getEnumAcceptType().equals(AcceptType.FORM_MULTIPART)) {
                        String[] sl = qpsb.deleteCharAt(qpsb.length() - 1).toString().split("&");
                        for (String string : sl) {
                            ppsb.append("-F \"").append(string).append("\" ");
                        }
                    } else {
                        ppsb.append("-d \"").append(qpsb.deleteCharAt(qpsb.length() - 1)).append("\"");
                    }
                    sb.append(ppsb).append(" \"");
                    sb.append(spec.getNumbaseurlid().getVc2baseurl());
                    sb.append("/").append(spec.getResourcePath()).append("\"");
                    param.put("CURL_POST", sb.toString());
                    break;
                case PUT:
                    sb.append("-X PUT \"");
                    sb.append(spec.getNumbaseurlid().getVc2baseurl());
                    sb.append("/").append(spec.getResourcePath());
                    sb.append("?").append(qpsb.deleteCharAt(qpsb.length()-1));
                    sb.append("\"");
                    param.put("CURL_PUT", sb.toString());
                    break;
                case DELETE:
                    sb.append("-X DELETE \"");
                    sb.append(spec.getNumbaseurlid().getVc2baseurl());
                    sb.append("/").append(spec.getResourcePath());
                    sb.append("?").append(qpsb.deleteCharAt(qpsb.length()-1));
                    sb.append("\"");
                    param.put("CURL_DELETE", sb.toString());
                    break;
                default:
                    sb.append("\"");
                    sb.append(spec.getNumbaseurlid().getVc2baseurl());
                    sb.append("/").append(spec.getResourcePath());
                    sb.append("?").append(qpsb.deleteCharAt(qpsb.length()-1));
                    sb.append("\"");
                    param.put("CURL_GET", sb.toString());
            }
        }
    }

    private void getQueryParamString(StringBuilder qpsb, Map param, Tspec spec) {
        List<Sysparam> sysparam = (List<Sysparam>) param.get("sysparam");
        for (Sysparam spar : sysparam) {
            if (spar.getIsRequired()) {
                qpsb.append(spar.getVc2paramname()).append("=");
                String value = Strings.isEmpty(spar.getVc2demovalue())
                        ?resolveDemoParamValue(spar.getEnumDataTypes(), spar.getVc2range())
                        :spar.getVc2demovalue();
                if(spar.getEnumDataTypes().equals(DataTypes.BINARY)){
                    qpsb.append("@");
                }
                qpsb.append(value).append("&");
            }
        }
        List<Trequestparam> trequestparamSet = spec.getTrequestparamSet();
        for (Trequestparam reqpar : trequestparamSet) {
            if (reqpar.getIsRequired()) {
                qpsb.append(reqpar.getVc2paramname()).append("=");
                String value = Strings.isEmpty(reqpar.getVc2demovalue())
                        ?resolveDemoParamValue(reqpar.getEnumDataTypes(), reqpar.getVc2range())
                        :reqpar.getVc2demovalue();
                if(reqpar.getEnumDataTypes().equals(DataTypes.BINARY)){
                    qpsb.append("@");
                }
                qpsb.append(value).append("&");
            }
        }
    }

    private String resolveDemoParamValue(DataTypes type, String range) {
        String result = "{value}";
        if (!type.equals(DataTypes.BINARY)) {
            if (range != null && range.length() > 0) {
                try {
                    result = RangeFactory.getRangeInstance(type,range).getBaseSample();
                } catch (Exception e) {
                    result = resolveValueByType(type);
                }
            } else {
                result = resolveValueByType(type);
            }
        } else {
            result = "1.jpg";
        }
        return result;
    }

    private String resolveValueByType(DataTypes type) {
        String result = "{value}";
        switch (type) {
            case INT:
                result = new IntRange(1, 100).getBaseSample();
                break;
            case INT64:
                result = new LongRange(1000000l, 10000000l).getBaseSample();
                break;
            case FLOAT:
                result = new FloatRange(1f, 100f).getBaseSample();
                break;
            case FLOAT64:
                result = new DoubleRange(1d, 1000000d).getBaseSample();
                break;
            case STRING:
                result = "ABC";
                break;
            default:
                result = "{value}";
        }
        return result;
    }

    public String getWikiOutPath(Tspec spec) {
        return baseArgument.getWikiFileBaseDir() + "/" + spec.getVc2version()
                + "/" + spec.getNumcateid().getNumparentcateid().getVc2catename()
                + "/" + spec.getNumcateid().getVc2catename()
                + "/" + spec.getEnumApiType().getName()
                + "/" + spec.getEnumApiStatus().getName()
                + "/" + spec.getNumspecid() + "_" + spec.getResourcePath() + ".wiki";
    }

    public void generateMenuByParentCate(Tspeccategory pcate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
