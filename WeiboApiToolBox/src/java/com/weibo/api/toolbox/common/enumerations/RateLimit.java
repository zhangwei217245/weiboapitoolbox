package com.weibo.api.toolbox.common.enumerations;

/**
 *
 * @author x-spirit
 */
public enum RateLimit {

    NONE {

        @Override
        public int getId() {
            return 1;
        }

        @Override
        public String getName() {
            return "无限制";
        }

        @Override
        public String getDesc() {
            return "没有任何频率限制";
        }
    }, USER {

        @Override
        public int getId() {
            return 2;
        }

        @Override
        public String getName() {
            return "按用户";
        }

        @Override
        public String getDesc() {
            return "按用户进行频率限制";
        }
    }, IP {

        @Override
        public int getId() {
            return 3;
        }

        @Override
        public String getName() {
            return "按IP";
        }

        @Override
        public String getDesc() {
            return "按IP进行频率限制";
        }
    }, USER_IP {

        @Override
        public int getId() {
            return 4;
        }

        @Override
        public String getName() {
            return "按用户和IP";
        }

        @Override
        public String getDesc() {
            return "按用户和IP进行限制";
        }
    };

    public abstract int getId();

    public abstract String getName();

    public abstract String getDesc();

    public static RateLimit getValueById(int id){
        for (RateLimit m : RateLimit.values()){
            if (m.getId()==id){
                return m;
            }
        }
        return null;
    }
}
