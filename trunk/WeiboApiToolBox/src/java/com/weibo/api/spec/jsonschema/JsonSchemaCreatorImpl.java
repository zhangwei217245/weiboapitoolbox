package com.weibo.api.spec.jsonschema;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weibo.api.toolbox.common.enumerations.DataTypes;
import com.weibo.api.toolbox.persist.entity.Tdatastruct;
import com.weibo.api.toolbox.persist.entity.Tstructfield;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author x-spirit
 */
public class JsonSchemaCreatorImpl {

    public void generateSchemaMap(Map schemaMap,Tdatastruct struct){
        String vc2desc = struct.getVc2desc();
        schemaMap.put("description", vc2desc);
        Map properties = new HashMap();
        generateProperties(properties,struct.getTstructfieldSet());
        schemaMap.put("properties", properties);
    }

    private void generateProperties(Map properties, List<Tstructfield> tstructfieldSet) {
        for (Tstructfield field : tstructfieldSet){
            String name = field.getVc2fieldname();
            addPropertyValues(field,properties);
            DataTypes dataType = field.getEnumDataTypes();
            if (dataType.equals(DataTypes.ENUM)){

            }
        }
    }

    private void addPropertyValues(Tstructfield field, Map properties) {
        Map propertyValues = new HashMap();
        propertyValues.put("type", field.getEnumDataTypes().getJsonType());
        //propertyValues.put("", field.get);
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
        param.put("description", "a person");
        param.put("type", "object");
        Map properties = new HashMap();
        Map propertieValue = new HashMap();
        propertieValue.put("type", "integer");
        properties.put("id", propertieValue);
        param.put("properties", properties);
        ObjectMapper om = new ObjectMapper();
        String writeValueAsString = om.defaultPrettyPrintingWriter().writeValueAsString(param);
        System.out.println(writeValueAsString);
    }

    
}
