package com.lrw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.lrw.vo.Question;

public class WriteDoc{
	
	public static void createWord(List<Question> questionList,String name) throws IOException {
		File file = new File("C:\\Users\\Administrator\\Desktop\\"+name);
		if(!file.exists()) {
			//不存在就创建
			file.createNewFile();
			FileInputStream in = new FileInputStream(file);
			FileOutputStream out=new FileOutputStream(file);
			StringBuffer str = new StringBuffer();
			for(int x=0;x<questionList.size();x++) {
				str.append((x+1)+"、 "+questionList.get(x).writeToDoc());
			}
			if(str!=null) {
				byte data[] = str.toString().getBytes();
				out.write(data); 							// 3．输出内容
				out.close();	
			}
		}
	}
}