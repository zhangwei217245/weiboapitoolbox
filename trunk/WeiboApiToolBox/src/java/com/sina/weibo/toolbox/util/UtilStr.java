package com.sina.weibo.toolbox.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import java.util.logging.Logger;

public final class UtilStr {

    public static final Logger log = Logger.getLogger(UtilStr.class.getName());
    public final static char[] BToA = "0123456789abcdef".toCharArray();

    private UtilStr() {
    }

    /**
     * 把16进制字符串转换成字节数组
     * 
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 把字节数组转换成16进制字符串
     * 
     * @param bArray
     * @return
     */
    public static String bytesToHexString(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 把字节数组转换为对象
     * 
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static final Object bytesToObject(byte[] bytes) throws IOException,
            ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(in);
        Object o = oi.readObject();
        oi.close();
        return o;
    }

    /**
     * 把可序列化对象转换成字节数组
     * 
     * @param s
     * @return
     * @throws IOException
     */
    public static final byte[] objectToBytes(Serializable s) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream ot = new ObjectOutputStream(out);
        ot.writeObject(s);
        ot.flush();
        ot.close();
        return out.toByteArray();
    }

    public static final String objectToHexString(Serializable s)
            throws IOException {
        return bytesToHexString(objectToBytes(s));
    }

    public static final Object hexStringToObject(String hex)
            throws IOException, ClassNotFoundException {
        return bytesToObject(hexStringToByte(hex));
    }

    /**
     * @函数功能: BCD码转为10进制串(阿拉伯数据)
     * @输入参数: BCD码
     * @输出结果: 10进制串
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
    }

    /**
     * @函数功能: 10进制串转为BCD码
     * @输入参数: 10进制串
     * @输出结果: BCD码
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }

        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;

        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }

            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    public static String BCD2ASC(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            int h = ((bytes[i] & 0xf0) >>> 4);
            int l = (bytes[i] & 0x0f);
            temp.append(BToA[h]).append(BToA[l]);
        }
        return temp.toString();
    }

    /**
     * MD5加密字符串，返回加密后的16进制字符串
     * 
     * @param origin
     * @return
     */
    public static String MD5EncodeToHex(String origin) {
        return bytesToHexString(MD5Encode(origin));
    }

    /**
     * MD5加密字符串，返回加密后的字节数组
     * 
     * @param origin
     * @return
     */
    public static byte[] MD5Encode(String origin) {
        return MD5Encode(origin.getBytes());
    }

    /**
     * MD5加密字节数组，返回加密后的字节数组
     * 
     * @param bytes
     * @return
     */
    public static byte[] MD5Encode(byte[] bytes) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            return md.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }

    }

    public String toUnicode(String strText, String code)
            throws UnsupportedEncodingException {
        char c;
        String strRet = "";
        int intAsc;
        String strHex;
        strText = new String(strText.getBytes("8859_1"), code);
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            if (intAsc > 128) {
                strHex = Integer.toHexString(intAsc);
                strRet = strRet + "&#x" + strHex + ";";
            } else {
                strRet = strRet + c;
            }
        }
        return strRet;
    }

    /**
     * 将字符串编码成 Unicode 。
     * 
     * @param theString
     *            待转换成Unicode编码的字符串。
     * @param escapeSpace
     *            是否忽略空格。
     * @return 返回转换后Unicode编码的字符串。
     */
    public static String toUnicode(String theString, boolean escapeSpace) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for (int x = 0; x < len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            switch (aChar) {
                case ' ':
                    if (x == 0 || escapeSpace) {
                        outBuffer.append('\\');
                    }
                    outBuffer.append(' ');
                    break;
                case '\t':
                    outBuffer.append('\\');
                    outBuffer.append('t');
                    break;
                case '\n':
                    outBuffer.append('\\');
                    outBuffer.append('n');
                    break;
                case '\r':
                    outBuffer.append('\\');
                    outBuffer.append('r');
                    break;
                case '\f':
                    outBuffer.append('\\');
                    outBuffer.append('f');
                    break;
                case '=': // Fall through
                case ':': // Fall through
                case '#': // Fall through
                case '!':
                    outBuffer.append('\\');
                    outBuffer.append(aChar);
                    break;
                default:
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >> 8) & 0xF));
                        outBuffer.append(toHex((aChar >> 4) & 0xF));
                        outBuffer.append(toHex(aChar & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    /**
     * 从 Unicode 码转换成编码前的特殊字符串。
     * 
     * @param in
     *            Unicode编码的字符数组。
     * @param off
     *            转换的起始偏移量。
     * @param len
     *            转换的字符长度。
     * @param convtBuf
     *            转换的缓存字符数组。
     * @return 完成转换，返回编码前的特殊字符串。
     */
    public String fromUnicode(char[] in, int off, int len, char[] convtBuf) {
        if (convtBuf.length < len) {
            int newLen = len * 2;
            if (newLen < 0) {
                newLen = Integer.MAX_VALUE;
            }
            convtBuf = new char[newLen];
        }
        char aChar;
        char[] out = convtBuf;
        int outLen = 0;
        int end = off + len;

        while (off < end) {
            aChar = in[off++];
            if (aChar == '\\') {
                aChar = in[off++];
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = in[off++];
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed \\uxxxx encoding.");
                        }
                    }
                    out[outLen++] = (char) value;
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    out[outLen++] = aChar;
                }
            } else {
                out[outLen++] = (char) aChar;
            }
        }
        return new String(out, 0, outLen);
    }

    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    public static String transform(String str, boolean bool) {
        String result = "";
        StringBuffer tch = null;
        if (str.length() < 1) {
            return result;
        } else {
            tch = new StringBuffer(1000);
        }
        for (int i = 0; i < str.length(); i++) {
            (new Integer((int) str.charAt(0))).toString();
            char aChar = (char) str.charAt(i);
            boolean flag = true;
            if (bool) {
                if (((aChar > 61) && (aChar < 127)) || aChar == '.') {
                    tch.append(new StringBuffer("\\u0"));
                } else {
                    tch.append(new StringBuffer("\\u0"));
                    flag = false;
                }
            }
            if (flag) {
                tch.append(new StringBuffer("\\u"));
                tch.append(new StringBuffer(new String(Integer.toHexString(aChar))));
            }
        }
        return tch.toString();
    }

    public void test(String[] args) {

        int len = args[0].length();
        String[] s = new String[len];

        for (int i = 0; i < len; i++) {
            char c = args[0].charAt(i);
            s[i] = Integer.toString(c, 16);
            System.out.println(c + "\t\\u" + s[i]);
        }

        System.out.println();
        for (int i = 0; i < s.length; i++) {
            char c = (char) Integer.valueOf(s[i], 16).intValue();
            System.out.println("\\u" + s[i] + "\t" + c);
        }

        Object o = new Object() {

            public String toString() {
                return "";
            }
        };

        System.out.println(resolveName("res/btn.gif"));
    }

    public String resolveName(String name) {
        if (name == null) {
            return name;
        }
        if (!name.startsWith("/")) {
            Class c = this.getClass();
            while (c.isArray()) {
                System.out.println("is   array.");
                c = c.getComponentType();
            }
            String baseName = c.getName();
            int index = baseName.lastIndexOf('.');
            if (index != -1) {
                name = baseName.substring(0, index).replace('.', '/') + "/" + name;
            }
        } else {
            name = name.substring(1);
        }
        return name;
    }
    private static final char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static void main(String args[]) throws UnsupportedEncodingException {

        //UtilStr util = new UtilStr();
        //String s = "abcedfghigklmnopqrstuvwxyz";
        //String s = "ABCDEFGHIGKLMNOPQRSTUVWXYZabcedfghigklmnopqrstuvwxyz";
        //System.out.println(s.getBytes());
        //byte[] b = {108, 9, 103, 16, 98, 72, 78, 72, 78};
        //System.out.println(bytesToHexString("爱情".getBytes()));
//		System.out.println(util.transform("我爱大头", false));
//		System.out.println(util.transform("我爱大头", false).getBytes());
//		System.out.println("\u0061\u6211\u7231\u5927\u5934");
        //System.out.println("\u5934\u5927\u7231\u6211");
        //System.out.println(System.getProperty("file.encoding"));
        //iso2gbk(b);
//		Utility.createJadFile("Y","c:\\datuu(mini)-moto-E6-390.jar","c:\\datuu(mini)-moto-E6-390.jad",
//				new File("c:\\datuu(mini)-moto-E6-390.jar"),"datuu(mini)-moto-E6-390.jar");
        System.out.println(UtilStr.transform("datuu.dat,,", true));
    }

    public static void iso2gbk(byte[] b) throws UnsupportedEncodingException {
        byte[] gbk = new byte[b.length];
        StringBuffer sb = new StringBuffer("");
        String[] strb = bytesToHexString(b).split(":");
        for (int i = strb.length - 1, f = 0; i >= 0 && f < strb.length; i--, f++) {
            gbk[f] = Byte.valueOf(strb[i]);
        }

        for (int i = 0; i < gbk.length; i++) {
            sb.append("\\u");
            sb.append(gbk[i]);
            sb.append(gbk[++i]);
        }
        String str = "";
        String newStr = unicodeToGB(sb.toString());
        for (int i = newStr.length(); i > 0; i--) {
            str += newStr.substring(i - 1, i);
        }
        System.out.print(str);
    }

    private static String unicodeToGB(String s) {
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(s, "\\u");
        while (st.hasMoreTokens()) {
            sb.append((char) Integer.parseInt(st.nextToken(), 16));
        }
        return sb.toString();
    }

    /**
     * 将非ASCII字符转换为UTF-8字符串
     * @author X-Spirit
     * @param src
     * @return
     */
    public static String toUtf8(String src) {
        byte[] b = src.getBytes();
        char[] c = new char[b.length];
        for (int i = 0; i < b.length; i++) {
            c[i] = (char) (b[i] & 0x00FF);
        }
        return new String(c);
    }
}
