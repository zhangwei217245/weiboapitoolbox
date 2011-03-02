/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.spec.jsonschema;

import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 *
 * @author x-spirit
 */
public interface JsonSchemaCreator {

    Map generateSchemaMap(Tdatastruct struct);

    void writeToFile(File file, Map schemaMap) throws IOException;

    void writeToStream(OutputStream out, Map schemaMap) throws IOException;

    String writeToString(Map schemaMap) throws IOException;

}
