/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.page.spec;

import com.weibo.api.toolbox.common.ConfirmBoxEventListener;
import com.weibo.api.toolbox.common.EventListenerAction;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.service.spec.CategoryProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.South;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;

/**
 *
 * @author x-spirit
 */
public class CategoryComposer extends GenericForwardComposer {

    private static final Log log = Log.lookup(CategoryComposer.class);
    private static final long serialVersionUID = 882909509423678953L;
    CategoryProvider cp = (CategoryProvider) SpringUtil.getBean("categoryProvider");
    IJpaDaoService jpaDaoService = (IJpaDaoService) SpringUtil.getBean("jpaDaoService");
    South attrlayout;
    Listbox classlist;
    Listbox catelist;
    Textbox name;
    Spinner order;
    Checkbox enable;
    Textbox intro;
    Object selectedObj;
    List<Tspeccategory> packages;
    Button attrsave;

    public void onDrop$classlist(DropEvent event){
        System.out.println(event.getTarget());
        System.out.println(event.getDragged());

    }

    private void setEditorAttributes(String _name, Integer _order, boolean _enable, String _intro) {
        name.setValue(_name);
        order.setValue(_order);
        enable.setChecked(_enable);
        intro.setValue(_intro);
        name.select();
        attrsave.setVisible(true);
    }

    public void onClick$classlist() {
        Tspeccategory pkg = null;
        Listitem selitem = classlist.getSelectedItem();
        if (selitem != null) {
            pkg = (Tspeccategory)selitem.getValue();
            refreshCateList();
            attrlayout.setTitle("大分类属性");
            setEditorAttributes(pkg.getVc2catename(), pkg.getNumindex(), pkg.getNumenable() <= 0 ? false : true, pkg.getVc2desc());
            catelist.renderAll();
        }
    }

    public void onClick$catelist() {
        Listitem selitem = catelist.getSelectedItem();
        if (selitem != null) {
            Tspeccategory speccate = (Tspeccategory) selitem.getValue();
            selectedObj = speccate;
            attrlayout.setTitle("小分类属性");
            setEditorAttributes(speccate.getVc2catename(), speccate.getNumindex(), speccate.getNumenable() <= 0 ? false : true, speccate.getVc2desc());
        }
    }

    public void onClick$pkgadd() {
        selectedObj = new Tspeccategory();
        attrlayout.setTitle("大分类属性");
        ((Tspeccategory) selectedObj).setNumparentcateid(jpaDaoService.findOneEntityById(Tspeccategory.class, 1));
        ((Tspeccategory) selectedObj).setNumindex(classlist.getItemCount() + 1);
        setEditorAttributes("这里输入名称", classlist.getItemCount() + 1, true, "这里输入描述");
    }

