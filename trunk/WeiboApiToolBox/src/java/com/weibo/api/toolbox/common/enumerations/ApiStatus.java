package com.weibo.api.toolbox.common.enumerations;

/**
 *
 * @author X-Spirit
 */
public enum ApiStatus {

    DRAFT {

        public int getId() {
            return 1;
        }

        public String getName() {
            return "草稿";
        }

        public String getDesc() {
            return "正在起草的文件";
        }
    }, DEV {

        public int getId() {
            return 2;
        }

        public String getName() {
            return "开发中";
        }

        public String getDesc() {
            return "审核完毕，开发中";
        }
    }, ALPHA {

        public int getId() {
            return 3;
        }

        public String getName() {
            return "Alpha测试";
        }

        public String getDesc() {
            return "开发完毕，进入Alpha测试阶段";
        }
    }, BETA {

        public int getId() {
            return 4;
        }

        public String getName() {
            return "Beta测试";
        }

        public String getDesc() {
            return "Beta测试阶段";
        }
    }, ACTIVE {

        public int getId() {
            return 5;
        }

        public String getName() {
            return "线上版本";
        }

        public String getDesc() {
            return "线上版本";
        }
    }, DEPRECATED{
        public int getId() {
            return 6;
        }

        public String getName() {
            return "已过时";
        }

        public String getDesc() {
            return "已过时";
        }
    }, DISABLE{
        public int getId() {
            return 7;
        }

        public String getName() {
            return "停用";
        }

        public String getDesc() {
            return "停用";
        }
    };

    public abstract int getId();

    public abstract String getName();

    public abstract String getDesc();

    public static ApiStatus getValueById(int id){
        for (ApiStatus m : ApiStatus.values()){
            if (m.getId()==id){
                return m;
            }
        }
        return null;
    }
}
