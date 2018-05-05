package com.test.base.tool;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ConvertUtil {

	public static BigDecimal convertToDec(Object o) {
		if (o == null)
			return null;
		if (o instanceof BigDecimal) {
			return (BigDecimal) o;
		}
		if (o instanceof Double) {
			return new BigDecimal(((Double) o).doubleValue());
		}
		if (o instanceof Integer) {
			return new BigDecimal(((Integer) o).intValue());
		}
		return new BigDecimal(o.toString());
	}

	public static String convertToString(Object o) {
		if (o == null)
			return null;
		return o.toString();
	}

	public static Integer convertToInt(Object o) {
		if (o == null)
			return null;
		if (o instanceof Integer) {
			return (Integer) o;
		}
		return new Integer(o.toString());
	}

	public static Long convertToLong(Object o) {
		if (o == null)
			return null;
		if (o instanceof Long) {
			return (Long) o;
		}
		if (o instanceof Double) {
			return ((Double) o).longValue();
		}
		return new Long(o.toString());
	}

	public static Double convertToDouble(Object o) {
		if (o == null)
			return null;
		if (o instanceof Double) {
			return (Double) o;
		}
		return new Double(o.toString());
	}

	public static Boolean convertToBool(Object o) {
		if (o == null)
			return null;
		if (o instanceof Boolean) {
			return (Boolean) o;
		}
		if (o instanceof Number) {
			return ((Number) o).doubleValue() != 0;
		}
		return new Boolean(o.toString());
	}

	public static Date convertToDate(Object o) {
		if (o == null)
			return null;
		if (o instanceof Date) {
			return (Date) o;
		}
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(o.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static final HashMap<String, String> TYPE_MAPPING;
	static {
		TYPE_MAPPING = new HashMap<String, String>();
		TYPE_MAPPING.put("java.lang.Integer", "convertToInt");
		TYPE_MAPPING.put("int", "convertToInt");
		TYPE_MAPPING.put("java.lang.String", "convertToString");
		TYPE_MAPPING.put("java.math.BigDecimal", "convertToDec");
		TYPE_MAPPING.put("java.lang.Double", "convertToDouble");
		TYPE_MAPPING.put("double", "convertToDouble");
		TYPE_MAPPING.put("java.lang.Boolean", "convertToBool");
		TYPE_MAPPING.put("boolean", "convertToBool");
		TYPE_MAPPING.put("java.util.Date", "convertToDate");
		TYPE_MAPPING.put("java.lang.Long", "convertToLong");
		TYPE_MAPPING.put("long", "convertToLong");
	}
}
