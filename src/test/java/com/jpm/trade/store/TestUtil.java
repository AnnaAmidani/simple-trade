package com.jpm.trade.store;

import java.math.BigDecimal;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.util.StockType;

/**
 * Unit test for simple TradeSession.
 */
public class TestUtil {
	
	public static StockModel buildStock(BigDecimal parValue, BigDecimal tickerPrice, int lastDividend, String symbol, StockType type) {
		StockModel stock = new StockModel();
		stock.setParValue(parValue);
		stock.setTickerPrice(tickerPrice);
		stock.setLastDividend(lastDividend);
		stock.setSymbol(symbol);
		stock.setType(type);
		return stock;
	}
}
