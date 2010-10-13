package com.sina.weibo.toolbox.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class CreatePropertyFile {

    public static final String PRODUCT_CATENA_KEY = "pc";
    public static final String CLIENT_FULLNAME_KEY = "cf";
    public static final String CLIENT_SHORTNAME_KEY = "cs";
    public static final String BACKGROUND_COLOR_KEY = "bc";
    public static final String BACKGROUND_UPPER_KEY = "bu";
    public static final String BACKGROUND_LINECOLOR_KEY = "bl";
    public static final String PRODUCT_MISC_KEY = "pm";

    public void makeFile(String[][] properties, String filename, String type) {

        DataOutputStream dot;

        try {

            OutputStream out = new FileOutputStream("/" + filename);
//            java.io.FileWriter dot=new java.io.FileWriter("/" + filename);

            dot = new DataOutputStream(out);

            dot.writeInt(properties.length * 2);

            for (int i = 0; i < properties.length; i++) {

                if (properties[i][0] == null || properties[i][0].equals("")) {
                    properties[i][0] = " ";
                }
                if (properties[i][1] == null || properties[i][1].equals("")) {
                    properties[i][1] = " ";
                }
                if (type.equals("sis")) {
                    dot.writeUTF(encode(properties[i][0]));
                    dot.writeUTF(encode(properties[i][1]));
                } else {
                    dot.writeUTF(encode(properties[i][0]));
                    dot.writeUTF(encode(properties[i][1]));
                }
            }

            dot.close();

        } catch (IOException ie) {

            ie.printStackTrace();

        }

    }

    public void makeFileNo(String[] properties, String filename) {

        DataOutputStream dot;

        try {

            OutputStream out = new FileOutputStream("/" + filename);

            dot = new DataOutputStream(out);

            dot.writeInt(properties.length);

            for (int i = 0; i < properties.length; i++) {

                if (properties[i].equals("")) {

                    properties[i] = " ";
                }

                dot.writeUTF(properties[i]);

            }

            dot.close();

        } catch (IOException ie) {

            ie.printStackTrace();

        }

    }

    public String encode(String str) {

        char[] data = str.toCharArray();

        for (int i = 0; i < str.length(); i++) {

            if (data[i] != ' ') {

                data[i] += 1;
            }

        }

        return String.valueOf(data);

    }

    public String decode(String str) {

        char[] data = str.toCharArray();

        for (int i = 0; i < str.length(); i++) {

            if (data[i] != ' ') {

                data[i] -= 1;
            }

        }

        return String.valueOf(data);

    }

    //ʾ��
    public static void main(String args[]) throws IOException {
        /*String old = "E:\\svn\\unite\\webapp\\oem\\buildreource\\mini\\4.0.1\\moto\\datuu(mini)-Motorola-E6\\META-INF\\MANIFEST.MF";
        String newstr = "c:\\MANIFEST.MF";
        String filepath = "E:\\svn\\unite\\webapp\\oem\\16125\\moto-3\\datuu(mini)-moto-E6.jar";
        new Utility().createJadFile("Y",old,newstr,new File(filepath),"datuu(mini)-moto-E6.jar");*/
        /*    	DataOutputStream dot;
        OutputStream out = new FileOutputStream("c:/aa.txt");

        dot = new DataOutputStream(out);
        
        dot.writeUTF("中国人");

        dot.close();
        System.out.println("over");*/
        String properties[][] = new String[3][2];

        //����������
        properties[1][0] = CreatePropertyFile.CLIENT_FULLNAME_KEY;
        properties[1][1] = "mini";

        //���к�
        properties[0][0] = CreatePropertyFile.PRODUCT_CATENA_KEY;
        properties[0][1] = "J";

        //wap�ƹ�
        properties[2][0] = CreatePropertyFile.PRODUCT_MISC_KEY;
        properties[2][1] = "25707";

        new CreatePropertyFile().makeFile(properties, "c:/datuu", "");
        try {
            //new CreatePropertyFile().copyTextFile("c:\\MANIFEST.MF", "d:\\aa.jad");
            //createJadFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("over");

    }

    /**
     * 生成JAD文件
     * @throws Exception
     */
    private static void createJadFile() throws Exception {
        /*String filecontect = "MIDlet-Name: datuu(mini)"+"\r\n";
        filecontect += "MIDlet-Version: 1.0.0" + "\r\n";
        filecontect += "MIDlet-Vendor: Datuu.com" + "\r\n";
        filecontect += "MIDlet-Jar-URL: http://dl.datuu.com/oem/100/se-2/datuu(mini)-se-K790-132.jar?o=100" + "\r\n";
        filecontect += "MIDlet-Jar-Size: 300608" + "\r\n";
        filecontect += "MIDlet-Description: 测试环境" + "\r\n";
        //filecontect += "MIDlet-Icon: /logo.png" + "\r\n";
        filecontect += "MIDlet-Info-URL: http://wap.datuu.com" + "\r\n";
        filecontect += "MIDlet-1: 测试环境,/logo.png,com.datuu.app.DTMain" + "\r\n";
        filecontect += "MIDlet-Permissions: javax.microedition.io.Connector.socket,javax.microedition.io.Connector.http" + "\r\n";*/
        //String aa = new String(test().getBytes("UTF-8"));
        //createFile("d:/aa.jad", "");
    	/*String properties[][] = new String[9][2];
        properties[0][0] = "MIDlet-Name:";
        properties[0][1] = "datuu(mini)";

        properties[1][0] = "MIDlet-Version:";
        properties[1][1] = "1.0.0";

        properties[2][0] = "MIDlet-Vendor:";
        properties[2][1] = "Datuu.com";

        properties[3][0] = "MIDlet-Jar-URL:";
        properties[3][1] = "http://dl.datuu.com/oem/100/se-2/datuu(mini)-se-K790-132.jar";

        properties[4][0] = "MIDlet-Jar-Size:";
        properties[4][1] = "247024";

        properties[5][0] = "MIDlet-Description:";
        properties[5][1] = "测试环境";

        properties[6][0] = "MIDlet-Info-URL:";
        properties[6][1] = "http://wap.datuu.com";

        properties[7][0] = "MIDlet-1:";
        properties[7][1] = "测试环境,/logo.png,com.datuu.app.DTMain";

        properties[8][0] = "MIDlet-Permissions:";
        properties[8][1] = "javax.microedition.io.Connector.socket,javax.microedition.io.Connector.http";

        new CreatePropertyFile().makeFile(properties,"c:\\se.jad","");*/

        System.out.println("create file over!");
    }

    public static void createFile(String path, String newByte) throws Exception {
        /*File tempFile = new File(path);
        //BufferedWriter out = new BufferedWriter(new FileWriter(new File(path)));
        OutputStream out = new FileOutputStream(tempFile, false);
        out.write(newByte);
        out.flush();
        out.close();*/

        DataOutputStream dot;
        OutputStream out = new FileOutputStream(path);

        dot = new DataOutputStream(out);

        dot.writeUTF(newByte);

        dot.close();
        try {
            //new CreatePropertyFile().copyTextFile("c:\\MANIFEST.MF", "d:\\aa.jad");
            //JarInputStream jarIn = new JarInputStream(new FileInputStream("c:\\datuu(mini)-Sony-Ericsson-K790.jar"));
        	/*JarFile jarIn = new JarFile(new File("c:\\datuu(mini)-Sony-Ericsson-K790.jar"));
            Manifest manifest = jarIn.getManifest();

            JarEntry entry = jarIn.getJarEntry();
            JarOutputStream jarOut = new JarOutputStream(new FileOutputStream("d:\\aa.jad"),	manifest);

            //        	创建一个读缓冲以便从输入处传输数据
            byte[] buf = new byte[4096];

            //while ((entry = jarIn.getNextJarEntry()) != null) {
            //        	从旧JAR中排出清单文件
            if ("META-INF/MANIFEST.MF".equals(entry.getName()))
            //continue;
            //        	为输出JAR写一个入口
            jarOut.putNextEntry(entry);
            int read;
            //	        	while ((read = jarIn.read(buf)) != -1) {
            //	        		jarOut.write(buf, 0, read);
            //	        	}
            jarOut.closeEntry();
            //}
             */

            /*   OutputStream out = new FileOutputStream(path);

            dot = new DataOutputStream(out);
            
            dot.writeUTF(newByte);

            dot.close();*/
            /*PrintWriter jad = new PrintWriter( new FileOutputStream(path));
            jad.write(newByte);
            jad.close();*/
            /*String aa = "MIDlet-Jar-URL: datuu(mini)-se-K790-132.jar" + "\r\n";
            aa += "MIDlet-Jar-Size: 299473";
            FileWriter fw=new FileWriter(path,true);
            fw.write(aa+"\r\n");
            fw.close();*/

            /* RandomAccessFile rf=new RandomAccessFile(path,"rw");
            //定义一个类RandomAccessFile的对象，并实例化
            rf.seek(rf.length());//将指针移动到文件末尾
            rf.writeBytes(aa);
            rf.close();//关闭文件流

            /*FileReader fr=new FileReader(path + "\\WriteData.txt");
            BufferedReader br=new BufferedReader(fr);//读取文件的BufferedRead对象
            String Line=br.readLine();
            while(Line!=null){
            Line=br.readLine();
            }
            fr.close();//*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String test() throws Exception {
        File file = new File("c:\\datuu(mini)-Sony-Ericsson-K790.jar");
        if (!file.exists()) {
            System.exit(0);
        }
        String aa = "";
        try {

            JarFile jar = new JarFile(file);
            Enumeration teset = jar.entries();

            jar.getManifest();
            Manifest mf = jar.getManifest();
            Attributes att = mf.getMainAttributes();
            Set tmp = att.entrySet();
            //PrintWriter jad = new PrintWriter( new FileOutputStream("c:\\simple.jad"));
            Set key = att.keySet();
            for (Iterator ite = att.keySet().iterator(); ite.hasNext();) {
            }
            Iterator iterator = key.iterator();
            while (iterator.hasNext()) {

                String k = iterator.next().toString();
                String v = (String) att.getValue(k);
                //jad.println(k + ": " + v);
                aa += k + ": " + v + "\r\n";
            }
            //jad.println("MIDlet-Jar-Size: "+file.length());
            //jad.println("MIDlet-Jar-URL: datuu(mini)-se-K790-132.jar");
            aa += "MIDlet-Jar-Size: " + file.length() + "\r\n";
            aa += "MIDlet-Jar-URL: datuu(mini)-Sony-Ericsson-K790.jar" + "\r\n";
            //jad.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aa;

    }
}
