/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.common.enumerations;

import javax.ws.rs.core.MediaType;

/**
 *
 * @author x-spirit
 */
public enum AcceptType {
    WILDCARD{
        public int getId(){
            return 1;
        }
        public String getName(){
            return "任意";
        }
        public MediaType getMediaType(){
            return MediaType.WILDCARD_TYPE;
        }
        public String getMediaString(){
            return MediaType.WILDCARD;
        }
        public String getDesc(){
            return "未指定的类型";
        }
    },
    FORM_URL_ENCODED{
        public int getId(){
            return 2;
        }
        public String getName(){
            return "FORM_URL_ENCODED";
        }
        public MediaType getMediaType(){
            return MediaType.APPLICATION_FORM_URLENCODED_TYPE;
        }
        public String getMediaString(){
            return MediaType.APPLICATION_FORM_URLENCODED;
        }
        public String getDesc(){
            return "经过URL ENCODE的表单";
        }
    },
    FORM_MULTIPART{
        public int getId(){
            return 3;
        }
        public String getName(){
            return "FORM_MULTIPART";
        }
        public MediaType getMediaType(){
            return MediaType.MULTIPART_FORM_DATA_TYPE;
        }
        public String getMediaString(){
            return MediaType.MULTIPART_FORM_DATA;
        }
        public String getDesc(){
            return "Multi-part表单";
        }
    };

    public abstract int getId();
    public abstract String getName();
    public abstract MediaType getMediaType();
    public abstract String getMediaString();
    public abstract String getDesc();

    public static AcceptType getValueById(int id){
        for (AcceptType m : AcceptType.values()){
            if (m.getId()==id){
                return m;
            }
        }
        return null;
    }
}
