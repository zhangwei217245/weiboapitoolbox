package com.weibo.api.toolbox.common.enumerations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author x-spirit
 */
public enum ContentType {

    APP_JSON{
        public int getId(){
            return 1;
        }
        public String getName(){
            return "JSON";
        }
        public MediaType getMediaType(){
            return MediaType.APPLICATION_JSON_TYPE;
        }
        public String getMediaString(){
            return MediaType.APPLICATION_JSON;
        }
        public String getDesc(){
            return "JSON格式";
        }
    },APP_XML{
        public int getId(){
            return 2;
        }
        public String getName(){
            return "XML";
        }
        public MediaType getMediaType(){
            return MediaType.APPLICATION_XML_TYPE;
        }
        public String getMediaString(){
            return MediaType.APPLICATION_XML;
        }
        public String getDesc(){
            return "XML格式";
        }
    },
    PLAIN_TXT{
        public int getId(){
            return 3;
        }
        public String getName(){
            return "纯文本";
        }
        public MediaType getMediaType(){
            return MediaType.TEXT_PLAIN_TYPE;
        }
        public String getMediaString(){
            return MediaType.TEXT_PLAIN;
        }
        public String getDesc(){
            return "纯文本";
        }
    };

    public abstract int getId();
    public abstract String getName();
    public abstract MediaType getMediaType();
    public abstract String getMediaString();
    public abstract String getDesc();


    public static ContentType getValueById(int id){
        for (ContentType m : ContentType.values()){
            if (m.getId()==id){
                return m;
            }
        }
        return null;
    }
    
    public static ContentType getValueById(String id){
        for (ContentType m : ContentType.values()){
            if (m.getId()==Integer.parseInt(id)){
                return m;
            }
        }
        return null;
    }
    public static ContentType[] getMultiValueByIds(String ids,String delimiter){
        List<ContentType> rstlst = new ArrayList<ContentType>();
        for (String id : ids.split(delimiter)){
            rstlst.add(getValueById(id));
        }
        return rstlst.toArray(new ContentType[]{});
    }
    public static String getMultiIds(ContentType[] enms, String delimiter){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (ContentType ct : enms){
            if (++i > 1){
                sb.append(delimiter);
            }
            sb.append(ct.getId());
        }
        return sb.toString();
    }
}
