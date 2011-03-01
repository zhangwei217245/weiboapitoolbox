package com.weibo.api.toolbox.common.enumerations;

/**
 *
 * @author X-Spirit
 */
public enum ApiType {

    PUBLIC {

        public int getId() {
            return 1;
        }

        public String getName() {
            return "公开接口";
        }

        public String getEnName(){
            return "Public";
        }

        public String getDesc() {
            return "公开开放的接口";
        }
    }, COOPERATE {

        public int getId() {
            return 2;
        }

        public String getName() {
            return "合作方接口";
        }

        public String getEnName(){
            return "Cooperation";
        }

        public String getDesc() {
            return "仅对合作方开放的接口";
        }
    }, INTERNAL {

        public int getId() {
            return 3;
        }

        public String getName() {
            return "内部接口";
        }

        public String getEnName(){
            return "Internal";
        }

        public String getDesc() {
            return "内部接口";
        }
    };

    public abstract int getId();

    public abstract String getName();

    public abstract String getEnName();

    public abstract String getDesc();

    public static ApiType getValueById(int id){
        for (ApiType m : ApiType.values()){
            if (m.getId()==id){
                return m;
            }
        }
        return null;
    }
}
