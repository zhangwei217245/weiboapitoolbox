/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.service.dashboard;

import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tspec;
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
@Service("dashBoardService")
public class DashBoardServiceImpl implements DashBoardService {

    @Resource
    IJpaDaoService jpaDaoService;


    public List<Tspec> getSpecByCondition(Integer id,String mainres,String subres,String sortCol,String sortDirection,int pagesize,int startpos){
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("t").from("Tspec t").orderBy("t.datupdated", "desc");
        Map param = new HashMap();
        if (id!=null){
            qlgen.where(null, "t.numspecid = :numspecid");
            param.put("numspecid", id);
        }
        if(ToolBoxUtil.isNotEmpty(mainres)){
            qlgen.where(null, "t.vc2mainresource LIKE :vc2mainresource");
            param.put("vc2mainresource", '%'+mainres+'%');
        }
        if(ToolBoxUtil.isNotEmpty(subres)){
            qlgen.where(null, "t.vc2subresource LIKE :vc2subresource");
            param.put("vc2subresource", '%'+subres+'%');
        }
        if(ToolBoxUtil.isNotEmpty(sortCol)&&ToolBoxUtil.isNotEmpty(sortDirection)){
            qlgen.orderBy(qlgen.ORDER_BY,null).orderBy(sortCol, sortDirection);
        }
        return jpaDaoService.findEntities(qlgen.toString(), param, false, startpos, pagesize);
    }

    public int getSpecCountByCondition(Integer id,String mainres,String subres){
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("count(t)").from("Tspec t");
        Map param = new HashMap();
        if (id!=null){
            qlgen.where(null, "t.numspecid = :numspecid");
            param.put("numspecid", id);
        }
        if(ToolBoxUtil.isNotEmpty(mainres)){
            qlgen.where(null, "t.vc2mainresource LIKE :vc2mainresource");
            param.put("vc2mainresource", '%'+mainres+'%');
        }
        if(ToolBoxUtil.isNotEmpty(subres)){
            qlgen.where(null, "t.vc2subresource LIKE :vc2subresource");
            param.put("vc2subresource", '%'+subres+'%');
        }
        return jpaDaoService.getEntityCount(qlgen.toString(), param).intValue();
    }
}
