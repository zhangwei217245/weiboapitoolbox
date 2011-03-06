package com.weibo.api.toolbox.page.dashboard;

import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.service.dashboard.DashBoardService;
import java.util.List;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;

/**
 *
 * @author x-spirit
 */
public class SpecListComposer extends GenericForwardComposer{

    DashBoardService dbs = (DashBoardService) SpringUtil.getBean("dashBoardService");
    List<Tspec> specList;

    Listbox updatedSpecList;
    Paging specPaging;
    Textbox idfilter;
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
        String id = idfilter==null?null:idfilter.getValue();
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


    public Textbox getIdfilter() {
        return idfilter;
    }

    public void setIdfilter(Textbox idfilter) {
        this.idfilter = idfilter;
    }

    public Textbox getMainresfilter() {
        return mainresfilter;
    }

    public void setMainresfilter(Textbox mainresfilter) {
        this.mainresfilter = mainresfilter;
    }

    public List<Tspec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Tspec> specList) {
        this.specList = specList;
    }

    public Paging getSpecPaging() {
        return specPaging;
    }

    public void setSpecPaging(Paging specPaging) {
        this.specPaging = specPaging;
    }

    public Textbox getSubresfilter() {
        return subresfilter;
    }

    public void setSubresfilter(Textbox subresfilter) {
        this.subresfilter = subresfilter;
    }

    public Listbox getUpdatedSpecList() {
        return updatedSpecList;
    }

    public void setUpdatedSpecList(Listbox updatedSpecList) {
        this.updatedSpecList = updatedSpecList;
    }

}
