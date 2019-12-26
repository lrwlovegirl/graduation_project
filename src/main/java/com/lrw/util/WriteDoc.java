package com.lrw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteDoc{
	
	public static void createWord(String name) throws IOException {
		File file = new File("C:\\Users\\Administrator\\Desktop\\"+name);
		if(!file.exists()) {
			//不存在就创建
			file.createNewFile();
			FileInputStream in = new FileInputStream(file);
			FileOutputStream out=new FileOutputStream(file);
			String str = "更多课程资源请访问：www.yootk.com";
			byte data[] = str.getBytes(); 						// 将字符串变为字节数组
			out.write(data); 							// 3．输出内容
			out.close();	
		}
	}
}