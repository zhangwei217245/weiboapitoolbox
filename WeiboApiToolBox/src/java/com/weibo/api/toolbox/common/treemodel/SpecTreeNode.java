package com.weibo.api.toolbox.common.treemodel;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.SimpleTreeNode;

/**
 *
 * @author x-spirit
 */
public class SpecTreeNode extends SimpleTreeNode {

    private static final long serialVersionUID = 1L;

    private Object _context;
    private boolean open = false;

    public SpecTreeNode(Object data) {
        super(data, new ArrayList());
    }

    public SpecTreeNode(Object data, List children) {
        super(data, children);
    }

    public SpecTreeNode(Object data, List children, boolean open) {
        super(data, children);
        setOpen(open);
    }

    public SpecTreeNode(Object data, Object context) {
        super(data, new ArrayList());
        setContext(context);
    }

    public SpecTreeNode(Object data, Object context, List children) {
        super(data, children);
        setContext(context);
    }

    public SpecTreeNode(Object data, Object context, List children, boolean open) {
        super(data, children);
        setContext(context);
        setOpen(open);
    }

    public void addChildren (List children){
        this.getChildren().addAll(children);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setContext(Object context) {
        _context = context;
    }

    public Object getContext() {
        return _context;
    }
}
