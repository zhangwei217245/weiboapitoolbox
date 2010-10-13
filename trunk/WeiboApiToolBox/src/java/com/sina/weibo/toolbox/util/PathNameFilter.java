package com.sina.weibo.toolbox.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据正则表达式过滤出所有匹配的文件，包括文件和目录
 * @author X-Spirit
 */
public class PathNameFilter implements FilenameFilter {

    private String key;

    public PathNameFilter() {
    }

    public PathNameFilter(String key) {
        this.key = key;
    }

    public boolean accept(File dir, String name) {

        Pattern p = Pattern.compile(key); //这里key是正则表达式了
        Matcher m = p.matcher(name);

        return m.matches();
    }
}
