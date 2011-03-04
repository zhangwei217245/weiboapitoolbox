package com.weibo.api.toolbox.page.admin;

import com.weibo.api.toolbox.persist.entity.Tcategory;
import com.weibo.api.toolbox.persist.entity.Tmenuitem;
import com.weibo.api.toolbox.service.rbac.RbacProvider;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.util.List;
import java.util.Set;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
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

    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        List<Tcategory> cateList = rbacProvider.getAllCateList();
        processTreeModel(cateList);
        super.doAfterCompose(comp);
    }

    private final class MenuTreeRender implements TreeitemRenderer{

        public void render(Treeitem trtm, Object data) throws Exception {
            Treerow tr = new Treerow();
            
            if (data.getClass().equals(Tcategory.class)){
                String name = ((Tcategory)data).getVc2catename();
                tr.appendChild(new Treecell());
            }else if (data.getClass().equals(Tmenuitem.class)){

            }
            
            trtm.appendChild(tr);
            
        }
    }

    private void processTreeModel(List<Tcategory> cateList) {
        root = new DefaultTreeNode(null);
        for (Tcategory cate : cateList){
            DefaultTreeNode node = new DefaultTreeNode(cate);
            processMenuItemNode(node,cate);
            root.add(node);
        }
        treeModel = new DefaultTreeModel(root);
    }

    private void processMenuItemNode(DefaultTreeNode node, Tcategory cate) {
        Set<Tmenuitem> tmenuitemSet = cate.getTmenuitemSet();
        if (ToolBoxUtil.isNotEmpty(tmenuitemSet)){
            for (Tmenuitem item : tmenuitemSet){
                node.add(new DefaultTreeNode(item));
            }
        }
    }
}
