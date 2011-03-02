/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.service.spec;

import com.weibo.api.spec.basic.BaseArgument;
import com.weibo.api.spec.jsonschema.JsonSchemaCreator;
import com.weibo.api.spec.wadl.WadlBinding;
import com.weibo.api.spec.wadl.wadl20090202.Application;
import com.weibo.api.toolbox.common.enumerations.ContentType;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tresponse;
import com.weibo.api.toolbox.persist.entity.Tspec;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Map<String, String> genMultiSchemaForEachSpec(List<Tspec> _specList) {
        Map<String, String> schemaDocMap = null;
        if (_specList != null && _specList.size() > 0) {
            schemaDocMap = new HashMap<String, String>();
            for (Tspec spec : _specList) {
                String schemaPath = genOneSchemaForOneSpec(spec);
                schemaDocMap.put(spec.getSpecTitle(), schemaPath);
            }
        }
        return schemaDocMap;
    }

    public String genOneSchemaForOneSpec(Tspec spec) {
        for (Tresponse response : spec.getTresponseSet()) {
            if (response.getEnumContentType().equals(ContentType.APP_JSON)) {
                return generateJsonSchema(response);
            }
        }
        return null;
    }

    public String genMultiSpecInOneWadl(List<Tspec> _specList) {
        String docpath = null;
        if (_specList != null && _specList.size() > 0) {
            try {
                Tspec samplespec = _specList.get(0);
                String doccatedir = baseArgument.getWadlFileBaseDir();
                //always replace the same file by spec id and cate_id,
                //ToolBoxUtil.deleteFile(new File(doccatedir));
                for (Tspec spec : _specList) {
                    genOneSpecWadl(spec);
                }
                docpath = doccatedir + "/" + "cate_" + samplespec.getNumcateid().getNumcateid() + ".wadl";
                Application app = wadlbinder.bindApplication(_specList);
                wadlbinder.marshallToFile(app, new File(docpath));
            } catch (IOException ex) {
                Logger.getLogger(SpecDocServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                docpath = null;
            }
        }
        return docpath;
    }

    public String genOneSpecWadl(Tspec spec) {
        String docpath = null;
        try {
            docpath = baseArgument.getWadlFileBaseDir()
                    + "/" + spec.getNumspecid() + ".wadl";
            Application app = wadlbinder.bindApplication(spec);
            wadlbinder.marshallToFile(app, new File(docpath));
        } catch (IOException ex) {
            Logger.getLogger(SpecDocServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            docpath = null;
        }
        return docpath;
    }

    private String generateJsonSchema(Tresponse response) {
        String schemaFilePath = null;
        if (response.getEnumDataTypes().isStruct()) {
            try {
                Tdatastruct struct = response.getNumdatastructid();
                Map schemaMap = jsonSchemaCreator.generateSchemaMap(struct);
                schemaFilePath = baseArgument.getSchemaFileBaseDir()
                        + "/" + struct.getStructDocName() + ".jssd";
                jsonSchemaCreator.writeToFile(new File(schemaFilePath), schemaMap);
            } catch (IOException ex) {
                Logger.getLogger(SpecDocServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return schemaFilePath;
    }
}
