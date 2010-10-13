package com.sina.weibo.toolbox.util;

import java.io.File;
import java.io.FileFilter;

/**
 * 根据条件过滤出相应的文件，条件为“dir”则过滤出目录，条件为“file”则过滤出文件
 * @author X-Spirit
 */
public class PathTypeFilter implements FileFilter {

    private String key;

    public PathTypeFilter() {
    }

    public PathTypeFilter(String key) {
        this.key = key;
    }

    public boolean accept(File file) {
        if ("dir".equals(key) && file.isDirectory()) {
            return true;
        } else if ("file".equalsIgnoreCase(key) && file.isFile()) {
            return true;
        } else {
            return false;
        }
    }
}
