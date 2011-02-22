package com.weibo.api.spec.wadl;


import com.weibo.api.spec.wadl.wadl20090202.Application;
import com.weibo.api.spec.wadl.wadl20090202.Doc;
import com.weibo.api.spec.wadl.wadl20090202.Method;
import com.weibo.api.spec.wadl.wadl20090202.Representation;
import com.weibo.api.spec.wadl.wadl20090202.Request;
import com.weibo.api.spec.wadl.wadl20090202.Resource;
import com.weibo.api.spec.wadl.wadl20090202.Resources;
import com.weibo.api.toolbox.common.enumerations.HttpMethod;
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
            
        }
    }

    public void bindRequest(Method mtd, Tspec spec){
        Request req = new Request();
        Representation rep = new Representation();
        rep.setMediaType("");
        rep.getParam();
        req.getRepresentation().add(new Representation());
        mtd.setRequest(req);
    }
}
