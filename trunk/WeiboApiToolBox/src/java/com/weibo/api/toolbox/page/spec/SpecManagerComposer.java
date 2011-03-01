package com.weibo.api.toolbox.page.spec;

import com.weibo.api.spec.basic.BaseArgument;
import com.weibo.api.spec.wadl.WadlBinding;
import com.weibo.api.spec.wadl.wadl20090202.Application;
import com.weibo.api.toolbox.common.enumerations.AcceptType;
import com.weibo.api.toolbox.common.enumerations.ApiStatus;
import com.weibo.api.toolbox.common.enumerations.ApiType;
import com.weibo.api.toolbox.common.enumerations.AuthType;
import com.weibo.api.toolbox.common.enumerations.HttpMethod;
import com.weibo.api.toolbox.common.enumerations.RateLimit;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Baseurl;
import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.service.spec.CategoryProvider;
import com.weibo.api.toolbox.service.spec.SpecProvider;
import com.weibo.api.toolbox.util.CodeMirrorSyntax;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.zkoss.lang.Strings;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Tree;

/**
 *
 * @author x-spirit
 */
public class SpecManagerComposer extends GenericForwardComposer {

    private static final Log log = Log.lookup(SpecManagerComposer.class);
    private static final long serialVersionUID = 307734780558131403L;
    CategoryProvider cp = (CategoryProvider) SpringUtil.getBean("categoryProvider");
    SpecProvider sp = (SpecProvider) SpringUtil.getBean("specProvider");
    IJpaDaoService daoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");
    WadlBinding wadlbinder = (WadlBinding) SpringUtil.getBean("wadlbinder");
    BaseArgument baseArgument = (BaseArgument)SpringUtil.getBean("baseArgument");
    
