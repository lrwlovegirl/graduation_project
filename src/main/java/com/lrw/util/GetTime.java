package com.lrw.util;


import java.text.SimpleDateFormat;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   CommonDate.java


import java.util.Calendar;
import java.util.Date;

public class GetTime
{
    //获取当前的时间
    public static String getFormatTime() {
    	String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    	return time ;
    }

    //获取当前时间的前几天，或后几天
    public static Date getDateFrontOneDay(int n){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, n);
        Date date = calendar.getTime();
        return  date;
    }

}
