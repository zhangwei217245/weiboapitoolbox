/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.service.spec;

import com.weibo.api.toolbox.common.enumerations.ContentType;
import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Baseurl;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tenumgroup;
import com.weibo.api.toolbox.persist.entity.Tenumvalues;
import com.weibo.api.toolbox.persist.entity.Trequestparam;
import com.weibo.api.toolbox.persist.entity.Tresponse;
import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.persist.entity.Tstructfield;
import com.weibo.api.toolbox.persist.qlgenerator.JPQLGenerator;
import com.weibo.api.toolbox.persist.qlgenerator.QLGenerator;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author x-spirit
 */
@Service("specProvider")
public class SpecProviderImpl implements SpecProvider {

    @Resource
    IJpaDaoService jpaDaoService;

    public List<Tenumvalues> getAllEnumvaluesByGroup(Tenumgroup group){
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("v").from("Tenumvalues v");
        Map param = new HashMap();
        if (group!=null){
            qlgen.where(null, "v.numenumgroupid = :numenumgroupid");
            param.put("numenumgroupid", group);
            return jpaDaoService.findEntities(qlgen.toString(), param, true, -1, -1);
        }
        return null;
    }

    
    
    private String getEnumRange(Tenumgroup group){
        QLGenerator qlgen = new JPQLGenerator();
        Map param = new HashMap();
        qlgen.select("t").from("Tenumvalues t").where(null, "t.numenable = 1");
        qlgen.where(null, "t.numenumgroupid = :numenumgroupid");
        param.put("numenumgroupid", group);
        List<Tenumvalues> enumvaluelist = jpaDaoService.findEntities(qlgen.toString(), param, true, -1, -1);
        StringBuilder sb = new StringBuilder();
        for (int i =0;i<enumvaluelist.size();i++){
            if (i>0){
                sb.append(",");
            }
            sb.append(enumvaluelist.get(i).getVc2enumvalue());
        }
        return sb.toString();
    }

    public void saveEnumValues(Tenumvalues value){
        if (value.getNumenumvalueid()==null){
            jpaDaoService.create(value);
        }else{
            jpaDaoService.edit(value);
        }
    }
    public List<Tenumgroup> getAllEnableEnumGroups(){
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("t").from("Tenumgroup t");
        qlgen.where(null, "t.numenable = 1");
        return jpaDaoService.findEntities(qlgen.toString(), null, true, -1, -1);
    }
    
    public List<Tenumgroup> getAllEnumGroups(){
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("t").from("Tenumgroup t");
        return jpaDaoService.findEntities(qlgen.toString(), null, true, -1, -1);
    }

    public void saveEnumgroup(Tenumgroup enumgroup){
        if (enumgroup.getNumenumgroupid()==null){
            jpaDaoService.create(enumgroup);
        } else {
            jpaDaoService.edit(enumgroup);
        }
    }

    public List<Tspec> getSpecListByCategory(Tspeccategory cate, Integer specid, String version) {
        QLGenerator qlgen = new JPQLGenerator();
        List rstlst = null;
        qlgen.select("s").from("Tspec s");
        Map<String, Object> param = new HashMap<String, Object>();
        if (cate != null) {
            param.put("numcateid", cate);
            qlgen.where(null, "s.numcateid = :numcateid");

            if (specid != null) {
                param.put("numspecid", specid);
                qlgen.where(null, "s.numspecid = :numspecid");
            }
            if (ToolBoxUtil.isNotEmpty(version)) {
                param.put("vc2version", '%' + version + '%');
                qlgen.where(null, "s.vc2version LIKE :vc2version");
            }
            qlgen.orderBy("s.numcateindex", "ASC");
            rstlst = jpaDaoService.findEntities(qlgen.toString(), param, true, -1, -1);
        }
        return rstlst;

    }

    public List<Baseurl> getBaseurls() {
        return jpaDaoService.findEntities("SELECT t FROM Baseurl t", null,true,-1,-1);
    }

