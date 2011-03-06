/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.service.dashboard;

import com.weibo.api.toolbox.persist.entity.Tspec;
import java.util.List;

/**
 *
 * @author x-spirit
 */
public interface DashBoardService {

    List<Tspec> getSpecByCondition(String id, String mainres, String subres, String sortCol, String sortDirection, int pagesize, int startpos);

    int getSpecCountByCondition(String id, String mainres, String subres);

}
