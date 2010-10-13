package com.sina.weibo.toolbox.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.*;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import java.util.logging.Logger;

public class Utility {

    public static final Logger log = Logger.getLogger(Utility.class.getName());
    private static String csResourcePath = "com.vv.auth.struts.ApplicationResource";
    private static Locale userLocale;

    public static String getRequestParameterAsString(HttpServletRequest request) {
        Map paramap = request.getParameterMap();
        StringBuffer sb = new StringBuffer();
        if (!paramap.isEmpty()) {
            for (Object o : paramap.keySet()) {
                String[] paramvalue = (String[]) paramap.get(o);
                sb.append("[" + o.toString() + ": (");
                for (String spara : paramvalue) {
                    sb.append(spara).append(",");
                }
                sb.setCharAt(sb.lastIndexOf(","), ')');
                sb.append("]");
            }
        } else {
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                isr = new InputStreamReader(request.getInputStream());
                br = new BufferedReader(isr);
                String a = null;
                while ((a = br.readLine()) != null) {
                    sb.append(a);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sb.append(e.getLocalizedMessage());
            } finally {
                try {
                    isr.close();
                    br.close();
                } catch (Exception e) {
                }
            }
        }
        String result = null;
        if (sb.length() >= 2000) {
            result = sb.substring(0, 1999);
        } else {
            result = sb.toString();
        }
        return result;
    }

    public static Integer getCurrSessionUserid(HttpServletRequest request) {
        Map userMap = (Map) request.getSession().getAttribute("USERMAP");
        if (userMap != null && userMap.size() > 0) {
            Integer userid = (Integer) userMap.get("userid");
            return userid;
        } else {
            return null;
        }

    }

    public static String getResourcePath() {
        return csResourcePath;
    }

    public static String getMessage(String asKey) {
        return getMessage(asKey, getUserLocale());
    }

    public static String getMessage(String asKey, String asLangCd) {
        return getMessage(asKey, getUserLocale(asLangCd));
    }

    public static String getMessage(String asKey, Locale asLocale) {
        ResourceBundle bundle = ResourceBundle.getBundle(Utility.getResourcePath(), asLocale);
        return bundle.getString(asKey);
    }

    public static String getFormatDouble(double aoValue, String asPattern) {
        DecimalFormat lDecimalFormat = new DecimalFormat(asPattern);
        return lDecimalFormat.format(aoValue);
    }

    /**
     * Gary: get user locale from langcd
     * @param asLangCd
     * @return
     */
    public static Locale getUserLocale(String asLangCd) {
        Locale lsUserLocale = Locale.ENGLISH;
        if (asLangCd.equals("zht")) {
            lsUserLocale = Locale.TRADITIONAL_CHINESE;
        } else if (asLangCd.equals("zhs")) {
            lsUserLocale = Locale.SIMPLIFIED_CHINESE;
        } else if (asLangCd.equals("en")) {
            lsUserLocale = Locale.ENGLISH;
        }

        return lsUserLocale;
    }

    public static Locale getUserLocale() {
        if (userLocale == null) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        return userLocale;
    }