    public int getMaxcateIndex(Tspeccategory cate) {
        String ql1 = "SELECT count(t.numcateindex) FROM Tspec t WHERE t.numcateid = :numcateid";
        Map param = new HashMap();
        param.put("numcateid", cate);
        int count = jpaDaoService.getEntityCount(ql1, param).intValue();
        return count;
    }

    public void saveTspec(Tspec spec) {
        fixMalParams(spec.getTrequestparamSet());
        fixMalResponse(spec.getTresponseSet());
        if (spec.getNumspecid() == null) {
            jpaDaoService.create(spec);
        } else {
            jpaDaoService.edit(spec);
        }
    }

    private void processParamEnumRange(Trequestparam param){
        if (param.getEnumDataTypes().equals(DataTypes.ENUM)){
            param.setVc2range(getEnumRange(param.getNumenumgroupid()));
        }
    }

    public void delReqParamById(String[] paramid) {
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.delete().from("Trequestparam p").where(null, qlgen.getIn_clause("p.numparamid", paramid));
        jpaDaoService.executeUpdate(qlgen.toString(), null);
    }

    public void delErrorDefById(String[] errorid) {
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.delete().from("Terrorcode e").where(null, qlgen.getIn_clause("e.numerrorid", errorid));
        jpaDaoService.executeUpdate(qlgen.toString(), null);
    }

    public List<Tdatastruct> getAllDataStruct(String structname, String version, boolean all, int startPos, int maxCount) {
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("t").from("Tdatastruct t");
        qlgen.where(null, "t.numenable = 1");
        Map param = new HashMap();
        if (ToolBoxUtil.isNotEmpty(structname)) {
            qlgen.where(null, "t.vc2structname LIKE :vc2structname");
            param.put("vc2structname", '%' + structname + '%');
        }
        if (ToolBoxUtil.isNotEmpty(version)) {
            qlgen.where(null, "t.vc2version LIKE :vc2version");
            param.put("vc2version", '%' + version + '%');
        }
        return jpaDaoService.findEntities(qlgen.toString(), param, all, startPos, maxCount);
    }

    public int getAllDataStructCount(String structname, String version) {
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("count(t)").from("Tdatastruct t");
        qlgen.where(null, "t.numenable = 1");
        Map param = new HashMap();
        if (ToolBoxUtil.isNotEmpty(structname)) {
            qlgen.where(null, "t.vc2structname LIKE :vc2structname");
            param.put("vc2structname", '%' + structname + '%');
        }
        if (ToolBoxUtil.isNotEmpty(version)) {
            qlgen.where(null, "t.vc2version LIKE :vc2version");
            param.put("vc2version", '%' + version + '%');
        }
        return jpaDaoService.getEntityCount(qlgen.toString(), param).intValue();
    }

    public void saveDataStruct(Tdatastruct struct) {
        fixMalFields(struct.getTstructfieldSet());
        if (struct.getNumdatastructid() == null) {
            jpaDaoService.create(struct);
        } else {
            jpaDaoService.edit(struct);
        }
    }

    public void saveStructField(Tstructfield field) {
        if (field.getNumfieldid() == null) {
            jpaDaoService.create(field);
        } else {
            jpaDaoService.edit(field);
        }
    }

    public void delStructFieldById(String[] numfieldid) {
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.delete().from("Tstructfield f").where(null, qlgen.getIn_clause("f.numfieldid", numfieldid));
        jpaDaoService.executeUpdate(qlgen.toString(), null);
    }

    public void delResponseById(String[] respids) {
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.delete().from("Tresponse r").where(null, qlgen.getIn_clause("r.numresponseid", respids));
        jpaDaoService.executeUpdate(qlgen.toString(), null);
    }

