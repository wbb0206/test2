package com.eastop.document.utils;

import org.junit.Test;

public class ImageUtilsTest {

	@Test
	public void test() {
		String strImg = ImageUtils.getImageStr("D:/document/portrait.png");
		System.out.println(strImg);
		ImageUtils.generateImage(strImg, "D:/document/头像1.png");
	}
}
