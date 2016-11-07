package com.app.utils;

public class StringUtil {

	public static boolean isEmpty(String str){
		if("".equals(str)|| str==null||"null".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNotEmpty(String str){
		return !StringUtil.isEmpty(str);
	}


	//获取图片的后缀名
	public static String getSuffix(String name){
		int index = name.lastIndexOf(".");
		String result = "";
		for (int i=index;i<name.length();i++){
			result+=String.valueOf(name.charAt(i));
		}
		return result;

	}

	/*public static void main(String[] args) {
		String str = StringUtil.getSuffix("1232763.72.png");
		System.out.print(str);
	}*/
}
