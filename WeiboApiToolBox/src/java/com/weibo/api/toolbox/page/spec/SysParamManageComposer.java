package com.weibo.api.toolbox.page.spec;

import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.common.enumerations.ParamStyle;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Sysparam;
import com.weibo.api.toolbox.persist.entity.Tenumgroup;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.service.spec.CategoryProvider;
import com.weibo.api.toolbox.service.spec.SpecProvider;
import com.weibo.api.toolbox.service.spec.SysDataProvider;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
public class SysParamManageComposer extends GenericForwardComposer{
    
    private static final Log log = Log.lookup(SysParamManageComposer.class);
    private static final long serialVersionUID = -4897503189584153001L;

    IJpaDaoService daoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");
    SysDataProvider sdprovider = (SysDataProvider)SpringUtil.getBean("sysDataProvider");
    SpecProvider sp = (SpecProvider) SpringUtil.getBean("specProvider");
    CategoryProvider cp = (CategoryProvider) SpringUtil.getBean("categoryProvider");

    private Tree catetree;
    private Listbox sysParamList;
    private SpecCategoryTreeModel specTreeModel;

    private List sysParamByCate;

    private Sysparam currSysParam;


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        catetree.setTreeitemRenderer(new SpecTreeRender());
        specTreeModel = new SpecCategoryTreeModel(cp.getRootCate());
        catetree.setModel(specTreeModel);
    }

    public void onSelect$sysParamList(){
        Listitem selectedItem = sysParamList.getSelectedItem();
        currSysParam = (Sysparam)selectedItem.getValue();
    }

    public void onClick$ctxm_newSysParam(){
        currSysParam = new Sysparam(null, "参数名", 1, 1, 1, "参数描述", 1);
        currSysParam.setNumcateid((Tspeccategory)catetree.getSelectedItem().getValue());
        refreshDataBinding();
    }

    public void onClick$ctxm_delSysParam(){
        sdprovider.deleteSysParam(currSysParam);
        refreshDataBinding();
    }

    public void onClick$btn_save(){
        sdprovider.saveSysParam(currSysParam);
        refreshDataBinding();
    }

    public void loadSysParamByCate(){
        int selcount = catetree.getSelectedCount();
        if (selcount > 0) {
            Treeitem ti = (Treeitem) catetree.getSelectedItems().iterator().next();
            Tspeccategory cate = (Tspeccategory) ti.getValue();
            sysParamByCate = sdprovider.getSysParametersByCate(cate);
        } else {
            sysParamByCate = sdprovider.getSysParametersByCate(null);
        }
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
            Sysparam dragged_param = (Sysparam)dragged.getValue();
            Tspeccategory dropped_cate = (Tspeccategory)dropped.getValue();
            dragged_param.setNumcateid(dropped_cate);
            sdprovider.saveSysParam(dragged_param);
            refreshDataBinding();
        }
    }

    private void refreshDataBinding() {
        AnnotateDataBinder binder = (AnnotateDataBinder) this.self.getAttribute("binder");
        binder.loadAll();
    }

    public List getSysParamByCate() {
        loadSysParamByCate();
        return sysParamByCate;
    }

    public void setSysParamByCate(List sysParamByCate) {
        this.sysParamByCate = sysParamByCate;
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

    public List<DataTypes> getAllDataTypes() {
        List<DataTypes> dtlst = Arrays.asList(DataTypes.values());
        Collections.sort(dtlst, new Comparator<DataTypes>() {

            public int compare(DataTypes o1, DataTypes o2) {
                return o1.getId() - o2.getId();
            }
        });
        return dtlst;
    }

    public List<Tenumgroup> getAllEnableEnumGroups(){
        return sp.getAllEnableEnumGroups();
    }

    public Sysparam getCurrSysParam() {
        return currSysParam;
    }

    public void setCurrSysParam(Sysparam currSysParam) {
        this.currSysParam = currSysParam;
    }

}
