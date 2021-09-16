package com.eastop.document.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件读取帮助类
 * 
 * @author DELL
 *
 */
public class PropertiesUtils {

	private static Properties props;

	static {
		loadProps();// 导入配置文件
	}

	/**
	 * 导入配置文件
	 */
	synchronized static private void loadProps() {
		props = new Properties();
		InputStream in = null;
		try {
			// 这里不能是config/properties/template.properties
			in = PropertiesUtils.class.getClassLoader().getResourceAsStream("config/template.properties");
			props.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据key获取value
	 * 
	 * @param key key
	 * @return value
	 */
	public static String getValue(String key) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key);
	}
}
