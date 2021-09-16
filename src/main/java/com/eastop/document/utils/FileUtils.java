package com.eastop.document.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件帮助类
 * 
 * @author liudong
 *
 */
public final class FileUtils {

	/**
	 * 新建文件
	 * 
	 * @param fileName 文件全路径
	 * @return true or false
	 */
	public static boolean createFile(String fileName) {
		boolean flag = false;// 标记文件是否创建成功

		File f = new File(fileName);
		try {
			if (!f.getParentFile().exists()) {// 父目录路径不存在
				f.getParentFile().mkdirs();// 级联创建父目录
			} else {// 父目录存在
				f.delete();// 先删除
			}

			f.createNewFile();// 新建文件
			flag = true;

		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}

		return flag;
	}
	
	/**
	 * 输出文件至浏览器
	 * 
	 * @param response
	 * @param filePath	文件路径
	 * @return
	 */
	public static void exportToBrowser(HttpServletResponse response, String filePath) {
		InputStream in = null;
		
		File file = new File(filePath);
		
		try {
			in = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ServletOutputStream out = null;

		if (in != null) {
			try {
				response.setContentType("application/x-msdownload;");
				response.setHeader("Content-Disposition", "attachment; fileName=" + new String(file.getName().getBytes("utf-8"), "ISO8859-1"));
				response.setHeader("Content-Length", String.valueOf(file.length())); 
				
				out = response.getOutputStream();

				byte[] buffer = new byte[1024]; // 缓冲区
				int bytesToRead = -1;
				// 通过循环将读入的Word文件的内容输出到浏览器中
				while ((bytesToRead = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytesToRead);
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				System.err.println("编码方式不支持.");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("流操作出错.");
			} finally {
				try {
					if (in != null) {
						in.close();
					}
					if (out != null) {
						out.close();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除指定文件
	 * 
	 * @param filePath	文件路径
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	//Test
	public static void main(String[] args) {
		String fileName = "E:/work1/temp0/temp1/tempFile.txt";
		// 创建文件
		createFile(fileName);
	}
}
