package com.weibo.api.toolbox.page.spec;

import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tstructfield;
import com.weibo.api.toolbox.service.spec.CategoryProvider;
import com.weibo.api.toolbox.service.spec.SpecProvider;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;

/**
 *
 * @author X-Spirit
 */
public class DataStructComposer extends GenericForwardComposer {

    private static final Log log = Log.lookup(SpecManagerComposer.class);
    private static final long serialVersionUID = -1610058058620582667L;
    CategoryProvider cp = (CategoryProvider) SpringUtil.getBean("categoryProvider");
    SpecProvider sp = (SpecProvider) SpringUtil.getBean("specProvider");
    Textbox namefilter;
    Textbox versionfilter;
    Listbox structlist;
    Listbox fieldlist;
    Grid structInfoEdit;
    Paging structPaging;
    List<Tdatastruct> dataStruct;
    Tdatastruct currentDataStruct;
    Tdatastruct nostruct = ToolBoxUtil.getNonStruct();

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        dataStruct = getDataStruct(namefilter.getValue(),versionfilter.getValue());
    }

    public void onClick$link_refresh() {
        structPaging.setActivePage(structPaging.getActivePage());
        onPaging$structPaging();
    }

    public void onPaging$structPaging() {
        dataStruct = getDataStruct(namefilter.getValue(),versionfilter.getValue());
        reloadDataBind(-1);
    }

    public void onOK$namefilter() {
        dataStruct = getDataStruct(namefilter.getValue(),versionfilter.getValue());
        reloadDataBind(-1);
    }

    public void onChange$namefilter(Event event) {
        onOK$namefilter();
    }

    public void onOK$versionfilter() {
        onOK$namefilter();
    }

    public void onChange$versionfilter(Event event) {
        onOK$namefilter();
    }

    public void onSelect$structlist() {
        Listitem selectedItem = structlist.getSelectedItem();
        currentDataStruct = (Tdatastruct) selectedItem.getValue();
        reloadDataBind(structlist.getSelectedIndex());
    }

    public void onClick$btn_addstruct() {
        currentDataStruct = new Tdatastruct(null, "新数据结构", "描述", 1);
        sp.saveDataStruct(currentDataStruct);
        dataStruct = getDataStruct(namefilter.getValue(),versionfilter.getValue());
        structPaging.setActivePage(structPaging.getPageCount()-1);
        dataStruct = getDataStruct(namefilter.getValue(),versionfilter.getValue());
        reloadDataBind(-1);
    }

    public void onClick$btn_delstruct() {
        currentDataStruct.setNumenable(0);
        sp.saveDataStruct(currentDataStruct);
        dataStruct = getDataStruct(namefilter.getValue(),versionfilter.getValue());
        reloadDataBind(-1);
    }

    public void onClick$btn_addfield() {
        if (currentDataStruct != null && currentDataStruct.getNumdatastructid() != null
                && currentDataStruct.getNumdatastructid() > 1) {
            Tstructfield tstructfield = new Tstructfield(null, "新字段", 1, "描述");
            tstructfield.setNumdatastructid(nostruct);
            tstructfield.setNumparentdatastructid(currentDataStruct);
            sp.saveStructField(tstructfield);
            if (currentDataStruct.getTstructfieldSet() == null) {
                currentDataStruct.setTstructfieldSet(new ArrayList<Tstructfield>());
            }
            currentDataStruct.getTstructfieldSet().add(tstructfield);
            sp.saveDataStruct(currentDataStruct);
            reloadDataBind(structlist.getSelectedIndex());
        }
    }

    public void onClick$btn_delfield() {
        Set selectedItems = fieldlist.getSelectedItems();
        if (selectedItems != null && selectedItems.size() > 0) {
            List<String> fieldids = new ArrayList<String>();
            for (Object o : selectedItems) {
                Listitem item = (Listitem) o;
                Tstructfield df = (Tstructfield) item.getValue();
                fieldids.add(df.getNumfieldid().toString());
                currentDataStruct.getTstructfieldSet().remove(df);
            }
            sp.delStructFieldById(fieldids.toArray(new String[fieldids.size()]));
            sp.saveDataStruct(currentDataStruct);
            reloadDataBind(structlist.getSelectedIndex());
        }
    }

    public void onClick$btn_savestruct() {
        sp.saveDataStruct(currentDataStruct);
        reloadDataBind(structlist.getSelectedIndex());
        onClick$link_refresh();
    }

    public List<Tdatastruct> getAllAvailDataStructs() {
        return sp.getAllDataStruct(null,null, true, -1, -1);
    }

    public List<Tdatastruct> getDataStruct(final String structname,final String version) {
        int totalcount = sp.getAllDataStructCount(structname,version);
        structPaging.setTotalSize(totalcount);
        int pgno = structPaging.getActivePage();
        int pageSize = structPaging.getPageSize();
        //System.out.println("total:" + totalcount + "pgno:" + pgno + "pageSize:" + pageSize);
        int start = pageSize * pgno;
        List<Tdatastruct> structList = sp.getAllDataStruct(structname,version, false, start, pageSize);

        return structList;
    }

    public List<Tdatastruct> getDataStruct() {
        return dataStruct;
    }

    public List<DataTypes> getAllDataTypes() {
        List<DataTypes> dtlst = Arrays.asList(DataTypes.values());
        Collections.sort(dtlst, new Comparator<DataTypes>() {

            public int compare(DataTypes o1, DataTypes o2) {
                return o1.getId() - o2.getId();
            }
        });
        return dtlst;
    }

    public Tdatastruct getCurrentDataStruct() {
        return currentDataStruct;
    }

    private void reloadDataBind(int selindex) {
        AnnotateDataBinder binder = (AnnotateDataBinder) this.self.getAttribute("binder", true);
        binder.loadAll();
        if (selindex >= 0) {
            structlist.setSelectedIndex(selindex);
        }
    }
}
