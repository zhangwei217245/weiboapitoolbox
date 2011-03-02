package com.weibo.api.spec.jsonschema;

import com.weibo.api.spec.basic.BaseArgument;
import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tenumvalues;
import com.weibo.api.toolbox.persist.entity.Tstructfield;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author x-spirit
 */
public class JsonSchemaCreatorImpl implements JsonSchemaCreator {

    @javax.annotation.Resource
    BaseArgument baseArgument;
    ObjectMapper om = new ObjectMapper();

    public String writeToString(Map schemaMap) throws IOException {
        return om.defaultPrettyPrintingWriter().writeValueAsString(schemaMap);
    }

    public void writeToFile(File file, Map schemaMap) throws IOException {
        PrintStream out = null;
        file.getParentFile().mkdirs();
        out = new PrintStream(file, "UTF-8");
        writeToStream(out, schemaMap);
    }

    public void writeToStream(OutputStream out, Map schemaMap) throws IOException {
        om.defaultPrettyPrintingWriter().writeValue(out, schemaMap);
    }

    public Map generateSchemaMap(Tdatastruct struct) {
        Map schemaMap = new HashMap();
        String vc2desc = struct.getVc2desc();
        processStructSchema(schemaMap, "$schema", struct);
        processStructSchema(schemaMap, "id", struct);
        schemaMap.put("description", vc2desc);
        schemaMap.put("type", "object");
        Map properties = new HashMap();
        generateProperties(properties, struct.getTstructfieldSet());
        schemaMap.put("properties", properties);
        return schemaMap;
    }

    private void generateProperties(Map properties, List<Tstructfield> tstructfieldSet) {
        for (Tstructfield field : tstructfieldSet) {
            addPropertyValues(field, properties);
        }
    }

    private void addPropertyValues(Tstructfield field, Map properties) {
        Map propertyValues = new HashMap();
        propertyValues.put("description", field.getVc2desc());
        propertyValues.put("required", calcRequired(field));
        DataTypes type = field.getEnumDataTypes();
        propertyValues.put("type", type.getJsonType());
        if (type.equals(DataTypes.ENUM)) {
            processEnumTypes(propertyValues, type, field);
        } else if (type.isStruct()) {
            processStructRef(propertyValues, type, field);
        }
        properties.put(field.getVc2fieldname(), propertyValues);
    }

    private void processEnumTypes(Map propertyValues, DataTypes type, Tstructfield field) {
        try {
            propertyValues.put("type", om.readValue(type.getJsonType(), ArrayList.class));
        } catch (Exception e) {
            propertyValues.put("type", "any");
        }
        propertyValues.put("enum", getEnumValues(field.getNumenumgroupid().getTenumvaluesSet()));
    }

    private Set getEnumValues(Set<Tenumvalues> tenumvaluesSet) {
        Set<String> evset = new HashSet();
        for (Tenumvalues ev : tenumvaluesSet) {
            evset.add(ev.getVc2enumvalue());
        }
        return evset;
    }

    private void processStructRef(Map propertyValues, DataTypes type, Tstructfield field) {
        if (type.equals(DataTypes.ARRAY)) {
            //json array must have multiple json objects,
            //an array of primitive types may not be easy to look up
            propertyValues.put("type", type.getJsonType());
            Map urimap = new HashMap();
            processStructSchema(urimap, "$ref", field.getNumdatastructid());
            propertyValues.put("items", urimap);
        } else if (type.equals(DataTypes.OBJECT)) {
            processStructSchema(propertyValues, "$ref", field.getNumdatastructid());
        }
    }

    private void processStructSchema(Map map, String refORschema, Tdatastruct struct) {
        String jssdUri = baseArgument.getSchemaBaseURI()
                + "/base/"
                + struct.getStructDocName() + ".jssd";
        map.put(refORschema, jssdUri);
    }

    private boolean calcRequired(Tstructfield field) {
        //TODO: simply mark the private field as not required.
        return (!field.getIsPrivate()) && field.getIsRequired();
    }

    public static void main(String[] args) throws IOException {
        /**Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map param = new HashMap();
        param.put("description", "a person");
        param.put("type", "object");
        Map properties = new HashMap();
        Map propertieValue = new HashMap();
        propertieValue.put("type", "integer");
        properties.put("id", propertieValue);
        param.put("properties", properties);
        System.out.println(gson.toJson(param));*/
        Map param = new HashMap();
        ObjectMapper om = new ObjectMapper();
        param.put("description", "a person");
        param.put("type", "object");
        Map properties = new HashMap();
        Map propertieValue = new HashMap();
        propertieValue.put("type", "integer");
        Set arr = new HashSet();
        arr.add("string");
        arr.add("integer");
        propertieValue.put("type", arr);
        properties.put("id", propertieValue);
        param.put("properties", properties);

        String writeValueAsString = om.defaultPrettyPrintingWriter().writeValueAsString(param);
        System.out.println(writeValueAsString);
    }
}
