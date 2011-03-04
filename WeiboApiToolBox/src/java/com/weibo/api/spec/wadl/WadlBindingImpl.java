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
import com.weibo.api.toolbox.persist.entity.Syserror;
import com.weibo.api.toolbox.persist.entity.Sysparam;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tenumgroup;
import com.weibo.api.toolbox.persist.entity.Tenumvalues;
import com.weibo.api.toolbox.persist.entity.Terrorcode;
import com.weibo.api.toolbox.persist.entity.Trequestparam;
import com.weibo.api.toolbox.persist.entity.Tresponse;
import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.service.spec.SysDataProvider;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
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

/**
 *
 * @author x-spirit
 */
public class WadlBindingImpl implements WadlBinding {

    @javax.annotation.Resource
    BaseArgument baseArgument;
    @javax.annotation.Resource
    Jaxb2Marshaller jaxb2Marshaller;
    @javax.annotation.Resource
    SysDataProvider sysDataProvider;

    private Set<String> schemaRef = new HashSet<String>();

    public WadlBindingImpl() {
    }

    public String marshallToString(Application app) {
        final StringWriter out = new StringWriter();
        jaxb2Marshaller.marshal(app, new StreamResult(out));
        return out.toString();
    }

    public void marshallToStream(Application app, OutputStream os) {
        jaxb2Marshaller.marshal(app, new StreamResult(os));
    }

    public void marshallToFile(Application app, File docFile) throws IOException {
        PrintStream out = null;
        docFile.getParentFile().mkdirs();
        out = new PrintStream(docFile, "UTF-8");
        marshallToStream(app, out);
        out.close();
    }

    public Map<String, List<Tspec>> seperateSpecListByBaseUrl(List<Tspec> specList) {
        Map<String, List<Tspec>> specmap = new HashMap<String, List<Tspec>>();
        for (Tspec spec : specList) {
            Baseurl baseurl = spec.getNumbaseurlid();
            String base = baseurl.getVc2baseurl() + "/" + spec.getVc2version();
            List<Tspec> sublist = specmap.get(base);
            if (sublist == null) {
                sublist = new ArrayList<Tspec>();
                specmap.put(base, sublist);
            }
            sublist.add(spec);
        }
        return specmap;
    }

    public Application bindApplication(Tspec spec) {
        List<Tspec> specList = new ArrayList<Tspec>();
        specList.add(spec);
        return bindApplication(specList);
    }

    public Application bindApplication(List<Tspec> specList) {
        Application appdefine = new Application();
        bindResourcesByBaseUrl(appdefine, specList);
        bindGrammers(appdefine);
        return appdefine;
    }

    public void bindResourcesByBaseUrl(Application appdefine, List<Tspec> specList) {
        Map<String, List<Tspec>> specmap = seperateSpecListByBaseUrl(specList);
        for (String base : specmap.keySet()) {
            List<Tspec> sublist = specmap.get(base);
            Resources ress = new Resources();
            bindResources(ress, sublist);
            appdefine.getResources().add(ress);
        }
    }

    public void bindGrammers(Application appdefine) {
        Grammars grammars = new Grammars();
        for (String sr : schemaRef) {
            addGrammerInclude(grammars, sr);
        }
        appdefine.setGrammars(grammars);
    }

    public void addGrammerInclude(Grammars grammars, String schemaRef) {
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
            bindDocs(ress.getDoc(), spec.getNumbaseurlid().getVc2desc());
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
        bindSysTemplateAndMatrixParam(res, spec);
        bindTemplateAndMatrixParam(res, spec);
        bindMethod(res, spec);
    }

