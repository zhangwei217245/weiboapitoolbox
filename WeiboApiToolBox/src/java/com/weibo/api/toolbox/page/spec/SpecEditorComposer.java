package com.weibo.api.toolbox.page.spec;

import com.weibo.api.spec.wadl.WadlBinding;
import com.weibo.api.toolbox.common.ConfirmBoxEventListener;
import com.weibo.api.toolbox.common.EventListenerAction;
import com.weibo.api.toolbox.common.enumerations.AcceptType;
import com.weibo.api.toolbox.common.enumerations.ApiStatus;
import com.weibo.api.toolbox.common.enumerations.ApiType;
import com.weibo.api.toolbox.common.enumerations.AuthType;
import com.weibo.api.toolbox.common.enumerations.ContentType;
import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.common.enumerations.HttpMethod;
import com.weibo.api.toolbox.common.enumerations.ParamStyle;
import com.weibo.api.toolbox.common.enumerations.RateLimit;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Baseurl;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tenumgroup;
import com.weibo.api.toolbox.persist.entity.Terrorcode;
import com.weibo.api.toolbox.persist.entity.Trequestparam;
import com.weibo.api.toolbox.persist.entity.Tresponse;
import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.service.spec.CategoryProvider;
import com.weibo.api.toolbox.service.spec.SpecProvider;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author X-Spirit
 */
public class SpecEditorComposer extends GenericForwardComposer {

    private static final Log log = Log.lookup(SpecManagerComposer.class);
    private static final long serialVersionUID = 3077338567136567L;
    CategoryProvider cp = (CategoryProvider) SpringUtil.getBean("categoryProvider");
    SpecProvider sp = (SpecProvider) SpringUtil.getBean("specProvider");
    IJpaDaoService daoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");
    
    Tspec currentSpec;
    Listbox paramgrid;
    Listbox errgrid;
    Listbox resplist;
    Listbox list_dependsOnWho;
    Listbox list_dependsOnMe;
    Intbox int_dependsOnMe;
    Intbox int_dependsOnWho;


    Tdatastruct nonstruct = ToolBoxUtil.getNonStruct();

    public void onClick$btn_dependsOnWho() throws InterruptedException{
        Integer whoid = int_dependsOnWho.getValue();
        Tspec who = daoService.findOneEntityById(Tspec.class, whoid);
        if (whoid.equals(currentSpec.getNumspecid())){
            Messagebox.show("要关联的Spec编号不能与当前编辑的Spec编号一样！", "错误", Messagebox.OK, Messagebox.ERROR);
            int_dependsOnWho.select();
            return;
        }
        if (who!=null){
            if (currentSpec.getTspecSetOnWho()==null){
                currentSpec.setTspecSetOnWho(new HashSet<Tspec>());
            }
            currentSpec.getTspecSetOnWho().add(who);
            who.getTspecSetOnMe().add(currentSpec);
            daoService.edit(who);
            refreshDataBinding();
        }else{
            Messagebox.show("没有编号为"+whoid+"的Spec！", "错误", Messagebox.OK, Messagebox.ERROR);
            int_dependsOnWho.select();
        }
    }
    public void onClick$btn_dependsOnMe() throws InterruptedException{
        Integer whoid = int_dependsOnMe.getValue();
        Tspec who = daoService.findOneEntityById(Tspec.class, whoid);
        if (whoid.equals(currentSpec.getNumspecid())){
            Messagebox.show("要关联的Spec编号不能与当前编辑的Spec编号一样！", "错误", Messagebox.OK, Messagebox.ERROR);
            int_dependsOnMe.select();
            return;
        }
        if (who!=null){
            if (currentSpec.getTspecSetOnMe()==null){
                currentSpec.setTspecSetOnMe(new HashSet<Tspec>());
            }
            currentSpec.getTspecSetOnMe().add(who);
            who.getTspecSetOnWho().add(currentSpec);
            daoService.edit(who);
            refreshDataBinding();
        }else{
            Messagebox.show("没有编号为"+whoid+"的Spec！", "错误", Messagebox.OK, Messagebox.ERROR);
            int_dependsOnMe.select();
        }
    }

