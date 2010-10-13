package com.sina.weibo.toolbox.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author X-Spirit
 * 根据正则表达式过滤出所有匹配的文件
 * 
 */
public class FileFilter implements FilenameFilter {

    private String key;

    public FileFilter() {
    }

    public FileFilter(String key) {
        this.key = key;
    }

    public boolean accept(File dir, String name) {

        File file = new File(dir.getAbsoluteFile() + "/" + name);

        if (file.isDirectory()) {
            return false; //如果是目录，不管有没有包含key，都返回false
        }
        if (key == null) {
            return true;//如果key为空，则返回true
        }
        Pattern p = Pattern.compile(key); //这里key是正则表达式了
        Matcher m = p.matcher(name);

        return m.matches();
    }
}
