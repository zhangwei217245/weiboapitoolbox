/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.page.dashboard;

import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.persist.entity.Tuser;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 *
 * @author x-spirit
 */
public class UserViewComposer extends GenericForwardComposer{

    Tuser currUser;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        Map<String, Tuser> argmap = Executions.getCurrent().getArg();
        currUser = argmap.get("viewingUser");
        super.doAfterCompose(comp);
    }

    public Tuser getCurrUser() {
        return currUser;
    }

    public void setCurrUser(Tuser currUser) {
        this.currUser = currUser;
    }

}
