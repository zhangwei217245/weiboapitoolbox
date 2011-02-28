/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.page.admin;

import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tgroup;
import com.weibo.api.toolbox.persist.qlgenerator.JPQLGenerator;
import com.weibo.api.toolbox.persist.qlgenerator.QLGenerator;
import java.util.List;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author x-spirit
 */
public class GroupManagerComposer extends GenericForwardComposer{
    private static final long serialVersionUID = -1473297473681404478L;
    IJpaDaoService jpaDaoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");

    Listbox grouplistbox;
    
    Tgroup currTgroup;

    public void onClick$groupadd(){
        currTgroup = new Tgroup(null, "新用户组","新用户组", 1);
        jpaDaoService.create(currTgroup);
        refreshDataBinding();
    }
    public void onClick$groupdel() throws InterruptedException{
        if (currTgroup.getNumgroupid().equals(1)||currTgroup.getNumgroupid().equals(2)){
            Messagebox.show("无法删除固化的用户组", "错误", Messagebox.OK, Messagebox.ERROR);
        }else{
            currTgroup.setNumenable(0);
            jpaDaoService.edit(currTgroup);
        }
        refreshDataBinding();
    }
    public void onClick$attrsave(){
        if (!(currTgroup.getNumgroupid().equals(1)||currTgroup.getNumgroupid().equals(2))){
            jpaDaoService.edit(currTgroup);
        }
        refreshDataBinding();
    }
    public List<Tgroup> getGroupList(){
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("t").from("Tgroup t");
        return jpaDaoService.findEntities(qlgen.toString(), null, true, -1, -1);
    }

    public Tgroup getCurrTgroup() {
        return currTgroup;
    }

    public void setCurrTgroup(Tgroup currTgroup) {
        this.currTgroup = currTgroup;
    }

    public void refreshDataBinding(){
        AnnotateDataBinder binder = (AnnotateDataBinder) this.self.getAttribute("binder", true);
        binder.loadAll();
    }
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }
}
