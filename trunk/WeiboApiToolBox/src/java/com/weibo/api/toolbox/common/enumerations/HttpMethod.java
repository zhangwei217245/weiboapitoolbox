package com.weibo.api.toolbox.common.enumerations;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author x-spirit
 */
public enum HttpMethod {

    GET {

        public int getId() {
            return 1;
        }

        public String getName() {
            return "GET";
        }

        public String getDesc() {
            return "GET方法";
        }

    }, POST {

        public int getId() {
            return 2;
        }

        public String getName() {
            return "POST";
        }

        public String getDesc() {
            return "POST方法";
        }

    }, PUT {

        public int getId() {
            return 3;
        }

        public String getName() {
            return "PUT";
        }

        public String getDesc() {
            return "PUT方法";
        }

    }, DELETE {

        public int getId() {
            return 4;
        }

        public String getName() {
            return "DELETE";
        }

        public String getDesc() {
            return "DELETE方法";
        }

    };

    public abstract int getId();

    public abstract String getName();

    public abstract String getDesc();

    public static HttpMethod getValueById(int id){
        for (HttpMethod m : HttpMethod.values()){
            if (m.getId()==id){
                return m;
            }
        }
        return null;
    }
    public static HttpMethod getValueById(String id){
        return getValueById(Integer.parseInt(id));
    }

    public static HttpMethod[] getMultiValueByIds(String ids,String delimiter){
        List<HttpMethod> rstlst = new ArrayList<HttpMethod>();
        for (String id : ids.split(delimiter)){
            rstlst.add(getValueById(id));
        }
        return rstlst.toArray(new HttpMethod[]{});
    }
    public static String getMultiIds(HttpMethod[] enms, String delimiter){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (HttpMethod enm : enms){
            if (++i > 1){
                sb.append(delimiter);
            }
            sb.append(enm.getId());
        }
        return sb.toString();
    }
}
