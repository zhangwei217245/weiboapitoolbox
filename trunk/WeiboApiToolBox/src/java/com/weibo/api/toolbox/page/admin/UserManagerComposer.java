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
import org.zkoss.lang.Strings;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
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

    Textbox emailtb;
    Textbox nametb;
    Textbox realnametb;
    Textbox mobiletb;
    Textbox departtb;
    Textbox pwdtb;
    Textbox pwdtb2;

    Tgroup currTgroup;
    Tuser currTuser;

    public void onClick$userlistbox(){
        if (userlistbox.getSelectedCount()>0){
            currTuser = (Tuser)userlistbox.getSelectedItem().getValue();
            pwdtb.setValue(currTuser.getVc2pwdstr());
            pwdtb2.setValue(currTuser.getVc2pwdstr());
            refreshDataBinding(userlistbox);
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
        refreshDataBinding(this.self);
    }
    public void onClick$btn_save() throws InterruptedException{
        if (!(currTuser==null||currTuser.getNumuserid().equals(1))){
            if (Strings.isEmpty(emailtb.getValue())||(!emailtb.getValue().matches(".+@.+\\.[a-z]+"))){
                emailtb.clearErrorMessage(true);
            }
            if (Strings.isEmpty(nametb.getValue())||Strings.isBlank(nametb.getValue())){
                nametb.clearErrorMessage(true);
            }
            if (Strings.isEmpty(pwdtb.getValue())||Strings.isBlank(pwdtb.getValue())){
                pwdtb.clearErrorMessage(true);
            }
            if (Strings.isEmpty(pwdtb2.getValue())||(!pwdtb.getValue().equals(pwdtb2.getValue()))){
                pwdtb2.clearErrorMessage(true);
            }
            if (Strings.isEmpty(realnametb.getValue())||Strings.isBlank(realnametb.getValue())){
                realnametb.clearErrorMessage(true);
            }
            if (Strings.isEmpty(mobiletb.getValue())||(!mobiletb.getValue().matches("1[0-9]{10}"))){
                mobiletb.clearErrorMessage(true);
            }
            if (Strings.isEmpty(departtb.getValue())||Strings.isBlank(departtb.getValue())){
                departtb.clearErrorMessage(true);
            }
            currTuser.setVc2pwdstr(pwdtb.getValue());
            currTuser.setVc2password(MD5Util.md5Digest(pwdtb.getValue()));
            jpaDaoService.edit(currTuser);
        } else {
            Messagebox.show("当前选择的用户信息不可更改，请重新选择用户", "错误", Messagebox.OK, Messagebox.ERROR);
        }
        refreshDataBinding(this.self);
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

    public void refreshDataBinding(Component compt){
        AnnotateDataBinder binder = (AnnotateDataBinder) compt.getAttribute("binder", true);
        binder.loadAll();
    }
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }
}
