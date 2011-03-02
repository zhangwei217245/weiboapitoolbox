package com.weibo.api.spec.wadl;

import com.weibo.api.spec.basic.BaseArgument;
import com.weibo.api.spec.wadl.wadl20090202.Application;
import com.weibo.api.spec.wadl.wadl20090202.Doc;
import com.weibo.api.spec.wadl.wadl20090202.Grammars;
import com.weibo.api.spec.wadl.wadl20090202.Include;
import com.weibo.api.spec.wadl.wadl20090202.Method;
import com.weibo.api.spec.wadl.wadl20090202.Option;
import com.weibo.api.spec.wadl.wadl20090202.Param;
import com.weibo.api.spec.wadl.wadl20090202.ParamStyle;
import com.weibo.api.spec.wadl.wadl20090202.Representation;
import com.weibo.api.spec.wadl.wadl20090202.Request;
import com.weibo.api.spec.wadl.wadl20090202.Resource;
import com.weibo.api.spec.wadl.wadl20090202.Resources;
import com.weibo.api.spec.wadl.wadl20090202.Response;
import com.weibo.api.toolbox.common.enumerations.AcceptType;
import com.weibo.api.toolbox.common.enumerations.ContentType;
import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.common.enumerations.HttpMethod;
import com.weibo.api.toolbox.persist.entity.Baseurl;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tenumgroup;
import com.weibo.api.toolbox.persist.entity.Tenumvalues;
import com.weibo.api.toolbox.persist.entity.Terrorcode;
import com.weibo.api.toolbox.persist.entity.Trequestparam;
import com.weibo.api.toolbox.persist.entity.Tresponse;
import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

/**
 *
 * @author x-spirit
 */
@Component("wadlbinder")
public class WadlBindingImpl implements WadlBinding {

    @javax.annotation.Resource
    BaseArgument baseArgument;
    @javax.annotation.Resource
    Jaxb2Marshaller jaxb2Marshaller;

    private Set<String> schemaRef = new HashSet<String>();

    public WadlBindingImpl() {
    }

    public String marshall(Application app){
        final StringWriter out = new StringWriter();
        jaxb2Marshaller.marshal(app, new StreamResult(out));
        return out.toString();
    }

    public void marshall(Application app,OutputStream os){
        jaxb2Marshaller.marshal(app, new StreamResult(os));
    }

    public void marshall(Application app,String docpath){
        PrintStream out = null;
        try {
            File doc = new File(docpath);
            doc.getParentFile().mkdirs();
            out = new PrintStream(doc, "UTF-8");
            jaxb2Marshaller.marshal(app, new StreamResult(out));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WadlBindingImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WadlBindingImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (Exception e) {
            }
        }
    }
    
    public Map<String,List<Tspec>> seperateSpecListByBaseUrl(List<Tspec> specList){
        Map<String,List<Tspec>> specmap = new HashMap<String, List<Tspec>>();
        for (Tspec spec : specList){
            Baseurl baseurl = spec.getNumbaseurlid();
            String base = baseurl.getVc2baseurl()+"/"+spec.getVc2version();
            List<Tspec> sublist = specmap.get(base);
            if (sublist==null){
                sublist = new ArrayList<Tspec>();
                specmap.put(base, sublist);
            }
            sublist.add(spec);
        }
        return specmap;
    }

    public Application bindApplication(Tspec spec){
        List<Tspec> specList = new ArrayList<Tspec>();
        specList.add(spec);
        return bindApplication(specList);
    }
    
    public Application bindApplication(List<Tspec> specList){
        Application appdefine = new Application();
        bindResourcesByBaseUrl(appdefine,specList);
        bindGrammers(appdefine);
        return appdefine;
    }

    public void bindResourcesByBaseUrl(Application appdefine, List<Tspec> specList) {
        Map<String, List<Tspec>> specmap = seperateSpecListByBaseUrl(specList);
        for (String base : specmap.keySet()){
            List<Tspec> sublist = specmap.get(base);
            Resources ress = new Resources();
            bindResources(ress,sublist);
            appdefine.getResources().add(ress);
        }
    }

    public void bindGrammers(Application appdefine) {
        Grammars grammars = new Grammars();
        for (String sr : schemaRef){
            addGrammerInclude(grammars, sr);
        }
        appdefine.setGrammars(grammars);
    }

