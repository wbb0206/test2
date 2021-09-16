package com.eastop.document;

import java.util.HashMap;
import java.util.Map;

import com.eastop.document.excel.ExcelGenerator;
import com.eastop.document.utils.DateUtils;
import com.eastop.document.utils.PropertiesUtils;

/**
 * excel格式生成策略
 * 
 * @author DELL
 *
 */
public class ExcelReportStrategy implements ReportStrategy {

	@Override
	public String generateReport(Object datas) {
		// TODO xls文件生成过程

		// 1. templatePath
		String xlsTemplatePath = PropertiesUtils.getValue("xls-template");

		ExcelGenerator excel = new ExcelGenerator();
		excel.initTemplate(xlsTemplatePath);// 初始化excel模板

		// 2.dataModel
		excel = initDataModel(datas, excel);// 填充数据模型

		// 3.xlsPath
		String xlsPath = getXlsPath();

		// 4.生成
		excel.export(xlsPath);

		return xlsPath;
	}

	/**
	 * 将数据模型填充进excel模板中
	 * 
	 * @param datas 数据模型
	 * @param excel excel模板对象
	 * @return excel生成器对象
	 */
	private ExcelGenerator initDataModel(Object datas, ExcelGenerator excel) {
		// 1.设置数据
		excel.createNewRow();
		excel.createCell("1111111");
		excel.createCell("aaaaaaaaaaaa");
		excel.createCell("a1");
		excel.createCell("a2a2");

		excel.createNewRow();
		excel.createCell("222222");
		excel.createCell("bbbbb");
		excel.createCell("b");
		excel.createCell("dbbb");

		excel.createNewRow();
		excel.createCell("3333333");
		excel.createCell("cccccc");
		excel.createCell("a1");
		excel.createCell(12333);

		excel.createNewRow();
		excel.createCell("4444444");
		excel.createCell("ddddd");
		excel.createCell("a1");
		excel.createCell("a2a2");

		excel.createNewRow();
		excel.createCell("555555");
		excel.createCell("eeeeee");
		excel.createCell("a1");
		excel.createCell(112);

		excel.createNewRow();
		excel.createCell("555555");
		excel.createCell("eeeeee");
		excel.createCell("a1");
		excel.createCell("a2a2");

		excel.createNewRow();
		excel.createCell("555555");
		excel.createCell("eeeeee");
		excel.createCell("a1");
		excel.createCell("a2a2");

		excel.createNewRow();
		excel.createCell("555555");
		excel.createCell("eeeeee");
		excel.createCell("a1");
		excel.createCell("a2a2");

		excel.createNewRow();
		excel.createCell("555555");
		excel.createCell("eeeeee");
		excel.createCell("a1");
		excel.createCell("a2a2");

		excel.createNewRow();
		excel.createCell("555555");
		excel.createCell("eeeeee");
		excel.createCell("a1");
		excel.createCell("a2a2");

		excel.createNewRow();
		excel.createCell("555555");
		excel.createCell("eeeeee");
		excel.createCell("a1");
		excel.createCell("a2a2");

		// 2.替换占位符
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", "数据报表");
		map.put("currentTime", DateUtils.getDate("yyyy年MM月dd日 HH时mm分ss秒"));
		map.put("department", "中国贵阳");
		
		excel.replacePlaceholderData(map);

		// 3.插入序号
		excel.insertNumber();
		return excel;
	}

	/**
	 * 获取xls文档路径
	 * 
	 * @return
	 */
	private String getXlsPath() {
		StringBuilder sb = new StringBuilder();
		sb.append(PropertiesUtils.getValue("baseDir"));// 基路径
		sb.append(PropertiesUtils.getValue("xls-destination"));

		String currentDate = DateUtils.formatDate(DateUtils.getDate());
		currentDate = currentDate.replaceAll("-", "/");
		sb.append(currentDate).append("/");

		// 文件名
		sb.append(System.currentTimeMillis()).append(".xls");

		return sb.toString();
	}
}
