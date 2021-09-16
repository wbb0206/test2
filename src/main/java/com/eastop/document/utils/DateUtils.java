package com.eastop.document.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间帮助类
 * 
 * @author DELL
 *
 */
public final class DateUtils {

	/** 默认时间格式 **/
	public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 格式化日期的格式，具体格式：年-月-日
	 * 
	 * @param date Date类型的时间
	 * @return 字符串
	 */
	public static String formatDate(Date date) {
		return DEFAULT_SDF.format(date);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return Date类型
	 */
	public static Date getDate() {
		return new Date();
	}

	/**
	 * 以指定格式获取当前时间
	 * 
	 * @param format 格式，eg: yyyy-MM-dd HH-mm-ss
	 * @return 字符串类型
	 */
	public static String getDate(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
}