    public void addGrammerInclude(Grammars grammars,String schemaRef) {
        Include inc = new Include();
        inc.setHref(schemaRef);
        grammars.getInclude().add(inc);
    }

    /**
     * bind Resource node for each spec
     * @param ress
     * @param sameBaseSepcs
     */
    public void bindResources(Resources ress, List<Tspec> sameBaseSepcs) {
        for (Tspec spec : sameBaseSepcs) {
            Resource res = new Resource();
            bindResource(res, spec);
            ress.getResource().add(res);
            ress.setBase(spec.getNumbaseurlid().getVc2baseurl() + "/" + spec.getVc2version());
            bindDocs(ress.getDoc(),spec.getNumbaseurlid().getVc2desc());
        }
    }

    /**
     * bind spec main resource to its first Resource node
     * if the spec doesn't have a subresource,
     * bind the main resource to its last Resource node.
     * @param res
     * @param spec
     */
    public void bindResource(Resource res, Tspec spec) {
        if (ToolBoxUtil.isNotEmpty(spec.getVc2subresource())) {
            Resource subres = new Resource();
            bindLastResource(subres, spec);
            res.getMethodOrResource().add(subres);
        } else {
            bindLastResource(res, spec);
        }
        res.setPath(spec.getVc2mainresource());
    }

    /**
     * bind spec subresource to its last Resource node.
     * @param res
     * @param spec
     */
    public void bindLastResource(Resource res, Tspec spec) {
        res.setPath(spec.getVc2subresource());
        res.setId(spec.getVc2specname());
        bindTemplateAndMatrixParam(res, spec);
        bindMethod(res, spec);
    }

    public void bindTemplateAndMatrixParam(Resource res, Tspec spec) {
        List<Trequestparam> trequestparamSet = spec.getTrequestparamSet();
        for (Trequestparam reqparam : trequestparamSet) {
            Param par = null;
            if (reqparam.getEnumParamStyle().getWadlStyle().equals(ParamStyle.MATRIX)) {
                par = new Param();
                copyParamValue(par, reqparam);
            }
            if (reqparam.getEnumParamStyle().getWadlStyle().equals(ParamStyle.TEMPLATE)) {
                par = new Param();
                copyParamValue(par, reqparam);
            }
            if (par!=null){
                res.getParam().add(par);
            }
        }
    }

    /**
     * bind Method node to its last Resource node
     * according to the 
     * @param rs
     * @param spec
     */
    public void bindMethod(Resource rs, Tspec spec) {
        HttpMethod[] enumHttpMethod = spec.getEnumHttpMethod();
        for (HttpMethod hm : enumHttpMethod) {
            Method mtd = new Method();
            mtd.setName(hm.getName());
            //mtd.setId(spec.getVc2specname());
            bindRequest(mtd, spec);
            bindResponse(mtd, spec);
            bindErrorResponse(mtd, spec);
            rs.getMethodOrResource().add(mtd);
        }
    }

    public void bindRequest(Method mtd, Tspec spec) {
        Request req = new Request();
        if (spec.getEnumAcceptType().equals(AcceptType.WILDCARD)) {
            bindRequestParameters(req, spec);
        } else {
            bindRequestRepresentation(req, spec);
        }
        mtd.setRequest(req);
    }

    public void bindRequestParameters(Request req, Tspec spec) {
        List<Trequestparam> trequestparamSet = spec.getTrequestparamSet();
        for (Trequestparam reqparam : trequestparamSet) {
            if (reqparam.getEnumParamStyle().getWadlStyle().equals(ParamStyle.HEADER)
                    ||reqparam.getEnumParamStyle().getWadlStyle().equals(ParamStyle.QUERY)){
                Param par = new Param();
                copyParamValue(par, reqparam);
                req.getParam().add(par);
            }
        }
    }

    public void bindRequestRepresentation(Request req, Tspec spec) {
        Representation rep = new Representation();
        rep.setMediaType(spec.getEnumAcceptType().getMediaString());
        bindRepresentationParameters(rep, spec);
        req.getRepresentation().add(rep);
    }

