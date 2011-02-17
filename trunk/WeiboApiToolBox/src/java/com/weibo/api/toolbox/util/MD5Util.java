/**
 * 
 */
package com.weibo.api.toolbox.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tangfulin
 * 
 */
public class MD5Util {

    public static final int hexbase = 16;

    public static String md5Digest(final String data) {
        return md5Digest(data.getBytes(Charset.forName("UTF-8")));
    }

    public static String md5Digest(final byte[] data) {
        String rst = null;
        try {
            final MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data);
            final byte[] digest = md5.digest();
            rst = encodeHex(digest);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MD5Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rst;
    }

    public static String encodeHex(final byte[] bytes) {
        final StringBuilder buf = new StringBuilder(bytes.length + bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static long hexdec(final String hex) {
        long num = 0;
        char c;
        for (int i = 0; i < hex.length(); ++i) {
            c = hex.charAt(i);
            num = num * hexbase + c;
        }
        return num;
    }

    /**
     * @param args
     */
    public static void main(final String[] args) {
        String pwd = "admin";
        System.out.println(md5Digest(pwd));
    }
}