    private Tree catetree;
    private Listbox specList;
    private SpecCategoryTreeModel specTreeModel;
    private Tspec currentSpec;
    List<Baseurl> baseUrls = sp.getBaseurls();
    List specListByCate;
    Intbox idfilter;
    Textbox versionfilter;
    String docbase = (Strings.isEmpty(baseArgument.getFileBase())
            ||baseArgument.getFileBase().equals("${webdoc}"))?
                Executions.getCurrent().getDesktop().getWebApp().getRealPath(baseArgument.getAllDocDir()):
                baseArgument.getFileBase()+"/"+baseArgument.getAllDocDir();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        catetree.setTreeitemRenderer(new SpecTreeRender());
        specTreeModel = new SpecCategoryTreeModel(cp.getRootCate());
        catetree.setModel(specTreeModel);
    }

    public void onClick$ctxm_genwadllist() throws InterruptedException {
        Tspeccategory cate = null;
        if (catetree.getSelectedCount() > 0) {
            Set selitems = catetree.getSelectedItems();
            for (Object sel : selitems) {
                Treeitem ti = (Treeitem) sel;
                cate = (Tspeccategory) ti.getValue();
            }
            List<Tspec> _specList = sp.getSpecListByCategory(cate, null, null);

            if (ToolBoxUtil.isNotEmpty(_specList)){
                String docpath = genMultiSpecInOneWadl(_specList);
                Map windowparam = new HashMap();
                windowparam.put(SpecDocViewer.ARG_DOCPATH, docpath);
                windowparam.put(SpecDocViewer.ARG_SYNTAX, CodeMirrorSyntax.XML.lowerName());
                showDocViewer(windowparam);
            } else {
                Messagebox.show("该分类下没有有效的SPEC定义！", "提示", Messagebox.OK, Messagebox.EXCLAMATION);
            }
            
        }
    }

    public void onClick$ctxm_genwadl() {
        if (specList.getSelectedCount() > 0) {
            currentSpec = (Tspec) specList.getSelectedItem().getValue();
            String docpath = genOneSpecWadl(currentSpec);
            Map windowparam = new HashMap();
            windowparam.put(SpecDocViewer.ARG_DOCPATH, docpath);
            windowparam.put(SpecDocViewer.ARG_SYNTAX, CodeMirrorSyntax.XML.lowerName());
            showDocViewer(windowparam);
        }
    }

    public void onClick$ctxm_editspec() {
        if (specList.getSelectedCount() > 0) {
            currentSpec = (Tspec) specList.getSelectedItem().getValue();
            Map<String, Tspec> argmap = new HashMap<String, Tspec>();
            argmap.put("editingSpec", currentSpec);
            showEditorWindow(argmap);
        }
    }

    public void onClick$ctxm_newspec() {
        Tspeccategory cate = null;
        if (catetree.getSelectedCount() > 0) {
            Set selectedItems = catetree.getSelectedItems();
            for (Object o : selectedItems) {
                Treeitem ti = (Treeitem) o;
                cate = (Tspeccategory) ti.getValue();
            }
            if (cate != null) {
                currentSpec = makeNewSpec(cate);
                Map<String, Tspec> argmap = new HashMap<String, Tspec>();
                argmap.put("editingSpec", currentSpec);
                showEditorWindow(argmap);
            }
        }
    }

    public void onOK$idfilter() {
        refreshDataBinding();
    }

    public void onChange$idfilter() {
        refreshDataBinding();
    }

    public void onChange$versionfilter() {
        refreshDataBinding();
    }

    public void onOK$versionfilter() {
        refreshDataBinding();
    }

    private Tspec makeNewSpec(Tspeccategory cate) {
        Tspec spec = new Tspec();
        spec.setNumenable(1);
        spec.setNumapitype(ApiType.PUBLIC.getId());
        spec.setEnumAcceptType(AcceptType.WILDCARD);
        spec.setNumstatus(ApiStatus.DRAFT.getId());
        spec.setNumauthtype(AuthType.REQUIRED.getId());
        spec.setNumratelimittype(RateLimit.USER_IP.getId());
        spec.setVc2httpmethod(HttpMethod.getMultiIds(new HttpMethod[]{HttpMethod.GET}, ","));
        spec.setNumbaseurlid(daoService.findOneEntityById(Baseurl.class, 1));
        spec.setNumcateid(cate);
        spec.setNumcateindex(sp.getMaxcateIndex(cate) + 1);
        spec.setVc2shortdesc("接口功能概述");
        spec.setVc2maindesc("接口功能详述");
        spec.setVc2cautions("注意事项1|\n注意事项2|\n注意事项3");
        return spec;
    }

    public List getSpecListByCate() {
        getSpecByCate();
        return specListByCate;
    }

    public void setSpecListByCate(List specListByCate) {
        this.specListByCate = specListByCate;
    }
    
    public void getSpecByCate() {
        int selcount = catetree.getSelectedCount();
        if (selcount > 0) {
            Treeitem ti = (Treeitem) catetree.getSelectedItems().iterator().next();
            Tspeccategory cate = (Tspeccategory) ti.getValue();
            specListByCate = sp.getSpecListByCategory(cate, idfilter.getValue(), versionfilter.getValue());
        } else {
            specListByCate = sp.getSpecListByCategory(null, idfilter.getValue(), versionfilter.getValue());
        }
    }

    private final class TreeitemDropListener implements EventListener {

        public void onEvent(Event event) throws Exception {
            DropEvent evt = (DropEvent) event;
            Listitem dragged = (Listitem) evt.getDragged();
            Treeitem dropped = (Treeitem) evt.getTarget().getParent();
            Tspeccategory dropcate = (Tspeccategory) dropped.getValue();
            Tspec dragspec = (Tspec) dragged.getValue();
            dragspec.setNumcateid(dropcate);
            daoService.edit(dragspec);
            refreshDataBinding();
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
            if (!isFirstLevel) {
                dataRow.setContext("specCatePopup");
                dataRow.setDroppable("true");
                dataRow.addEventListener("onDrop", new TreeitemDropListener());
            }
        }
    }

    private void showDocViewer(Map args) {
        try {
            Window wadlviewer = (Window) Executions.createComponents("/spec/specdocviewer.zul", null, args);
            wadlviewer.doModal();
        } catch (InterruptedException ex) {
            log.warning("InterruptedException:", ex);
        } catch (SuspendNotAllowedException ex) {
            log.warning("SuspendNotAllowedException:", ex);
        }
    }

    private void showEditorWindow(Map<String, Tspec> args) {
        try {
            Window speceditor = (Window) Executions.createComponents("/spec/speceditor.zul", null, args);
            speceditor.addEventListener("onClose", new EventListener() {

                public void onEvent(Event event) throws Exception {
                    refreshDataBinding();
                }
            });
            speceditor.doModal();
        } catch (InterruptedException ex) {
            log.warning("InterruptedException:", ex);
        } catch (SuspendNotAllowedException ex) {
            log.warning("SuspendNotAllowedException:", ex);
        }
    }

    private void refreshDataBinding() {
        AnnotateDataBinder binder = (AnnotateDataBinder) this.self.getAttribute("binder");
        binder.loadAll();
    }

    private String genMultiSpecInOneWadl(List<Tspec> _specList) {
        if (_specList != null && _specList.size() > 0) {
            Tspec samplespec = _specList.get(0);
            String doccatedir = getWadlDocCateDir(samplespec);
            ToolBoxUtil.deleteFile(new File(doccatedir));
            for (Tspec spec : _specList) {
                genOneSpecWadl(spec);
            }
            String docpath = doccatedir
                    + "/" + "pack" + ".wadl";
            Application app = wadlbinder.bindApplication(_specList);
            wadlbinder.marshall(app, docpath);
            return docpath;
        } else {
            return null;
        }
    }

    private String genOneSpecWadl(Tspec spec) {
        String docpath = getWadlDocCateDir(spec)
                + "/" + spec.getVc2version().trim()
                + "/" + spec.getNumspecid() + ".wadl";
        if (!new File(docpath).exists()){
            Application app = wadlbinder.bindApplication(spec);
            wadlbinder.marshall(app, docpath);
        }
        return docpath;
    }

    private String getWadlDocCateDir(Tspec samplespec){
        String dirpath = docbase + "/" + baseArgument.getSpecBase()
                    + "/" + samplespec.getEnumApiType().name()
                    + "/" + samplespec.getEnumApiStatus().name()
                    + "/" + samplespec.getNumcateid().getVc2catename().trim();
        return dirpath;
    }
}
