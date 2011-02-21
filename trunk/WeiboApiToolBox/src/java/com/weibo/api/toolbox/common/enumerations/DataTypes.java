package com.weibo.api.toolbox.common.enumerations;

/**
 *
 * @author x-spirit
 */
public enum DataTypes {

    INT{
        public int getId(){
            return 1;
        }
        public String getName(){
            return "int";
        }
        public String getDesc(){
            return "整型";
        }
        public String getJsonType(){
            return "integer";
        }
    },INT64{
        public int getId(){
            return 2;
        }
        public String getName(){
            return "int64";
        }
        public String getDesc(){
            return "64位整型";
        }
        public String getJsonType(){
            return "integer";
        }
    },FLOAT{
        public int getId(){
            return 3;
        }
        public String getName(){
            return "float";
        }
        public String getDesc(){
            return "浮点型";
        }
        public String getJsonType(){
            return "number";
        }
    },FLOAT64{
        public int getId(){
            return 4;
        }
        public String getName(){
            return "float64";
        }
        public String getDesc(){
            return "64位浮点型";
        }
        public String getJsonType(){
            return "number";
        }
    },BOOLEAN{
        public int getId(){
            return 5;
        }
        public String getName(){
            return "boolean";
        }
        public String getDesc(){
            return "布尔型";
        }
        public String getJsonType(){
            return "boolean";
        }
    },DATETIME{
        public int getId(){
            return 6;
        }
        public String getName(){
            return "DateTime";
        }
        public String getDesc(){
            return "字符型";
        }
        public String getJsonType(){
            return "string";
        }
    },STRING{
        public int getId(){
            return 7;
        }
        public String getName(){
            return "string";
        }
        public String getDesc(){
            return "字符串型";
        }
        public String getJsonType(){
            return "string";
        }
    },BINARY{
        public int getId(){
            return 8;
        }
        public String getName(){
            return "Binary";
        }
        public String getDesc(){
            return "二进制数据或二进制流";
        }
        public String getJsonType(){
            return "any";
        }
    },ARRAY{
        public int getId(){
            return 9;
        }
        public String getName(){
            return "Array";
        }
        public String getDesc(){
            return "数组型";
        }
        public String getJsonType(){
            return "array";
        }
    },OBJECT{
        public int getId(){
            return 10;
        }
        public String getName(){
            return "Object";
        }
        public String getDesc(){
            return "对象";
        }
        public String getJsonType(){
            return "object";
        }
    },ENUM{
        public int getId(){
            return 11;
        }
        public String getName(){
            return "Enum";
        }
        public String getDesc(){
            return "枚举型";
        }
        public String getJsonType(){
            return "any";
        }
    },WILDCARD{
        public int getId(){
            return 12;
        }
        public String getName(){
            return "WILDCARD";
        }
        public String getDesc(){
            return "通配符(任意类型)";
        }
        public String getJsonType(){
            return "any";
        }
    };
    public abstract int getId();
    public abstract String getName();
    public abstract String getDesc();
    public abstract String getJsonType();

    public boolean isNumeric(){
        return (this.getJsonType().equals("integer")||this.getJsonType().equals("number"));
    }
    public boolean isString() {
        return (this.getJsonType().equals("string"));
    }
    public boolean isPrimitive() {
        return !(this.getJsonType().equals("object")||this.getJsonType().equals("array"));
    }
    public boolean isStruct() {
        return (this.getJsonType().equals("array")||this.getJsonType().equals("object"));
    }

    public static DataTypes getValueById(int id){
        for (DataTypes m : DataTypes.values()){
            if (m.getId()==id){
                return m;
            }
        }
        return null;
    }
}
