package com.weibo.api.toolbox.page.dashboard;


import com.weibo.api.toolbox.common.enumerations.AcceptType;
import com.weibo.api.toolbox.common.enumerations.ApiStatus;
import com.weibo.api.toolbox.common.enumerations.ApiType;
import com.weibo.api.toolbox.common.enumerations.AuthType;
import com.weibo.api.toolbox.common.enumerations.ContentType;
import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.common.enumerations.HttpMethod;
import com.weibo.api.toolbox.common.enumerations.ParamStyle;
import com.weibo.api.toolbox.common.enumerations.RateLimit;
import com.weibo.api.toolbox.persist.entity.Baseurl;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tenumgroup;
import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.persist.entity.Tspecreview;
import com.weibo.api.toolbox.service.spec.CategoryProvider;
import com.weibo.api.toolbox.service.spec.SpecProvider;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;

/**
 *
 * @author x-spirit
 */
public class SpecViewerComposer extends GenericForwardComposer{

    CategoryProvider cp = (CategoryProvider) SpringUtil.getBean("categoryProvider");
    SpecProvider sp = (SpecProvider) SpringUtil.getBean("specProvider");
    Tspec currentSpec;
    Tspecreview currentSpecReview;
    List<Tspecreview> allReviews;


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        Map<String, Tspec> argmap = Executions.getCurrent().getArg();
        currentSpec = argmap.get("editingSpec");
        allReviews = getAllReviews();
        currentSpecReview = new Tspecreview(null, "", 0, null);
        currentSpecReview.setNumspecid(currentSpec);
        super.doAfterCompose(comp);
        autoCheck("cbhm", currentSpec.getVc2httpmethod(), ",", comp);
    }

    public void onClick$btn_addRev(){
        currentSpecReview.setNumreviewerid(ToolBoxUtil.getCurrentUser());
        sp.saveReview(currentSpecReview);
        currentSpecReview = new Tspecreview(null, "", 0, null);
        currentSpecReview.setNumspecid(currentSpec);
        refreshDataBinding();
    }

    private void autoCheck(String cb_prefix, String ids, String delimiter, Component comp) {
        String[] arrids = ids.split(delimiter);
        for (String id : arrids) {
            Checkbox cbox = (Checkbox) comp.getFellow(cb_prefix + "_" + id);
            cbox.setChecked(true);
        }
    }

    public List<HttpMethod> getAllHttpMethods() {
        List<HttpMethod> hmlst = Arrays.asList(HttpMethod.values());
        Collections.sort(hmlst, new Comparator<HttpMethod>() {

            public int compare(HttpMethod o1, HttpMethod o2) {
                return o1.getId() - o2.getId();
            }
        });
        return hmlst;
    }

    public List<AcceptType> getAllAcceptType() {
        List<AcceptType> atlst = Arrays.asList(AcceptType.values());
        Collections.sort(atlst, new Comparator<AcceptType>() {

            public int compare(AcceptType o1, AcceptType o2) {
                return o1.getId() - o2.getId();
            }
        });
        return atlst;
    }

    public List<ContentType> getAllContentType() {
        List<ContentType> ctlst = Arrays.asList(ContentType.values());
        Collections.sort(ctlst, new Comparator<ContentType>() {

            public int compare(ContentType o1, ContentType o2) {
                return o1.getId() - o2.getId();
            }
        });
        return ctlst;
    }

    public List<AuthType> getAllAuthType() {
        List<AuthType> atlst = Arrays.asList(AuthType.values());
        Collections.sort(atlst, new Comparator<AuthType>() {

            public int compare(AuthType o1, AuthType o2) {
                return o1.getId() - o2.getId();
            }
        });
        return atlst;
    }

    public List<RateLimit> getAllRateLimit() {
        List<RateLimit> rlmtlst = Arrays.asList(RateLimit.values());
        Collections.sort(rlmtlst, new Comparator<RateLimit>() {

            public int compare(RateLimit o1, RateLimit o2) {
                return o1.getId() - o2.getId();
            }
        });
        return rlmtlst;
    }

    public List<ApiStatus> getAllApiStatus() {
        List<ApiStatus> aslst = Arrays.asList(ApiStatus.values());
        Collections.sort(aslst, new Comparator<ApiStatus>() {

            public int compare(ApiStatus o1, ApiStatus o2) {
                return o1.getId() - o2.getId();
            }
        });
        return aslst;
    }

    public List<ApiType> getAllApiType() {
        List<ApiType> atlst = Arrays.asList(ApiType.values());
        Collections.sort(atlst, new Comparator<ApiType>() {

            public int compare(ApiType o1, ApiType o2) {
                return o1.getId() - o2.getId();
            }
        });
        return atlst;
    }

    public List<DataTypes> getAllDataTypes() {
        List<DataTypes> dtlst = Arrays.asList(DataTypes.values());
        Collections.sort(dtlst, new Comparator<DataTypes>() {

            public int compare(DataTypes o1, DataTypes o2) {
                return o1.getId() - o2.getId();
            }
        });
        return dtlst;
    }

    public List<DataTypes> getAllPrimitiveTypes() {
        List<DataTypes> dtlst = new ArrayList<DataTypes>();
        for (DataTypes type:DataTypes.values()){
            if (type.isPrimitive()){
                dtlst.add(type);
            }
        }
        Collections.sort(dtlst, new Comparator<DataTypes>() {

            public int compare(DataTypes o1, DataTypes o2) {
                return o1.getId() - o2.getId();
            }
        });
        return dtlst;
    }

    public List<ParamStyle> getAllParamStyle() {
        List<ParamStyle> pslst = Arrays.asList(ParamStyle.values());
        Collections.sort(pslst, new Comparator<ParamStyle>() {

            public int compare(ParamStyle o1, ParamStyle o2) {
                return o1.getId() - o2.getId();
            }
        });
        return pslst;
    }

    public List<Tspecreview> getAllReviews(){
        if (currentSpec!=null){
            allReviews = sp.getReviewBySpec(currentSpec);
        }
        return allReviews;
    }

    public List<Tenumgroup> getAllEnableEnumGroups(){
        return sp.getAllEnableEnumGroups();
    }

    public List<Tspeccategory> getAllSubCategory() {
        return cp.getAllSubCate();
    }

    public List<Tdatastruct> getAllAvailDataStructs(){
        return sp.getAllDataStruct(null,null, true, -1, -1);
    }

    public Tspec getCurrentSpec() {
        return currentSpec;
    }

    public void setCurrentSpec(Tspec currentSpec) {
        this.currentSpec = currentSpec;
    }

    public List<Baseurl> getBaseUrls() {
        return sp.getBaseurls();
    }

    public Tspecreview getCurrentSpecReview() {
        return currentSpecReview;
    }

    public void setCurrentSpecReview(Tspecreview currentSpecReview) {
        this.currentSpecReview = currentSpecReview;
    }

    public void refreshDataBinding(){
        AnnotateDataBinder binder = (AnnotateDataBinder) this.self.getAttribute("binder", true);
        binder.loadAll();
    }
}
