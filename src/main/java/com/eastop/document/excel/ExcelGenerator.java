package com.eastop.document.excel;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.sun.media.sound.InvalidFormatException;
import javafx.scene.control.Cell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.eastop.document.utils.FileUtils;

/**
 * excel文档生成器
 * 
 * @author liudong
 *
 */
public final class ExcelGenerator<WorkBook> {

	/** 数据行标识 **/
	public final static String DATA_LINE = "datas";

	/** 默认样式标识 **/
	public final static String DEFAULT_STYLE = "defaultStyles";

	/** 行样式标识 **/
	public final static String STYLE = "styles";

	/** 插入序号样式标识 **/
	public final static String SERIAL_NUMBER = "sernums";

	/** 工作薄 **/
	private WorkBook workBook;

	/** 工作表 **/
	private Sheet sheet;

	/** 数据的初始化列数 **/
	private int initColIndex;

	/** 数据的初始化行数 **/
	private int initRowIndex;

	/** 当前列数 **/
	private int currentColIndex;

	/** 当前行数 **/
	private int currentRowIndex;

	/** 当前行对象 **/
	private Row currentRow;

	/** 最后一行的数据索引 **/
	private int lastRowIndex;

	/** 默认样式 **/
	private CellStyle defaultStyle;

	/** 默认行高 **/
	private float rowHeight;

	/** 存储某一方所对于的样式 **/
	private Map<Integer, CellStyle> styles;

	/** 序号的列 **/
	private int serialColIndex;

	public ExcelGenerator() {
	}

	/**
	 * 获取xls模板
	 * 
	 * @param templatePath
	 *            模板路径
	 */
	public void initTemplate(String templatePath) {
		try {
			// excel文件名
			InputStream in = ExcelGenerator.class.getResourceAsStream(templatePath);
			workbook = WorkbookFactory.create(in);

			initTemplate();// 初始化模板
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("InvalidFormatException, please check.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("The template file is not exist, please check.");
		}
	}

	/**
	 * 初始化模板
	 */
	private void initTemplate() {
		sheet = workbook.getSheetAt(0);
		initConfigData();
		lastRowIndex = sheet.getLastRowNum();
		currentRow = sheet.getRow(currentRowIndex);
	}

	/**
	 * 循环遍历，找到有datas字符的那个单元，记录initColIndex，initRowIndex，currentColIndex，currentRowIndex
	 * 调用initStyles()方法
	 * 在寻找datas字符的时候会顺便找一下sernums，如果有则记录其列号serialColIndex；如果没有则调用initSerial()方法，重新循环查找
	 */
	private void initConfigData() {
		boolean findData = false;
		boolean findSerial = false;// 是否找到序号位置
		for (Row row : sheet) {
			if (findData) {
				break;
			}
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(SERIAL_NUMBER)) {
					serialColIndex = c.getColumnIndex();
					findSerial = true;
				}
				if (str.equals(DATA_LINE)) {
					initColIndex = c.getColumnIndex();
					initRowIndex = row.getRowNum();
					currentColIndex = initColIndex;
					currentRowIndex = initRowIndex;
					findData = true;
					break;
				}
			}
		}
		if (!findSerial) {
			initSerial();
		}
		initStyles();
	}

	/**
	 * 初始化序号位置
	 */
	private void initSerial() {
		for (Row row : sheet) {
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(SERIAL_NUMBER)) {
					serialColIndex = c.getColumnIndex();
				}
			}
		}
	}

	/**
	 * 初始化样式信息
	 */
	private void initStyles() {
		styles = new HashMap<Integer, CellStyle>();
		for (Row row : sheet) {
			for (Cell c : row) {
				if (c.getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				String str = c.getStringCellValue().trim();
				if (str.equals(DEFAULT_STYLE)) {
					defaultStyle = c.getCellStyle();
					rowHeight = row.getHeightInPoints();
				}
				if (str.equals(STYLE) || str.equals(SERIAL_NUMBER)) {
					styles.put(c.getColumnIndex(), c.getCellStyle());
				}
			}
		}
	}

	/**
	 * 创建一个单元格，并填充值进去
	 * 
	 * @param value 字符串型
	 */
	public void createCell(String value) {
		Cell c = currentRow.createCell(currentColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		currentColIndex++;
	}

	public void createCell(int value) {
		Cell c = currentRow.createCell(currentColIndex);
		setCellStyle(c);
		c.setCellValue((int) value);
		currentColIndex++;
	}

	public void createCell(Date value) {
		Cell c = currentRow.createCell(currentColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		currentColIndex++;
	}

	public void createCell(double value) {
		Cell c = currentRow.createCell(currentColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		currentColIndex++;
	}

	public void createCell(boolean value) {
		Cell c = currentRow.createCell(currentColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		currentColIndex++;
	}

	public void createCell(Calendar value) {
		Cell c = currentRow.createCell(currentColIndex);
		setCellStyle(c);
		c.setCellValue(value);
		currentColIndex++;
	}

	/**
	 * 设置某个单元格的样式
	 * 
	 * @param c
	 */
	private void setCellStyle(Cell c) {
		if (styles.containsKey(c.getColumnIndex())) {
			c.setCellStyle(styles.get(c.getColumnIndex()));
		} else {
			c.setCellStyle(defaultStyle);
		}
	}

	/**
	 * 新建一行
	 */
	public void createNewRow() {
		if (lastRowIndex > currentRowIndex && currentRowIndex != initRowIndex) {
			sheet.shiftRows(currentRowIndex, lastRowIndex, 1, true, true);
			lastRowIndex++;
		}
		currentRow = sheet.createRow(currentRowIndex);
		currentRow.setHeightInPoints(rowHeight);
		currentRowIndex++;
		currentColIndex = initColIndex;
	}

	/**
	 * 插入序号，会自动找相应的序号标示的位置完成插入
	 */
	public void insertNumber() {
		int index = 1;
		Row row = null;
		Cell c = null;
		for (int i = initRowIndex; i < currentRowIndex; i++) {
			row = sheet.getRow(i);
			c = row.createCell(serialColIndex);
			setCellStyle(c);
			c.setCellValue(index++);
		}
	}

	/**
	 * 根据map替换相应的常量，通过Map中的值来替换#开头的值
	 * 
	 * @param datas
	 */
	public void replacePlaceholderData(Map<String, String> datas) {
		if (datas == null) {
			return;
		}
		for (Row row : sheet) {// 循环遍历工作表的每一行
			for (Cell c : row) {// 取出每一个单元格
				if (c.getCellType() != Cell.CELL_TYPE_STRING) {
					continue;
				}
				String str = c.getStringCellValue().trim();
				if (str.startsWith("#")) {// 占位符以#开头
					if (datas.containsKey(str.substring(1))) {
						c.setCellValue(datas.get(str.substring(1)));// 替换
					}
				}
			}
		}
	}

	/**
	 * 生成xls文件
	 * 
	 * @param xlsPath 生成的xls文件路径
	 */
	public void export(String xlsPath) {
		OutputStream fos = null;

		if (FileUtils.createFile(xlsPath)) {// 新建xls文件
			try {
				fos = new BufferedOutputStream(new FileOutputStream(xlsPath));
				workbook.write(fos);// 写出
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("写入的文件不存在." + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("写入数据失败." + e.getMessage());
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException("关闭流出错." + e.getMessage());
					}
				}
			}
		}
	}

}
