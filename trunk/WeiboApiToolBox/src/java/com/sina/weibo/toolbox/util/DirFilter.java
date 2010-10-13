package com.sina.weibo.toolbox.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *根据正则表达式过滤出所有匹配的目录
 * @author X-Spirit
 */
public class DirFilter implements FilenameFilter {

    private String key;

    public DirFilter() {
    }

    public DirFilter(String key) {
        this.key = key;
    }

    public boolean accept(File dir, String name) {

        File file = new File(dir.getAbsoluteFile() + "/" + name);

        if (file.isFile()) {
            return false; //如果是文件，不管有没有包含key，都返回false
        }
        if (key == null) {
            return true;//如果key为空，则返回true
        }
        Pattern p = Pattern.compile(key); //这里key是正则表达式了
        Matcher m = p.matcher(name);

        return m.matches();
    }
}
