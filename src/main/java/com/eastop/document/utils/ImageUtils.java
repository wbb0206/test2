package com.eastop.document.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/***
 * 图片帮助类，实现图片与base64字符串之间的转换
 * 
 * @author liudong
 *
 */
public final class ImageUtils {

	/**
	 * 图片转base64码
	 * 
	 * @param imgFilePath 图片路径
	 * @return base64字符串
	 */
	public static String getImageStr(String imgFilePath) {
		InputStream in = null;

		byte[] data = null;

		StringBuilder sb = new StringBuilder();
		try {
			in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			// 对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();

			sb.append(encoder.encode(data));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * base64字符串转化成图片，对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @param imageBase64	图片的base64码格式
	 * @param imgFilePath	图片生成路径
	 * @return	true or false
	 */
	public static boolean generateImage(String imageBase64, String imgFilePath) {
		if (imageBase64 == null) { // 图像数据为空
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			byte[] b = decoder.decodeBuffer(imageBase64);// Base64解码
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			out = new FileOutputStream(imgFilePath);// 生成图片
			out.write(b);
			out.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Test
	public static void main(String[] args) {
		String strImg = getImageStr("E://头像.png");
		System.out.println(strImg);
		generateImage(strImg, "E://头像1.png");
	}

}
