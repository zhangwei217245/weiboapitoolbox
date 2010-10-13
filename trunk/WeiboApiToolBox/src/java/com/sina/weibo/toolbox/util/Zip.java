package com.sina.weibo.toolbox.util;

import java.io.*;
import java.util.*;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import java.util.logging.Logger;

public class Zip {

    public static final Logger log = Logger.getLogger(Zip.class.getName());

    public static void ZipFiles(File[] srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void UnZipFiles(File file, String descDir) throws ZipException, IOException {
        ZipFile zf = new ZipFile(file);
        File dir = new File(descDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            String zipEntryName = entry.getName();
            InputStream in = zf.getInputStream(entry);
            File tempFile = new File(descDir + "/" + zipEntryName);
            if (entry.isDirectory()) {
                tempFile.mkdir();
                continue;
            } else {
                makedir(tempFile.getParent());
            }
            if (!entry.isDirectory()) {
                OutputStream out = new FileOutputStream(tempFile);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
                if (tempFile.getName().endsWith(".jar")) {
                    UnZipFiles(tempFile, tempFile.getPath().substring(0, tempFile.getPath().indexOf(".jar")));
                }
            }
        }
        zf.close();
        file.delete();
    }

    private static void makedir(String path) {
        File aa = new File(path);
        if (!aa.exists()) {
            if (aa.getParent() != null && aa.getParent().length() > 0) {
                makedir(aa.getParent());
            }
            aa.mkdir();
        }
    }
}
