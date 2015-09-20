package com.rayleigh.asm;

public class BuildUtil {

	/* 将类名转换成asm可识别的字符串 */
	public static String transferClassName(Class cls) {
		String clsname = cls.getName();
		return clsname.replace('.', '/');
	}

	/* 将类名转换成asm可识别的字符串 */
	public static String transferClassName(String clsname) {
		return clsname.replace('.', '/');
	}
}
