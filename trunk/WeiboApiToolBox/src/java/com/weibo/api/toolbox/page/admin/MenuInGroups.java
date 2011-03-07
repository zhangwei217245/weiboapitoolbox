/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.page.admin;

import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tcategory;
import com.weibo.api.toolbox.persist.entity.Tgroup;
import com.weibo.api.toolbox.persist.entity.Tmenuitem;
import com.weibo.api.toolbox.service.rbac.RbacProvider;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

/**
 *
 * @author x-spirit
 */
public class MenuInGroups extends GenericForwardComposer{
    
    private static final long serialVersionUID = -5833318568248520783L;


    private RbacProvider rbacProvider = (RbacProvider) SpringUtil.getBean("rbacProvider");
    IJpaDaoService jpaDaoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");
    public DefaultTreeModel treeModel;
    public DefaultTreeNode root;
    private Tree menutree;

    Tgroup currentGroup;


    public void onClick$btn_save(){
        if (menutree.getSelectedCount()>0){
            if (currentGroup!=null){
                Set<Tmenuitem> tmenuitemSet = currentGroup.getTmenuitemSet();
//                for(Tmenuitem menu : tmenuitemSet){
//                    if(menu.getTgroupSet()!=null){
//                        menu.getTgroupSet().remove(currentGroup);
//                    }
//                }
                currentGroup.getTmenuitemSet().clear();
                jpaDaoService.edit(currentGroup);
                Set<Treeitem> selitems = menutree.getSelectedItems();
                for(Treeitem trtm : selitems){
                    Object value = trtm.getValue();
                    if(value.getClass().equals(Tmenuitem.class)){
                        currentGroup.getTmenuitemSet().add((Tmenuitem)value);
                    }
                }
                jpaDaoService.edit(currentGroup);
            }
        }
    }
    public void onClick$btn_cancel(){
        Event closeEvent = new Event("onClose", this.self, null);
        Events.postEvent(closeEvent);
    }
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        Map<String, Tgroup> argmap = Executions.getCurrent().getArg();
        currentGroup = argmap.get("selgroup");
        List<Tcategory> cateList = rbacProvider.getAllCateList();
        processTreeModel(cateList);
        super.doAfterCompose(comp);
        menutree.setModel(treeModel);
        menutree.setItemRenderer(new MenuTreeRender());
    }

    private final class MenuTreeRender implements TreeitemRenderer{

        public void render(Treeitem trtm, Object node) throws Exception {
            Treerow tr = new Treerow();
            String name = null;
            String img = null;
            Object data = ((DefaultTreeNode)node).getData();
            trtm.setValue(data);
            if (data.getClass().equals(Tcategory.class)){
                name = ((Tcategory)data).getVc2catedesc();
                img = ((Tcategory)data).getImgEnable();
                trtm.setCheckable(false);
            }else if (data.getClass().equals(Tmenuitem.class)){
                name = ((Tmenuitem)data).getVc2itemdesc();
                img = ((Tmenuitem)data).getImgEnable();
                trtm.setCheckable(true);
                boolean isInGroup = currentGroup.getTmenuitemSet().contains((Tmenuitem)data);
                if (isInGroup){
                    trtm.setSelected(true);
                }
            }
            tr.appendChild(new Treecell(name,img));
            trtm.appendChild(tr);
        }
    }

    private void processTreeModel(List<Tcategory> cateList) {
        root = new DefaultTreeNode(null,new ArrayList<DefaultTreeNode>());
        for (Tcategory cate : cateList){
            DefaultTreeNode node = new DefaultTreeNode(cate,new ArrayList<DefaultTreeNode>());
            processMenuItemNode(node,cate);
            root.add(node);
        }
        treeModel = new DefaultTreeModel(root);
    }

    private void processMenuItemNode(DefaultTreeNode node, Tcategory cate) {
        List<Tmenuitem> tmenuitemList = rbacProvider.getAllMenuByCate(cate);
        if (ToolBoxUtil.isNotEmpty(tmenuitemList)){
            for (Tmenuitem item : tmenuitemList){
                node.add(new DefaultTreeNode(item));
            }
        }
    }
}