    public void onClick$menu_rm_dpom() {
        Listitem selItem = list_dependsOnMe.getSelectedItem();
        if (selItem!=null){
            Tspec who = (Tspec)selItem.getValue();
            who.getTspecSetOnWho().remove(currentSpec);
            daoService.edit(who);
            currentSpec.getTspecSetOnMe().remove(who);
            refreshDataBinding();
        }
    }
    public void onClick$menu_rm_dpow() {
        Listitem selItem = list_dependsOnWho.getSelectedItem();
        if (selItem!=null){
            Tspec who = (Tspec)selItem.getValue();
            who.getTspecSetOnMe().remove(currentSpec);
            daoService.edit(who);
            currentSpec.getTspecSetOnWho().remove(who);
            refreshDataBinding();
        }
    }

    public void onClick$btn_cancel() {
        Event closeEvent = new Event("onClose", this.self, null);
        Events.postEvent(closeEvent);
    }

    public void refreshDataBinding(){
//        if (currentSpec.getNumspecid()!=null){
//            sp.saveTspec(currentSpec);
//        }
        AnnotateDataBinder binder = (AnnotateDataBinder) this.self.getAttribute("binder", true);
        binder.loadAll();
    }

    public void onClick$btn_save() {
        String hmids = getCheckboxValues("cbhm", ",", this.self);
        //String ctids = getCheckboxValues("cbct", ",", this.self);
        currentSpec.setVc2httpmethod(hmids);
        final Component selfcomp = this.self;
        ConfirmBoxEventListener listener = new ConfirmBoxEventListener();
        listener.setOk_operation(new EventListenerAction() {
            public void execute() {
                refreshDataBinding();
            }
        });
        listener.setCancel_operation(new EventListenerAction() {
            public void execute() {
                Event closeEvent = new Event("onClose", selfcomp, null);
                Events.postEvent(closeEvent);
            }
        });
        try {
            sp.saveTspec(currentSpec);
            Messagebox.show("保存成功！继续编辑？", "确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, listener);
        } catch (Exception e) {
            log.warning("Exception when Save:",e);
            try {
                Messagebox.show("错误：" + e.getLocalizedMessage(), "错误", Messagebox.OK, Messagebox.ERROR);
            } catch (InterruptedException ex) {
                Logger.getLogger(SpecEditorComposer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void onClick$btn_addresponse(){
        List<Tresponse> tresponseSet = currentSpec.getTresponseSet();
        Tresponse resp = new Tresponse(null, "返回结果标签", 1, 1,1, "描述");
        resp.setNumdatastructid(nonstruct);
        resp.setNumspecid(currentSpec);
        tresponseSet.add(resp);
        refreshDataBinding();
    }
    public void onClick$btn_delresponse(){
        List<Tresponse> tresponseSet = currentSpec.getTresponseSet();
        Set<Listitem> selitems = resplist.getSelectedItems();
        List<String> respids = new ArrayList<String>();
        for (Listitem item : selitems) {
            Tresponse tresp = (Tresponse) item.getValue();
            tresponseSet.remove(tresp);
            if (tresp.getNumresponseid() != null) {
                respids.add(tresp.getNumresponseid() + "");
            }
        }
        sp.delResponseById(respids.toArray(new String[respids.size()]));
        refreshDataBinding();
    }
    public void onClick$btn_addparam() {
        List<Trequestparam> trequestparamSet = currentSpec.getTrequestparamSet();
        Trequestparam trequestparam = new Trequestparam(null, "参数名", 1, 1, 1, "输入参数描述", 1);
        trequestparam.setNumspecid(currentSpec);
        trequestparamSet.add(trequestparam);
        refreshDataBinding();
    }

    public void onClick$btn_delparam() {
        List<Trequestparam> trequestparamSet = currentSpec.getTrequestparamSet();
        Set<Listitem> selitems = paramgrid.getSelectedItems();
        List<String> paramids = new ArrayList<String>();
        for (Listitem item : selitems) {
            Trequestparam tparam = (Trequestparam) item.getValue();
            trequestparamSet.remove(tparam);
            if (tparam.getNumparamid() != null) {
                paramids.add(tparam.getNumparamid() + "");
            }
        }
        sp.delReqParamById(paramids.toArray(new String[paramids.size()]));
        refreshDataBinding();
    }

    public void onClick$btn_adderr() {
        List<Terrorcode> terrorcodeSet = currentSpec.getTerrorcodeSet();
        Terrorcode terrorcode = new Terrorcode(null, "错误码", "http状态码", "错误消息", "错误描述", 1);
        terrorcode.setNumspecid(currentSpec);
        terrorcodeSet.add(terrorcode);
        refreshDataBinding();
    }

    public void onClick$btn_delerr() {
        List<Terrorcode> terrorcodeSet = currentSpec.getTerrorcodeSet();
        Set<Listitem> selitems = errgrid.getSelectedItems();
        List<String> errorids = new ArrayList<String>();
        for (Listitem item : selitems) {
            Terrorcode terrcode = (Terrorcode) item.getValue();
            terrorcodeSet.remove(terrcode);
            if (terrcode.getNumerrorid() != null) {
                errorids.add(terrcode.getNumerrorid() + "");
            }
        }
        sp.delErrorDefById(errorids.toArray(new String[errorids.size()]));
        refreshDataBinding();
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        Map<String, Tspec> argmap = Executions.getCurrent().getArg();
        currentSpec = argmap.get("editingSpec");
        initReqParamSet();
        initErrorSet();
        initResponse();
        super.doAfterCompose(comp);
        autoCheck("cbhm", currentSpec.getVc2httpmethod(), ",", comp);
    }

    private void initErrorSet() {
        if (currentSpec.getTerrorcodeSet() == null) {
            currentSpec.setTerrorcodeSet(new ArrayList<Terrorcode>());
        }
    }

    private void initReqParamSet() {
        if (currentSpec.getTrequestparamSet() == null) {
            currentSpec.setTrequestparamSet(new ArrayList<Trequestparam>());
        }
    }

    private void initResponse() {
        if (currentSpec.getTresponseSet() == null){
            currentSpec.setTresponseSet(new ArrayList<Tresponse>());
        }
    }

    private void autoCheck(String cb_prefix, String ids, String delimiter, Component comp) {
        String[] arrids = ids.split(delimiter);
        for (String id : arrids) {
            Checkbox cbox = (Checkbox) comp.getFellow(cb_prefix + "_" + id);
            cbox.setChecked(true);
        }
    }

    private String getCheckboxValues(String cb_prefix, String delimiter, Component comp) {

        int i = 0;
        StringBuilder sb = new StringBuilder();
        if (cb_prefix.equals("cbhm")) {
            String allids = HttpMethod.getMultiIds(HttpMethod.values(), delimiter);
            for (String id : allids.split(delimiter)) {
                Checkbox cbox = (Checkbox) comp.getFellow(cb_prefix + "_" + id);
                if (cbox.isChecked()) {
                    if (++i > 1) {
                        sb.append(",");
                    }
                    sb.append(id);
                }
            }
        } else if (cb_prefix.equals("cbct")) {
            String allids = ContentType.getMultiIds(ContentType.values(), delimiter);
            for (String id : allids.split(delimiter)) {
                Checkbox cbox = (Checkbox) comp.getFellow(cb_prefix + "_" + id);
                if (cbox.isChecked()) {
                    if (++i > 1) {
                        sb.append(",");
                    }
                    sb.append(id);
                }
            }
        }
        return sb.toString();
    }

    public List<Baseurl> getBaseUrls() {
        return sp.getBaseurls();
    }

    public Tspec getCurrentSpec() {
        return currentSpec;
    }

    public void setCurrentSpec(Tspec currentSpec) {
        this.currentSpec = currentSpec;
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

    public List<Tenumgroup> getAllEnableEnumGroups(){
        return sp.getAllEnableEnumGroups();
    }

    public List<Tspeccategory> getAllSubCategory() {
        return cp.getAllSubCate();
    }

    public List<Tdatastruct> getAllAvailDataStructs(){
        return sp.getAllDataStruct(null,null, true, -1, -1);
    }
}
