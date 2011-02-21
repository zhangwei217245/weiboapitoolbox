/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.service.spec;

import com.weibo.api.toolbox.persist.entity.Baseurl;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tenumgroup;
import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.persist.entity.Tstructfield;
import java.util.List;

/**
 *
 * @author x-spirit
 */
public interface SpecProvider {

    List<Tspec> getSpecListByCategory(Tspeccategory cate,Integer specid,String version);
    List<Baseurl> getBaseurls();
    void saveTspec(Tspec spec);
    int getMaxcateIndex(Tspeccategory cate);
    void delReqParamById(String[] paramid);
    void delErrorDefById(String[] errorid);
    List<Tdatastruct> getAllDataStruct(String structname,String version, boolean all, int startPos, int maxCount);
    int getAllDataStructCount(String structname,String version);
    void saveDataStruct(Tdatastruct struct);
    void saveStructField(Tstructfield field);
    void delStructFieldById(String[] numfieldid);
    void delResponseById(String[] respids);
    void updateSpecIndex(Tspec spec, int oldindex, int newindex);
    List<Tenumgroup> getAllEnableEnumGroups();
    List<Tenumgroup> getAllEnumGroups();
    void saveEnumgroup(Tenumgroup enumgroup);
}
