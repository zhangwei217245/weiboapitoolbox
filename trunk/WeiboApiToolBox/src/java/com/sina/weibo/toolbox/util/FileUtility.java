package com.sina.weibo.toolbox.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import java.util.logging.Logger;

import java.io.BufferedInputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

public class FileUtility {

    public static final Logger log = Logger.getLogger(FileUtility.class.getName());
    public static String charset = Charset.defaultCharset().name();
    public static boolean found = false;

    /**
     * 创建目录
     * @param path
     * @return
     */
    public static void createFile(File f, String filetype) throws IOException {

        if (!f.getParentFile().exists() && f.getParentFile() != null) {
            createFile(f.getParentFile(), "dir");
        } else {
            if (filetype.equals("dir")) {
                f.mkdir();
            } else {
                f.createNewFile();
            }

        }

    }

    public static void createFile(String path) throws IOException {
        File f = new File(path);

        if (!f.getParentFile().exists() && f.getParentFile() != null) {
            createFile(f.getParentFile(), "dir");
        } else {
            f.mkdirs();
        }

    }

    /**
     * 根据filterkey的正则表达式的到匹配的文件名数组。如果filterkey为空，则忽略正则表达式
     * filetype为dir则只列出目录，filetype为file则只列出文件，否则忽视文件类型
     * @param dir
     * @param filterkey
     * @param filetype
     * @return
     */
    public static String[] getFileNameList(String dir, String filetype, String filterkey) {
        File fdir = new File(dir);
        String[] flist = null;
        if (fdir.exists() && fdir.isDirectory()) {
            if (Utility.isNotEmpty(filterkey)) {
                if (Utility.isNotEmpty(filetype) && "dir".equalsIgnoreCase(filetype)) {
                    flist = fdir.list(new DirFilter(filterkey));
                } else if (Utility.isNotEmpty(filetype) && "file".equalsIgnoreCase(filetype)) {
                    flist = fdir.list(new FileFilter(filterkey));
                } else {
                    flist = fdir.list(new PathNameFilter(filterkey));
                }
            } else {
                if (Utility.isNotEmpty(filetype) && "dir".equalsIgnoreCase(filetype)) {
                    flist = fdir.list(new DirFilter(null));
                } else if (Utility.isNotEmpty(filetype) && "file".equalsIgnoreCase(filetype)) {
                    flist = fdir.list(new FileFilter(null));
                } else {
                    flist = fdir.list();
                }
            }
        }
        return flist;
    }

    /**
     * 根据filterkey的正则表达式的到匹配的文件对象数组。如果filterkey为空，则忽略正则表达式
     * filetype为dir则只列出目录，filetype为file则只列出文件，否则忽视文件类型
     * @param dir
     * @param filterkey
     * @return
     */
    public static File[] getFileList(String dir, String filetype, String filterkey) {
        File fdir = new File(dir);
        File[] flist = null;
        if (fdir.exists() && fdir.isDirectory()) {
            if (Utility.isNotEmpty(filterkey)) {
                if (Utility.isNotEmpty(filetype) && "dir".equalsIgnoreCase(filetype)) {
                    flist = fdir.listFiles(new DirFilter(filterkey));
                } else if (Utility.isNotEmpty(filetype) && "file".equalsIgnoreCase(filetype)) {
                    flist = fdir.listFiles(new FileFilter(filterkey));
                } else {
                    flist = fdir.listFiles(new PathNameFilter(filterkey));
                }
            } else {
                if (Utility.isNotEmpty(filetype) && "dir".equalsIgnoreCase(filetype)) {
                    flist = fdir.listFiles(new PathTypeFilter(filetype));
                } else if (Utility.isNotEmpty(filetype) && "file".equalsIgnoreCase(filetype)) {
                    flist = fdir.listFiles(new PathTypeFilter(filetype));
                } else {
                    flist = fdir.listFiles();
                }
            }
        }
        return flist;
    }

