package com.weibo.api.toolbox.page.admin;

import com.weibo.api.toolbox.persist.entity.Tcategory;
import com.weibo.api.toolbox.persist.entity.Tmenuitem;
import com.weibo.api.toolbox.service.rbac.RbacProvider;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.spring.SpringUtil;
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
public class MenuManagerComposer extends GenericForwardComposer{
    private static final long serialVersionUID = -1473297473681404478L;

    private RbacProvider rbacProvider = (RbacProvider) SpringUtil.getBean("rbacProvider");
    
    public DefaultTreeModel treeModel;
    public DefaultTreeNode root;
    private Tree menutree;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
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
            if (data.getClass().equals(Tcategory.class)){
                name = ((Tcategory)data).getVc2catedesc();
                img = ((Tcategory)data).getImgEnable();
            }else if (data.getClass().equals(Tmenuitem.class)){
                name = ((Tmenuitem)data).getVc2itemdesc();
                img = ((Tmenuitem)data).getImgEnable();
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
