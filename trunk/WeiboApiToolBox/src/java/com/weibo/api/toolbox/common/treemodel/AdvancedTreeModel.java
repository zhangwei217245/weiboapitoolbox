/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.common.treemodel;

import java.util.Arrays;
import org.zkoss.util.logging.Log;
import org.zkoss.zul.SimpleTreeModel;
import org.zkoss.zul.SimpleTreeNode;
import org.zkoss.zul.event.TreeDataEvent;

/**
 *
 * @author x-spirit
 */
public class AdvancedTreeModel extends SimpleTreeModel {

    private static final long serialVersionUID = -8555440226174449129L;
    private static final Log log = Log.lookup(AdvancedTreeModel.class);

    

    SimpleTreeNode _root;


    public AdvancedTreeModel(SimpleTreeNode root) {
        super(root);
        _root = root;
    }



    @Override
    public Object getChild(Object parent, int index) {
        return super.getChild(parent, index);
    }

    @Override
    public int getChildCount(Object parent) {
        return super.getChildCount(parent);
    }

    @Override
    public boolean isLeaf(Object node) {
        return super.isLeaf(node);
    }


    
    /**
     * remove the nodes which parent is <code>parent</code> with indexes
     * <code>indexes</code>
     *
     * @param parent
     *            The parent of nodes are removed
     * @param indexFrom
     *            the lower index of the change range
     * @param indexTo
     *            the upper index of the change range
     * @throws IndexOutOfBoundsException
     *             - indexFrom < 0 or indexTo > number of parent's children
     */
    public void remove(SimpleTreeNode parent, int indexFrom, int indexTo) throws IndexOutOfBoundsException {
        SimpleTreeNode stn = parent;
        for (int i = indexTo; i >= indexFrom; i--) {
            try {
                stn.getChildren().remove(i);
            } catch (Exception exp) {
                log.warning("AdvancedTreeModel.remove", exp);
                System.out.println(exp.getMessage());
            }
        }
        if (dfSearch(_root, stn)) {
            fireEvent(parent, indexFrom, indexTo, TreeDataEvent.INTERVAL_REMOVED);
        }
    }

    public void remove(SimpleTreeNode target) throws IndexOutOfBoundsException {
        int index = 0;
        SimpleTreeNode parent = null;
        // find the parent and index of target
        parent = dfSearchParent(_root, target);
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index).equals(target)) {
                break;
            }
        }
        remove(parent, index, index);
    }

    /**
     * insert new nodes which parent is <code>parent</code> with indexes
     * <code>indexes</code> by new nodes <code>newNodes</code>
     *
     * @param parent
     *            The parent of nodes are inserted
     * @param indexFrom
     *            the lower index of the change range
     * @param indexTo
     *            the upper index of the change range
     * @param newNodes
     *            New nodes which are inserted
     * @throws IndexOutOfBoundsException
     *             - indexFrom < 0 or indexTo > number of parent's children
     */
    public void insert(Object parent, int indexFrom, int indexTo, SimpleTreeNode[] newNodes) throws IndexOutOfBoundsException {
        SimpleTreeNode stn = (SimpleTreeNode) parent;
        for (int i = indexFrom; i <= indexTo; i++) {
            try {
                stn.getChildren().add(i, newNodes[i - indexFrom]);
            } catch (Exception exp) {
                throw new IndexOutOfBoundsException("Out of bound: " + i + " while size=" + stn.getChildren().size());
            }
        }
        if (dfSearch(_root, stn)) {
            fireEvent(parent, indexFrom, indexTo, TreeDataEvent.INTERVAL_ADDED);
        }
    }

    /**
     * append new nodes which parent is <code>parent</code> by new nodes
     * <code>newNodes</code>
     *
     * @param parent
     *            The parent of nodes are appended
     * @param newNodes
     *            New nodes which are appended
     */
    public void add(SimpleTreeNode parent, Object[] newNodes) {
        SimpleTreeNode stn = (SimpleTreeNode) parent;
        int indexFrom = stn.getChildren().size();
        int indexTo = stn.getChildren().size() + newNodes.length - 1;
        stn.getChildren().addAll(Arrays.asList(newNodes));

        // if SimpleTreeNode exist in the model
        if (dfSearch(_root, stn)) {
            fireEvent(parent, indexFrom, indexTo, TreeDataEvent.INTERVAL_ADDED);
        }

    }

    private boolean dfSearch(Object node, Object target) {
        if (node.equals(target)) {
            return true;
        } else {
            int size = getChildCount(node);
            for (int i = 0; i < size; i++) {
                boolean flag = dfSearch(getChild(node, i), target);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    private SimpleTreeNode dfSearchParent(SimpleTreeNode node, SimpleTreeNode target) {
        if (node.getChildren().contains(target)) {
            return node;
        } else {
            int size = getChildCount(node);
            for (int i = 0; i < size; i++) {
                SimpleTreeNode parent = dfSearchParent((SimpleTreeNode) getChild(node, i), target);
                if (parent != null) {
                    return parent;
                }
            }
        }
        return null;
    }
}
