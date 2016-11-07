package com.app.utils;

import java.util.UUID;

public class UUIDUtil {

	public static String getUUID(){
		UUID uuid=UUID.randomUUID();
		/*return uuid.toString().replace("-","");	//去掉“-”*/
		return uuid.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(UUIDUtil.getUUID());
	}

}
