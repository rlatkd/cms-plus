package kr.or.kosa.cmsplusmain.util;

import java.text.DecimalFormat;

public class FormatUtil {

	private static final String MONEY_FORMAT = "###,###";

	public static String formatMoney(Long money) {
		DecimalFormat df = new DecimalFormat(MONEY_FORMAT);
		return df.format(money);
	}
}
