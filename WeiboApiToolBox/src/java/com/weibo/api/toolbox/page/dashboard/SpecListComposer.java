package com.weibo.api.toolbox.page.dashboard;

import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.service.dashboard.DashBoardService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author x-spirit
 */
public class SpecListComposer extends GenericForwardComposer{

    private static final Log log = Log.lookup(SpecListComposer.class);
    DashBoardService dbs = (DashBoardService) SpringUtil.getBean("dashBoardService");
    List<Tspec> specList;

    Listbox updatedSpecList;
    Listheader speclh_update;
    Paging specPaging;
    Intbox idfilter;
    Textbox mainresfilter;
    Textbox subresfilter;

    public void onClick$link_specrefresh() {
        specPaging.setActivePage(specPaging.getActivePage());
        onPaging$specPaging();
    }

    public void onPaging$specPaging() {
        specList = getData();
        reloadDataBind();
    }
    public void onChange$idfilter(){
        onPaging$specPaging();
    }
    public void onChange$mainresfilter(){
        onPaging$specPaging();
    }
    public void onChange$subresfilter(){
        onPaging$specPaging();
    }
    public void onOK$idfilter(){
        onPaging$specPaging();
    }
    public void onOK$mainresfilter(){
        onPaging$specPaging();
    }
    public void onOK$subresfilter(){
        onPaging$specPaging();
    }
    public void onClick$slp_viewspec(){
        if (updatedSpecList.getSelectedCount() > 0) {
            Tspec currentSpec = (Tspec) updatedSpecList.getSelectedItem().getValue();
            Map<String, Tspec> argmap = new HashMap<String, Tspec>();
            argmap.put("editingSpec", currentSpec);
            showEditorWindow(argmap);
        }
    }


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        specList = getData();
    }
    private void reloadDataBind() {
        AnnotateDataBinder binder = (AnnotateDataBinder) this.self.getAttribute("binder", true);
        binder.loadAll();
    }

    private List<Tspec> getData() {
        Integer id = idfilter==null?null:idfilter.getValue();
        String mainres = mainresfilter==null?null:mainresfilter.getValue();
        String subres = subresfilter==null?null:subresfilter.getValue();
        int totalcount = dbs.getSpecCountByCondition(id, mainres, subres);
        specPaging.setTotalSize(totalcount);
        int pgno = specPaging.getActivePage();
        int pageSize = specPaging.getPageSize();
        //System.out.println("total:" + totalcount + "pgno:" + pgno + "pageSize:" + pageSize);
        int start = pageSize * pgno;
        return dbs.getSpecByCondition(id, mainres,
                subres, "t.datupdated", "desc", pageSize, start);
    }


    public List<Tspec> getSpecList() {
        specList = getData();
        return specList;
    }

    public void setSpecList(List<Tspec> specList) {
        this.specList = specList;
    }

    public void setUpdatedSpecList(Listbox updatedSpecList) {
        this.updatedSpecList = updatedSpecList;
    }

    private void showEditorWindow(Map<String, Tspec> argmap) {
        try {
            Window speceditor = (Window) Executions.createComponents("/dashboard/specviewer.zul", null, argmap);
            speceditor.doModal();
        } catch (InterruptedException ex) {
            log.warning("InterruptedException:", ex);
        } catch (SuspendNotAllowedException ex) {
            log.warning("SuspendNotAllowedException:", ex);
        }
    }

}
