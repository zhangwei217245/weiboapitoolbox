package com.sina.weibo.toolbox.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.util.logging.Logger;


import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporterParameter;

/**
 *
 * @author X-Spirit
 */
public class JasperUtil {

    public static final Logger log = Logger.getLogger(JasperUtil.class.getName());

    //生成CSV格式，通过页面响应输出
    public void csvReport(String designrealpath, String reportName, Map parameters, List rstlst, HttpServletResponse response) throws Exception {
        //得到JasperPrint对象
        JasperPrint jp = this.getJasper(rstlst, parameters, designrealpath);
        //预设输出类型
        response.setContentType("application/octet-stream");
        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRCsvExporterParameter.OUTPUT_WRITER, response.getWriter());
        String rname = Utility.isEmpty(reportName) ? "file.csv" : reportName + ".csv";
        response.setHeader("Content-Disposition", "attachment;filename=" + rname);
        exporter.setParameter(JRCsvExporterParameter.OUTPUT_FILE_NAME, rname);
        exporter.exportReport();
    }
    //生成TXT格式，通过页面响应输出

    public void txtReport(String designrealpath, String reportName, Map parameters, List rstlst, HttpServletResponse response) throws Exception {
        JasperPrint jp = this.getJasper(rstlst, parameters, designrealpath);
        JRTextExporter exporter = new JRTextExporter();
        response.setContentType("application/octet-stream");
        //response.setCharacterEncoding("utf-8");
        exporter.setParameter(JRTextExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 80);
        exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 60);
        exporter.setParameter(JRTextExporterParameter.OUTPUT_WRITER, response.getWriter());
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        String rname = Utility.isEmpty(reportName) ? "file.txt" : reportName + ".txt";
        exporter.setParameter(JRTextExporterParameter.OUTPUT_FILE_NAME, rname);
        exporter.exportReport();
    }
    //生成XML格式，通过页面响应输出(可配合Flash等RIA应用)

    public void xmlReport(String designrealpath, String reportName, Map parameters, List rstlst, HttpServletResponse response) throws Exception {
        JasperPrint jp = this.getJasper(rstlst, parameters, designrealpath);
        JRXmlExporter exporter = new JRXmlExporter();
        response.setContentType("text/xml");
        //response.setCharacterEncoding("utf-8");
        exporter.setParameter(JRXmlExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRXmlExporterParameter.OUTPUT_WRITER, response.getWriter());
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        String rname = Utility.isEmpty(reportName) ? "file.jrpxml" : reportName + ".jrpxml";
        response.setHeader("Content-Disposition", "inline;filename=" + rname);
        exporter.setParameter(JRXmlExporterParameter.OUTPUT_FILE_NAME, rname);
        exporter.exportReport();
    }
    //生成PDF格式，通过页面响应输出到下载

    public void pdfReport(String designrealpath, String reportName, Map parameters, List rstlst, HttpServletResponse response) throws Exception {
        //得到JasperPrint对象
        JasperPrint jp = this.getJasper(rstlst, parameters, designrealpath);
        //预设输出类型
        response.setContentType("application/pdf");
        //得到报表生成器对象
        JRPdfExporter exporter = new JRPdfExporter();
        byte[] bytes;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        //向报表生成器传递参数，例如字体、报表模板、输出流等信息
//        HashMap fontMap = new HashMap();
//        FontKey key = new FontKey("微软雅黑", true, false);
//        PdfFont font = new PdfFont("msyh.ttf", "UniGB-UCS2-H", true);
//        fontMap.put(key, font);
        //pdf   font   name:STTong-light
        //pdf   encoding:UniGB-UCS2-H(Simple   chinese) 在模板中这样设定汉字就没有问题了，jasperreport中多数fontname,pdf   fontname组合没办法显示汉字
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bo);
//        exporter.setParameter(JRExporterParameter.FONT_MAP, fontMap);
        //报表生成器生成报表，并向指定的输出流输出报表文件
        exporter.exportReport();
        bytes = bo.toByteArray();
        //将包含报表文件的输出流中的字节通过Servlet输出流进行输出
        if (bytes != null && bytes.length > 0) {
            response.reset();
            String rname = Utility.isEmpty(reportName) ? "Report" : reportName;
            response.setHeader("Content-Disposition", "attachment;filename=" + rname + ".pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream sos = response.getOutputStream();
            sos.write(bytes, 0, bytes.length);
            sos.flush();
            sos.close();
        }
    }

    //生成Excel格式，通过页面响应输出到下载
    public void excelReport(String designrealpath, String reportName, Map parameters, List rstlst, HttpServletResponse response) throws Exception {
        //得到JasperPrint对象
        JasperPrint jp = this.getJasper(rstlst, parameters, designrealpath);
        //预设输出类型
        response.setContentType("application/vnd.ms-excel");
        JRXlsExporter exporter = new JRXlsExporter();
        byte[] bytes;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bo);
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
        //设定字体自适应单元格大小
        exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        if (Utility.isNotEmpty(reportName)) {
            String sheetname = new String(reportName.getBytes("iso8859-1"), "GBK");
            exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, new String[]{sheetname});
        }

        exporter.exportReport();
        bytes = bo.toByteArray();

