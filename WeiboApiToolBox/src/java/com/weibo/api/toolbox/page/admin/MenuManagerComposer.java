package com.weibo.api.toolbox.page.admin;

import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tcategory;
import com.weibo.api.toolbox.persist.entity.Tmenuitem;
import com.weibo.api.toolbox.service.rbac.RbacProvider;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.lang.Objects;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

/**
 *
 * @author x-spirit
 */
public class MenuManagerComposer extends GenericForwardComposer {

    private static final long serialVersionUID = -1473297473681404478L;
    private RbacProvider rbacProvider = (RbacProvider) SpringUtil.getBean("rbacProvider");
    IJpaDaoService daoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");
    private DefaultTreeModel treeModel;
    private DefaultTreeNode root;
    private Tree menutree;
    Textbox menu_name;
    Textbox menu_desc;
    Textbox menu_href;
    Textbox menu_img;
    Spinner menu_index;
    Checkbox menu_enable;
    Object selobj;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        List<Tcategory> cateList = rbacProvider.getAllCateList();
        processTreeModel(cateList);
        super.doAfterCompose(comp);
        menutree.setModel(getTreeModel());
        menutree.setItemRenderer(new MenuTreeRender());
    }

    private void refreshDataBinding() {
        List<Tcategory> cateList = rbacProvider.getAllCateList();
        processTreeModel(cateList);
        AnnotateDataBinder binder = (AnnotateDataBinder) this.self.getAttribute("binder");
        binder.loadAll();
        menutree.setModel(getTreeModel());
    }

    private void setValuesForCate(Tcategory cate) {
        menu_name.setValue(cate.getVc2catename());
        menu_desc.setValue(cate.getVc2catedesc());
        menu_href.setValue(cate.getVc2catehref());
        menu_img.setValue(cate.getVc2cateimg());
        menu_index.setValue(cate.getNumindex());
        menu_enable.setChecked(cate.getIsEnable());
    }

    private void setValuesForMenu(Tmenuitem menu) {
        menu_name.setValue(menu.getVc2itemname());
        menu_desc.setValue(menu.getVc2itemdesc());
        menu_href.setValue(menu.getVc2itemurl());
        menu_img.setValue(menu.getVc2itemimg());
        menu_index.setValue(menu.getNumindex());
        menu_enable.setChecked(menu.getIsEnable());
    }

    private void getValuesForCate(Tcategory cate) {
        cate.setVc2catename(menu_name.getValue());
        cate.setVc2catedesc(menu_desc.getValue());
        cate.setVc2catehref(menu_href.getValue());
        cate.setVc2cateimg(menu_img.getValue());
        cate.setNumindex(menu_index.getValue());
        cate.setIsEnable(menu_enable.isChecked());
    }

    private void getValuesForMenu(Tmenuitem menu) {
        menu.setVc2itemname(menu_name.getValue());
        menu.setVc2itemdesc(menu_desc.getValue());
        menu.setVc2itemurl(menu_href.getValue());
        menu.setVc2itemimg(menu_img.getValue());
        menu.setNumindex(menu_index.getValue());
        menu.setIsEnable(menu_enable.isChecked());
    }

    private final class MenuTreeRender implements TreeitemRenderer {

        public void render(Treeitem trtm, Object node) throws Exception {
            Treerow tr = new Treerow();
            String name = null;
            String img = null;
            Object data = ((DefaultTreeNode) node).getData();
            if (data.getClass().equals(Tcategory.class)) {
                name = ((Tcategory) data).getNumindex() + "." + ((Tcategory) data).getVc2catedesc();
                img = ((Tcategory) data).getImgEnable();
                tr.setContext("menuTreePopup");
                tr.setDroppable("true");
                tr.addEventListener("onDrop", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        DropEvent evt = (DropEvent) event;
                        Treeitem dragged_trtm = (Treeitem) evt.getDragged().getParent();
                        Treeitem dropped_trtm = (Treeitem) evt.getTarget().getParent();
                        if (dragged_trtm.getValue().getClass().equals(Tmenuitem.class)) {
                            Tcategory cate = (Tcategory) dropped_trtm.getValue();
                            Tmenuitem menu = (Tmenuitem) dragged_trtm.getValue();
                            rbacProvider.changeMenuCate(cate, menu);
                            refreshDataBinding();
                        }
                    }
                });
            } else if (data.getClass().equals(Tmenuitem.class)) {
                name = ((Tmenuitem) data).getNumindex() + "." + ((Tmenuitem) data).getVc2itemdesc();
                img = ((Tmenuitem) data).getImgEnable();
                tr.setDraggable("true");
                tr.setDroppable("true");
                tr.addEventListener("onDrop", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        DropEvent evt = (DropEvent) event;
                        Treeitem dragged_trtm = (Treeitem) evt.getDragged().getParent();
                        Treeitem dropped_trtm = (Treeitem) evt.getTarget().getParent();
                        if (dragged_trtm.getValue().getClass().equals(Tmenuitem.class)) {
                            Tmenuitem menu_1 = (Tmenuitem) dropped_trtm.getValue();
                            Tmenuitem menu_2 = (Tmenuitem) dragged_trtm.getValue();
                            if (menu_1.getNumcateid().equals(menu_2.getNumcateid())) {
                                rbacProvider.updateMenuIndex(menu_2, menu_2.getNumindex(), menu_1.getNumindex());
                            } else {
                                rbacProvider.changeMenuCate(menu_1.getNumcateid(), menu_2);
                                rbacProvider.updateMenuIndex(menu_2, menu_2.getNumindex(), menu_1.getNumindex());
                            }
                            refreshDataBinding();
                        }
                    }
                });
            }
            tr.appendChild(new Treecell(name, img));
            trtm.setValue(data);
            trtm.appendChild(tr);
        }
    }

    public void onClick$menutree() {
        if (menutree.getSelectedCount() > 0) {
            Treeitem selitem = menutree.getSelectedItem();
            selobj = selitem.getValue();
            if (selobj.getClass().equals(Tcategory.class)) {
                setValuesForCate((Tcategory) selobj);
            } else if (selobj.getClass().equals(Tmenuitem.class)) {
                setValuesForMenu((Tmenuitem) selobj);
            }
        }
    }

    private void processTreeModel(List<Tcategory> cateList) {
        setRoot(new DefaultTreeNode(null, new ArrayList<DefaultTreeNode>()));
        for (Tcategory cate : cateList) {
            DefaultTreeNode node = new DefaultTreeNode(cate, new ArrayList<DefaultTreeNode>());
            processMenuItemNode(node, cate);
            getRoot().add(node);
        }
        setTreeModel(new DefaultTreeModel(getRoot()));
    }

    private void processMenuItemNode(DefaultTreeNode node, Tcategory cate) {
        List<Tmenuitem> tmenuitemList = rbacProvider.getAllMenuByCate(cate);
        if (ToolBoxUtil.isNotEmpty(tmenuitemList)) {
            for (Tmenuitem item : tmenuitemList) {
                node.add(new DefaultTreeNode(item));
            }
        }
    }

    public void onClick$mtp_createTop() {
        selobj = new Tcategory(null, "newmenu", "新菜单", 1, 1);
        ((Tcategory) selobj).setVc2catehref("NONE");
        ((Tcategory) selobj).setVc2cateimg("");
        ((Tcategory) selobj).setIsEnable(true);
        ((Tcategory) selobj).setNumindex(rbacProvider.getNextIndexForCate());
        setValuesForCate((Tcategory) selobj);
    }

    public void onClick$mtp_createSub() {
        Tcategory paraCate = (Tcategory) selobj;
        selobj = new Tmenuitem(null, "cate.menu", "/new.zul","新子菜单", 1, 1);
        ((Tmenuitem) selobj).setVc2itemimg("");
        ((Tmenuitem) selobj).setNumcateid(paraCate);
        ((Tmenuitem) selobj).setNumindex(rbacProvider.getNextIndexForMenu(paraCate));
        setValuesForMenu(((Tmenuitem) selobj));
    }

    public void onClick$menusave() throws InterruptedException {
        if (selobj.getClass().equals(Tcategory.class)) {
            if (rbacProvider.checkDuplicatedIndexForCate((Tcategory) selobj,menu_index.getValue())) {
                Messagebox.show("指定的父菜单排序值已经存在", "错误", Messagebox.OK, Messagebox.ERROR);
                return;
            }
            getValuesForCate((Tcategory) selobj);
        } else if (selobj.getClass().equals(Tmenuitem.class)) {
            if (rbacProvider.checkDuplicatedIndexForMenu((Tmenuitem) selobj, menu_index.getValue())) {
                Messagebox.show("指定的子菜单排序值已经存在", "错误", Messagebox.OK, Messagebox.ERROR);
                return;
            }
            getValuesForMenu((Tmenuitem) selobj);
        }
        daoService.edit(selobj);
        Messagebox.show("保存成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
        refreshDataBinding();
    }

    /**
     * @return the treeModel
     */
    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    /**
     * @param treeModel the treeModel to set
     */
    public void setTreeModel(DefaultTreeModel treeModel) {
        this.treeModel = treeModel;
    }

    /**
     * @return the root
     */
    public DefaultTreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(DefaultTreeNode root) {
        this.root = root;
    }
    
}
