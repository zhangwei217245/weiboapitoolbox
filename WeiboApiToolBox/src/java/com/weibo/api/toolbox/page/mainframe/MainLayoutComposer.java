package com.weibo.api.toolbox.page.mainframe;

import com.weibo.api.toolbox.persist.entity.Tuser;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

/**
 * @author jumperchen
 * 
 */
public class MainLayoutComposer extends GenericForwardComposer implements
        MainLayoutAPI {

    private static final Log log = Log.lookup(MainLayoutComposer.class);
    Textbox searchBox;
    Listbox itemList;
    Include xcontents;
    Div header;
    Button _selected;
    Label lb_username;

    public MainLayoutComposer() {
    }

    private Map getCategoryMap() {
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
        Map cateMap = (Map) request.getSession().getAttribute("cateMap");
        return cateMap;
    }

    public void onCategorySelect(ForwardEvent event) {
        Button btn = (Button) event.getOrigin().getTarget();
        Listitem item = null;
        if (_selected != btn) {
            _selected = btn;
        } else {
            item = itemList.getSelectedItem();
        }
        String href = getCategory(_selected.getId()).getHref();
        if (href != null && href.length() > 0 && (!href.equals("NONE"))) {
            Executions.getCurrent().sendRedirect(href);
        } else {
            ListModel selectedModel = getSelectedModel();
            int listsize = selectedModel.getSize();
            itemList.setModel(selectedModel);
            if (item != null) {
                itemList.renderAll();
                ((Listitem) itemList.getFellow(item.getId())).setSelected(true);
            } else if (listsize > 0){
                itemList.setSelectedIndex(0);
                onSelect$itemList();
            }
        }
    }

    public void onBookmarkChange$main(BookmarkEvent event) {
        String id = event.getBookmark();
        if (id.length() > 0) {
            final DemoItem[] items = getItems();
            for (int i = 0; i < items.length; i++) {
                if (items[i].getId().equals(id)) {
                    _selected = (Button) self.getFellow(items[i].getCateId());
                    itemList.setModel(getSelectedModel());
                    itemList.renderAll();
                    Listitem item = ((Listitem) itemList.getFellow(id));
                    item.setSelected(true);
                    itemList.invalidate();
                    setSelectedCategory(item,false);
                    xcontents.setSrc(((DemoItem) item.getValue()).getFile());
                    item.focus();
                    return;
                }
            }
        }
    }

    public void onSelect$itemList() {
        Listitem item = itemList.getSelectedItem();

        if (item != null) {

            // sometimes the item is unloaded.
            if (!item.isLoaded()) {
                itemList.renderItem(item);
            }

            setSelectedCategory(item,false);
            xcontents.setSrc(((DemoItem) item.getValue()).getFile());
        }
    }

    public void onClick$btn_logout(Event event) {
        final Execution exec = Executions.getCurrent();
        HttpServletRequest request = (HttpServletRequest) exec.getNativeRequest();
        request.getSession().invalidate();
        exec.sendRedirect("/index.zul");
    }

    public void onMainCreate(Event event) {
        checkSessionBroken(false);
        final Execution exec = Executions.getCurrent();
        final String id = exec.getParameter("id");
        Listitem item = null;
        if (id != null) {
            try {
                final LinkedList list = new LinkedList();
                final DemoItem[] items = getItems();
                for (int i = 0; i < items.length; i++) {
                    if (items[i].getId().equals(id)) {
                        list.add(items[i]);
                    }
                }
                if (!list.isEmpty()) {
                    itemList.setModel(new ListModelList(list));
                    itemList.renderAll();
                    item = (Listitem) self.getFellow(id);
                    setSelectedCategory(item,true);
                }
            } catch (ComponentNotFoundException ex) { // ignore
            }
        }

        if (item == null) {
            item = (Listitem) self.getFellow("cg1m1");
            setSelectedCategory(item,true);
        }
        xcontents.setSrc(((DemoItem) item.getValue()).getFile());

        itemList.selectItem(item);
    }

    private void setSelectedCategory(Listitem item,boolean refreshUserNameLabel) {
        checkSessionBroken(refreshUserNameLabel);
        DemoItem di = (DemoItem) item.getValue();
        _selected = (Button) self.getFellow(di.getCateId());
        String deselect = _selected != null ? "jq('#" + _selected.getUuid()
                + "').addClass('demo-seld').siblings().removeClass('demo-seld');" : "";
        Clients.evalJavaScript(deselect);
        item.getDesktop().setBookmark(item.getId());
    }

    public void onCtrlKey$searchBox(KeyEvent event) {
        int keyCode = event.getKeyCode();
        List items = itemList.getItems();
        if (items.isEmpty()) {
            return;
        }
        Listitem item = null;
        switch (keyCode) {
            case 38: // UP
                item = itemList.getItemAtIndex(items.size() - 1);
                itemList.setSelectedItem(item);
                break;
            case 40: // DOWN
                item = itemList.getItemAtIndex(0);
                itemList.setSelectedItem(item);
                break;
        }
        if (item != null) {
            if (!item.isLoaded()) {
                itemList.renderItem(item);
            }
            setSelectedCategory(item,false);
            xcontents.setSrc(((DemoItem) item.getValue()).getFile());
            item.focus();
        }
    }

    public void onChanging$searchBox(InputEvent event) {
        String key = event.getValue();
        LinkedList item = new LinkedList();
        DemoItem[] items = getItems();

        if (key.trim().length() != 0) {
            for (int i = 0; i < items.length; i++) {
                if (items[i].getLabel().toLowerCase().indexOf(key.toLowerCase()) != -1) {
                    item.add(items[i]);
                }
            }
            itemList.setModel(new ListModelList(item));
        } else {
            itemList.setModel(new ListModelList(items));
        }
        _selected = null;
    }

    private DemoItem[] getItems() {
        LinkedList items = new LinkedList();
        Category[] categories = getCategories();
        for (int i = 0; i < categories.length; i++) {
            items.addAll(categories[i].getItems());
        }
        return (DemoItem[]) items.toArray(new DemoItem[]{});
    }

    public Category[] getCategories() {
        Map cateMap = getCategoryMap();
        if (cateMap!=null&&cateMap.size()>0){
            Collection cates = cateMap.values();
            Category[] catearr = (Category[]) cates.toArray(new Category[]{});
            Arrays.sort(catearr, new Comparator<Category>(){

                public int compare(Category c1, Category c2) {
                    String idx1 = new StringBuilder(c1.getId()).delete(0, 2).toString();
                    String idx2 = new StringBuilder(c2.getId()).delete(0, 2).toString();
                    return Integer.parseInt(idx1)-Integer.parseInt(idx2);
                }

            });
            return catearr;
        }else{
            return new Category[]{new Category("cg1", "/img/Centigrade-Widget-Icons/EnterpriseAndIntegration-48x48.png", "", "/index.zul")};
        }
    }

    public ListitemRenderer getItemRenderer() {
        return _defRend;
    }
    private static final ListitemRenderer _defRend = new ItemRender();

    private static class ItemRender implements ListitemRenderer, java.io.Serializable {

        public void render(Listitem item, Object data) {
            DemoItem di = (DemoItem) data;
            Listcell lc = new Listcell();
            item.setValue(di);
            lc.setHeight("30px");
            lc.setImage(di.getIcon());
            item.setId(di.getId());
            lc.setLabel(di.getLabel());
            lc.setParent(item);
        }
    };

    private Category getCategory(String cateId) {
        return (Category) getCategoryMap().get(cateId);
    }

    public ListModel getSelectedModel() {
        Category cate = _selected == null ? getCategories()[0]
                : getCategory(_selected.getId());
        return new ListModelList(cate.getItems());
    }

    public void checkSessionBroken(boolean refreshUserNameLabel){
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
        HttpSession sess = request.getSession();
        if (sess != null) {
            Tuser user = (Tuser) request.getSession().getAttribute("user");
            if (user != null) {
                if(refreshUserNameLabel){
                    lb_username.setValue(user.getVc2realname());
                }
            } else {
                Executions.getCurrent().sendRedirect("/timeout.zul");
            }
        }else{
            Executions.getCurrent().sendRedirect("/timeout.zul");
        }
    }
    // Composer Implementation
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        checkSessionBroken(true);
        Events.postEvent("onMainCreate", comp, null);
    }

}
