package com.lrw.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//操作数的公具类
public class NumberUtils {
    
	//获取某个范围内的不重复的随机数
	/**
	 * 
	 * @param sz  范围内的最大值
	 * @param capacity 需要的个数
	 * @return
	 */
	public static List<Integer>  getRandomNumberList(int sz,int capacity) {
	   List<Integer> numberList = new ArrayList<Integer>();
	   for(int x=0;x<sz;x++) {
		   numberList.add(x);//随机数集合
	   }
	   List<Integer> randomNumberList = new ArrayList<Integer>();
	   int index = 0;//随机索引
	   Random random = new Random();
	   for (int i = 0; i < capacity; i++) {
           index = random.nextInt(sz - i);
           randomNumberList.add(numberList.get(index));
           numberList.remove(index);//删除
       }
		return randomNumberList;
	}
	
	
	
	
}
