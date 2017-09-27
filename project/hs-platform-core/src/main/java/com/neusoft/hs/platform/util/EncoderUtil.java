package com.neusoft.hs.platform.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncoderUtil {

	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// 确定计算方法
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		// 加密后的字符串
		return new String(Base64.getEncoder().encode(md5.digest(str.getBytes("utf-8"))));
	}

}