//        ConfigManager conf = Utility.getPattern("webbk.xml");
//        String xlsdir = conf.selectValue("report.xlsdir");
//        FileUtility.createFile(xlsdir);
//        FileOutputStream fos=new FileOutputStream(new File(xlsdir + System.currentTimeMillis() + ".xls"));
//        if(bytes != null && bytes.length > 0){
//            fos.write(bytes);
//            fos.flush();
//            fos.close();
//        }
        if (bytes != null && bytes.length > 0) {
            response.reset();
            String rname = Utility.isEmpty(reportName) ? "Report" : reportName;
            response.setHeader("Content-Disposition", "attachment;filename=" + rname + ".xls");
            response.setContentLength(bytes.length);
            ServletOutputStream sos = response.getOutputStream();
            sos.write(bytes, 0, bytes.length);
            sos.flush();
            sos.close();
        }
    }

    //生成HTML格式，通过页面响应输出到下载
    public void htmlReport(String designrealpath, String reportName, Map parameters, List rstlst, HttpServletResponse response) throws Exception {
        JasperPrint jp = this.getJasper(rstlst, parameters, designrealpath);
        JRHtmlExporter exporter = new JRHtmlExporter();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jp);
        exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, response.getWriter());
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        //对于交叉表，不需要将内嵌的框导出为内嵌表格
        exporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES, Boolean.FALSE);
        exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//        String HTML_HEADER = "";
//        HTML_HEADER = HTML_HEADER + "<html> ";
//        HTML_HEADER = HTML_HEADER + "<head> ";
//        HTML_HEADER = HTML_HEADER + "<title></title> ";
//        HTML_HEADER = HTML_HEADER + "    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"/> ";
//        HTML_HEADER = HTML_HEADER + "    <meta http-equiv=\"imagetoolbar\" content=\"no\"> ";
//        HTML_HEADER = HTML_HEADER + "    <style type=\"text/css\"> ";
//        HTML_HEADER = HTML_HEADER + "    a{text-decoration:none} ";
//        HTML_HEADER = HTML_HEADER + "    </style> ";
//        HTML_HEADER = HTML_HEADER + "    </head> ";
//        HTML_HEADER = HTML_HEADER + "    <body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\"> ";
//        HTML_HEADER = HTML_HEADER + "    <div > ";
//        exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER,HTML_HEADER);
        exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "pt");//设定HTML报表的CSS度量单位（px太小，用PT较好）
//        exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER,"</div></body></html>");
        exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "<br style='page-break-before:always;'>");
        String rname = Utility.isEmpty(reportName) ? "Report.html" : reportName + ".html";
        exporter.setParameter(JRHtmlExporterParameter.OUTPUT_FILE_NAME, rname);

        exporter.exportReport();

    }

    /**
     * 传入结果集List，以及JasperDesign文件的路径，得到JasperPrint对象
     * @param rstlst
     * @param designrealpath
     * @return
     * @throws java.lang.Exception
     */
    public JasperPrint getJasper(List rstlst, Map parameters, String designrealpath) throws Exception {

        //File file=new File(application.getRealPath("/Report/RTAReport.jrxml"));
        System.out.println("--jasperDesign Load begin ---");
        log.info("--jasperDesign Load begin ---");
        File file = new File(designrealpath);
        JasperDesign jasperDesign = JRXmlLoader.load(file);
        System.out.println("--jasperDesign Load end ---");
        log.info("--jasperDesign Load end ---");

        System.out.println("--jasperReport Compile begin---");
        log.info("--jasperReport Compile begin---");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        System.out.println("--jasperReport Compile end---");
        log.info("--jasperReport Compile end---");

        //parameters.put("Reporttitle", "Address Report");
        //parameters.put("BaseDir", file.getParentFile());

        //查询
        //this.invokeDAO(partnerid, dao, actionTag, BaseForm.QUERY, ds);
        //List list = dao.getResults();
        Collection rows = setMapKeyUpperCase(rstlst);
        //获取一个JasperPrint对象   
        JasperPrint jasperPrint = setReportCollection(jasperReport, parameters, rows);
        return jasperPrint;
    }

    /**  
     * 获取JasperPrint对象  
     *   
     * @param url  
     * @param list  
     * @return  
     * @throws Exception  
     */
    public JasperPrint setReportCollection(JasperReport report, Map parameters, Collection list)
            throws Exception {
        JRMapCollectionDataSource dataSource;
        //将list数据集转换为JRMapCollectionDataSource   
        dataSource = new JRMapCollectionDataSource(list);
        //使用JasperFillManager填充JasperPrint对象
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
        return jasperPrint;
    }

    public List<Map> setMapKeyUpperCase(List<Map> lst) {
        List rstlst;
        if (Utility.isNotEmpty(lst)) {
            rstlst = new ArrayList();
            for (int i = 0; i < lst.size(); i++) {
                Map map = lst.get(i);
                Set keyset = map.keySet();
                Map newmap = new HashMap();
                Object[] obs = keyset.toArray();
                keyset = null;
                for (int j = 0; j < obs.length; j++) {
                    String key = (String) obs[j];
                    newmap.put(key.toUpperCase(), map.get(key));
                    key = null;
                }
                map = null;
                rstlst.add(newmap);
            }
        } else {
            rstlst = lst;
        }
        return rstlst;
    }
}
