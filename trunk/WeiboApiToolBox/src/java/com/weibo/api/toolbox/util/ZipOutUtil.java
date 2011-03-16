/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tools.zip.ZipOutputStream;

/**
 *
 * @author x-spirit
 */
public class ZipOutUtil {

    public static void zip(File out, File src) {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            String base = "";
            if (src.isDirectory()) {
                base = src.getName();
            }
            zip(zos, src, base);
        } catch (Exception ex) {
            Logger.getLogger(ZipOutUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                zos.close();
            } catch (IOException ex) {
                Logger.getLogger(ZipOutUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * ZipOutPut (out is need to manually close)
     * @param out
     * @param f
     * @param base
     * @throws Exception
     */
    public static void zip(ZipOutputStream out, File f, String base) throws Exception {
        out.setEncoding("GB18030");
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            //if(base.length()!=0){
                out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
            //}
            //out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        }else{
            out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
            //out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b;
            System.out.println(base);
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
        //out.close();
    }

}
