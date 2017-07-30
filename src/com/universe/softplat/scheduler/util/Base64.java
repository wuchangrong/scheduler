package com.universe.softplat.scheduler.util;

/**
 * base64����
 */
public class Base64 {
	private Base64() {
	}

	public static String bytesToBASE64(byte b[]) {
		if (b == null || b.length <= 0) {
			return null;
		}
		return (new sun.misc.BASE64Encoder()).encode(b);
	}
	public static byte[] base64ToBytes(String s) {
		if (s == null) {
			return null;
		}
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		try {
			return decoder.decodeBuffer(s);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ת���󽫽���еĿո�س���ȥ����
	 * base64ת������ַ������ܰ���\r\n
	 * RFC2045�й涨�ģ�  
     *     The encoded output stream must be represented in lines of no more than 76 characters each
	 * @param b
	 * @return
	 */
	public static String bytesToBASE64NoSpace(byte b[]) {
		if (b == null || b.length <= 0) {
			return null;
		}
		String r =  (new sun.misc.BASE64Encoder()).encode(b);
		
		return r.replaceAll("\\s", "");
	}
	
}