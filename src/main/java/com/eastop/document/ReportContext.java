package com.eastop.document;

/**
 * 报表环境类
 * 
 * @author DELL
 *
 */
public class ReportContext {
	
	/** 持有一个具体策略的对象 */
	private ReportStrategy strategy;

	/**
	 * 构造函数，传入一个具体策略对象
	 * 
	 * @param strategy 具体策略对象
	 */
	public ReportContext(ReportStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * 策略方法，调用具体的报表生成策略
	 * 
	 * @param datas	需要在模板里面填充的数据
	 * @return	生成的文件路径
	 */
	public String generateReport(Object datas) {
		return strategy.generateReport(datas);
	}

}