    private void bindSysTemplateAndMatrixParam(Resource res, Tspec spec) {
        try {
            Tspeccategory numparentcateid = spec.getNumcateid().getNumparentcateid();
            List<Sysparam> sysparamByCate = sysDataProvider.getSysParametersByCate(numparentcateid);
            for (Sysparam syspar : sysparamByCate) {
                Param par = null;
                if (syspar.getEnumParamStyle().getWadlStyle().equals(ParamStyle.MATRIX)) {
                    par = new Param();
                    copySysParamValue(par, syspar);
                }
                if (syspar.getEnumParamStyle().getWadlStyle().equals(ParamStyle.TEMPLATE)) {
                    par = new Param();
                    copySysParamValue(par, syspar);
                }
                if (par != null) {
                    res.getParam().add(par);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(WadlBindingImpl.class.getName()).log(Level.SEVERE, null, e);
        }
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
            if (par != null) {
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
            bindSysRequestParameters(req, spec);
            bindRequestParameters(req, spec);
        } else {
            bindRequestRepresentation(req, spec);
        }
        mtd.setRequest(req);
    }

    private void bindSysRequestParameters(Request req, Tspec spec) {
        try {
            Tspeccategory numparentcateid = spec.getNumcateid().getNumparentcateid();
            List<Sysparam> sysparamByCate = sysDataProvider.getSysParametersByCate(numparentcateid);
            for (Sysparam syspar : sysparamByCate) {
                if (syspar.getEnumParamStyle().getWadlStyle().equals(ParamStyle.HEADER)
                        || syspar.getEnumParamStyle().getWadlStyle().equals(ParamStyle.QUERY)) {
                    Param par = new Param();
                    copySysParamValue(par, syspar);
                    req.getParam().add(par);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(WadlBindingImpl.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void bindRequestParameters(Request req, Tspec spec) {
        List<Trequestparam> trequestparamSet = spec.getTrequestparamSet();
        for (Trequestparam reqparam : trequestparamSet) {
            if (reqparam.getEnumParamStyle().getWadlStyle().equals(ParamStyle.HEADER)
                    || reqparam.getEnumParamStyle().getWadlStyle().equals(ParamStyle.QUERY)) {
                Param par = new Param();
                copyParamValue(par, reqparam);
                req.getParam().add(par);
            }
        }
    }

    public void bindRequestRepresentation(Request req, Tspec spec) {
        Representation rep = new Representation();
        rep.setMediaType(spec.getEnumAcceptType().getMediaString());
        bindSysRepresentationParameters(rep, spec);
        bindRepresentationParameters(rep, spec);
        req.getRepresentation().add(rep);
    }

    public void bindRepresentationParameters(Representation rep, Tspec spec) {
        List<Trequestparam> trequestparamSet = spec.getTrequestparamSet();
        for (Trequestparam reqparam : trequestparamSet) {
            if (reqparam.getEnumParamStyle().getWadlStyle().equals(ParamStyle.HEADER)
                    || reqparam.getEnumParamStyle().getWadlStyle().equals(ParamStyle.QUERY)) {
                Param par = new Param();
                copyParamValue(par, reqparam);
                rep.getParam().add(par);
            }
        }
    }

    private void bindSysRepresentationParameters(Representation rep, Tspec spec) {
        try {
            Tspeccategory numparentcateid = spec.getNumcateid().getNumparentcateid();
            List<Sysparam> sysparamByCate = sysDataProvider.getSysParametersByCate(numparentcateid);
            for (Sysparam syspar : sysparamByCate) {
                if (syspar.getEnumParamStyle().getWadlStyle().equals(ParamStyle.HEADER)
                        || syspar.getEnumParamStyle().getWadlStyle().equals(ParamStyle.QUERY)) {
                    Param par = new Param();
                    copySysParamValue(par, syspar);
                    rep.getParam().add(par);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(WadlBindingImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void bindResponse(Method mtd, Tspec spec) {
        List<Tresponse> tresponseSet = spec.getTresponseSet();
        for (Tresponse tresp : tresponseSet) {
            Response resp = new Response();
            resp.getStatus().add(200L);
            DataTypes enumDataTypes = tresp.getEnumDataTypes();
            if (DataTypes.OBJECT.equals(enumDataTypes) || DataTypes.ARRAY.equals(enumDataTypes)) {
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

        if (enumContentType.equals(ContentType.APP_XML)) {
            prefix = "xsd";
            schemalocal = ds.getStructDocName() + ".xsd";
        } else if (enumContentType.equals(ContentType.APP_JSON)) {
            prefix = "jssd";
            schemalocal = ds.getStructDocName() + ".jssd";
        }

        this.schemaRef.add(uri + "/" + schemalocal);

        repset.setElement(new QName(uri, localpart, prefix));

        resp.getRepresentation().add(repset);
    }

    public void bindErrorResponse(Method mtd, Tspec spec) {
        List<ErrorResponse> errlist = new ArrayList<ErrorResponse>();
        try {
            List<Terrorcode> terrorcodeSet = spec.getTerrorcodeSet();
            for (Terrorcode errcode : terrorcodeSet) {
                ErrorResponse errorResponse = new ErrorResponse(errcode.getVc2errorcode(), errcode.getVc2httpcode(), errcode.getVc2errmsg(), errcode.getVc2cnmsg(), errcode.getVc2desc(), errcode.getVc2detail());
                errlist.add(errorResponse);
            }
            Tspeccategory numparentcateid = spec.getNumcateid().getNumparentcateid();
            List<Syserror> syserrorByCate = sysDataProvider.getSyserrorByCate(numparentcateid);
            for (Syserror syserr : syserrorByCate){
                ErrorResponse errorResponse = new ErrorResponse(syserr.getVc2errorcode(), syserr.getVc2httpcode(), syserr.getVc2errmsg(), syserr.getVc2cnmsg(), syserr.getVc2desc(), syserr.getVc2detail());
                errlist.add(errorResponse);
            }
        } catch (Exception e) {
            Logger.getLogger(WadlBindingImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        Map<String,Response> errmap = new HashMap<String, Response>();
        for (ErrorResponse errresp : errlist){
            Response resp = null;
            if (errmap.get(errresp.getVc2httpcode())==null){
                resp = new Response();
                resp.getStatus().add(Long.parseLong(errresp.getVc2httpcode()));
            }else{
                resp = errmap.get(errresp.getVc2httpcode());
            }
            bindErrorRepresentation(resp, errresp);
            errmap.put(errresp.getVc2httpcode(),resp);
        }
        mtd.getResponse().addAll(errmap.values());
    }

    public void bindErrorRepresentation(Response resp, ErrorResponse errresp) {
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
                && reqparam.getNumenumgroupid() != null) {
            Tenumgroup enumvals = reqparam.getNumenumgroupid();
            Set<Tenumvalues> tenumvaluesSet = enumvals.getTenumvaluesSet();
            for (Tenumvalues enumv : tenumvaluesSet) {
                if (enumv.getIsEnable()) {
                    Option opt = new Option();
                    opt.setValue(enumv.getVc2enumvalue());
                    par.getOption().add(opt);
                }
            }
        }
    }

    private void bindDocs(List<Doc> docs, String... doc) {

        for (String str : doc) {
            Doc document = new Doc();
            document.getContent().add(str);
            docs.add(document);
        }
    }

    private void copySysParamValue(Param par, Sysparam syspar) {
        par.setName(syspar.getVc2paramname());
        par.setRequired(syspar.getIsRequired());
        par.setStyle(syspar.getEnumParamStyle().getWadlStyle());
        par.setRepeating(syspar.getIsRepeating());
        par.setType(new QName("http://www.w3.org/2001/XMLSchema",
                syspar.getEnumDataTypes().getXsdType()));
        if (ToolBoxUtil.isNotEmpty(syspar.getVc2defaultvalue())) {
            par.setDefault(syspar.getVc2defaultvalue());
        }
        if (ToolBoxUtil.isNotEmpty(syspar.getVc2demovalue())) {
            par.setFixed(syspar.getVc2demovalue());
        }
        if (syspar.getEnumDataTypes().equals(DataTypes.ENUM)
                && syspar.getNumenumgroupid() != null) {
            Tenumgroup enumvals = syspar.getNumenumgroupid();
            Set<Tenumvalues> tenumvaluesSet = enumvals.getTenumvaluesSet();
            for (Tenumvalues enumv : tenumvaluesSet) {
                if (enumv.getIsEnable()) {
                    Option opt = new Option();
                    opt.setValue(enumv.getVc2enumvalue());
                    par.getOption().add(opt);
                }
            }
        }
    }


    public class ErrorResponse {

        private String vc2errorcode;
        private String vc2httpcode;
        private String vc2errmsg;
        private String vc2cnmsg;
        private String vc2desc;
        private String vc2detail;

        public ErrorResponse(String vc2errorcode, String vc2httpcode, String vc2errmsg, String vc2cnmsg, String vc2desc, String vc2detail) {
            this.vc2errorcode = vc2errorcode;
            this.vc2httpcode = vc2httpcode;
            this.vc2errmsg = vc2errmsg;
            this.vc2cnmsg = vc2cnmsg;
            this.vc2desc = vc2desc;
            this.vc2detail = vc2detail;
        }

        public String getVc2cnmsg() {
            return vc2cnmsg;
        }

        public void setVc2cnmsg(String vc2cnmsg) {
            this.vc2cnmsg = vc2cnmsg;
        }

        public String getVc2desc() {
            return vc2desc;
        }

        public void setVc2desc(String vc2desc) {
            this.vc2desc = vc2desc;
        }

        public String getVc2detail() {
            return vc2detail;
        }

        public void setVc2detail(String vc2detail) {
            this.vc2detail = vc2detail;
        }

        public String getVc2errmsg() {
            return vc2errmsg;
        }

        public void setVc2errmsg(String vc2errmsg) {
            this.vc2errmsg = vc2errmsg;
        }

        public String getVc2errorcode() {
            return vc2errorcode;
        }

        public void setVc2errorcode(String vc2errorcode) {
            this.vc2errorcode = vc2errorcode;
        }

        public String getVc2httpcode() {
            return vc2httpcode;
        }

        public void setVc2httpcode(String vc2httpcode) {
            this.vc2httpcode = vc2httpcode;
        }
    }
}
