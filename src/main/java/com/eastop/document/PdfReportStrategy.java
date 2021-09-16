package com.eastop.document;

import java.util.HashMap;
import java.util.Map;

import com.eastop.document.pdf.PdfGenerator;
import com.eastop.document.utils.DateUtils;
import com.eastop.document.utils.PropertiesUtils;

/**
 * PDF格式生成策略
 * 
 * @author DELL
 *
 */
public class PdfReportStrategy implements ReportStrategy {

	@Override
	public String generateReport(Object datas) {
		// TODO pdf文件生成过程

		// 1. templatePath
		String docTemplatePath = PropertiesUtils.getValue("pdf-template");

		// 2.pdfPath
		String pdfPath = getPdfPath();

		// 3.dataModel
		Map<String, Object> dataModel = initDataModel(datas);

		// 4.生成pdf文件
		PdfGenerator pdf = new PdfGenerator();
		pdf.export(docTemplatePath, pdfPath, dataModel);

		return pdfPath;
	}

	/**
	 * 处理数据格式，以符合map类型
	 * 
	 * @param datas
	 * @return map类型的数据
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> initDataModel(Object datas) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (datas instanceof Map) {
			dataMap = (Map<String, Object>) datas;
		} else {
			// TODO 作格式转换处理
			
		}
		return dataMap;
	}
	
	/**
	 * 获取doc文档路径
	 * 
	 * @return
	 */
	private String getPdfPath() {
		StringBuilder sb = new StringBuilder();
		sb.append(PropertiesUtils.getValue("baseDir"));// 基路径
		sb.append(PropertiesUtils.getValue("pdf-destination"));

		String currentDate = DateUtils.formatDate(DateUtils.getDate());
		currentDate = currentDate.replaceAll("-", "/");
		sb.append(currentDate).append("/");

		// 文件名
		sb.append(System.currentTimeMillis()).append(".pdf");

		return sb.toString();
	}

}