    /**
     * @author X-Spirit
     * 使用正则表达式过滤的文件删除
     * @param filePath 文件路径，如果是具体文件，则直接删除，正则表达式无效
     * @param regex 用于匹配文件名的正则表达式，例如newscon_*.html可以写为“newscon_[0-9]+\\.html”
     * @return 
     * */
    public static void deleteFilesByRegex(String filePath, String regex)
            throws IOException {
        File file = new File(filePath);
        if (file.isFile()) {
            log.info("Delete:" + file.getAbsolutePath());
            //System.out.println("Delete:"+file.getAbsolutePath()); 
            file.delete();// 如果是个文件则删除
            return;
        }
        // 否则是目录，继续遍历里面的文件
        File[] files = file.listFiles(new FileFilter(regex));
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                //System.out.println(files[i].getAbsolutePath());
                deleteFilesByRegex(files[i].getAbsolutePath(), regex);

            }
        }
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    public static void deleteFile(String path) throws IOException {
        File f = new File(path);//定义文件路径       
        if (f.exists() && f.isDirectory()) {//判断是文件还是目�录
            if (f.listFiles().length == 0) {//若目录下没有文件则直接删�除
                f.delete();
            } else {//若有则把文件放进数组，并判断是否有下级目�录
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        deleteFile(delFile[j].getAbsolutePath());//递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();//删除文件
                }
            }
            deleteFile(path);//递归调用
        } else if (f.exists() && f.isFile()) {
            f.delete();
        }
    }

    public static void copyFiel(String oldfile, String newfile)
            throws Exception {
        java.io.FileInputStream in = new java.io.FileInputStream(oldfile);
        java.io.FileOutputStream out = new java.io.FileOutputStream(newfile);
        byte Buff[] = new byte[1024];
        int len;
        while ((len = in.read(Buff)) > -1) {
            out.write(Buff, 0, len);
        }
        in.close();
        out.close();
    }

    /**
     * 获取网络文件
     *@author X-Spirit
     *@param url 网络上的URL
     *@param fileName 本地的绝对路径
     *@throws Exception
     *@return
     */
    public static void getNetFile(String url, String fileName) throws Exception {
        URL netUrl = new URL(url);
        log.info("@@@NetFileURL:" + netUrl + "");
        java.io.InputStream in = netUrl.openStream();
        java.io.FileOutputStream out = new java.io.FileOutputStream(fileName);
        byte Buff[] = new byte[1024];
        int len;
        while ((len = in.read(Buff)) > -1) {
            out.write(Buff, 0, len);
        }
        in.close();
        out.close();
        log.info("@@@Get Net File:" + netUrl + " SUCCESS!!!");
    }

    public static byte[] getNetFileByte(String url, String fileName) throws Exception {
        try {
            URL netUrl = new URL(url);
            java.io.InputStream in = netUrl.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * @author X-Spirit
     * 读取文本文件到StringBuffer
     * @param filepath   输入文件完整路径（包括文件名及扩展名）        
     * @param enctype    文件输入编码方案（UTF-8、GBK、GB2312等等）
     * @return StringBuffer sb
     * @throws IOException
     */
    public static StringBuffer readTextFile(String filepath, String enctype)
            throws IOException {

        FileInputStream fis = new FileInputStream(filepath);
        UnicodeInputStream uin = new UnicodeInputStream(fis, enctype);
        String chset = uin.getEncoding(); // check and skip possible BOM bytes
        System.out.println(chset);
        InputStreamReader isr;
        if (chset == null) {
            isr = new InputStreamReader(uin);
        } else {
            isr = new InputStreamReader(uin, chset);
        }
        BufferedReader bfrd = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer("");
        String rdline = null;
        while ((rdline = bfrd.readLine()) != null) {
            sb.append(rdline);
            sb.append("\r\n");
        }
        isr.close();
        bfrd.close();
        return sb;
    }

    /**
     * @author X-Spirit
     * 在文本文件中读取beginmark所在行以后及endmark以前的内容，读取到StringBuffer
     * @param filepath 输入文件完整路径（包括文件名及扩展名）  
     * @param beginmark 起始标志
     * @param endmark 结束标志
     * @return
     * @throws java.io.IOException
     */
    public static StringBuffer readTextFileByMark(String filepath, String beginmark, String endmark) throws Exception {
        String enctype = getFileEncoding(filepath, "local");
        FileInputStream fis = new FileInputStream(filepath);
        UnicodeInputStream uin = new UnicodeInputStream(fis, enctype);
        String chset = uin.getEncoding(); // check and skip possible BOM bytes
        System.out.println(chset);
        InputStreamReader isr;
        if (chset == null) {
            isr = new InputStreamReader(uin);
        } else {
            isr = new InputStreamReader(uin, chset);
        }
        BufferedReader bfrd = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer("");
        String rdline = null;
        boolean metbeginmark = false;
        while ((rdline = bfrd.readLine()) != null) {
            System.out.println(rdline);
            if (rdline.startsWith(beginmark)) {
                metbeginmark = true;
                break;
            } else {
                continue;
            }
        }
        while ((rdline = bfrd.readLine()) != null && metbeginmark) {
            System.out.println(rdline);
            if (!rdline.startsWith(endmark)) {
                sb.append(rdline);
                sb.append("\r\n");
            } else {
                break;
            }
        }
        isr.close();
        bfrd.close();
        return sb;
    }

    /**
     * @author X-Spirit
     * 在文本文件中读取beginmark所在行以后及endmark以前的内容，读取到StringBuffer
     * @param is InputStream
     * @param beginmark 起始标志
     * @param endmark 结束标志
     * @param enctype 文件输入编码方案（UTF-8、GBK、GB2312等等）
     * @return
     * @throws java.io.IOException
     */
    public static StringBuffer readTextFileByMark(FileInputStream is, String beginmark, String endmark)
            throws Exception {
        String enctype = getFileEncoding(is);
        UnicodeInputStream uin = new UnicodeInputStream(is, enctype);
        String chset = uin.getEncoding(); // check and skip possible BOM bytes
        System.out.println(chset);
        InputStreamReader isr;
        if (chset == null) {
            isr = new InputStreamReader(uin);
        } else {
            isr = new InputStreamReader(uin, chset);
        }

        BufferedReader bfrd = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer("");
        String rdline = null;
        boolean metbeginmark = false;
        while ((rdline = bfrd.readLine()) != null) {
            System.out.println(rdline);
            if (rdline.startsWith(beginmark)) {
                metbeginmark = true;
                break;
            } else {
                continue;
            }
        }
        while ((rdline = bfrd.readLine()) != null && metbeginmark) {
            System.out.println(rdline);
            if (!rdline.startsWith(endmark)) {
                sb.append(rdline);
                sb.append("\r\n");
            } else {
                break;
            }
        }
        isr.close();
        bfrd.close();
        return sb;
    }

    /**
     * @author X-Spirit
     * 在文本文件中读取prefix所在行的内容，从prefix后面取
     * @param is InputStream
     * @param prefix 整个文本中唯一的行前缀
     * @return
     * @throws java.io.IOException
     */
    public static StringBuffer readTextLineByPrefix(String filepath, String prefix)
            throws Exception {
        String enctype = getFileEncoding(filepath, "local");
        FileInputStream fis = new FileInputStream(filepath);
        UnicodeInputStream uin = new UnicodeInputStream(fis, enctype);
        String chset = uin.getEncoding(); // check and skip possible BOM bytes
        System.out.println(chset);
        InputStreamReader isr;
        if (chset == null) {
            isr = new InputStreamReader(uin);
        } else {
            isr = new InputStreamReader(uin, chset);
        }
        BufferedReader bfrd = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer("");
        String rdline = null;
        while ((rdline = bfrd.readLine()) != null) {
            if (rdline.startsWith(prefix)) {
                sb.append(rdline.substring(prefix.length()));
                return sb;
            }
        }
        isr.close();
        bfrd.close();
        return sb;
    }

    /**
     * @author X-Spirit
     * 将String写入文本文件
     * @param filepath   输出文件完整路径（包括文件名及扩展名）        
     * @param enctype    文件输出编码方案（UTF-8、GBK、GB2312等等）
     * @param textcontent  要写入的文本字符串
     * @return 
     * @throws IOException
     */
    public static void writeTextFile(String filepath, String enctype,
            String textcontent) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                filepath), enctype);
        BufferedWriter bfw = new BufferedWriter(osw);
        bfw.write(textcontent);
        bfw.close();
        osw.close();
    }

    /**
     * 生成随机文件名
     * 
     * @param 原文件名称
     * @return
     */
    public static String createtFileName(String oriFileName) {
        String exName = oriFileName.substring(oriFileName.lastIndexOf("."));
        java.util.Date dt = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = fmt.format(dt);
        fileName = fileName + exName; //extension,   you   can   change   it.   
        return fileName;
    }

    /**
     * @author X-Spirit
     * 读取网络文本文件
     * @param url
     * @return
     * @throws java.lang.Exception
     */
    public static StringBuffer readNetTextFile(String url) throws Exception {
        //猜测要读取文件的最可能字符集
        String chset = getFileEncoding(url, "net");

        //读取为文本
        URL netUrl = new URL(url);
        System.out.println("u.:" + FileUtility.charset);
        java.io.InputStream in = netUrl.openStream();
        UnicodeInputStream uin = new UnicodeInputStream(in, chset);
        chset = uin.getEncoding(); // check and skip possible BOM bytes
        System.out.println(chset);
        InputStreamReader inr;
        if (chset == null) {
            inr = new InputStreamReader(uin);
        } else {
            inr = new InputStreamReader(uin, chset);
        }
        BufferedReader bfrd = new BufferedReader(inr);
        StringBuffer sb = new StringBuffer("");
        String rdline = null;
        while ((rdline = bfrd.readLine()) != null) {
            sb.append(rdline);
            sb.append("\r\n");
        }
        inr.close();
        bfrd.close();
        return sb;
    }

    /**
     * 猜测要读取的文本文件可能的字符集编码方案，返回相应的字符集名称
     * @author X-Spirit
     * @param path 文本文件的路径（可以是本地或者网络）
     * @param mode （工作模式，net为网络，local为本地）
     * @return
     * @throws java.lang.Exception
     */
    public static String getFileEncoding(String path, String mode) throws Exception {
        InputStream is = null;
        if (mode.equalsIgnoreCase("local")) {
            is = new FileInputStream(path);
        } else if (mode.equalsIgnoreCase("net")) {
            URL netUrl = new URL(path);
            is = netUrl.openStream();
        }
        return getFileEncoding(is);
    }

    /**
     * 猜测要读取的InputStream可能的字符集编码方案，返回相应的字符集名称
     * @author X-Spirit
     * @param is 
     * @return
     * @throws java.lang.Exception
     */
    public static String getFileEncoding(InputStream is) throws Exception {
        // Initalize the nsDetector() ;
        int lang = nsPSMDetector.CHINESE;
        nsDetector det = new nsDetector(lang);

        det.Init(new nsICharsetDetectionObserver() {

            public void Notify(String charset) {
                FileUtility.found = true;
                FileUtility.charset = charset;
                System.out.println("CHARSET = " + charset);
            }
        });

        BufferedInputStream imp = null;
        imp = new BufferedInputStream(is);

        byte[] buf = new byte[1024];
        int len;
        boolean done = false;
        boolean isAscii = true;

        while ((len = imp.read(buf, 0, buf.length)) != -1) {

            // Check if the stream is only ascii.
            if (isAscii) {
                isAscii = det.isAscii(buf, len);            // DoIt if non-ascii and not done yet.
            }
            if (!isAscii && !done) {
                done = det.DoIt(buf, len, false);
            }
        }
        det.DataEnd();

        if (isAscii) {
            System.out.println("123 CHARSET = ASCII");
            found = true;
            FileUtility.charset = "ASCII";
        }

        if (!found) {
            String prob[] = det.getProbableCharsets();
            for (int i = 0; i < prob.length; i++) {
                System.out.println("Probable Charset = " + prob[i]);
            }
            FileUtility.charset = prob[0];
        }
        return FileUtility.charset;
    }

    /**
     * 将文件转换为byte数组
     *
     * @param File
     * @return
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);
            }
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "http://webservices.ctocio.com.cn/comment/181/7711681.shtml";
        String fileName = "E:/JiaHui/54_c_1.txt";
        try {
            String enctype = getFileEncoding(fileName, "local");
            StringBuffer strb = readTextLineByPrefix(fileName, "BOOKD");
            String enc = null;
            FileInputStream fis = new FileInputStream(fileName);
            //jcifs.Config.setProperty( "jcifs.netbios.wins", "192.168.2.10" );
            //NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("domain", "Administrator", "admin");
            //SmbFileInputStream sfis = new SmbFileInputStream(new SmbFile("smb://192.168.2.10/程序/Make/0/1/1_c_1.txt",auth));

            UnicodeInputStream uin = new UnicodeInputStream(fis, enc);
            enc = uin.getEncoding(); // check and skip possible BOM bytes
            System.out.println(enc);
            InputStreamReader in;


            if (enc == null) {
                in = new InputStreamReader(uin);
            } else {
                in = new InputStreamReader(uin, enc);
            }
            BufferedReader bfrd = new BufferedReader(in);
            StringBuffer sb = new StringBuffer("");
            String rdline = null;
            while ((rdline = bfrd.readLine()) != null) {
                sb.append(rdline);
                sb.append("\r\n");
            }

            in.close();
            bfrd.close();


            System.out.println(strb.toString());
            //System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到目录下所有文件或文件
     * @param picPath
     * @param list
     */
    public static List getOnDir(String picPath, boolean isFile) {
        List list = new ArrayList();
        File f = new File(picPath);
        if (f.exists() && f.isDirectory()) {
            File delFile[] = f.listFiles();
            for (int j = 0; j < f.listFiles().length; j++) {
                //文件
                if (isFile) {
                    if (delFile[j].exists() && delFile[j].isFile()) {
                        //log.info("############## pathinfo -----------"+ delFile[j].getPath());
                        list.add(delFile[j]);
                    }
                } else {//文件�
                    if (delFile[j].exists() && delFile[j].isDirectory()) {
                        //log.info("############## false pathinfo -----------"+ delFile[j].getPath());
                        list.add(delFile[j]);
                    }
                }

            }
        }
        return list;
    }

    /**
     * 生成ｊａｄ文件
     * @param sourceFile　原　MANIFEST.MF
     * @param newFile　　　生成　ｊａｄ
     * @param fjar　　　　ｊａｒ文件
     * @param filename　　包名
     */
    public static boolean createJadFile(String type, String sourceFile, String newFile, File fjar, String filename) {
        try {
            String tmpname = fjar.getName();
            if (fjar.getName().substring(fjar.getName().indexOf(".") + 1).equals("jar")) {
                JarFile jarIn = new JarFile(fjar);
                Manifest manifest = jarIn.getManifest();
                Attributes att = manifest.getMainAttributes();
                Set key = att.keySet();
                Iterator iterator = key.iterator();
                String midletName = "";
                String midlet_1 = "";
                String version = "";
                if (filename.indexOf("\n") > 0) {
                    filename = filename.substring(filename.indexOf("\n"));
                }
                while (iterator.hasNext()) {
                    String k = iterator.next().toString();
                    String v = (String) att.getValue(k);
                    if (k.equals("MIDlet-Name")) {
                        midletName = v;
                    }
                    if (k.equals("MIDlet-1")) {
                        midlet_1 = v;
                    }
                    if (k.equals("MIDlet-Version")) {
                        version = v;
                    }
                    if (Utility.isNotEmpty(midletName) && Utility.isNotEmpty(midlet_1) && Utility.isNotEmpty(version)) {
                        break;
                    }
                    //jad.println(k + ": " + v);

                }
                File fi = new File(sourceFile);
                if (fi.exists()) {
                    //判断如果是reader调用新方法
                    if (tmpname.indexOf("reader") > 0) {
                        createJad(fjar, newFile, orgJadContent(fjar));
                    } else {
                        createJad(fjar, newFile, midletName, midlet_1, version);
                    }
                    if ("Y".equals(type)) {
                        String contect = "MIDlet-Jar-Size: " + fjar.length() + "\n";
                        contect += "MIDlet-Jar-URL: " + filename;
                        addTextContect(newFile, contect);
                    }
                } else {
                    throw new Exception();
                }
                //去除文件结尾的换行符
                writeJad(newFile, newFile);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE,null, e);
            return false;
        }
        return true;
    }

    /**
     * 从META-INF 读取信息
     * @param fjar
     * @return
     * @throws Exception
     */
    private static String orgJadContent(File fjar) throws Exception {
        String result = "";
        JarFile jarIn = new JarFile(fjar);
        Manifest manifest = jarIn.getManifest();
        Attributes att = manifest.getMainAttributes();
        Set key = att.keySet();
        Iterator iterator = key.iterator();
        while (iterator.hasNext()) {
            String k = iterator.next().toString();
            String v = (String) att.getValue(k);
            if (k.equals("MIDlet-Permissions")) {
                result += "MIDlet-Permissions: javax.microedition.io.Connector.socket,javax.microedition.io.Connector.http";
            } else {
                result += k + ": " + v;
            }
            if (iterator.hasNext()) {
                result += "\n";
            }
        }
        result += "\n";
        return result;
    }

    private static void createJad(File jarfile, String newfile, String filecontect) throws Exception {
        //生成文件
        OutputStream out1 = new FileOutputStream(newfile);
        Writer out = new OutputStreamWriter(out1, "UTF-8");
        out.write(filecontect);
        out.flush();
        out.close();
    }

    /**
     * create jad
     * @param jarfile
     * @param newfile
     * @throws Exception
     */
    private static void createJad(File jarfile, String newfile, String midletName, String midlet_1, String version) throws Exception {
        String filecontect = "Manifest-Version: 1.0" + "\n";
        filecontect += "MIDlet-Name: " + midletName + "\n";
        filecontect += "MIDlet-Version: " + version + "\n";
        filecontect += "MIDlet-Vendor: Datuu.com" + "\n";
        filecontect += "MIDlet-Description: 手机大头(mini)" + "\n";
        filecontect += "MIDlet-Icon: /logo.png" + "\n";
        filecontect += "MIDlet-Info-URL: http://wap.datuu.com" + "\n";
        //filecontect += "MIDlet-1: 手机大头(mini),/logo.png,com.datuu.app.DTMain" + "\n";
        filecontect += "MIDlet-1: " + midlet_1 + "\n";
        filecontect += "MIDlet-Permissions: javax.microedition.io.Connector.socket,javax.microedition.io.Connector.http" + "\n";
        filecontect += "MicroEdition-Configuration: CLDC-1.1" + "\n";
        filecontect += "MicroEdition-Profile: MIDP-2.0" + "\n";
        //生成文件
        OutputStream out1 = new FileOutputStream(newfile);
        Writer out = new OutputStreamWriter(out1, "UTF-8");
        out.write(filecontect);
        out.flush();
        out.close();
    }

    /**
     * 将字符串内容追加到文件
     * @param path  文件路径
     * @param contect  追加内容
     * @throws IOException 
     * @throws Exception
     */
    public static void addTextContect(String path, String contect) throws IOException {
        //定义一个类RandomAccessFile的对象，并实例化
        RandomAccessFile rf = new RandomAccessFile(path, "rw");
        rf.seek(rf.length());//将指针移动到文件末尾
        rf.writeBytes(contect);
        rf.close();
    }

    /**
     * 生成jad文件，并写入信息
     * @param sourcefile
     * @param newfile
     * @throws Exception
     */
    private static void writeJad(String sourcefile, String newfile) throws Exception {
        InputStreamReader read = new InputStreamReader(new FileInputStream(new File(sourcefile)), "UTF-8");
        BufferedReader reader = new BufferedReader(read);
        StringBuffer s = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            s.append(line).append("\n");
        }

        String temp = s.toString();
        log.info("######:  writeJad : " + temp);
        if (temp.endsWith("\n")) {
            temp = temp.substring(0, temp.length() - 1);
        }
        //输出文件
        OutputStream out1 = new FileOutputStream(newfile);
        Writer out = new OutputStreamWriter(out1, "UTF-8");
        out.write(temp);
        out.flush();
    }
}
