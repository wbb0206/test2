package com.eastop.document.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

import javax.security.auth.login.Configuration;

/**
 * freemarker帮助类
 * 
 * @author liudong
 *
 */
public final class FreemarkerUtils {

	/** freemaker模板的配置信息 **/
	private static Configuration config = null;

	/** 编码方式 **/
	private String encoding;

	/** 默认为UTF-8 **/
	private static final String DEFAULT_ENCODING = "UTF-8";

	public FreemarkerUtils() {
		this(DEFAULT_ENCODING);
	}

	public FreemarkerUtils(String encoding) {
		this.encoding = encoding;
		config = new Configuration(Configuration.VERSION_2_3_23);
		config.setDefaultEncoding(encoding);
		config.setClassForTemplateLoading(this.getClass(), "/");
	}

	/**
	 * 获取doc模板
	 * 
	 * @param templatePath 模板路径
	 * @return
	 */
	private Template getTemplate(String templatePath) {
		Template template = null;
		try {
			template = config.getTemplate(templatePath);
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("找不到doc模板文件." + e.getMessage());
		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
			throw new RuntimeException("doc模板文件格式不正确." + e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("doc模板文件解析出错." + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("doc模板文件流读取出错." + e.getMessage());
		}
		return template;
	}

	/**
	 * 生成指定格式的文件
	 * 
	 * @param templatePath 模板文件路径
	 * @param targetPath 目标文件路径，可以是doc文件
	 * @param dataModel 数据模型
	 */
	public void generateFile(String templatePath, String targetPath, Map<String, Object> dataModel) {
		Writer writer = null;
		Template template = null;

		try {
			if (FileUtils.createFile(targetPath)) {// 新建doc文件
				
				template = getTemplate(templatePath);// 拿模板

				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetPath), encoding));

				// 合成
				template.process(dataModel, writer);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("生成目标文件出错." + e.getMessage());
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭流失败." + e.getMessage());
			}
		}
	}

	/**
	 * 生成html字符串
	 * 
	 * @param templatePath 模板路径
	 * @param dataModel 数据模型
	 * @return html字符串
	 */
	public String generateStr(String templatePath, Map<String, Object> dataModel) {
		Template template = null;
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = null;
		StringBuffer sb = new StringBuffer();
		try {
			template = getTemplate(templatePath);// 拿模板

			writer = new BufferedWriter(stringWriter);
			template.process(dataModel, writer);
			writer.flush();

			sb.append(stringWriter.getBuffer());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("生成html字符串出错." + e.getMessage());
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (stringWriter != null) {
					stringWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭流失败." + e.getMessage());
			}
		}

		return sb.toString();
	}
}
