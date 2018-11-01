package ge.umas.etl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Etl {

	public static boolean IsGeorgianCodePage(String text) {
		boolean result = false;
		if (text != null) {
			String patternText = "[ა-ჰ]";
			Pattern pattern = Pattern.compile(patternText);
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				result = true;
			}
		}
		return result;
	}

	public static String ParseDate(String str) {
		String result = null;
		if (str != null) {
			DateTimeFormatter formatterR;
			DateTimeFormatter formatterW = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime date = null;
			String patternText;
			Pattern pattern;
			String matchedText;
			if (str.length() == 10) {
				str = str + " 00:00:00";
			}
			patternText = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
			pattern = Pattern.compile(patternText);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				try {
					matchedText = matcher.group(0).toString();
					formatterR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					date = LocalDateTime.parse(matchedText, formatterR);
					result = formatterW.format(date);
				} catch (DateTimeException e) {
					result = null;
				}
			} else {
				patternText = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
				pattern = Pattern.compile(patternText);
				matcher = pattern.matcher(str);
				if (matcher.find()) {
					try {
						matchedText = matcher.group(0).toString();
						formatterR = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
						date = LocalDateTime.parse(matchedText, formatterR);
						result = formatterW.format(date);
					} catch (DateTimeException e) {
						result = null;
					}
				} else {
					result = null;
				}
			}
		}
		return result;
	}
	
		public static String ParseValidLibraryString(String str) {
		String result = null;
		if ((str != null) && (str.length() > 0)) {
			result = str;
			result = result.replaceAll("[\n\r]", " ");
			result = result.replaceAll("[`\\\\]", "");
			int i = 0;
			while (result.length() - 1 > i) {
				String ts = result.substring(i, i + 2);
				if ("  ".contains(String.valueOf(ts))) {
					result = result.replace("  ", " ");
				} else {
					i = i + 1;
				}
			}
			result = result.trim();
			if (!str.equals(result))

				if (result.length() == 0) {
					result = null;
				}
		}
		return result;
	}

	public static String ParseLong(String str) {
		String result = null;
		if ((str != null) && (str.length() > 0)) {
			if (str.equals("NULL")||str.equals("null")) {
				return result;
			}
			result = str.trim();
			Long longData;
			try {
				longData = Long.parseLong(result);
				result = longData.toString();
			} catch (NumberFormatException e) {
				result = null;
			}
		}
		return result;
	}

	public static String ParseValidTextString(String str) {
		String result = null;
		if ((str != null) && (str.length() > 0)) {
			if (str.equals("NULL")||str.equals("null")) {
				return result;
			}
			result = str;
			result = result.replaceAll("[\n\r]", " ");
			result = result.replaceAll("[`\\\\]", "");
			result = result.replaceAll("\\u0026amp;", "AND");
			result = result.replaceAll("\\u0026", "AND");
			result = result.trim();
			if (result.length() == 0) {
				result = null;
			}
		}
		return result;
	}

	public static String ParseValidNameString(String str) {
		String result = null;
		if ((str != null) && (str.length() > 0)) {
			if (str.equals("NULL")||str.equals("null")) {
				return result;
			}
			result = str;
			result = result.replaceAll("[,.\\-\\-\r\n]", " ");
			result = result.replaceAll("[0-9]|[`\\\\~!@#$%^&*()+=}{|\":;?\\/><|_\\[\\]]", "");
			result = result.replaceAll("\\u0026amp;", "AND");
			result = result.replaceAll("\\u0026", "AND");
			result = result.replaceAll(" {1,}", " ");
			while (result.length() > 0) {
				char ch = result.charAt(0);
				if ("[ ']".contains(String.valueOf(ch))) {
					result = result.substring(1);
				} else {
					break;
				}
			}
			result = result.trim();
			result = result.toUpperCase();
			if (result.length() == 0) {
				result = null;
			}
		}
		return result;
	}

	public static String ParseDecimal(String str) {
		String result = null;
		if ((str != null) && (str.length() > 0)) {
			if (str.equals("NULL")||str.equals("null")) {
				return result;
			}
			if (str != null)
				result = str.replaceAll("[ ]", "");
			double decimalData;
			try {
				DecimalFormat format = (DecimalFormat) NumberFormat.getInstance();
				DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
				char sep = symbols.getDecimalSeparator();
				result = result.replaceAll("[,.]", String.valueOf(sep));
				decimalData = Double.parseDouble(result);
				result = String.valueOf(decimalData);
			} catch (NumberFormatException e) {
				result = null;
			}
		}
		return result;
	}

	public static boolean CodePageControl(String text) {
		boolean result = false;
		if (text != null) {
			String patternText = "[a-zA-Z]";
			Pattern pattern = Pattern.compile(patternText);
			Matcher matcher = pattern.matcher(text);
			if (matcher.find()) {
				result = true;
			}
		}
		return result;
	}

}
