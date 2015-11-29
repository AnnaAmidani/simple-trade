package com.jpm.trade.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class TradeUtil {
	
	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randIntInRange(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	/**
	 * 
	 * @param min
	 * @param max
	 * @param precision
	 * @return
	 */
	public static BigDecimal randBigDecimalInRange(double min, double max, int precision) {
		Random rand = new Random();
		double range = max - min;
		double scaled = rand.nextDouble() * range;
		double shifted = scaled + min;
		BigDecimal random = new BigDecimal(shifted);
		random.setScale(precision, RoundingMode.HALF_UP);
		return random;
	}

	public static String formatGBP(BigDecimal num) {
		return "GBP " + num.setScale(2, RoundingMode.HALF_UP).toString();
	}

}