    public static java.util.Date convertStringToDate(String asDate, String asPattern) {
        SimpleDateFormat format = new SimpleDateFormat(asPattern);
        try {
            return format.parse(asDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateToString(java.util.Date asDate, String asPattern) {
        SimpleDateFormat format = new SimpleDateFormat(asPattern);
        try {
            return format.format(asDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param asDate
     * @param asPattern
     * @return
     */
    public static Timestamp convertStringToTimeStamp(String asDate, String asPattern) {
        java.sql.Timestamp lStamp = null;
        if (asDate == null || asDate.length() == 0) {
            lStamp = new java.sql.Timestamp(System.currentTimeMillis());
        } else {
            if (asPattern == null || asPattern.length() == 0) {
                try {
                    lStamp = java.sql.Timestamp.valueOf(asDate);
                } catch (Exception e) {
                    lStamp = new java.sql.Timestamp(System.currentTimeMillis());
                }
            } else {
                try {
                    SimpleDateFormat lFormat = new SimpleDateFormat(asPattern);
                    lStamp = new java.sql.Timestamp(lFormat.parse(asDate).getTime());
                } catch (Exception e) {
                    lStamp = new java.sql.Timestamp(System.currentTimeMillis());
                }
            }
        }
        return lStamp;
    }

    /**
     *
     * @param asDate
     * @param asPattern
     * @return
     */
    public static String convertTimeStampToString(java.util.Date asDate, String asPattern) {
        if (asDate == null) {
            return "";
        }
        SimpleDateFormat lFormat = new SimpleDateFormat();
        if ((asPattern != null) && (asPattern.length() > 0)) {
            lFormat.applyPattern(asPattern);
        }
        return lFormat.format(asDate);
    }

    /**
     *
     * @param adDate
     * @param asPattern
     * @return
     */
    public static String GetFormattedDate(java.sql.Date adDate, String asPattern) {
        String lstDate = "";
        if (adDate != null) {
            SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat();
            lSimpleDateFormat.applyPattern(asPattern);
            lstDate = lSimpleDateFormat.format(adDate);
        } else {
            lstDate = "";
        }
        return lstDate;
    }

    /**
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean greatEqual(Date date1, Date date2) {
        if (date1.after(date2)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean littleEqual(Date date1, Date date2) {
        if (date1.before(date2)) {
            return false;
        } else {
            return true;
        }
    }

    public static String[] getErrorChar() {
        String[] strs = {"//", "\\", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "[", "]", "{", "}", ";", ":", "\"", "'", ",", ".", "<", ">", "?", "`", "~"};
        return strs;
    }


    /**
     *
     * @param menus
     * @param userPermission
     * @return
     */
    public static List getUserMenu(List menus, Map userPermission) throws Exception {
        //log.info("########### menu begin");
        List list = new ArrayList();
        for (int i = 0; i < menus.size(); i++) {
            Map lm = (Map) menus.get(i);
            StringBuffer url = new StringBuffer();
            StringBuffer child = new StringBuffer();
            if (lm.get("childs") == null) {
                if (userPermission.get(lm.get("action") + "") != null) {
                    list.add(lm.get("action") + "");
                }
            } else {
                if (lm.get("childs") != null) {
                    List listChild = (List) lm.get("childs");
                    for (int f = 0; f < listChild.size(); f++) {
                        Map lmChild = (Map) listChild.get(f);
                        if (userPermission.get(lmChild.get("action") + "") != null) {
                            list.add(lmChild.get("action") + "");
                        }
                    }
                }
            }
        }
        //log.info("############## menu:"+list.get(0));
        //log.info("########### menu end");
        return list;
    }

    /**
     *
     * @param keyWord
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String iso2utf(String keyWord) throws UnsupportedEncodingException {
        String keyWordTemp = new String(keyWord.getBytes("iso-8859-1"), "utf-8");
        if (!keyWord.equals(keyWordTemp)) {
            byte[] bytes = keyWord.getBytes("iso-8859-1");
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] < 0) {
                    keyWord = new String(keyWord.getBytes("iso-8859-1"), "utf-8");
                    break;
                }
            }
        }
        return keyWord;
    }

    /**
     *
     * @param keyWord
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String utf2iso(String keyWord) throws UnsupportedEncodingException {
        String keyWordTemp = new String(keyWord.getBytes("utf-8"), "gb2312");
        if (!keyWord.equals(keyWordTemp)) {
            byte[] bytes = keyWord.getBytes("utf-8");
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] < 0) {
                    keyWord = new String(keyWord.getBytes("utf-8"), "gb2312");
                    break;
                }
            }
        }
        return keyWord;
    }

    public static String iso2gbk(String keyWord) {
        String keyWordTemp = "";
        try {
            keyWordTemp = new String(keyWord.getBytes("ISO-8859-1"), "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return keyWordTemp;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean hasElement(Object[] a,Object elem){
        if(isNotEmpty(a)){
            for(Object o: a){
                if(elem==null&&o==null){
                    return true;
                }else if(elem.equals(o)){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean isEmpty(Object[] a){
        return ((a == null) || (a.length == 0));
    }

    public static boolean isEmpty(String s) {
        return ((s == null) || (s.length() == 0));
    }

    public static boolean isEmpty(Collection c) {
        return ((c == null) || (c.size() == 0));
    }

    public static boolean isNotEmpty(Object[] a){
        return ((a != null) && (a.length > 0));
    }

    public static boolean isNotEmpty(String s) {
        return ((s != null) && (s.length() > 0));
    }

    public static boolean isNotEmpty(Collection c) {
        return ((c != null) && (c.size() > 0));
    }

    /**
     * 鐏忓棗鐡х粭锔胯鏉烆剚宕查幋鎭杋st
     * @param str
     * @param delimiter
     * @return
     */
    public static List strToList(String str, String delimiter) {
        StringTokenizer token = new StringTokenizer(str, delimiter, false);
        List result = new ArrayList();
        while (token.hasMoreElements()) {
            result.add((String) token.nextElement());
        }
        return result;
    }

    
    

    /**
     * 閸掓稑缂撻惄顔肩秿
     * @param path
     * @return
     */
    private void createFileDir(String path) {

        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

    }

    /**
     * 閸掔娀娅庨弬鍥︽
     * @param path
     * @return
     */
    public void deleteFile(String path) throws IOException {

        File f = new File(path);//鐎规矮绠熼弬鍥︽鐠侯垰绶�
        if (f.exists() && f.isDirectory()) {//閸掋倖鏌囬弰顖涙瀮娴犳儼绻曢弰顖滄窗閿熻棄缍�
            if (f.listFiles().length == 0) {//閼汇儳娲拌ぐ鏇氱瑓濞屸剝婀侀弬鍥︽閸掓瑧娲块幒銉ュ灩閿熶粙娅�
                f.delete();
            } else {//閼汇儲婀侀崚娆愬Ω閺傚洣娆㈤弨鎹愮箻閺佹壆绮嶉敍灞借嫙閸掋倖鏌囬弰顖氭儊閺堝绗呯痪褏娲伴敓钘夌秿
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        deleteFile(delFile[j].getAbsolutePath());//闁帒缍婄拫鍐暏del閺傝纭堕獮璺哄絿瀵版鐡欓惄顔肩秿鐠侯垰绶�
                    }
                    delFile[j].delete();//閸掔娀娅庨弬鍥︽
                }
            }
            deleteFile(path);//闁帒缍婄拫鍐暏
        } else if (f.exists() && f.isFile()) {
            f.delete();
        }

    }

    /**
     * 瀵版鍩岄幓蹇氬牚娣団剝浼�
     * @param obj
     * @return
     */
    public static String invokeGetter2String(Object obj) {

        String content = "";
        Method[] fields = obj.getClass().getMethods();

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().indexOf("get") >= 0) {
                try {
                    if ((!"getWriteStr".equals(fields[i].getName())) && (!"getWriteStr2".equals(fields[i].getName()))) {
                        content += (fields[i].getName() + " " + fields[i].invoke(obj, new Object[]{}) + "\n");
                    }
                } catch (IllegalArgumentException e) {
                    log.log(Level.SEVERE,null, e);
                } catch (IllegalAccessException e) {
                    log.log(Level.SEVERE,null, e);
                } catch (InvocationTargetException e) {
                    log.log(Level.SEVERE,null, e);
                }
            }
        }
        return content;

    }

    /**
     * 瀵版鍩宲artnerid
     * @param request
     * @return
     */
    public static String getNumpartnerid(HttpServletRequest request) throws Exception {
        HttpSession ses = request.getSession(false);
        if (ses.getAttribute("USERMAP") != null && ((Map) ses.getAttribute("USERMAP")).get("numpartnerid") != null) {
            return ((Map) ses.getAttribute("USERMAP")).get("numpartnerid") + "";
        }
        return "";
    }

    /**
     * 灏嗕俊鎭緭鍏ヨ嚦椤甸潰
     * @param lstTree
     * @param response
     * @param sbf
     * @throws Exception
     */
    public static void outPrintTree(HttpServletResponse response, String sbf) throws Exception {
        PrintWriter out = null;
        response.setContentType("application/xml; charset=UTF-8");
        try {
            out = response.getWriter();
            int srcBegin = 0;
            int srcEnd = sbf.length() >= 1024 ? 1024 : sbf.length();
            while (srcEnd != 0) {
                out.write(sbf.substring(srcBegin, srcEnd));
                sbf = sbf.substring(srcEnd);
                srcEnd = sbf.length() >= 1024 ? 1024 : sbf.length();
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            log.log(Level.SEVERE,null, e);
        } finally {
            out.close();
        }
    }

    public static String listToXml(List lstResult) throws Exception {
        String result = "";
        if (Utility.isNotEmpty(lstResult)) {
            StringBuffer sb = new StringBuffer();
            sb.append("<TreeHeader>");
            for (int i = 0; i < lstResult.size(); i++) {
                Map lmvalue = (Map) lstResult.get(i);
                sb.append("<TreeNode>");
                for (Iterator ite = lmvalue.keySet().iterator(); ite.hasNext();) {
                    String key = ite.next().toString();
                    sb.append("<" + key + ">");
                    try {
                        sb.append(JavaEscape.escape((lmvalue.get(key) + "")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sb.append("</" + key + ">");
                }
                sb.append("</TreeNode>");
            }
            sb.append("");
            sb.append("</TreeHeader>");
            result = sb.toString();
        }
        return result;
    }

    /**鑾峰彇鍖呭惈璇锋眰涓婁笅鏂囪矾寰勭殑瀹屾暣URL澶村瓧涓�
     * @param request
     * @return String
     * @throws Exception
     */
    public static String getRequestUrlHeader(HttpServletRequest request) throws Exception {
        String urlheader = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        return urlheader;
    }

    /**
     * 鏍煎紡鍖栧瓧绗︿覆杩囨护鐗规畩绗﹀彿
     * @param contentStr
     * @return
     */
    public static String formatValue(String contentStr) {
        if (contentStr == null) {
            return contentStr;
        }
        contentStr = contentStr.replaceAll("'", "''");
        char[] charArray = contentStr.toCharArray();
        StringBuffer strBuffer = new StringBuffer();
        for (char aChar : charArray) {
            if (aChar < 0x20 && aChar != '\t' && aChar != '\n' && aChar != '\r') {
                strBuffer.append(" ");
            } else {
                strBuffer.append(aChar);
            }
        }
        return strBuffer.toString();
    }

    /**
     * 澶嶅埗Map
     * @param lmold
     * @return
     */
    public static Map cloneMap(Map lmold) {
        Map lmres = new HashMap();
        if (lmold != null && lmold.size() > 0) {
            for (Iterator it = lmold.keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                Object obj = (Object) lmold.get(key);
                lmres.put(key, obj);
            }
        }
        return lmres;
    }

    /**
     * @author author X-Spirit
     * 根据正则表达式对论坛ID进行处理，以方便当前项目中架构树的使用
     * @param oid  从数据库中取出的32位长的论坛ID
     * @param regexp 正则表达式，目前推荐使用^[0]+
     * @return 返回Map&lt;String,String&gt;,用bbsid得到论坛架构ID，用lastindex得到越过的0的个数。
     */
    public static Map<String, String> truncateBbsId(String oid, String regexp) {
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(oid);
        HashMap<String, String> resmap = new HashMap<String, String>();
        boolean b = m.find();
        int lidx = oid.length() - 1;
        if (b) {
            lidx = m.end();
        }
        //System.out.println(lidx);
        String nid = oid.substring(lidx, oid.length());
        resmap.put("bbsid", nid);
        resmap.put("lastindex", String.valueOf(lidx));
        return resmap;
    }

    /**
     * 根据数据库中的论坛架构ID长度补全已经截取的新ID
     * @param nid  已经过截断操作的ID
     * @param olen 数据库中的ID总长
     * @return
     */
    public static String fillBbsId(String nid, int olen) {
        StringBuffer sb = new StringBuffer();
        if (nid != null) {
            int zerolen = olen - nid.length();
            for (int i = 0; i < zerolen; i++) {
                sb.append("0");
            }
        }
        sb.append(nid);
        return sb.toString();
    }

    public static void main(String args[]) {
        Map<String, String> i = truncateBbsId("00000000000000000000000000009004", "^[0]+");
        String s = i.get("bbsid");
        String l = i.get("lastindex");
        String o = fillBbsId(s, 32);
        System.out.println(i);
        System.out.println(o + "," + o.length());
    }

    public static int powBinary(String binary) {
        double n = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                n = n + Math.pow(2, binary.length() - i - 1);
            }
        }
        return (int) n;
    }

    public static String contentToBinary(String content) {
        char[] chars = {'0', '0', '0', '0', '0', '0', '0', '0'};
        if (content.contains("<txt>")) {
            chars[7] = '1';
        }
        if (content.contains("<pic>")) {
            chars[6] = '1';
        }
        if (content.contains("<att>")) {
            chars[5] = '1';
        }
        return String.valueOf(chars);
    }

    public static String encode(String origin) {
        char[] utfBytes = origin.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < utfBytes.length; i++) {
            if (utfBytes[i] == '&') {
                stringBuffer.append("&amp;");
                continue;
            }
            if (utfBytes[i] == '<') {
                stringBuffer.append("&lt;");
                continue;
            }
            if (utfBytes[i] == '>') {
                stringBuffer.append("&gt;");
                continue;
            }
            if (utfBytes[i] == '\\') {
                stringBuffer.append("&quot;");
                continue;
            }
            if (utfBytes[i] == '\'') {
                stringBuffer.append("&apos;");
                continue;
            }
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() == 2 && utfBytes[i] > 127) {
                stringBuffer.append("&#x").append("00").append(hexB).append(";");
            } else if (hexB.length() > 2) {
                stringBuffer.append("&#x").append(hexB).append(";");
            } else {
                stringBuffer.append(utfBytes[i]);
            }
        }
        if (stringBuffer.length() == 0) {
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }
}
