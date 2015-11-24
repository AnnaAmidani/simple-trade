package com.jpm.trade.util;

import java.util.Random;

public enum OperationType {
	BUY, SELL;

	private static final OperationType[] operationTypes = values();
	private static final int enumSize = operationTypes.length;
	private static final Random random = new Random();

	
	public static OperationType getRandomOperationType() {
		return operationTypes[random.nextInt(enumSize)];
	}
}
