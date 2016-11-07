package com.app.utils;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static String getCurrentDateStr()throws Exception{
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}

	public static void main(String[] args){
		Logger logger = Logger.getLogger(DateUtil.class);
		String date = formatDate(new Date(),"yyyy-MM-dd hh:mm:ss");
		logger.info(date);
	}

	//数据库中取出的时间和面多出一个“.0”，这个方法就是把这个字符串裁剪掉
	public static String cutString(String str){
		if(str.length()>2){
			return str.substring(0,str.length()-2);
		}
		return null;
	}
}