    public void onClick$pkgdel() {
        try {
            if (classlist.getSelectedItem() != null) {
                ConfirmBoxEventListener listener = new ConfirmBoxEventListener();
                listener.setOk_operation(new EventListenerAction() {

                    public void execute() {
                        deleteSelectedPkg();
                    }
                });
                Messagebox.show("即将删除大分类，确定吗？", "确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, listener);
            } else {
                Messagebox.show("请选择一个大分类", "错误", Messagebox.OK, Messagebox.ERROR);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CategoryComposer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onClick$cateadd() {
        selectedObj = new Tspeccategory();
        attrlayout.setTitle("小分类属性");
        ((Tspeccategory) selectedObj).setNumindex(catelist.getItemCount() + 1);

        ((Tspeccategory) selectedObj)
                .setNumparentcateid((Tspeccategory) classlist.getSelectedItem().getValue());

        setEditorAttributes("这里输入名称", catelist.getItemCount() + 1, true, "这里输入描述");
    }

    public void onClick$catedel() {
        try {
            if (catelist.getSelectedItem() != null) {
                ConfirmBoxEventListener listener = new ConfirmBoxEventListener();
                listener.setOk_operation(new EventListenerAction() {

                    public void execute() {
                        deleteSelectedSpecCate();
                    }
                });
                Messagebox.show("即将删除小分类，确定吗？", "确认", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, listener);
            } else {
                Messagebox.show("请选择一个小分类", "错误", Messagebox.OK, Messagebox.ERROR);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CategoryComposer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onClick$attrsave() {
        try {
            if (selectedObj != null) {
                if (selectedObj.getClass().getSimpleName().equalsIgnoreCase("Tspeccategory")) {
                    setValueForSpecCate((Tspeccategory) selectedObj);
                    jpaDaoService.edit(selectedObj);
                    if (attrlayout.getTitle().contains("小分类")){
                        refreshCateList();
                    } else if (attrlayout.getTitle().contains("大分类")){
                        refreshClassList();
                        refreshCateList();
                    }
                }
                Messagebox.show("操作成功", "信息", Messagebox.OK, Messagebox.INFORMATION);
                attrsave.setVisible(false);
            } else {
                Messagebox.show("请选择一个大分类或者一个小分类", "错误", Messagebox.OK, Messagebox.ERROR);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CategoryComposer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setValueForSpecCate(Tspeccategory cate) {
        cate.setVc2catename(name.getValue());
        cate.setNumenable(enable.isChecked() ? 1 : 0);
        cate.setVc2desc(intro.getValue());
        processCateIndex(cate,cate.getNumindex(),order.getValue());
    }

    private void processCateIndex(Tspeccategory cate,int oldindex,int newindex){
        cate.setNumindex(newindex);
        updateCateIndex(cate, oldindex, newindex);
    }

    private void deleteSelectedSpecCate() {
        Tspeccategory value = (Tspeccategory) catelist.getSelectedItem().getValue();
        if (value != null) {
            try {
                value.setNumenable(0);
                jpaDaoService.edit(value);
                refreshCateList();
                Messagebox.show("操作成功", "信息", Messagebox.OK, Messagebox.INFORMATION);
            } catch (InterruptedException ex) {
                Logger.getLogger(CategoryComposer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void deleteSelectedPkg() {
        Tspeccategory value = (Tspeccategory) classlist.getSelectedItem().getValue();
        if (value != null) {
            try {
                value.setNumenable(0);
                jpaDaoService.edit(value);
                refreshClassList();
                Messagebox.show("操作成功", "信息", Messagebox.OK, Messagebox.INFORMATION);
            } catch (InterruptedException ex) {
                Logger.getLogger(CategoryComposer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void refreshCateList() {
        if (classlist.getSelectedCount() > 0) {
            Tspeccategory pkg = (Tspeccategory) classlist.getSelectedItem().getValue();
            selectedObj = pkg;
            int itemsize = catelist.getItemCount();
            for (int i = itemsize - 1; i >= 0; i--) {
                catelist.removeItemAt(i);
            }
            List<Tspeccategory> speccatelist = cp.getCateChildren(pkg);
            for (Tspeccategory cate : speccatelist) {
                Listitem newitem = new Listitem();
                newitem.setValue(cate);
                newitem.setDraggable("true");
                newitem.setDroppable("true");
                String tag = cate.getNumenable() > 0 ? "right_small.png" : "wrong_small.png";
                Listcell lc = new Listcell(cate.getVc2catename());
                lc.setImage("/img/smallicons/" + tag);
                Listcell idxlc = new Listcell(cate.getNumindex() + "");
                newitem.setTooltiptext(cate.getVc2desc());
                newitem.appendChild(idxlc);
                newitem.appendChild(lc);
                newitem.addEventListener("onDrop", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        DropEvent evt = (DropEvent)event;
                        Listitem dragged = (Listitem)evt.getDragged();
                        Listitem dropped = (Listitem)evt.getTarget();
                        processDragOnDrop(dragged, dropped);
                        refreshCateList();
                    }
                });
                catelist.appendChild(newitem);
            }
            catelist.renderAll();
        }
    }

    private void processDragOnDrop(Listitem dragged,Listitem dropped) throws InterruptedException{
        Tspeccategory draggedcate = (Tspeccategory)dragged.getValue();
        Tspeccategory droppedcate = (Tspeccategory)dropped.getValue();
        if (draggedcate.getNumparentcateid().equals(droppedcate.getNumparentcateid())){
            processCateIndex(draggedcate,draggedcate.getNumindex(),droppedcate.getNumindex());
        }else if(droppedcate.getNumparentcateid().getNumcateid().equals(1)){
            draggedcate.setNumparentcateid(droppedcate);
        }else{
            Messagebox.show("不支持该操作！", "错误", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        jpaDaoService.edit(draggedcate);
    }

    private void refreshClassList() {
        packages = cp.getCateLevelOne();
        int claitemsize = classlist.getItemCount();
        for (int i = claitemsize - 1; i >= 0; i--) {
            classlist.removeItemAt(i);
        }
        for (Tspeccategory pkg : packages) {
            Listitem newitem = new Listitem();
            newitem.setValue(pkg);
            newitem.setDraggable("true");
            newitem.setDroppable("true");
            String tag = pkg.getNumenable() > 0 ? "right_small.png" : "wrong_small.png";
            Listcell lc = new Listcell(pkg.getVc2catename());
            lc.setImage("/img/smallicons/" + tag);
            Listcell idxlc = new Listcell(pkg.getNumindex() + "");
            newitem.setTooltiptext(pkg.getVc2desc());
            newitem.appendChild(idxlc);
            newitem.appendChild(lc);
            newitem.addEventListener("onDrop", new EventListener() {
                    public void onEvent(Event event) throws Exception {
                        DropEvent evt = (DropEvent)event;
                        Listitem dragged = (Listitem)evt.getDragged();
                        Listitem dropped = (Listitem)evt.getTarget();
                        processDragOnDrop(dragged, dropped);
                        refreshClassList();
                    }
                });
            classlist.appendChild(newitem);
        }
        int itemsize = catelist.getItemCount();
        for (int i = itemsize - 1; i >= 0; i--) {
            catelist.removeItemAt(i);
        }
        classlist.renderAll();
        catelist.renderAll();
    }

    private void updateCateIndex(Tspeccategory cate, int oldindex, int newindex) {
        String ql = null;
        if (oldindex > newindex) {
            ql = "UPDATE " + cate.getClass().getName() + " t SET t.numindex = t.numindex+1 "
                    + "WHERE t.numindex >= :newindex "
                    + "AND t.numindex <= :oldindex "
                    + "AND t.numparentcateid = :parentCate "
                    + "AND t.numenable > 0";
        } else if (oldindex < newindex) {
            ql = "UPDATE " + cate.getClass().getName() + " t SET t.numindex = t.numindex-1 "
                    + "WHERE t.numindex <= :newindex "
                    + "AND t.numindex >= :oldindex "
                    + "AND t.numparentcateid = :parentCate "
                    + "AND t.numenable > 0";
        }

        Map params = new HashMap();
        params.put("newindex", newindex);
        params.put("oldindex", oldindex);
        params.put("parentCate", cate.getNumparentcateid());
        if (ql != null) {
            jpaDaoService.executeUpdate(ql, params);
        }
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        refreshClassList();
    }
}