    public void updateSpecIndex(Tspec spec, int oldindex, int newindex) {
        String ql = null;
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.update(spec.getClass().getName() + " t").where(null, "t.numcateid = :numcateid").where(null, "t.numenable = 1");
        if (oldindex > newindex) {
            qlgen.set("t.numcateindex = t.numcateindex+1");
            qlgen.where(null, "t.numcateindex >= :newindex");
            qlgen.where(null, "t.numcateindex <= :oldindex");
            ql = qlgen.toString();
        } else if (oldindex < newindex) {
            qlgen.set("t.numcateindex = t.numcateindex-1");
            qlgen.where(null, "t.numcateindex <= :newindex");
            qlgen.where(null, "t.numcateindex >= :oldindex");
            ql = qlgen.toString();
        }
        Map params = new HashMap();
        params.put("newindex", newindex);
        params.put("oldindex", oldindex);
        params.put("numcateid", spec.getNumcateid());
        if (ql != null) {
            jpaDaoService.executeUpdate(ql, params);
        }
    }

    private void fixMalFields(List<Tstructfield> tstructfieldSet) {
        if (ToolBoxUtil.isNotEmpty(tstructfieldSet)){
            for (Tstructfield field : tstructfieldSet){
                fixMalType(field);
            }
        }
    }

    private void fixMalType(Tstructfield field){
        if (!field.getEnumDataTypes().isStruct()){
            field.setNumdatastructid(null);
        } else if (!hasDataStruct(field.getNumdatastructid())){
            field.setEnumDataTypes(DataTypes.STRING);
            field.setNumdatastructid(null);
        }

        if (!field.getEnumDataTypes().equals(DataTypes.ENUM)){
            field.setNumenumgroupid(null);
        } else if (!hasEnumGroup(field.getNumenumgroupid())){
            field.setEnumDataTypes(DataTypes.STRING);
            field.setNumenumgroupid(null);
        }
    }

    private boolean hasDataStruct(Tdatastruct struct){
        return struct!=null&&struct.getNumdatastructid()!=1;
    }
    private boolean hasEnumGroup(Tenumgroup enumgroup){
        return enumgroup!=null&&enumgroup.getNumenumgroupid()!=1;
    }

    private void fixMalParams(List<Trequestparam> trequestparamSet) {
        if (ToolBoxUtil.isNotEmpty(trequestparamSet)){
            for (Trequestparam param : trequestparamSet){
                fixMalType(param);
            }
        }
    }

    private void fixMalResponse(List<Tresponse> tresponseSet) {
        if (ToolBoxUtil.isNotEmpty(tresponseSet)){
            for (Tresponse response : tresponseSet){
                fixMalType(response);
            }
        }
    }

    private void fixMalType(Trequestparam param) {
        if (param.getEnumDataTypes().equals(DataTypes.ENUM)&&hasEnumGroup(param.getNumenumgroupid())){
            param.setVc2range(getEnumRange(param.getNumenumgroupid()));
        }else if (!param.getEnumDataTypes().equals(DataTypes.ENUM)){
            param.setNumenumgroupid(null);
        } else if (!hasEnumGroup(param.getNumenumgroupid())){
            param.setEnumDataTypes(DataTypes.STRING);
            param.setNumenumgroupid(null);
        }
    }

    private void fixMalType(Tresponse response) {
        if (response.getEnumDataTypes().isStruct()&&hasDataStruct(response.getNumdatastructid())){
            response.setEnumContentType(ContentType.APP_JSON);
        }else if (!response.getEnumDataTypes().isStruct()){
            response.setNumdatastructid(null);
            response.setEnumContentType(ContentType.PLAIN_TXT);
        } else if (!hasDataStruct(response.getNumdatastructid())){
            response.setEnumDataTypes(DataTypes.STRING);
            response.setNumdatastructid(null);
            response.setEnumContentType(ContentType.PLAIN_TXT);
        }
        if (!response.getEnumDataTypes().equals(DataTypes.ENUM)){
            response.setNumenumgroupid(null);
        } else if (!hasEnumGroup(response.getNumenumgroupid())){
            response.setEnumDataTypes(DataTypes.STRING);
            response.setNumenumgroupid(null);
        }
    }
}
