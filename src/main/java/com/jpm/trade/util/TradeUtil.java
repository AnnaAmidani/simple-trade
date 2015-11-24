package com.jpm.trade.util;

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
	 * @return
	 */
	public static double randDoubleInRange(double min, double max) {
		Random rand = new Random();
		double range = max - min;
		double scaled = rand.nextDouble() * range;
		double shifted = scaled + min;
		return shifted;
	}
}
