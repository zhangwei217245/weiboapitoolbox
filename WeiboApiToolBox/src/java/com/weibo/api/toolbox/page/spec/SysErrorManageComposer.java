package com.weibo.api.toolbox.page.spec;

import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Syserror;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.service.spec.CategoryProvider;
import com.weibo.api.toolbox.service.spec.SysDataProvider;
import java.util.List;

import org.zkoss.spring.SpringUtil;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

/**
 *
 * @author x-spirit
 */
public class SysErrorManageComposer extends GenericForwardComposer{

    private static final long serialVersionUID = 443580061839870882L;
    private static final Log log = Log.lookup(SysErrorManageComposer.class);

    IJpaDaoService daoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");
    SysDataProvider sdprovider = (SysDataProvider)SpringUtil.getBean("sysDataProvider");
    CategoryProvider cp = (CategoryProvider) SpringUtil.getBean("categoryProvider");

    private Tree catetree;
    private Listbox sysErrorList;
    private SpecCategoryTreeModel specTreeModel;

    private List sysErrorByCate;

    private Syserror currSysError;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        catetree.setTreeitemRenderer(new SpecTreeRender());
        specTreeModel = new SpecCategoryTreeModel(cp.getRootCate());
        catetree.setModel(specTreeModel);
    }

    public void onSelect$sysErrorList(){
        Listitem selectedItem = sysErrorList.getSelectedItem();
        currSysError = (Syserror)selectedItem.getValue();
    }

    public void onClick$ctxm_newSysError(){
        currSysError = new Syserror(null, "错误码", "HTTP状态码", "错误消息", "错误描述", 1);
        currSysError.setNumcateid((Tspeccategory)catetree.getSelectedItem().getValue());
        refreshDataBinding();
    }

    public void onClick$ctxm_delSysError(){
        sdprovider.deleteSysError(currSysError);
        refreshDataBinding();
    }

    public void onClick$btn_save(){
        sdprovider.saveSysError(currSysError);
        refreshDataBinding();
    }

    private final class SpecTreeRender implements TreeitemRenderer {

        public void render(Treeitem treeItem, Object treeNode) throws Exception {
            final Tspeccategory cate = (Tspeccategory) treeNode;
            Treerow dataRow = new Treerow();
            dataRow.setParent(treeItem);
            treeItem.setValue(cate);
            String labelName = cate.getNumindex() + ". " + cate.getVc2catename();
            String img = cate.getNumenable() > 0
                    ? "right_small.png" : "wrong_small.png";
            boolean isFirstLevel = cate.getNumparentcateid().getNumcateid() == 1 ? true : false;
            Treecell tc = new Treecell(labelName, "/img/smallicons/" + img);

            dataRow.appendChild(tc);
            if (isFirstLevel) {
                dataRow.setContext("specCatePopup");
                dataRow.setDroppable("true");
                dataRow.addEventListener("onDrop", new TreeitemDropListener());
            }
        }
    }

    private final class TreeitemDropListener implements EventListener {

        public void onEvent(Event event) throws Exception {
            DropEvent evt = (DropEvent) event;
            Listitem dragged = (Listitem) evt.getDragged();
            Treeitem dropped = (Treeitem)evt.getTarget().getParent();
            Syserror dragged_error = (Syserror)dragged.getValue();
            Tspeccategory dropped_cate = (Tspeccategory)dropped.getValue();
            dragged_error.setNumcateid(dropped_cate);
            sdprovider.saveSysError(dragged_error);
            refreshDataBinding();
        }
    }

    public void loadSysErrorByCate(){
        int selcount = catetree.getSelectedCount();
        if (selcount > 0) {
            Treeitem ti = (Treeitem) catetree.getSelectedItems().iterator().next();
            Tspeccategory cate = (Tspeccategory) ti.getValue();
            sysErrorByCate = sdprovider.getSyserrorByCate(cate);
        } else {
            sysErrorByCate = sdprovider.getSyserrorByCate(null);
        }
    }

    public List getSysErrorByCate() {
        loadSysErrorByCate();
        return sysErrorByCate;
    }

    public void setSysErrorByCate(List sysErrorByCate) {
        this.sysErrorByCate = sysErrorByCate;
    }

    private void refreshDataBinding() {
        AnnotateDataBinder binder = (AnnotateDataBinder) this.self.getAttribute("binder");
        binder.loadAll();
    }

    public Syserror getCurrSysError() {
        return currSysError;
    }

    public void setCurrSysError(Syserror currSysError) {
        this.currSysError = currSysError;
    }

    
}
