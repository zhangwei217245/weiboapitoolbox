package com.weibo.api.toolbox.service.spec;

import com.weibo.api.toolbox.common.treemodel.SpecTreeNode;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author x-spirit
 */
@Service("categoryProvider")
public class CategoryProviderImpl implements CategoryProvider {
    @Resource
    IJpaDaoService jpaDaoService;

    public List<Tspeccategory> getAllSubCate(){
        String ql = "SELECT t FROM Tspeccategory t WHERE t.numparentcateid.numcateid <> 1 ORDER BY t.numparentcateid.numindex ASC, t.numindex";
        return jpaDaoService.findEntities(ql, null, true, -1, -1);
    }
    public List<Tspeccategory> getCateChildren(Tspeccategory pkg){
        String ql = "SELECT t FROM Tspeccategory t WHERE t.numparentcateid = :numparentcateid ORDER BY numindex";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("numparentcateid", pkg);
        return jpaDaoService.findEntities(ql, param, true, -1, -1);
    }

    public int getCateChildrenCount(Tspeccategory pkg){
        String ql = "SELECT count(t) FROM Tspeccategory t WHERE t.numparentcateid = :numparentcateid ORDER BY numindex";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("numparentcateid", pkg);
        return jpaDaoService.getEntityCount(ql, param).intValue();
    }

    public List<Tspeccategory> getCateLevelOne(){
        String ql = "SELECT t FROM Tspeccategory t WHERE t.numparentcateid.numcateid = 1 ORDER BY t.numindex";
        return jpaDaoService.findEntities(ql, null, true, -1, -1);
    }

    public Tspeccategory getRootCate(){
        return jpaDaoService.findOneEntityById(Tspeccategory.class, 1);
    }

    public void persistCategory(Tspeccategory speccate){
        if (speccate.getNumcateid()==null){
            jpaDaoService.create(speccate);
        }else{
            jpaDaoService.edit(speccate);
        }
    }
}
