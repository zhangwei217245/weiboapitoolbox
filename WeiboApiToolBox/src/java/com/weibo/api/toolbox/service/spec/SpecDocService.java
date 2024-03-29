/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.service.spec;

import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @author x-spirit
 */
public interface SpecDocService {

    Map<String, List<String>> genMultiSchemaForEachSpec(List<Tspec> _specList);

    List<String> genOneSchemaForOneSpec(Tspec spec);

    String genMultiSpecInOneWadl(List<Tspec> _specList);

    String genOneSpecWadl(Tspec spec);

    File batchJsonSchemaByStruct();

    File batchWikiByStruct();

    File genMultiSpecWadl(List<Tspeccategory> cateLevelOne);

    File genWikiZipByParentCate(Tspeccategory pcate);
}
