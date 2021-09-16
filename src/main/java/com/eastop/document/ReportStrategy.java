package com.eastop.document;

/**
 * 报表生成策略，提供不同的报表格式，只需扩展该类即可<br />
 * <br />
 * 目前提供了doc、pdf、xls三种格式的报表
 * 
 * @author DELL
 *
 */
public interface ReportStrategy {

	/**
	 * 使用指定的数据生成报表
	 * 
	 * @param datas	模板数据
	 * @return	生成的文件路径
	 */
	String generateReport(Object datas);
}
