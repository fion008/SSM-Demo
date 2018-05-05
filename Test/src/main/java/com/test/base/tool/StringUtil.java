package com.test.base.tool;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


/**
 * 字符串工具类
 * 
 */
public class StringUtil extends StringUtilParent {
	private static int sequence = 0;
	private static int length = 1;

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		if (str != null && !"".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		if (obj != null && obj.toString() != null
				&& !"".equals(obj.toString().trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否为空(自动截取首尾空白)
	 * 
	 * @param str
	 *            源字符串
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return isEmpty(str, true);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            源字符串
	 * @param trim
	 *            是否截取首尾空白
	 * @return
	 */
	public static boolean isEmpty(String str, boolean trim) {
		return str == null ? true : "".equals(str.trim());
	}

	/**
	 * @param str
	 *            the string need to be parsed
	 * @param delim
	 *            the delimiter to seperate created by zqf at 6/1/2013
	 */
	public static String[] parseToArray(String str, String delim) {
		ArrayList<Object> arr = new ArrayList<Object>();
		StringTokenizer st = new StringTokenizer(str, delim);
		while (st.hasMoreTokens()) {
			arr.add(st.nextToken());
		}
		String[] ret = new String[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			ret[i] = (String) arr.get(i);
		}
		return ret;
	}

	/**
	 * replace a old substring with rep in str
	 * 
	 * @param str
	 *            the string need to be replaced
	 * @param old
	 *            the string need to be removed
	 * @param rep
	 *            the string to be inserted
	 * @return string replaced
	 */
	public static String replace(String str, String old, String rep) {
		if ((str == null) || (old == null) || (rep == null)) {// if one is null
																// return ""
			return "";
		}
		int index = str.indexOf(old);
		if ((index < 0) || "".equals(old)) { // if no old string found or
												// nothing to replace,return the
												// origin
			return str;
		}
		StringBuffer strBuf = new StringBuffer(str);
		while (index >= 0) { // found old part
			strBuf.delete(index, index + old.length());
			strBuf.insert(index, rep);
			index = strBuf.toString().indexOf(old);
		}
		return strBuf.toString();
	}

	/**
	 * 带逗号分隔的数字转换为NUMBER类型
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Number stringToNumber(String str) throws ParseException {
		if (str == null || "".equals(str)) {
			return null;
		}
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator(',');
		dfs.setMonetaryDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("###,###,###,###.##", dfs);
		return df.parse(str);
	}

	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 用于字符串替换
	 * 
	 * @param target
	 *            目标对象 需要替换的字符串
	 * @param replacement
	 *            要替换的字符串
	 * @param value
	 *            替换的值
	 * @return
	 */
	public static String replacement(String target, String replacement,
			String value) {
		if (target != null)
			return target.replace(replacement, value);
		return null;
	}

	/**
	 * 验证一个字符串是否完全由纯数字组成的字符串，当字符串为空时也返回false.
	 * 
	 * @author WuShuicheng .
	 * @param str
	 *            要判断的字符串 .
	 * @return true or false .
	 */
	public static boolean isNumeric(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		} else {
			return str.matches("\\d*");
		}
	}

	/**
	 * 获取字符串长度，当字符串为空时返回0.
	 * 
	 * @param str
	 *            .
	 * @return length .
	 */
	public static int strLength(String str) {
		if (StringUtils.isBlank(str)) {
			return 0;
		} else {
			return str.length();
		}
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为3位 ，当字符串为空时返回0.
	 * 
	 * @param str
	 *            字符串 .
	 * @return 字符串的长度 .
	 */
	public static int strLengthCn(String str) {
		if (StringUtils.isBlank(str)) {
			return 0;
		}
		int valueLength = 0;
		final String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int num = 0; num < str.length(); num++) {
			/* 获取一个字符 */
			final String temp = str.substring(num, num + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为3 */
				valueLength += 3;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 计算指定时间与当前时间的差
	 * 
	 * @param date
	 * @return
	 */
	public static String convDateToString(Date date) {
		Long time = new Date().getTime() - date.getTime();
		Long min = time / 1000 / 60;
		if (min < 5) {
			return "刚刚";
		} else if (min >= 5 && min < 60) {
			return min + "分钟之前";
		} else if (min >= 60 && min < 1440) {
			return min / 60 + "小时之前";
		} else if (min >= 1440 && min < 10080) {
			return min / 60 / 24 + "天之前";
		} else if (min >= 10080 && min < 40320) {
			return min / 60 / 24 / 7 + "周之前";
		} else if (min >= 40320 && min < 525600) {
			return min / 60 / 24 / 7 / 4 + "月之前";
		} else if (min >= 525600) {
			return min / 60 / 24 / 365 + "年之前";
		}
		return null;
	}

	/**
	 * @description 获取当前服务器日期
	 * @return
	 */
	public static String getCurrdate(String formatStr) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
		String mDateTime = formatter.format(cal.getTime());
		return mDateTime;
	}

	/**
	 * 将Object值转换成Double类型
	 * 
	 * @param value
	 * @return
	 */
	public static double getDoubleByObj(Object value) {
		if (value == null) {
			return 0;
		}
		return Double.valueOf(String.valueOf(value));
	}

	/**
	 * 将Object值转换成Float类型
	 * 
	 * @param value
	 * @return
	 */
	public static float getFloatByObj(Object value) {
		if (value == null) {
			return 0;
		}
		return Float.valueOf(String.valueOf(value));
	}

	/**
	 * 将Object值转换成Integer类型
	 * 
	 * @param value
	 * @return
	 */
	public static Integer getIntegerByObj(Object value) {
		if (value == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(value));
	}

	/**
	 * 解析字符串 ---> 去掉字符串中回车、换行、空格
	 * 
	 * @param str
	 *            被解析字符串
	 * @return String 解析后的字符串
	 */
	public static String parse(String str) {
		return str.replaceAll("\n", "").replaceAll("chr(13)", "")
				.replaceAll(" ", "");
	}

	public static Integer[] Str2Integers(String value) {
		if (null == value
				|| !org.springframework.util.StringUtils.hasText(value)) {
			return null;
		}
		String[] values = value.split(",");
		Integer[] v = new Integer[values.length];
		for (int i = 0; i < values.length; i++) {
			v[i] = Integer.parseInt(values[i]);
		}
		return v;
	}

	public static String[] Str2Strings(String value) {
		if (null == value
				|| !org.springframework.util.StringUtils.hasText(value)) {
			return null;
		}
		String[] values = value.split(",");
		String[] v = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			v[i] = values[i];
		}
		return v;
	}

	public static int strFormateInt(Object obj) {
		if (isNotNull(obj)) {
			return "是".equals(obj) ? 1 : 0;
		} else {
			return 0;
		}
	}

	/**
	 * 获取UUID
	 * 
	 * @return UUID
	 */
	public static String getUUID() {
		return (UUID.randomUUID() + "").replaceAll("-", "");
	}

	/**
	 * 获取去掉横线的长度为32的UUID串.
	 * 
	 * @author WuShuicheng.
	 * @return uuid.
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取带横线的长度为36的UUID串.
	 * 
	 * @author WuShuicheng.
	 * @return uuid.
	 */
	public static String get36UUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 将字符串转移为ASCII码
	 * 
	 * @param cnStr
	 * @return
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// System.out.println(Integer.toHexString(bGBK[i]&0xff));
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	/**
	 * 
	 * @param initCode
	 *            初始化编码
	 * @param length
	 *            需要生成编码长度
	 * @param ind
	 *            地增量
	 * @return 递增后的编码
	 */
	public static String getNextCode(String initCode, int length, int ind) {
		Integer temp = Integer.parseInt(initCode);
		temp = temp + ind;
		String tempCode = temp.toString();
		int tempLen = 0;
		if (tempCode.length() < length) {
			tempLen = length - tempCode.length();
		}
		for (int i = 0; i < tempLen; i++) {
			tempCode = "0" + tempCode;
		}
		return tempCode;
	}

	public static int switchNumber(String str) {
		char c = str.charAt(0);
		int temp = 0;
		switch (c) {
		// 数值
		case '〇':
		case '零':
			temp = 0;
			break;
		case '一':
			temp = 1;
			break;
		case '二':
			temp = 2;
			break;
		case '三':
			temp = 3;
			break;
		case '四':
			temp = 4;
			break;
		case '五':
			temp = 5;
			break;
		case '六':
			temp = 6;
			break;
		case '七':
			temp = 7;
			break;
		case '八':
			temp = 8;
			break;
		case '九':
			temp = 9;
			break;
		// 单位，前缀是单数字
		case '十':
			temp = 10;
			break;
		}
		return temp;
	}

	/**
	 * 中文数字转换为阿拉伯数
	 * 
	 * @param String
	 *            s
	 */
	public static int cnNumToInt(String s) {
		int result = 0;
		int yi = 1;// 记录高级单位
		int wan = 1;// 记录高级单位
		int ge = 1;// 记录单位
		char c = s.charAt(0);
		int temp = 0;// 记录数值
		switch (c) {
		// 数值
		case '〇':
		case '零':
			temp = 0;
			break;
		case '一':
			temp = 1 * ge * wan * yi;
			ge = 1;
			break;
		case '二':
			temp = 2 * ge * wan * yi;
			ge = 1;
			break;
		case '三':
			temp = 3 * ge * wan * yi;
			ge = 1;
			break;
		case '四':
			temp = 4 * ge * wan * yi;
			ge = 1;
			break;
		case '五':
			temp = 5 * ge * wan * yi;
			ge = 1;
			break;
		case '六':
			temp = 6 * ge * wan * yi;
			ge = 1;
			break;
		case '七':
			temp = 7 * ge * wan * yi;
			ge = 1;
			break;
		case '八':
			temp = 8 * ge * wan * yi;
			ge = 1;
			break;
		case '九':
			temp = 9 * ge * wan * yi;
			ge = 1;
			break;
		// 单位，前缀是单数字
		case '十':
			ge = 10;
			break;
		case '百':
			ge = 100;
			break;
		case '千':
			ge = 1000;
			break;
		// 高级单位，前缀可以是多个数字
		case '万':
			wan = 10000;
			ge = 1;
			break;
		case '亿':
			yi = 100000000;
			wan = 1;
			ge = 1;
			break;
		default:
			return -1;
		}
		result += temp;
		if (ge > 1) {
			result += 1 * ge * wan * yi;
		}
		return result;
	}

	public static String geneStrAry(String str, String splits) {
		if (StringUtil.isEmpty(str))
			return "";
		String[] ary = str.split(splits);
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < ary.length; i++) {
			sb.append("'");
			sb.append(ary[i]);
			sb.append("'");
			if (i < ary.length - 1)
				sb.append(",");
		}
		return sb.toString();
	}

	public static boolean equals(String str1, String str2) {
		return str1 == null ? false : str2 == null ? true : str1.equals(str2);
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? false : str2 == null ? true : str1
				.equalsIgnoreCase(str2);
	}

	/**
	 * 
	 * @param obj
	 *            传数值类型的obj
	 * @param format
	 * @return
	 */
	public static String decimalFormat(Object obj) {
		if (null == obj)
			return "";
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(obj);
	}

	/**
	 * 
	 * @param obj
	 *            传数值类型的obj
	 * @param format
	 * @return
	 */
	public static String decimalFormat(Object obj, String format) {
		if (null == obj)
			return "";
		DecimalFormat df = new DecimalFormat(format);
		return df.format(obj);
	}

	/**
	 * 去除html代码(HTML过滤还可以使用jsoup工具包进行处理).
	 * 
	 * @param inputString
	 *            含html标签的字符串 .
	 * @return 文本字符串 .
	 */
	public static String htmlToText(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		try {
			// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
			// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String patternStr = "\\s+";
			// 过滤script标签
			Pattern p_script = Pattern.compile(regEx_script,
					Pattern.CASE_INSENSITIVE);
			Matcher m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll("");
			// 过滤style标签
			Pattern p_style = Pattern.compile(regEx_style,
					Pattern.CASE_INSENSITIVE);
			Matcher m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll("");
			// 过滤html标签
			Pattern p_html = Pattern.compile(regEx_html,
					Pattern.CASE_INSENSITIVE);
			Matcher m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll("");
			// 过滤空格
			Pattern p_ba = Pattern
					.compile(patternStr, Pattern.CASE_INSENSITIVE);
			Matcher m_ba = p_ba.matcher(htmlStr);
			htmlStr = m_ba.replaceAll("");
		} catch (Exception e) {
			System.out.println("=== HtmlToText exception: " + e.getMessage());
		}
		return htmlStr; // 返回文本字符串
	}

	public static boolean isPhone(String phone) {
		boolean mobile = phone
				.matches("^1(3[0-9]|5[0-35-9]|8[025-9]|7[0-9])\\d{8}$");
		/**
		 * 中国移动：China Mobile
		 * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188,147
		 */
		boolean cm = phone
				.matches("^1(34[0-8]|(3[5-9]|5[017-9]|8[278]|47)\\d)\\d{7}$");
		/**
		 * 中国联通：China Unicom 130,131,132,152,155,156,185,186
		 */
		boolean cu = phone.matches("^1(3[0-2]|5[256]|8[56])\\d{8}$");
		/**
		 * 中国电信：China Telecom 133,1349,153,180,189
		 */
		boolean ct = phone.matches("^1((99|33|53|8[01349])[0-9]|349)\\d{7}$");
		if (mobile || cm || cu || ct) {
			return true;
		}
		return false;
	}

	public static Boolean isNumber(String number) {
		boolean n = number.matches("^[0-9]*$");
		if (n) {
			return true;
		}
		return false;
	}

	public static Boolean isEmail(String email) {
		boolean mail = email
				.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		if (mail) {
			return true;
		}
		return false;
	}

	public static Boolean isUsername(String username) {
		// Boolean us =
		// username.matches("^.{0,4}$|.{21,}|^[^A-Za-z0-9\u4E00-\u9FA5]|[^\\w\u4E00-\u9FA5.-]|([_.-])\1");
		Boolean us = username.matches("^[a-zA-Z0-9_\\W]{3,20}$");
		if (us) {
			return true;
		}
		return false;
	}

	public static Boolean isVerifyCode(String code) {
		Boolean vc = code.matches("^[0-9]{6}$");
		if (vc) {
			return true;
		}
		return false;
	}

	public static Boolean isPassword(String password) {
		Boolean pwd = password.matches("^[a-zA-Z0-9_\\W]{6,16}$");
		if (pwd) {
			return true;
		}
		return false;
	}

	public static Boolean isBirthday(String birthday) {
		Boolean bth = birthday
				.matches("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$");
		if (bth) {
			return true;
		}
		return false;
	}

	/**
	 * YYYYMMDDHHMMSS+6位自增长码(20位)
	 * 
	 * @author shijing 2015年6月29日下午1:25:23
	 * @return
	 */
	public static synchronized String getLocalTrmSeqNum() {
		sequence = sequence >= 9 ? 1 : sequence + 1;
		String datetime = new SimpleDateFormat("yyMMddHHmmss")
				.format(new Date());
		String s = Integer.toString(sequence);
		Random ran = new Random();
		String rans = Integer.toString(ran.nextInt(89999) + 10000);
		return datetime + rans + addLeftZero(s, length);
	}

	/**
	 * 左填0
	 * 
	 * @author shijing 2015年6月29日下午1:24:32
	 * @param s
	 * @param length
	 * @return
	 */
	public static String addLeftZero(String s, int length) {
		// StringBuilder sb=new StringBuilder();
		int old = s.length();
		if (length > old) {
			char[] c = new char[length];
			char[] x = s.toCharArray();
			if (x.length > length) {
				throw new IllegalArgumentException(
						"Numeric value is larger than intended length:" + s
								+ length);
			}
			int lim = c.length - x.length;
			for (int i = 0; i < lim; i++) {
				c[i] = '0';
			}
			System.arraycopy(x, 0, c, lim, x.length);
			return new String(c);
		}
		return s.substring(0, length);

	}
}
