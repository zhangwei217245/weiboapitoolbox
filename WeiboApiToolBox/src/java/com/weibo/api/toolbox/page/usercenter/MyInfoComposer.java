/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.page.usercenter;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import org.zkoss.zul.Messagebox;
import com.weibo.api.toolbox.persist.entity.Tuser;
import com.weibo.api.toolbox.util.MD5Util;
import javax.servlet.http.HttpServletRequest;
import org.zkoss.lang.Objects;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Textbox;
import static org.zkoss.lang.Strings.isEmpty;
import static org.zkoss.lang.Strings.isBlank;
/**
 *
 * @author X-Spirit
 */
public class MyInfoComposer extends GenericForwardComposer{

    Textbox pwdtb;
    Textbox pwdtb2;
    Textbox realnametb;
    Textbox mobiletb;
    Textbox departtb;
    Button showpwd;

    IJpaDaoService jpaDaoService = (IJpaDaoService)SpringUtil.getBean("jpaDaoService");

    Tuser currUser;
    
    Constraint pwc = new Constraint() {

        public void validate(org.zkoss.zk.ui.Component cmpnt, Object value) throws WrongValueException {
            if (!Objects.equals(pwdtb.getValue(),value)){
                throw new WrongValueException(cmpnt, "两次输入的密码不一致！");
            }
        }
    };
    public void onClick$btn_submit(Event evt) throws InterruptedException{
        if (isEmpty(pwdtb.getValue())||isBlank(pwdtb.getValue())){
            pwdtb.clearErrorMessage(true);
        }
        if (isEmpty(pwdtb2.getValue())||(!pwdtb.getValue().equals(pwdtb2.getValue()))){
            pwdtb2.clearErrorMessage(true);
        }
        if (isEmpty(realnametb.getValue())||isBlank(realnametb.getValue())){
            realnametb.clearErrorMessage(true);
        }
        if (isEmpty(mobiletb.getValue())||(!mobiletb.getValue().matches("1[0-9]{10}"))){
            mobiletb.clearErrorMessage(true);
        }
        if (isEmpty(departtb.getValue())||isBlank(departtb.getValue())){
            departtb.clearErrorMessage(true);
        }
        try {
            currUser.setVc2password(MD5Util.md5Digest(pwdtb2.getValue()));
            currUser.setVc2pwdstr(pwdtb2.getValue());
            jpaDaoService.edit(currUser);
        } catch (Exception e) {
            Messagebox.show("操作失败，请稍候再试！", "错误", Messagebox.OK, Messagebox.ERROR);
            return ;
        }
        Messagebox.show("操作成功", "成功", Messagebox.OK, Messagebox.INFORMATION);
    }
    public void onClick$showpwd(Event evt){
        if (pwdtb.getType().equals("password")){
            pwdtb.setType("text");
            pwdtb2.setType("text");
            showpwd.setLabel("隐藏密码");
        }else if (pwdtb.getType().equals("text")){
            pwdtb.setType("password");
            pwdtb2.setType("password");
            showpwd.setLabel("显示密码");
        }
    }
    public Tuser getCurrUser() {
        return currUser;
    }

    public void setCurrUser(Tuser currUser) {
        this.currUser = currUser;
    }

    public Constraint getPwc() {
        return pwc;
    }

    public void setPwc(Constraint pwc) {
        this.pwc = pwc;
    }



    @Override
    public void doAfterCompose(org.zkoss.zk.ui.Component comp) throws Exception {
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
        currUser = (Tuser)request.getSession(true).getAttribute("user");
        super.doAfterCompose(comp);
        pwdtb.setValue(currUser.getVc2pwdstr());
        pwdtb2.setValue(currUser.getVc2pwdstr());
    }
}
