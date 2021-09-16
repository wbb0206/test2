package com.eastop.document.pdf;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.eastop.document.utils.DateUtils;
import com.eastop.document.utils.FileUtils;
import com.eastop.document.utils.FreemarkerUtils;
import com.eastop.document.utils.PropertiesUtils;
import com.lowagie.text.pdf.BaseFont;

/**
 * pdf文档生成器
 * 
 * @author liudong
 *
 */
public class PdfGenerator {

	/**
	 * 生成pdf文件
	 * 
	 * @param templatePath 模板路径
	 * @param pdfPath pdf文件生成路径
	 * @param dataModel 数据模型
	 */
	public void export(String templatePath, String pdfPath, Map<String, Object> dataModel) {
		// step 1. 生成html字符串
		FreemarkerUtils freemarker = new FreemarkerUtils();

		// step 2. html临时文件路径
		String htmlPath = getHtmlPath();

		// step 3. 生成html临时文件
		freemarker.generateFile(templatePath, htmlPath, dataModel);

		// step 4. 根据html临时文件生成pdf
		if (generate(htmlPath, pdfPath)) {
			// step 5. 删除html临时文件
			FileUtils.deleteFile(htmlPath);
		}
	}

	/**
	 * 获取html临时文件路径
	 * 
	 * @return
	 */
	private String getHtmlPath() {
		StringBuilder sb = new StringBuilder();
		sb.append(PropertiesUtils.getValue("baseDir"));// 基路径
		sb.append(PropertiesUtils.getValue("pdf-destination"));

		String currentDate = DateUtils.formatDate(DateUtils.getDate());
		currentDate = currentDate.replaceAll("-", "/");
		sb.append(currentDate).append("/");

		// 文件名
		sb.append(System.currentTimeMillis()).append(".html");

		return sb.toString();
	}

	/**
	 * 根据html临时文件生成pdf文件
	 * 
	 * @param htmlPath html临时文件路径
	 * @param pdfPath pdf文件生成路径
	 * @return
	 */
	public boolean generate(String htmlPath, String pdfPath) {
		boolean flag = false;
		OutputStream out = null;
		try {

			ITextRenderer renderer = new ITextRenderer();

			String url = new File(htmlPath).toURI().toURL().toString();

			renderer.setDocument(url);

			// 解决中文支持问题
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if ("linux".equals(getCurrentOperatingSystem())) {// Linux
				fontResolver.addFont("/usr/share/fonts/chiness/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			} else {// Windows
				fontResolver.addFont("C:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}

			if (FileUtils.createFile(pdfPath)) {// 新建pdf文件
				out = new BufferedOutputStream(new FileOutputStream(pdfPath));
			}

			// 解决图片的相对路径问题
			renderer.getSharedContext().setBaseURL(getPdfImagePath());
			renderer.layout();
			renderer.createPDF(out);

			out.flush();

			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			throw new RuntimeException("生成pdf文件出错." + e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭流失败." + e.getMessage());
			}
		}

		return flag;
	}

	/**
	 * 获取pdf图片保存的路径
	 * 
	 * @return
	 */
	private String getPdfImagePath() {
		StringBuffer sb = new StringBuffer();
		sb.append("file:/");
		sb.append(PropertiesUtils.getValue("baseDir"));
		sb.append(PropertiesUtils.getValue("pdf-destination"));
		sb.append(PropertiesUtils.getValue("images"));
		return sb.toString();
	}

	/**
	 * 获取当前操作系统名称
	 * 
	 * @return
	 */
	private String getCurrentOperatingSystem() {
		String os = System.getProperty("os.name").toLowerCase();
		System.out.println("当前操作系统：" + os);
		return os;
	}
}
