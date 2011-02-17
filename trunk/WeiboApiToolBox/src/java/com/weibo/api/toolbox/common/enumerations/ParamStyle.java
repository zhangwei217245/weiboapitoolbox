/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.toolbox.common.enumerations;

/**
 *
 * @author x-spirit
 */
public enum ParamStyle {
    
    QueryParam{
        public int getId(){
            return 1;
        }
        public String getName(){
            return "QueryParam";
        }
        public String getDesc(){
            return "查询参数，通过请求提交的查询参数";
        }
    },PathParam{
        public int getId(){
            return 2;
        }
        public String getName(){
            return "PathParam";
        }
        public String getDesc(){
            return "路径中的参数，即REST风格的参数";
        }
    },FormParam{
        public int getId(){
            return 3;
        }
        public String getName(){
            return "FormParam";
        }
        public String getDesc(){
            return "表单参数。从MIME为application/x-www-form-urlencoded的POST请求中提取的参数";
        }
    },
    HeaderParam{
        public int getId(){
            return 4;
        }
        public String getName(){
            return "HeaderParam";
        }
        public String getDesc(){
            return "请求头参数。放在请求头中的参数";
        }
    },CookieParam{
        public int getId(){
            return 5;
        }
        public String getName(){
            return "CookieParam";
        }
        public String getDesc(){
            return "Cookie参数，从Cookie中提取的参数";
        }
    },MatrixParam{
        public int getId(){
            return 6;
        }
        public String getName(){
            return "MatrixParam";
        }
        public String getDesc(){
            return "一种灵活到少见的参数风格，不推荐使用。http://www.w3.org/DesignIssues/MatrixURIs.html";
        }
    };
    
    public abstract int getId();
    public abstract String getName();
    public abstract String getDesc();

    public static ParamStyle getValueById(int id){
        for (ParamStyle m : ParamStyle.values()){
            if (m.getId()==id){
                return m;
            }
        }
        return null;
    }
}
