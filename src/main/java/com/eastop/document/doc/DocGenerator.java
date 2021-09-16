package com.eastop.document.doc;

import java.util.Map;

import com.eastop.document.utils.FreemarkerUtils;

/**
 * doc文档生成器
 * 
 * @author liudong
 *
 */
public final class DocGenerator {

	/**
	 * 生成doc文件
	 * 
	 * @param templatePath 模板文件路径
	 * @param docPath 生成的doc文件路径
	 * @param dataModel 数据模型
	 */
	public void export(String templatePath, String docPath, Map<String, Object> dataModel) {
		FreemarkerUtils freemarker = new FreemarkerUtils();
		freemarker.generateFile(templatePath, docPath, dataModel);
	}

}
