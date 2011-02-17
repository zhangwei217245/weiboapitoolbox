package com.weibo.api.toolbox.common.enumerations;

/**
 *
 */
public enum AuthType {

    REQUIRED {

        public int getId() {
            return 1;
        }

        public String getName() {
            return "必须验证";
        }

        public String getDesc() {
            return "必须验证";
        }
    },
    OPTION {

        public int getId() {
            return 2;
        }

        public String getName() {
            return "半开放";
        }

        public String getDesc() {
            return "可以不验证，但验证后接口表现会有所不同";
        }
    },
    NONE {

        public int getId() {
            return 3;
        }

        public String getName() {
            return "无验证";
        }

        public String getDesc() {
            return "不需要验证";
        }
    };

    public abstract int getId();

    public abstract String getName();

    public abstract String getDesc();

    public static AuthType getValueById(int id){
        for (AuthType m : AuthType.values()){
            if (m.getId()==id){
                return m;
            }
        }
        return null;
    }
}
