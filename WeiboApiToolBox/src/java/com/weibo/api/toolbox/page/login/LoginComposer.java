/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.page.login;

import com.weibo.api.toolbox.page.mainframe.Category;
import com.weibo.api.toolbox.page.mainframe.DemoItem;
import java.util.LinkedHashMap;
import com.weibo.api.toolbox.persist.entity.Tcategory;
import com.weibo.api.toolbox.persist.entity.Tmenuitem;
import com.weibo.api.toolbox.persist.entity.Tuser;
import com.weibo.api.toolbox.service.rbac.RbacProvider;
import com.weibo.api.toolbox.util.MD5Util;
import java.util.Set;
import org.zkoss.spring.SpringUtil;
import static com.weibo.api.toolbox.util.ToolBoxUtil.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author x-spirit
 */
public class LoginComposer extends GenericForwardComposer {

    private static final long serialVersionUID = -2215198986703910764L;
    
    private static final Log log = Log.lookup(LoginComposer.class);
    private Textbox userName;
    private Textbox passWord;
    private Label msg;
    private RbacProvider rbacProvider = (RbacProvider) SpringUtil.getBean("rbacProvider");
    private static Map _cateMap = new LinkedHashMap();

    public void onOK$passWord(){
        onClick$btn_login();
    }

    public void onClick$btn_login() {
        String username = userName.getValue();
        String password = passWord.getValue();
        if (isNotEmpty(username) && isNotEmpty(password)) {
            
            Tuser user = rbacProvider.login(username, password);
            if (user != null) {
                loadMenus(user);
                execution.sendRedirect("/mainFrame.zul");
            } else {
                msg.setValue("* 用户名或密码错误！");
            }
        } else {
            msg.setValue("* 需要用户名和密码！");
        }
    }

    public void onClick$btn_register() {
        execution.sendRedirect("/register.zul");
    }

    private void loadMenus(Tuser user) {
        HttpServletRequest request = (HttpServletRequest) execution.getNativeRequest();
        request.getSession(true).setAttribute("user", user);
        Set<Tmenuitem> menuItems = rbacProvider.getUsersEnableMenuItems(user);
        Set<Tcategory> cates = rbacProvider.getAllEnableCategories(menuItems);
        for (Tcategory cate : cates) {
            Category category = new Category("cg" + cate.getNumindex(), "/img/Centigrade-Widget-Icons/EnterpriseAndIntegration-48x48.png", cate.getVc2catedesc(), cate.getVc2catehref());
            _cateMap.put("cg" + cate.getNumindex(), category);
        }
        for (Tmenuitem menu : menuItems) {
            String cateid = "cg" + menu.getNumcateid().getNumindex();
            Category cate = (Category) _cateMap.get(cateid);
            DemoItem item = new DemoItem(cateid + "m" + menu.getNumindex(), cateid,
                    menu.getVc2itemurl(), "/img/smallicons/weiboicon.png",
                    menu.getVc2itemdesc());
            cate.addItem(item);
            Collections.sort(cate.getItems(), new Comparator<DemoItem>() {

                public int compare(DemoItem i1, DemoItem i2) {
                    String midx1 = i1.getId().substring(i1.getId().indexOf('m') + 1, i1.getId().length());
                    String midx2 = i2.getId().substring(i2.getId().indexOf('m') + 1, i2.getId().length());
                    return Integer.parseInt(midx1) - Integer.parseInt(midx2);
                }
            });
            if (log.debugable()) {
                log.debug(cate.getItems() + "");
            }
        }
        request.getSession().setAttribute("cateMap", _cateMap);
    }

    public Textbox getPassWord() {
        return passWord;
    }

    public void setPassWord(Textbox passWord) {
        this.passWord = passWord;
    }

    public Textbox getUserName() {
        return userName;
    }

    public void setUserName(Textbox userName) {
        this.userName = userName;
    }

    public Label getMsg() {
        return msg;
    }

    public void setMsg(Label msg) {
        this.msg = msg;
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }
}
