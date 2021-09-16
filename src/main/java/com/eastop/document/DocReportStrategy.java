package com.eastop.document;

import java.util.HashMap;
import java.util.Map;

import com.eastop.document.doc.DocGenerator;
import com.eastop.document.utils.DateUtils;
import com.eastop.document.utils.PropertiesUtils;

/**
 * doc格式生成策略
 * 
 * @author DELL
 *
 */
public class DocReportStrategy implements ReportStrategy {

	@Override
	public String generateReport(Object datas) {
		// TODO doc文件生成过程

		// 1. templatePath
		String docTemplatePath = PropertiesUtils.getValue("doc-template");

		// 2.docPath
		String docPath = getDocPath();

		// 3.dataModel
		Map<String, Object> dataModel = initDataModel(datas);

		// 4.生成doc文件
		DocGenerator doc = new DocGenerator();
		doc.export(docTemplatePath, docPath, dataModel);

		return docPath;
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
	private String getDocPath() {
		StringBuilder sb = new StringBuilder();
		sb.append(PropertiesUtils.getValue("baseDir"));// 基路径
		sb.append(PropertiesUtils.getValue("doc-destination"));

		String currentDate = DateUtils.formatDate(DateUtils.getDate());
		currentDate = currentDate.replaceAll("-", "/");
		sb.append(currentDate).append("/");

		// 文件名
		sb.append(System.currentTimeMillis()).append(".doc");

		return sb.toString();
	}
}
