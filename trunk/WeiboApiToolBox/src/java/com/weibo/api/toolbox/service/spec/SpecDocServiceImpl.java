/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.service.spec;

import com.weibo.api.spec.basic.BaseArgument;
import com.weibo.api.spec.jsonschema.JsonSchemaCreator;
import com.weibo.api.spec.wadl.WadlBinding;
import com.weibo.api.spec.wadl.wadl20090202.Application;
import com.weibo.api.spec.wiki.WikiGenerator;
import com.weibo.api.toolbox.common.enumerations.ContentType;
import com.weibo.api.toolbox.persist.IJpaDaoService;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tresponse;
import com.weibo.api.toolbox.persist.entity.Tspec;
import com.weibo.api.toolbox.persist.entity.Tspeccategory;
import com.weibo.api.toolbox.persist.entity.Tstructfield;
import com.weibo.api.toolbox.persist.qlgenerator.JPQLGenerator;
import com.weibo.api.toolbox.persist.qlgenerator.QLGenerator;
import com.weibo.api.toolbox.util.ToolBoxUtil;
import com.weibo.api.toolbox.util.ZipOutUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    @Resource
    IJpaDaoService jpaDaoService;
    @Resource
    WikiGenerator wikigenerator;
    @Resource
    SpecProvider specProvider;

    public Map<String, List<String>> genMultiSchemaForEachSpec(List<Tspec> _specList) {
        Map<String, List<String>> schemaDocMap = null;
        if (_specList != null && _specList.size() > 0) {
            schemaDocMap = new HashMap<String, List<String>>();
            for (Tspec spec : _specList) {
                List<String> structPathList = genOneSchemaForOneSpec(spec);
                schemaDocMap.put(spec.getSpecTitle(), structPathList);
            }
        }
        return schemaDocMap;
    }

    public List<String> genOneSchemaForOneSpec(Tspec spec) {
        List<String> structPathList = new ArrayList<String>();
        for (Tresponse response : spec.getTresponseSet()) {
            if (response.getEnumContentType().equals(ContentType.APP_JSON)) {
                generateJsonSchema(structPathList, response);
            }
        }
        return structPathList;
    }

    public File genMultiSpecWadl(List<Tspeccategory> cateLevelOne){
        String doccatedir = baseArgument.getWadlFileBaseDir();
        for (Tspeccategory levelOne : cateLevelOne) {
            List<Tspec> _specList = specProvider.getSpecListByParentCate(levelOne);
            if (ToolBoxUtil.isNotEmpty(_specList)) {
                genMultiSpecInOneWadl(_specList);
            }
        }
        
        File out = new File(doccatedir+ ".zip");
        File in = new File(doccatedir);
        ZipOutUtil.zip(out, in);
        return out;
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

    private void generateJsonSchema(List<String> structPathList, Tresponse response) {
        if (response.getEnumDataTypes().isStruct()) {
            Tdatastruct struct = response.getNumdatastructid();
            generateJsonSchemaByStruct(structPathList, struct);
        }
    }

    public File batchJsonSchemaByStruct() {
        QLGenerator qlgen = new JPQLGenerator();
        qlgen.select("ds").from("Tdatastruct ds").where(null, "ds.numenable=1");
        List<Tdatastruct> dslist = jpaDaoService.findEntities(qlgen.toString(), null, true, -1, -1);
        for (Tdatastruct ds : dslist) {
            try {
                String schemaFilePath = baseArgument.getSchemaFileBaseDir() + "/" + ds.getStructDocName() + ".jssd";
                File schemaFile = new File(schemaFilePath);
                Map schemaMap = jsonSchemaCreator.generateSchemaMap(ds);
                jsonSchemaCreator.writeToFile(schemaFile, schemaMap);
            } catch (IOException ex) {
                Logger.getLogger(SpecDocServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        File out = new File(baseArgument.getSchemaFileBaseDir() + ".zip");
        File in = new File(baseArgument.getSchemaFileBaseDir());
        ZipOutUtil.zip(out, in);
        return out;
    }

    public File batchWikiByStruct() {
        wikigenerator.generateDsMenu();
        File in = new File(baseArgument.getDSWikiFileBaseDir());
        File out = new File(baseArgument.getDSWikiFileBaseDir()+".zip");
        ZipOutUtil.zip(out, in);
        return out;
    }

    private void generateJsonSchemaByStruct(List<String> structPathList, Tdatastruct struct) {
        String schemaFilePath = baseArgument.getSchemaFileBaseDir()
                + "/" + struct.getStructDocName() + ".jssd";
        File schemaFile = new File(schemaFilePath);
        long interval = System.currentTimeMillis() - schemaFile.lastModified();
        if (schemaFile.exists() && interval < 5000) {
            return;
        }
        try {
            Map schemaMap = jsonSchemaCreator.generateSchemaMap(struct);
            jsonSchemaCreator.writeToFile(schemaFile, schemaMap);
            structPathList.add(schemaFilePath);
        } catch (IOException ex) {
            Logger.getLogger(SpecDocServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Tstructfield field : struct.getTstructfieldSet()) {
            Tdatastruct fieldstruct = field.getNumdatastructid();
            if (field.getEnumDataTypes().isStruct() && fieldstruct != null) {
                generateJsonSchemaByStruct(structPathList, fieldstruct);
            }
        }
    }

    public File genWikiZipByParentCate(Tspeccategory pcate){
        final Map dataMap = new HashMap();
        String menuPath = wikigenerator.generateMenuByParentCate(pcate, dataMap, true);
        File in = new File(menuPath).getParentFile();
        File out = new File(in.getAbsolutePath()+".zip");
        ZipOutUtil.zip(out, in);
        return out;
    }
}
