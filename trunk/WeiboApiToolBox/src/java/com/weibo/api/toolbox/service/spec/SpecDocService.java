/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.service.spec;

import com.weibo.api.toolbox.persist.entity.Tspec;
import java.util.List;
import java.util.Map;

/**
 *
 * @author x-spirit
 */
public interface SpecDocService {

    Map<String,String> genMultiSchemaForEachSpec(List<Tspec> _specList);

    String genOneSchemaForOneSpec(Tspec spec);

    String genMultiSpecInOneWadl(List<Tspec> _specList);

    String genOneSpecWadl(Tspec spec);

}
