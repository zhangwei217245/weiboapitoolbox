/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.page.spec;

import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tenumgroup;
import com.weibo.api.toolbox.persist.entity.Tenumvalues;
import com.weibo.api.toolbox.service.spec.SpecProvider;
import java.util.List;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;

/**
 *
 * @author x-spirit
 */
public class EnumManageComposer extends GenericForwardComposer {

    private static final long serialVersionUID = 2160699766806562295L;
    private static final Log log = Log.lookup(EnumManageComposer.class);
    SpecProvider sp = (SpecProvider) SpringUtil.getBean("specProvider");
    IJpaDaoService daoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");
    South attrlayout;
    Listbox enumgrouplist;
    Listbox enumvaluelist;
    Textbox name;
    Checkbox enable;
    Textbox intro;
    Object selectedObj;
    Button groupsave;
    Button valuesave;
    Label lb_name;

    public void onClick$groupsave() {
        setValueFromEditor();
    }
    public void onClick$valuesave() {
        setValueFromEditor();
    }
    public void onClick$enumgrouplist() {
        Listitem selectedItem = enumgrouplist.getSelectedItem();
        if (selectedItem != null) {
            selectedObj = (Tenumgroup) selectedItem.getValue();
            showSelObjInEditor();
        }
    }

    public void onClick$enumvaluelist() {
        Listitem selectedItem = enumvaluelist.getSelectedItem();
        if (selectedItem != null) {
            selectedObj = (Tenumvalues) selectedItem.getValue();
            showSelObjInEditor();
        }
    }

    public void onClick$enumgroupadd() {
        selectedObj = new Tenumgroup(null, "newenum", "新枚举", 1);
        showSelObjInEditor();
    }

    public void onClick$enumgroupdel() {
        Listitem selectedItem = enumgrouplist.getSelectedItem();
        if (selectedItem != null) {
            Tenumgroup delgroup = (Tenumgroup) selectedItem.getValue();
            try {
                daoService.destroy(delgroup.getClass(), delgroup.getNumenumgroupid());
            } catch (Exception e) {
                log.warning("Destroy Tenumgroup["+delgroup.getNumenumgroupid()+"] failed, try to disable it");
                delgroup.setIsEnable(false);
                sp.saveEnumgroup(delgroup);
            }
        }
    }

    public void onClick$enumvalueadd() throws InterruptedException {
        if (enumgrouplist.getSelectedItem() != null) {
            Tenumgroup group = (Tenumgroup) enumgrouplist.getSelectedItem().getValue();
            selectedObj = new Tenumvalues(null, "value", "枚举值含义", 1);
            ((Tenumvalues) selectedObj).setNumenumgroupid(group);
            showSelObjInEditor();
        }else{
            Messagebox.show("请选择一个枚举", "错误", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public void onClick$enumvaluedel() {
        Listitem selectedItem = enumvaluelist.getSelectedItem();
        if (selectedItem != null) {
            Tenumvalues delvalue = (Tenumvalues) selectedItem.getValue();
            try {
                daoService.destroy(delvalue.getClass(), delvalue.getNumenumvalueid());
            } catch (Exception e) {
                log.warning("Destroy Tenumvalues ["+delvalue.getNumenumvalueid()+"] failed, try to disable it");delvalue.setIsEnable(false);
                daoService.edit(delvalue);
            }
        }
    }

    private void showSelObjInEditor() {
        if (selectedObj.getClass().equals(Tenumgroup.class)) {
            Tenumgroup selgroup = (Tenumgroup) selectedObj;
            attrlayout.setTitle("编辑枚举信息");
            groupsave.setVisible(true);
            valuesave.setVisible(false);
            lb_name.setValue("枚举名");
            name.setValue(selgroup.getVc2enumgroupname());
            intro.setValue(selgroup.getVc2enumgroupdesc());
            enable.setChecked(selgroup.getIsEnable());
        } else if (selectedObj.getClass().equals(Tenumvalues.class)) {
            Tenumvalues selvalue = (Tenumvalues) selectedObj;
            attrlayout.setTitle("编辑枚举值信息");
            groupsave.setVisible(false);
            valuesave.setVisible(true);
            lb_name.setValue("枚举值");
            name.setValue(selvalue.getVc2enumvalue());
            intro.setValue(selvalue.getVc2enumdesc());
            enable.setChecked(selvalue.getIsEnable());
        }
    }

    private void setValueFromEditor() {
        if (selectedObj.getClass().equals(Tenumgroup.class)) {
            Tenumgroup selgroup = (Tenumgroup) selectedObj;
            selgroup.setVc2enumgroupname(name.getValue());
            selgroup.setVc2enumgroupdesc(intro.getValue());
            selgroup.setIsEnable(enable.isChecked());
            daoService.edit(selgroup);
        } else if (selectedObj.getClass().equals(Tenumvalues.class)) {
            Tenumvalues selvalue = (Tenumvalues) selectedObj;
            selvalue.setVc2enumvalue(name.getValue());
            selvalue.setVc2enumdesc(intro.getValue());
            selvalue.setIsEnable(enable.isChecked());
            daoService.edit(selvalue);
        }
    }

    public List<Tenumgroup> getAllEnumGroups() {
        return sp.getAllEnumGroups();
    }

    public List<Tenumvalues> getAllEnumvaluesByGroup() {
        Listitem selectedItem = enumgrouplist.getSelectedItem();
        if (selectedItem != null) {
            Tenumgroup group = (Tenumgroup) selectedItem.getValue();
            return sp.getAllEnumvaluesByGroup(group);
        } else {
            return null;
        }
    }
}
