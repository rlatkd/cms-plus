package kr.or.kosa.cmsplusmain.util;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatUtil {

	private static final String MONEY_FORMAT = "###,###";
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String formatMoney(Long money) {
		DecimalFormat df = new DecimalFormat(MONEY_FORMAT);
		return df.format(money);
	}

	public static String formatDateTime(LocalDateTime dateTime) {
		return format(dateTime, DATE_TIME_FORMAT);
	}

	public static String format(LocalDateTime dateTime, String pattern) {
		if (dateTime == null) {
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return dateTime.format(formatter);
	}
}
