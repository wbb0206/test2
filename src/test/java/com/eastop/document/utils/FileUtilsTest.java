package com.eastop.document.utils;

import org.junit.Test;

public class FileUtilsTest {
	
	@Test
	public void testCreateFile() {
		String fileName = "D:/document/tempFile.txt";
		// 创建文件
		FileUtils.createFile(fileName);
	}

}
