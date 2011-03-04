package com.weibo.api.toolbox.page.admin;

import com.weibo.api.toolbox.persist.entity.Tcategory;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;

/**
 *
 * @author x-spirit
 */
public class MenuManagerComposer extends GenericForwardComposer{
    private static final long serialVersionUID = -1473297473681404478L;

    public DefaultTreeModel treeModel = new DefaultTreeModel(null);
    public DefaultTreeNode root = new DefaultTreeNode(null);

    public List<Tcategory> cateList;
    
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        
        super.doAfterCompose(comp);
    }
}
