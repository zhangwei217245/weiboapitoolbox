/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.common;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 *
 * @author x-spirit
 */
public class ToolBoxCommonComposer extends GenericForwardComposer{

    private static final long serialVersionUID = -7333126329246410784L;

    @Override
    public boolean doCatch(Throwable ex) throws Exception {
        return super.doCatch(ex);
    }

    @Override
    public void doFinally() throws Exception {
        super.doFinally();
    }

    @Override
    public void onEvent(Event evt) throws Exception {
        super.onEvent(evt);
    }


}
