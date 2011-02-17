/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.service.spec;

import com.weibo.api.toolbox.persist.entity.Syserror;
import com.weibo.api.toolbox.persist.entity.Sysparam;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import java.util.List;

/**
 *
 * @author x-spirit
 */
public interface SysDataProvider {

    List getSysParametersByCate(Tspeccategory cate);

    List getSyserrorByCate(Tspeccategory cate);

    void saveSysParam(Sysparam sysparam);

    void saveSysError(Syserror syserr);

    void deleteSysParam(Sysparam sysparam);

    void deleteSysError(Syserror syserr);
}
