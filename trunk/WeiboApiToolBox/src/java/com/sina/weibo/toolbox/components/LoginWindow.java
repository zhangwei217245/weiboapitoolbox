/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.components;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author x-spirit
 */
@Named
@SessionScoped
public class LoginWindow extends GenericForwardComposer implements Serializable{
    private Textbox username;
    private Textbox password;
    private Button btn_login;
    
    public void onClick$btn_login() throws InterruptedException{
        System.out.println("slfslk");
        if("asdf".equals(username.getText())&&"1234".equals(password.getText())){
            org.zkoss.zk.ui.Executions.sendRedirect("/mainFrm.zul");
        }else{
            Messagebox.show("You cannot Login!!");
        }
        
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        username = (Textbox)comp.getFellow("username");
        password = (Textbox)comp.getFellow("password");
        btn_login = (Button)comp.getFellow("btn_login");
    }


    
}
