package com.hecheng.utils;

import java.util.UUID;

public class UuidUtils {

	/**
	 * 随机生成32位的uuid
	 * @return
	 */
	public static String get32UUID() {
		
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		
		return uuid;
	}
	
}
