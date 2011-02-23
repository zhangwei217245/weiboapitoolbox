package com.weibo.api.spec.wadl;


import com.weibo.api.spec.wadl.wadl20090202.Application;
import com.weibo.api.spec.wadl.wadl20090202.Doc;
import com.weibo.api.spec.wadl.wadl20090202.Method;
import com.weibo.api.spec.wadl.wadl20090202.Param;
import com.weibo.api.spec.wadl.wadl20090202.ParamStyle;
import com.weibo.api.spec.wadl.wadl20090202.Representation;
import com.weibo.api.spec.wadl.wadl20090202.Request;
import com.weibo.api.spec.wadl.wadl20090202.Resource;
import com.weibo.api.spec.wadl.wadl20090202.Resources;
import com.weibo.api.spec.wadl.wadl20090202.Response;
import com.weibo.api.toolbox.common.enumerations.AcceptType;
import com.weibo.api.toolbox.common.enumerations.HttpMethod;
import com.weibo.api.toolbox.persist.entity.Trequestparam;
import com.weibo.api.toolbox.persist.entity.Tresponse;
import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.util.List;

/**
 *
 * @author x-spirit
 */
public class WadlBindingImpl {
    
    public void bindResources(Resources ress,List<Tspec> sameBaseSepcs){
        for (Tspec spec : sameBaseSepcs){
            Resource res = new Resource();
            bindResource(res, spec);
            ress.getResource().add(res);
            ress.setBase(spec.getNumbaseurlid().getVc2baseurl()+"/"+spec.getVc2version());
            Doc doc = new Doc();
            doc.getContent().add("<![CDATA[\n"+spec.getNumbaseurlid().getVc2desc()+"\n]]>");
            ress.getDoc().add(doc);
        }
    }

    public void bindResource(Resource res, Tspec spec){
        if (ToolBoxUtil.isNotEmpty(spec.getVc2subresource())){
            Resource subres = new Resource();
            bindLastResource(subres, spec);
            res.getMethodOrResource().add(subres);
        }else{
            bindLastResource(res, spec);
        }
        res.setPath(spec.getVc2mainresource());
    }

    public void bindLastResource(Resource res, Tspec spec){
        res.setPath(spec.getVc2subresource());
        res.setId(spec.getVc2specname());
        bindMethod(res, spec);
    }


    public void bindMethod(Resource rs, Tspec spec){
        HttpMethod[] enumHttpMethod = spec.getEnumHttpMethod();
        for(HttpMethod hm : enumHttpMethod){
            Method mtd = new Method();
            mtd.setName(hm.getName());
            mtd.setId(spec.getVc2specname());
            bindRequest(mtd, spec);
            bindResponse(mtd,spec);
        }
    }

    public void bindRequest(Method mtd, Tspec spec){
        Request req = new Request();
        if (spec.getEnumAcceptType().equals(AcceptType.WILDCARD)){
            bindRequestParameters(req,spec);
        }else{
            bindRequestRepresentation(req,spec);
        }
        mtd.setRequest(req);
    }

    public void bindRequestParameters(Request req,Tspec spec){
        List<Trequestparam> trequestparamSet = spec.getTrequestparamSet();
        for (Trequestparam reqparam : trequestparamSet){
            Param par = new Param();
            par.setName(reqparam.getVc2paramname());
            par.setRequired(reqparam.getIsRequired());
            par.setStyle(reqparam.getEnumParamStyle().getWadlStyle());
            par.setRepeating(reqparam.getIsRepeating());
            if (ToolBoxUtil.isNotEmpty(reqparam.getVc2defaultvalue())){
                par.setDefault(reqparam.getVc2defaultvalue());
            }
            if (ToolBoxUtil.isNotEmpty(reqparam.getVc2demovalue())){
                par.setFixed(reqparam.getVc2demovalue());
            }
            req.getParam().add(par);
        }
    }
    
    public void bindRequestRepresentation(Request req,Tspec spec){
        Representation rep = new Representation();
        rep.setMediaType(spec.getEnumAcceptType().getMediaString());
        bindRepresentationParameters(rep,spec);
        req.getRepresentation().add(rep);
        
    }
    public void bindRepresentationParameters(Representation rep,Tspec spec){
        List<Trequestparam> trequestparamSet = spec.getTrequestparamSet();
        for (Trequestparam reqparam : trequestparamSet){
            Param par = new Param();
            par.setName(reqparam.getVc2paramname());
            par.setRequired(reqparam.getIsRequired());
            par.setStyle(reqparam.getEnumParamStyle().getWadlStyle());
            par.setRepeating(reqparam.getIsRepeating());
            if (ToolBoxUtil.isNotEmpty(reqparam.getVc2defaultvalue())){
                par.setDefault(reqparam.getVc2defaultvalue());
            }
            if (ToolBoxUtil.isNotEmpty(reqparam.getVc2demovalue())){
                par.setFixed(reqparam.getVc2demovalue());
            }
            rep.getParam().add(par);
        }
    }

    public void bindResponse(Method mtd, Tspec spec){
        List<Tresponse> tresponseSet = spec.getTresponseSet();
        for (Tresponse tresp : tresponseSet){
            Response resp = new Response();
            bindResponseRepresentation(resp,spec);
        }
    }

    public void bindResponseRepresentation(Response resp, Tspec spec){
        
    }
}
