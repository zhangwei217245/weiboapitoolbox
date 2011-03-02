/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.service.spec;

import com.weibo.api.spec.basic.BaseArgument;
import com.weibo.api.spec.jsonschema.JsonSchemaCreator;
import com.weibo.api.spec.wadl.WadlBinding;
import com.weibo.api.spec.wadl.wadl20090202.Application;
import com.weibo.api.toolbox.persist.entity.Tspec;
import java.io.File;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author x-spirit
 */
@Service("specDocService")
public class SpecDocServiceImpl implements SpecDocService {
    @Resource
    WadlBinding wadlbinder;
    @Resource
    BaseArgument baseArgument;
    @Resource
    JsonSchemaCreator jsonSchemaCreator;

    public String genMultiSpecInOneWadl(List<Tspec> _specList) {
        if (_specList != null && _specList.size() > 0) {
            Tspec samplespec = _specList.get(0);
            String doccatedir = baseArgument.getWadlFileBaseDir();
            //always replace the same file by spec id and cate_id,
            //ToolBoxUtil.deleteFile(new File(doccatedir));
            for (Tspec spec : _specList) {
                genOneSpecWadl(spec);
            }
            String docpath = doccatedir
                    + "/" + "cate_"
                    + samplespec.getNumcateid().getNumcateid()
                    + ".wadl";
            Application app = wadlbinder.bindApplication(_specList);
            wadlbinder.marshallToFile(app, new File(docpath));
            return docpath;
        } else {
            return null;
        }
    }

    public String genOneSpecWadl(Tspec spec) {
        String docpath = baseArgument.getWadlFileBaseDir()
                + "/" + spec.getNumspecid() + ".wadl";
        if (!new File(docpath).exists()){
            Application app = wadlbinder.bindApplication(spec);
            wadlbinder.marshallToFile(app, new File(docpath));
        }
        return docpath;
    }
}