    public void bindRepresentationParameters(Representation rep, Tspec spec) {
        List<Trequestparam> trequestparamSet = spec.getTrequestparamSet();
        for (Trequestparam reqparam : trequestparamSet) {
            if (reqparam.getEnumParamStyle().getWadlStyle().equals(ParamStyle.HEADER)
                    ||reqparam.getEnumParamStyle().getWadlStyle().equals(ParamStyle.QUERY)){
                Param par = new Param();
                copyParamValue(par, reqparam);
                rep.getParam().add(par);
            }
        }
    }

    public void bindResponse(Method mtd, Tspec spec) {
        List<Tresponse> tresponseSet = spec.getTresponseSet();
        for (Tresponse tresp : tresponseSet) {
            Response resp = new Response();
            resp.getStatus().add(200L);
            DataTypes enumDataTypes = tresp.getEnumDataTypes();
            if (DataTypes.OBJECT.equals(enumDataTypes)||DataTypes.ARRAY.equals(enumDataTypes)){
                bindResponseRepresentation(resp, tresp);
            } else {
                //TODO: if return a primitive type, what should we expect?
            }
            mtd.getResponse().add(resp);
        }
    }

    public void bindResponseRepresentation(Response resp, Tresponse tresp) {
        Representation repset = new Representation();
        Tdatastruct ds = tresp.getNumdatastructid();
        ContentType enumContentType = tresp.getEnumContentType();
        String schemalocal = null;
        repset.setMediaType(tresp.getEnumContentType().getMediaString());
        
        String uri = baseArgument.getSchemaBaseURI();
        String prefix = "ds";
        String localpart = tresp.getVc2responsename();

        if (enumContentType.equals(ContentType.APP_XML)){
            prefix = "xsd";
            schemalocal = ds.getVc2version()+"/"+ds.getVc2structname()+".xsd";
        } else if (enumContentType.equals(ContentType.APP_JSON)){
            prefix = "jssd";
            schemalocal = ds.getVc2version()+"/"+ds.getVc2structname()+".jssd";
        }

        this.schemaRef.add(uri+"/"+schemalocal);
        
        repset.setElement(new QName(uri, localpart, prefix));
        
        resp.getRepresentation().add(repset);
    }

    public void bindErrorResponse(Method mtd, Tspec spec) {
        List<Terrorcode> terrorcodeSet = spec.getTerrorcodeSet();
        for (Terrorcode errcode : terrorcodeSet) {
            Response resp = new Response();
            resp.getStatus().add(Long.parseLong(errcode.getVc2httpcode()));
            bindErrorRepresentation(resp,errcode);
            mtd.getResponse().add(resp);
        }
    }

    public void bindErrorRepresentation(Response resp, Terrorcode errcode) {
        Representation repres = new Representation();
        //TODO: don't know where put our error define
    }
    
    private void copyParamValue(Param par, Trequestparam reqparam) {
        par.setName(reqparam.getVc2paramname());
        par.setRequired(reqparam.getIsRequired());
        par.setStyle(reqparam.getEnumParamStyle().getWadlStyle());
        par.setRepeating(reqparam.getIsRepeating());
        par.setType(new QName("http://www.w3.org/2001/XMLSchema",
                reqparam.getEnumDataTypes().getXsdType()));
        if (ToolBoxUtil.isNotEmpty(reqparam.getVc2defaultvalue())) {
            par.setDefault(reqparam.getVc2defaultvalue());
        }
        if (ToolBoxUtil.isNotEmpty(reqparam.getVc2demovalue())) {
            par.setFixed(reqparam.getVc2demovalue());
        }
        if (reqparam.getEnumDataTypes().equals(DataTypes.ENUM)
                &&reqparam.getNumenumgroupid()!=null){
            Tenumgroup enumvals = reqparam.getNumenumgroupid();
            Set<Tenumvalues> tenumvaluesSet = enumvals.getTenumvaluesSet();
            for (Tenumvalues enumv : tenumvaluesSet){
                if (enumv.getIsEnable()){
                    Option opt = new Option();
                    opt.setValue(enumv.getVc2enumvalue());
                    par.getOption().add(opt);
                }
            }
        }
    }

    private void bindDocs(List<Doc> docs, String ...doc) {

        for (String str : doc){
            Doc document = new Doc();
            document.getContent().add(str);
            docs.add(document);
        }
    }
}
