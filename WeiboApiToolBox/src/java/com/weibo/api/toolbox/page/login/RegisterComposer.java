/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.page.login;

import java.util.Map;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tgroup;
import com.weibo.api.toolbox.persist.entity.Tuser;
import com.weibo.api.toolbox.persist.qlgenerator.JPQLGenerator;
import com.weibo.api.toolbox.persist.qlgenerator.QLGenerator;
import com.weibo.api.toolbox.util.MD5Util;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.spring.context.annotation.EventHandler;
import org.zkoss.spring.util.GenericSpringComposer;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import static com.weibo.api.toolbox.util.ToolBoxUtil.*;

/**
 *
 * @author x-spirit
 */
@Component("registerComposer")
@Scope("desktop")
public class RegisterComposer extends GenericSpringComposer {
    @Autowired
    Textbox emailtb;
    @Autowired
    Textbox nametb;
    @Autowired
    Textbox pwdtb;
    @Autowired
    Textbox pwdtb2;
    @Autowired
    Textbox realnametb;
    @Autowired
    Textbox mobiletb;
    @Autowired
    Textbox departtb;
    @Autowired
    Button btn_submit;
    @Autowired
    Button btn_cancel;

    @Resource
    IJpaDaoService jpaDaoService;


    Constraint const_email = new Constraint() {
        public void validate(org.zkoss.zk.ui.Component cmpnt, Object o) throws WrongValueException {
            System.out.println(o);
            if (isEmpty((String)o)||(!((String)o).matches(".+@.+\\.[a-z]+"))){
                throw new WrongValueException(cmpnt, "请输入正确的E-mail地址");
            }
            if (isDuplicatedEmail((String)o)){
                throw new WrongValueException(cmpnt, "该E-mail地址已经被注册！");
            }
        }
    };

    Constraint const_username = new Constraint() {

        public void validate(org.zkoss.zk.ui.Component cmpnt, Object o) throws WrongValueException {
            System.out.println(o);
            if (isEmpty((String)o)){
                throw new WrongValueException(cmpnt, "请输入用户名");
            }
            if (isDuplicatedUsername((String)o)){
                throw new WrongValueException(cmpnt, "该用户名已经被注册！");
            }
        }

    };

    @EventHandler("btn_submit.onClick")
    public void register(Event evt) throws InterruptedException {
        if (isEmpty(emailtb.getValue())||(!emailtb.getValue().matches(".+@.+\\.[a-z]+"))){
            emailtb.clearErrorMessage(true);
        }
        if (isEmpty(nametb.getValue())){
            nametb.clearErrorMessage(true);
        }
        if (isEmpty(pwdtb.getValue())){
            pwdtb.clearErrorMessage(true);
        }
        if (isEmpty(pwdtb2.getValue())||(!pwdtb.getValue().equals(pwdtb2.getValue()))){
            pwdtb2.clearErrorMessage(true);
        }
        if (isEmpty(realnametb.getValue())){
            realnametb.clearErrorMessage(true);
        }
        if (isEmpty(mobiletb.getValue())||(!mobiletb.getValue().matches("1[0-9]{10}"))){
            mobiletb.clearErrorMessage(true);
        }
        if (isEmpty(departtb.getValue())){
            departtb.clearErrorMessage(true);
        }
        try {
            Tgroup nogroup = jpaDaoService.findOneEntityById(Tgroup.class, 2);
            Tuser user = new Tuser(null, emailtb.getValue(), nametb.getValue(), realnametb.getValue(), MD5Util.md5Digest(pwdtb2.getValue()), 1, 0);
            user.setVc2department(departtb.getValue());
            user.setVc2phone(mobiletb.getValue());
            nogroup.getTuserSet().add(user);
            user.getTgroupSet().add(nogroup);
            jpaDaoService.edit(user);
        } catch (Exception e) {
            Messagebox.show("操作失败，请稍候再试！", "错误", Messagebox.OK, Messagebox.ERROR);
            return ;
        }
        Messagebox.show("注册成功", "成功", Messagebox.OK, Messagebox.INFORMATION,new EventListener() {

                public void onEvent(Event event) throws Exception {
                    if (event.getName().equals("onOK")||event.getName().equals("onClose")){
                        Executions.getCurrent().sendRedirect("/index.zul");
                    }
                }
            });
    }
    @EventHandler("btn_cancel.onClick")
    public void cancel(Event evt){
        Executions.getCurrent().sendRedirect("/index.zul");
    }
    private boolean isDuplicatedEmail(String email){
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("count(t)").from("Tuser t").where(null, "t.vc2email = :vc2email");
        Map param = new HashMap();
        param.put("vc2email", email);
        int count = jpaDaoService.getEntityCount(qlgen.toString(), param).intValue();
        return count > 0;
    }

    private boolean isDuplicatedUsername(String username){
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("count(t)").from("Tuser t").where(null, "t.vc2username = :vc2username");
        Map param = new HashMap();
        param.put("vc2username", username);
        int count = jpaDaoService.getEntityCount(qlgen.toString(), param).intValue();
        return count > 0;
    }

    public Constraint getConst_email() {
        return const_email;
    }

    public void setConst_email(Constraint const_email) {
        this.const_email = const_email;
    }

    public Constraint getConst_username() {
        return const_username;
    }

    public void setConst_username(Constraint const_username) {
        this.const_username = const_username;
    }

    
}
