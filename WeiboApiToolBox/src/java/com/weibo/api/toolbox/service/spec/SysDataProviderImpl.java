package com.weibo.api.toolbox.service.spec;

import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Syserror;
import com.weibo.api.toolbox.persist.entity.Sysparam;
import com.weibo.api.toolbox.persist.entity.Tenumgroup;
import com.weibo.api.toolbox.persist.entity.Tenumvalues;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.persist.qlgenerator.JPQLGenerator;
import com.weibo.api.toolbox.persist.qlgenerator.QLGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author x-spirit
 */
@Service("sysDataProvider")
public class SysDataProviderImpl implements SysDataProvider {

    @Resource
    IJpaDaoService jpaDaoService;

    public List getSysParametersByCate(Tspeccategory cate) {
        QLGenerator qlgen = new JPQLGenerator();
        List rstlst = null;
        qlgen.select("s").from("Sysparam s").where(null, "s.numenable = 1");
        if (cate != null) {
            qlgen.where(null, "s.numcateid = :numcateid");
            Map param = new HashMap();
            param.put("numcateid", cate);
            rstlst = jpaDaoService.findEntities(qlgen.toString(), param, true, -1, -1);
        }
        return rstlst;
    }

    public List getSyserrorByCate(Tspeccategory cate){
        QLGenerator qlgen = new JPQLGenerator();
        List rstlst = null;
        qlgen.select("e").from("Syserror e").where(null, "e.numenable=1");
        if (cate!=null){
            qlgen.where(null, "e.numcateid = :numcateid");
            Map param = new HashMap();
            param.put("numcateid", cate);
            rstlst = jpaDaoService.findEntities(qlgen.toString(), param, true, -1, -1);
        }
        return rstlst;
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

    public void saveSysParam(Sysparam sysparam){
        if (sysparam.getEnumDataTypes().equals(DataTypes.ENUM)&&hasEnumGroup(sysparam)){
            sysparam.setVc2range(getEnumRange(sysparam.getNumenumgroupid()));
        } else if (!sysparam.getEnumDataTypes().equals(DataTypes.ENUM)){
            sysparam.setNumenumgroupid(null);
        } else if (!hasEnumGroup(sysparam)){
            sysparam.setEnumDataTypes(DataTypes.STRING);
            sysparam.setNumenumgroupid(null);
        }
        if (sysparam.getNumparamid()==null){
            jpaDaoService.create(sysparam);
        }else{
            jpaDaoService.edit(sysparam);
        }
    }

    private boolean hasEnumGroup(Sysparam sysparam){
        return sysparam.getNumenumgroupid()!=null&&sysparam.getNumenumgroupid().getNumenumgroupid()!=1;
    }

    public void saveSysError(Syserror syserr){
        if (syserr.getNumerrorid() == null){
            jpaDaoService.create(syserr);
        }else{
            jpaDaoService.edit(syserr);
        }
    }

    public void deleteSysParam(Sysparam sysparam){
        jpaDaoService.destroy(sysparam.getClass(), sysparam.getNumparamid());
    }

    public void deleteSysError(Syserror syserr){
        jpaDaoService.destroy(syserr.getClass(), syserr.getNumerrorid());
    }
}
