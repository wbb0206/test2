package com.eastop.document;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.eastop.document.constant.DocumentTypeConsts;
import com.eastop.document.utils.DateUtils;
import com.eastop.document.utils.ImageUtils;

/**
 * 报表文档生成测试
 *
 * @author DELL
 *
 */
public class Client {

    /**
     * doc
     */
    @Test
    public void testDoc() {
        Map<String, Object> datas =  packagingData();//数据
        String type = "0";
        exportReport(type, datas);
    }

    /**
     * xls
     */
    @Test
    public void testExcel() {
        Object datas = new Object();
        String type = "1";
        exportReport(type, datas);
    }

    /**
     * PDF
     */
    @Test
    public void testPdf() {
        Map<String, Object> datas =  packagingData();//数据
        String type = "2";
        exportReport(type, datas);
    }

    /**
     * 根据type生成不同格式的报表
     *
     * @param type 文档类型，支持doc、xls、pdf三种
     * @param datas 数据模型
     * @return 报表文件生成路径
     */
    public String exportReport(String type, Object datas) {
        String destination = "";// 报表文件路径

        ReportStrategy strategy = null;// 使用不同的生成策略
        if (StringUtils.isNotBlank(type)) {
            switch (type) {
                case DocumentTypeConsts.DOC:
                    strategy = new DocReportStrategy();// doc
                    break;
                case DocumentTypeConsts.XLS:
                    strategy = new ExcelReportStrategy();// excel
                    break;
                case DocumentTypeConsts.PDF:
                    strategy = new PdfReportStrategy();// pdf
                    break;
                default:
                    strategy = new ExcelReportStrategy();// excel
                    break;
            }

            // 环境类调用具体报表生成策略
            ReportContext context = new ReportContext(strategy);
            destination = context.generateReport(datas);
        }

        return destination;
    }

    public Map<String, Object> packagingData() {
        Map<String, Object> datas = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> single = null;

        for (int i = 0; i < 100; i++) {
            single = new HashMap<String, Object>();

            single.put("name", "张三" + i);// 姓名

            if (i % 2 == 0) {
                single.put("sex", "男");// 性别
            } else {
                single.put("sex", "女");
            }
            single.put("age", 25);// 年龄
            single.put("describe", "test" + i);// 简介

            list.add(single);
        }

        datas.put("dataList", list);

        String imagePath = this.getClass().getClassLoader().getResource("").getPath();
        imagePath = imagePath.substring(0, imagePath.lastIndexOf("test")) + "classes/config/images/portrait.png";
        datas.put("image", ImageUtils.getImageStr(imagePath));
        datas.put("currentTime", DateUtils.getDate("yyyy年MM月dd日 HH时mm分ss秒"));
        datas.put("department", "中国贵州");
        return datas;
    }

}
