/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.page.admin;

import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tgroup;
import com.weibo.api.toolbox.persist.entity.Tuser;
import com.weibo.api.toolbox.persist.qlgenerator.JPQLGenerator;
import com.weibo.api.toolbox.persist.qlgenerator.QLGenerator;
import com.weibo.api.toolbox.util.MD5Util;
import java.util.List;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author x-spirit
 */
public class UserManagerComposer extends GenericForwardComposer{

    private static final long serialVersionUID = 1416756736243614017L;
    IJpaDaoService jpaDaoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");

    Listbox grouplistbox;
    Listbox userlistbox;
    Textbox pwdtb;
    Textbox pwdtb2;

    Tgroup currTgroup;
    Tuser currTuser;

    public void onClick$userlistbox(){
        if (userlistbox.getSelectedCount()>0){
            Tuser seluser = (Tuser)userlistbox.getSelectedItem().getValue();
            pwdtb.setValue(seluser.getVc2pwdstr());
            pwdtb2.setValue(seluser.getVc2pwdstr());
        }
    }
    public void onClick$useradd(){
        currTuser = new Tuser(null, "who@some.com", "who", "who", MD5Util.md5Digest("123456"), "123456", 1, 0);
        currTuser.setVc2department("where");
        currTuser.setVc2phone("13999999999");
        currTuser.getTgroupSet().add(currTgroup);
        currTgroup.getTuserSet().add(currTuser);
        jpaDaoService.edit(currTuser);
        pwdtb.setValue(currTuser.getVc2pwdstr());
        pwdtb2.setValue(currTuser.getVc2pwdstr());
        refreshDataBinding();
    }
    public void onClick$btn_save(){
        if (!(currTuser.getNumuserid().equals(1))){
            currTuser.setVc2pwdstr(pwdtb.getValue());
            currTuser.setVc2password(MD5Util.md5Digest(pwdtb.getValue()));
            jpaDaoService.edit(currTuser);
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

    public Tuser getCurrTuser() {
        return currTuser;
    }

    public void setCurrTuser(Tuser currTuser) {
        this.currTuser = currTuser;
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
